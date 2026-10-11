package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import negocio.ArchivoPais;
import negocio.Pais;
import negocio.Similaridad;

public class MainForm {

	private JFrame frame;

	// Todos los datos cargados, la ventana solo los muestra
	private Pais pais;

	// Controles que se usan fuera del método donde se crean
	private JTextField txtProvincia;
	private DefaultListModel<String> modeloProvincias;
	private JComboBox<String> cmbProvincia1;
	private JComboBox<String> cmbProvincia2;
	private JTextField txtPeso;
	private DefaultTableModel modeloSimilaridades;
	private JTable tablaSimilaridades;
	private JTextField txtCantidadRegiones;
	private JCheckBox chkRecalcular;
	private DefaultListModel<String> modeloRegiones;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		pais = new Pais();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Diseñando regiones");
		// La consigna pide no pasar de 1366 x 768
		frame.setBounds(100, 100, 1100, 650);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(5, 5));

		// Cada paso de la carga va en su propio panel, de izquierda a derecha
		frame.getContentPane().add(crearPanelProvincias(), BorderLayout.WEST);
		frame.getContentPane().add(crearPanelSimilaridades(), BorderLayout.CENTER);
		frame.getContentPane().add(crearPanelRegiones(), BorderLayout.EAST);
		frame.getContentPane().add(crearPanelInferior(), BorderLayout.SOUTH);
	}

	// Panel 1, para cargar provincias y ver la lista de las que ya están
	private JPanel crearPanelProvincias() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(BorderFactory.createTitledBorder("1. Provincias"));
		panel.setPreferredSize(new Dimension(240, 0));

		// Campo de texto con el botón al lado
		JPanel panelCarga = new JPanel(new BorderLayout(5, 5));
		txtProvincia = new JTextField();
		JButton btnAgregar = new JButton("Agregar");
		panelCarga.add(txtProvincia, BorderLayout.CENTER);
		panelCarga.add(btnAgregar, BorderLayout.EAST);
		panel.add(panelCarga, BorderLayout.NORTH);

		// Lista con las provincias cargadas
		modeloProvincias = new DefaultListModel<>();
		panel.add(new JScrollPane(new JList<>(modeloProvincias)), BorderLayout.CENTER);

		// Las validaciones las hace Pais, acá solo mostramos el error
		ActionListener agregarProvincia = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pais.agregarProvincia(txtProvincia.getText());
					// Limpiamos el campo para cargar la siguiente
					txtProvincia.setText("");
					actualizarPantalla();
				} catch (IllegalArgumentException ex) {
					mostrarError(ex.getMessage());
				}
				txtProvincia.requestFocus();
			}
		};
		btnAgregar.addActionListener(agregarProvincia);
		// Con Enter en el campo de texto también se agrega
		txtProvincia.addActionListener(agregarProvincia);

		return panel;
	}

	// Panel 2, para cargar la similaridad entre dos provincias vecinas
	private JPanel crearPanelSimilaridades() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(BorderFactory.createTitledBorder("2. Similaridad entre provincias vecinas"));

		JPanel panelCarga = new JPanel(new GridLayout(2, 1));
		// Fila 1, las dos provincias que se eligen de las cargadas
		JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cmbProvincia1 = new JComboBox<>();
		cmbProvincia2 = new JComboBox<>();
		cmbProvincia1.setPreferredSize(new Dimension(220, 25));
		cmbProvincia2.setPreferredSize(new Dimension(220, 25));
		fila1.add(cmbProvincia1);
		fila1.add(new JLabel("y"));
		fila1.add(cmbProvincia2);

		// Fila 2, el peso y el botón para agregar
		JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		txtPeso = new JTextField(5);
		JButton btnAgregar = new JButton("Agregar / modificar");
		JButton btnEliminar = new JButton("Eliminar seleccionada");
		JLabel lblPeso = new JLabel("Similaridad");
		// El método arma un árbol mínimo y corta las aristas más pesadas,
		// por eso un número bajo significa que las provincias se parecen mucho
		lblPeso.setToolTipText("Mientras más bajo el número, más parecidas son las provincias");
		txtPeso.setToolTipText(lblPeso.getToolTipText());
		fila2.add(lblPeso);
		fila2.add(txtPeso);
		fila2.add(btnAgregar);

		panelCarga.add(fila1);
		panelCarga.add(fila2);
		panel.add(panelCarga, BorderLayout.NORTH);

		modeloSimilaridades = new DefaultTableModel(new Object[] { "Provincia", "Provincia", "Similaridad" }, 0) {
			// La tabla es solo para mirar, se modifica con los controles de arriba
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		// Se puede elegir una sola fila, que es la que borra el botón Eliminar
		tablaSimilaridades = new JTable(modeloSimilaridades);
		tablaSimilaridades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel.add(new JScrollPane(tablaSimilaridades), BorderLayout.CENTER);

		JPanel panelEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelEliminar.add(btnEliminar);
		panel.add(panelEliminar, BorderLayout.SOUTH);

		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Si no hay provincias los combos están vacíos
				if (cmbProvincia1.getSelectedItem() == null || cmbProvincia2.getSelectedItem() == null) {
					mostrarError("Primero hay que cargar al menos dos provincias");
					return;
				}
				int peso;
				try {
					peso = Integer.parseInt(txtPeso.getText().trim());
				} catch (NumberFormatException ex) {
					mostrarError("La similaridad tiene que ser un número entero");
					return;
				}
				// Si ya existía la similaridad, Pais le cambia el peso
				try {
					pais.agregarSimilaridad((String) cmbProvincia1.getSelectedItem(),
							(String) cmbProvincia2.getSelectedItem(), peso);
					txtPeso.setText("");
					actualizarPantalla();
				} catch (IllegalArgumentException ex) {
					mostrarError(ex.getMessage());
				}
			}
		});

		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// getSelectedRow devuelve -1 si no hay ninguna fila elegida
				int fila = tablaSimilaridades.getSelectedRow();
				if (fila == -1) {
					mostrarError("Seleccioná una similaridad de la tabla");
					return;
				}
				// Las dos primeras columnas tienen los nombres de las provincias
				pais.eliminarSimilaridad((String) modeloSimilaridades.getValueAt(fila, 0),
						(String) modeloSimilaridades.getValueAt(fila, 1));
				actualizarPantalla();
			}
		});

		return panel;
	}

	// Panel 3, para elegir k, calcular y ver las regiones
	private JPanel crearPanelRegiones() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(BorderFactory.createTitledBorder("3. Regiones"));
		panel.setPreferredSize(new Dimension(320, 0));

		JPanel panelOpciones = new JPanel(new GridLayout(3, 1));
		JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		txtCantidadRegiones = new JTextField(5);
		fila1.add(new JLabel("Cantidad de regiones (k)"));
		fila1.add(txtCantidadRegiones);

		JButton btnCalcular = new JButton("Calcular regiones");
		chkRecalcular = new JCheckBox("Recalcular al modificar el mapa");

		panelOpciones.add(fila1);
		panelOpciones.add(btnCalcular);
		panelOpciones.add(chkRecalcular);
		panel.add(panelOpciones, BorderLayout.NORTH);

		// Acá se muestra el resultado, el nombre de cada región y abajo sus provincias
		modeloRegiones = new DefaultListModel<>();
		panel.add(new JScrollPane(new JList<>(modeloRegiones)), BorderLayout.CENTER);

		ActionListener calcular = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcularRegiones(true);
			}
		};
		btnCalcular.addActionListener(calcular);
		// Con Enter en el campo de k también se calcula
		txtCantidadRegiones.addActionListener(calcular);

		return panel;
	}

	// Botones de abajo para abrir, guardar y empezar de nuevo
	private JPanel crearPanelInferior() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnAbrir = new JButton("Abrir archivo...");
		JButton btnGuardar = new JButton("Guardar archivo...");
		JButton btnLimpiar = new JButton("Borrar todo");
		panel.add(btnAbrir);
		panel.add(btnGuardar);
		panel.add(btnLimpiar);

		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selector = crearSelectorDeArchivos();
				// Si el usuario cancela no hacemos nada
				if (selector.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION)
					return;
				try {
					// Lo que había cargado se reemplaza por lo del archivo
					pais = ArchivoPais.leer(selector.getSelectedFile().toPath());
					modeloRegiones.clear();
					actualizarPantalla();
				} catch (IOException ex) {
					mostrarError("No se pudo leer el archivo");
				} catch (IllegalArgumentException ex) {
					// El mensaje ya trae el número de línea del error
					mostrarError("El archivo tiene un error. " + ex.getMessage());
				}
			}
		});

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser selector = crearSelectorDeArchivos();
				if (selector.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION)
					return;
				try {
					ArchivoPais.guardar(pais, selector.getSelectedFile().toPath());
				} catch (IOException ex) {
					mostrarError("No se pudo guardar el archivo");
				}
			}
		});

		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Preguntamos antes porque no se puede deshacer
				int respuesta = JOptionPane.showConfirmDialog(frame, "¿Borrar todas las provincias y similaridades?",
						"Borrar todo", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					pais = new Pais();
					modeloRegiones.clear();
					actualizarPantalla();
				}
			}
		});

		return panel;
	}

	// Arranca en la carpeta datos del proyecto si existe,
	// que es donde dejamos los archivos de ejemplo
	private JFileChooser crearSelectorDeArchivos() {
		File carpetaDatos = new File("datos");
		return new JFileChooser(carpetaDatos.isDirectory() ? carpetaDatos : null);
	}

	// Vuelve a cargar todos los controles con lo que tiene el país
	private void actualizarPantalla() {
		List<String> provincias = pais.obtenerProvincias();

		// Lista de provincias
		modeloProvincias.clear();
		for (String provincia : provincias) {
			modeloProvincias.addElement(provincia);
		}

		// Combos, el segundo arranca en otra provincia para no elegir la misma
		cmbProvincia1.setModel(new DefaultComboBoxModel<>(provincias.toArray(new String[0])));
		cmbProvincia2.setModel(new DefaultComboBoxModel<>(provincias.toArray(new String[0])));
		if (provincias.size() > 1) {
			cmbProvincia2.setSelectedIndex(1);
		}

		// Tabla de similaridades, se vacía y se vuelve a llenar
		modeloSimilaridades.setRowCount(0);
		for (Similaridad similaridad : pais.obtenerSimilaridades()) {
			modeloSimilaridades.addRow(new Object[] { similaridad.getProvincia1(), similaridad.getProvincia2(),
					similaridad.getPeso() });
		}

		if (chkRecalcular.isSelected()) {
			calcularRegiones(false);
		}
	}

	// Si lo pidió el usuario con el botón mostramos los errores en un cartel.
	// Sino solo lo avisamos en la lista de regiones
	private void calcularRegiones(boolean pedidoPorElUsuario) {
		modeloRegiones.clear();
		try {
			int k = leerCantidadDeRegiones();
			List<List<String>> regiones = pais.calcularRegiones(k);
			// Una línea con el nombre de la región y abajo una por provincia
			for (int i = 0; i < regiones.size(); i++) {
				List<String> region = regiones.get(i);
				modeloRegiones.addElement("Región " + (i + 1) + " (" + region.size() + " provincias)");
				for (String provincia : region) {
					modeloRegiones.addElement("      " + provincia);
				}
				modeloRegiones.addElement(" ");
			}
		} catch (IllegalStateException | IllegalArgumentException ex) {
			if (pedidoPorElUsuario) {
				mostrarError(ex.getMessage());
			} else {
				modeloRegiones.addElement("No se pueden calcular las regiones");
				modeloRegiones.addElement(ex.getMessage());
			}
		}
	}

	// El rango de k lo controla el negocio, acá solo vemos que sea un número
	private int leerCantidadDeRegiones() {
		try {
			return Integer.parseInt(txtCantidadRegiones.getText().trim());
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("La cantidad de regiones tiene que ser un número entero");

		}
	}

	// Cartel de error que usan todos los botones
	private void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
