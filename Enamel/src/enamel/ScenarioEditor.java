package enamel;

import javafx.stage.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ScenarioEditor {

	public static void display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Scenario Editor");
		window.setFullScreen(true);
		
		window.showAndWait();
	}
}
