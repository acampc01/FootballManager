package controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.Alerts;
import models.Usuario;

public class AddUserController {
	private static double xOffset = 0;
	private static double yOffset = 0;

    @FXML
    private JFXButton back;

    @FXML
    private TextField nickField;

    @FXML
    private PasswordField passField;

    @FXML
    private TextField emailField;

    @FXML
    private FontAwesomeIconView tick1;
    
    @FXML
    private FontAwesomeIconView tick2;
    
    @FXML
    private FontAwesomeIconView tick3;

    @FXML
    private FontAwesomeIconView x1;

    @FXML
    private FontAwesomeIconView x2;

    @FXML
    private FontAwesomeIconView x3;

    @FXML
    private JFXButton create;

    @FXML
    void initialize() {
  	
    	nickField.setOnKeyReleased(e -> {
    		if(isCorrect(nickField.getText(), 0)) {
    			x1.setVisible(false);
    			tick1.setVisible(true);
    		}else {
    			x1.setVisible(true);
    			tick1.setVisible(false);
    		}
    	});
    	
    	passField.setOnKeyReleased(e -> {
    		if(isCorrect(passField.getText(), 1)) {
    			x2.setVisible(false);
    			tick2.setVisible(true);
    		}else {
    			x2.setVisible(true);
    			tick2.setVisible(false);
    		}
    	});
    	
    	emailField.setOnKeyReleased(e -> {
    		if(isCorrect(emailField.getText(), 2)) {
    			x3.setVisible(false);
    			tick3.setVisible(true);
    		}else {
    			x3.setVisible(true);
    			tick3.setVisible(false);
    		}
    	});
    }
    
    @FXML
    void onBack(ActionEvent event) {
    	Node source = (Node) event.getSource();
		javafx.stage.Window stage = source.getScene().getWindow();
		
		((javafx.stage.Stage) stage).close();
    }

    @FXML
    void onCreate(ActionEvent event) {
    	if(tick1.isVisible() && tick2.isVisible() && tick3.isVisible()) {
    		Usuario nuevo = new Usuario(nickField.getText(),passField.getText());
    		nuevo.setEmail(emailField.getText());
    		
    		if(nuevo.createUser())
    			Alerts.showCorrect("CREATION");
    		else
    			Alerts.showError("CREATION");
    		
    		onBack(event);
    	}
    }

    private boolean isCorrect(String contenido, int type) {
    	Usuario consulta = new Usuario();
    	return !consulta.userExists(contenido, type);	
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
