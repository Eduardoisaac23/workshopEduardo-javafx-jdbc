package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

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
		System.out.println("menuItemAboutAction");
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
