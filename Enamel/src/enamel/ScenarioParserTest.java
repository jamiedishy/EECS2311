package enamel;

import static org.junit.Assert.*;
import enamel.*;
import enamel.ScenarioParser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
public class ScenarioParserTest {

	ScenarioParser scene = new ScenarioParser(true);
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testScenarioParser() {
		assertEquals(scene.userInput, false);
		assertEquals(scene.player.brailleCellNumber, 0);
	}
	@Test
	public void testSkip() {
		try {
		Scanner fileScanner = null;
		fileScanner = new Scanner(new FileReader("TesterFile.txt"));
		fileScanner.hasNextLine();
		if(!fileScanner.hasNextLine()) 
			assertTrue(fileScanner.close(),true);
		}
		catch ( FileNotFoundException e) {
			e.printStackTrace();
		}

	
	}
	@Test
	public void testSkip02() {
		try {
			scene.skip(skip);
		}
		catch ( IllegalArgumentException e){
			e.printStackTrace();
		}
	
		
	}

	@Test
	public void testPerformAction01() {
		try {
			scene.performAction("TesterFile.txt");
		}
		catch ( InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testPerformAction02() {
		try {
			scene.performAction(fileLine);
		}
		catch ( InputMismatchException e){
		e.printStackTrace();
		}
		}

	@Test
	public void testRepeatText() {
		try {
			scene.repeatText();
		}
	}

	@Test
	public void testSetScenarioFile01() {
		
		try { scene.setScenarioFile("Tester101.txt1");
		}
		
		catch ( IllegalArgumentException e) {
			e.printStackTrace();
		}
}
	@Test
	public void testSetScenarioFile02() {
		
		try { scene.setScenarioFile("Scenario_1.txt");
		}
		
		catch ( InputMismatchException e) {
			e.printStackTrace();
		}
}
}
