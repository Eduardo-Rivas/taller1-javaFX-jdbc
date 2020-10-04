package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList; 
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Vendedor;
import model.exceptions.ValidaErroresCampos;
import model.services.VendedorService;

public class VendedorFormController implements Initializable {

	//--Ceramos una Dependecia del Deprtamento--//
	private Vendedor ven;

	//--Ceramos una Dependecia del Servicio--//
	private VendedorService serviVen;
	
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
	
	//--Cramos una Lista para Registrar Eventos--//
	private List<DataChangeListeners> dataChangeListeners = new ArrayList<>();
	
	
	//--Creamos un Método para Asignar la dependencia--//
	public void setVendedor(Vendedor ven) {
		this.ven =  ven; 
	}
 
	//--Creamos un Método para Asignar serviDep--//
	public void setVendedorServicio(VendedorService serviVen) {
		this.serviVen = serviVen;
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
			ven = getDatos();
 
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
	private Vendedor getDatos() {
		//--Instanciamos el Deprtamento--//
		Vendedor obj = new Vendedor();
	
		
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
		ValidaText.setTextFieldMaxLength(txtNombre, 70);
		ValidaText.setTextFieldDouble(txtSalarioBase);
		ValidaText.setTextFieldMaxLength(txtEmail, 60);
		Utils.formatDatePicker(dpFecha, "dd/MM/yyyy");
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
