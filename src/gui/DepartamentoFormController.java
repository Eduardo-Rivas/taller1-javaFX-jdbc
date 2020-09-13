package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.Dbexception;
import gui.Listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import gui.util.ValidaText;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Departamento;
import model.services.DepartamentoService;

public class DepartamentoFormController implements Initializable {

	//--Ceramos una Dependecia del Deprtamento--//
	private Departamento depart;

	//--Ceramos una Dependecia del Servicio--//
	private DepartamentoService serviDep;
	
	//--Lista de Obj. Interfaces--//
	private List<DataChangeListener> dataChagedListeners = new ArrayList<>();
	
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
	
	//--Creamos un Método para Asignar la dependencia--//
	public void setDepartamento(Departamento depart) {
		this.depart =  depart;
	}

	//--Creamos un Método para Asignar serviDep--//
	public void setDepartamentoServicio(DepartamentoService serviDep) {
		this.serviDep = serviDep;
	} 
	 
	//--Método para escribir ChagedListener--//
	public void escribeDataChangedListener(DataChangeListener listener) {
		//--Agregamos a la lsita--//
		dataChagedListeners.add(listener);
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

			//--Llamamos al Servicio para Salvar los Datos--//
			int opc = 1;//--Incluir--//
			serviDep.insertOrUpdate(depart,opc);
			
			labErr.setText("Registro Actualizado...");
	
			//--Llaamos al Método para Notificar el Listener--//
			notifyDataChagedListeners();
			
			//--Cerramos la Ventana Actual--//
			Utils.actualStage(evento).close();
			
		} catch (Dbexception e) {
			Alerts.showAlert("Registro Nulo", null, e.getMessage(), AlertType.ERROR);
		}

	}
	
	private void notifyDataChagedListeners() {
		for(DataChangeListener xlistener : dataChagedListeners) {
			xlistener.onDataChanged();
		}
		
	}

	//--Método para tomar los Datos de la Pantalla--//
	private Departamento getDatos() {
		//--Instanciamos el Deprtamento--//
		Departamento obj = new Departamento();

		obj.setId(Utils.convertirEntero(txtId.getText()));
		obj.setNombre(txtNombre.getText());
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
		//--Llamaos la Método goR() tomar siguiente Id--//
		depart.setId(serviDep.goReg());
		txtId.setText(String.valueOf(depart.getId()));
		txtNombre.setText(depart.getNombre());
	}

}