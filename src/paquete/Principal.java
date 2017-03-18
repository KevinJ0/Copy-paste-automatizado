package paquete;

import java.awt.*;

import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.xml.ws.BindingType;

public class Principal {

	public static void main(String[] args) {

		Marco M = new Marco();
		M.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}

class Marco extends JFrame {
	private boolean sele;
	private JScrollPane Scroll;
	private int tamanoScroll = 0;
	private int cont = 0;
	int nume = 0;
	private int largo = 0;
	private File direcP;
	private File direcC;
	private JButton Boton_Extension;
	private JButton botonEscogerC;
	private JButton botonEscogerP;
	private JButton botonEmpezar;
	private JTextField campoEscogerC = new JTextField(35);
	private JTextField campoEscogerP = new JTextField(35);
	private JLabel Leibol_agregar;
	private JTextField Campo_Extension;
	private JPanel Lamina_Central = new JPanel();
	private Listformato misextenciones = new Listformato();
	private Copiar copi = new Copiar();
 	private ArrayList<BufferedInputStream> miBuferd = new ArrayList<BufferedInputStream>();
	private ArrayList<File> listArchivos = new ArrayList<File>();
	private ArrayList<laminaE> setLaminas = new ArrayList<laminaE>();
	private boolean resultado = false;

	public Marco() {

		// miBuferd=copi.getBIS();
		// pega.comenzar(miBuferd,listArchivos,Copiar.selecDirectorio());

		JPanel Lamina_Sur = new JPanel(new GridLayout(4, 1));
		JPanel lamina4 = new JPanel();

		JPanel lamina3 = new JPanel();
		JPanel lamina2 = new JPanel();
		JPanel lamina1 = new JPanel();

		// setBounds(400, 100, 700, 400);
		setLayout(new BorderLayout());
		botonEmpezar = new JButton("Empezar");
		botonEmpezar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				y: if (!campoEscogerC.getText().isEmpty()) {
					if (!campoEscogerP.getText().isEmpty()) {
						direcC = new File(campoEscogerC.getText().trim());
						if (direcC.isDirectory()) {
							if (direcC.canRead()) {
								resultado = copi.comenzar(direcC, misextenciones);
								listArchivos = copi.getListFile();
								miBuferd = copi.getBIS();
							} else {
								JOptionPane.showConfirmDialog(null,
										"El directorio de donde se van a copiar los archivos no puede ser leido",
										"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
								break y;
							}
						} else {
							JOptionPane.showConfirmDialog(null,
									"El directorio de donde se van a copiar los archivos no es valido", "Error",
									JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
							break y;
						}
						if (resultado) {
							if (miBuferd.size() != 0 || listArchivos.size() != 0) {
								direcP = new File(campoEscogerP.getText().trim());
								if (direcP.isDirectory()) {
									if (direcP.canWrite()) {
										System.out.println("El cantidad de : " + listArchivos.size());
										

										Pegar pega = new Pegar(miBuferd, listArchivos, direcP);
										pega.comenzar();
									} else {
										JOptionPane.showConfirmDialog(null,
												"El directorio en donde se van a copiar los archivos no puede ser escrito",
												"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
									}

								} else {
									JOptionPane.showConfirmDialog(null,
											"El directorio en donde se van a copiar los archivos no es valido", "Error",
											JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					} else {
						JOptionPane.showConfirmDialog(null,
								"Debe escoger un directorio en donde se van a copiar los archivos", "Error",
								JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					}

				} else {
					JOptionPane.showConfirmDialog(null,
							"Debe escoger un directorio de donde se van a copiar los archivos", "Error",
							JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		Leibol_agregar = new JLabel("Nuevo formato:");
		Campo_Extension = new JTextField(8);
		Campo_Extension.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				sele = false;
				if (Campo_Extension.getSelectedText() != null) {
					if (Campo_Extension.getSelectedText().length() == 10)
						sele = true;
				}
				if (Campo_Extension.getText().length() >= 10 && !sele) { // no
																			// se
																			// loque
																			// hice
																			// pero
																			// funcionó
																			// :|

					e.consume();
				}
			}

			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == 10) {
					String extension = Campo_Extension.getText();
					a: if (!extension.isEmpty()) {
						Iterator<String> ite = misextenciones.iterator();
						while (ite.hasNext()) {
							if (ite.next().equals(("." + extension))) {
								JOptionPane.showConfirmDialog(null, "Esta extension ya existe", "Extension existente",
										JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
								break a;
							}
						}
						String id = "extension" + String.valueOf((int) (Math.random() * 1000000000));
						laminaE nuevaLamina = new laminaE(extension, id);
						Lamina_Central.add(nuevaLamina);
						setLaminas.add(nuevaLamina);
						largo = largo + nuevaLamina.getPreferredSize().width;

						if (largo >= 680) {
							// System.out.println("Se cumple la condicion con: "
							// + largo);
							cont++;
							if (cont > 4) {
								Lamina_Central.setPreferredSize(new Dimension(680,
										(nuevaLamina.getPreferredSize().height + Lamina_Central.getHeight() + 5))); // es
																													// la
																													// distancia
																													// vertical
																													// entre
																													// los
																													// paneles
																													// para
																													// abajo
							}
							largo = nuevaLamina.getPreferredSize().width;
							// System.out.println(nuevaLamina.getPreferredSize().height);
						}
						// System.out.println(largo);
						Lamina_Central.updateUI();
						misextenciones.setFormato(extension);
					}

				}
			}

			public void keyReleased(KeyEvent arg0) {

			}

		});

		InputMap map2 = Campo_Extension.getInputMap(JComponent.WHEN_FOCUSED);
		map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");

		Boton_Extension = new JButton("Agregar");
		Boton_Extension.addActionListener(new Agregar());
		botonEscogerC = new JButton("Escoger");
		botonEscogerC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Copiar.selecDirectorio() != null) {
					direcC = Copiar.dameRuta();
					campoEscogerC.setText(direcC.getAbsolutePath());
				}
			}

		});
		botonEscogerP = new JButton("Escoger");
		botonEscogerP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (Copiar.selecDirectorio() != null) {
					direcP = Copiar.dameRuta();
					campoEscogerP.setText(direcP.getAbsolutePath());
				}
			}

		});

		lamina1.add(Leibol_agregar);
		lamina1.add(Campo_Extension);
		lamina1.add(Boton_Extension);
		lamina2.add(new JLabel("Copiar de:"));
		lamina2.add(campoEscogerC);
		lamina2.add(botonEscogerC);
		lamina3.add(new JLabel("Pegar en:"));
		lamina3.add(campoEscogerP);
		lamina3.add(botonEscogerP);
		lamina4.add(botonEmpezar);

		Lamina_Sur.add(lamina1);
		Lamina_Sur.add(lamina2);
		Lamina_Sur.add(lamina3);
		Lamina_Sur.add(lamina4);

		Scroll = new JScrollPane(Lamina_Central);

		Scroll.setPreferredSize(new Dimension(700, 220));
		Scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(0), "Extensiones"));
		tamanoScroll = Scroll.getPreferredSize().width;
		Lamina_Central.setLayout(new FlowLayout(FlowLayout.LEFT));
		Lamina_Central.setPreferredSize(new Dimension(680, 36)); // es la altura
																	// de los
																	// paneles
																	// (laminasE),
																	// en su
																	// defecto
																	// será
																	// cambiado

		add(Lamina_Sur, BorderLayout.SOUTH);
		add(Scroll, BorderLayout.CENTER);

		pack();
		setResizable(false);
		setLocationByPlatform(true);
		setVisible(true);

	}

	private class Agregar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String extension = Campo_Extension.getText();
			k: if (!extension.isEmpty()) {
				Iterator<String> ite = misextenciones.iterator();
				while (ite.hasNext()) {
					if (ite.next().equals(("." + extension))) {
						JOptionPane.showConfirmDialog(null, "Esta extension ya existe", "Extension existente",
								JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
						break k;
					}
				}
				String id = "extension" + String.valueOf((int) (Math.random() * 1000000000));
				laminaE nuevaLamina = new laminaE(extension, id);

				Lamina_Central.add(nuevaLamina);
				setLaminas.add(nuevaLamina);
				largo = largo + nuevaLamina.getPreferredSize().width;

				if (largo >= tamanoScroll) {
					// System.out.println("Se cumple la condicion con: " +
					// largo);
					cont++;

					if (cont > 4) {
						Lamina_Central.setPreferredSize(new Dimension(tamanoScroll,
								(nuevaLamina.getPreferredSize().height + Lamina_Central.getHeight() + 5)));// 5
																											// es
																											// la
																											// distancia
																											// horizontal
																											// entre
																											// las
																											// laminas

					}
					largo = nuevaLamina.getPreferredSize().width;
					// System.out.println(nuevaLamina.getPreferredSize().height);
				}
				// System.out.println(largo);
				Lamina_Central.updateUI();
				misextenciones.setFormato(extension);
			}
		}

	}

	class laminaE extends JPanel {
		String extension;
		int numero;
		String ID;

		String getI() {
			return this.ID;
		}

		public laminaE(String extension, String ID) {
			this.ID = ID;
			this.extension = extension;
			nume = nume + 1;
			JButton botonExten = new JButton("Quitar " + extension);
			accionQ miaccion = new accionQ(ID, extension);
			botonExten.addActionListener(miaccion);
			add(botonExten);

		}

		class accionQ implements ActionListener {

			int b = nume;
			String extencion;
			String ID;

			public accionQ(String e, String extencion) {
				this.ID = e;
				this.extencion = extencion;
			}

			String dameI() {
				return this.ID;
			}

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {

					int tamaño = setLaminas.size();
					romper: for (int i = 0; i < tamaño; i++) {
						if (setLaminas.get(i).getI() == dameI()) {
							Lamina_Central.remove(setLaminas.get(i));
							setLaminas.remove(i);
							break romper;
						}
					}
					tamaño = misextenciones.size();

					romper1: for (int i1 = 0; i1 < tamaño; i1++) {

						if (misextenciones.get(i1).equals(("." + extencion))) {
							// System.out.println(misextenciones.size());
							misextenciones.remove(i1);
							break romper1;
						}
					}

					Lamina_Central.updateUI();

				} catch (Throwable e) {
					e.printStackTrace();
				}
			}

		}
	}
}
