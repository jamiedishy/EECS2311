package enamel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class BrailleCellGUI {

	BrailleCell bc;
	RadioButton[] pins;
	TextField charInput;
	ListView<String> module;
	CheckBox cbClear;
	int cellIndex;
	final int NUM_PINS = 8;
	
	BrailleCellGUI (ListView<String> list, int index) {
		bc = new BrailleCell();
		pins = new RadioButton[NUM_PINS];
		charInput = new TextField();
		cbClear = new CheckBox("Clear");
		module = list;
		cellIndex = index;

		int LIMIT = 1;
		
        charInput.setPromptText("Enter character here");
        charInput.setAlignment(Pos.CENTER);
		
        charInput.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    // Check if the new character is greater than LIMIT
                    if (charInput.getText().length() >= LIMIT) {
                        charInput.setText(charInput.getText().substring(0, LIMIT));
                    }
                }
                
                if (newValue.intValue() == 0) {
                	for (int i = 0; i < NUM_PINS; i++)
            			pins[i].setDisable(false);
                }
            }
        });
        
        charInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {             
                if (!charInput.getText().toLowerCase().matches("[a-z]"))
                	charInput.setText("");
                else {
        			try {
						bc.displayCharacter(charInput.getText().toLowerCase().charAt(0));
						updateGUI();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}               	
                }
                
            }
        });
        
		cbClear.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				if (cbClear.isSelected()) {
					clear();
					setDisable(true);
				}
				else {
					setDisable(false);			
				}
			}
		});
	
	}
	
	public TextField getCharInput() { 
		return charInput; 
	}
	
	public CheckBox getCbClear() {
		return cbClear;
	}
	
	public RadioButton getPin(int index) {
		return pins[index]; 
	}

	public RadioButton[] getCell() {
		return pins;
	}
	
	public void setPin(int index, RadioButton rb) {
		pins[index] = rb;
	}
	
	public String update() {
		String state = "";
		for (int i = 0; i < NUM_PINS; i++) {
			if (pins[i].isSelected())
				state += "1";
			else
				state += "0";
		}
		bc.setPins(state);
		return state;
	}
	
	public void updateGUI() {
		for (int i = 0; i < NUM_PINS; i++) {
			pins[i].setSelected(bc.getPinState(i));
			pins[i].setDisable(true);
		}
	}
	
	public void clear() {
		bc.clear();
		charInput.setText("");
		for (int i = 0; i < NUM_PINS; i++) {
			pins[i].setSelected(false);
			pins[i].setDisable(false);
		}
	}
	
	public void setDisable(boolean isDisable) {
		if (isDisable) {
			for (int i = 0; i < NUM_PINS; i++) {
				pins[i].setDisable(true);
			}			
			charInput.setDisable(true);			
		} else {
			for (int i = 0; i < NUM_PINS; i++) {
				pins[i].setDisable(false);
			}			
			charInput.setDisable(false);							
		}
	}
	
	public boolean isEmpty() {
		int count = 0;
		
		for (int i = 0; i < NUM_PINS; i++) {
			if (pins[i].isSelected())
				count++;
		}			
		
		return (count > 0) ? false : true;
	}

	public void write() {
		if (cbClear.isSelected()) {
			module.getItems().addAll("/~disp-cell-clear:" + cellIndex);
			return;
		}
		
		if (charInput.getText() != null && !charInput.getText().equals("")) {
			module.getItems().addAll("/~disp-cell-char:" + cellIndex + " " + charInput.getText());
			return;
		}
		
		if (!isEmpty()) {
			module.getItems().addAll("/~disp-cell-pins:" + cellIndex + " " + update());
		}
	}
	
	
}
