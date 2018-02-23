package enamel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractButton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VisualPlayerTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testRefresh() {
		VisualPlayer cell = new VisualPlayer(1, 8);
		cell.getCell(0).setPins("01010101");
		cell.refresh();
	//	assertEquals(cell.pins[0].getSelectedObjects();
		System.out.println(cell.pins[0].getSelectedObjects());
		//fail("Not yet implemented");
	}

	@Test
	void testAddSkipButtonListener() {
		
		
		//fail("Not yet implemented");
	}

	@Test
	void testRemoveButtonListener() {
		VisualPlayer player = new VisualPlayer(1, 8);
		try {
			Thread.sleep(4000); // problem: runnable put on 'invoke and wait' call, meaning that runnable hasnt finished
			// invoking by the time the other methods in the test are called. This pauses execution for three seconds
			// for the runnable to catch up before calling the other methods. 
		} catch (InterruptedException e1) {
			
			e1.printStackTrace();
		}
	    try {
			player.removeButtonListener(9);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Invalid index.", e.getMessage());			
		}
		try {
			player.removeButtonListener(-1);
		}
		catch (IllegalArgumentException e) {
			assertEquals("Invalid index.", e.getMessage());
		}
		
		player.refresh();
		player.addSkipButtonListener(0, "hello", new ScenarioParser(false));
		player.removeButtonListener(0);
		assertEquals(0, player.getButton(0).getActionListeners().length);
	}

	@Test
	void testAddRepeatButtonListener() {
		VisualPlayer button = new VisualPlayer(1,8);
		
	}

	@Test
	void testVisualPlayer() {
		fail("Not yet implemented");
	}

	@Test
	void testSetButton() {
		VisualPlayer button = new VisualPlayer(1,8);
		try {
			button.setButton(-1);
		}
		catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Non-positive integer entered.");
		}
	}

	@Test
	void testSetCell() {
		VisualPlayer cell = new VisualPlayer(1,8);
		try {
			cell.setCell(-1);
		}
		catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Non-positive integer entered.");
		}
	}

	@Test
	void testGetButton() {
		VisualPlayer player = new VisualPlayer(1, 8);
		try {
			player.getButton(9);
		}
		catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Invalid button index.");
		}
		try {
			player.getButton(-1);
		}
		catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Invalid button index.");
		} 
	}

}
