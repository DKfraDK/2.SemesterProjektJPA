package service;

import java.util.List;

import model.Behandling;
import model.Delbehandling;
import model.Mellemvare;
import model.Mellemvarelager;
import model.Produkttype;
import model.Toerretid;

public interface DaoInterface {

	public Mellemvarelager getMellemvarelager();
	public List<Behandling> getBehandlinger();
	public List<Delbehandling> getDelbehandlinger();
	public List<Toerretid> getToerretider();
	public List<Produkttype> getProdukttyper();
	public List<Mellemvare> getMellemvarer();
	public void addMellemvare(Mellemvare mellemvare);
	public void addBehandling(Behandling behandling);
	public void addToerretid(Toerretid toerretid);
	public void addProdukttype(Produkttype produkttype);
	public void addDelbehandling(Delbehandling delbehandling);
	public void removeBehandling(Behandling behandling);
	public void removeToerretid(Toerretid toerretid);
	public void removeProdukttype(Produkttype produkttype);
	public void removeDelbehandling(Delbehandling delbehandling);
	public void removeMellemvare(Mellemvare mellemvare);
}
