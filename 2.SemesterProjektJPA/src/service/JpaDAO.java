package service;

import java.util.List;

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
<<<<<<< HEAD
		Delbehandling d = new Delbehandling("peter", 1, 2, 3);
		Behandling b = new Behandling("Lars");
=======
		Delbehandling d = new Delbehandling("ChokoladeOvertraek", 1, 2, 3);
		Behandling b = new Behandling("Chokolade");
>>>>>>> branch 'master' of https://github.com/DKfraDK/2.SemesterProjektJPA.git
		b.addDelbehandling(d);
		Produkttype p = new Produkttype("Skumbanan", b);
		Mellemvare m = new Mellemvare("Mellemvare1", p, 0);
		getDao().storeMellemvare(m, d, b, p);
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
	
	public void storeMellemvare(Mellemvare m, Delbehandling d, Behandling b, Produkttype p){
		tx.begin();
		em.persist(m);
		em.persist(d);
		em.persist(b);
		em.persist(p);
		tx.commit();
	}
	
	public List<Mellemvare> getAllMellemvarer() {
		   return em.createQuery("SELECT m FROM Mellemvare m", Mellemvare.class).getResultList();
		}
}
