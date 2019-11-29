package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Services.DepartamentoService;
import model.entities.Departmento;

public class DepartamentoListController implements Initializable {

	//não se pode colocar aqui a implementação dessa classe
	//pois acaba sendo um acoplamento forte
	//para isso será criado um metodo set
	private DepartamentoService servico;//  = new DepartamentoService();
	
	
	@FXML
	private TableView<Departmento> tableViewDepartamento;
	
	@FXML
	private TableColumn<Departmento, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Departmento, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	
	//Vai ter que carregar a lista no ObservableList
	private ObservableList<Departmento> obsList;
	
	
	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = gui.util.Utils.currentStage(event);
		createDialogForm("/gui/DepartmentoForm.fxml", parentStage);
	}
	
	//metodo para injetar dependência por outro lugar
	
	public void setDepartamentoServico(DepartamentoService service) {
		this.servico = service;
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializableNodes();
		
	}


	//Metodo auxiliar
	private void initializableNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		
		Stage stage =  (Stage) Main.getMainScene().getWindow();
		tableViewDepartamento.prefHeightProperty().bind(stage.heightProperty());
	}
	
	/*metodo responsável por acessar o sreviço, carregar os departamentos e jogar 
	 esses departamento em meu ObservableList linha 40
	com isso vou associar ele com meu tableView e com isso os departamentos vão aparecer
	na tela */
	public void updateTableView() {
		if(servico == null) {
			throw new IllegalStateException("Serviçoi estava nulo");
		}
		
		List<Departmento> list = servico.findAll();
		//aqui instância meu  ObservableList pegando os dados originais da listinha
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartamento.setItems(obsList);
		
	}
	
	//finção para carregar a janela do formulário de um novo departamento
	private void createDialogForm(String absoluteName, Stage parenStage) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com o dado do departamento");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parenStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
			
		}catch(IOException e ) {
			Alerts.showAlert("IO Exception", "Erroao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}

}
