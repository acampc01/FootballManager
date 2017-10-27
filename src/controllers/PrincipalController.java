package controllers;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Usuario;
import windows.Window;

public class PrincipalController {
	private static double xOffset = 0;
	private static double yOffset = 0;
	
	private static Usuario usuario = Window.getUser();
	
    @FXML
    private JFXTextField user;

    @FXML
    private ImageView imgUser;

    @FXML
    private JFXButton money;

    @FXML
    private JFXButton logOut;

    @FXML
    private JFXButton type;

    @FXML
    private JFXButton addUser;

    @FXML
    private JFXButton deleteUser;
    
    @FXML
    void onAddUser(ActionEvent event) {
    	Window.toAddUser();
    }

    @FXML
    void onContactUs(ActionEvent event) {
    	JOptionPane.showMessageDialog(null, "Proximamente", "Proximamente", JOptionPane.WARNING_MESSAGE);
    }

    @FXML
    void onDeleteUser(ActionEvent event) {
    	//Window.toRemoveUser();
    }

    @FXML
    void onLogOut(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void onMarket(ActionEvent event) {
    	Window.toMarket();
    }

    @FXML
    void onSellPlayer(ActionEvent event) {
    	Window.toTradeUp();
    }
    
    @FXML
    void initialize() {
    	user.setText(usuario.getNombreUsuario());
    	money.setText("" + usuario.getMoney());
    	type.setText(usuario.getTipoUsuario());
    	if(!usuario.getTipoUsuario().equals("Admin")) {
    		addUser.setVisible(false);
    		deleteUser.setVisible(false);
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
