package gui.util;


import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
 
public class Utils {

	//--Método Statico para tomar la Pantalla Padre--//
	public static Stage actualStage(ActionEvent evento) {
		return (Stage) ((Node) evento.getSource()).getScene().getWindow();
	}
	
	//--Método para Convertir a Entero--//
	public static Integer convertirEntero(String str) {
		try {
			return Integer.parseInt(str);
		}
		catch(NumberFormatException e) {
			return null;
		}
				
	} 
}
