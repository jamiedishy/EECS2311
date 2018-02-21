package enamel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

class BrailleCellTest {

	BrailleCell temp;
	
	@BeforeEach
	void setup() {
		temp = new BrailleCell();
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void test01_DisplayCharacterEx() throws InterruptedException {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("What happened?");
		temp.displayCharacter('$');
	}

	@Test
	void test02_DisplayCharacter_SetPins() throws InterruptedException {
		boolean[] expected = {true, false, false, false, false, false, false, false};
		temp.displayCharacter('A');
		for (int i = 0; i < 8; i++ ) {
			assertEquals(expected[i], temp.getPinState(i));
		}	
	}
	
	@Test
	void test03_SetPinsEx1() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Testing number of pins");
		temp.setPins("11");
	}

	@Test
	void test04_SetPinsEx2() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Unreadable characters");
		temp.setPins("11022340");
	}
	
	@Test
	void test05_RaiseOnePinEx() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Pin out of bounds");
		temp.raiseOnePin(9);
	}

	@Test
	void test06_RaiseOnePin() {
		for (int i = 0; i < 7; i++) {
			temp.raiseOnePin(i);
			assertEquals(true, temp.getPinState(i));
		}
	}
	
	@Test
	void testLowerOnePin() {
		fail("Not yet implemented");
	}

	@Test
	void testClear() {
		fail("Not yet implemented");
	}

	@Test
	void testGetPinState() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNumberOfPins() {
		fail("Not yet implemented");
	}

}
