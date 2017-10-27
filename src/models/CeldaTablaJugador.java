package models;

import java.util.ArrayList;

import com.jfoenix.controls.JFXCheckBox;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * 
 * Clase utilizada para rellenar tablas de java fx8
 * 
 * @author Alex
 *
 */
public class CeldaTablaJugador {

	private final SimpleStringProperty name;

	private final SimpleStringProperty position;

	private final SimpleIntegerProperty value;

	private final SimpleIntegerProperty price;
	
	private final JFXCheckBox checkbox;

	public CeldaTablaJugador(Jugador player, ArrayList<JFXCheckBox> checkList) {
		this.name = new SimpleStringProperty(player.getName());
		this.position = new SimpleStringProperty(player.getPosition());
		this.value = new SimpleIntegerProperty(player.getValue());
		this.price = new SimpleIntegerProperty(player.getPrice());
		checkbox = new JFXCheckBox("");
		checkbox.setId(""+name.get());
		checkList.add(checkbox);
	}

	public String getName() {
		return name.get();
	}

	public String getPosition() {
		return position.get();
	}

	public int getValue() {
		return value.get();
	}

	public int getPrice() {
		return price.get();
	}

	public JFXCheckBox getCheckbox() {
		return checkbox;
	}
	
}
