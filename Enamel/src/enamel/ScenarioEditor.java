package enamel;

import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;

public class ScenarioEditor {

	Stage window;
	BorderPane mainLayout;
	IntegerProperty CELL_COUNT;
	IntegerProperty BUTTON_COUNT;
	StringProperty FILENAME;
	StringProperty STATUS_MSG;
	ListView<String> storyList;
	
	protected void display() {
		//FILENAME.set("");
    	CELL_COUNT = new SimpleIntegerProperty(0);
    	BUTTON_COUNT = new SimpleIntegerProperty(0);
    	FILENAME = new SimpleStringProperty(null);
    	STATUS_MSG = new SimpleStringProperty(null);
    	
		window = new Stage();
		//window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Scenario Editor");
		window.setMinHeight(720);
		//window.setMaxHeight(720);
		window.setMinWidth(1360);
		//window.setMaxWidth(1360);
		//window.setResizable(false);

		mainLayout = new BorderPane();
		
		// Menu bar
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		MenuItem miNew = new MenuItem("New...");
		MenuItem miOpen = new MenuItem("Open...");
		MenuItem miSave = new MenuItem("Save");
		MenuItem miSaveAs = new MenuItem("Save as...");
		MenuItem miPlay = new MenuItem("Play scenario");
		MenuItem miClose = new MenuItem("Close file");
		MenuItem miExit = new MenuItem("Exit");
		menuFile.getItems().addAll(
			miNew, miOpen, new SeparatorMenuItem(), 
			miSave, miSaveAs, new SeparatorMenuItem(),
			miPlay, new SeparatorMenuItem(),
			miClose, new SeparatorMenuItem(),
			miExit
		);
		Menu menuAudio = new Menu("Audio");
		MenuItem miUpload = new MenuItem("Upload...");
		MenuItem miRecord = new MenuItem("Record...");
		menuAudio.getItems().addAll(miUpload, miRecord);
		Menu menuHelp = new Menu("Help");
		menuBar.getMenus().addAll(menuFile, menuAudio, menuHelp);
		mainLayout.setTop(menuBar);
		miOpen.setDisable(true);
		
		displayWelcomePage();

		// Status bar
		AnchorPane statusBar = new AnchorPane();
		HBox headerInfo = new HBox(16);
		Label fileName = new Label();
		Label bcellCount = new Label();
		Label buttonCount = new Label();
		Label statusMsg = new Label();
		
		statusBar.setPadding(new Insets(0, 10, 0, 5));
		headerInfo.setPadding(new Insets(5, 5, 5, 5));
		fileName.setText(FILENAME.get());
		statusMsg.setText(STATUS_MSG.get());

		AnchorPane.setLeftAnchor(headerInfo, 0.0);
		AnchorPane.setTopAnchor(headerInfo, 0.0);
		AnchorPane.setBottomAnchor(headerInfo, 0.0);
		AnchorPane.setRightAnchor(statusMsg, 0.0);
		AnchorPane.setTopAnchor(statusMsg, 0.0);
		AnchorPane.setBottomAnchor(statusMsg, 0.0);
		
		headerInfo.getChildren().addAll(
				new Label("Braille Cells:"), bcellCount,
				new Separator(Orientation.VERTICAL),
				new Label("Buttons:"), buttonCount,
				new Separator(Orientation.VERTICAL),
				fileName
			);
		
		statusBar.getChildren().addAll(
				headerInfo,
				statusMsg
			);
		
		mainLayout.setBottom(statusBar);
		
		bcellCount.textProperty().bind(CELL_COUNT.asString());
		buttonCount.textProperty().bind(BUTTON_COUNT.asString());
		fileName.textProperty().bind(FILENAME);
		statusMsg.textProperty().bind(STATUS_MSG);
		
		// Event Handlers
		miPlay.setOnAction(e -> playScenario());
		miNew.setOnAction(e -> newScenario());
		miOpen.setOnAction(e -> openScenario());
		miSave.setOnAction(e -> saveScenario());
		miSaveAs.setOnAction(e -> saveAsScenario());
		miClose.setOnAction(e -> displayWelcomePage());
		miExit.setOnAction(e -> window.close());
		miUpload.setOnAction(e -> uploadAudio());
		miRecord.setOnAction(e -> recordAudio());
		
		// Paint the whole window and show
		Scene editor = new Scene(mainLayout);
		window.setScene(editor);
		window.show();
	}
	
    private void displayWelcomePage() { 
    	CELL_COUNT.setValue(0);
    	BUTTON_COUNT.set(0);
    	FILENAME.setValue(null);
    	STATUS_MSG.set(null);
    	storyList = new ListView<String>();
    	
    	// Side list
		mainLayout.setLeft(storyList);
		
		// Main page
		Label filler = new Label("Welcome Screen");
		mainLayout.setCenter(filler);		
	}

	private void uploadAudio() {
		File src = ofc("Upload an audio file...", "Audio file", "*.wav");
		if (src != null) {
			File target = new File(System.getProperty("user.dir") + File.separator + 
									"FactoryScenarios" + File.separator + 
									"AudioFiles" + File.separator +
									src.getName());
			
			try {
				Files.copy(src.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				STATUS_MSG.set("Upload failed");
			}
			
			STATUS_MSG.set(target.getName() + " uploaded successfully");
		}
	}

	private void recordAudio() {
		RecordAudioDialog.display();
	}

	private void openScenario() {
		File chosenFile = ofc("Play a scenario file...", "Scenario file", "*.txt");
		System.out.println("FILE: " + chosenFile.toString());
	}

	private void playScenario() {
    	File chosenFile = ofc("Open a scenario file...", "Scenario file", "*.txt");
    	if (chosenFile != null) {
    		Thread playThread = new Thread(new Runnable() {
	            @Override
	            public void run() {
	    	    	ScenarioParser s = new ScenarioParser(true);
	    	    	s.setScenarioFile(chosenFile.getAbsolutePath());
	            }
	        });
	        
	        playThread.start();
    	}
    }
    
    private File ofc(String title, String type, String ext) {
    	FileChooser fc = new FileChooser();
    	File defaultDir = new File(System.getProperty("user.dir") + File.separator + "FactoryScenarios");
    	fc.setTitle(title);
    	fc.setInitialDirectory(defaultDir);
    	fc.getExtensionFilters().addAll(
    		new FileChooser.ExtensionFilter(type, ext)
    	);
    	return fc.showOpenDialog(window);   	
    }
    
    private void saveScenario() {
    	if ( CELL_COUNT.get() != 0 || BUTTON_COUNT.get() != 0 )
    		sfc(FILENAME.get());
    }
    
    private void saveAsScenario() {
    	if ( CELL_COUNT.get() != 0 || BUTTON_COUNT.get() != 0 )
    		sfc(null);
    }
    
    private void sfc(String filename) {
    	File chosenFile;    	
    	String ext = ".txt";
    	File defaultDir = new File(System.getProperty("user.dir") + File.separator + "FactoryScenarios");
    	
    	if (filename == null || filename.equals("")) {
	    	FileChooser fc = new FileChooser();
	
	    	fc.setTitle("Save scenario file as...");
	    	fc.setInitialDirectory(defaultDir);
	    	fc.getExtensionFilters().addAll(
	    		new FileChooser.ExtensionFilter("Scenario file", ext)
	    	);
	    	chosenFile = fc.showSaveDialog(window);
    	} else {
    		chosenFile = new File(defaultDir.getAbsolutePath() + File.separator + filename);
    	}

    	if (chosenFile == null)
    		return;
    	
		try {
			FileWriter fw = new FileWriter(chosenFile);
	        BufferedWriter bw = new BufferedWriter(fw);
	        
	        bw.write("Cell " + CELL_COUNT.get());
	        bw.newLine();
	        bw.write("Button " + BUTTON_COUNT.get());
	        bw.newLine();
	        
	    	for (Object line : storyList.getItems().toArray()) {
	    		bw.write((String) line);
	    		bw.newLine();
	    	}	 
	    	
	    	bw.close();
		} catch (IOException e) {
			STATUS_MSG.set("Save failed");
		}

		STATUS_MSG.set(chosenFile.getName() + " saved successfully");
    }    
    
    private void newScenario() {
    	String headers = NewScenarioDialog.display();
    	
    	if (headers == null)
    		return;
    	
    	String[] headerInfo = headers.split("/");
    	FILENAME.set(headerInfo[0]);
    	CELL_COUNT.set(Integer.parseInt(headerInfo[1]));
    	BUTTON_COUNT.set(Integer.parseInt(headerInfo[2]));
    	
    	displayStartPage();
    }
    
    private void displayStartPage() {
    	VBox startPage = new VBox(16);
    	startPage.setAlignment(Pos.CENTER);
    	Label startPrompt = new Label();
    	startPrompt.setText("What would you like to create?");
    	Button createStory = new Button("Story");
    	Button createQuiz = new Button("Quiz");
    	Button createBraille = new Button("Braille");
    	startPage.getChildren().addAll(startPrompt, createStory, createQuiz, createBraille);
    	
    	createStory.setOnAction(e -> createStory());
    	createQuiz.setOnAction(e -> createQuiz());
    	createBraille.setOnAction(e -> createBraille());
    	mainLayout.setCenter(startPage);   	
    }
    
    private void createBraille() {
    	
        String cssLayout = "-fx-border-color: black;\n" +
						   "-fx-border-width: 2;\n";

		GridPane braillePage = new GridPane();
		braillePage.setPadding(new Insets(10, 10, 10, 10));
        braillePage.setVgap(10);
        braillePage.setHgap(10);
        braillePage.setAlignment(Pos.CENTER);

        Button add = new Button("Add");
        Button reset = new Button("Reset");
        Button back = new Button("Back");
        
        BrailleCellGUI[] bcGUI = new BrailleCellGUI[CELL_COUNT.get()];
        GridPane[] bcLayout = new GridPane[CELL_COUNT.get()];
                
        int i;
        for (i = 0; i < CELL_COUNT.get(); i++) {
 
        	bcGUI[i] = new BrailleCellGUI(storyList, i);
        	bcLayout[i] = new GridPane();
            bcLayout[i].setAlignment(Pos.CENTER);
    		bcLayout[i].setPadding(new Insets(6, 0, 6, 6));
            bcLayout[i].setHgap(3);
            bcLayout[i].setVgap(6);
    		bcLayout[i].setStyle(cssLayout);        	
        	
            Label cellTag = new Label(Integer.toString(i + 1));
            Label fillerGap = new Label();
            Label filler1   = new Label();
            Label filler2   = new Label();
            cellTag.setAlignment(Pos.CENTER);
            fillerGap.setMinWidth(30);
            filler1.setMinWidth(50);
            filler2.setMinWidth(50);
            
            for (int j = 0; j < 8; j++) {
            	bcGUI[i].setPin(j, new RadioButton());
            	bcLayout[i].getChildren().addAll(bcGUI[i].getPin(j));
            }
            
            GridPane.setHalignment(cellTag, HPos.CENTER);
            GridPane.setHalignment(bcLayout[i], HPos.CENTER);
            GridPane.setHalignment(bcGUI[i].getCharInput(), HPos.CENTER);
            GridPane.setHalignment(bcGUI[i].getCbClear(), HPos.CENTER);
            
            GridPane.setConstraints(cellTag, (4 * i) + 1, 0);
            GridPane.setConstraints(bcLayout[i], (4 * i) + 1, 1);
            GridPane.setConstraints(bcGUI[i].getCharInput(), (4 * i), 2, 3, 1);
            GridPane.setConstraints(bcGUI[i].getCbClear(), (4 * i), 3, 3, 1);
            GridPane.setConstraints(filler1, (4 * i), 0);
            GridPane.setConstraints(filler2, (4 * i) + 2, 0);
            
            GridPane.setConstraints(bcGUI[i].getPin(0), 0, 0);
            GridPane.setConstraints(bcGUI[i].getPin(1), 0, 1);
            GridPane.setConstraints(bcGUI[i].getPin(2), 0, 2);
            GridPane.setConstraints(bcGUI[i].getPin(3), 1, 0);
            GridPane.setConstraints(bcGUI[i].getPin(4), 1, 1);
            GridPane.setConstraints(bcGUI[i].getPin(5), 1, 2);
            GridPane.setConstraints(bcGUI[i].getPin(6), 0, 3);
            GridPane.setConstraints(bcGUI[i].getPin(7), 1, 3);
            
            braillePage.getChildren().addAll(
            		cellTag, bcLayout[i], bcGUI[i].getCharInput(), 
            		bcGUI[i].getCbClear(), filler1, filler2
            	);
            
            if (i + 1 < CELL_COUNT.get()) {
                GridPane.setConstraints(fillerGap, (4 * i) + 3, 0);
                braillePage.getChildren().addAll(fillerGap);
            }
        }

/*        CheckBox cbClear = new CheckBox("Clear Braille cell");
        GridPane.setHalignment(cbClear, HPos.CENTER);
        GridPane.setConstraints(cbClear, 0, 3, (4 * i) - 1, 1);
     */   
        HBox buttonsLayout = new HBox(10);
        buttonsLayout.setPadding(new Insets(5, 5, 5, 5));
        buttonsLayout.setAlignment(Pos.CENTER);
        GridPane.setHalignment(buttonsLayout, HPos.CENTER);
        GridPane.setConstraints(buttonsLayout, 0, 4, (4 * i) - 1, 1);
        buttonsLayout.getChildren().addAll(add, reset, back);
        
        braillePage.getChildren().addAll(buttonsLayout);
       
		add.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				for (int i = 0; i < CELL_COUNT.get(); i++) {
					bcGUI[i].write();
				}
				reset.fire();
			}
		});
		
		reset.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				for (int i = 0; i < CELL_COUNT.get(); i++) {
					bcGUI[i].clear();
					bcGUI[i].getCbClear().setSelected(false);
					bcGUI[i].getCharInput().setDisable(false);
				}
			}
		});
		
        back.setOnAction(e -> displayStartPage());
           
        mainLayout.setCenter(braillePage);

	}

	private void createStory() {
    	GridPane storyPage = new GridPane();
		storyPage.setPadding(new Insets(5, 5, 5, 5));
        storyPage.setVgap(10);
        storyPage.setHgap(12);
        storyPage.setAlignment(Pos.CENTER);
        //storyPage.setGridLinesVisible(true);
        
        TextArea text = new TextArea(null);
        Label audioLabel = new Label("Audio: ");
        ComboBox audio = new ComboBox();
        ToggleGroup tg = new ToggleGroup();
        RadioButton rbUserinput = new RadioButton("Wait for User input");
        RadioButton rbRepeat = new RadioButton("Repeat input");
        Button add = new Button("Add");
        Button reset = new Button("Reset");
        Button back = new Button("Back");
        text.setPromptText("Type anything here");
        rbUserinput.setToggleGroup(tg);
        rbRepeat.setToggleGroup(tg);
        
        File audioFile = new File(System.getProperty("user.dir") + File.separator + 
        						  "FactoryScenarios" + File.separator + 
        						  "AudioFiles");
        
        if (audioFile.isDirectory()) {
        	for (String s : audioFile.list()) {
        		if (FilenameUtils.getExtension(s).toLowerCase().equals("wav"))
        			audio.getItems().addAll(s);
        	}
        }

        if (BUTTON_COUNT.get() < 2) 
        	rbRepeat.setDisable(true);
 
        GridPane.setConstraints(text, 0, 0, 6, 6);
        GridPane.setConstraints(audioLabel, 0, 6, 1, 2);
        GridPane.setConstraints(audio, 1, 6, 1, 2);
        GridPane.setConstraints(rbUserinput, 2, 6);
        GridPane.setConstraints(rbRepeat, 2, 7);
        GridPane.setConstraints(add, 3, 6, 1, 2);
        GridPane.setConstraints(reset, 4, 6, 1, 2);
        GridPane.setConstraints(back, 5, 6, 1, 2);
            
        add.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				ScenarioNode temp = new ScenarioNode(storyList, CELL_COUNT.get(), BUTTON_COUNT.get());
				temp.setStory(true);
				temp.setText(text.getText());
				temp.setAudio((audio.getValue() == null) ? null : audio.getValue().toString());
				temp.setRepeat(rbRepeat.isSelected());
				temp.setWait(rbUserinput.isSelected());
				temp.write();
				reset.fire();
			}
        });
        
		reset.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				text.setText(null);
				audio.setValue(null);
				rbUserinput.setSelected(false);
				rbRepeat.setSelected(false);
			}
		});
		
		back.setOnAction(e -> displayStartPage());
       
		storyPage.getChildren().addAll(
			text,
			audioLabel, audio,
			rbUserinput, rbRepeat,
			add, reset, back
		);
		
		add.setAccessibleText("Add current input to scenario file");
		
        mainLayout.setCenter(storyPage);
    }
    
    private void createQuiz() {
    	GridPane quizPage = new GridPane();
		quizPage.setPadding(new Insets(5, 5, 5, 5));
        quizPage.setVgap(10);
        quizPage.setHgap(12);
        quizPage.setAlignment(Pos.CENTER);
        //quizPage.setGridLinesVisible(true);
        
        Label questLabel = new Label("Question: ");
        TextField question = new TextField(null);
        ArrayList<ComboBox> audio = new ArrayList<ComboBox>();
        ArrayList<TextField> response = new ArrayList<TextField>();
        ArrayList<Label> responseLabel = new ArrayList<Label>();
        ArrayList<Label> numChoice = new ArrayList<Label>();
        Button add = new Button("Add");
        Button reset = new Button("Reset");
        Button back = new Button("Back");
        GridPane.setConstraints(questLabel, 0, 0, 1, 1);
        GridPane.setConstraints(question, 1, 0, 7, 1);
        GridPane.setConstraints(add, 4, 5, 1, 1);
        GridPane.setConstraints(reset, 5, 5, 1, 1);
        GridPane.setConstraints(back, 6, 5, 1, 1);
        question.setPromptText("Type question here");

        File audioFile = new File(System.getProperty("user.dir") + File.separator + 
				  "FactoryScenarios" + File.separator + 
				  "AudioFiles");
        ArrayList<String> audioList = new ArrayList<String>();
        if (audioFile.isDirectory()){
        	for (String s : audioFile.list()) {
        		if (FilenameUtils.getExtension(s).toLowerCase().equals("wav"))
        			audioList.add(s);
        	}
        }
        
        for (int i = 0; i < BUTTON_COUNT.get(); i++){

        	audio.add(new ComboBox());
        	response.add(new TextField());
        	//responseLabel.add(new Label("Response:"));
        	numChoice.add(new Label("Option " + (i + 1) + ":"));
        	response.get(i).setText(null);
        	response.get(i).setPromptText("Type response when chosen");

        	GridPane.setConstraints(numChoice.get(i), 0, i + 1, 1, 1);
        	GridPane.setConstraints(audio.get(i), 1, i + 1, 1, 1);
        	GridPane.setConstraints(response.get(i), 2, i + 1, 6, 1);
        	//GridPane.setConstraints(responseLabel.get(i), 2, i + 1, 1, 1);
        	
        	if (audioFile.isDirectory())
        		audio.get(i).getItems().addAll(audioList);
        	
        	quizPage.getChildren().addAll(
    			numChoice.get(i),
				audio.get(i), response.get(i)
				//responseLabel.get(i), 
    		);        	
        }
        
        add.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				ScenarioNode temp = new ScenarioNode(storyList, CELL_COUNT.get(), BUTTON_COUNT.get());
				ArrayList<String> reply = new ArrayList<String>();
				ArrayList<String> cues = new ArrayList<String>();
				temp.setQuiz(true);
				temp.setText(question.getText());
		        for (int i = 0; i < BUTTON_COUNT.get(); i++) {
		        	reply.add(response.get(i).getText());
		        	cues.add((audio.get(i).getValue() == null) ? null : audio.get(i).getValue().toString());
		        }
		        temp.setReply(reply);
		        temp.setCues(cues);
		        temp.write();
		        reset.fire();
			}
        });
        
		reset.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				question.setText(null);
		        for (int i = 0; i < BUTTON_COUNT.get(); i++){
		        	response.get(i).setText(null);
		        	audio.get(i).setValue(null);
		        }     								
			}
		});
		
		back.setOnAction(e -> displayStartPage());        
        
        quizPage.getChildren().addAll(
			questLabel, question,
			add, reset, back
		);
		
        mainLayout.setCenter(quizPage);
    }
    
}
