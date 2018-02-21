package enamel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewScenarioDialog {

	private static String header;
	
	protected static String display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("New Scenario");
		window.setResizable(false);

		GridPane layout = new GridPane();
		layout.setPadding(new Insets(5, 5, 5, 5));
        layout.setVgap(10);
        layout.setHgap(12);
        layout.setAlignment(Pos.CENTER);
        
		Label cellLabel = new Label("Braille Cells");
		Label btnLabel = new Label("Buttons");
		Label fileLabel = new Label("Filename");
		Label extLabel = new Label(".txt");
		TextField fileText = new TextField();		
		Button createButton = new Button("Create");
		Button cancelButton = new Button("Cancel");
		ComboBox cellInput = new ComboBox();
		ComboBox btnInput = new ComboBox();
		HBox filenameInputLayout = new HBox(5);
		filenameInputLayout.getChildren().addAll(fileText, extLabel);
		HBox buttonsLayout = new HBox(40);
		buttonsLayout.setAlignment(Pos.CENTER);
		buttonsLayout.getChildren().addAll(createButton, cancelButton);
		
		cellInput.getItems().addAll("1", "2", "3", "4");
		cellInput.setValue("1");
		btnInput.getItems().addAll("1", "2", "3", "4");		
		btnInput.setValue("1");

		GridPane.setConstraints(fileLabel, 0, 0);
		GridPane.setConstraints(filenameInputLayout, 1, 0);
		GridPane.setConstraints(cellLabel, 0, 1);
		GridPane.setConstraints(cellInput, 1, 1);
		GridPane.setConstraints(btnLabel, 0, 2);
		GridPane.setConstraints(btnInput, 1, 2);
		GridPane.setConstraints(buttonsLayout, 0, 3, 2, 1);

		layout.getChildren().addAll(
				cellLabel, cellInput,
				btnLabel, btnInput,
				fileLabel, filenameInputLayout,
				buttonsLayout
				);
		
		fileText.setText("asd");
		createButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				header = new String("");
				header += fileText.getText() + ".txt/";
				header += cellInput.getValue().toString() + "/";
				header += btnInput.getValue().toString();
				window.close();
			}
		});
		
		cancelButton.setOnAction(e -> window.close());
		
		Scene newDialog = new Scene(layout);
		window.setScene(newDialog);
		window.showAndWait();
		
		return header;
	}

}
