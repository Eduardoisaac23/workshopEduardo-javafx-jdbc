package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem MenuItemVendedor;
	
	@FXML
	private MenuItem MenuItemDepartamento;
	
	@FXML
	private  MenuItem menuItemAbout;
	
	
	//Declarar metodo para tratar cada uma das ações do item de menu
	@FXML
	public void onMenuItemVendedorAction() {
		System.out.println("onMenuItemVendedorAction");
	}
	
	@FXML
	public void MenuItemDepartamentoAction() {
		System.out.println("MenuItemDepartamentoAction");
	}
	
	@FXML
	public void menuItemAboutAction() {
		loadView("/gui/About.fxml");

	}
	
	@Override
	public void initialize(URL uri, ResourceBundle arg1) {
		// TODO Auto-genera ted method stub
		
	}
	
	// função
	private void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox =  (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());

		} catch (IOException e) {
			Alerts.showAlert("IO exception", "Erro loadig view", e.getMessage(), AlertType.ERROR);
		}
	}

}
