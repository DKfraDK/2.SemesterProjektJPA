package service;

import java.util.ArrayList;

import model.Behandling;
import model.Delbehandling;
import model.Mellemvare;
import model.Mellemvarelager;
import model.Produkttype;
import model.Toerretid;
import dao.Dao;
import dao.DaoInterface;

public class Service {
	
	private static DaoInterface dao = Dao.getDao(); //V¾lg her hvilken dao du vil benytte, enten JpaDao eller Dao.
	
	public static void nyDag() {
		int nyDag = dao.getMellemvarelager().getDage() + 1;
		dao.getMellemvarelager().setDage(nyDag);
		for (Mellemvare m : dao.getMellemvarelager().getMellemvarer()) {
			if (m.getSidsteDelbehandling().getMaxToerreTid() < dao
					.getMellemvarelager()
					.getDageTilToerreSidenSidsteDelbehandling(m)) {
				dao.getMellemvarelager().getForGamleMellemvareList().add(m);
			}
		}
		for(Mellemvare m : dao.getMellemvarelager().getForGamleMellemvareList()){
			dao.removeMellemvare(m);
		}
		dao.getMellemvarelager().updateLagerBeholdning();

	}

	public static int getDag(){
		return dao.getMellemvarelager().getDage();
	}
	
	public static void createSomeObjects() {
		Mellemvarelager lager = Mellemvarelager.getInstance("lager 1");
		lager.setDage(2);

		Delbehandling dSkum = createDelbehandling("Skum", 1, 2, 5);
		Delbehandling dChoko = createDelbehandling("ChokoladeOvertraek", 2, 3,
				4);
		Delbehandling dDragee = createDelbehandling("Drageering", 3, 4, 12);
		Delbehandling dChokoYderst = createDelbehandling(
				"ChokoladeOvertraek yderst", 2, 3, 5);
		Delbehandling dHvile = createDelbehandling("Hvile periode", 1, 2, 8);
		Behandling bChokoSkum = createBehandling("Chokolade Skum");
		addDelbehandlingTilBehandling(bChokoSkum, dSkum);
		addDelbehandlingTilBehandling(bChokoSkum, dChoko);
		addDelbehandlingTilBehandling(bChokoSkum, dHvile);
		addDelbehandlingTilBehandling(bChokoSkum, dChokoYderst);
		Behandling bLakrids = createBehandling("Lakrids knas");
		addDelbehandlingTilBehandling(bLakrids, dHvile);
		addDelbehandlingTilBehandling(bLakrids, dDragee);
		Behandling bChokoLakrids = createBehandling("Chokalde Lakrids");
		addDelbehandlingTilBehandling(bChokoLakrids, dChoko);
		addDelbehandlingTilBehandling(bChokoLakrids, dDragee);
		addDelbehandlingTilBehandling(bChokoLakrids, dHvile);
		addDelbehandlingTilBehandling(bChokoLakrids, dChokoYderst);
		Produkttype pSkumBanan = createProdukttype("SkumBanan", bChokoSkum);
		Produkttype pPTaerter = createProdukttype("PTaerter", bChokoSkum);
		Produkttype pLakridsPinde = createProdukttype("LakridsPinde", bLakrids);
		Produkttype pChokoladeLakrids = createProdukttype(
				"ChokoladeOvertrukketLakrids", bChokoLakrids);
		Mellemvare m1 = createMellemvare("Vare1", pSkumBanan, 1);
		Mellemvare m2 = createMellemvare("Vare2", pSkumBanan, 1);
		Mellemvare m3 = createMellemvare("Vare3", pSkumBanan, 2);

		Mellemvare m4 = createMellemvare("Vare4", pPTaerter, 1);
		Mellemvare m5 = createMellemvare("vare5", pPTaerter, 2);

		Mellemvare m6 = createMellemvare("vare6", pLakridsPinde, 4);

		Mellemvare m7 = createMellemvare("vare7", pChokoladeLakrids, 3);
		
		Service.getMellemvarelager().updateLagerBeholdning();
	}

	public static Mellemvare createMellemvare(String id,
			Produkttype produkttype, int tid) {
		Mellemvare mellemvare = new Mellemvare(id, produkttype, tid);
		if (!dao.getMellemvarer().contains(mellemvare)) {
			dao.addMellemvare(mellemvare);
		}
		dao.getMellemvarelager().updateLagerBeholdning();
		return mellemvare;
	}

	public static void updateMellemvare(Mellemvare mellemvare, String id,
			Produkttype produkttype) {
		mellemvare.setId(id);
		mellemvare.setProdukttype(produkttype);
	}

	public static void deleteMellemvare(Mellemvare mellemvare) {
		for(Toerretid t : mellemvare.getToerretider()){
			mellemvare.deleteToerretid(t);
			dao.removeToerretid(t);
		}
		dao.removeMellemvare(mellemvare);
	}

	public static Produkttype createProdukttype(String navn,
			Behandling behandling) {
		Produkttype produkttype = new Produkttype(navn, behandling);
		if (!dao.getProdukttyper().contains(produkttype)) {
			dao.addProdukttype(produkttype);
		}
		return produkttype;
	}

	public static void updateProdukttype(Produkttype produkttype, String navn,
			Behandling behandling) {
		produkttype.setBehandling(behandling);
		produkttype.setNavn(navn);
	}

	public static boolean deleteProdukttype(Produkttype produkttype) {
		boolean found = false;
		for(Mellemvare m : dao.getMellemvarelager().getMellemvarer()){
			if(m.getProdukttype().equals(produkttype)){
				found = true;
			}
		}
		if(!found){
			dao.removeProdukttype(produkttype);
			return true;
		}else{
			return false;
		}
	}

	public static Behandling createBehandling(String navn) {
		Behandling behandling = new Behandling(navn);
		if (!dao.getBehandlinger().contains(behandling)) {
			dao.addBehandling(behandling);
		}
		return behandling;
	}

	public static void updateBehandling(Behandling behandling, String navn) {
		behandling.setNavn(navn);
	}

	public static void addDelbehandlingTilBehandling(Behandling behandling,
			Delbehandling delbehandling) {
		behandling.addDelbehandling(delbehandling);
	}

	public static boolean deleteBehandling(Behandling behandling) {
		boolean found = false;
		for(Produkttype p : dao.getProdukttyper()){
			if(p.getBehandling().equals(behandling)){
				found = true;
			}
		}
		if(!found){
			dao.removeBehandling(behandling);
			return true;
		}else{
			return false;
		}
	}

	public static Delbehandling createDelbehandling(String navn, int min,
			int ideal, int max) {
		Delbehandling delbehandling = new Delbehandling(navn, min, ideal, max);
		if (!dao.getDelbehandlinger().contains(delbehandling)) {
			dao.addDelbehandling(delbehandling);
		}
		return delbehandling;
	}

	public static void updateDelbehandling(Delbehandling delbehandling,
			String navn, int min, int ideal, int max) {
		delbehandling.setNavn(navn);
		delbehandling.setMinToerreTid(min);
		delbehandling.setIdealToerreTid(ideal);
		delbehandling.setMaxToerreTid(max);
	}

	public static boolean deleteDelBehandling(Delbehandling delbehandling) {
		boolean found = false;
		for(Behandling b : dao.getBehandlinger()){
			for(Delbehandling d : b.getDelbehandlinger()){
				if(d.equals(delbehandling)){
					found = true;
				}
			}
		}
		if(!found){
			dao.removeDelbehandling(delbehandling);
			return true;
		}else{
			return false;
		}	
	}

	public static ArrayList<Mellemvare> getMellemvarer() {
		return new ArrayList<Mellemvare>(dao.getMellemvarer());
	}

	public static ArrayList<Produkttype> getProdukttyper() {
		return new ArrayList<Produkttype>(dao.getProdukttyper());
	}

	public static ArrayList<Behandling> getBehandling() {
		return new ArrayList<Behandling>(dao.getBehandlinger());
	}

	public static ArrayList<Delbehandling> getDelbehandlinger() {
		return new ArrayList<Delbehandling>(dao.getDelbehandlinger());
	}
	
	public static Mellemvarelager getMellemvarelager(){
		return dao.getMellemvarelager();
	}
}