package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Delbehandling {
	@Id
	private String navn;
	private int maxToerreTid;
	private int idealToerreTid;
	private int minToerreTid;

	public Delbehandling(String navn, int minToerreTid, int idealToerreTid,
			int maxToerreTid) {
		this.navn = navn;
		this.maxToerreTid = maxToerreTid;
		this.idealToerreTid = idealToerreTid;
		this.minToerreTid = minToerreTid;
	}

	public Delbehandling() {

	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public int getMaxToerreTid() {
		return maxToerreTid;
	}

	public void setMaxToerreTid(int maxToerreTid) {
		this.maxToerreTid = maxToerreTid;
	}

	public int getIdealToerreTid() {
		return idealToerreTid;
	}

	public void setIdealToerreTid(int idealToerreTid) {
		this.idealToerreTid = idealToerreTid;
	}

	public int getMinToerreTid() {
		return minToerreTid;
	}

	public void setMinToerreTid(int minToerreTid) {
		this.minToerreTid = minToerreTid;
	}

	public String toString() {
		return navn;
	}

}
