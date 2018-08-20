package enamel;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RecordAudioView {

	Stage window;
	GridPane layout;
	
	TextField filename;
	Button startBtn, stopBtn, saveBtn;
	Label message;
		
	public RecordAudioView() {
		window = new Stage();
		layout = new GridPane();
		filename = new TextField();
		startBtn = new Button("Start");
		stopBtn = new Button("Stop");
		saveBtn = new Button("Save");
		message = new Label();
		layoutWindow();
		addAccessibility();
	}
	
	private void layoutWindow() {
		layout.setPadding(new Insets(5, 5, 5, 5));
        layout.setVgap(10);
        layout.setHgap(12);
        layout.setAlignment(Pos.CENTER);
        //layout.setGridLinesVisible(true);
		
		stopBtn.setDisable(true);
		filename.setPromptText("Sample.wav");
		filename.setAlignment(Pos.CENTER);
		
		GridPane.setConstraints(startBtn, 0, 0, 3, 1);
		GridPane.setConstraints(stopBtn, 0, 1, 3, 1);
		GridPane.setConstraints(filename, 0, 2, 3, 1);
		GridPane.setConstraints(saveBtn, 0, 3, 3, 1);
		GridPane.setConstraints(message, 0, 4, 3, 1);
		GridPane.setHalignment(startBtn, HPos.CENTER);
		GridPane.setHalignment(stopBtn, HPos.CENTER);
		GridPane.setHalignment(saveBtn, HPos.CENTER);
		GridPane.setHalignment(message, HPos.CENTER);
		
		layout.getChildren().addAll(
				message, startBtn, stopBtn, filename, saveBtn
		);
	}
	
	public void display() {
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Record Audio");
		window.setResizable(false);
		
		Scene main = new Scene(layout);
		main.getStylesheets().add("default.css");
		layout.setId("dialog");
		window.setScene(main);
		window.showAndWait();	
	}
	
	private void addAccessibility() {
		message.focusTraversableProperty().bind(Platform.accessibilityActiveProperty());
		startBtn.setAccessibleHelp("Press to start recording");
		stopBtn.setAccessibleHelp("Press to stop the recording");
		filename.setAccessibleText("Enter the name of the wav file here");
		saveBtn.setAccessibleHelp("Press to save the recorded session using the provided filename");		
	}
}
