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
@Table(name = "usuario")
public class Usuario {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column
	private String nick;

	@Column
	private String password;

	@Column
	private String email;

	@Column
	private String type;

	@Column
	private int money;
	
	public Usuario(){}

	public Usuario(String nombreUsuario, String contraseña){
		this.nick = nombreUsuario;
		this.password = contraseña;
		this.type = "Standard";
		this.money = 10000000;
	}
	
	@SuppressWarnings("unchecked")
	public Usuario getSession(Usuario user){
		SessionFactory sesion = HibernateUtil.getSessionFactory();
	    Session session = sesion.openSession();
	    session.beginTransaction();
		
		List<Usuario> result = session.createQuery("from Usuario where nick='" + user.getNombreUsuario() + "' and password ='" + user.getContraseña()+"'").list();
		
		if(!result.isEmpty()){
			return result.get(0);
		}else{
			return null;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCodigo() {
		return id;
	}

	public String getNombreUsuario() {
		return nick;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nick = nombreUsuario;
	}

	public String getContraseña() {
		return password;
	}

	public void setContraseña(String contraseña) {
		this.password = contraseña;
	}

	public String getTipoUsuario() {
		return type;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.type = tipoUsuario;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public boolean createUser() {
		try {
			SessionFactory sesion = HibernateUtil.getSessionFactory();
			Session session = sesion.openSession();
			Transaction tx = session.beginTransaction();
			session.save(this);
			tx.commit();
			
			Transaccion trans = new Transaccion("Administrador ha creado al usuario: [" + this.getNombreUsuario() + ":" + this.getEmail()+ "].");
			trans.createTransaccion();
			session.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	public boolean userExists(String contenido, int type) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		session.beginTransaction();

		switch(type) {
		case 0: //nick
			this.setNombreUsuario(contenido);
			int rNick = session.createQuery("from Usuario where nick='" + this.getNombreUsuario() + "'").list().size();
			session.close();

			if(rNick>0)
				return true;
			return false;
		case 1: //pass
			if(contenido.length() >= 4) {
				return false;
			}
			return true;
		case 2: //email
			this.setEmail(contenido);
			int rEmail = session.createQuery("from Usuario where email='" + this.getEmail() + "'").list().size();
			session.close();

			if(rEmail>0)
				return true;
			return false;
		default: 
			return false;
		}
	}
	
	public boolean updateUser() {
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
		return "Usuario [name = " + nick + ", pass = " + password + ", type = " + type +", money= " + money + "]";
	}

}