package enamel;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LogStatsView extends GridPane {

	Stage window;
	GridPane layout;
	
	ArrayList<Label> actionName;
	ArrayList<Label> actionCount;
	
	HashMap<Actions, Integer> data;
	
	public LogStatsView(ActionLogger log) {
		window = new Stage();
		layout = new GridPane();
		data = log.data;
		actionName = new ArrayList<Label>();
		actionCount = new ArrayList<Label>();
		
		Label nameHeader = new Label("Actions");
		nameHeader.setId("boldtitle");
		Label countHeader = new Label("Count");
		countHeader.setId("boldtitle");
		
		actionName.add(nameHeader);
		actionCount.add(countHeader);
		
		for (Actions a : data.keySet()) {
			actionName.add(new Label(log.properName(a)));
			actionCount.add(new Label(data.get(a).toString()));
		}

		layoutWindow();
		addAccessibility();
	}
	
	private void layoutWindow() {
		layout.setPadding(new Insets(5, 5, 5, 5));
        layout.setVgap(10);
        layout.setHgap(12);
        layout.setAlignment(Pos.CENTER);
        //layout.setGridLinesVisible(true);
		
		int size = actionName.size();
		for (int i = 0; i < size; i++) {
			GridPane.setConstraints(actionName.get(i), 0, i);
			GridPane.setConstraints(actionCount.get(i), 1, i);
			GridPane.setHalignment(actionName.get(i), HPos.LEFT);
			GridPane.setHalignment(actionCount.get(i), HPos.CENTER);
			layout.getChildren().addAll(actionName.get(i), actionCount.get(i));
		}
		
	}
	
	public void display() {
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Action Stats");
		window.setResizable(false);
		
		Scene main = new Scene(layout);
		main.getStylesheets().add("default.css");
		layout.setId("dialog");
		window.setScene(main);
		window.showAndWait();	
	}
	
	private void addAccessibility() {
		int size = actionName.size();
		for (int i = 0; i < size; i++) {
			actionName.get(i).focusTraversableProperty().bind(Platform.accessibilityActiveProperty());
			actionCount.get(i).focusTraversableProperty().bind(Platform.accessibilityActiveProperty());
		}		
	}
	
	
}
