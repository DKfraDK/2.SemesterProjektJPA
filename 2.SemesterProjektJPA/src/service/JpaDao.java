package service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import model.Behandling;
import model.Delbehandling;
import model.Mellemvare;
import model.Mellemvarelager;
import model.Produkttype;
import model.Toerretid;

public class JpaDao implements DaoInterface{

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Mellemvarelager");
	private EntityManager em = emf.createEntityManager();
	private EntityTransaction tx = em.getTransaction();
	
	private Mellemvarelager mellemvarelager = Mellemvarelager.getInstance("Lager1");
	private static JpaDao jpaDao;

	public static JpaDao getDao(){
		if(jpaDao == null){
			jpaDao = new JpaDao();
		}
		return jpaDao;
	}
	
	private JpaDao(){
		//singleton
	}
	
	@Override
	public Mellemvarelager getMellemvarelager() {
		return mellemvarelager;
	}
	
	@Override
	public List<Mellemvare> getMellemvarer() {
		return em.createQuery("SELECT m FROM Mellemvare m", Mellemvare.class).getResultList();
	}

	@Override
	public List<Behandling> getBehandlinger() {
		return em.createQuery("SELECT b FROM Behandling b", Behandling.class).getResultList();
	}

	@Override
	public List<Delbehandling> getDelbehandlinger() {
		return em.createQuery("SELECT d FROM Delbehandling d", Delbehandling.class).getResultList();
	}

	@Override
	public List<Toerretid> getToerretider() {
		return em.createQuery("SELECT t FROM Toerretid t", Toerretid.class).getResultList();
	}

	@Override
	public List<Produkttype> getProdukttyper() {
		return em.createQuery("SELECT p FROM Produkttype p", Produkttype.class).getResultList();
	}

	@Override
	public void addBehandling(Behandling behandling) {
		tx.begin();
		em.persist(behandling);
		tx.commit();
	}
	
	@Override
	public void addMellemvare(Mellemvare mellemvare){
		tx.begin();
		em.persist(mellemvare);
		tx.commit();
	}

	@Override
	public void addToerretid(Toerretid toerretid) {
		tx.begin();
		em.persist(toerretid);
		tx.commit();		
	}

	@Override
	public void addProdukttype(Produkttype produkttype) {
		tx.begin();
		em.persist(produkttype);
		tx.commit();		
	}

	@Override
	public void addDelbehandling(Delbehandling delbehandling) {
		tx.begin();
		em.persist(delbehandling);
		tx.commit();		
	}

	@Override
	public void removeBehandling(Behandling behandling) {
		tx.begin();
		em.remove(behandling);
		tx.commit();		
	}

	@Override
	public void removeToerretid(Toerretid toerretid) {
		tx.begin();
		em.remove(toerretid);
		tx.commit();		
	}

	@Override
	public void removeProdukttype(Produkttype produkttype) {
		tx.begin();
		em.remove(produkttype);
		tx.commit();			
	}

	@Override
	public void removeDelbehandling(Delbehandling delbehandling) {
		tx.begin();
		em.remove(delbehandling);
		tx.commit();			
	}

	@Override
	public void removeMellemvare(Mellemvare mellemvare) {
		tx.begin();
		em.remove(mellemvare);
		tx.commit();
	}

}
