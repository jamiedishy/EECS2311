package enamel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ScenePanelView extends VBox {

	TreeView<String> tree;
	TreeItem<String> root;
	
	Button editBtn, delBtn, upBtn, downBtn;
	
	public ScenePanelView() {
		root = new TreeItem<String>();
		tree = new TreeView<String>(root);
		editBtn = new Button("Edit");
		delBtn = new Button("Delete");		
		upBtn = new Button("Up");
		downBtn = new Button("Down");
		layoutPane();
		addAccessibility();
	}

	private void layoutPane() {
		this.setSpacing(10);
		this.setAlignment(Pos.CENTER);		
		
		GridPane buttonLayout = new GridPane();
		buttonLayout.setPadding(new Insets(5, 5, 5, 5));
		buttonLayout.setVgap(10);
		buttonLayout.setHgap(12);
		buttonLayout.setAlignment(Pos.CENTER);
		GridPane.setConstraints(editBtn, 0, 0);
		GridPane.setConstraints(delBtn, 0, 1);
		GridPane.setConstraints(upBtn, 1, 0);
		GridPane.setConstraints(downBtn, 1, 1);
		buttonLayout.getChildren().addAll(editBtn, delBtn, upBtn, downBtn);
		
		this.getChildren().addAll(tree, buttonLayout);		
        String cssLayout = "-fx-background-color: beige;";
        this.setStyle(cssLayout);
		
		tree.setShowRoot(false);
		root.setExpanded(true);
	}
	
	private void addAccessibility() {
		delBtn.setAccessibleText("Delete scenario");
		delBtn.setAccessibleHelp("This button deletes the selected item from the scenario list. " + 
									"Press SPACE to confirm.");
		
		editBtn.setAccessibleText("Edit scenario");
		editBtn.setAccessibleHelp("This button edits the selected item from the scenario list. " + 
									"Press SPACE to confirm.");
		
		upBtn.setAccessibleText("Move scenario up");
		upBtn.setAccessibleHelp("This button moves the selected item 1 level higher in the scenario list. " + 
									"Press SPACE to confirm.");
		
		downBtn.setAccessibleText("Move scenario down");
		downBtn.setAccessibleHelp("This button moves the selected item 1 level lower in the scenario list. " + 
									"Press SPACE to confirm.");
	}	
}
