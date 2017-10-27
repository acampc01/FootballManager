package models;

import java.util.Date;

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
@Table(name = "transaccion")
public class Transaccion {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String message;
	
	@Column
	private Date date = new Date();
	
	public Transaccion() {
		
	}
	
	public Transaccion(String mess){
		this.message = mess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}
	
	public boolean createTransaccion() {
		try {
			SessionFactory sesion = HibernateUtil.getSessionFactory();
			Session session = sesion.openSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(this);
			tx.commit();
			session.close();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String toString() {
		return "Transaccion: [ID: " + id + " ,Date: "+ date + " ,Message: "+ message + "]\n";
	}
}
