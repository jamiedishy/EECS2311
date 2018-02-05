package enamel;

import java.io.File;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ToyAuthoring extends Application {
	
	Stage window;
	Scene startMenu;

    public static void main(String[] args) { 	
    	//JFileChooser fc = new JFileChooser();
    	//int returnVal = fc.showOpenDialog(fc);
    	//File file = fc.getSelectedFile();
    	//ScenarioParser s = new ScenarioParser(true);
    	//s.setScenarioFile("FactoryScenarios/Scenario_" + 3 + ".txt");
    	//s.setScenarioFile(file.getAbsolutePath());
    	
    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	window = primaryStage;
    	window.setTitle("Start");
    	
    	Button createButton = new Button("Create Scenario");
    	createButton.setOnAction(e -> ScenarioEditor.display());
    	
    	Button editButton = new Button("Edit Scenario");
    	
    	
    	Button audioButton = new Button("Audio Manager");
    	
    	
    	Button playButton = new Button("Play Scenario");
    	playButton.setOnAction(e -> playScenario());   	
    	
    	VBox layout = new VBox(16);
    	layout.getChildren().addAll(createButton, editButton, audioButton, playButton);
    	layout.setAlignment(Pos.CENTER);
    	
    	startMenu = new Scene(layout, 100, 200);
    	window.setScene(startMenu);
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
    

}