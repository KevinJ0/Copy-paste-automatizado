package paquete;

import java.io.*;
import java.util.*;
import javax.swing.*;

import paquete.Principal.Marco;

public class Copiar {

	private String formatosNoexistentes = "";
	private double tmp;
	private boolean comienza; // para saber si el boton empezar debe correr el
								// error de los formatos o no
	private static JFileChooser chooser = new JFileChooser();
	private static File ruta;
	private File direccionpegar;
	private ArrayList<File> listarchivosC = new ArrayList<File>();
	private ArrayList<File> listarchivosP = new ArrayList<File>();
	private Listformato listformat = new Listformato();
	private File temp[];
	private boolean pausa;
	private Hilos H1;
	private boolean detener;
	private boolean all = false;
	private int resultado = 2;
	// static volatile Hilos H1;

	// metodo para seleccionar un directorio

	// @Si devuelve null es porque no es un directorio valido

	static File selecDirectorio() {
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showDialog(null, "Seleccionar") == JFileChooser.APPROVE_OPTION) {
			ruta = chooser.getSelectedFile();
			/*
			 * if (ruta.isDirectory() && ruta.exists()) { //
			 * System.out.print(ruta); } else { return null; } } else { return
			 * null; }
			 */
		}
		return ruta;
	}

	static File dameRuta() {
		return ruta;
	}

	class Filtro implements FilenameFilter {
		String extension;

		Filtro(String extension) {
			this.extension = extension;
		}

		public boolean accept(File dir, String name) {
			return name.endsWith(extension);
		}
	}

	boolean comenzar(File directorio, Listformato listado_format, char CoP) {
		boolean comienza = false;
		ArrayList<File> listarchivosTEMP = new ArrayList<File>();
		rompe: if (directorio != null && directorio.canRead()) {
			// sí se puede
			// leer es
			// porque
			// existe
			int repetir = listado_format.size();
			listformat = listado_format;
			if (repetir == 0 && CoP == 'c') {
				resultado = JOptionPane.showConfirmDialog(null, "¿Desea copiar todos los archivos de este directorio?",
						"No hay formatos para filtrar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (resultado == JOptionPane.OK_OPTION) {
				} else {
					comienza = false;
					break rompe;
				}
			}

			temp = directorio.listFiles();
			all = true;
			if (CoP == 'p') {
				// System.out.println(directorio);
			}

			for (int i = 0; i < repetir; i++) {
				temp = directorio.listFiles(new Filtro(listado_format.get(i)));


				if (CoP == 'c') {
					int npeso = 67;
					
					if (temp.length == 0) {
					
						formatosNoexistentes = formatosNoexistentes + " [" +listado_format.get(i).substring(1).trim()
								+ "] ";

						//if (formatosNoexistentes.length() > npeso) {
						//	formatosNoexistentes = formatosNoexistentes + "\n";
					//		npeso = formatosNoexistentes.length() + 67;
						//}

					}
				}

				if (temp.length != 0) {

					for (File e : temp) {
						e.setReadable(true);
						// if (CoP == 'p')
						// System.out.println(" k: ");

						if (e.exists() && !e.isDirectory() && !e.isHidden()) {
							// if (CoP == 'p')
							// System.out.println(" p: " + e.getAbsolutePath());
							// System.out.println(" p: ");

							listarchivosTEMP.add(e);
						}

					}
				}
				all = false;
			}

			// --------------------------------------------------------------------------------------------------------------------
if (CoP == 'c'){
			if (!formatosNoexistentes.trim().isEmpty()) {
				System.out.println("paso por aca");
				nicon.notify.core.Notification.show("Informe",
						"No se pudo encontrar los archivos con formato: " + "\n" + dameformatos(),
						nicon.notify.core.Notification.NICON_LIGHT_THEME, nicon.notify.core.Notification.INFO_ICON,
						true, 15000);
				formatosNoexistentes = "";

			}
			// --------------------------------------------------------------------------------------------------------------------
}
			if (all) {

				for (File e : temp) {
					e.setReadable(true);
					// if (CoP == 'p')
					// System.out.println(" k: ");

					if (e.exists() && !e.isDirectory() && !e.isHidden()) {
						// if (CoP == 'p')
						// System.out.println(" p: " + e.getAbsolutePath());
						// System.out.println(" p: ");

						listarchivosTEMP.add(e);
					}

				}
			}

			comienza = true;

		}

		if (CoP == 'c') { // vamos a pasar dependiendo de quien sea la
							// llamada
			listarchivosC = listarchivosTEMP;

			System.out.println("paso por c: " + listarchivosTEMP.size());
		} else {

			listarchivosP = listarchivosTEMP;
			System.out.println("paso por p: " + listarchivosTEMP.size());
			all = false;
		}

		if (listarchivosC.size() > 0)
			comienza = true;

		return comienza;
	}

	boolean dameresultado() {
		return comienza;
	}

	int getListFile() {
		return listarchivosC.size();
	}

	void setdirecP(File direccionpegar) {
		this.direccionpegar = direccionpegar;
	}

	String dameformatos() {
		return formatosNoexistentes;
	}

	public void run() {
		pausa = false;
		detener = false;

		H1 = new Hilos();
		H1.setPriority(Thread.MIN_PRIORITY);
		H1.start();
	}

	void stop() {
		detener = true;
		// try {
		// Thread.currentThread().join();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	void pausa() {
		pausa = !pausa;
	}

	class Hilos extends Thread {
		public void run() {
			ArrayList<File> cantR = new ArrayList<File>();
			boolean resultado1 = comenzar(direccionpegar, listformat, 'p'); // este
																			// es
																			// un
																			// comenzar
																			// pero
																			// para
																			// el
																			// directorio
																			// de
																			// pegado
			/*
			 * vamos a comparar para ver si hay ya eso archivos están el
			 * directorio donde se van a pegar,si lo está lo elimino
			 */

			if (resultado1) {
				String eo;
				File eo2 = null;
				Iterator<File> i1 = listarchivosC.iterator();

				// System.out.println("lista dearchivos D C : " +
				// listarchivosC.size());
				// System.out.println("lista dearchivos D P : " +
				// listarchivosP.size());
				while (i1.hasNext()) {

					// System.out.println("lista dearchivos D P : " +
					// listarchivosP.size());
					Iterator<File> i2 = listarchivosP.iterator();
					// if (removio)
					eo2 = i1.next();

					while (i2.hasNext()) {

						eo = i2.next().getName();
						// System.out.println("es :" + eo2);

						if (eo2.getName().equalsIgnoreCase(eo)) {
							cantR.add(eo2);
							// listarchivosC.remove(eo2);
							// System.out.println(" no removio: " +eo2+"
							// \n"+eo);
						}
					}

				}

				for (int ieo = 0; ieo < cantR.size(); ieo++) {
					listarchivosC.remove(cantR.get(ieo));
					// System.out.println("see eliminó: " +
					// cantR.get(ieo).getName());

				}

			}

			if (listarchivosC.size() != 0) {
				// System.out.println("tamaño de archivos en donde se copiará: "
				// + listarchivosC.size());
				// System.out.println("tamaño de archivos en donde se pegará: "
				// + listarchivosP.size());
				int ok;
				Iterator<File> iterador = listarchivosC.iterator();
				BufferedInputStream entrada = null;
				BufferedOutputStream salida = null;
				// System.out.println("la cantidad de archivos que se van a
				// copiar son: " + listarchivosC.size());
				// System.out.println("los archivos que se van a copiar son: ");
				Marco.verBarraCarga(); // ahora le paso la cantidad de
										// archivos
										// que sí se van a copiar
				double cargadouble = (double) 100 / listarchivosC.size();

				tmp = (double) cargadouble * getListFile();
				boolean salta = false;
				// System.out.println(cargadouble);
				while (iterador.hasNext() && !detener) {

					File archiv = iterador.next();
					try {
						// System.out.println(archiv.getName());
						try {
							entrada = new BufferedInputStream(new FileInputStream(archiv));
							salida = new BufferedOutputStream(
									new FileOutputStream(direccionpegar + "//" + archiv.getName()));

						} catch (Exception e3) {
							// System.out.println("se saltó con: " +
							// archiv.getName());

							salta = true;
						}
						if (!salta) {

							while ((ok = entrada.read()) != -1) {
								salida.write(ok);
								if (detener) {
									// System.out.println("se detuvo la copia");
									break;
								}
							}
						} else
							salta = false;

						try {
							if (entrada != null) {
								entrada.close();
							}
							if (salida != null) {
								salida.close();
							}
							if (!iterador.hasNext()) {
								cargadouble = 100;
								// System.out.println("si se cumple el ultimo: "
								// + cargadouble);
							}
							Marco.dalecarga(cargadouble);
						} catch (IOException ex) {
							JOptionPane.showConfirmDialog(null,
									"Ha ocurrido un error al intentar cerrar los los bufferes de entrada y salida: "
											+ ex.getMessage() + ".",
									"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						}

					} catch (Exception e2) {
						JOptionPane.showConfirmDialog(
								null, "Ha ocurrido un error en el proceso de pegado de los archivos... \n"
										+ e2.getMessage() + ".",
								"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						e2.printStackTrace();
					}

					if (detener) {
						listarchivosC.clear();
						listarchivosP.clear();
						try {
							entrada.close();

						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							salida.close();
							java.nio.file.Files.delete(new File(direccionpegar + "//" + archiv.getName()).toPath());
							// borrar.deleteOnExit();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						System.out.println("Se detuvo");
						Marco.quitBarraCarga(true);
					}
				}

			} else {

				Marco.quitBarraCarga(false); // no es que la voy a quitar sinó
												// que me habilite el boton
												// empezar again
			}

		}

	}
}