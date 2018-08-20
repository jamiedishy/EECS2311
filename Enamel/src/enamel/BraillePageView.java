package enamel;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class BraillePageView extends GridPane {
	
	BrailleCellView[] bcells;
    Button addBtn, resetBtn, backBtn, updateBtn;
	
	int cellCount;
	boolean isEdit;
	
	public BraillePageView(int cc, boolean state) {
        addBtn = new Button("Add");
        resetBtn = new Button("Reset");
        backBtn = new Button("Back");
        updateBtn = new Button("Update");
		cellCount = cc;
		isEdit = state;
        
        bcells = new BrailleCellView[cellCount];
        for (int i = 0; i < cellCount; i++) {
        	bcells[i] = new BrailleCellView(i);
        }
		
		layoutPage();
		
	}

	private void layoutPage() {
		this.setPadding(new Insets(10, 10, 10, 10));
        this.setVgap(10);
        this.setHgap(10);
        this.setAlignment(Pos.CENTER);
	
        int i;
        for (i = 0; i < cellCount; i++) {
       	
            Label cellTag = new Label(Integer.toString(i + 1));
            Label fillerGap = new Label();
            Label filler1   = new Label();
            Label filler2   = new Label();
            
            cellTag.setAlignment(Pos.CENTER);
            fillerGap.setMinWidth(30);
            filler1.setMinWidth(50);
            filler2.setMinWidth(50);
                        
            GridPane.setHalignment(cellTag, HPos.CENTER);         
            GridPane.setConstraints(cellTag, (4 * i) + 1, 0);
            GridPane.setConstraints(filler1, (4 * i), 0);
            GridPane.setConstraints(filler2, (4 * i) + 2, 0);
            
            this.getChildren().addAll(
            		cellTag, bcells[i], bcells[i].charInput, 
            		bcells[i].cbClear, filler1, filler2
            	);
            
            if (i + 1 < cellCount) {
                GridPane.setConstraints(fillerGap, (4 * i) + 3, 0);
                this.getChildren().addAll(fillerGap);
            }
            
        }
        
        HBox buttonsLayout = new HBox(10);
        buttonsLayout.setPadding(new Insets(5, 5, 5, 5));
        buttonsLayout.setAlignment(Pos.CENTER);
        GridPane.setHalignment(buttonsLayout, HPos.CENTER);
        GridPane.setConstraints(buttonsLayout, 0, 4, (4 * i) - 1, 1);
        
		if (isEdit)
			buttonsLayout.getChildren().add(updateBtn);
		else
			buttonsLayout.getChildren().add(addBtn);
        
        buttonsLayout.getChildren().addAll(resetBtn, backBtn);
           
        this.getChildren().addAll(buttonsLayout);
	}

}
