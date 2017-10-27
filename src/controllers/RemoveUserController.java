package controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class RemoveUserController {
	private static double xOffset = 0;
	private static double yOffset = 0;
	
	
	
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
