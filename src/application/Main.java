package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override               //argumento palco da minha sena
	public void start(Stage primaryStage) {
		//INSTANCIOU UM NOVO OBJETO  DO TIPO FXMLLoader  LOADER
		try {                                                        //este e o caminho da view
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			//depois chamamos um loader.load(); e carrega a View
			Parent parent = loader.load();
			// criar um objeto do tipo Scene  que terá mainScene como sena principal
			// e istânciado esta sena já passando como argumento o bojeto principal da minha View
			//No caso e o AnchorPane vasio
			Scene mainScene = new Scene(parent);//resumindo criou a sena
			// primaryStage palco da minha sena que etá estanciado no meu metodo void 
			//será setado como minha sena principal
			primaryStage.setScene(mainScene);
			//definir o titolo do meu palco
			primaryStage.setTitle("Sample JavaFX application");
			//Mostrar o palco
			primaryStage.show();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
