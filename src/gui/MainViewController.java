package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

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
		loadView("/gui/DepartamentList.fxml");
	} 

	@FXML
	public void onmenuIteAboAction() {
		loadView("/gui/About.fxml");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}

	//--Método Presentción de Pantallas de las Opciones--//
	private synchronized void loadView(String ruta) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
			
			//--Vbox de About.fxml--//
			VBox newVbox = loader.load();
			 
			//--Tomamos la referncia de mainScene del Menu--//
			//--Fué Hecha en la Clase Main, método getMainScene()--//
			Scene mainScene = Main.getMainScene();
			
			/*--Tomamos una Refrencia del Vbox del Menu--//
			/--Hacemos un Casting del Scrolpane del Menu--//
			/--Hacemos un getContent para refrenciar todo el ScrolPane del Menu--//
			/--Hacemos un Casting del Vbox del Menu--//
			/--Ya podemos cargar el contenido de About.fxml--//
			/--Dentro de MainView.fxml dentro de la Jerarquia de Nodos--*/
			VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			//--Tenemos que guardar una referencia para el Menu--//
			//--Estoy guradando los Hijos del Menu que estan en el VBox Principal--//
			Node mainMenu = mainVbox.getChildren().get(0);
			
			//--Podemos Limpiar los Hijos del Menu--//
			//--Para que puedan Aparecer los Hijos del VBox de About--//
			mainVbox.getChildren().clear();
			
			//--Hacemos que Aparesca el Menu--//
			//--Ya que el Menu siempre debe estar--//
			mainVbox.getChildren().add(mainMenu);
			
			//--Hacemos que Apareser los Hijo de VBox del About--//
			mainVbox.getChildren().addAll(newVbox.getChildren());
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Load View About", e.getMessage(), AlertType.ERROR);
		}
	}
	
}
