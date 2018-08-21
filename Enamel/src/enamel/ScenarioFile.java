package enamel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScenarioFile implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final int MAX_CELL = 4;
	public static final int MAX_BUTTONS = 4;
	String filename;
	int cellCount;
	int buttonCount;
	ArrayList<ScenarioModule> contents;
	
	public ScenarioFile() {
		this(null, 1, 1, null);
	}
	
	public ScenarioFile(String fn, int cc, int bc) {
		this(fn, cc, bc, null);
	}
	
	public ScenarioFile(String fn, int cc, int bc, List<ScenarioModule> list) {
		setFilename(fn);
		setCellcount(cc);
		setButtoncount(bc);
		setContents(list);
	}
	
	public String getFilename() { return filename; }
	public int getCellCount() { return cellCount; }
	public int getButtonCount() { return buttonCount; }
	public ArrayList<ScenarioModule> getContents() { return contents; }
	
	public void setFilename(String fn) { filename = fn;	}
	public void setCellcount(int cc) { cellCount = cc; }
	public void setButtoncount(int bc) { buttonCount = bc; }
	public void setContents(List<ScenarioModule> list) {
		if (list != null) {
			contents = new ArrayList<ScenarioModule>();
			contents.addAll(list);			
		}
	}
	public void addContent(ScenarioModule content) {
		if (contents == null)
			contents = new ArrayList<ScenarioModule>();
		
		contents.add(content);
	}
		
	public void copy(ScenarioFile other) {
		setFilename(other.filename);
		setCellcount(other.cellCount);
		setButtoncount(other.buttonCount);
		setContents(other.contents);
	}
}
