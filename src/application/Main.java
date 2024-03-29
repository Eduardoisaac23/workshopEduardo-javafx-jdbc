package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {

	// atributo para guardar referência
	private static Scene mainScene;
	
	@Override               //argumento palco da minha sena
	public void start(Stage primaryStage) {
		//INSTANCIOU UM NOVO OBJETO  DO TIPO FXMLLoader  LOADER
		try {                                                        //este e o caminho da view
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			//depois chamamos um loader.load(); e carrega a View
			ScrollPane scrollPane = loader.load();
			//Altura da scroll
			scrollPane.setFitToHeight(true);
			//largura da scroll
			scrollPane.setFitToWidth(true);
			// criar um objeto do tipo Scene  que terá mainScene como sena principal
			// e istânciado esta sena já passando como argumento o bojeto principal da minha View
			//No caso e o AnchorPane vasio
			mainScene = new Scene(scrollPane);//resumindo criou a sena
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
	//metodo para pegar referência
	public static Scene getMainScene() {
		return mainScene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
