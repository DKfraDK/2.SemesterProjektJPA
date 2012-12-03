package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Toerretid {
	@Id
	@GeneratedValue
	private int Id;
	private int tid;
	@ManyToOne
	private Delbehandling delbehandling = null;
	
	
	Toerretid(int tid, Delbehandling delbehandling){
		this.setTid(tid);
		this.delbehandling=delbehandling;
		
	}
	Toerretid(){
		
	}
	
	public Delbehandling getDelbehandling(){
		return delbehandling;
	}
	public void setDelbehandling(Delbehandling delbehandling){
		if(this.delbehandling!=delbehandling){
			this.delbehandling=delbehandling;
		}
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}
	
	public String toString(){
		return delbehandling + " begyndt dag: " + tid;
	}

}
