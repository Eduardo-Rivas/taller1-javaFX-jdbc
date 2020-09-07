package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Departamento;
import model.services.DepartamentoService;

public class DepartamentoListController implements Initializable{
	private DepartamentoService service; 
	
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
 
	private ObservableList<Departamento> obsList;
	
	public void setDepartamentoService(DepartamentoService service) {
		this.service = service;
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

	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Servicio está Nulo...");
		}
		List<Departamento> lista = service.findAll();
		obsList = FXCollections.observableArrayList(lista);
		tableViewDep.setItems(obsList); 
	}

}
