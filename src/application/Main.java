package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application{
	@Override
	public void start(Stage primaryStage) {
	   try {
	      FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
	      ScrollPane scrolPane = loader.load();
	      
	      //--Ajustamos el Menu al ScrolPane--//
	      scrolPane.setFitToHeight(true);
	      scrolPane.setFitToWidth(true);
	      
	      Scene mainScene = new Scene(scrolPane);
	      primaryStage.setScene(mainScene);
	      primaryStage.setTitle("Sistema de Vendedores");
	      primaryStage.show();
	   } 
	   catch (IOException e) {
	      e.printStackTrace();
	   }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
