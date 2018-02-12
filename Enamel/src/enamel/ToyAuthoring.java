package enamel;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class ToyAuthoring {

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
    	    
    	  
    }
}