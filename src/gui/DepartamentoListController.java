package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Departamento;

public class DepartamentoListController implements Initializable{

	@FXML
	private TableView<Departamento> tableViewDep;
	
	@FXML
	private TableColumn<Departamento, Integer> tableColId;
	
	@FXML
	private TableColumn<Departamento, String>  tableColNombre;
	
	@FXML
	private Button btnIncluir;
	
	@FXML
	public void onbtnIncluirAction() {
		System.out.println("Pulsó Botón Incluir...");
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//--Método para Inicializar el TableView--//
		initializeCols();
		
	}

	//--Método Interno para Iniciaizar Las Columnas del TableView-
	private void initializeCols() {
		//--Configuramos las Columnas del TableView--//
		tableColId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
		
		//--Redimencionamos el TableView a la Pantalle--//
		//--Hacemos un DawonCasting a Stage--//
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDep.prefHeightProperty().bind(stage.heightProperty());
	}

}
