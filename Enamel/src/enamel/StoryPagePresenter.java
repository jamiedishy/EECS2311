package enamel;

import com.sun.javafx.scene.control.skin.TextAreaSkin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class StoryPagePresenter extends PagePresenter {
	
	private StoryModule model;
	private final StoryPageView view;
	private final ScenarioEditorPresenter parent;
	TreeItem<String> branch;
	
	public StoryPagePresenter(StoryPageView view, ScenarioEditorPresenter parent) {
		this.view = view;
		this.parent = parent;
		attachEvents();
	}
	
	public StoryPagePresenter(StoryPageView view, ScenarioEditorPresenter parent, ScenarioModule model, TreeItem<String> branch) {
		this(view, parent);
		this.model = (StoryModule) model;
		this.branch = branch;
		populateFields();
	}

	private void attachEvents() {
        view.addBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				model = new StoryModule(parent.CELL_COUNT.get(), parent.BUTTON_COUNT.get());
				branch = new TreeItem<String>();
				setModel();				

				if (!branch.getChildren().isEmpty()) {
					parent.model.addContent(model);
					parent.listPresenter.scenes.add(model);
					parent.listPresenter.view.root.getChildren().addAll(branch);
				}
				//parent.updateView();
				view.resetBtn.fire();
				
				parent.log.add(Actions.NEW_STORY);
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
				view.text.setText(null);
				view.audio.setValue(null);
				view.inputRBtn.setSelected(false);
				view.repeatRBtn.setSelected(false);
			}
		});
		
		view.backBtn.setOnAction(e -> parent.displayPage(Page.START));
		
		view.text.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
			    if (event.getCode().equals(KeyCode.TAB)) {
			        Node node = (Node) event.getSource();
			        if (node instanceof TextArea) {
			            TextAreaSkin skin = (TextAreaSkin) ((TextArea)node).getSkin();
			            if (event.isShiftDown()) {
			                skin.getBehavior().traversePrevious();
			            }
			            else {
			                skin.getBehavior().traverseNext();
			            }
			        }

			        event.consume();
			    }
			}
	    });
				
	}
	
	private void setModel() {
		model.setText(view.text.getText());
		model.setAudio((view.audio.getValue() == null) ? null : view.audio.getValue().toString());
		model.setRepeat(view.repeatRBtn.isSelected());
		model.setWait(view.inputRBtn.isSelected());
		branch = model.write(branch);
	}
	
	public StoryModule getModel() { return model; }
	
	private void populateFields() {
		if (model.getText() != null)
			view.text.setText(model.getText());
		
		if (model.getAudio() != null)
			view.audio.setValue(model.getAudio());
		
		view.repeatRBtn.setSelected(model.getRepeat());
		
		view.inputRBtn.setSelected(model.getWait());
	}
	
	private boolean isEmpty() {
		if ( (view.text.getText() == null || view.text.getText().trim().isEmpty()) &&
			 (view.audio.getValue() == null) &&
			 (!view.repeatRBtn.isSelected()) &&
			 (!view.inputRBtn.isSelected())					
			)
			return true;
		
		return false;
	}
	
}
