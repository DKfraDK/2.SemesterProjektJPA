package service;

import java.util.ArrayList;
import java.util.List;

import model.Behandling;
import model.Delbehandling;
import model.Mellemvare;
import model.Mellemvarelager;
import model.Produkttype;
import model.Toerretid;

public class Dao implements DaoInterface{
	
	private static Dao dao;
	
	private static Mellemvarelager mellemvarelager = Mellemvarelager
			.getInstance("Lager1");
	private static ArrayList<Behandling> behandlinger = new ArrayList<Behandling>();
	private static ArrayList<Delbehandling> delbehandlinger = new ArrayList<Delbehandling>();
	private static ArrayList<Toerretid> toerretider = new ArrayList<Toerretid>();
	private static ArrayList<Produkttype> produkttyper = new ArrayList<Produkttype>();
	private static ArrayList<Mellemvare> mellemvarer = new ArrayList<Mellemvare>();

	public static Dao getDao(){
		if(dao == null){
			dao = new Dao();
		}
		return dao;
	}
	
	private Dao(){
		//Singleton
	}
	
	public Mellemvarelager getMellemvarelager() {
		return mellemvarelager;
	}
	
	@Override
	public List<Mellemvare> getMellemvarer() {
		return new ArrayList<Mellemvare>(mellemvarer);
	}

	public ArrayList<Behandling> getBehandlinger() {
		return new ArrayList<Behandling>(behandlinger);
	}

	public ArrayList<Delbehandling> getDelbehandlinger() {
		return new ArrayList<Delbehandling>(delbehandlinger);
	}

	public ArrayList<Toerretid> getToerretider() {
		return new ArrayList<Toerretid>(toerretider);
	}

	public ArrayList<Produkttype> getProdukttyper() {
		return new ArrayList<Produkttype>(produkttyper);
	}
	
	public void addMellemvare(Mellemvare mellemvare) {
		if (!mellemvarer.contains(mellemvare)) {
			mellemvarer.add(mellemvare);
		}
	}

	public void addBehandling(Behandling behandling) {
		if (!behandlinger.contains(behandling)) {
			behandlinger.add(behandling);
		}
	}

	public void addToerretid(Toerretid toerretid) {
		if (!toerretider.contains(toerretid)) {
			toerretider.add(toerretid);
		}
	}

	public void addProdukttype(Produkttype produkttype) {
		if (!produkttyper.contains(produkttype)) {
			produkttyper.add(produkttype);
		}
	}

	public void addDelbehandling(Delbehandling delbehandling) {
		if (!delbehandlinger.contains(delbehandling)) {
			delbehandlinger.add(delbehandling);
		}
	}

	public void removeBehandling(Behandling behandling) {
		if (behandlinger.contains(behandling)) {
			behandlinger.remove(behandling);
		}
	}

	public void removeToerretid(Toerretid toerretid) {
		if (toerretider.contains(toerretid)) {
			toerretider.remove(toerretid);
		}
	}

	public void removeProdukttype(Produkttype produkttype) {
		if (produkttyper.contains(produkttype)) {
			produkttyper.remove(produkttype);
		}
	}

	public void removeDelbehandling(Delbehandling delbehandling) {
		if (delbehandlinger.contains(delbehandling)) {
			delbehandlinger.remove(delbehandling);
		}
	}

	@Override
	public void removeMellemvare(Mellemvare mellemvare) {
		if(mellemvarer.contains(mellemvare)){
			mellemvarer.remove(mellemvare);
		}
	}
}
