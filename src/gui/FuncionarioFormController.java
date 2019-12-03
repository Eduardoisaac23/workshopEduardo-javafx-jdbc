package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
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
import model.Services.FuncionarioService;
import model.entities.Funcionario;
import model.exceptions.ValidationException;

public class FuncionarioFormController implements Initializable{
	
	//Dependências
	private Funcionario entity;
	//Dependências
	private FuncionarioService servico;
	
	private List<DataChangeListener> dataChangeListners = new ArrayList<>();
	
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
	
	public void setFuncionario(Funcionario entity) {
		this.entity= entity;
		
	}
	
	public void setFuncionarioService(FuncionarioService servico) {
		this.servico = servico;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listner) {
		dataChangeListners.add(listner);
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
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		}catch(ValidationException e ) {
			setErrorMenssage(e.getErros());
		}
		catch(DbException e ) {
			Alerts.showAlert("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	private void notifyDataChangeListeners() {
	
		for(DataChangeListener listener : dataChangeListners) {
			listener.onDataChange();
		}
	}

	private Funcionario getFormData() {
		Funcionario obj = new Funcionario();  
		
		ValidationException exception = new ValidationException("Validação  errada");
		
		obj.setId( Utils.tryParseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addErro("name", "O campo não pode ser vazio");
		}
		obj.setName(txtName.getText());
		
		if(exception.getErros().size() > 0) {
			throw exception;
		}

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
	
	private void setErrorMenssage(Map<String, String> erros) {
		Set<String> fields = erros.keySet();
		if(fields.contains("name")) {
			labelErrorName.setText(erros.get("name"));
		}
	}

}
