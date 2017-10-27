package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Alerts;
import models.CeldaTablaJugador;
import models.Fichaje;
import models.Jugador;
import models.Usuario;
import windows.Window;

public class TradeUpController {
	private static double xOffset = 0;
	private static double yOffset = 0;

	private static ArrayList<JFXCheckBox> checkList = new ArrayList<JFXCheckBox>();

	private static Usuario usuario = Window.getUser();
	private Fichaje fichaje = new Fichaje();

	@FXML
	private TableView<CeldaTablaJugador> marketTable;

	@FXML
	private TableColumn<CeldaTablaJugador, String> nameRow;

	@FXML
	private TableColumn<CeldaTablaJugador, String> positionRow;

	@FXML
	private TableColumn<CeldaTablaJugador, Integer> valueRow;

	@FXML
	private TableColumn<CeldaTablaJugador, Integer> priceRow;

	@FXML
	private TableColumn<CeldaTablaJugador, JFXCheckBox> buttonRow;

	@FXML
	private ObservableList<CeldaTablaJugador> data;

	@FXML
	private JFXButton back;

	@FXML
	private JFXButton money;

	@FXML
	void onBack(ActionEvent event) {
		Node source = (Node) event.getSource();
		javafx.stage.Window stage = source.getScene().getWindow();
		
		((javafx.stage.Stage) stage).close();
	}

	@FXML
	void onVender(ActionEvent event) {
		boolean correct = false;
		boolean someSelected = false;
		
		if(!checkList.isEmpty()) {
			for (Iterator<JFXCheckBox> iterator = checkList.iterator(); iterator.hasNext();) {
				JFXCheckBox checkBox = (JFXCheckBox) iterator.next();
				if(checkBox.isSelected()) {
					someSelected = true;
					Fichaje f = new Fichaje();
					f.update(checkBox.getId());
					correct = f.tradeUp();
					f.update();
				}
			}
		}

		if(correct && someSelected) {
			Alerts.showCorrect("TRADE UP");
			initialize();
		}else if(someSelected){
			Alerts.showError("CANNOT TRADE UP");
		}
	}

	@FXML
	void initialize() {
		CeldaTablaJugador[] players;

		{
			List<Jugador> pls = fichaje.getOwnedPlayers(usuario);
			players = new CeldaTablaJugador[pls.size()];

			for (int i = 0; i < pls.size(); i++) {
				players[i] = new CeldaTablaJugador(pls.get(i), checkList);
			}
		}
		
		if(!checkList.isEmpty()) {
			for (Iterator<JFXCheckBox> iterator = checkList.iterator(); iterator.hasNext();) {
				JFXCheckBox checkBox = (JFXCheckBox) iterator.next();
				List<Jugador> pls = fichaje.getOwnedPlayers(usuario);
				for (Iterator<Jugador> iterator2 = pls.iterator(); iterator2.hasNext();) {
					Jugador jugador = (Jugador) iterator2.next();
					if(jugador.getName().equals(checkBox.getId()))
						checkBox.setDisable(fichaje.inSell(jugador));
				}
			}
		}
		
		data = FXCollections.observableArrayList(players);
		
		money.setText(""+usuario.getMoney());

		nameRow.setCellValueFactory(new PropertyValueFactory<CeldaTablaJugador, String>("name"));
		positionRow.setCellValueFactory(new PropertyValueFactory<CeldaTablaJugador, String>("position"));
		valueRow.setCellValueFactory(new PropertyValueFactory<CeldaTablaJugador, Integer>("value"));
		priceRow.setCellValueFactory(new PropertyValueFactory<CeldaTablaJugador, Integer>("price"));
		buttonRow.setCellValueFactory(new PropertyValueFactory<CeldaTablaJugador, JFXCheckBox>("checkbox"));

		marketTable.setItems(data);
		marketTable.setPlaceholder(new Label("No players for trade up, try buying some!"));
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
