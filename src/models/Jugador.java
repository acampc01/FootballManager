package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import controllers.HibernateUtil;

@Entity
@Table(name = "jugador")
public class Jugador {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idP;
	
	@Column
	private String name;
	
	@Column
	private String position;
	
	@Column
	private int price;

	@Column
	private int value;
	
	public Jugador(){}
	
	public Jugador(String name, String position, int value, int price) {
		this.name = name;
		this.position = position;
		this.value = value;
	}

	public int getId() {
		return idP;
	}

	public String getName() {
		return name;
	}

	public String getPosition() {
		return position;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getValue() {
		return value;
	}
	
	@SuppressWarnings("unchecked")
	public static Jugador getPlayer(String name, String position) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
	    Session session = sesion.openSession();
	    session.beginTransaction();
	
		List<Jugador> result = session.createQuery("from Fichaje where name ='" + name + "' and position = '" + position + "'").list();
		session.close();
		
		if(!result.isEmpty() && result.size() == 1)
			return result.get(0);
		else
			return null;
	}
	
	public boolean updatePlayer() {
		try {
			SessionFactory sesion = HibernateUtil.getSessionFactory();
			Session session = sesion.openSession();
			Transaction tx = session.beginTransaction();
			session.update(this);
			tx.commit();
			session.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "Jugador [name = " + name + ", position = " + position + ", value = " + value + "]";
	}
	
}
