package enamel;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.geometry.*;

public class AlertDialog {
	
	private Stage window;
	private VBox layout;	
	
	private Label message;
	private Button okBtn;
	
	public AlertDialog(String title, String message) {
		this.window = new Stage();
		this.layout = new VBox();
		this.message = new Label(message);
		this.okBtn = new Button("OK");
		layoutWindow(title);
		addAccessibility();
		attachEvents();
		display();
	}
	
	private void layoutWindow(String title) {
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
		window.setResizable(false);		
		
        message.setWrapText(true);
        message.setMaxWidth(250);
        message.setMinWidth(250);
        message.setAlignment(Pos.CENTER);
        message.setTextAlignment(TextAlignment.CENTER);
        
        layout.setSpacing(30);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);  
        layout.getChildren().addAll(message, okBtn);      
	}
	
	private void addAccessibility() {
		message.focusTraversableProperty().bind(Platform.accessibilityActiveProperty());
	}

	private void attachEvents() {
        okBtn.setOnAction(e -> window.close());
	}
	
    private void display() {
        Scene scene = new Scene(layout);
		scene.getStylesheets().add("default.css");
		layout.setId("dialog");
        window.setScene(scene);
        window.showAndWait();
    }

}