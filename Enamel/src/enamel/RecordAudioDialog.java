package enamel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
 
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class RecordAudioDialog{
	
    private static final int BUFFER_SIZE = 4096;
    private static ByteArrayOutputStream recordBytes;
    private static TargetDataLine audioLine;
    private static AudioFormat format; 
    private static boolean isRunning;
    
	protected static void display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Record Audio");
		window.setResizable(false);

		GridPane layout = new GridPane();
		layout.setPadding(new Insets(5, 5, 5, 5));
        layout.setVgap(10);
        layout.setHgap(12);
        layout.setAlignment(Pos.CENTER);
        //layout.setGridLinesVisible(true);
		
		TextField filename = new TextField();
		Button start = new Button("Start");
		Button stop = new Button("Stop");
		Button save = new Button("Save");
		Label msg = new Label("Start Recording!");
		filename.setPromptText("Sample.wav");
		filename.setAlignment(Pos.CENTER);
		GridPane.setConstraints(start, 0, 0, 3, 1);
		GridPane.setHalignment(start, HPos.CENTER);
		GridPane.setConstraints(stop, 0, 1, 3, 1);
		GridPane.setHalignment(stop, HPos.CENTER);
		GridPane.setConstraints(filename, 0, 2, 3, 1);
		GridPane.setConstraints(save, 0, 3, 3, 1);
		GridPane.setHalignment(save, HPos.CENTER);
		GridPane.setConstraints(msg, 0, 4, 3, 1);
		GridPane.setHalignment(msg, HPos.CENTER);
		
		layout.getChildren().addAll(
				start,
				stop,
				filename,
				save,
				msg
			);

		start.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
		        
		        Thread recordThread = new Thread(new Runnable() {
		            @Override
		            public void run() {
		                try {
							start() ;
						} catch (LineUnavailableException e) {
							msg.setText("WAV recording not supported");
						}          
		            }
		        });
		        
		        recordThread.start();
				msg.setText("Recording has started!");
			}
		});
		
		stop.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				try {
					stop();
				} catch (IOException e) {
					msg.setText("Record got corrupted");
				}
				msg.setText("Recording is finished!");
			}
		});		
		
		save.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
		        File audioFile = new File(System.getProperty("user.dir") + File.separator + 
						  "FactoryScenarios" + File.separator + 
						  "AudioFiles" + File.separator +
						  filename.getText() + ".wav");
		        
				try {
					save(audioFile);
				} catch (IOException e) {
					msg.setText("Something went wrong");
				}
				msg.setText(filename.getText() + ".wav is saved!");
			}
		});
		
		Scene recordDialog = new Scene(layout);
		window.setScene(recordDialog);
		window.showAndWait();
	}

//////// http://www.codejava.net/coding/a-utility-for-recording-sound-in-java //////////////	
	
    /**
     * Defines a default audio format used to record
     */
    private static AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
                bigEndian);
    }
 
    /**
     * Start recording sound.
     * @throws LineUnavailableException if the system does not support the specified
     * audio format nor open the audio data line.
     */
    private static void start() throws LineUnavailableException {
        format = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
        // checks if system supports the data line
        if (!AudioSystem.isLineSupported(info)) {
            throw new LineUnavailableException(
                    "The system does not support the specified format.");
        }
 
        audioLine = AudioSystem.getTargetDataLine(format);
        //audioLine = (TargetDataLine) AudioSystem.getLine(info);
 
        audioLine.open(format);
        audioLine.start();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = 0;
 
        recordBytes = new ByteArrayOutputStream();
        isRunning = true;

        while (isRunning) {
            bytesRead = audioLine.read(buffer, 0, buffer.length);
            recordBytes.write(buffer, 0, bytesRead);
        }
    }
 
    /**
     * Stop recording sound.
     * @throws IOException if any I/O error occurs.
     */
    private static void stop() throws IOException {
        isRunning = false;
         
        if (audioLine != null) {
            audioLine.stop();
            audioLine.close();
        }
    }
 
    /**
     * Save recorded sound data into a .wav file format.
     * @param wavFile The file to be saved.
     * @throws IOException if any I/O error occurs.
     */
    private static void save(File wavFile) throws IOException {
        byte[] audioData = recordBytes.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format,
                audioData.length / format.getFrameSize());
 
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, wavFile);
 
        audioInputStream.close();
        recordBytes.close();
    }
}