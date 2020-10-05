package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbintegrtyException;
import gui.Listeners.DataChangeListeners;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
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
	TableColumn<Departamento, Departamento> tableColumnEDIT;

	@FXML
	TableColumn<Departamento, Departamento> tableColumnREMOVE;

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
		
		//--Boton de Edicion en TableView--//
		initEditButtons(); 
		
		//--Boton para Remover en TableView--//
		initRemoveButtons();
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
			controller.cargaTextDepto();//--Incluyendo--//
			 
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
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error Load View", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChaged() {
		updateTableView();
	}
 
	//--Método para Modificar--//
	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
			private final Button button = new Button("edit");
		
			@Override
			protected void updateItem(Departamento obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(
								obj, "/gui/DepartamentoForm.fxml",Utils.actualStage(event)));
			}
		
		});
	}//--Fin del Método initEditButtons()--/
	
	//--Inicio del Metodo para Remover--//
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
			private final Button button = new Button("remove");
			 
			@Override
			protected void updateItem(Departamento obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
				 
			}
		});	
	}//--Final del Método Remover-//

	//--Método Interno para Recibir confirmacón para Eliminar--//
	private void removeEntity(Departamento obj) {
		Optional<ButtonType> result = 
			Alerts.showConfirmation("Confirmación", "Está seguro de Eliminar el Registro...");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Servicio Está Nulo");
			}
			try {
				service.remove(obj);
				updateTableView();
			} catch (DbintegrtyException e) {
				Alerts.showAlert("Error de Borrado", null, e.getMessage(), AlertType.ERROR);
			}
		}

	}
	
   
}
