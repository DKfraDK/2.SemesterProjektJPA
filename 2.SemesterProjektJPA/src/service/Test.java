package service;

import model.Behandling;
import model.Delbehandling;
import model.Mellemvare;
import model.Mellemvarelager;
import model.Produkttype;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Delbehandling del1 = new Delbehandling("Chokoladeovertræk", 1,2,3);
		Delbehandling del2 = new Delbehandling("Chokoladeovertræk2", 1,1,5);
		Delbehandling del3 = new Delbehandling("Fyld med skum",1,1,3);
		Delbehandling del4 = new Delbehandling("Glasur",1,3,3);
		
		Behandling b1 = new Behandling("Skumbananbehandling");
		Behandling b2 = new Behandling("P-Tærte behandling");
		Behandling b3 = new Behandling("Sukker sjov");
		b3.addDelbehandling(del3);
		b3.addDelbehandling(del4);
		b2.addDelbehandling(del1);
		b2.addDelbehandling(del2);
		b1.addDelbehandling(del1);
		b1.addDelbehandling(del2);
		Produkttype p1 = new Produkttype("Skumbanan", b1);
		Produkttype p2 = new Produkttype("P-Tærter", b2);
		Produkttype p3 = new Produkttype("PinkShock",b3);
		
		Mellemvare m1 = new Mellemvare("001",p1, 0);
		Mellemvare m2 = new Mellemvare("002",p2, 0);
		Mellemvare m3 = new Mellemvare("003",p3, 0);
		Mellemvarelager ml1 = Mellemvarelager.getInstance("Lager of d00m");
		
		ml1.setDage(2);
		
		ml1.addMellemvare(m1);		
		ml1.addMellemvare(m2);
		ml1.addMellemvare(m3);
		
		System.out.println("Alle mellemvarer: " + ml1.getMellemvarer());
		System.out.println("N¾ste mellemvare til behandling: " + ml1.getNaesteMellemvareTilBehandling());
		System.out.println("F¾rdige mellemvarer: " + ml1.getFaerdigeMellemvarer());
		System.out.println("N¾ste mellemvare til behandling: " + ml1.getNaesteMellemvareTilBehandling());
		System.out.println("F¾rdige mellemvarer: " + ml1.getFaerdigeMellemvarer());
		System.out.println("N¾ste mellemvare til behandling: " + ml1.getNaesteMellemvareTilBehandling());
		System.out.println("F¾rdige mellemvarer: " + ml1.getFaerdigeMellemvarer());
		System.out.println("N¾ste mellemvare til behandling: " + ml1.getNaesteMellemvareTilBehandling());
		System.out.println("F¾rdige mellemvarer: " + ml1.getFaerdigeMellemvarer());
		System.out.println("N¾ste mellemvare til behandling: " + ml1.getNaesteMellemvareTilBehandling());
		System.out.println("F¾rdige mellemvarer: " + ml1.getFaerdigeMellemvarer());
		System.out.println("N¾ste mellemvare til behandling: " + ml1.getNaesteMellemvareTilBehandling());
		System.out.println("F¾rdige mellemvarer: " + ml1.getFaerdigeMellemvarer());

		System.out.println("Alle mellemvarer: " + ml1.getMellemvarer());
		
		System.out.println("F¾rdige mellemvarer: " + ml1.getFaerdigeMellemvarer());
		
		
		
	}

}
