package enamel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class BrailleCellPresenter {

	BrailleCell bc;
	BrailleCellView view;
	BraillePagePresenter parent;
    final int LIMIT = 1;
	
	public BrailleCellPresenter(BrailleCellView view, BraillePagePresenter parent) {
		this.bc = new BrailleCell();
		this.view = view;
		this.parent = parent;
		attachEvents();		
	}

	private void attachEvents() {

        view.charInput.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (view.charInput.getText().length() >= LIMIT) {
                        view.charInput.setText(view.charInput.getText().substring(0, LIMIT));
                    }
                }
                
                if (newValue.intValue() == 0) {
                	for (int i = 0; i < view.NUM_PINS; i++)
            			view.pins[i].setDisable(false);
                }
            }
        });
        
        view.charInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {             
                if (!view.charInput.getText().toLowerCase().matches("[a-z]"))
                	view.charInput.setText("");
                else {
        			try {
						bc.displayCharacter(view.charInput.getText().toLowerCase().charAt(0));
						updateGUI();
						disablePins(true);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}               	
                }
                
            }
        });
        
		view.cbClear.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				if (view.cbClear.isSelected()) {
					clear();
					setDisable(true);
				}
				else {
					setDisable(false);			
				}
			}
		});
	
	}		

/*	public void setPin(int index, RadioButton rb) {
		pins[index] = rb;
	}*/
	
	public String update() {
		String state = "";
		for (int i = 0; i < view.NUM_PINS; i++) {
			if (view.pins[i].isSelected())
				state += "1";
			else
				state += "0";
		}
		bc.setPins(state);
		return state;
	}
	
	public void updateGUI() {
		for (int i = 0; i < view.NUM_PINS; i++) {
			view.pins[i].setSelected(bc.getPinState(i));
			//view.pins[i].setDisable(true);
		}
	}
	
	public void disablePins(boolean val) {
		for (int i = 0; i < view.NUM_PINS; i++) {
			view.pins[i].setDisable(val);
		}		
	}
	
	public void clear() {
		bc.clear();
		view.charInput.setText("");
		for (int i = 0; i < view.NUM_PINS; i++) {
			view.pins[i].setSelected(false);
			view.pins[i].setDisable(false);
		}
	}
	
	public void setDisable(boolean isDisable) {
		if (isDisable) {
			for (int i = 0; i < view.NUM_PINS; i++) {
				view.pins[i].setDisable(true);
			}			
			view.charInput.setDisable(true);			
		} else {
			for (int i = 0; i < view.NUM_PINS; i++) {
				view.pins[i].setDisable(false);
			}			
			view.charInput.setDisable(false);							
		}
	}
	
	public boolean isEmpty() {
		int count = 0;
		
		for (int i = 0; i < view.NUM_PINS; i++) {
			if (view.pins[i].isSelected())
				count++;
		}			
		
		return (count > 0) ? false : true;
	}
}
