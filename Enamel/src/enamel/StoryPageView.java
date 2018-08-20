package enamel;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class StoryPageView extends GridPane {

    TextArea text;
    Label audioLbl;
    ComboBox<String> audio;
    ToggleGroup tg;
    RadioButton inputRBtn, repeatRBtn;
    Button addBtn, updateBtn, resetBtn, backBtn;
    
    int buttonCount;
    boolean isEdit;
	
	public StoryPageView(int bc, boolean state) {
	    text = new TextArea(null);
	    audioLbl = new Label("Audio: ");
	    audio = new ComboBox<String>();
	    tg = new ToggleGroup();
	    inputRBtn = new RadioButton("Wait for User input");
	    repeatRBtn = new RadioButton("Repeat input");
	    addBtn = new Button("Add");
	    updateBtn = new Button("Update");
	    resetBtn = new Button("Reset");
	    backBtn = new Button("Back");
	    
	    buttonCount = bc;
	    isEdit = state;
	    layoutPage();
	    addAccessibility();
	}

	private void layoutPage() {
		this.setPadding(new Insets(5, 5, 5, 5));
	    this.setVgap(10);
	    this.setHgap(12);
	    this.setAlignment(Pos.CENTER);
	    //this.setGridLinesVisible(true);
	    
	    text.setPromptText("Type anything here");
	    inputRBtn.setToggleGroup(tg);
	    repeatRBtn.setToggleGroup(tg);
	    
	    File audioFile = new File(System.getProperty("user.dir") + File.separator + 
	    						  "FactoryScenarios" + File.separator + 
	    						  "AudioFiles");
	    
	    if (audioFile.isDirectory()) {
	    	for (String s : audioFile.list()) {
	    		if (FilenameUtils.getExtension(s).toLowerCase().equals("wav"))
	    			audio.getItems().addAll(s);
	    	}
	    }
	
	    if (buttonCount < 2)
	    	repeatRBtn.setDisable(true);
	
	    GridPane.setConstraints(text, 0, 0, 6, 6);
	    GridPane.setConstraints(audioLbl, 0, 6, 1, 2);
	    GridPane.setConstraints(audio, 1, 6, 1, 2);
	    GridPane.setConstraints(inputRBtn, 2, 6);
	    GridPane.setConstraints(repeatRBtn, 2, 7);
	    GridPane.setConstraints(addBtn, 3, 6, 1, 2);
	    GridPane.setConstraints(updateBtn, 3, 6, 1, 2);
	    GridPane.setConstraints(resetBtn, 4, 6, 1, 2);
	    GridPane.setConstraints(backBtn, 5, 6, 1, 2);
	    
		this.getChildren().addAll(
				text,
				audioLbl, audio,
				inputRBtn, repeatRBtn
			);
		
		if (isEdit)
			this.getChildren().add(updateBtn);
		else
			this.getChildren().add(addBtn);
		
		this.getChildren().addAll(resetBtn, backBtn);
	}
	
	private void addAccessibility() {
		text.focusTraversableProperty().bind(Platform.accessibilityActiveProperty());
		text.setAccessibleText("Type any text here");
	    audioLbl.setLabelFor(audio);
	    audio.setAccessibleText("Select an audio from this combobox");
	    audio.setAccessibleHelp("Press F4 to expand and use the arrow keys to scroll");
	    inputRBtn.setAccessibleText("Prompt 1 wait for user input");
	    inputRBtn.setAccessibleHelp("Press SPACE to check this option");
	    repeatRBtn.setAccessibleText("Prompt 2 repeat scenario content");
	    repeatRBtn.setAccessibleHelp("Press SPACE to check this option");
	    updateBtn.setAccessibleHelp("Press SPACE to select");
	    addBtn.setAccessibleHelp("Press SPACE to select");
	    resetBtn.setAccessibleHelp("Press SPACE to select");
	    backBtn.setAccessibleHelp("Press SPACE to select");
	}
}
