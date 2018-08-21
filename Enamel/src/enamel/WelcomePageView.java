package enamel;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WelcomePageView extends VBox {

	Label text;
	
	public WelcomePageView() {
		text = new Label();
		layoutPage();
		addAccessibility();
	}

	private void layoutPage() {
		this.setSpacing(10);
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(text);

		text.setText("Welcome to\nSCENARIO EDITOR");
		text.setId("welcome");
	}
	
	private void addAccessibility() {
		text.focusTraversableProperty().bind(Platform.accessibilityActiveProperty());
		text.setAccessibleText("To start, hit Alt on your keyboard to access the Menu bar\n" +
						   	   "or use some of the predefined keyboard shortcuts.");
	}
	
}
