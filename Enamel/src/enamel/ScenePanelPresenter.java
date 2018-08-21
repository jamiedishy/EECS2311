package enamel;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;

public class ScenePanelPresenter {
	
	ArrayList<ScenarioModule> scenes;
	ScenePanelView view;
	ScenarioEditorPresenter parent;
	
	public ScenePanelPresenter(ScenePanelView view, ScenarioEditorPresenter parent) {
		this.view = view;
		this.parent = parent;
		this.scenes = new ArrayList<ScenarioModule>();
		attachEvents();
		addListener();
		makeAccessible();
	}

	private void addListener() {

		view.root.leafProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				makeAccessible();
			}
			
        });
		
	}

	private void attachEvents() {
        view.editBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				
				if (getBranch() == null)
					return;
				
				//disablePanel(true);
				if (getScene() instanceof StoryModule)
					parent.displayPage(Page.STORY, getScene(), getBranch());
				
				if (getScene() instanceof QuizModule)
					parent.displayPage(Page.QUIZ, getScene(), getBranch());
				
				if (getScene() instanceof BrailleModule)
					parent.displayPage(Page.BRAILLE, getScene(), getBranch());		
								
				parent.log.add(Actions.EDIT_SCENE);
			}
        });
        
        view.delBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (currIdx() < scenes.size()) {
					scenes.remove(currIdx());
					view.root.getChildren().remove(currIdx());
					
					parent.log.add(Actions.DELETE_SCENE);
				}
			}
        });
        
		view.upBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				int i = currIdx();
				if (i > 0 && i < scenes.size()) {					
					// swap in arraylist
					ScenarioModule temp = scenes.get(i);
					scenes.set(i, scenes.get(i - 1));
					scenes.set(i - 1, temp);
					
					// swap in tree
					TreeItem<String> item = getBranch();
			        List<TreeItem<String>> list = new ArrayList<TreeItem<String>>();
			        Object prev = null;
			        for (Object child : view.root.getChildren()) {
			            if (child == item) {
			                list.add((TreeItem<String>) child);
			            } else {
			                if (prev != null) 
			                	list.add((TreeItem<String>) prev);
			                
			                prev = child;
			            }
			        }
			        if (prev != null) 
			        	list.add((TreeItem<String>) prev);
			        
			        view.root.getChildren().clear();
			        view.root.getChildren().addAll(list);
			        view.tree.getSelectionModel().select(item);
			        
					parent.log.add(Actions.UP_SCENE);
				}
			}
		});
				
		view.downBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				if (currIdx() < scenes.size() - 1) {
					int i = currIdx();
					
					// swap in arraylist
					ScenarioModule temp = scenes.get(i);
					scenes.set(i, scenes.get(i + 1));
					scenes.set(i + 1, temp);
					
					// swap in tree
					TreeItem<String> item = getBranch();
			        List<TreeItem<String>> list = new ArrayList<TreeItem<String>>();
			        TreeItem<String> prev = null;
			        
			        for (TreeItem<String> child : view.root.getChildren()) {    	
			            if (child != item) {
			            	list.add(child);
			            	
			                if (prev == item) 
			                	list.add(prev);
			            }
			            prev = child;
			        }
			        
			        view.root.getChildren().clear();
			        view.root.getChildren().addAll(list);
			        view.tree.getSelectionModel().select(item);
			        
					parent.log.add(Actions.DOWN_SCENE);
				}
			}
		});
		
	}
	
	public void update(ArrayList<ScenarioModule> modules) {
		if (modules != null) {
	    	for (ScenarioModule content : modules) {
	    		scenes.add(content);
	    		view.root.getChildren().addAll(content.write(new TreeItem<String>()));
	    	}
		}
	}
	
	/*
	 * Returns the branch containing the header
	 */
	public TreeItem<String> getBranch() {
		if (view.root.getChildren().isEmpty())
			return null;
		
		TreeItem<String> branch = view.tree.getSelectionModel().getSelectedItem();
		
		if (branch != null && branch.getParent() != view.root)
			branch = branch.getParent();
		
		return branch;
	}
	
	public ScenarioModule getScene() {	
		return scenes.get(currIdx());
	}
	
	public int currIdx() {
		TreeItem<String> item = getBranch();
		int i = 0;
		
		for (TreeItem<String> child : view.root.getChildren()) {
			if (child == item)
				break;
			
			i++;
		}
		
		return i;
	}
	
	public void disablePanel(boolean val) {
		view.tree.setDisable(val);
		view.editBtn.setDisable(val);
		view.delBtn.setDisable(val);
		view.upBtn.setDisable(val);
		view.downBtn.setDisable(val);
	}
	
	/*
	 * Reset panel and data
	 * 
	 */
	public void reset() {
		this.scenes = new ArrayList<ScenarioModule>();
		view.root.getChildren().clear();
	}
	
	private void makeAccessible() {
		boolean b = currIdx() > 0;
		view.tree.setFocusTraversable(b);		
	}
			
}
