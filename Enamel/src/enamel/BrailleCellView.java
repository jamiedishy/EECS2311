package enamel;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class BrailleCellView extends GridPane {

	RadioButton[] pins;
	TextField charInput;
	CheckBox cbClear;
	int cellIndex;
	final int NUM_PINS = 8;
    private final String cssLayout = "-fx-border-color: black;\n" +
			   						 "-fx-border-width: 2;";
    
	public BrailleCellView (int index) {

		pins = new RadioButton[NUM_PINS];
		charInput = new TextField();
		cbClear = new CheckBox("Clear");
		cellIndex = index;

		layoutView();
		addAccessibility();
	}

	private void layoutView() {
        this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(6, 0, 6, 6));
        this.setHgap(3);
        this.setVgap(6);
		this.setStyle(cssLayout);  
				
        charInput.setPromptText("Enter character here");
        charInput.setAlignment(Pos.CENTER);
        
        for (int i = 0; i < NUM_PINS; i++) {
        	pins[i] = new RadioButton();
        	this.getChildren().addAll(pins[i]);
        }
        
        GridPane.setHalignment(this, HPos.CENTER);
        GridPane.setHalignment(charInput, HPos.CENTER);
        GridPane.setHalignment(cbClear, HPos.CENTER);
        
        GridPane.setConstraints(this, (4 * cellIndex) + 1, 1);
        GridPane.setConstraints(charInput, (4 * cellIndex), 2, 3, 1);
        GridPane.setConstraints(cbClear, (4 * cellIndex), 3, 3, 1);
        
        GridPane.setConstraints(pins[0], 0, 0);
        GridPane.setConstraints(pins[1], 0, 1);
        GridPane.setConstraints(pins[2], 0, 2);
        GridPane.setConstraints(pins[3], 1, 0);
        GridPane.setConstraints(pins[4], 1, 1);
        GridPane.setConstraints(pins[5], 1, 2);
        GridPane.setConstraints(pins[6], 0, 3);
        GridPane.setConstraints(pins[7], 1, 3);
	}

	private void addAccessibility() {
		this.focusTraversableProperty().bind(Platform.accessibilityActiveProperty());
		this.setAccessibleText("Braille Cell " + (cellIndex + 1));
		this.setAccessibleHelp("You can set individual pins, assign a letter or clear this cell");
		
		for (int i = 0; i < NUM_PINS; i++) {
			pins[i].setAccessibleText("Cell " + (cellIndex + 1) + ", " + "Pin " + (i + 1));
		}
		
		charInput.setAccessibleText("Enter a letter for Braille cell " + (cellIndex + 1));
		cbClear.setAccessibleText("Clear Braille cell " + (cellIndex + 1));
	}
}
