package controllers;

import windows.Window;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.Alerts;
import models.Usuario;

public class LoginController{
	private static double xOffset = 0;
	private static double yOffset = 0;

	private static Usuario usuario = Window.getUser();
	private Usuario conn = new Usuario();

	@FXML
	private JFXTextField user;

	@FXML
	private JFXPasswordField pass;

	@FXML
	private JFXButton log;

	@FXML
	void logIn(ActionEvent event) {
		if(!user.getText().equals("") && !pass.getText().equals("")) {
			Usuario nuevo = conn.getSession(new Usuario(user.getText(),pass.getText()));

			Window.setUser(nuevo);
			usuario = nuevo;

			if(usuario != null) {
				Alerts.showCorrect("LOGIN");
				Window.toMainMenu();
			}else {
				Alerts.showError("LOGIN");
			}
		}else {
			Alerts.showError("LOGIN");
		}
	}

	@FXML
	void onKeyPressed(KeyEvent event) {
		if(event.getCode() == KeyCode.ESCAPE) {
			System.exit(0);
		}
		if(event.getCode() == KeyCode.ENTER) {
			logIn(new ActionEvent());
		}
	}

	@FXML
	void onMousePressed(MouseEvent event) {
		Node source = (Node) event.getSource();
		javafx.stage.Window stage = source.getScene().getWindow();
		
		xOffset = stage.getX() - event.getScreenX();
		yOffset = stage.getY() - event.getScreenY();
	}

	@FXML
	void onMouseDragged(MouseEvent event) {
		Node source = (Node) event.getSource();
		javafx.stage.Window stage = source.getScene().getWindow();
		
		stage.setX(event.getScreenX() + xOffset);
		stage.setY(event.getScreenY() + yOffset);
	}

}
