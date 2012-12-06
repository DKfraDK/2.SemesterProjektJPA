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

	// //////////////////TEST I MELLEMVARELAGER//////////////////////
	@Test
	public void testAddMellemvare() {
		System.out.println("Test addMellemvare");
		Delbehandling dHvile = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile);
		Produkttype p1 = new Produkttype("pSkumBanan", b1);
		Mellemvarelager m = Mellemvarelager.getInstance("Mellemvare");
		Mellemvare mv = new Mellemvare("007", p1, 0);
		m.addMellemvare(mv);
		assertTrue(m.getMellemvarer().contains(mv));

	}

	// //////////////////TEST I MELLEMVARELAGER//////////////////////
	@Test
	public void testRemoveMellemvare() {
		System.out.println("Test removeMellemvare");
		Delbehandling dHvile = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile);
		Produkttype p1 = new Produkttype("pSkumBanan", b1);
		Mellemvarelager m = Mellemvarelager.getInstance("Mellemvare");
		Mellemvare mv = new Mellemvare("007", p1, 0);

		m.addMellemvare(mv);

		m.removeMellemvare(mv);
		assertFalse(m.getMellemvarer().contains(mv));
	}

	@Test
	// //////////////////TEST I MELLEMVARE//////////////////////
	public void testGetProdukttype_og_testAtMellemvareFaarTilknyttetEnProdukttypeVedOprettelse() {
		System.out.println("Test getProdukttype");
		Delbehandling dHvile = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile);
		Produkttype p1 = new Produkttype("pSkumBanan", b1);
		Mellemvare mv = new Mellemvare("007", p1, 0);

		assertSame(p1, mv.getProdukttype());

	}

	// //////////////////TEST I MELLEMVARE//////////////////////
	@Test
	public void testSetProdukttype() {
		System.out.println("Test setProdukttype");
		Delbehandling dHvile = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile);
		Produkttype p1 = new Produkttype("pSkumBanan", b1);
		Produkttype p2 = new Produkttype("pPtaerte", b1);
		Mellemvare mv = new Mellemvare("007", p1, 0);

		mv.setProdukttype(p2);
		assertTrue(mv.getProdukttype().equals(p2));

	}

	// //////////////////TEST I MELLEMVARE//////////////////////
	@Test
	public void testCreateToerretid_Og_getToerretider() {
		System.out.println("Test CreateToerretid_Og_getToerretider");
		Delbehandling dHvile1 = new Delbehandling("Hvile periode", 1, 2, 8);
		Delbehandling dHvile2 = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile1);
		Service.addDelbehandlingTilBehandling(b1, dHvile2);
		Produkttype p1 = new Produkttype("pSkumBanan", b1);
		Mellemvare mv = new Mellemvare("007", p1, 0);

		Toerretid t = mv.createToerretid(0);
		assertTrue(mv.getToerretider().contains(t));

	}

	// //////////////////TEST I MELLEMVARE//////////////////////
	@Test
	public void testDeleteToerretid() {
		System.out.println("Test DeleteToerretid");
		Delbehandling dHvile1 = new Delbehandling("Hvile periode", 1, 2, 8);
		Delbehandling dHvile2 = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile1);
		Service.addDelbehandlingTilBehandling(b1, dHvile2);
		Produkttype p1 = new Produkttype("pSkumBanan", b1);
		Mellemvare mv = new Mellemvare("007", p1, 0);
		Toerretid t = mv.createToerretid(0);

		assertTrue(mv.getToerretider().contains(t));
		mv.deleteToerretid(t);
		assertTrue(!mv.getToerretider().contains(t));

	}

	// //////////////////TEST I MELLEMVARE//////////////////////
	@Test
	public void testGetNaesteDelbehandling() {
		Delbehandling dHvile1 = new Delbehandling("Hvile periode", 1, 2, 8);
		Delbehandling dHvile2 = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile1);
		Service.addDelbehandlingTilBehandling(b1, dHvile2);
		Produkttype p1 = new Produkttype("pSkumBanan", b1);
		Mellemvare mv = new Mellemvare("007", p1, 0);

		assertTrue(dHvile2.equals(mv.getNaesteDelbehandling()));
	}

	// //////////////////TEST I MELLEMVARE//////////////////////
	@Test
	public void testGetSidsteDelbehandling() {
		Delbehandling dHvile1 = new Delbehandling("Hvile periode", 1, 2, 8);
		Delbehandling dHvile2 = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile1);
		Service.addDelbehandlingTilBehandling(b1, dHvile2);
		Produkttype p1 = new Produkttype("pSkumBanan", b1);
		Mellemvare mv = new Mellemvare("007", p1, 0);
		Toerretid t = mv.createToerretid(0);

		assertTrue(dHvile2.equals(mv.getSidsteDelbehandling()));
	}

	// //////////////////TEST I MELLEMVARELAGER//////////////////////
	// ////////////GetNæsteMellemvareTilDelbehandling//////
	@Test
	public void testEnkeltvare() {
		Delbehandling dHvile1 = new Delbehandling("Hvile periode", 1, 2, 8);
		Delbehandling dHvile2 = new Delbehandling("Hvile periode", 1, 2, 8);
		Behandling b1 = new Behandling("Skumbehandling");
		Service.addDelbehandlingTilBehandling(b1, dHvile1);
		Service.addDelbehandlingTilBehandling(b1, dHvile2);
		Produkttype p1 = new Produkttype("pSkumBanan", b1);
		Mellemvarelager m = Mellemvarelager.getInstance("Mellemvare");
		m.clearLager();
		m.setDage(2);
		Mellemvare mv1 = new Mellemvare("008", p1, 0);
		m.addMellemvare(mv1);
		System.out.println("Print af lager" + m.getMellemvarer());
		assertEquals(mv1, m.getNaesteMellemvareTilBehandling());
	}

	// //////////////////TEST I MELLEMVARELAGER//////////////////////
	// ////////////GetNæsteMellemvareTilDelbehandling//////
	@Test
	public void testForMax() {
		Delbehandling dHvile1 = new Delbehandling("Hvile periode1", 1, 2, 6);
		Delbehandling dHvile2 = new Delbehandling("Hvile periode2", 1, 2, 4);
		Delbehandling dHvile3 = new Delbehandling("Hvile periode3", 1, 2, 3);
		Behandling b1 = new Behandling("Skumbehandling");
		Behandling b2 = new Behandling("SkumNu");
		Service.addDelbehandlingTilBehandling(b1, dHvile1);
		Service.addDelbehandlingTilBehandling(b1, dHvile2);
		Service.addDelbehandlingTilBehandling(b2, dHvile3);
		Service.addDelbehandlingTilBehandling(b2, dHvile2);
		Produkttype p1 = new Produkttype("pSkumBanan", b1);
		Produkttype p2 = new Produkttype("pPtart", b2);
		Mellemvarelager m = Mellemvarelager.getInstance("Mellemvare");
		m.clearLager();
		m.setDage(3);
		Mellemvare mv1 = new Mellemvare("008", p1, 0);
		Mellemvare mv2 = new Mellemvare("009", p2, 0);
		m.addMellemvare(mv1);
		m.addMellemvare(mv2);
		System.out.println("næste behandling" + mv1.getSidsteDelbehandling());
		System.out.println("Print af lager" + m.getMellemvarer());
		assertEquals(mv2, m.getNaesteMellemvareTilBehandling());
	}
	
	// //////////////////TEST I MELLEMVARELAGER//////////////////////
		// ////////////GetNæsteMellemvareTilDelbehandling//////
		@Test
		public void testIdeal() {
			Delbehandling dHvile1 = new Delbehandling("Hvile periode1", 1, 2, 6);
			Delbehandling dHvile2 = new Delbehandling("Hvile periode2", 1, 2, 4);
			Delbehandling dHvile3 = new Delbehandling("Hvile periode3", 1, 3, 12);
			Behandling b1 = new Behandling("Skumbehandling");
			Behandling b2 = new Behandling("SkumNu");
			Service.addDelbehandlingTilBehandling(b1, dHvile1);
			Service.addDelbehandlingTilBehandling(b1, dHvile2);
			Service.addDelbehandlingTilBehandling(b2, dHvile3);
			Service.addDelbehandlingTilBehandling(b2, dHvile2);
			Produkttype p1 = new Produkttype("pSkumBanan", b1);
			Produkttype p2 = new Produkttype("pPtart", b2);
			Mellemvarelager m = Mellemvarelager.getInstance("Mellemvare");
			m.clearLager();
			m.setDage(3);
			Mellemvare mv1 = new Mellemvare("008", p1, 0);
			Mellemvare mv2 = new Mellemvare("009", p2, 0);
			m.addMellemvare(mv1);
			m.addMellemvare(mv2);
			System.out.println("næste behandling" + mv1.getSidsteDelbehandling());
			System.out.println("Print af lager" + m.getMellemvarer());
			assertEquals(mv2, m.getNaesteMellemvareTilBehandling());
		}
		// //////////////////TEST I MELLEMVARELAGER//////////////////////
		// ////////////GetNæsteMellemvareTilDelbehandling//////
		@Test
		public void testEftermin() {
			Delbehandling dHvile1 = new Delbehandling("Hvile periode1", 4, 4, 6);
			Delbehandling dHvile2 = new Delbehandling("Hvile periode2", 3, 4, 5);
			Delbehandling dHvile3 = new Delbehandling("Hvile periode3", 3, 4, 7);
			Behandling b1 = new Behandling("Skumbehandling");
			Behandling b2 = new Behandling("SkumNu");
			Service.addDelbehandlingTilBehandling(b1, dHvile1);
			Service.addDelbehandlingTilBehandling(b1, dHvile2);
			Service.addDelbehandlingTilBehandling(b2, dHvile3);
			Service.addDelbehandlingTilBehandling(b2, dHvile2);
			Produkttype p1 = new Produkttype("pSkumBanan", b1);
			Produkttype p2 = new Produkttype("pPtart", b2);
			Mellemvarelager m = Mellemvarelager.getInstance("Mellemvare");
			m.clearLager();
			m.setDage(3);
			Mellemvare mv1 = new Mellemvare("008", p1, 0);
			Mellemvare mv2 = new Mellemvare("009", p2, 0);
			m.addMellemvare(mv1);
			m.addMellemvare(mv2);
			System.out.println("næste behandling" + mv1.getSidsteDelbehandling());
			System.out.println("Print af lager" + m.getMellemvarer());
			assertEquals(mv2, m.getNaesteMellemvareTilBehandling());
		}
		
		// //////////////////TEST I MELLEMVARELAGER//////////////////////
		// ////////////GetNæsteMellemvareTilDelbehandling//////
		@Test
		public void testEns() {
			Delbehandling dHvile1 = new Delbehandling("Hvile periode1", 1, 2, 6);
			Delbehandling dHvile2 = new Delbehandling("Hvile periode2", 1, 2, 6);
			Delbehandling dHvile3 = new Delbehandling("Hvile periode3", 1, 2, 6);
			Behandling b1 = new Behandling("Skumbehandling");
			Behandling b2 = new Behandling("SkumNu");
			Service.addDelbehandlingTilBehandling(b1, dHvile1);
			Service.addDelbehandlingTilBehandling(b1, dHvile2);
			Service.addDelbehandlingTilBehandling(b2, dHvile3);
			Service.addDelbehandlingTilBehandling(b2, dHvile2);
			Produkttype p1 = new Produkttype("pSkumBanan", b1);
			Produkttype p2 = new Produkttype("pPtart", b2);
			Mellemvarelager m = Mellemvarelager.getInstance("Mellemvare");
			m.clearLager();
			m.setDage(3);
			Mellemvare mv1 = new Mellemvare("008", p1, 0);
			Mellemvare mv2 = new Mellemvare("009", p2, 0);
			m.addMellemvare(mv1);
			m.addMellemvare(mv2);
			System.out.println("næste behandling" + mv1.getSidsteDelbehandling());
			System.out.println("Print af lager" + m.getMellemvarer());
			assertEquals(mv1, m.getNaesteMellemvareTilBehandling());
		}
		// //////////////////TEST I MELLEMVARELAGER//////////////////////
		// ////////////GetNæsteMellemvareTilDelbehandling//////
		@Test
		public void testForklar() {
			Delbehandling dHvile1 = new Delbehandling("Hvile periode1", 4, 6, 8);
			Delbehandling dHvile2 = new Delbehandling("Hvile periode2", 4, 6, 8);
			Delbehandling dHvile3 = new Delbehandling("Hvile periode3", 4, 6, 8);
			Behandling b1 = new Behandling("Skumbehandling");
			Behandling b2 = new Behandling("SkumNu");
			Service.addDelbehandlingTilBehandling(b1, dHvile1);
			Service.addDelbehandlingTilBehandling(b1, dHvile2);
			Service.addDelbehandlingTilBehandling(b2, dHvile3);
			Service.addDelbehandlingTilBehandling(b2, dHvile2);
			Produkttype p1 = new Produkttype("pSkumBanan", b1);
			Produkttype p2 = new Produkttype("pPtart", b2);
			Mellemvarelager m = Mellemvarelager.getInstance("Mellemvare");
			m.clearLager();
			m.setDage(3);
			Mellemvare mv1 = new Mellemvare("008", p1, 0);
			Mellemvare mv2 = new Mellemvare("009", p2, 0);
			m.addMellemvare(mv1);
			m.addMellemvare(mv2);
			System.out.println("næste behandling" + mv1.getSidsteDelbehandling());
			System.out.println("Print af lager" + m.getMellemvarer());
			assertEquals(null, m.getNaesteMellemvareTilBehandling());
		}
		
		// //////////////////TEST I MELLEMVARELAGER//////////////////////
		// ////////////GetNæsteMellemvareTilDelbehandling//////
		@Test
		public void testTom() {
			Delbehandling dHvile1 = new Delbehandling("Hvile periode1", 1, 2, 6);
			Delbehandling dHvile2 = new Delbehandling("Hvile periode2", 1, 2, 4);
			Delbehandling dHvile3 = new Delbehandling("Hvile periode3", 1, 2, 3);
			Behandling b1 = new Behandling("Skumbehandling");
			Behandling b2 = new Behandling("SkumNu");
			Service.addDelbehandlingTilBehandling(b1, dHvile1);
			Service.addDelbehandlingTilBehandling(b1, dHvile2);
			Service.addDelbehandlingTilBehandling(b2, dHvile3);
			Service.addDelbehandlingTilBehandling(b2, dHvile2);
			Produkttype p1 = new Produkttype("pSkumBanan", b1);
			Produkttype p2 = new Produkttype("pPtart", b2);
			Mellemvarelager m = Mellemvarelager.getInstance("Mellemvare");
			m.clearLager();
			m.setDage(3);
			System.out.println("Print af lager" + m.getMellemvarer());
			assertEquals(null, m.getNaesteMellemvareTilBehandling());
		}

}
