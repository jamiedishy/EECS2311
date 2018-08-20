package enamel;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartPageView extends VBox {

	Label startPrompt;
	Button createStoryBtn, createQuizBtn, createBrailleBtn;
	
	public StartPageView() {
    	startPrompt = new Label();
    	createStoryBtn = new Button("Story");
    	createQuizBtn = new Button("Quiz");
    	createBrailleBtn = new Button("Braille");
    	layoutPage();
    	addAccessibility();
	}

	private void layoutPage() {
		this.setSpacing(16);
		this.setAlignment(Pos.CENTER);
		
    	startPrompt.setText("What would you like to create?");

    	this.getChildren().addAll(
    			startPrompt,
    			createStoryBtn,
    			createQuizBtn,
    			createBrailleBtn
    		);		
	}

	private void addAccessibility() {
		startPrompt.focusTraversableProperty().bind(Platform.accessibilityActiveProperty());
		startPrompt.setAccessibleText("Start creating scenarios by choosing one of the following.\n" +
			   	   					  "Some predefined keyboard shortcuts are also available");
		
		createStoryBtn.setAccessibleText("Create a story scenario");
		createStoryBtn.setAccessibleHelp("Press SPACE to select");
		
		createQuizBtn.setAccessibleText("Create a quiz scenario");
		createQuizBtn.setAccessibleHelp("Press SPACE to select");
		
		createBrailleBtn.setAccessibleText("Create a braille scenario");
		createBrailleBtn.setAccessibleHelp("Press SPACE to select");
	}
}
