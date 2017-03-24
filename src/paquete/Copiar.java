package paquete;

import java.io.*;
import java.util.*;
import javax.swing.*;

import paquete.Principal.Marco;
 
public class Copiar {
	private double tmp;
 	private static JFileChooser chooser = new JFileChooser();
	private static File ruta;
	private File direccionpegar;
	private ArrayList<File> listarchivosC = new ArrayList<File>();
	private ArrayList<File> listarchivosP = new ArrayList<File>();
	private Listformato listformat = new Listformato();
	private File temp[];
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
			if (repetir == 0) {
				resultado = JOptionPane.showConfirmDialog(null, "¿Desea copiar todos los archivos de este directorio?",
						"No hay formatos para filtrar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (resultado == JOptionPane.OK_OPTION) {
					temp = directorio.listFiles();
				} else {
					break rompe;
				}
			} else {
				for (int i = 0; i < repetir; i++)
					temp = directorio.listFiles(new Filtro(listado_format.get(i)));
			}
			for (File e : temp) {
				if (e.exists()) {
					listarchivosTEMP.add(e);
				}

			}
			// System.out.println("tamaño de lista temporal de archivos: " +
			// listarchivosTEMP.size());
		}

		if (listado_format.size() > 0) {
			if (CoP == 'c') { // vamos a pasar dependiendo de quien sea la
								// llamada
				listarchivosC = listarchivosTEMP;
			} else {
				listarchivosP = listarchivosTEMP;
			}
		}

		if (listarchivosC.size() > 0)
			comienza = true;

		return comienza;
	}

	int getListFile() {
		return listarchivosC.size();
	}

	void setdirecP(File direccionpegar) {
		this.direccionpegar = direccionpegar;
	}

	public void run() {

		Hilos H1 = new Hilos();
		H1.start();
	}

	class Hilos extends Thread {
		public void run() {
			boolean resultado1 = comenzar(direccionpegar, listformat, 'p');
			/*
			 * vamos a comparar para ver si hay ya eso archivos están el
			 * directorio donde se van a pegar,si lo está lo elimino
			 */

			if (resultado1) {
				for (int i = 0; i < listarchivosC.size(); i++) {
					for (int i2 = 0; i2 < listarchivosP.size(); i2++) {

						if (listarchivosC.get(i).getName().equals(listarchivosP.get(i2).getName())) {

							listarchivosC.remove(i);
							// System.out.println("son iguales");
						} else {
							// System.out.println("No son iguales");
						}
					}
				}
			}

			if (listarchivosC.size() != 0) {
				System.out.println("tamaño de archivos en donde se copiará: " + listarchivosC.size());
				System.out.println("tamaño de archivos en donde se pegará: " + listarchivosP.size());
				int ok;
				Iterator<File> iterador = listarchivosC.iterator();
				BufferedInputStream entrada;
				BufferedOutputStream salida;
				System.out.println("la cantidad de archivos que se van a copiar son: " + listarchivosC.size());
				System.out.println("los archivos que se van a copiar son: ");
				Marco.verBarraCarga(); // ahora le paso la cantidad de
											// archivos
											// que sí se van a copiar
				double cargadouble = (double) 100 / listarchivosC.size();

				tmp = (double) cargadouble * getListFile();

				System.out.println(cargadouble);
				while (iterador.hasNext()) {
					File archiv = iterador.next();
					try {
						entrada = new BufferedInputStream(new FileInputStream(archiv));
						salida = new BufferedOutputStream(
								new FileOutputStream(direccionpegar + "//" + archiv.getName()));
						// System.out.println(archiv.getName());

						while ((ok = entrada.read()) != -1) {
							salida.write(ok);
						}
						try {
							if (entrada != null) {
								entrada.close();
							}
							if (salida != null) {
								salida.close();
							}

							if (!iterador.hasNext()) {
								cargadouble = 100;
								System.out.println("si se cumple el ultimo: "+cargadouble);
							}
							Marco.dalecarga(cargadouble);
						} catch (IOException ex) {
							JOptionPane.showConfirmDialog(null,
									"Ha ocurrido un error al intentar cerrar los los bufferes de entrada y salida: "
											+ ex.getMessage(),
									"No hay formatos para filtrar", JOptionPane.OK_CANCEL_OPTION,
									JOptionPane.ERROR_MESSAGE);
						}

					} catch (Exception e2) {
						JOptionPane.showConfirmDialog(null,
								"Ha ocurrido un error en el proceso de pegado de los archivos: " + e2.getMessage(),
								"No hay formatos para filtrar", JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.ERROR_MESSAGE);
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