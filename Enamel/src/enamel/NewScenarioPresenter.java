package enamel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class NewScenarioPresenter {
	
	private ScenarioFile model;
	private final NewScenarioView view;
	
	public NewScenarioPresenter(NewScenarioView view) {
		this.model = null;
		this.view = view;
		attachEvents();
	}
	
	private void attachEvents() {
		view.createBtn.disableProperty().bind(view.filename.textProperty().isEmpty());
		
		view.createBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				model = new ScenarioFile();
				model.setFilename(view.filename.getText() + ".txt");
				model.setCellcount(view.cellCBox.getValue());
				model.setButtoncount(view.buttonCBox.getValue());				
				view.window.close();
			}
		});
		
		view.cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				view.window.close();
			}
		});
	}
	
	public ScenarioFile getModel() { return model; }
	
}
