package enamel;

import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.File;
import javafx.geometry.*;

public class ScenarioEditor {

	Stage window;
	BorderPane mainLayout;
	int CELL_COUNT = 0;
	int BUTTON_COUNT = 0;
	String FILENAME = "";
	
	protected void display() {
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Scenario Editor");
		window.setMinHeight(720);
		window.setMaxHeight(720);
		window.setMinWidth(1360);
		window.setMaxWidth(1360);
		window.setResizable(false);

		mainLayout = new BorderPane();
		
		// Menu bar
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		MenuItem miNew = new MenuItem("New...");
		MenuItem miOpen = new MenuItem("Open...");
		MenuItem miSave = new MenuItem("Save");
		MenuItem miSaveAs = new MenuItem("Save as...");
		MenuItem miPlay = new MenuItem("Play scenario");
		MenuItem miClose = new MenuItem("Close file");
		MenuItem miExit = new MenuItem("Exit");
		menuFile.getItems().addAll(
				miNew, 
				miOpen, 
				new SeparatorMenuItem(), 
				miSave, 
				miSaveAs,
				new SeparatorMenuItem(),
				miPlay,
				new SeparatorMenuItem(),
				miClose,
				new SeparatorMenuItem(),
				miExit);
		Menu menuAudio = new Menu("Audio");
		MenuItem miUpload = new MenuItem("Upload...");
		MenuItem miRecord = new MenuItem("Record...");
		menuAudio.getItems().addAll(miUpload, miRecord);
		Menu menuHelp = new Menu("Help");
		menuBar.getMenus().addAll(menuFile, menuAudio, menuHelp);
		mainLayout.setTop(menuBar);
		
		// Side list
		ListView<String> storyList = new ListView<String>();
		mainLayout.setLeft(storyList);
		
		// Main page
		Label filler = new Label("Welcome Screen");
		mainLayout.setCenter(filler);

		// Status bar
		HBox statusBar = new HBox(16);
		statusBar.setPadding(new Insets(5, 5, 5, 5));
		Label fileName = new Label();
		fileName.setText(FILENAME);
		Label bcellCount = new Label();
		bcellCount.setText("Braille Cells: " + CELL_COUNT);
		Label buttonCount = new Label();
		buttonCount.setText("Buttons: " + BUTTON_COUNT);
		statusBar.getChildren().addAll(bcellCount, buttonCount);
		mainLayout.setBottom(statusBar);
		
		// Event Handlers
		miPlay.setOnAction(e -> playScenario());
		miNew.setOnAction(e -> newScenario());
		
		
		
		
		// Paint the whole window and show
		Scene editor = new Scene(mainLayout);
		window.setScene(editor);
		window.show();
	}
	
    private void playScenario() {
    	FileChooser fc = new FileChooser();
    	File defaultDir = new File(System.getProperty("user.dir") + File.separator + "FactoryScenarios");
    	fc.setTitle("Open a scenario file...");
    	fc.setInitialDirectory(defaultDir);
    	fc.getExtensionFilters().addAll(
    			new FileChooser.ExtensionFilter("Scenario file (*.txt)","*.txt")
    		);
    	File chosenFile = fc.showOpenDialog(window);
    	if (chosenFile != null) {
	    	ScenarioParser s = new ScenarioParser(true);
	    	s.setScenarioFile(chosenFile.getAbsolutePath());
    	}
    }
    
    private void newScenario() {
    	VBox startPage = new VBox(16);
    	startPage.setAlignment(Pos.CENTER);
    	Label startPrompt = new Label();
    	startPrompt.setText("What would you like to create?");
    	Button createStory = new Button("Story");
    	Button createQuiz = new Button("Quiz");
    	startPage.getChildren().addAll(startPrompt, createStory, createQuiz);
    	
    	createStory.setOnAction(e -> createStory());
    	createQuiz.setOnAction(e -> createQuiz());
    	mainLayout.setCenter(startPage);
    }
    
    private void createStory() {
    	return;
    }
    
    private void createQuiz() {
    	return;
    }
}
