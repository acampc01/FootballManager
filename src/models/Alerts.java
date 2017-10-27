package models;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

public class Alerts {
	
	public static void showCorrect(String message) {
		new Alerts().showInfo(message);
	}
	
	public static void showError(String message) {
		new Alerts().showErrorM(message);
	}
	
	private void showInfo(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("CORRECT");
	    alert.setHeaderText("CORRECT: " + message);
	    alert.initStyle(StageStyle.UNDECORATED);
	    
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("alerts/alert.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");
		
		alert.showAndWait();
	}
	
	private void showErrorM(String message) {
		Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("ERROR");
	    alert.setHeaderText("INCORRECT: " + message);
	    alert.initStyle(StageStyle.UNDECORATED);
	    
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("alerts/alert.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");
		
		alert.showAndWait();
	}
}
