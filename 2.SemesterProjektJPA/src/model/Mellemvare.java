package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Mellemvare {
	@Id
	private String id;
	@Enumerated(EnumType.STRING)
	private Status status = null;
	@ManyToOne
	private Produkttype produkttype =null;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Toerretid> toerretider = new ArrayList<Toerretid>();
	
	public Mellemvare(String id, Produkttype produkttype, int tid){
		this.produkttype=produkttype;
		this.setId(id);
		createToerretid(tid);
		setStatus(Status.TILTOERRING);
	}
	public Mellemvare(){
		
	}
	public Produkttype getProdukttype(){
		return produkttype;
	}
	
	public void setProdukttype(Produkttype produkttype){
		if(this.produkttype!=produkttype){
			this.produkttype=produkttype;
		}
	}
	public ArrayList<Toerretid> getToerretider(){
		return new ArrayList<Toerretid>(toerretider);
	}
	
	/**
	 * 
	 * @param tid den dag tørretiden  blev oprettet. Dvs den dag mellemvaren for sin delbehandling
	 * @return tørretiden selv
	 * @throws RuntimeException kaster en exception hvis der ikke findes flere delbehandlinger på mellemvaren
	 */
	public Toerretid createToerretid(int tid){
		Delbehandling delbehandling = getNaesteDelbehandling();
		Toerretid toerretid = new Toerretid(tid, delbehandling);
		toerretider.add(toerretid);
		return toerretid;
	}
	public void deleteToerretid(Toerretid toerretid){
		if(toerretider.contains(toerretid)){
			toerretider.remove(toerretid);
		}
	}
	/**
	 * finder den næsten delbehandling for mellemvaren ved at gå til behandling over produkttype.
	 * @return retunere den næste delbehandling for mellemvarens tilknyttede behandling.
	 */
	public Delbehandling getNaesteDelbehandling(){
		int antalDelbehandlinger = getProdukttype().getBehandling().getDelbehandlinger().size();
		int antalToerretider = toerretider.size();
		Delbehandling delbehandling = null;
		if( antalDelbehandlinger >= antalToerretider + 1){
			delbehandling = getProdukttype().getBehandling().getDelbehandlinger().get(antalToerretider);
		}
		return delbehandling;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * hjælpe metode der findes den sidste "aktuelle" delbehandling
	 * @return den sidste delbehandling
	 */
	public Delbehandling getSidsteDelbehandling(){
		return getProdukttype().getBehandling().getDelbehandlinger().get(toerretider.size()-1);
	}
	
	public String toString(){
		return id;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
