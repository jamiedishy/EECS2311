package enamel;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;

public class QuizPagePresenter extends PagePresenter {
	
	private QuizModule model;
	private final QuizPageView view;
	private final ScenarioEditorPresenter parent;
	TreeItem<String> branch;
	
	public QuizPagePresenter(QuizPageView view, ScenarioEditorPresenter parent) {
		this.view = view;
		this.parent = parent;
		attachEvents();
	}
	
	public QuizPagePresenter(QuizPageView view, ScenarioEditorPresenter parent, ScenarioModule model, TreeItem<String> branch) {
		this(view, parent);
		this.model = (QuizModule) model;
		this.branch = branch;
		populateFields();
	}

	private void attachEvents() {
        view.addBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				model = new QuizModule(parent.CELL_COUNT.get(), parent.BUTTON_COUNT.get());
				branch = new TreeItem<String>();
				setModel();				

				if (!branch.getChildren().isEmpty()) {
					parent.model.addContent(model);
					parent.listPresenter.scenes.add(model);
					parent.list.root.getChildren().addAll(branch);
				}
				//parent.updateView();
				view.resetBtn.fire();
				
				parent.log.add(Actions.NEW_QUIZ);
			}
        });
        
        view.updateBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (isEmpty()) {
					new AlertDialog("Empty scene", "Scene on edit cannot be empty");
					return;
				}
							
				setModel();
				parent.displayPage(Page.START);
			}
        });
        
		view.resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				view.question.setText(null);
		        for (int i = 0; i < parent.BUTTON_COUNT.get(); i++){
		        	view.response.get(i).setText(null);
		        	view.audio.get(i).setValue(null);
		        }    
			}
		});
		
		view.backBtn.setOnAction(e -> parent.displayPage(Page.START));
	}
	
	private void setModel() {
		ArrayList<String> reply = new ArrayList<String>();
		ArrayList<String> cues = new ArrayList<String>();
		
		model.setQuestion(view.question.getText());
        for (int i = 0; i < parent.BUTTON_COUNT.get(); i++) {
        	reply.add(view.response.get(i).getText());
        	cues.add((view.audio.get(i).getValue() == null) ? null : view.audio.get(i).getValue().toString());
        }		
		
		model.setResponses(reply);
		model.setAudioCues(cues);
		branch = model.write(branch);
	}
	
	public QuizModule getModel() { return model; }
	
	private void populateFields() {
		if (model.getQuestion() != null)
			view.question.setText(model.getQuestion());
		
		if (model.getAudioCues() != null) {
			for (int i = 0; i < model.BUTTON_COUNT; i++) {
				if (model.getAudioCues().get(i) != null)
					view.audio.get(i).setValue(model.getAudioCues().get(i));
			}
		}
		
		if (model.getResponses() != null) {
			for (int i = 0; i < model.BUTTON_COUNT; i++) {
				if (model.getResponses().get(i) != null)
					view.response.get(i).setText(model.getResponses().get(i));
			}			
		}
	}
	
	private boolean isEmpty() {

		if ( view.question.getText() != null && !view.question.getText().trim().isEmpty() )
			return false;
		
		if (model.getAudioCues() != null) {
			for (int i = 0; i < model.BUTTON_COUNT; i++) {
				if (model.getAudioCues().get(i) != null)
					return false;
			}
		}
		
		if (model.getResponses() != null) {
			for (int i = 0; i < model.BUTTON_COUNT; i++) {
				if (model.getResponses().get(i) != null)
					return false;
			}			
		}		
		
		return true;
	}
}
