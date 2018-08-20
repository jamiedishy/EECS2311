package enamel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewScenarioView {
	
	Stage window;
	GridPane mainLayout;
	HBox filenameLayout, buttonsLayout;
	
	Label cellLbl, buttonLbl, fileLbl, extLbl;
	TextField filename;	
	Button createBtn, cancelBtn;
	ComboBox<Integer> cellCBox, buttonCBox;
	
	public NewScenarioView() {
		window = new Stage();
		mainLayout = new GridPane();
		cellLbl = new Label("Braille Cells");
		buttonLbl = new Label("Buttons");
		fileLbl = new Label("Filename");
		extLbl = new Label(".txt");
		filename = new TextField();		
		createBtn = new Button("Create");
		cancelBtn = new Button("Cancel");
		cellCBox = new ComboBox<Integer>();
		buttonCBox = new ComboBox<Integer>();
		filenameLayout = new HBox();
		buttonsLayout = new HBox();
		layoutWindow();
		addAccessibility();
	}

	private void layoutWindow() {
		mainLayout.setPadding(new Insets(5, 5, 5, 5));
        mainLayout.setVgap(10);
        mainLayout.setHgap(12);
        mainLayout.setAlignment(Pos.CENTER);
		
		filenameLayout.setSpacing(5);
		filename.setPromptText("Type filename here");
		filename.setAlignment(Pos.CENTER_LEFT);
		filenameLayout.getChildren().addAll(filename, extLbl);
		
		buttonsLayout.setSpacing(40);
		buttonsLayout.setAlignment(Pos.CENTER);
		buttonsLayout.getChildren().addAll(createBtn, cancelBtn);
		
		for (Integer i = 1; i <= ScenarioFile.MAX_CELL; i++) {
			cellCBox.getItems().addAll(i);
		}
		cellCBox.setValue(1);
		
		for (Integer i = 1; i <= ScenarioFile.MAX_BUTTONS; i++) {
			buttonCBox.getItems().addAll(i);
		}
		buttonCBox.setValue(1);

		GridPane.setConstraints(fileLbl, 0, 0);
		GridPane.setConstraints(filenameLayout, 1, 0);
		GridPane.setConstraints(cellLbl, 0, 1);
		GridPane.setConstraints(cellCBox, 1, 1);
		GridPane.setConstraints(buttonLbl, 0, 2);
		GridPane.setConstraints(buttonCBox, 1, 2);
		GridPane.setConstraints(buttonsLayout, 0, 3, 2, 1);

		mainLayout.getChildren().addAll(
			fileLbl, filenameLayout,
			cellLbl, cellCBox,
			buttonLbl, buttonCBox,
			buttonsLayout
		);
	}

	public void display() {
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("New Scenario");
		window.setResizable(false);		
		
		Scene main = new Scene(mainLayout);
		main.getStylesheets().add("default.css");
		mainLayout.setId("dialog");
		window.setScene(main);
		window.showAndWait();
	}
	
	private void addAccessibility() {

		fileLbl.setLabelFor(filename);
		filename.setAccessibleHelp("Type the desired filename here");	

		cellLbl.setLabelFor(cellCBox);
		cellCBox.setAccessibleHelp("Choose from 1 to 4. Press F4 to expand and use the arrow keys to scroll");
		
		buttonLbl.setLabelFor(buttonCBox);
		buttonCBox.setAccessibleHelp("Choose from 1 to 4. Press F4 to expand and use the arrow keys to scroll");
		
		createBtn.setAccessibleHelp("Press SPACE to confirm");
		cancelBtn.setAccessibleHelp("Press SPACE to cancel");
		
	}
	
}
