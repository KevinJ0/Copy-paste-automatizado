package paquete;

import java.io.*;
import java.util.*;
import javax.swing.*;

class Copiar {
	private FileInputStream entrada;
	private static JFileChooser chooser = new JFileChooser();
	private static File ruta;
	private ArrayList<File> listarchivos = new ArrayList<File>();
	private ArrayList<String> listformat = new ArrayList<String>();
	private ArrayList<BufferedInputStream> Bufferdentrada;
	private File directorio;
	private File temp[];
	private int resultado = 2;

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

	boolean comenzar(File directorio, Listformato listado_format) {
		boolean comienza = false;
		rompe: if (directorio != null && directorio.canRead()) {
			// sí se puede
			// leer es
			// porque
			// existe
			int repetir = listado_format.size();
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
					listarchivos.add(e);
				}

			}

			Bufferdentrada = new ArrayList<BufferedInputStream>();
			Iterator<File> iteradorFile = listarchivos.iterator();
		//	System.out.println(listarchivos.size() + " Cantidad de archivos");

			rompe1: while (iteradorFile.hasNext()) {
				try {
					entrada = new FileInputStream(iteradorFile.next());
					
					System.out.println("tamaño de lista de archivos: "+listarchivos.size());
					System.out.println("tamaño de la entrada actual: "+entrada.available());
 					Bufferdentrada.add(new BufferedInputStream(entrada)); // new FileInputStream(iteradorFile.next()))
 					try {
 						entrada.close();
 					} catch (IOException e1) {
 						comienza = false;
 						JOptionPane.showConfirmDialog(null,
 								"Ha ocurrido un error al intentar cerrar la conección del FileInputStream (entrada)", "Error",
 								JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
 					}
				} catch (IOException e) {
					comienza = false;
					JOptionPane.showConfirmDialog(null,
							"Error en intentar almacenar el archivo en el BufferedInputStream (BufferedInputStream)",
							"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					break rompe1;
				}

			}
	
			comienza = true;

		} else {
			JOptionPane.showConfirmDialog(null, "Hubo un problema con el directorio de copia", "Error",
					JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		this.directorio = directorio;
		
		return comienza;
	}

	ArrayList<File> getListFile() {

		return listarchivos;
	}

	File dameDirectorio() {
		return this.directorio;
	}

	ArrayList<BufferedInputStream> getBIS() {

		return Bufferdentrada;

	}
}
