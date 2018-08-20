package enamel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;

public class BraillePagePresenter extends PagePresenter {

	private BrailleModule model;
	private final BraillePageView view;
	private final ScenarioEditorPresenter parent;
	private final BrailleCellPresenter[] child;
	TreeItem<String> branch;
	
	public BraillePagePresenter(BraillePageView view, ScenarioEditorPresenter parent) {
		this.view = view;
		this.parent = parent;
		this.child = new BrailleCellPresenter[parent.CELL_COUNT.get()];
		
		for (int i = 0; i < parent.CELL_COUNT.get(); i++) {
			child[i] = new BrailleCellPresenter(view.bcells[i], this);
		}
		
		attachEvents();
	}
	
	public BraillePagePresenter(BraillePageView view, ScenarioEditorPresenter parent, ScenarioModule model, TreeItem<String> branch) {
		this(view, parent);
		this.model = (BrailleModule) model;
		this.branch = branch;
		populateFields();
	}

	private void attachEvents() {
        view.addBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				model = new BrailleModule(parent.CELL_COUNT.get(), parent.BUTTON_COUNT.get());
				branch = new TreeItem<String>();
				setModel();				

				if (!branch.getChildren().isEmpty()) {
					parent.model.addContent(model);
					parent.listPresenter.scenes.add(model);
					parent.listPresenter.view.root.getChildren().addAll(branch);
				}

				view.resetBtn.fire();				
				parent.log.add(Actions.NEW_BRAILLE);
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
		        for (int i = 0; i < parent.CELL_COUNT.get(); i++){
					child[i].clear();
					view.bcells[i].cbClear.setSelected(false);
					view.bcells[i].charInput.setDisable(false);
		        }    
			}
		});
				
		view.backBtn.setOnAction(e -> parent.displayPage(Page.START));
	}
	
	private void setModel() {
		for (int i = 0; i < model.CELL_COUNT; i++) {			
			model.setClearSelected(i, child[i].view.cbClear.isSelected());
			model.setCharacter(i, child[i].view.charInput.getText());
			model.setCellState(i, child[i].update());
		}
		
		branch = model.write(branch);
	}
	
	public BrailleModule getModel() { return model; }
	
	private void populateFields() {
		
		for (int i = 0; i < model.CELL_COUNT; i++) {
			if (model.getClearSelected(i)) {
				child[i].view.cbClear.setSelected(true);
				continue;
			}
			
			if (model.getCharacter(i) != null && !model.getCharacter(i).trim().isEmpty()) {
				child[i].view.charInput.setText(model.getCharacter(i));
				continue;
			}
			
			if (!model.isClear(i)) {
				child[i].bc.setPins(model.getStrState(i));
				child[i].updateGUI();
			}
		}
		
	}
	
	private boolean isEmpty() {

		for (int i = 0; i < model.CELL_COUNT; i++) {
			if (child[i].view.cbClear.isSelected())
				return false;
			
			if (!child[i].isEmpty())
				return false;
		}	
		
		return true;
	}
}
