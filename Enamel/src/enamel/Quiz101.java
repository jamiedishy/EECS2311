package enamel;
import javafx.application.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.*;


public class Quiz101 extends Application{
	
	Stage quiz;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		quiz = primaryStage;
		quiz.setTitle("This is the title");
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		
		//TextField
		Text questionprompt = new Text("Please type question here");
		//setfont
		grid.add(questionprompt, 1 , 1 );
		TextField inputQuestion = new TextField();
		grid.add(inputQuestion, 1, 2);
		
		Text questionMark = new Text("?");
		grid.add(questionMark, 2, 1);
		
		
		//Multiple Choice Slots Lettering
		//A
		Text letterA = new Text ("A");
		grid.add(letterA, 0, 4);
		
		//B
		Text letterB = new Text ("B");
		grid.add(letterB, 0, 5);
		
		//C
		Text letterC = new Text ("C");
		grid.add(letterC, 0, 6);
		
		//D
		Text letterD = new Text ("D");
		grid.add(letterD, 0, 7);
		
		//Input Fields and Buttons
		
		//A
		TextField inputA = new TextField();
		grid.add(inputA, 1 , 4);
		
		Button buttonA = new Button("Enter");
		grid.add(buttonA, 2, 4);

		//B
		TextField inputB = new TextField();
		grid.add(inputB,1 , 5);
		
		Button buttonB = new Button("Enter");
		grid.add(buttonB, 2, 5);

		
		//C
		TextField inputC = new TextField();
		grid.add(inputC ,1 , 6);
		
		Button buttonC = new Button("Enter");
		grid.add(buttonC, 2, 6);

		//D
		
		TextField inputD = new TextField();
		grid.add(inputD, 1 , 7);
		
		Button buttonD = new Button("Enter");
		grid.add(buttonD, 2, 7);

		
		
		
		
		
		
		/*//name Label
		Label nameLabel = new Label("Username");
		GridPane.setConstraints(nameLabel,0, 0);
		
		// name input 
		TextField nameInput = new TextField ("bucky");
		GridPane.setConstraints(nameInput, 1 ,0 );
		
		//pass label
		Label passLabel = new Label("Password");
		GridPane.setConstraints(passLabel, 0, 1);
		
		// Password input
		TextField passInput = new TextField ();
		passInput.setPromptText("password");
		GridPane.setConstraints(passInput, 1, 1);
		
		Button loginButton = new Button("Log In");
		GridPane.setConstraints(loginButton,1,2);
		*/
		//grid.getChildren().addAll(nameLabel,nameInput,passLabel,passInput,loginButton);
		
		Scene scene = new Scene(grid, 300, 200);
		quiz.setScene(scene);
		
		quiz.show();
		
	}

}
