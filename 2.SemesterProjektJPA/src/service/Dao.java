package service;

import java.util.ArrayList;
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

public class Dao {
	private static Dao jpaDao;
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Mellemvarelager");
	private static EntityManager em = emf.createEntityManager();
	private static EntityTransaction tx = em.getTransaction();
	
	private static Mellemvarelager mellemvarelager = Mellemvarelager.getInstance("Lager1");
	private static List<Behandling> behandlinger = new ArrayList<Behandling>();
	private static List<Delbehandling> delbehandlinger = new ArrayList<Delbehandling>();
	private static List<Toerretid> toerretider = new ArrayList<Toerretid>();
	private static List<Produkttype> produkttyper = new ArrayList<Produkttype>();
	
//	public static void main(String[] args) {
//		Delbehandling d = new Delbehandling("ChokoladeOvertraek", 1, 2, 3);
//		getDao().storeDelbehandling(d);
//
//		Behandling b = new Behandling("Chokolade");
//		getDao().storeBehandling(b);
//		b.addDelbehandling(d);
//		
//		Produkttype p = new Produkttype("Skumbanan", b);
//		getDao().storeProdukttype(p);
//
//		Mellemvare m = new Mellemvare("Mellemvare1", p, 0);
//		getDao().storeMellemvare(m);
//		
//		List<Mellemvare> list = getDao().getAllMellemvarer();
//		for(Mellemvare mellemvare : list){
//			System.out.println("Mellemvare: " + mellemvare );
//			System.out.println("Produkttype: " + mellemvare.getProdukttype());
//			System.out.println("Behandling: " + mellemvare.getProdukttype().getBehandling());
//			for(Toerretid t : mellemvare.getToerretider()){
//				System.out.println("T¿rretid: " + t);
//			}
//			for(Delbehandling delbehandling : mellemvare.getProdukttype().getBehandling().getDelbehandlinger()){
//				System.out.println("Delbehandling: " + delbehandling);
//			}
//		}
//	}
	
	public static int updateDB(){
		int counter = 0;
		for(Delbehandling d : delbehandlinger){
			Dao.storeDelbehandling(d);
			counter++;
		}
		for(Behandling b : behandlinger){
			Dao.storeBehandling(b);
			counter++;
		}
		for(Produkttype p : produkttyper){
			Dao.storeProdukttype(p);
			counter++;
		}
		for(Mellemvare m : mellemvarelager.getMellemvarer()){
			Dao.storeMellemvare(m);
			counter++;
		}
		return counter;
	}
	
	public static void loadFromDb(){
		delbehandlinger = getAllDelbehandlinger();
		behandlinger = getAllBehandlinger();
		produkttyper = getAllProdukttyper();
		toerretider = getAllToerretider();
		mellemvarelager.setMellemvarer(getAllMellemvarer());
	}
	
	
	public static Dao getDao(){
		if(jpaDao == null){
			jpaDao = new Dao();
		}
		return jpaDao;
	}
	
	private Dao(){
		//singleton
	}
	
	public static void storeMellemvare(Mellemvare m){
		tx.begin();
		em.persist(m);
		tx.commit();
	}
	
	public static void removeMellemvareFromDb(Mellemvare m){
		tx.begin();
		em.remove(m);
		tx.commit();
	}
	
	public static void storeDelbehandling(Delbehandling d){
		tx.begin();
		em.persist(d);
		tx.commit();
	}
	
	public static void removeDelbehandlingFromDb(Delbehandling d){
		tx.begin();
		em.remove(d);
		tx.commit();
	}
	
	public static void storeBehandling(Behandling b){
		tx.begin();
		em.persist(b);
		tx.commit();
	}
	
	public static void removeBehandlingFromDb(Behandling b){
		tx.begin();
		em.remove(b);
		tx.commit();
	}
	
	public static void storeProdukttype(Produkttype p){
		tx.begin();
		em.persist(p);
		tx.commit();
	}
	
	public static void removeProdukttypeFromDb(Produkttype p){
		tx.begin();
		em.remove(p);
		tx.commit();
	}
	
	public static void removeToerretidFromDb(Toerretid t){
		tx.begin();
		em.remove(t);
		tx.commit();
	}
		
	public static void dropDb(){
		for(Mellemvare m : getAllMellemvarer()){
			removeMellemvareFromDb(m);
		}
		for(Toerretid t : getAllToerretider()){
			removeToerretidFromDb(t);
		}
		for(Produkttype p : getAllProdukttyper()){
			removeProdukttypeFromDb(p);
		}
		for(Behandling b : getAllBehandlinger()){
			removeBehandlingFromDb(b);
		}
		for(Delbehandling d : getAllDelbehandlinger()){
			removeDelbehandlingFromDb(d);
		}
	}
	
	public static List<Mellemvare> getAllMellemvarer() {
		return em.createQuery("SELECT m FROM Mellemvare m", Mellemvare.class).getResultList();
	}
	
	public static List<Delbehandling> getAllDelbehandlinger(){
		return em.createQuery("SELECT d FROM Delbehandling d", Delbehandling.class).getResultList();
	}
	
	public static List<Behandling> getAllBehandlinger(){
		return em.createQuery("SELECT b FROM Behandling b", Behandling.class).getResultList();
	}
	
	public static List<Produkttype> getAllProdukttyper(){
		return em.createQuery("SELECT p FROM Produkttype p", Produkttype.class).getResultList();
	}
	
	public static List<Toerretid> getAllToerretider(){
		return em.createQuery("SELECT t FROM Toerretid t", Toerretid.class).getResultList();
	}
	
	public static Mellemvarelager getMellemvarelager() {
		return mellemvarelager;
	}

	public static ArrayList<Behandling> getBehandlinger() {
		return new ArrayList<Behandling>(behandlinger);
	}

	public static ArrayList<Delbehandling> getDelbehandlinger() {
		return new ArrayList<Delbehandling>(delbehandlinger);
	}

	public static ArrayList<Toerretid> getToerretider() {
		return new ArrayList<Toerretid>(toerretider);
	}

	public static ArrayList<Produkttype> getProdukttyper() {
		return new ArrayList<Produkttype>(produkttyper);
	}

	public static void addBehandling(Behandling behandling) {
		if (!behandlinger.contains(behandling)) {
			behandlinger.add(behandling);
		}
	}

	public static void addToerretid(Toerretid toerretid) {
		if (!toerretider.contains(toerretid)) {
			toerretider.add(toerretid);
		}
	}

	public static void addProdukttype(Produkttype produkttype) {
		if (!produkttyper.contains(produkttype)) {
			produkttyper.add(produkttype);
		}
	}

	public static void addDelbehandling(Delbehandling delbehandling) {
		if (!delbehandlinger.contains(delbehandling)) {
			delbehandlinger.add(delbehandling);
		}
	}

	public static void removeBehandling(Behandling behandling) {
		if (behandlinger.contains(behandling)) {
			behandlinger.remove(behandling);
		}
	}

	public static void removeToerretid(Toerretid toerretid) {
		if (toerretider.contains(toerretid)) {
			toerretider.remove(toerretid);
		}
	}

	public static void removeProdukttype(Produkttype produkttype) {
		if (produkttyper.contains(produkttype)) {
			produkttyper.remove(produkttype);
		}
	}

	public static void removeDelbehandling(Delbehandling delbehandling) {
		if (delbehandlinger.contains(delbehandling)) {
			delbehandlinger.remove(delbehandling);
		}
	}
}
