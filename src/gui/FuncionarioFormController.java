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

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
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
import model.Services.DepartamentoService;
import model.Services.FuncionarioService;
import model.entities.Departamento;
import model.entities.Funcionario;
import model.exceptions.ValidationException;

public class FuncionarioFormController implements Initializable{
	
	//Dependências
	private Funcionario entity;
	//Dependências
	private FuncionarioService servico;
	//Dependências
	private DepartamentoService departamentoService;
	
	private List<DataChangeListener> dataChangeListners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private DatePicker dpBirthDate;
	
	@FXML
	private TextField txtBaseSalary;
	
	
	@FXML
	private ComboBox<Departamento> comboBoxDepartmento;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorEmail;
	
	@FXML
	private Label labelErrorBirthDate;
	
	@FXML
	private Label labelErrorBaseSalary;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancel;
	
	private ObservableList<Departamento> obsList;
	
	public void setFuncionario(Funcionario entity) {
		this.entity= entity;
		
	}
	
	public void setServices(FuncionarioService servico, DepartamentoService departamentoService) {
		this.servico = servico;
		this.departamentoService = departamentoService;
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
		Constraints.setTextFieldMaxLength(txtName, 70);
		Constraints.setTextFieldDouble(txtBaseSalary);
		Constraints.setTextFieldMaxLength(txtEmail, 60);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		initializeComboBoxDepartment();
	}
	
	public void updateFormDate() {
		if(entity == null) {
			throw new IllegalStateException("Entidade nula");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		Locale.setDefault(Locale.US);
		txtBaseSalary.setText(String.format("%.2f",entity.getBaseSalary()));
		if(entity.getBirthDate() != null) {
			dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
		}
		if(entity.getDepartamento() == null) {
			comboBoxDepartmento.getSelectionModel().selectFirst();
		}else {
			comboBoxDepartmento.setValue(entity.getDepartamento());
		}
		
	}
	// carregar objetos associados
	public void loadAssociatedObjects() {
		if(departamentoService == null) {
			throw new IllegalStateException("Departamento de serviço estava nulo");
		}
		List<Departamento> list = departamentoService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxDepartmento.setItems(obsList);
	}
	
	private void setErrorMenssage(Map<String, String> erros) {
		Set<String> fields = erros.keySet();
		if(fields.contains("name")) {
			labelErrorName.setText(erros.get("name"));
		}
	}
	
	private void initializeComboBoxDepartment() {
		Callback<ListView<Departamento>, ListCell<Departamento>> factory = lv -> new ListCell<Departamento>() {
			@Override
			protected void updateItem(Departamento item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		
		};	
		comboBoxDepartmento.setCellFactory(factory);
		comboBoxDepartmento.setButtonCell(factory.call(null));
    }
}
