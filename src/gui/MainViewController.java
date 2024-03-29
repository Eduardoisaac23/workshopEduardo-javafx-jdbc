package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.Services.DepartamentoService;
import model.Services.FuncionarioService;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemVendedor;
	
	@FXML
	private MenuItem menuItemDepartamento;
	
	@FXML
	private  MenuItem menuItemAbout;
	
	
	//Declarar metodo para tratar cada uma das ações do item de menu
	@FXML
	public void onMenuItemVendedorAction() {
		loadView("/gui/FuncionarioListController.fxml", (FuncionarioListController controller) -> {
			controller.setFuncionarioServico(new FuncionarioService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemDepartamentoAction() {
		loadView("/gui/DepartamentoListController.fxml", (DepartamentoListController controller) -> {
			controller.setDepartamentoServico(new DepartamentoService());
			controller.updateTableView();
		});

	}
	
	@FXML
	public void onmenuItemAboutAction() {
		loadView("/gui/About.fxml",  x -> {});

	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-genera ted method stub
		
	}
	
	// função
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox =  (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller =loader.getController();
			initializingAction.accept(controller);

		} catch (IOException e) {
			Alerts.showAlert("IO exception", "Erro loadig view", e.getMessage(), AlertType.ERROR);
		}
	}

}
