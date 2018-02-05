package enamel;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ToyAuthoring {

    public static void main(String[] args) { 	
    	//JFileChooser fc = new JFileChooser();
    	//int returnVal = fc.showOpenDialog(fc);
    	//File file = fc.getSelectedFile();
    	ScenarioParser s = new ScenarioParser(true);
    	s.setScenarioFile("FactoryScenarios/Scenario_" + 3 + ".txt");
    	//s.setScenarioFile(file.getAbsolutePath());
    }
}