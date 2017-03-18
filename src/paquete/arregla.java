package paquete;
import java.awt.*;

import java.applet.*;

public class arregla {
	 public static void main(String[] args){
			
			cuadros cu = new cuadros();
			
		}
}


class cuadros extends Applet{

TextArea caja=new TextArea("");

Button abre=new Button("Abrir");

Frame c1=new Frame();

FileDialog cuadro=new

FileDialog(c1,"Abrir",FileDialog.LOAD);

public void init(){

setLayout(null);

add(caja);

add(abre);

caja.reshape(10,10,200,100);

abre.reshape(10,110,60,40);

}

public boolean action(Event evt,Object obj){

String fichero=null;

String directorio=null;

 if(evt.target instanceof Button)

{

if(obj.equals("Abrir"))

{

        cuadro.show();

 directorio=cuadro.getDirectory();

 fichero=cuadro.getFile();

        if(fichero!=null)

caja.setText(directorio+fichero);

}

return true;

}

return false;

}       

} //cierra la clase
