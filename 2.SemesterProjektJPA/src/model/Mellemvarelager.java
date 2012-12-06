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
	 * @param navn navnet på mellemvarelageret
	 */
	private Mellemvarelager(String navn) {
		this.navn = navn;
	}
	
	/**
	 * Nødvendig kontruktor for JPA
	 */
	public Mellemvarelager(){
	
	}

	/**
	 * Singleton getter for mellemvarelageret
	 * @param newnavn navnet på mellemvarelaget
	 * @return Mellemvarelageret
	 */
	public static Mellemvarelager getInstance(String newnavn) {
		if (uniqueInstance == null) {
			uniqueInstance = new Mellemvarelager(newnavn);
		}
		return uniqueInstance;
	}

	/**
	 * Returnerer en oversigt over kritiske mellemvarer, altså mellemvarer
	 * der har lagt til tørre i længere eller samme tid som deres pågældene delbehandlings
	 * idealtørretid og mindre eller samme tid som deres pågældenes delbehandlings
	 * maxtørretid.
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
	 * @return En ArrayList<Mellemvare> med alle færdige mellemvarer.
	 */
	public ArrayList<Mellemvare> getFaerdigeMellemvarer() {
		return new ArrayList<Mellemvare>(faerdigeMellemvarer);
	}

	/**
	 * En metode til at finde det antal dage en mellemvare har lagt til tørring
	 * siden den fik sin sidste delbehandling.
	 * @param m den mellemvare man vil have tiden på
	 * @return antal dage mellemvaren har lagt på lager siden sidste delbehandling
	 */
	public int getDageTilToerreSidenSidsteDelbehandling(Mellemvare m) {
		return dage
				- m.getToerretider().get(m.getToerretider().size() - 1)
						.getTid();
	}

	/**
	 * En metode til at finde den næste mellemvare som er klar til behandling.
	 * Denne findes ved at prioritere mellemvarene efter deres nuværende delbehandlings
	 * min, ideal og max tid. Så de varer der er igang med en delbehandling som er
	 * tættest på at blive for gammel bliver returneret først.
	 * @return Næste mellemvare klar til behandling prioriteret efter
	 *         mellemvarens delbehandlingers min, ideal og max tørretider.
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
										// med max tørretid
			for (Mellemvare m : getMellemvarerMedIdealToerretid()) {
				if (m.getSidsteDelbehandling().getIdealToerreTid() > ideal) {
					ideal = m.getSidsteDelbehandling().getIdealToerreTid();
					resultMellemvare = m;
				}
			}
		}
		int min = 0;
		if (resultMellemvare == null) { // Hvis der ikke er nogen mellemvarer
										// med ideal tørretid
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
																		// været
																		// igennem
																		// alle
																		// delbehandlinger
			flytTilFaerdigvare(resultMellemvare);
		} else if (resultMellemvare != null) { // Mellemvaren har flere
												// delbehandlinger og den er nu
												// igang med at tørre igen
			resultMellemvare.createToerretid(dage);
		}
		return resultMellemvare;
	}

	/**
	 * Hjælpemetode til at finde en liste over alle mellemvarer som er igang med en delbehandling
	 * der er på dens maksimale tørretid lige nu.
	 * @return En ArrayList<Mellemvare> som indeholder alle de mellemvarer der har maksimal-
	 * tørretid på deres nuværende delbehandling.
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
	 * Hjælpemetode til at finde en liste over alle mellemvarer som er igang med en delbehandling
	 * der er på ideal tørretid lige nu.
	 * @return En ArrayList<Mellemvare> som indeholder alle de mellemvarer hvor idealtørretiden <= 
	 * den nuværende tid mellemvare har lagt til tørring og maxtørretiden > den nuværende tid 
	 * mellemvaren har lagt til tørring.
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
	 * Hjælpemetode til at finde en liste over alle mellemvarer som er igang med en delbehandling
	 * der er på minimum tørretid lige nu.
	 * @return En ArrayList<Mellemvare> som indeholder alle de mellemvarer hvor minimum-tørretiden <= 
	 * den nuværende tid mellemvare har lagt til tørring og ideal-tørretiden > den nuværende tid 
	 * mellemvaren har lagt til tørring.
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
	 * st¯tte metode til at flytte en mellemvare til fÊrdigvare
	 * @param m den mellemvarse man ¯nsker at flytte
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
	 * En metode til at tilføje en mellemvare til mellemvarer listen.
	 * Metoden gør ikke noget hvis den mellemvare man forsøger at tilføje
	 * allerde findes i mellemvarer listen.
	 * @param mellemvare den mellemvare man vil tilføje
	 */
	public void addMellemvare(Mellemvare mellemvare) {
		if (!mellemvarer.contains(mellemvare)) {
			mellemvarer.add(mellemvare);
		}
	}

	/**
	 * En metode til at fjerne en mellemvare fra mellemvarer listen.
	 * Metoden gør ikke noget hvis den mellemvare man forsøger at fjerne
	 * ikke findes i listen.
	 * @param mellemvare den mellemvare man vil fjerne
	 */
	public void removeMellemvare(Mellemvare mellemvare) {
		if (mellemvarer.contains(mellemvare)) {
			mellemvarer.remove(mellemvare);
		}
	}

	/**
	 * @param m den mellemvare man vil finde placeringen på
	 * @return en int der fortæller hvilken placering mellemvaren har.
	 */
	public int getPlacering(Mellemvare m) {
		return mellemvarer.indexOf(m);
	}

	/**
	 * @return en int der fortæller hvor mange dage lageret har været i brug.
	 */
	public int getDage() {
		return dage;
	}

	/**
	 * @param dage det antal dage man vil sætte lageret til at have været aktivt i.
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
	 * @param allMellemvarer sætter mellemvarelagerets mellemvare liste til at være den medgivne liste.
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
	 * @param nyList sætter forGamleMellemvarer til at være den medgivne liste.
	 */
	public void setForGamleMellemvareList(ArrayList<Mellemvare> nyList){
		forGamleMellemvarer = nyList;
	}

	/**
	 * @return en int der fortæller hvor mange mellemvare som er blevet for gamle i
	 * al den tid mellemvarelageret har eksisteret.
	 */
	public int getStatistikOverForGamleMellemvarer(){
		return forGamleMellemvarer.size();
	}

}
