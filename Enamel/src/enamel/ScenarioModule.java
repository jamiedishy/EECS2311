package enamel;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;

public abstract class ScenarioModule implements Serializable {

	private static final long serialVersionUID = 1L;
	protected final int PAUSE = 1;
	protected final String JUMP_TAG = "JUMP";
	protected ArrayList<String> SKIP_TAG;
	public final int CELL_COUNT;
	public final int BUTTON_COUNT;


	public ScenarioModule(int cc, int bc) {
		CELL_COUNT = cc;
		BUTTON_COUNT = bc;
	}
	
	public abstract String header();
	public abstract ArrayList<String> body();

	public TreeItem<String> write(TreeItem<String> branch) {
		
		if (branch == null) {
			branch = new TreeItem<String>(header());	
		} else {
			branch.setValue(header());
			branch.getChildren().clear();
		}
		
		for (String line : body())
			branch.getChildren().add(new TreeItem<String>(line));	
		
		return branch;
	}

	public void write(ListView<String> l) {
		l.getItems().addAll(body());
	}
	
}
