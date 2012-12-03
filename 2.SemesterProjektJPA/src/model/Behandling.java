package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Behandling {
@Id
	private String navn;
	private List<Delbehandling> delbehandlinger = new ArrayList<Delbehandling>();
	public Behandling(String navn) {
		this.setNavn(navn);

	}
	public Behandling(){
		
	}

	public Delbehandling createDelbehandling(String navn, int Toerretid,
			int idealToerreTid, int minToerretid) {
		Delbehandling delbehandling = new Delbehandling(navn, Toerretid,
				idealToerreTid, minToerretid);
		delbehandlinger.add(delbehandling);
		return delbehandling;
	}

	public void addDelbehandling(Delbehandling delbehandling) {
		if (!delbehandlinger.contains(delbehandling)) {
			delbehandlinger.add(delbehandling);
		}

	}

	public void deleteDelbehandling(Delbehandling delbehandling) {
		if (delbehandlinger.contains(delbehandling)) {
			delbehandlinger.remove(delbehandling);
		}
	}
	
	public ArrayList<Delbehandling> getDelbehandlinger(){
		return new ArrayList<Delbehandling>(delbehandlinger);
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}
	
	public String toString(){
		return navn;
	}

}
