package windows;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Usuario;

public class Window extends Application{

	/*
	 * TODO
	 * 
	 * ventana y peticiones ventana borrar usuario
	 * 
	 */
	
	private static Usuario usuario;
	private static Stage stage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;

		Parent loginRes = FXMLLoader.load(Window.class.getClassLoader().getResource("controllers/interfaces/Login.fxml"));

		GridPane login = new GridPane();
		login.getChildren().add(loginRes);

		Scene loginScene = new Scene(login);

		stage.setScene(loginScene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();
	}

	public static void toMainMenu() {
		Parent p;
		try {
			stage.close();
			stage = new Stage();
			
			p = FXMLLoader.load(Window.class.getClassLoader().getResource("controllers/interfaces/PrincipalWindow.fxml"));

			GridPane gridPane = new GridPane();
			gridPane.getChildren().add(p);

			Scene scene = new Scene(gridPane);

			stage.initStyle(StageStyle.UNDECORATED);
			stage.setResizable(false);
			stage.sizeToScene();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error cargando la ventana principal.");
		}
	}

	public static void toMarket() {
		Parent p;
		try {
			stage = new Stage();
			
			p = FXMLLoader.load(Window.class.getClassLoader().getResource("controllers/interfaces/MarketWindow.fxml"));

			GridPane gridPane = new GridPane();
			gridPane.getChildren().add(p);

			Scene scene = new Scene(gridPane);
			
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setResizable(false);
			stage.sizeToScene();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error cargando la ventana 'market'.");
		}
	}

	public static void toTradeUp() {
		Parent p;
		try {
			stage = new Stage();
			
			p = FXMLLoader.load(Window.class.getClassLoader().getResource("controllers/interfaces/TradeUpWindow.fxml"));

			GridPane gridPane = new GridPane();
			gridPane.getChildren().add(p);

			Scene scene = new Scene(gridPane);
			
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setResizable(false);
			stage.sizeToScene();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			System.err.println("Error cargando la ventana 'trade up'.");
		}
	}

	public static void toAddUser() {
		Parent p;
		try {
			stage = new Stage();
			
			p = FXMLLoader.load(Window.class.getClassLoader().getResource("controllers/interfaces/AddUser.fxml"));

			GridPane gridPane = new GridPane();
			gridPane.getChildren().add(p);

			Scene scene = new Scene(gridPane);
			
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setResizable(false);
			stage.sizeToScene();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			System.err.println("Error cargando la ventana 'add user'.");
		}
	}

	public static void toRemoveUser() {
		Parent p;
		try {
			stage = new Stage();
			
			p = FXMLLoader.load(Window.class.getClassLoader().getResource("controllers/interfaces/RemoveUser.fxml"));

			GridPane gp = new GridPane();
			gp.getChildren().add(p);

			Scene scene = new Scene(gp);
			
			stage.initStyle(StageStyle.DECORATED);
			stage.setResizable(false);
			stage.sizeToScene();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			System.err.println("Error cargando la ventana 'remove user'.");
		}
	}
	
	public static Usuario getUser() {
		return usuario;
	}
	
	public static void setUser(Usuario user) {
		usuario = user;
	}
}