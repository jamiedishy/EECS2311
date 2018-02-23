package enamel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import enamel.BrailleCell;

class BrailleCellTest {

	BrailleCell temp;

	@BeforeEach
	void setup() {
		temp = new BrailleCell();
	}

	@Test
	public void test01_DisplayCharacterEx() throws InterruptedException {
		try {
			temp.displayCharacter('$');
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Non standard character");
		}
	}

	@Test
	void test02_DisplayCharacter_SetPins() throws InterruptedException {
		boolean[] expected = { true, false, false, false, false, false, false, false };
		temp.displayCharacter('A');
		for (int i = 0; i < 8; i++) {
			assertEquals(expected[i], temp.getPinState(i));
		}
	}

	@Test
	void test03_SetPinsEx1() {
		try {
			temp.setPins("11");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Illegal string passed, length > or < 8.");
		}
	}

	@Test
	void test04_SetPinsEx2() {
		try {
			temp.setPins("11022340");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Invalid string passed, non-binary character detected.");
		}
	}

	@Test
	void test05_RaiseOnePinEx() {
		BrailleCell pin = new BrailleCell();
		try {
			pin.raiseOnePin(8);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Invalid index");
		}
	}
	
	@Test
	void test06_RaiseOnePin() {
		for (int i = 0; i < 8; i++) {
			temp.raiseOnePin(i);
			assertEquals(true, temp.getPinState(i));
		}
	}

	@Test
	void testLowerOnePin() {
		BrailleCell pin = new BrailleCell();
		try {
			int a = 9;
			a = pin.getNumberOfPins();
		}
		catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Invalid index");
		}
		pin.lowerOnePin(5);
		assertEquals(false, pin.getPinState(5));
	}


	@Test
	void testClear() {
		BrailleCell pin = new BrailleCell();
		for (int i = 0; i < 8; i++) {
			pin.getPinState(i);
			assertEquals(pin.getPinState(i), false);
		}
	}

	@Test
	void testGetPinState() {
		BrailleCell pin = new BrailleCell();
		pin.raiseOnePin(4);
		assertEquals(pin.getPinState(4), true);
	}

	@Test
	void testGetNumberOfPins() {
		BrailleCell pins = new BrailleCell();
		assertEquals(pins.getNumberOfPins(), 8);
	}

}
