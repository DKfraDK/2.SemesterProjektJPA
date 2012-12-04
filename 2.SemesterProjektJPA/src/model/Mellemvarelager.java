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
	private List<String> placeringer = new ArrayList<String>();
	@OneToMany
	private List<Mellemvare> mellemvarer = new ArrayList<Mellemvare>();
	private List<Mellemvare> faerdigeMellemvarer = new ArrayList<Mellemvare>();

	private int dage = 0;

	private Mellemvarelager(String navn) {
		this.navn = navn;
	}
	public Mellemvarelager(){
	
	}

	public static Mellemvarelager getInstance(String newnavn) {
		if (uniqueInstance == null) {
			uniqueInstance = new Mellemvarelager(newnavn);
		}
		return uniqueInstance;
	}

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

	public ArrayList<Mellemvare> getFaerdigeMellemvarer() {
		return new ArrayList<Mellemvare>(faerdigeMellemvarer);
	}

	public int getDageTilToerreSidenSidsteDelbehandling(Mellemvare m) {
		return dage
				- m.getToerretider().get(m.getToerretider().size() - 1)
						.getTid();
	}

	/**
	 * @return N¾ste mellemvare klar til behandling prioriteret efter
	 *         mellemvarens delbehandlingers min, ideal og max t¿rretider.
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
										// med max t¿rretid
			for (Mellemvare m : getMellemvarerMedIdealToerretid()) {
				if (m.getSidsteDelbehandling().getIdealToerreTid() > ideal) {
					ideal = m.getSidsteDelbehandling().getIdealToerreTid();
					resultMellemvare = m;
				}
			}
		}
		int min = 0;
		if (resultMellemvare == null) { // Hvis der ikke er nogen mellemvarer
										// med ideal t¿rretid
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
																		// v¾ret
																		// igennem
																		// alle
																		// delbehandlinger
			flytTilFaerdigvare(resultMellemvare);
		} else if (resultMellemvare != null) { // Mellemvaren har flere
												// delbehandlinger og den er nu
												// igang med at t¿rre igen
			resultMellemvare.createToerretid(dage);
		}
		return resultMellemvare;
	}

	private ArrayList<Mellemvare> getMellemvarerMedMaxToerretid() {
		ArrayList<Mellemvare> resultList = new ArrayList<Mellemvare>();
		for (Mellemvare m : mellemvarer) {
			if (m.getSidsteDelbehandling().getMaxToerreTid() == getDageTilToerreSidenSidsteDelbehandling(m)) {
				resultList.add(m);
			}
		}
		return resultList;
	}

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
	 * støtte metode til at flytte en mellemvare til færdigvare
	 * 
	 * @param m
	 *            den mellemvarse man ønsker at flytte
	 */
	private void flytTilFaerdigvare(Mellemvare m) {
		mellemvarer.remove(m);
		faerdigeMellemvarer.add(m);
		m.setStatus(Status.FAERDIG);
	}

	public List<Mellemvare> getMellemvarer() {
		return new ArrayList<Mellemvare>(mellemvarer);
	}

	public void addMellemvare(Mellemvare mellemvare) {
		if (!mellemvarer.contains(mellemvare)) {
			mellemvarer.add(mellemvare);
		}
	}

	public void removeMellemvare(Mellemvare mellemvare) {
		if (mellemvarer.contains(mellemvare)) {
			mellemvarer.remove(mellemvare);
		}
	}

	public int getPlacering(Mellemvare m) {
		return placeringer.indexOf(m);

	}

	public int getDage() {
		return dage;
	}

	public void setDage(int dage) {
		this.dage = dage;
	}

	public void updateLagerBeholdning() {
		mellemvarer = Service.getMellemvarer();
	}

	public ArrayList<Mellemvare> getForGamleMellemvarer() {
		ArrayList<Mellemvare> resultList = new ArrayList<Mellemvare>();
		for (Mellemvare m : mellemvarer) {
			if (m.getSidsteDelbehandling().getMaxToerreTid() < getDageTilToerreSidenSidsteDelbehandling(m)) {
				resultList.add(m);
			}
		}
		return resultList;
	}

	public void clearLager() {
		mellemvarer.clear();
	}
	public void setMellemvarer(List<Mellemvare> allMellemvarer) {
		mellemvarer = allMellemvarer;
	}

}
