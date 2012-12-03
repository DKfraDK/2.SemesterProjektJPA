package service;

import static org.junit.Assert.*;

import model.Behandling;
import model.Delbehandling;
import model.Mellemvare;
import model.Mellemvarelager;
import model.Produkttype;

import org.junit.Before;
import org.junit.Test;

public class MellemvareLagerTest {
	
	@Before
	public void setup(){
		Mellemvarelager lager = Mellemvarelager.getInstance("lager test");
		lager.clearLager();
	}
	
	@Test
	public void test1(){
		Delbehandling d1 = new Delbehandling("Test1", 0, 0, 0);
		Delbehandling d2 = new Delbehandling("Test2", 0, 0, 0);
		Behandling b1 = new Behandling("BehandlingTest");
		Service.addDelbehandlingTilBehandling(b1, d1);
		Service.addDelbehandlingTilBehandling(b1, d2);
		Produkttype p1 = new Produkttype("ProduktTest", b1);
		Mellemvare v1 = new Mellemvare("VareTest", p1, 0);
		Mellemvarelager lager = Mellemvarelager.getInstance("lager test");
		lager.addMellemvare(v1);
		lager.setDage(0);
		assertEquals(v1, lager.getNaesteMellemvareTilBehandling());
	}
	
	@Test
	public void test2(){
		Delbehandling d3 = new Delbehandling("Test1", 1, 1, 1);
		Delbehandling d4 = new Delbehandling("Test2", 1, 1, 1);
		Behandling b2 = new Behandling("BehandlingTest");
		Service.addDelbehandlingTilBehandling(b2, d3);
		Service.addDelbehandlingTilBehandling(b2, d4);
		Produkttype p2 = new Produkttype("ProduktTest", b2);
		Mellemvare v2 = new Mellemvare("VareTest2", p2, 0);
		Mellemvarelager lager = Mellemvarelager.getInstance("lager test");
		lager.addMellemvare(v2);
		lager.setDage(1);
		assertEquals(v2, lager.getNaesteMellemvareTilBehandling());
	}

	
}