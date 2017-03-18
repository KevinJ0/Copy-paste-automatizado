package paquete;

import java.util.ArrayList;

public class Listformato extends ArrayList<String> {

	void setFormato(String formato) {
		add("." + formato.trim());

	}
	
}
