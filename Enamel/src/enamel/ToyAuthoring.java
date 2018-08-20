package enamel;

import javafx.application.Application;
import javafx.stage.*;

public class ToyAuthoring extends Application {
	
	public static void main(String[] args) { 	  	
    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	ScenarioEditorView mainView = new ScenarioEditorView();
    	ScenarioEditorPresenter mainPresenter = new ScenarioEditorPresenter(mainView);
    	mainView.display();
    }
}