package enamel;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

public class RecordAudioPresenter {
	
	private SoundRecordingUtil audio;
	private final RecordAudioView view;
	private final ScenarioEditorPresenter parent;
	
	StringProperty msg;
	
	public RecordAudioPresenter(RecordAudioView view, ScenarioEditorPresenter parent) {
		this.audio = new SoundRecordingUtil();
		this.view = view;
		this.parent = parent;
		msg = new SimpleStringProperty("Press start to begin session");

		attachEvents();
	}

	private void attachEvents() {
		view.message.textProperty().bind(msg);
		
		view.saveBtn.disableProperty().bind(view.filename.textProperty().isEmpty());
			
		view.startBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				
		        Thread recordThread = new Thread(new Runnable() {
		            @Override
		            public void run() {
		                try {
							audio.start();
						} catch (LineUnavailableException e) {
							Platform.runLater(new Runnable() {
								@Override
								public void run() {								
									msg.set("Recording not supported");
								}							
							});
							view.stopBtn.setDisable(true);
							view.filename.setDisable(true);
							return;
						}
		            }
		        });
		        
		        view.startBtn.setDisable(true);
		        recordThread.start();
		        view.stopBtn.setDisable(false);
				msg.set("Recording in session");

			}
		});
		
		view.stopBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {				
				try {
					audio.stop();
				} catch (IOException e) {
					msg.set("Record got corrupted");
				}
				
				view.stopBtn.setDisable(true);
				view.startBtn.setDisable(false);
				msg.set("Recording is finished!");
			}
		});		
		
		view.saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				if (audio.isRunning)
					return;
				
		        File audioFile = new File(parent.audioDir + File.separator + 
		        		view.filename.getText() + ".wav");
		        
		        if (audio.isEmpty()) {
		        	msg.set("No recording found");
		        	return;
		        }
		        
				try {
					audio.save(audioFile);
				} catch (IOException e) {
					msg.set("Something went wrong");
				}
				
				msg.set(view.filename.getText() + ".wav is saved!");				
				view.filename.setText(null);
				
				parent.log.add(Actions.RECORD_AUDIO);
			}
		});
		
	}	
}
