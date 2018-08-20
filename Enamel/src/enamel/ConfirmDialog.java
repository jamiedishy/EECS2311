package enamel;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.geometry.*;

public class ConfirmDialog {
	
	private Stage window;
	private VBox layout;
	private HBox buttonsLayout;
	
	private Label message;
	private Button yesBtn;
	private Button noBtn;
	
	private boolean answer;

    public ConfirmDialog(String title, String message) {
		this.window = new Stage();
		this.layout = new VBox();
		this.buttonsLayout = new HBox();
		this.message = new Label(message);    	
		this.yesBtn = new Button("Yes");
		this.noBtn = new Button("No");
		layoutWindow(title);
		addAccessibility();
		attachEvents();
		display();
    }
    
    public boolean getAnswer() {
    	return answer;
    }
    
    private void layoutWindow(String title) {
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
		window.setResizable(false);
		
        message.setWrapText(true);
        message.setMaxWidth(250);
        message.setMinWidth(250);
        message.setAlignment(Pos.CENTER);
        message.setTextAlignment(TextAlignment.JUSTIFY);
        
        buttonsLayout.setSpacing(40);
        buttonsLayout.setAlignment(Pos.CENTER);
		buttonsLayout.getChildren().addAll(yesBtn, noBtn);        
        
        layout.setSpacing(30);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);    
        layout.getChildren().addAll(message, buttonsLayout);	
    }
    
	private void addAccessibility() {
		message.focusTraversableProperty().bind(Platform.accessibilityActiveProperty());
	}
	
    private void attachEvents() {
        yesBtn.setOnAction(e -> {
            answer = true;
            window.close();
        });
        
        noBtn.setOnAction(e -> {
            answer = false;
            window.close();
        });    	
    }
    
    
    private void display() {
        Scene scene = new Scene(layout);
		scene.getStylesheets().add("default.css");
		layout.setId("dialog");
        window.setScene(scene);
        window.showAndWait();
    }

}