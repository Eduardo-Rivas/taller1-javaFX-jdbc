package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.ValidaText;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartamentoFormController implements Initializable {

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

}
