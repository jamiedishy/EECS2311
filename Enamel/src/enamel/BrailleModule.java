package enamel;

import java.util.ArrayList;

public class BrailleModule extends ScenarioModule {

	private final int NUM_PINS = 8;	
	private BrailleCell[] cells;
	private String[] charInput;
	private boolean[] clearSelected;
	
	public BrailleModule(int cc, int bc) {
		super(cc, bc);

		charInput = new String[cc];
		clearSelected = new boolean[cc];
		cells = new BrailleCell[cc];
		for (int i = 0; i < cc; i++)
			cells[i] = new BrailleCell();
	}

	public void setCharacter(int index, String c) { charInput[index] = c; }
	public void setCellState(int index, String state) { cells[index].setPins(state); }
	public void setClearSelected(int index, boolean cs) { clearSelected[index] = cs; }
	
	public BrailleCell getCell(int index) { return cells[index]; }
	public String getCharacter(int index) { return charInput[index]; }
	public boolean getClearSelected(int index) {return clearSelected[index]; }
		
	public boolean[] getBoolState(int index) {
		boolean[] pinState = new boolean[NUM_PINS];
		
		for (int i = 0; i < NUM_PINS; i++) {
			pinState[i] = cells[index].getPinState(i);
		}
		
		return pinState;
	}
	
	public String getStrState(int index) {
		String pinState = "";
		
		for (int i = 0; i < NUM_PINS; i++) {
			if (cells[index].getPinState(i))
				pinState += "1";
			else
				pinState += "0";
		}
			
		return pinState;
	}
	
	public boolean isClear(int index) { 
		return getStrState(index).equals("00000000");
	}
	
	public void clear(int index) {
		cells[index].clear();
		charInput[index] = null;
	}
		
	@Override
	public String header() {
		String result = "";
		
		for (int i = 0; i < CELL_COUNT; i++) {		
			if (clearSelected[i]) {
				if (!result.isEmpty())
					result += " + ";
				result += "Clear " + (i + 1);
				continue;
			}
			
			if (charInput[i] != null && !charInput[i].isEmpty()) {
				if (!result.isEmpty())
					result += " + ";
				result += charInput[i] + " on " + (i + 1);
				continue;
			}
			
			if (!isClear(i)) {
				if (!result.isEmpty())
					result += " + ";
				result += getStrState(i) + " on " + (i + 1);
			}
		}
		
		return result;
	}

	@Override
	public ArrayList<String> body() {
		ArrayList<String> result = new ArrayList<String>();
		
		for (int i = 0; i < CELL_COUNT; i++) {
			if (clearSelected[i]) {
				result.add("/~disp-cell-clear:" + i);
				continue;
			}
			
			if (charInput[i] != null && !charInput[i].isEmpty()) {
				result.add("/~disp-cell-char:" + i + " " + charInput[i]);
				continue;
			}
			
			if (!isClear(i)) {
				result.add("/~disp-cell-pins:" + i + " " + getStrState(i));
			}
		}
		
		return result;
	}
	
}
