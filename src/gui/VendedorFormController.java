package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import db.Dbexception;
import gui.Listeners.DataChangeListeners;
import gui.util.Alerts;
import gui.util.Utils;
import gui.util.ValidaText;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Departamento;
import model.entities.Vendedor;
import model.exceptions.ValidaErroresCampos;
import model.services.DepartamentoService;
import model.services.VendedorService;

public class VendedorFormController implements Initializable {

	//--Ceramos una Dependecia del Deprtamento--//
	private Vendedor ven;

	//--Ceramos una Dependecia del Servicio--//
	private VendedorService serviVen;
	
	private DepartamentoService serviDep;
	
	private VendedorListController venListCont;
	
	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNombre;
	
	@FXML
	private TextField txtEmail;

	@FXML
	private DatePicker dpFecha;
	
	@FXML
	private TextField txtSalarioBase;
	
	@FXML
	private ComboBox<Departamento> comboBoxDep;

	@FXML
	private Label labErr;

	@FXML
	private Label labErrEmail;
	
	@FXML
	private Label labErrFecha;

	@FXML
	private Label labErrSalarioBase;
	
	@FXML
	private Button btnSave;
	 
	@FXML
	private Button btnCancel;
	
	private ObservableList<Departamento> obsList;
	
	//--Cramos una Lista para Registrar Eventos--//
	private List<DataChangeListeners> dataChangeListeners = new ArrayList<>();
	
	
	//--Creamos un Método para Asignar la dependencia--//
	public void setVendedor(Vendedor ven) {
		this.ven =  ven; 
	}
 
	//--Creamos un Método para Asignar serviDep--//
	public void setServicios(VendedorService serviVen, DepartamentoService serviDep) {
		this.serviVen = serviVen;
		this.serviDep = serviDep; 
	} 
	
	//--Método para A%adir Evento a la LIsta--//
	public void escribeDataChageListener(DataChangeListeners listener) {
		dataChangeListeners.add(listener);
	}
	 
	@FXML
	public void onbtnSaveAction(ActionEvent evento) {
		if(ven == null) {
			throw new IllegalStateException("Vendedor Estaba Nulo");
		}
		if(serviVen == null) {
			throw new IllegalStateException("Servicio Estaba Nulo");
		}
		try {
			//--Llamamos al Métdo getDatos--//
			ven = getFormDatos();
 
			venListCont = new VendedorListController();
			 
			//--Llamamos al Servicio para Salvar los Datos--//
			//int val = depListCont.getFlag();//--Incluir--//
			serviVen.insertOrUpdate(ven);
			
			labErr.setText("Registro Actualizado...");
	
			//--Método para Notificar Cambios--//
			notificaDataChageListener();
			
			//--Cerramos la Ventana Actual--//
			Utils.actualStage(evento).close();
			
		} 
		catch(ValidaErroresCampos e) {
			setErrorMsg(e.getErrores());
		}
		catch (Dbexception e) {
			Alerts.showAlert("Registro Nulo", null, e.getMessage(), AlertType.ERROR);
		}

	}
	 
	//--Notifica el Listener en la Lista--//
	private void notificaDataChageListener() {
		for(DataChangeListeners listeners : dataChangeListeners) {
			listeners.onDataChaged();
		}
		
	}
 
	//--Método para tomar los Datos de la Pantalla--//
	private Vendedor getFormDatos() {
		//--Instanciamos el Vendedor en un Obj.--//
		Vendedor objVen = new Vendedor();
		
		//--Instanciamos Clase de Cargar Errores de Campos--//
		ValidaErroresCampos errCampos = new ValidaErroresCampos("Validación de Campos");
		
		//--Cargamos lo que esta en Field en Obj. Id.--//
		objVen.setId(Utils.convertirEntero(txtId.getText()));

		//--Verifica el Contenido del Campo Nombre--//
		if(txtNombre.getText() == null || txtNombre.getText().trim().equals("")){
			errCampos.addErrores("nombre", "Nombre no Puede ser Vacío");
		}
		else {
			objVen.setNombre(txtNombre.getText());
		}

		//--Verifica el Contenido del Campo Email--//
		if(txtEmail.getText() == null || txtEmail.getText().trim().equals("")){
			errCampos.addErrores("email", "Email no Puede ser Vacío");
		}
		else {
			objVen.setEmail(txtEmail.getText());
		}
		 
		if(dpFecha.getValue() == null) {
			errCampos.addErrores("fecha", "Fecha no Puede ser Vacía");
		}
		else {
			//--Tomamos la Fecha y la Colocamos en un instant--//
			Instant instant = Instant.from(dpFecha.getValue().atStartOfDay(ZoneId.systemDefault()));
			objVen.setFecha(Date.from(instant));
		}
 
		if(txtSalarioBase.getText() == null || txtSalarioBase.getText().trim().equals("")){
			errCampos.addErrores("salarioBase", "Salario no Puede ser Vacío");
		}
		else {
			//--Tomamos el Salario--//
			objVen.setSalarioBase(Utils.convertirDouble(txtSalarioBase.getText()));
		}
		
		//--Asignamos el Departamento para el Vendedor--//
		objVen.setDepartamento(comboBoxDep.getValue());;
		 
		//--Verifica Valores en el Map--//
		if(errCampos.getErrores().size() > 0){
			throw errCampos;
		}
		return objVen;
	}
 
	@FXML
	public void onbtnCancelAction(ActionEvent evento){
		//--Cerramos la Ventana Actual--//
		Utils.actualStage(evento).close();
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initCampos();
	}

	private void initCampos() {
		ValidaText.setTextFieldInteger(txtId);
		ValidaText.setTextFieldMaxLength(txtNombre, 70);
		ValidaText.setTextFieldDouble(txtSalarioBase);
		ValidaText.setTextFieldMaxLength(txtEmail, 60);
		Utils.formatDatePicker(dpFecha, "dd/MM/yyyy");
		initializeComboBoxDep();
	} 
	
	//--Método para Cargar los TextField a la Pantalla--//
	public void cargaTextDepto() {
		//--Verificamos que el Depto no esté vacio--//
		if(ven == null) {
			throw new IllegalStateException("Vendedor está Nulo");
		} 
		 
		txtId.setText(String.valueOf(ven.getId()));
		txtNombre.setText(ven.getNombre());
		txtEmail.setText(ven.getEmail());
		Locale.setDefault(Locale.US);
		txtSalarioBase.setText(String.format("%.2f", ven.getSalarioBase()));
		if(ven.getFecha() != null){
			dpFecha.setValue(LocalDate.ofInstant(ven.getFecha().toInstant(), ZoneId.systemDefault()));
		}
		
		if(ven.getDepartamento() == null) {
			comboBoxDep.getSelectionModel().selectFirst();
		}   
		else {
			comboBoxDep.setValue(ven.getDepartamento());
		}
		
	}
	 
	public void loadObjAso() {
		if(serviDep == null) {
			throw new IllegalStateException("Servicio de Departamento Estaba Nulo");
		} 
		List<Departamento> lista = serviDep.findAll();
		obsList = FXCollections.observableArrayList(lista);
		comboBoxDep.setItems(obsList);
	}
	
	//--Método Privado para Asiginar los Msgs--//
	private void setErrorMsg(Map<String, String> errores) {
		//--Cargamos una coleccion set con el Key--//
		Set<String> campoKey = errores.keySet();
		
		//--Validamos usando Condiciones Ternarias--//
		labErr.setText(campoKey.contains("nombre") ? errores.get("nombre") : "");
		
		//--Verificamos que el Campo Email existe--//
		labErrEmail.setText(campoKey.contains("email") ? errores.get("email") : "");
		 
		//--Verificamos que el Campo SalarioBase existe--//
		labErrSalarioBase.setText(campoKey.contains("salarioBase") ? errores.get("salarioBase") : "");

		//--Verificamos que el Campo Fecha existe--//
		labErrFecha.setText(campoKey.contains("fecha") ? errores.get("fecha") : "");
		
	}
	
	private void initializeComboBoxDep() {
		Callback<ListView<Departamento>, ListCell<Departamento>> factory = lv -> new ListCell<Departamento>() {
			
			@Override
			protected void updateItem(Departamento item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNombre());
			}
			
		};
		comboBoxDep.setCellFactory(factory);
		comboBoxDep.setButtonCell(factory.call(null));
	}
	
}
