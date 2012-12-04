package service;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import model.Behandling;
import model.Delbehandling;
import model.Mellemvare;
import model.Produkttype;

public class JpaDAO {
	private static JpaDAO jpaDao;
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Mellemvarelager");
	private EntityManager em = emf.createEntityManager();
	private EntityTransaction tx = em.getTransaction();
	
	public static void main(String[] args) {
		Delbehandling d = new Delbehandling("peter", 1, 2, 3);
		Behandling b = new Behandling("Lars");
		b.addDelbehandling(d);
		Produkttype p = new Produkttype("LarsLarsen", b);
		Mellemvare m = new Mellemvare("Hej", p, 0);
		getDao().storeMellemvare(m);
		System.out.println(getDao().getAllMellemvarer());
	}
	
	
	public static JpaDAO getDao(){
		if(jpaDao == null){
			jpaDao = new JpaDAO();
		}
		return jpaDao;
	}
	
	private JpaDAO(){
		//singleton
	}
	
	public void storeMellemvare(Mellemvare m){
		tx.begin();
		em.persist(m);
		tx.commit();
	}
	
	public ArrayList<Mellemvare> getAllMellemvarer() {
		   return (ArrayList<Mellemvare>) em.createQuery("SELECT m FROM Mellemvare m", Mellemvare.class).getResultList();
		}
}
