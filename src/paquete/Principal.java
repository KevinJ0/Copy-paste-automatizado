package paquete;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.peer.WindowPeer;
import java.io.*;
import java.util.*;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import nicon.notify.core.Notification;
import javax.swing.ImageIcon;
import net.sf.jcarrierpigeon.Notification;
import net.sf.jcarrierpigeon.NotificationQueue;
import net.sf.jcarrierpigeon.WindowPosition;

import javax.swing.*;

public class Principal {

	public static void main(String[] args) {
		Marco M = new Marco();
		M.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		 try{
		     UIManager.setLookAndFeel(new NimbusLookAndFeel());
		           Notification.showConfirm("Notificacion con NiconNotify","Hola amigos esta es mi notificacion personalizada", Notification,true, 10000);
		 
		//El metodo showConfirm() puede variar con esto me refiero a que puedes solo mostrar el mensaje o bien puedes mandar mensaje, el tema que prefieras , si se reproduce un sonido o no y tambien por cuanto tiempo se mostrara, este ultimo es el que muestro en el ejemplo.
		 
		        }catch(Exception ex){
		            System.err.println("error: "+ex.getCause());
		        }	}

	static class Marco extends JFrame {
		private static marcoN mimarcoN; // cuadro de carga en proceso
		private static marcoNC mimarcoNC = new marcoNC(); // cuadro de carga
															// completado

		// private static Notification notiC = new Notification(mimarcoNC,
		// WindowPosition.BOTTOMRIGHT, 0, 0, 1999);
		private static Notification noti;
		//private static NotificationQueue mostrarnoti = new NotificationQueue();
		private static JPanel lamina4 = new JPanel();
		private static JPanel lamina3 = new JPanel();
		private static JPanel lamina2 = new JPanel();
		private static JPanel lamina1 = new JPanel();
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
		private static JButton botonEmpezar;
		private JTextField campoEscogerC = new JTextField(35);
		private JTextField campoEscogerP = new JTextField(35);
		private JLabel Leibol_agregar;
		private JTextField Campo_Extension;
		private JPanel Lamina_Central = new JPanel();
		private static JPanel Lamina_Sur = new JPanel(new GridLayout(4, 1));
		private Listformato misextenciones = new Listformato();
		private static Copiar copi = new Copiar();
		private int listArchivos = 0;
		private ArrayList<laminaE> setLaminas = new ArrayList<laminaE>();
		private boolean resultado = false;
		private static JProgressBar barra = new JProgressBar(0, 100);
		private static JProgressBar barraN = new JProgressBar(0, 100);
		private static JPanel laminaN = new JPanel(new BorderLayout());
		private static int ultimo;
		private static int carga;
		private static double cargad = 0.0;

		static void quitBarraCarga(boolean b) {
			// System.out.println("paso pr aca");
			if (b) {
				// laminaN.remove(0);
				Lamina_Sur.remove(0);
				Lamina_Sur.setLayout(new GridLayout(4, 1));
				botonEmpezar.setEnabled(true);
				Lamina_Sur.add(lamina1);
				Lamina_Sur.add(lamina2);
				Lamina_Sur.add(lamina3);
				Lamina_Sur.add(lamina4);
				botonEmpezar.setEnabled(true);
				Notification notiC = new Notification(mimarcoNC, WindowPosition.BOTTOMRIGHT, 0, 0, 2000);
				notiC.animate();
			} else {
				botonEmpezar.setEnabled(true);
			}
			Lamina_Sur.updateUI();
		}

		static void verBarraCarga() {
			// laminaN.remove(0);

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
			Lamina_Sur.add(laminacarga);
			Lamina_Sur.updateUI();

		}

		static void dalecarga(double porcentaje) {
			ultimo = carga;
			cargad += porcentaje;
			// System.out.println("cuenta");
			// System.out.println("ultimo: " + ultimo);

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

				barra.setForeground(Color.LIGHT_GRAY);
				barraN.setForeground(Color.LIGHT_GRAY);

				carga = 0;
				cargad = 0;
			}
		}

		public Marco() {
			try {
				mimarcoN = new marcoN();

				noti = new Notification(mimarcoN, WindowPosition.BOTTOMRIGHT, 0, 0, 999999999);
				barra.setForeground(Color.LIGHT_GRAY);
				barraN.setForeground(Color.LIGHT_GRAY);
				ClassTray MiTray = new ClassTray();
				// setBounds(400, 100, 700, 400);

				setLayout(new BorderLayout());
				botonEmpezar = new JButton("Empezar");
				botonEmpezar.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						botonEmpezar.setEnabled(false);
						campoEscogerC.setText("C://Users//Ranses Y K//Documents");
						campoEscogerP.setText("C://Users//Ranses Y K//Music");

						y: if (!campoEscogerC.getText().isEmpty()) {
							if (!campoEscogerP.getText().isEmpty()) {
								direcC = new File(campoEscogerC.getText().trim());
								if (direcC.isDirectory()) {
									if (direcC.canRead()) {

										resultado = copi.comenzar(direcC, misextenciones, 'c');
										listArchivos = copi.getListFile();

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

									if (listArchivos != 0) {

										direcP = new File(campoEscogerP.getText().trim());
										if (direcP.isDirectory()) {
											if (direcP.canWrite()) {
												// System.out.println("La
												// cantidad de : " +
												// listArchivos);
												copi.setdirecP(direcP);
												copi.run();

												// if(!mimarcoN.isVisible())
												// noti.animate();

												//mostrarnoti.add(noti);

											} else {
												JOptionPane.showConfirmDialog(null,
														"El directorio en donde se van a copiar los archivos no puede ser escrito",
														"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
											}

										} else {
											JOptionPane.showConfirmDialog(null,
													"El directorio en donde se van a copiar los archivos no es valido",
													"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
										}
									}
								} else {
									JOptionPane.showConfirmDialog(null,
											"No se a encontrada ningún archivo con los formatos establecidos",
											"Informe", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

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
										JOptionPane.showConfirmDialog(null, "Esta extension ya existe",
												"Extension existente", JOptionPane.DEFAULT_OPTION,
												JOptionPane.INFORMATION_MESSAGE);
										break a;
									}
								}
								String id = "extension" + String.valueOf((int) (Math.random() * 1000000000));
								laminaE nuevaLamina = new laminaE(extension, id);
								Lamina_Central.add(nuevaLamina);
								setLaminas.add(nuevaLamina);
								largo = largo + nuevaLamina.getPreferredSize().width;

								if (largo >= 680) {
									// System.out.println("Se cumple la
									// condicion
									// con: "
									// + largo);
									cont++;
									if (cont > 4) {
										Lamina_Central.setPreferredSize(
												new Dimension(680, (nuevaLamina.getPreferredSize().height
														+ Lamina_Central.getHeight() + 5))); // es
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

				Scroll.setPreferredSize(new Dimension(700, 220));
				Scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(0), "Extensiones"));
				tamanoScroll = Scroll.getPreferredSize().width;
				Lamina_Central.setLayout(new FlowLayout(FlowLayout.LEFT));
				Lamina_Central.setPreferredSize(new Dimension(680, 36)); // es
																			// la
																			// altura
																			// de
																			// los
																			// paneles
																			// (laminasE),
																			// en
																			// su
																			// defecto
																			// será
																			// cambiado
				add(Lamina_Sur, BorderLayout.SOUTH);
				add(Scroll, BorderLayout.CENTER);
				pack();
				setResizable(false);
				setLocationByPlatform(true);
				misextenciones.setFormato("mp3");
				// botonEmpezar.doClick();

				setVisible(true);
			} catch (Exception e) {
				JOptionPane.showConfirmDialog(null, "Error al instanciar el marco",
						"Error de instancia: " + e.getMessage(), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				System.exit(0);

			}
		}
		static class marcoNC extends JFrame {

			public marcoNC() {
				
				addWindowListener(new WindowAdapter() {
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowIconified(WindowEvent e) {

					}

					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowDeactivated(WindowEvent e) {
						// dispose();
						// noti.end();

					}

					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowActivated(WindowEvent e) {
					}
				});
				setBounds(0, 0, 190, 60);
				this.setUndecorated(true);
				
			}

		}

		static class marcoN extends JFrame {

			public marcoN() {
				addWindowListener(new WindowAdapter() {

					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowIconified(WindowEvent e) {

					}

					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowDeactivated(WindowEvent e) {
						// dispose();
						// noti.end();

					}

					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowActivated(WindowEvent e) {
					}
				});
				setBounds(0, 0, 190, 60);
				this.setUndecorated(true);
				// barraN.setBounds(0, 0, 199, 25);
				barraN.setPreferredSize(new Dimension(164, 16));

				Font mifont = new Font("Arial", 0, 11);
				JButton minimizar = new JButton("Minimizar");
				minimizar.setFont(mifont);
				JButton cancelar = new JButton("Cancelar");
				cancelar.setFont(mifont);
				minimizar.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				minimizar.setPreferredSize(new Dimension(80, 21));
				cancelar.setPreferredSize(new Dimension(80, 21));
				laminaN.setBorder(BorderFactory.createEtchedBorder(1));
				JPanel laminasur = new JPanel();
				JPanel laminabarraN = new JPanel();
				laminabarraN.add(barraN);
				laminasur.add(cancelar);
				laminasur.add(minimizar);
				laminaN.add(laminabarraN, BorderLayout.CENTER);
				laminaN.add(laminasur, BorderLayout.SOUTH);
				add(laminaN);
			}

		}

		class ClassTray {
			public ClassTray() {

				if (!SystemTray.isSupported()) {
					System.out.println("SystemTray no es soportado");
					return;
				}

				final PopupMenu popup = new PopupMenu();

				final TrayIcon trayIcon = new TrayIcon(new ImageIcon("src//paquete//exit.png").getImage());
				final SystemTray tray = SystemTray.getSystemTray();

				// Create a pop-up menu components
				CheckboxMenuItem setStart = new CheckboxMenuItem("Añadir al inicio del sistema");
				MenuItem showItem = new MenuItem("Mostrar");
				MenuItem exitItem = new MenuItem("Salir");

				showItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(true);
						setState(JFrame.NORMAL); // lo trae al frente
					}
				});
				exitItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				// Add components to pop-up menu
				popup.add(showItem);
				popup.addSeparator();
				popup.add(setStart);
				popup.addSeparator();
				popup.add(exitItem);
				trayIcon.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(true);
						setState(JFrame.NORMAL); // lo trae al frente

					}
				});
				trayIcon.setPopupMenu(popup);

				try {
					tray.add(trayIcon);
				} catch (AWTException e) {
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
}