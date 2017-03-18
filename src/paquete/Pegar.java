package paquete;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class Pegar {

	private int byteread = 0;
	private InputStream fuente;
	private File directorio;
	private ArrayList<BufferedInputStream> bufferdentrada;
	private ArrayList<File> archivos_entrada;
	private File direccionpegar;

	public Pegar(ArrayList<BufferedInputStream> bufferdentrada, ArrayList<File> archivos_entrada, File direccionpegar) {
		this.direccionpegar = direccionpegar;
		this.bufferdentrada = bufferdentrada;
		this.archivos_entrada = archivos_entrada;

	}

	void comenzar() {
		int i = 0;
		directorio = direccionpegar;
		Iterator<BufferedInputStream> iteradorP = bufferdentrada.iterator();
		Iterator<File> iteradorE = archivos_entrada.iterator();

		while (iteradorE.hasNext()) {
			File archiv = new File(iteradorE.next().getAbsolutePath());
			try (BufferedOutputStream salida = new BufferedOutputStream(new FileOutputStream(archiv))) { // de
																											// esta
																											// manera
																											// el
																											// try
																											// cierra
																											// la
																											// coneccion
																											// de
																											// salida
																											// automaticamente
				
				System.out.println(bufferdentrada.get(i).available() + " bytes");
				i--;
				
				fuente = iteradorP.next();
				// System.out.println(archiv.getName());
				// System.out.println("Tamaño: " + fuente.available() + "
				// bytes");
				// System.out.println("Tamaño: " +
				// mientras los byte de leectura no sean -1
				while (byteread != -1) {
					byteread = fuente.read(); // leo y paso datos byte
					salida.write(byteread); // escribo datos byte
				}
				byteread = 0; // limpio la variable para la siguiente lectura y
								// escritura
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(null,
						"Ha ocurrido un error en el proceso de escritura de los datos " + e.getMessage(), "Error",
						JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				// e.printStackTrace();
			}
			try {
				System.out.println(" cierra");
				fuente.close();
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}

		/*
		 * try { fuente.close(); } catch (IOException e1) {
		 * JOptionPane.showConfirmDialog(null,
		 * "Error al cerrar el objeto BufferedInputStream (fuente)", "Error",
		 * JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE); }
		 */
	}

	File dameDirectorio() {
		return directorio;
	}
}
