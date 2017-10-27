package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
@Table(name = "fichaje")
public class Fichaje {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column
	private Date date = new Date();

	@Column
	private int idUser;

	@Column
	private int idPlayer;

	@Column
	private boolean selling;

	public Fichaje(){}

	public Fichaje(Date date, int idUser, int idPlayer, boolean selling){
		this.date = date;
		this.idUser = idUser;
		this.idPlayer = idPlayer;
		this.selling = selling;
	}

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public int getIdUser() {
		return idUser;
	}

	public int getIdPlayer() {
		return idPlayer;
	}

	public boolean isSelling() {
		return selling;
	}

	public boolean update() {
		try {
			SessionFactory sesion = HibernateUtil.getSessionFactory();
			Session session = sesion.openSession();
			Transaction tx = session.beginTransaction();
			this.date = new Date();
			session.update(this);
			tx.commit();
			session.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	public boolean update(String namePlayer) {
		try {
			SessionFactory sesion = HibernateUtil.getSessionFactory();
			Session session = sesion.openSession();
			session.beginTransaction();

			Jugador j = (Jugador) (session.createQuery("from Jugador where name ='" + namePlayer + "'").list().get(0));
			Fichaje f = (Fichaje) (session.createQuery("from Fichaje where idPlayer =" + j.getId()).list().get(0));

			this.id = f.getId();
			this.date = f.getDate();
			this.idUser = f.getIdUser();
			this.idPlayer = f.getIdPlayer();
			this.selling = f.isSelling();

			session.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	public boolean inSell(Jugador player) {
		try {
			SessionFactory sesion = HibernateUtil.getSessionFactory();
			Session session = sesion.openSession();
			session.beginTransaction();
			Fichaje f = (Fichaje) (session.createQuery("from Fichaje where idPlayer ='" + player.getId() + "'").list().get(0));

			session.close();
			return f.isSelling();
		}catch(Exception e) {
			return false;
		}
	}

	public boolean tradeUp() {
		try {
			SessionFactory sesion = HibernateUtil.getSessionFactory();
			Session session = sesion.openSession();
			Transaction tx = session.beginTransaction();
			this.selling = true;
			session.update(this);

			Jugador player = (Jugador) (session.createQuery("from Jugador where id =" + idPlayer).list().get(0));
			Usuario owner = (Usuario) (session.createQuery("from Usuario where id =" + idUser).list().get(0));

			Transaccion trans = new Transaccion("El usuario '" + owner.getNombreUsuario() + "' ha puesto en venta a '" + player.getName() + "' por " + player.getPrice() + "e.");
			trans.createTransaccion();

			tx.commit();
			session.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	public boolean realize(Usuario buyer) {
		try {
			SessionFactory sesion = HibernateUtil.getSessionFactory();
			Session session = sesion.openSession();
			Transaction tx = session.beginTransaction();

			Jugador player = (Jugador) (session.createQuery("from Jugador where id =" + idPlayer).list().get(0));
			Usuario owner = (Usuario) (session.createQuery("from Usuario where id =" + idUser).list().get(0));

			if(buyer.getMoney() - player.getPrice() >= 0) {
				buyer.setMoney(buyer.getMoney() - player.getPrice());
				owner.setMoney(owner.getMoney() + player.getPrice());
			}else {
				return false;
			}

			this.selling = false;
			this.idUser = buyer.getCodigo();
			this.date = new Date();

			owner.updateUser();
			buyer.updateUser();

			session.update(this);
			tx.commit();

			Transaccion trans = new Transaccion("El usuario '" + buyer.getNombreUsuario() + "' ha comprado '" + player.getName() + "'.");
			trans.createTransaccion();

			session.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Jugador> getOwnedPlayers(Usuario user) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		session.beginTransaction();

		List<Integer> ids = session.createQuery("select idPlayer from Fichaje where idUser = " + user.getCodigo()).list();
		List<Jugador> rs = session.createQuery("from Jugador").list();
		List<Jugador> result = new ArrayList<Jugador>();

		for (Iterator<Integer> iterator = ids.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();

			for (Iterator<Jugador> iterator2 = rs.iterator(); iterator2.hasNext();) {
				Jugador jugador = (Jugador) iterator2.next();

				if(jugador.getId() == integer)
					result.add( jugador );
			}
		}	

		/*
		 * FORMA MENOS EFICIENTE
		 */

		//List<Integer> ids = session.createQuery("select idPlayer from Fichaje where idUser = " + user.getCodigo()).list();
		//List<Jugador> result = new ArrayList<Jugador>();
		//
		//for (Iterator<Integer> iterator = ids.iterator(); iterator.hasNext();) {
		//	Integer integer = (Integer) iterator.next();
		//	result.add( (Jugador) (session.createQuery("from Jugador where id =" + integer).list().get(0)) );
		//}

		session.close();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Jugador> getNotOwnedPlayers(Usuario user) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		session.beginTransaction();

		List<Integer> ids = session.createQuery("select idPlayer from Fichaje where idUser != " + user.getCodigo() + " and selling = 1").list();
		List<Jugador> rs = session.createQuery("from Jugador").list();
		List<Jugador> result = new ArrayList<Jugador>();

		for (Iterator<Integer> iterator = ids.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();

			for (Iterator<Jugador> iterator2 = rs.iterator(); iterator2.hasNext();) {
				Jugador jugador = (Jugador) iterator2.next();

				if(jugador.getId() == integer)
					result.add( jugador );
			}
		}		

		session.close();
		return result;
	}

}
