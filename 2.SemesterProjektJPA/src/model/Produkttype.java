package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Produkttype {
	@Id
	private String navn;
	@ManyToOne
	private Behandling behandling= null;
	
	
	public Produkttype(String navn, Behandling behandling){
		this.behandling = behandling;
		this.navn=navn;
		
	}
	public Produkttype(){
		
	}
	public void setNavn(String navn){
		this.navn=navn;
	}
	public Behandling getBehandling(){
		return behandling;
	}
	
	public void setBehandling(Behandling behandling){
		if(this.behandling!=behandling){
			this.behandling=behandling;
		}
	}
	
	
	public String toString(){
		return navn;
	}
}
