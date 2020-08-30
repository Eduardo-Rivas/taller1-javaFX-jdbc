package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application{
	/*--Definimos éste Atributo para luego tomarlo--//
	/--Como refrencia en los Nodos del Menu Princial--*/
	private static Scene mainScene;
	
	@Override
	public void start(Stage primaryStage) {
	   try {
	      FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
	      ScrollPane scrolPane = loader.load();
	       
	      //--Ajustamos el Menu al ScrolPane--//
	      scrolPane.setFitToHeight(true);
	      scrolPane.setFitToWidth(true);
	      
	      mainScene = new Scene(scrolPane);
	      primaryStage.setScene(mainScene);
	      primaryStage.setTitle("Sistema de Vendedores");
	      primaryStage.show();
	   } 
	   catch (IOException e) {
	      e.printStackTrace();
	   }
	}
	
	//--Método para tomar mainScene--//
	public static Scene getMainScene() {
		return mainScene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
