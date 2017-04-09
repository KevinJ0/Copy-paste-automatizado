package paquete;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.TrayIcon.MessageType;

public class Principal {

	public static void main(String[] args) {

		 try {
		 try {
		 UIManager.setLookAndFeel(new
		 javax.swing.plaf.nimbus.NimbusLookAndFeel());
		 } catch (UnsupportedLookAndFeelException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 } catch (Exception e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }

		Marco M = new Marco();
		// M.setVisible(true);
		// moni.setMillisToDecideToPopup(2);

		// M.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);-------------------------------------------------------

	}

	static class Marco extends JFrame {
		/**
		 * 
		 */
		private static tempo mit = new tempo();
		private static boolean completado = false;
		private Properties P = new Properties();
		private static final long serialVersionUID = 1L;
		private static boolean mostrar = true;
		private static boolean en_proceso;
		private boolean sele;
		private boolean resultado = false;
		private long tiempo;
		private int listArchivos = 0;
		private int nume = 0;
		final static int LADOY = Toolkit.getDefaultToolkit().getScreenSize().height - 110;
		final static int LADOX = Toolkit.getDefaultToolkit().getScreenSize().width - 5;
		private static int ultimo;
		private static int carga;
		private static double cargad = 0.0;

		// private int i4 = 0;
		// private boolean limite;
		private static marcoN mimarcoN; // cuadro de carga en proceso
		private JScrollPane Scroll;
		// private int tamanoScroll = 0;
		// private int cont = 0;
		// private int largo = 0;
		private File direcP;
		private File direcC;
		private static JButton cancelar;
		private JButton Boton_Extension;
		private JButton botonEscogerC;
		private JButton botonEscogerP;
		private static JButton botonEmpezar;
		private static JTextField campoEscogerC = new JTextField(35);
		private static JTextField campoEscogerP = new JTextField(35);
		private JLabel Leibol_agregar;
		private JTextField Campo_Extension;
		private static JPanel Lamina_Sur = new JPanel(new GridLayout(4, 1));
		private Listformato misextenciones = new Listformato();
		private static Copiar copi = new Copiar();
		private ArrayList<laminaE> setLaminas = new ArrayList<laminaE>();
		private static JProgressBar barra = new JProgressBar(0, 100);
		private static JProgressBar barraN = new JProgressBar(0, 100);
		private static JPanel laminaN = new JPanel(new BorderLayout());
		private JPanel Lamina_Central = new JPanel();
		private static JPanel lamina4 = new JPanel();
		private static JPanel lamina3 = new JPanel();
		private static JPanel lamina2 = new JPanel();
		private static JPanel lamina1 = new JPanel();
		private mCheckboxMenuItem minuto_10 = new mCheckboxMenuItem("10 minutos");
		private mCheckboxMenuItem minuto_30 = new mCheckboxMenuItem("30 minutos");
		private mCheckboxMenuItem hora_1 = new mCheckboxMenuItem("1 hora");
		private mCheckboxMenuItem hora_2 = new mCheckboxMenuItem("2 horas");
		private CheckboxMenuItem setStart = new CheckboxMenuItem("Iniciar sin visibilidad");
		private MenuItem showItem = new MenuItem("Mostrar");
		private PopupMenu DtimeMenu = new PopupMenu("Configurar el lapso de tiempo");
		private static boolean automatic;

		static void quitBarraCarga(boolean b) {
			// //System.out.println("paso pr aca");
			if (b) {
				Lamina_Sur.remove(0);
				Lamina_Sur.setLayout(new GridLayout(4, 1));
				botonEmpezar.setEnabled(true);
				Lamina_Sur.add(lamina1);
				Lamina_Sur.add(lamina2);
				Lamina_Sur.add(lamina3);
				Lamina_Sur.add(lamina4);
				botonEmpezar.setEnabled(true);
				mimarcoN.SinProceso();

			} else {
				botonEmpezar.setEnabled(true);
			}

			barra.setValue(0);
			barra.setString("0%");
			carga = 0;
			cargad = 0;
			en_proceso = false;
			try {
				Lamina_Sur.updateUI();
			} catch (NullPointerException e) {
			}

		}

		static void verBarraCarga() {

			Lamina_Sur.remove(0);
			Lamina_Sur.remove(0);
			Lamina_Sur.remove(0);
			Lamina_Sur.remove(0);
			Lamina_Sur.setLayout(new FlowLayout(FlowLayout.CENTER));
			JPanel laminacarga = new JPanel();
			barra.setPreferredSize(new Dimension(440, 20));
			barra.setStringPainted(true);
			barraN.setStringPainted(true);
			laminacarga.add(barra);
			laminacarga.add(cancelar);
			Lamina_Sur.add(laminacarga);
			en_proceso = true;
			mimarcoN.Trabajando();
			try {
				Lamina_Sur.updateUI();
			} catch (NullPointerException e) {
			}

		}

		static void dalecarga(double porcentaje) {

			ultimo = carga;
			cargad += porcentaje;
			// //System.out.println("ultimo: " + ultimo);

			carga = (int) cargad;
			// System.out.println("carga: " + carga);
			if (carga > barra.getMaximum())
				carga = 100;

			if (carga >= ultimo) {
				barra.setValue(carga);
				barra.setString(String.valueOf(carga + "%"));
				barra.repaint();
				barraN.setValue(carga); // esta es la barra del frame de
										// notificaciones
				barraN.setString(String.valueOf(carga + "%"));
				barraN.repaint();
			}

			if (barra.getMaximum() == carga) {
				barra.setForeground(new Color(0, 230, 20));
				barraN.setForeground(new Color(0, 230, 20));
				try {
					Thread.sleep(850);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				quitBarraCarga(true);
				barra.setForeground(Color.DARK_GRAY);
				carga = 0;
				cargad = 0;

				mimarcoN.Completado();
			}
		}

		public Marco() {
			try {

				// setIconImage(new
				// ImageIcon("src//paquete//icono.png").getImage());
				setIconImage(new ImageIcon(getClass().getResource("/paquete/icono.png")).getImage());

				// setUndecorated(true);
				setTitle("C&P's");
				// Shape forma = new Ellipse2D.Double(33, 2, 444, 404);
				//
				// setShape( forma);
				if (new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "Config.ini")
						.exists()) {
					System.out.println(
							System.getProperty("user.dir") + System.getProperty("file.separator") + "Config.ini");

					P.load(new FileReader(
							System.getProperty("user.dir") + System.getProperty("file.separator") + "Config.ini"));
				} else {
					P.store(new FileWriter(
							System.getProperty("user.dir") + System.getProperty("file.separator") + "Config.ini"),
							null);
					System.out.println("Creando Archivo de configuración");
				}
				mimarcoN = new marcoN();
				barra.setForeground(Color.DARK_GRAY);
				barraN.setForeground(Color.DARK_GRAY);
				CreateTray trayIcon = new CreateTray(
						new ImageIcon(getClass().getResource("/paquete/iconoTray.png")).getImage());
				trayIcon.displayMessage("Iniciado", "Ya estoy listo para realizar las copias :D", MessageType.INFO);
				Font mifont = new Font("Verdana", Font.PLAIN, 18);
				setLayout(new BorderLayout());
				botonEmpezar = new JButton("Empezar");
				botonEmpezar.setFont(mifont);
				botonEmpezar.setPreferredSize(new Dimension(140, 39));
				botonEmpezar.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						botonEmpezar.setEnabled(false);

						y: if (!campoEscogerC.getText().isEmpty()) {
							if (!campoEscogerP.getText().isEmpty()) {
								direcC = new File(campoEscogerC.getText().trim());
								if (direcC.isDirectory()) {
									if (direcC.canRead()) {
										System.out.println(automatic + " esto es");
										copi.mostrarmsj(automatic);

										resultado = copi.comenzar(direcC, misextenciones, 'c');
										listArchivos = copi.getListFile();

									} else {
										botonEmpezar.setEnabled(true);

										JOptionPane.showConfirmDialog(null,
												"El directorio de donde se van a copiar los archivos no puede ser leido.",
												"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
										break y;
									}
								} else {
									botonEmpezar.setEnabled(true);

									JOptionPane.showConfirmDialog(null,
											"El directorio de donde se van a copiar los archivos no es valido.",
											"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
									break y;
								}
								if (resultado) {

									if (listArchivos != 0) {

										direcP = new File(campoEscogerP.getText().trim());
										if (direcP.isDirectory()) {
											if (direcP.canWrite()) {
												copi.setdirecP(direcP);
												copi.run();
											} else {
												botonEmpezar.setEnabled(true);

												JOptionPane.showConfirmDialog(null,
														"El directorio en donde se van a copiar los archivos no puede ser escrito.",
														"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
											}

										} else {
											botonEmpezar.setEnabled(true);

											JOptionPane.showConfirmDialog(null,
													"El directorio en donde se van a copiar los archivos no es valido.",
													"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
										}
									} else
										botonEmpezar.setEnabled(true);

								} else {
									botonEmpezar.setEnabled(true);
									if (copi.dameresultado()) {

										JOptionPane.showConfirmDialog(null,
												copi.dameformatos().length() > 0
														? "No se a encontrada ningún archivo con estos formatos."
														: "No se a encontrada ningún archivo con este formato.",
												"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
									}

								}
							} else {
								botonEmpezar.setEnabled(true);
								JOptionPane.showConfirmDialog(null,
										"Debe escoger un directorio en donde se van a copiar los archivos.", "Error",
										JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
							}

						} else {
							botonEmpezar.setEnabled(true);
							JOptionPane.showConfirmDialog(null,
									"Debe escoger un directorio de donde se van a copiar los archivos.", "Error",
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

						if (arg0.getKeyCode() == 10 && !Campo_Extension.getText().trim().isEmpty()) {
							if (misextenciones.size() >= 50) {
								JOptionPane.showConfirmDialog(null,
										"Usted a superado el limite de extensiones permidos que son 50.", "Error",
										JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
							} else {
								String extension = Campo_Extension.getText();
								k: if (!extension.isEmpty()) {
									Iterator<String> ite = misextenciones.iterator();
									while (ite.hasNext()) {
										if (ite.next().equals(("." + extension))) {
											JOptionPane.showConfirmDialog(null, "Esta extension ya existe",
													"Extension existente", JOptionPane.DEFAULT_OPTION,
													JOptionPane.INFORMATION_MESSAGE);
											break k;
										}
									}

									Random aleatorio = new Random(System.currentTimeMillis());
									int intAletorio = aleatorio.nextInt(999999999);
									// Refrescar datos aleatorios
									aleatorio.setSeed(System.currentTimeMillis());
									// ... o mejor

									String id = extension + Lamina_Central.getComponentCount()
											+ String.valueOf(intAletorio);
									laminaE nuevaLamina = new laminaE(extension, id);
									Lamina_Central.add(nuevaLamina);

									setLaminas.add(nuevaLamina);
									try {
										Lamina_Central.updateUI();
									} catch (NullPointerException e) {
									}

									misextenciones.setFormato(extension);
									P.setProperty(nuevaLamina.getI(), extension);
									try {
										P.store(new FileWriter(System.getProperty("user.dir")
												+ System.getProperty("file.separator") + "Config.ini"), null);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}

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
							P.setProperty("DirectorioC", campoEscogerC.getText());
							try {
								P.store(new FileWriter(System.getProperty("user.dir")
										+ System.getProperty("file.separator") + "Config.ini"), null);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
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
							P.setProperty("DirectorioP", campoEscogerP.getText());
							try {
								P.store(new FileWriter(System.getProperty("user.dir")
										+ System.getProperty("file.separator") + "Config.ini"), null);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

				}

				);

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

				Scroll.setPreferredSize(new Dimension(700, 188 + 5));
				Scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(0), "Extensiones"));
				// tamanoScroll = Scroll.getPreferredSize().width - 10; // los
				// 10
				// son
				// espacios
				// los
				// en
				// los
				// laterales

				Lamina_Central.setLayout(new FlowLayout(FlowLayout.CENTER));
				Lamina_Central.setPreferredSize(new Dimension(0, 38));
				add(Lamina_Sur, BorderLayout.SOUTH);
				add(Scroll, BorderLayout.CENTER);
				cancelar = new JButton("Cancelar");
				cancelar.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						copi.stop();
						mostrar = false;
						mimarcoN.desaparecer();
					}
				});
				pack();
				setResizable(true);
				setLocationByPlatform(true);
				// misextenciones.setFormato("mp3");
				// botonEmpezar.doClick();
				if (P.containsKey("DirectorioP") && P.containsKey("DirectorioC")) {
					if (!P.getProperty("DirectorioC").isEmpty() && !P.getProperty("DirectorioP").isEmpty()) {
						campoEscogerC.setText(P.getProperty("DirectorioC"));
						campoEscogerP.setText(P.getProperty("DirectorioP"));
					}
				} else
					System.out.println("no exite el directorioC");

				if (P.containsKey("Inicio")) {
					if (P.getProperty("Inicio").equals("2"))
						setVisible(true);
					System.out.println("lo oontiene en inicio : " + P.getProperty("Inicio"));

				} else {
					setVisible(true);
				}

				addWindowListener(new WindowAdapter() {
					@Override
					public void windowIconified(WindowEvent arg0) {
						dispose();
					}

					@Override
					public void windowClosing(WindowEvent arg0) {
						copi.stop();
						System.exit(0);
					}
				});

				// ------------------------------------------------------------------------------------------------
				try {
					Object[] obj = P.keySet().toArray();
					Arrays.sort(obj);
					if (obj.length != 0) {
						for (Object e : obj) {
							if (!String.valueOf(e).equalsIgnoreCase("DirectorioC")
									&& !String.valueOf(e).equalsIgnoreCase("DirectorioP")
									&& !String.valueOf(e).equalsIgnoreCase("Inicio")
									&& !String.valueOf(e).equalsIgnoreCase("Tiempo")
									&& !String.valueOf(e).equalsIgnoreCase("Trabajar_sin_molestias")) {
								// System.out.println(misextenciones.size());
								String extension = P.getProperty(String.valueOf(e).trim());
								String id = String.valueOf(e).trim();
								laminaE nuevaLamina = new laminaE(extension, id);
								Lamina_Central.add(nuevaLamina);
								setLaminas.add(nuevaLamina);
								try {
									Lamina_Central.updateUI();
								} catch (NullPointerException e2) {
								}
								misextenciones.setFormato(extension);

							}

						}
					}
				} catch (Exception er) {
					JOptionPane
							.showConfirmDialog(
									null, "Error", "Error al crear los formatos guardados. " + er.getLocalizedMessage()
											+ " " + er.getMessage(),
									JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					er.printStackTrace();

				}

				if (P.containsKey("Trabajar_sin_molestias")) {
					if (P.getProperty("Trabajar_sin_molestias").equals("1"))
						automatic = true;

					System.out.println("lo que Contiene aotometic: " + P.getProperty("Trabajar_sin_molestias"));

				} else {
					automatic = false;
				}

			} catch (Exception e) {
				JOptionPane.showConfirmDialog(null, "Error al instanciar el marco.",
						"Error de instancia: " + e.getMessage(), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				System.exit(0);

			}
		}

		static class tempo extends Thread {
			long tiempo;
			long ini = System.nanoTime();
			long ahora = System.nanoTime();
			long diferencia = ahora - ini;

			public tempo() {
				setPriority(Thread.MIN_PRIORITY);
				setDaemon(false);
			}

			void reset() {
				ini = System.nanoTime();

			}

			public void setTiempo(long newtiempo) {
				this.tiempo = newtiempo * 60000; // 1 minute
			}

			@Override
			public void run() {
				while (true) {
					ini = System.nanoTime();
					ahora = System.nanoTime();
					diferencia = ahora - ini;

					try {
						sleep(tiempo);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (!en_proceso && botonEmpezar.isEnabled() && !campoEscogerP.getText().isEmpty()
							&& !campoEscogerC.getText().isEmpty()) {
						System.out.println("Inicia el proceso automatico");
						botonEmpezar.doClick();
					}
				}
			}
		}

		static class marcoN extends JDialog {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			int Px;
			pon dalepon;

			void mostrar() {
				dalepon = new pon();
				dalepon.setPriority(Thread.NORM_PRIORITY);
				try {
					dalepon.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				dalepon.start();

			}

			void desaparecer() {
				quita dalequita = new quita();
				dalequita.start();
			}

			class pon extends Thread {
				float opacidad = 0.01f;

				public void run() {
					mimarcoN.setOpacity(0.1f);

					mimarcoN.setVisible(true);

					while (!(opacidad >= 1.0f)) {
						mimarcoN.setOpacity(opacidad);
						// System.out.println("aclarando");

						try {
							sleep(4);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						opacidad = opacidad + 0.01f;
					}
					toFront();

					opacidad = 0f;
				}

			}

			class quita extends Thread {
				float opacidad = 1f;

				public void run() {
					while (!(opacidad < 0.0f)) {
						mimarcoN.setOpacity(opacidad);
						try {
							sleep(4);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						opacidad = opacidad - 0.01f;
					}
					opacidad = 0f;
					mimarcoN.dispose();
					if (!en_proceso)
						SinProceso();
				}
			}

			Font fontlabel = new Font("Kartika", Font.BOLD, 14);
			JLabel miestado = new JLabel("Sin proceso aparente");

			JButton minimizar = new JButton("Minimizar");
			JButton detener = new JButton("Detener");
			JPanel laminasur = new JPanel();
			JPanel laminabarraN = new JPanel();
			JPanel laminamsj = new JPanel();

			void Completado() {
				miestado.setText("Completado");
				laminamsj.setBackground(Color.green.darker());
				barraN.setValue(100);
				barraN.setString("100%");
				barraN.setForeground(Color.green);
				completado = true;
				detener.setEnabled(false);
			if (!automatic){
				if (!mimarcoN.isVisible()) {
					
					nicon.notify.core.Notification.show("Completado", "Las copias se han finalizado correctamente",
							nicon.notify.core.Notification.NICON_LIGHT_THEME, true, 15000);
				}
			}
			}

			void SinProceso() {
				mit.reset();
				detener.setEnabled(false);
				barraN.setValue(0);
				barraN.setString("0%");
				barraN.setForeground(Color.DARK_GRAY);
				laminamsj.setBackground(Color.DARK_GRAY);
				miestado.setText("Sin proceso aparente");
				try {
					laminaN.updateUI();
				} catch (Exception e) {
				}

			}

			void Trabajando() {
				detener.setEnabled(true);
				barraN.setValue(0);
				barraN.setString("0%");
				barraN.setForeground(Color.DARK_GRAY);
				miestado.setText("Trabajando en ello...");
				laminamsj.setBackground(Color.YELLOW.darker());
				;
			}

			public marcoN() {

				setFocusable(true);
				setResizable(false);
				setBounds(LADOX - 240, LADOY - 40, 236, 100);
				setUndecorated(true);
				laminamsj.setBackground(Color.DARK_GRAY);
				addWindowListener(new WindowAdapter() {
					@Override
					public void windowActivated(WindowEvent arg0) {
						mostrar = true;
					}

					@Override
					public void windowIconified(WindowEvent e) {
						barraN.setForeground(Color.DARK_GRAY);
						barraN.setValue(0);
						barraN.setString("0%");
						detener.setEnabled(true);
					}

				});
				barraN.setPreferredSize(new Dimension(194, 22));
				Font mifont = new Font("Arial", 0, 11);

				minimizar.setFont(mifont);
				detener.setFont(mifont);
				minimizar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (mostrar) {
							mostrar = false;
							desaparecer();
						}
					}
				});

				detener.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						copi.stop();
						mostrar = false;
						desaparecer();
						detener.setEnabled(false);
					}
				});
				miestado.setFont(fontlabel);
				// JPanel lN = new JPanel();
				// minimizar.setPreferredSize(new Dimension(80, 21));
				// cancelar.setPreferredSize(new Dimension(80, 21));
				laminaN.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
				laminabarraN.add(barraN, BorderLayout.CENTER);
				laminasur.add(detener);
				laminasur.add(minimizar);
				detener.setEnabled(false);
				laminamsj.setPreferredSize(new Dimension(236, 29));
				laminaN.add(laminamsj, BorderLayout.NORTH);
				laminamsj.add(miestado, BorderLayout.CENTER);
				laminaN.add(laminabarraN, BorderLayout.CENTER);
				laminaN.add(laminasur, BorderLayout.SOUTH);
				add(laminaN);
			}
		}

		void CambioTiempo(ItemEvent arg0) {

			if (minuto_10.toString().equals(arg0.getItem())) {
				minuto_30.setState(false);
				hora_1.setState(false);
				hora_2.setState(false);
				tiempo = 10l;

			}

			if (minuto_30.toString().equals(arg0.getItem())) {
				minuto_10.setState(false);
				hora_1.setState(false);
				hora_2.setState(false);
				tiempo = 30l;

			}

			if (hora_1.toString().equals(arg0.getItem())) {
				minuto_30.setState(false);
				minuto_10.setState(false);
				hora_2.setState(false);
				tiempo = 60l;

			}

			if (hora_2.toString().equals(arg0.getItem())) {
				minuto_30.setState(false);
				minuto_10.setState(false);
				hora_1.setState(false);
				tiempo = 120l;
			}

			P.setProperty("Tiempo", String.valueOf(arg0.getItem()));

			if (!hora_1.getState() && !hora_2.getState() && !minuto_10.getState() && !minuto_30.getState()) {
				P.setProperty("Tiempo", "");
				System.out.println("desa");
				tiempo = 0l;
			} else {
				P.setProperty("Tiempo", String.valueOf(arg0.getItem()));
			}

			try {
				P.store(new FileWriter(
						System.getProperty("user.dir") + System.getProperty("file.separator") + "Config.ini"), null);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if (tiempo == 0) {
				mit.suspend();
			} else {
				mit.setTiempo(tiempo);
				mit.resume();
			}
		}

		class mCheckboxMenuItem extends CheckboxMenuItem {

			private static final long serialVersionUID = 1L;
			private String nombre;

			public mCheckboxMenuItem(String g, boolean a) {
				super(g, a);

				nombre = g;
				addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent arg0) {
						CambioTiempo(arg0);

					}

				});
			}

			public mCheckboxMenuItem(String g) {
				super(g);
				nombre = g;
				addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(ItemEvent arg0) {
						CambioTiempo(arg0);

					}
				});
			}

			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return nombre;
			}
		}

		class CreateTray extends TrayIcon {
			public CreateTray(Image e) {
				super(e);
				if (!SystemTray.isSupported()) {
					// System.out.println("SystemTray no es soportado");
					return;
				}

				final PopupMenu popup = new PopupMenu();
				final SystemTray tray = SystemTray.getSystemTray();
				setImageAutoSize(true);

				// CheckboxMenuItemPeer
				// mi_grupo.setSelectedCheckbox(minuto_10);
				// mi_grupo.add(minuto_30);
				// mi_grupo.add(hora_1) ;
				// mi_grupo.add(hora_2);
				// mi_grupo.add(desactivar);
				//

				DtimeMenu.add(minuto_10);
				DtimeMenu.add(minuto_30);
				DtimeMenu.add(hora_1);
				DtimeMenu.add(hora_2);

				MenuItem exitItem = new MenuItem("Salir");
				setStart.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						//
						System.out.println(P.getProperty("Inicio"));
						P.setProperty("Inicio", String.valueOf(arg0.getStateChange()));
						try {
							P.store(new FileWriter(System.getProperty("user.dir") + System.getProperty("file.separator")
									+ "Config.ini"), null);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				CheckboxMenuItem workAItem = new CheckboxMenuItem("Trabajar sin molestias");
				workAItem.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent arg01) {

						P.setProperty("Trabajar_sin_molestias", String.valueOf(arg01.getStateChange()));
						automatic = !automatic;
						try {
							P.store(new FileWriter(System.getProperty("user.dir") + System.getProperty("file.separator")
									+ "Config.ini"), null);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				if (P.containsKey("Inicio")) {
					setStart.setState((P.getProperty("Inicio").equals("1") ? true : false));

					// //System.out.println("lo oontiene:
					// "+P.getProperty("Inicio"));

				}

				if (P.containsKey("Tiempo")) {
					if (P.getProperty("Tiempo").equals("10 minutos")) {
						tiempo = 10l;
						minuto_10.setState(true);
					} else if (P.getProperty("Tiempo").equals("30 minutos")) {
						tiempo = 30l;
						minuto_30.setState(true);
					} else if (P.getProperty("Tiempo").equals("1 hora")) {
						tiempo = 60l;
						hora_1.setState(true);
					} else if (P.getProperty("Tiempo").equals("2 horas")) {
						hora_2.setState(true);
						tiempo = 120l;
					} else {
						tiempo = 90l;
					}
				} else {
					tiempo = 90l;
				}

				System.out.println("lo contiene tiempo " + tiempo);
				System.out.println("cada_: " + tiempo + " minutos");

				mit.setTiempo(tiempo);

				mit.start();
				if (tiempo == 90l)
					mit.suspend();

				showItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						setState(JFrame.NORMAL); // lo trae al frente

						setVisible(true);
					}
				});

				exitItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							P.store(new FileWriter(System.getProperty("user.dir") + System.getProperty("file.separator")
									+ "Config.ini"), null);
						} catch (Exception e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						System.exit(0);
					}
				});
				if (P.containsKey("Trabajar_sin_molestias")) {
					workAItem.setState((P.getProperty("Trabajar_sin_molestias").equals("1") ? true : false));

					// //System.out.println("lo oontiene:
					// "+P.getProperty("Inicio"));

				}
				// Add components to pop-up menu
				popup.add(showItem);
				popup.addSeparator();
				popup.add(setStart);
				popup.addSeparator(); // cmd talvez. no pude.
				popup.add(workAItem);
				popup.add(DtimeMenu);
				popup.addSeparator(); // cmd talvez. no pude.
				popup.add(exitItem);

				addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent arg0) {

						if (arg0.getClickCount() == 1 && arg0.getButton() == 1 && en_proceso && !Marco.this.isVisible()
								|| completado) {
							if (mimarcoN.isVisible()) {
								mimarcoN.toFront();
							}
							mimarcoN.mostrar();
							completado = false;
						}
					}
				});
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setState(JFrame.NORMAL); // lo trae al frente
						setVisible(true); // lo hace visible. este es el orden
											// correcto.
					}
				});

				setPopupMenu(popup);
				try {
					tray.add(this);
				} catch (AWTException e0) {
					System.out.println("TrayIcon could not be added.");
				}
			}
		}

		private class Agregar implements ActionListener {
			@Override

			public void actionPerformed(ActionEvent e) {
				String extension = Campo_Extension.getText();
				k: if (!extension.isEmpty()) {
					Iterator<String> ite = misextenciones.iterator();

					while (ite.hasNext()) {
						if (ite.next().equals(("." + extension))) {
							JOptionPane.showConfirmDialog(null, "Esta extension ya existe.", "Extension existente",
									JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
							break k;
						}
					}

					Random aleatorio = new Random(System.currentTimeMillis());
					int intAletorio = aleatorio.nextInt(999999999);
					// Refrescar datos aleatorios
					aleatorio.setSeed(System.currentTimeMillis());
					// ... o mejor

					String id = extension + Lamina_Central.getComponentCount() + String.valueOf(intAletorio);
					laminaE nuevaLamina = new laminaE(extension, id);
					Lamina_Central.add(nuevaLamina);
					setLaminas.add(nuevaLamina);
					try {
						Lamina_Central.updateUI();
					} catch (Exception e2) {

					}
					misextenciones.setFormato(extension);
				}
			}

		}

		@SuppressWarnings("serial")
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
								// Hashtable<String,String> contenedor=new
								// Hashtable<String,String>();
								// contenedor.put("1", "e");
								P.remove(dameI());

								try {
									P.store(new FileWriter(System.getProperty("user.dir")
											+ System.getProperty("file.separator") + "Config.ini"), null);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								setLaminas.remove(i);
								break romper;
							}
						}
						tamaño = misextenciones.size();

						romper1: for (int i1 = 0; i1 < tamaño; i1++) {

							if (misextenciones.get(i1).equals(("." + extencion))) {
								misextenciones.remove(i1);

								break romper1;
							}
						}
						try {
							Lamina_Central.updateUI();
						} catch (NullPointerException e) {
						}

					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
}