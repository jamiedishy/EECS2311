package enamel;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class QuizPageView extends GridPane {
	
	Label questLbl;
	TextField question;
	Button addBtn, resetBtn, backBtn, updateBtn;
	ArrayList<ComboBox<String>> audio;
	ArrayList<TextField> response;
	ArrayList<Label> numChoice;
	
	int buttonCount;
	boolean isEdit;
	
	public QuizPageView(int bc, boolean state) {
        questLbl = new Label("Question: ");
        question = new TextField(null);
        audio = new ArrayList<ComboBox<String>>();
        response = new ArrayList<TextField>();
        numChoice = new ArrayList<Label>();
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
        
        GridPane.setConstraints(questLbl, 0, 0, 1, 1);
        GridPane.setConstraints(question, 1, 0, 7, 1);
        GridPane.setConstraints(addBtn, 4, 5, 1, 1);
        GridPane.setConstraints(updateBtn, 4, 5, 1, 1);
        GridPane.setConstraints(resetBtn, 5, 5, 1, 1);
        GridPane.setConstraints(backBtn, 6, 5, 1, 1);
        question.setPromptText("Type question with choices here");

        File audioFile = new File(System.getProperty("user.dir") + File.separator + 
				  "FactoryScenarios" + File.separator + 
				  "AudioFiles");
        ArrayList<String> audioList = new ArrayList<String>();
        if (audioFile.isDirectory()){
        	for (String s : audioFile.list()) {
        		if (FilenameUtils.getExtension(s).toLowerCase().equals("wav"))
        			audioList.add(s);
        	}
        }
        
        for (int i = 0; i < buttonCount; i++){

        	audio.add(new ComboBox<String>());
        	response.add(new TextField());
        	//responseLabel.add(new Label("Response:"));
        	numChoice.add(new Label("Option " + (i + 1) + ":"));
        	response.get(i).setText(null);
        	response.get(i).setPromptText("Type response when chosen");

        	GridPane.setConstraints(numChoice.get(i), 0, i + 1, 1, 1);
        	GridPane.setConstraints(audio.get(i), 1, i + 1, 1, 1);
        	GridPane.setConstraints(response.get(i), 2, i + 1, 6, 1);
        	//GridPane.setConstraints(responseLabel.get(i), 2, i + 1, 1, 1);
        	
        	if (audioFile.isDirectory())
        		audio.get(i).getItems().addAll(audioList);
        	
        	this.getChildren().addAll(
    			numChoice.get(i),
				audio.get(i), response.get(i)
				//responseLabel.get(i), 
    		);        	
        }

        this.getChildren().addAll(
			questLbl, question
		);
        
		if (isEdit)
			this.getChildren().add(updateBtn);
		else
			this.getChildren().add(addBtn);
		
		this.getChildren().addAll(resetBtn, backBtn);
	}
	
	private void addAccessibility() {
        questLbl.setLabelFor(question);
        question.setAccessibleText("Type the question and options here");
        
        for (int i = 0; i < buttonCount; i++) {
    	    audio.get(i).setAccessibleText("Select an audio for option " + (i + 1));
    	    audio.get(i).setAccessibleHelp("Press F4 to expand and use the arrow keys to scroll");
    	    numChoice.get(i).setLabelFor(response.get(i));
    	    response.get(i).setAccessibleText("Type response when this option gets selected");
        }
	
	}

}
