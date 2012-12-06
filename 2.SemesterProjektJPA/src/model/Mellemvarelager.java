package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import service.Service;

@Entity
public class Mellemvarelager { //One-to-many Unidirectional
	@Id
	private String navn;
	private static Mellemvarelager uniqueInstance;
	@OneToMany
	private List<Mellemvare> mellemvarer = new ArrayList<Mellemvare>();
	private List<Mellemvare> faerdigeMellemvarer = new ArrayList<Mellemvare>();
	private List<Mellemvare> forGamleMellemvarer = new ArrayList<Mellemvare>();
	private int dage = 0;

	/**
	 * Private kontruktor for mellemvarelager
	 * @param navn navnet p� mellemvarelageret
	 */
	private Mellemvarelager(String navn) {
		this.navn = navn;
	}
	
	/**
	 * N�dvendig kontruktor for JPA
	 */
	public Mellemvarelager(){
	
	}

	/**
	 * Singleton getter for mellemvarelageret
	 * @param newnavn navnet p� mellemvarelaget
	 * @return Mellemvarelageret
	 */
	public static Mellemvarelager getInstance(String newnavn) {
		if (uniqueInstance == null) {
			uniqueInstance = new Mellemvarelager(newnavn);
		}
		return uniqueInstance;
	}

	/**
	 * Returnerer en oversigt over kritiske mellemvarer, alts� mellemvarer
	 * der har lagt til t�rre i l�ngere eller samme tid som deres p�g�ldene delbehandlings
	 * idealt�rretid og mindre eller samme tid som deres p�g�ldenes delbehandlings
	 * maxt�rretid.
	 * @return En ArrayList<Mellemvare> med alle kritiske mellemvarer.
	 */
	public ArrayList<Mellemvare> getOversigtOverKritiskeMellemvarer() {
		ArrayList<Mellemvare> kritiskeMellemvarer = new ArrayList<Mellemvare>();
		for (Mellemvare m : mellemvarer) {
			if (m.getStatus().equals(Status.TILTOERRING)) {
				if (m.getNaesteDelbehandling().getIdealToerreTid() <= getDageTilToerreSidenSidsteDelbehandling(m)
						&& m.getNaesteDelbehandling().getMaxToerreTid() >= getDageTilToerreSidenSidsteDelbehandling(m)) {
					kritiskeMellemvarer.add(m);
				}
			}
		}

		return kritiskeMellemvarer;
	}

	/**
	 * @return En ArrayList<Mellemvare> med alle f�rdige mellemvarer.
	 */
	public ArrayList<Mellemvare> getFaerdigeMellemvarer() {
		return new ArrayList<Mellemvare>(faerdigeMellemvarer);
	}

	/**
	 * En metode til at finde det antal dage en mellemvare har lagt til t�rring
	 * siden den fik sin sidste delbehandling.
	 * @param m den mellemvare man vil have tiden p�
	 * @return antal dage mellemvaren har lagt p� lager siden sidste delbehandling
	 */
	public int getDageTilToerreSidenSidsteDelbehandling(Mellemvare m) {
		return dage
				- m.getToerretider().get(m.getToerretider().size() - 1)
						.getTid();
	}

	/**
	 * En metode til at finde den n�ste mellemvare som er klar til behandling.
	 * Denne findes ved at prioritere mellemvarene efter deres nuv�rende delbehandlings
	 * min, ideal og max tid. S� de varer der er igang med en delbehandling som er
	 * t�ttest p� at blive for gammel bliver returneret f�rst.
	 * @return N�ste mellemvare klar til behandling prioriteret efter
	 *         mellemvarens delbehandlingers min, ideal og max t�rretider.
	 * @return Null hvis der ikke findes en mellemvare der er klar til en ny
	 *         behandling.
	 */
	public Mellemvare getNaesteMellemvareTilBehandling() {
		Mellemvare resultMellemvare = null;
		int max = 0;
		for (Mellemvare m : getMellemvarerMedMaxToerretid()) {
			if (m.getSidsteDelbehandling().getMaxToerreTid() > max) {
				max = m.getSidsteDelbehandling().getMaxToerreTid();
				resultMellemvare = m;
			}
		}
		int ideal = 0;
		if (resultMellemvare == null) { // Hvis der ikke er nogen mellemvarer
										// med max t�rretid
			for (Mellemvare m : getMellemvarerMedIdealToerretid()) {
				if (m.getSidsteDelbehandling().getIdealToerreTid() > ideal) {
					ideal = m.getSidsteDelbehandling().getIdealToerreTid();
					resultMellemvare = m;
				}
			}
		}
		int min = 0;
		if (resultMellemvare == null) { // Hvis der ikke er nogen mellemvarer
										// med ideal t�rretid
			for (Mellemvare m : getMellemvarerMedMinToerretid()) {
				if (m.getSidsteDelbehandling().getMinToerreTid() > min) {
					min = m.getSidsteDelbehandling().getMinToerreTid();
					resultMellemvare = m;
				}
			}
		}
		if (resultMellemvare != null
				&& resultMellemvare.getNaesteDelbehandling() == null) { // Mellemvaren
																		// har
																		// v�ret
																		// igennem
																		// alle
																		// delbehandlinger
			flytTilFaerdigvare(resultMellemvare);
		} else if (resultMellemvare != null) { // Mellemvaren har flere
												// delbehandlinger og den er nu
												// igang med at t�rre igen
			resultMellemvare.createToerretid(dage);
		}
		return resultMellemvare;
	}

	/**
	 * Hj�lpemetode til at finde en liste over alle mellemvarer som er igang med en delbehandling
	 * der er p� dens maksimale t�rretid lige nu.
	 * @return En ArrayList<Mellemvare> som indeholder alle de mellemvarer der har maksimal-
	 * t�rretid p� deres nuv�rende delbehandling.
	 */
	private ArrayList<Mellemvare> getMellemvarerMedMaxToerretid() {
		ArrayList<Mellemvare> resultList = new ArrayList<Mellemvare>();
		for (Mellemvare m : mellemvarer) {
			if (m.getSidsteDelbehandling().getMaxToerreTid() == getDageTilToerreSidenSidsteDelbehandling(m)) {
				resultList.add(m);
			}
		}
		return resultList;
	}
	
	/**
	 * Hj�lpemetode til at finde en liste over alle mellemvarer som er igang med en delbehandling
	 * der er p� ideal t�rretid lige nu.
	 * @return En ArrayList<Mellemvare> som indeholder alle de mellemvarer hvor idealt�rretiden <= 
	 * den nuv�rende tid mellemvare har lagt til t�rring og maxt�rretiden > den nuv�rende tid 
	 * mellemvaren har lagt til t�rring.
	 */
	private ArrayList<Mellemvare> getMellemvarerMedIdealToerretid() {
		ArrayList<Mellemvare> resultList = new ArrayList<Mellemvare>();
		for (Mellemvare m : mellemvarer) {
			if (m.getSidsteDelbehandling().getIdealToerreTid() <= getDageTilToerreSidenSidsteDelbehandling(m)
					&& m.getSidsteDelbehandling().getMaxToerreTid() > getDageTilToerreSidenSidsteDelbehandling(m)) {
				resultList.add(m);
			}
		}
		return resultList;
	}

	/**
	 * Hj�lpemetode til at finde en liste over alle mellemvarer som er igang med en delbehandling
	 * der er p� minimum t�rretid lige nu.
	 * @return En ArrayList<Mellemvare> som indeholder alle de mellemvarer hvor minimum-t�rretiden <= 
	 * den nuv�rende tid mellemvare har lagt til t�rring og ideal-t�rretiden > den nuv�rende tid 
	 * mellemvaren har lagt til t�rring.
	 */
	private ArrayList<Mellemvare> getMellemvarerMedMinToerretid() {
		ArrayList<Mellemvare> resultList = new ArrayList<Mellemvare>();
		for (Mellemvare m : mellemvarer) {
			if (m.getSidsteDelbehandling().getMinToerreTid() <= getDageTilToerreSidenSidsteDelbehandling(m)
					&& m.getSidsteDelbehandling().getIdealToerreTid() > getDageTilToerreSidenSidsteDelbehandling(m)) {
				resultList.add(m);
			}
		}
		return resultList;
	}

	/**
	 * st�tte metode til at flytte en mellemvare til f�rdigvare
	 * @param m den mellemvarse man �nsker at flytte
	 */
	private void flytTilFaerdigvare(Mellemvare m) {
		mellemvarer.remove(m);
		faerdigeMellemvarer.add(m);
		m.setStatus(Status.FAERDIG);
	}

	/**
	 * @return En List<Mellemvare> som er en kopi af alle mellemvarer.
	 */
	public List<Mellemvare> getMellemvarer() {
		return new ArrayList<Mellemvare>(mellemvarer);
	}

	/**
	 * En metode til at tilf�je en mellemvare til mellemvarer listen.
	 * Metoden g�r ikke noget hvis den mellemvare man fors�ger at tilf�je
	 * allerde findes i mellemvarer listen.
	 * @param mellemvare den mellemvare man vil tilf�je
	 */
	public void addMellemvare(Mellemvare mellemvare) {
		if (!mellemvarer.contains(mellemvare)) {
			mellemvarer.add(mellemvare);
		}
	}

	/**
	 * En metode til at fjerne en mellemvare fra mellemvarer listen.
	 * Metoden g�r ikke noget hvis den mellemvare man fors�ger at fjerne
	 * ikke findes i listen.
	 * @param mellemvare den mellemvare man vil fjerne
	 */
	public void removeMellemvare(Mellemvare mellemvare) {
		if (mellemvarer.contains(mellemvare)) {
			mellemvarer.remove(mellemvare);
		}
	}

	/**
	 * @param m den mellemvare man vil finde placeringen p�
	 * @return en int der fort�ller hvilken placering mellemvaren har.
	 */
	public int getPlacering(Mellemvare m) {
		return mellemvarer.indexOf(m);
	}

	/**
	 * @return en int der fort�ller hvor mange dage lageret har v�ret i brug.
	 */
	public int getDage() {
		return dage;
	}

	/**
	 * @param dage det antal dage man vil s�tte lageret til at have v�ret aktivt i.
	 */
	public void setDage(int dage) {
		this.dage = dage;
	}

	/**
	 * Opdaterer mellemvarelagerets lagerbeholdning af mellemvarer, til at matche de
	 * mellemvarer der findes i DAO'en.
	 */
	public void updateLagerBeholdning() {
		mellemvarer = Service.getMellemvarer();
	}

	/**
	 * Fjerner alle varer fra mellemvarelageret.
	 */
	public void clearLager() {
		mellemvarer.clear();
	}
	
	/**
	 * @param allMellemvarer s�tter mellemvarelagerets mellemvare liste til at v�re den medgivne liste.
	 */
	public void setMellemvarer(List<Mellemvare> allMellemvarer) {
		mellemvarer = allMellemvarer;
	}
	
	
	/**
	 * @return en List<Mellemvare> med alle de mellemvarer som er for gamle.
	 */
	public List<Mellemvare> getForGamleMellemvareList() {
		return forGamleMellemvarer;
	}
	
	/**
	 * @param nyList s�tter forGamleMellemvarer til at v�re den medgivne liste.
	 */
	public void setForGamleMellemvareList(ArrayList<Mellemvare> nyList){
		forGamleMellemvarer = nyList;
	}

	/**
	 * @return en int der fort�ller hvor mange mellemvare som er blevet for gamle i
	 * al den tid mellemvarelageret har eksisteret.
	 */
	public int getStatistikOverForGamleMellemvarer(){
		return forGamleMellemvarer.size();
	}

}
