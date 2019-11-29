package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentoFormController implements Initializable{
	
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancel;
	
	public void onBtSalvarAction() {
		System.out.println("onBtSalvarAction");
	}
	public void onBtCancelarAction() {
		System.out.println("onBtCancelarAction");
	}

	@Override
	public void initialize(URL erl, ResourceBundle rb) {
	
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}

}
