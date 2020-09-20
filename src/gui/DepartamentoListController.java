package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.Listeners.DataChangeListeners;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Departamento;
import model.services.DepartamentoService;

public class DepartamentoListController implements Initializable, DataChangeListeners{
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
	public void onbtnIncluirAction(ActionEvent evento) {
		//--Tomamos el Stage Actual(Pantalla Actual)--//
		Stage padreStage = Utils.actualStage(evento); 
		
		//--Instanciamos un Departamento Vacio--//
		Departamento obj = new Departamento();
		
		//--Llama al Método para Configurar el Formulario--//
		createDialogForm(obj,"/gui/DepartamentoForm.fxml",padreStage);
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
		//--Creamos una Lista y cargamos service.findAll()--//
		List<Departamento> lista = service.findAll();
		obsList = FXCollections.observableArrayList(lista);
		tableViewDep.setItems(obsList); 
	} 
	
	//--Método para Crear el Formulario--//
	private void createDialogForm(Departamento obj,String ruta, Stage padreStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
			Pane pane = loader.load();
			
			//--Establecemos la Refrencia del Formulario--//
			DepartamentoFormController controller = loader.getController();
			
			//--Inyectamos la Dependencia--//
			controller.setDepartamento(obj);

			//--Inyectamos la Dependencia del DepartamentoServicio--//
			controller.setDepartamentoServicio(new DepartamentoService());
	
			//--Escribios para Escuchar el Evento--//
			controller.escribeDataChageListener(this);
			 
			//--Cargamos los Txts DepartamentoFormController()--//
			controller.cargaTextDepto();
			
			//--Configuramos la Pantalla Nueva--//
			Stage nuevaPantalla = new Stage();
			nuevaPantalla.setTitle("Registro de Departamentos");
			
			//--Cramos un Scene apartir del Pane--//
			nuevaPantalla.setScene(new Scene(pane));
			nuevaPantalla.setResizable(false);
			
			//--Stage Padre(Propietario) de la Nueva Ventana--//
			nuevaPantalla.initOwner(padreStage);
			
			nuevaPantalla.initModality(Modality.WINDOW_MODAL);
			nuevaPantalla.showAndWait();
		} 
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error Load View", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChaged() {
		updateTableView();
	}


}
