package UnitTest;

import static org.junit.Assert.*;
import model.Behandling;
import model.Delbehandling;
import model.Mellemvare;
import model.Mellemvarelager;
import model.Produkttype;
import model.Toerretid;

import org.junit.Before;
import org.junit.Test;

import service.Service;

public class RelationsTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
		System.out.println("ekstra test");
	}
	////////////////////TEST I MELLEMVARELAGER//////////////////////
	@Test
	public void testAddMellemvare(){
		System.out.println("Test addMellemvare");
		Delbehandling dHvile = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile);
		Produkttype p1 = new Produkttype("pSkumBanan",b1);
		Mellemvarelager m = Mellemvarelager.getInstance("Mellemvare");
		Mellemvare mv = new Mellemvare("007",p1,0);
		m.addMellemvare(mv);
		assertTrue(m.getMellemvarer().contains(mv));
		
	}
////////////////////TEST I MELLEMVARELAGER//////////////////////
	@Test
	public void testRemoveMellemvare(){
		System.out.println("Test removeMellemvare");
		Delbehandling dHvile = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile);
		Produkttype p1 = new Produkttype("pSkumBanan",b1);
		Mellemvarelager m = Mellemvarelager.getInstance("Mellemvare");
		Mellemvare mv = new Mellemvare("007",p1,0);
		
		m.addMellemvare(mv);
		
		m.removeMellemvare(mv);
		assertFalse(m.getMellemvarer().contains(mv));
	}
	
	@Test
////////////////////TEST I MELLEMVARE//////////////////////
	public void testGetProdukttype_og_testAtMellemvareFaarTilknyttetEnProdukttypeVedOprettelse(){
		System.out.println("Test getProdukttype");
		Delbehandling dHvile = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile);
		Produkttype p1 = new Produkttype("pSkumBanan",b1);
		Mellemvare mv = new Mellemvare("007",p1,0);
		
		assertSame(p1,mv.getProdukttype());
		
	}
////////////////////TEST I MELLEMVARE//////////////////////
	@Test
	public void testSetProdukttype(){
		System.out.println("Test setProdukttype");
		Delbehandling dHvile = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile);
		Produkttype p1 = new Produkttype("pSkumBanan",b1);
		Produkttype p2 = new Produkttype("pPtaerte",b1);
		Mellemvare mv = new Mellemvare("007",p1,0);
		
		mv.setProdukttype(p2);
		assertTrue(mv.getProdukttype().equals(p2));
		
	}
////////////////////TEST I MELLEMVARE//////////////////////
	@Test
	public void testCreateToerretid_Og_getToerretider(){
		System.out.println("Test CreateToerretid_Og_getToerretider");
		Delbehandling dHvile1 = new Delbehandling("Hvile periode", 1, 2, 8);
		Delbehandling dHvile2 = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile1);
		Service.addDelbehandlingTilBehandling(b1, dHvile2);
		Produkttype p1 = new Produkttype("pSkumBanan",b1);
		Mellemvare mv = new Mellemvare("007",p1,0);
		
		Toerretid t = mv.createToerretid(0);
		assertTrue(mv.getToerretider().contains(t));
		
	}
////////////////////TEST I MELLEMVARE//////////////////////
	@Test
	public void testDeleteToerretid(){
		System.out.println("Test DeleteToerretid");
		Delbehandling dHvile1 = new Delbehandling("Hvile periode", 1, 2, 8);
		Delbehandling dHvile2 = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile1);
		Service.addDelbehandlingTilBehandling(b1, dHvile2);
		Produkttype p1 = new Produkttype("pSkumBanan",b1);
		Mellemvare mv = new Mellemvare("007",p1,0);
		Toerretid t = mv.createToerretid(0);
		
		
		assertTrue(mv.getToerretider().contains(t));
		mv.deleteToerretid(t);
		assertTrue(!mv.getToerretider().contains(t));
		
		
	}
////////////////////TEST I MELLEMVARE//////////////////////
	@Test
	public void testGetNaesteDelbehandling(){
		Delbehandling dHvile1 = new Delbehandling("Hvile periode", 1, 2, 8);
		Delbehandling dHvile2 = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile1);
		Service.addDelbehandlingTilBehandling(b1, dHvile2);
		Produkttype p1 = new Produkttype("pSkumBanan",b1);
		Mellemvare mv = new Mellemvare("007",p1,0);
	
		
		
		assertTrue(dHvile2.equals(mv.getNaesteDelbehandling()));
	}
////////////////////TEST I MELLEMVARE//////////////////////
	@Test
	public void testGetSidsteDelbehandling(){
		Delbehandling dHvile1 = new Delbehandling("Hvile periode", 1, 2, 8);
		Delbehandling dHvile2 = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile1);
		Service.addDelbehandlingTilBehandling(b1, dHvile2);
		Produkttype p1 = new Produkttype("pSkumBanan",b1);
		Mellemvare mv = new Mellemvare("007",p1,0);
		Toerretid t = mv.createToerretid(0);
		
		assertTrue(dHvile2.equals(mv.getSidsteDelbehandling()));
	}

}
