package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Services.DepartamentoService;
import model.entities.Departamento;

public class DepartamentoFormController implements Initializable{
	
	//Dependências
	private Departamento entity;
	private DepartamentoService servico;
	
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
	
	public void setDepartamento(Departamento entity) {
		this.entity= entity;
		
	}
	
	public void setDepartamentoService(DepartamentoService servico) {
		this.servico = servico;
	}
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		//operação defensiva
		if(entity == null) {
			throw new IllegalStateException("Entidade nulo");
		}
		//operação defensiva
		if(servico == null) {
			throw new IllegalStateException("Serviço nulo");
		}
		try {
			entity = getFormData();
			servico.saveOrUpdate(entity);
			Utils.currentStage(event).close();
		}catch(DbException e ) {
			Alerts.showAlert("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	private Departamento getFormData() {
		Departamento obj = new Departamento();
		obj.setId( Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		
		return obj;
	}

	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL erl, ResourceBundle rb) {
	
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	
	public void updateFormDate() {
		if(entity == null) {
			throw new IllegalStateException("Entidade nula");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
	}

}
