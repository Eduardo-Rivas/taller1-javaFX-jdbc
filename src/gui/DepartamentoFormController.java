package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.ValidaText;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Departamento;

public class DepartamentoFormController implements Initializable {

	//--Cramos una Dependencia--//
	private Departamento dpto;
	
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
	
	//--Método para Inyectar dependecia--//
	public void setDepartamento(Departamento dpto) {
		this.dpto = dpto;
	}
	
	@FXML
	public void onbtnSaveAction() {
		System.out.println("Pusó Boton Salvar...");
	}
	
	@FXML
	public void onbtnCancelAction() {
		System.out.println("Pusó Boton Cancelar...");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initCampos();
	}

	private void initCampos() {
		ValidaText.setTextFieldInteger(txtId);
		ValidaText.setTextFieldMaxLength(txtNombre, 30);
	}
	
	//--Método para Actualizar los Textos del Fromulario--//
	public void updateFormDpto(){
		//--Verifica que el Dpto no esté Vacio--//
		if(dpto == null){
			throw new IllegalStateException("La Departamento está Vacia");
		}
		//--Cargamos los Valores a los Txts--//
		txtId.setText(String.valueOf(dpto.getId()));
		txtNombre.setText(dpto.getNombre());
	}

}
