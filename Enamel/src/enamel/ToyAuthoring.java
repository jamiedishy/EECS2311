package enamel;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ToyAuthoring extends Application {
	
	Stage window;
	Scene startMenu;


    public static void main(String[] args) { 	

    	//Create a file chooser
  	  JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
  	    
  	    //In response to a button click:
  	  int returnVal = fc.showOpenDialog(null);
  	  
  	  if (returnVal == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
		}  
    	
  	  	ScenarioParser s = new ScenarioParser(true);
    	    s.setScenarioFile("FactoryScenarios/Scenario_" + 2 + ".txt");
    	    
    	  

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
/* OLD CODE
      	window = primaryStage;
    	window.setTitle("Start");
    	
    	Button createButton = new Button("Create Scenario");    	
    	Button editButton = new Button("Edit Scenario");   	
    	Button audioButton = new Button("Audio Manager");
    	Button playButton = new Button("Play Scenario");
    	
    	createButton.setMaxWidth(Double.MAX_VALUE);
    	editButton.setMaxWidth(Double.MAX_VALUE);
    	audioButton.setMaxWidth(Double.MAX_VALUE);
    	playButton.setMaxWidth(Double.MAX_VALUE);

    	createButton.setOnAction(e -> ScenarioEditor.display());
    	playButton.setOnAction(e -> playScenario());    	
    	
    	VBox layout = new VBox(16);
    	layout.setPadding(new Insets(20, 20, 20, 20));
    	layout.getChildren().addAll(createButton, editButton, audioButton, playButton);
    	layout.setAlignment(Pos.CENTER);

    	startMenu = new Scene(layout);
    	window.setScene(startMenu);
    	window.show();
 */
    	ScenarioEditor main = new ScenarioEditor();
    	main.display();
    }
/*    
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
*/
}