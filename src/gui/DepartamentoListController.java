package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Services.DepartamentoService;
import model.entities.Departamento;

public class DepartamentoListController implements Initializable, DataChangeListener {

	// não se pode colocar aqui a implementação dessa classe
	// pois acaba sendo um acoplamento forte
	// para isso será criado um metodo set
	private DepartamentoService servico;// = new DepartamentoService();

	@FXML
	private TableView<Departamento> tableViewDepartamento;

	@FXML
	private TableColumn<Departamento, Integer> tableColumnId;

	@FXML
	private TableColumn<Departamento, String> tableColumnName;

	@FXML
	private TableColumn<Departamento, Departamento> tableColumnEDIT;

	@FXML
	private TableColumn<Departamento, Departamento> tableColumnREMOVE;

	@FXML
	private Button btNew;

	// Vai ter que carregar a lista no ObservableList
	private ObservableList<Departamento> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = gui.util.Utils.currentStage(event);
		// instancia um departamento vazio
		Departamento obj = new Departamento();
		createDialogForm(obj, "/gui/DepartamentoForm.fxml", parentStage);
	}

	// metodo para injetar dependência por outro lugar

	public void setDepartamentoServico(DepartamentoService service) {
		this.servico = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializableNodes();

	}

	// Metodo auxiliar
	private void initializableNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartamento.prefHeightProperty().bind(stage.heightProperty());
	}

	/*
	 * metodo responsável por acessar o sreviço, carregar os departamentos e jogar
	 * esses departamento em meu ObservableList linha 40 com isso vou associar ele
	 * com meu tableView e com isso os departamentos vão aparecer na tela
	 */
	public void updateTableView() {
		if (servico == null) {
			throw new IllegalStateException("Serviçoi estava nulo");
		}

		List<Departamento> list = servico.findAll();
		// aqui instância meu ObservableList pegando os dados originais da listinha
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartamento.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	// função para carregar a janela do formulário de um novo departamento
	private void createDialogForm(Departamento obj, String absoluteName, Stage parenStage) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			DepartamentoFormController controller = loader.getController();
			controller.setDepartamento(obj);
			// Injeção de dependência
			controller.setDepartamentoService(new DepartamentoService());
			// --------------------------
			controller.subscribeDataChangeListener(this);
			controller.updateFormDate();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com o dado do departamento");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parenStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChange() {
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Departamento obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/DepartamentoForm.fxml",
						gui.util.Utils.currentStage(event)));
			}

		});
	}

	@SuppressWarnings("unused")
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Departamento, Departamento>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Departamento obj, boolean empty) {
				super.updateItem(	 obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	// operação par aremover uma entidade
	private void removeEntity(Departamento obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Conf	irmação", "Tem serteza que gostaria de deletar");
		if (result.get() == ButtonType.OK) {
			if (servico == null) {
				throw new IllegalStateException("Serviço nulo");
			}
			try {
				servico.remove(obj);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
}
