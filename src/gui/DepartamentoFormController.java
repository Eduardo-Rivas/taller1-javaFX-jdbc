package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import db.Dbexception;
import gui.Listeners.DataChangeListeners;
import gui.util.Alerts;
import gui.util.Utils;
import gui.util.ValidaText;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Departamento;
import model.exceptions.ValidaErroresCampos;
import model.services.DepartamentoService;

public class DepartamentoFormController implements Initializable {

	//--Ceramos una Dependecia del Deprtamento--//
	private Departamento depart;

	//--Ceramos una Dependecia del Servicio--//
	private DepartamentoService serviDep;
	
	private DepartamentoListController depListCont;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNombre;
	
	@FXML
	private Label labErr;
	
	@FXML
	private Button btnSave;
	 
	@FXML
	private Button btnCancel;
	
	//--Cramos una Lista para Registrar Eventos--//
	private List<DataChangeListeners> dataChangeListeners = new ArrayList<>();
	
	
	//--Creamos un Método para Asignar la dependencia--//
	public void setDepartamento(Departamento depart) {
		this.depart =  depart;
	}

	//--Creamos un Método para Asignar serviDep--//
	public void setDepartamentoServicio(DepartamentoService serviDep) {
		this.serviDep = serviDep;
	} 
	
	//--Método para A%adir Evento a la LIsta--//
	public void escribeDataChageListener(DataChangeListeners listener) {
		dataChangeListeners.add(listener);
	}
	 
	@FXML
	public void onbtnSaveAction(ActionEvent evento) {
		if(depart == null) {
			throw new IllegalStateException("Departamento Estaba Nulo");
		}
		if(serviDep == null) {
			throw new IllegalStateException("Servicio Estaba Nulo");
		}
		try {
			//--Llamamos al Métdo getDatos--//
			depart = getDatos();
 
			depListCont = new DepartamentoListController();
			 
			//--Llamamos al Servicio para Salvar los Datos--//
			//int val = depListCont.getFlag();//--Incluir--//
			serviDep.insertOrUpdate(depart);
			
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
	private Departamento getDatos() {
		//--Instanciamos el Deprtamento--//
		Departamento obj = new Departamento();
	
		
		//--Instanciamos Clase de Cargar Errores de Campos--//
		ValidaErroresCampos exception = new ValidaErroresCampos("Validación de Campos");
		
		obj.setId(Utils.convertirEntero(txtId.getText()));

		//--Verifica el Contenido del Campo Nombre--//
		if(txtNombre.getText() == null || txtNombre.getText().trim().equals("")){
			exception.addErrores("nombre", "Campo no Puede ser Vacío");
		}
		else {
			obj.setNombre(txtNombre.getText());
		}
			
		//--Verifica Valores en el Map--//
		if(exception.getErrores().size() > 0){
			throw exception;
		}
		return obj;
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
		ValidaText.setTextFieldMaxLength(txtNombre, 30);
	} 
	
	//--Método para Cargar los TextField a la Pantalla--//
	public void cargaTextDepto() {
		//--Verificamos que el Depto no esté vacio--//
		if(depart == null) {
			throw new IllegalStateException("Departamento está Nulo");
		} 
		
		txtId.setText(String.valueOf(depart.getId()));
		txtNombre.setText(depart.getNombre());
	}
	
	//--Método Privado para Asiginar los Msgs--//
	private void setErrorMsg(Map<String, String> errores) {
		//--Cargamos una coleccion set con el Key--//
		Set<String> campos = errores.keySet();
		
		//--Verificamos que el Campo nombre existe--//
		if(campos.contains("nombre")){
			//--Asignamos al Label el Contenido del Error--//
			labErr.setText(errores.get("nombre")); 
		} 
	}
	
}
