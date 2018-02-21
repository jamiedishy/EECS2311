package enamel;

import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.File;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;

public class ScenarioEditor {

	Stage window;
	BorderPane mainLayout;
	IntegerProperty CELL_COUNT = new SimpleIntegerProperty(0);
	IntegerProperty BUTTON_COUNT = new SimpleIntegerProperty(0);
	StringProperty FILENAME = new SimpleStringProperty();
	
	protected void display() {
		FILENAME.set("");
		
		window = new Stage();
		//window.initModality(Modality.APPLICATION_MODAL);
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
		fileName.setText(FILENAME.get());
		Label bcellCount = new Label();

		Label buttonCount = new Label();

		statusBar.getChildren().addAll(
				new Label("Braille Cell(s): "), bcellCount, 
				new Label("Button(s): "), buttonCount, 
				fileName
			);
		mainLayout.setBottom(statusBar);
		
		fileName.textProperty().bind(FILENAME);
		bcellCount.textProperty().bind(CELL_COUNT.asString());
		buttonCount.textProperty().bind(BUTTON_COUNT.asString());
		
		// Event Handlers
		miPlay.setOnAction(e -> playScenario());
		miNew.setOnAction(e -> newScenario());
		miOpen.setOnAction(e -> openScenario());
		miExit.setOnAction(e -> window.close());
		
		
		// Paint the whole window and show
		Scene editor = new Scene(mainLayout);
		window.setScene(editor);
		window.show();
	}
	
    private void openScenario() {
		File chosenFile = fc("Play a scenario file...", "*.txt");
		System.out.println("FILE: " + chosenFile.toString());
	}

	private void playScenario() {
    	File chosenFile = fc("Open a scenario file...", "*.txt");
    	if (chosenFile != null) {
	    	ScenarioParser s = new ScenarioParser(true);
	    	s.setScenarioFile(chosenFile.getAbsolutePath());
    	}
    }
    
    private File fc(String title, String ext) {
    	FileChooser fc = new FileChooser();
    	File defaultDir = new File(System.getProperty("user.dir") + File.separator + "FactoryScenarios");
    	fc.setTitle(title);
    	fc.setInitialDirectory(defaultDir);
    	fc.getExtensionFilters().addAll(
    			new FileChooser.ExtensionFilter("Scenario file (" + ext + ")", ext)
    		);
    	return fc.showOpenDialog(window);   	
    }
    
    private void newScenario() {
    	String headers = NewScenarioDialog.display();
    	if (headers == null)
    		return;

    	String[] headerInfo = headers.split("/");
    	FILENAME.set(headerInfo[0]);
    	CELL_COUNT.set(Integer.parseInt(headerInfo[1]));
    	BUTTON_COUNT.set(Integer.parseInt(headerInfo[2]));
   	
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
    	GridPane storyPage = new GridPane();
		storyPage.setPadding(new Insets(5, 5, 5, 5));
        storyPage.setVgap(10);
        storyPage.setHgap(12);
        storyPage.setAlignment(Pos.CENTER);
        storyPage.setGridLinesVisible(true);
        
        TextArea text = new TextArea();
        Label audioLabel = new Label("Audio: ");
        ComboBox audio = new ComboBox();
        ToggleGroup tg = new ToggleGroup();
        RadioButton rbUserinput = new RadioButton("Wait for User input");
        RadioButton rbYesNo = new RadioButton("Repeat input");
        Button add = new Button("Add");
        Button reset = new Button("Reset");
        Button cancel = new Button("Cancel");
       
        File audioFile = new File(System.getProperty("user.dir") + File.separator + 
        						  "FactoryScenarios" + File.separator + 
        						  "AudioFiles");
        if (audioFile.isDirectory()){
        	String[] audioFileList = audioFile.list();
        	for (String s : audioFileList)
        		audio.getItems().addAll(s);
        }
        rbUserinput.setToggleGroup(tg);
        rbYesNo.setToggleGroup(tg);
        
        GridPane.setConstraints(text, 0, 0, 6, 6);
        GridPane.setConstraints(audioLabel, 0, 6, 1, 2);
        GridPane.setConstraints(audio, 1, 6, 1, 2);
        GridPane.setConstraints(rbUserinput, 2, 6);
        GridPane.setConstraints(rbYesNo, 2, 7);
        GridPane.setConstraints(add, 3, 6, 1, 2);
        GridPane.setConstraints(reset, 4, 6, 1, 2);
        GridPane.setConstraints(cancel, 5, 6, 1, 2);

		reset.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				text.clear();
				audio.setValue("");
				rbUserinput.setSelected(false);
				rbYesNo.setSelected(false);
			}
		});
        
		storyPage.getChildren().addAll(
				text,
				audioLabel, audio,
				rbUserinput, rbYesNo,
				add, reset, cancel
			);
		
        mainLayout.setCenter(storyPage);
    }
    
    private void createQuiz() {
    	return;
    }
    
}
