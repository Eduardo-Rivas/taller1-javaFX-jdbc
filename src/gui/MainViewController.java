package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {

	//--Definimos los Atributos del View--//
	@FXML
	private MenuItem menuItemVen;
	
	@FXML
	private MenuItem menuIteDep;
	
	@FXML
	private MenuItem menuItemAbo;
	
	@FXML
	public void onmenuItemVenAction() {
		System.out.println("Opción Cadastro de Vendedor...");
	}
	
	@FXML
	public void onmenuIteDepAction() {
		System.out.println("Opción Cadastro de Departamentos...");
	} 

	@FXML
	public void onmenuIteAboAction() {
		System.out.println("Opción de Ayuda...");
	}

	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}

	
}
