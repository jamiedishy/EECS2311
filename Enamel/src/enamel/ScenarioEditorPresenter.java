package enamel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;

/**
* This class provides the back-end logic and event handling for components in the
* <code>ScenarioEditorView</code> class. 
* 
* @author Team 14: Jamie Dishy, Jonas Laya, Samuel On, Paul Sison
* 
*/

public class ScenarioEditorPresenter {

	ScenarioFile model;
	ScenarioEditorView view;
	IntegerProperty CELL_COUNT;
	IntegerProperty BUTTON_COUNT;
	StringProperty FILENAME;
	StringProperty STATUS_MSG;

	ScenePanelView list;
	ScenePanelPresenter listPresenter;
	
	ActionLogger log;
	
	File defaultDir, binDir, dataDir, audioDir;
	StartPageView startPage;
	
	public ScenarioEditorPresenter(ScenarioEditorView view) {
		this.view = view;
		displayPage(Page.WELCOME);
		initData();
		bindData();
		attachEvents();
	}

	private void bindData() {
		view.cellLbl.textProperty().bind(CELL_COUNT.asString());
		view.buttonLbl.textProperty().bind(BUTTON_COUNT.asString());
		view.filenameLbl.textProperty().bind(FILENAME);
		view.statusLbl.textProperty().bind(STATUS_MSG);
	}
	
	private void attachEvents() {
		final KeyCombination newComb = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
		final KeyCombination openComb = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
		final KeyCombination saveComb = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
		final KeyCombination saveAsComb = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);
		final KeyCombination playComb = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
		final KeyCombination closeComb = new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN);
		final KeyCombination exitComb = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
		final KeyCombination uploadComb = new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN);
		final KeyCombination recordComb = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
		final KeyCombination statsComb = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);
		
		view.miNew.setAccelerator(newComb);
		view.miOpen.setAccelerator(openComb);
		view.miSave.setAccelerator(saveComb);
		view.miSaveAs.setAccelerator(saveAsComb);
		view.miPlay.setAccelerator(playComb);
		view.miClose.setAccelerator(closeComb);
		view.miExit.setAccelerator(exitComb);
		view.miUpload.setAccelerator(uploadComb);
		view.miRecord.setAccelerator(recordComb);
		view.miStats.setAccelerator(statsComb);
			
		view.window.setOnCloseRequest(e -> log.serialize());		
		view.miNew.setOnAction(e -> newScenario());
		view.miOpen.setOnAction(e -> openScenario());
		view.miSave.setOnAction(e -> saveScenario());
		view.miSaveAs.setOnAction(e -> saveAsScenario());
		view.miPlay.setOnAction(e -> playScenario());
		view.miClose.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				resetData();
				displayPage(Page.WELCOME);
			}
		});
		view.miExit.setOnAction(e -> {
			log.serialize();
			view.window.close();
		});
		view.miUpload.setOnAction(e -> uploadAudio());
		view.miRecord.setOnAction(e -> recordAudio());
		view.miStats.setOnAction(e -> viewStats());
			
		view.main.getAccelerators().put(
			new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN),
            new Runnable() {
				@Override
	            public void run() {
	            	list.editBtn.fire();
	            }
			});
		
		view.main.getAccelerators().put(
			new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN),
		    new Runnable() {
				@Override
				public void run() {
					list.delBtn.fire();
				}
			});
		
		view.main.getAccelerators().put(
			new KeyCodeCombination(KeyCode.P, KeyCombination.SHIFT_DOWN),
            new Runnable() {
				@Override
	            public void run() {
	            	list.upBtn.fire();
	            }
			});
		
		view.main.getAccelerators().put(
			new KeyCodeCombination(KeyCode.O, KeyCombination.SHIFT_DOWN),
            new Runnable() {
				@Override
	            public void run() {
	            	list.downBtn.fire();
	            }
			});
		
		view.main.getAccelerators().put(
				new KeyCodeCombination(KeyCode.DIGIT1, KeyCombination.CONTROL_DOWN),
	            new Runnable() {
					@Override
		            public void run() {
						if (startPage != null)
							startPage.createStoryBtn.fire();
		            }
				});
		
		view.main.getAccelerators().put(
				new KeyCodeCombination(KeyCode.DIGIT2, KeyCombination.CONTROL_DOWN),
	            new Runnable() {
					@Override
		            public void run() {
						if (startPage != null)
							startPage.createQuizBtn.fire();
		            }
				});
		
		view.main.getAccelerators().put(
				new KeyCodeCombination(KeyCode.DIGIT3, KeyCombination.CONTROL_DOWN),
	            new Runnable() {
					@Override
		            public void run() {
						if (startPage != null)
							startPage.createBrailleBtn.fire();
		            }
				});
	}

	public void initData() {
		CELL_COUNT = new SimpleIntegerProperty(0);
		BUTTON_COUNT = new SimpleIntegerProperty(0);
		FILENAME = new SimpleStringProperty(null);
		STATUS_MSG = new SimpleStringProperty(null);
		
		list = new ScenePanelView();
		listPresenter = new ScenePanelPresenter(list, this);
		view.mainLayout.setLeft(list);
		
		defaultDir = new File(System.getProperty("user.dir") + File.separator + "FactoryScenarios");
		binDir = new File(defaultDir.getAbsolutePath() + File.separator + "bin");
		dataDir = new File(defaultDir.getAbsolutePath() + File.separator + "data");
		audioDir = new File(defaultDir.getAbsolutePath() + File.separator + "AudioFiles");
		
		if (!defaultDir.exists())
			defaultDir.mkdir();
		
		if (!binDir.exists())
			binDir.mkdir();
		
		if (!dataDir.exists())
			dataDir.mkdir();
		
		if (!audioDir.exists())
			audioDir.mkdir();
		
		log = new ActionLogger(dataDir);
	}
	
	private void resetData() {
    	CELL_COUNT.setValue(0);
    	BUTTON_COUNT.set(0);
    	FILENAME.setValue(null);
    	STATUS_MSG.set(null);
    	listPresenter.reset();
    	startPage = null;
	}
	
	public void updateView() {
    	FILENAME.set(model.getFilename());
    	CELL_COUNT.set(model.getCellCount());
    	BUTTON_COUNT.set(model.getButtonCount());  	    	
    	listPresenter.update(model.getContents());
    	view.mainLayout.setLeft(list);   	
	}
	
    public void displayPage(Page page) {
    	switch (page) {
    		case WELCOME:
    			view.mainLayout.setCenter(new WelcomePageView());
    			break;
    		case START:
    			startPage = new StartPageView();
    			StartPagePresenter startPresenter = new StartPagePresenter(startPage, this);
    			view.mainLayout.setCenter(startPage);
    			break;
    		case STORY:
    			StoryPageView storyPage = new StoryPageView(BUTTON_COUNT.get(), false);
    			StoryPagePresenter storyPresenter = new StoryPagePresenter(storyPage, this);
    			view.mainLayout.setCenter(storyPage);
    			break;
    		case QUIZ:
    			QuizPageView quizPage = new QuizPageView(BUTTON_COUNT.get(), false);
    			QuizPagePresenter quizPresenter = new QuizPagePresenter(quizPage, this);
    			view.mainLayout.setCenter(quizPage);
    			break;
    		case BRAILLE:
    			BraillePageView braillePage = new BraillePageView(CELL_COUNT.get(), false);
    			BraillePagePresenter braillePresenter = new BraillePagePresenter(braillePage, this);
    			view.mainLayout.setCenter(braillePage);
    			break;
    	} 	
    }
    
    public void displayPage(Page page, ScenarioModule scene, TreeItem<String> branch) {
    	switch (page) {
			case STORY:
				StoryPageView storyPage = new StoryPageView(BUTTON_COUNT.get(), true);
				StoryPagePresenter storyPresenter = new StoryPagePresenter(storyPage, this, scene, branch);
				view.mainLayout.setCenter(storyPage);
				break;
			case QUIZ:
				QuizPageView quizPage = new QuizPageView(BUTTON_COUNT.get(), true);
				QuizPagePresenter quizPresenter = new QuizPagePresenter(quizPage, this, scene, branch);
				view.mainLayout.setCenter(quizPage);				
				break;
			case BRAILLE:
    			BraillePageView braillePage = new BraillePageView(CELL_COUNT.get(), true);
    			BraillePagePresenter braillePresenter = new BraillePagePresenter(braillePage, this, scene, branch);
    			view.mainLayout.setCenter(braillePage);
				break;
		}    	
    }
    
    private void newScenario() {
    	NewScenarioView newView = new NewScenarioView();
    	NewScenarioPresenter newPresenter = new NewScenarioPresenter(newView);
    	newView.display();

    	ScenarioFile temp = newPresenter.getModel();
    	
    	if (temp == null)
    		return;
    	
    	resetData();

    	model = temp;
    	updateView();
    	
    	displayPage(Page.START);  	
    	log.add(Actions.NEW_SCENARIO);
    }
    
	private void openScenario() {
		File chosenFile = ofc("Open a scenario file...", "Scenario file", "*.txt");
		
		if(chosenFile == null)
			return;
		
		try {
			
			ScenarioFile temp = deserializeScenario(chosenFile.getName());
			
			if (temp != null) {
				
				// avoids null error when user directly opens file
				if (model == null)
					model = new ScenarioFile();
				
				model.copy(temp);
			}

		} catch (Exception e) {
			
			if (e instanceof FileNotFoundException) {
				new AlertDialog("Opening failed", chosenFile.getName() + " is not supported.");
			}
			else {
				new AlertDialog("Opening failed", chosenFile.getName() + " is corrupted.");				
			}
		
			return;
			
		}
 	
		resetData();
		listPresenter.reset();
		updateView();
		STATUS_MSG.set(FILENAME.get() + " opened successfully");
		
		displayPage(Page.START);		
		log.add(Actions.OPEN_SCENARIO);
	}
    
	private void playScenario() {
    	File chosenFile = ofc("Play a scenario file...", "Scenario file", "*.txt");
    	if (chosenFile != null) {
    		Thread playThread = new Thread(new Runnable() {
	            @Override
	            public void run() {
	    	    	ScenarioParser s = new ScenarioParser(true);
	    	    	s.setScenarioFile(chosenFile.getAbsolutePath());
	            }
	        });
	        
	        playThread.start();	        
	        log.add(Actions.PLAY_SCENARIO);
    	}
    }
	
	/*
	 * Utility method. Open file chooser.
	 * Used by openScenario and playScenario.
	 */
    private File ofc(String title, String type, String ext) {
    	FileChooser fc = new FileChooser();
    	
    	fc.setTitle(title);
    	fc.setInitialDirectory(defaultDir);
    	fc.getExtensionFilters().addAll(
    		new FileChooser.ExtensionFilter(type, ext)
    	);
    	return fc.showOpenDialog(view.window);   	
    }
    
    /*
     * Considering turning into a static method in ScenarioFile
     */
    private void serializeScenario(ScenarioFile sf) throws IOException {
    	String ext = ".bin";
    	
    	File binFile = new File(binDir.getAbsolutePath() + File.separator + 
    					FilenameUtils.removeExtension(sf.getFilename()) + ext);
    	FileOutputStream fs = new FileOutputStream(binFile);
        ObjectOutputStream os = new ObjectOutputStream(fs);
        os.writeObject(sf);
        os.close();
        fs.close();
    }

    /*
     * Considering turning into a static method in ScenarioFile
     */
    private ScenarioFile deserializeScenario(String filename) throws Exception {
    	String ext = ".bin";
    	
    	File binFile = new File(binDir.getAbsolutePath() + File.separator + 
    					FilenameUtils.removeExtension(filename) + ext);
    	FileInputStream fs = new FileInputStream(binFile);
        ObjectInputStream is = new ObjectInputStream(fs);
        ScenarioFile temp = (ScenarioFile) is.readObject();
        is.close();
        fs.close();

        return temp;
    }    
    
    private void saveScenario() {
    	if (CELL_COUNT.get() != 0 && BUTTON_COUNT.get() != 0)
    		save(FILENAME.get());
    }
    
    private void saveAsScenario() {
    	if (CELL_COUNT.get() != 0 && BUTTON_COUNT.get() != 0 )
    		save(null);
    }
    
    /*
     * Utility method for saving.
     */
    private void save(String filename) {
    	File chosenFile;    	
    	String ext = ".txt";
    	
    	if (filename == null || filename.equals("")) {
	    	FileChooser fc = new FileChooser();
	
	    	fc.setTitle("Save scenario file as...");
	    	fc.setInitialDirectory(defaultDir);
	    	fc.getExtensionFilters().addAll(
	    		new FileChooser.ExtensionFilter("Scenario file", ext)
	    	);
	    	chosenFile = fc.showSaveDialog(view.window);
    	} else {
    		chosenFile = new File(defaultDir.getAbsolutePath() + File.separator + filename);
    	}

    	if (chosenFile == null)
    		return;
    	
		try {
			
			FileWriter fw = new FileWriter(chosenFile);
	        BufferedWriter bw = new BufferedWriter(fw);
	        ScenarioFile temp = new ScenarioFile();
	        temp.copy(model);
	        temp.setFilename(chosenFile.getName());
	        temp.setContents(listPresenter.scenes);

	        	        
	        bw.write("Cell " + CELL_COUNT.get());
	        bw.newLine();
	        bw.write("Button " + BUTTON_COUNT.get());
	        bw.newLine();
	            
	        for (ScenarioModule content : temp.getContents()) {
	        	for (String line : content.body()) {
	        		bw.write(line);
	        		bw.newLine();
	        	}
	        } 	
	    	
	    	bw.close();
	    	
	    	serializeScenario(temp);
	    	
	    	FILENAME.set(chosenFile.getName());
	    	
		} catch (IOException e) {
			new AlertDialog("Save failed", chosenFile.getName() + " was not saved. Try again!");
		}

		STATUS_MSG.set(chosenFile.getName() + " saved successfully");	
		log.add(Actions.SAVE_SCENARIO);
    }        

	private void uploadAudio() {
		File src = ofc("Upload an audio file...", "Audio file", "*.wav");
		if (src != null) {
			File target = new File(audioDir.getAbsolutePath() + File.separator + src.getName());
			
			try {
				Files.copy(src.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				new AlertDialog("Upload failed", target.getName() + " was not uploaded. Try again!");
			}
			
			STATUS_MSG.set(target.getName() + " uploaded successfully");
			
			log.add(Actions.UPLOAD_AUDIO);
		}
	}

	private void recordAudio() {
		RecordAudioView recView = new RecordAudioView();
		RecordAudioPresenter recPresenter = new RecordAudioPresenter(recView, this);
		recView.display();
	}
    
	private void viewStats() {
		LogStatsView logView = new LogStatsView(log);
		logView.display();
	}
	
}