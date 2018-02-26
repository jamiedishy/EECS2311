package enamel;

import java.util.ArrayList;

import javafx.scene.control.ListView;

public class ScenarioNode {

	int CELL_COUNT;
	int BUTTON_COUNT;
	String text;
	boolean isStory;
	boolean isQuiz;
	boolean isRepeat;
	boolean isWait;
	String audio;
	ListView<String> module;
	final String JUMP_TAG = "JUMP";
	final int PAUSE = 1;
	ArrayList<String> reply = new ArrayList<String>();
	ArrayList<String> cues = new ArrayList<String>();
	ArrayList<String> SKIP_TAG = new ArrayList<String>();
	
	ScenarioNode(ListView<String> list, int cell, int button) {
		this.module = list;
		this.CELL_COUNT = cell;
		this.BUTTON_COUNT = button;
		
		for (int i = 0; i < BUTTON_COUNT; i++)
			SKIP_TAG.add("SKIP" + (i + 1));
	}
	
	public void setStory(boolean story) { this.isStory = story; }
	public void setQuiz(boolean quiz) { this.isQuiz = quiz; }
	public void setText(String text) { this.text = text; }
	public void setReply(ArrayList<String> reply) { this.reply = reply; }
	public void setRepeat(boolean repeat) { this.isRepeat = repeat; }
	public void setWait(boolean wait) { this.isWait = wait; }
	public void setAudio(String audio) { this.audio = audio; }
	public void setCues(ArrayList<String> cues) { this.cues = cues; }
	
	public void write() {	
		
		if (isStory) {
			if (audio != null)
				module.getItems().addAll("/~sound:" + audio);
			
			if (isRepeat)
				module.getItems().addAll("/~repeat");
		
			if (text != null)
				module.getItems().addAll(text);
					
			if (isWait || isRepeat) {		
				module.getItems().addAll("Press 1 to continue.");
				if (isRepeat) {
					module.getItems().addAll("Press 2 to repeat.");				
					module.getItems().addAll("/~endrepeat");
					module.getItems().addAll("/~repeat-button:1");
				}						
				module.getItems().addAll("/~skip-button:0 " + JUMP_TAG);
				module.getItems().addAll("/~user-input");
				module.getItems().addAll("/~" + JUMP_TAG);
				module.getItems().addAll("/~pause:" + PAUSE);
				module.getItems().addAll("/~reset-buttons");
			}
		}
		
		if (isQuiz) {
			boolean isCueON = false;
			boolean isReplyON = false;
			
			if (text != null)
				module.getItems().addAll(text);			
				
			for (int i = 0; i < BUTTON_COUNT; i++) {
				if (!isCueON && cues.get(i) != null)
					isCueON = true;
				
				if (!isReplyON && reply.get(i) != null)
					isReplyON = true;
				
				if (cues.get(i) != null || reply.get(i) != null)
					module.getItems().addAll("/~skip-button:" + i + " " + SKIP_TAG.get(i));
			}
			
			if (isCueON || isReplyON)
				module.getItems().addAll("/~user-input");
			
			for (int i = 0; i < BUTTON_COUNT; i++) {
				
				if (cues.get(i) != null || reply.get(i) != null)
					module.getItems().addAll("/~" + SKIP_TAG.get(i));
					
				if (cues.get(i) != null)
					module.getItems().addAll("/~sound:" + cues.get(i).toString());
				
				if (reply.get(i) != null)
					module.getItems().addAll(reply.get(i));
				
				if (cues.get(i) != null || reply.get(i) != null)
					module.getItems().addAll("/~skip:" + JUMP_TAG);
				
			}			
			
			if (isCueON || isReplyON) {
				module.getItems().addAll("/~" + JUMP_TAG);
				module.getItems().addAll("/~pause:" + PAUSE);
				module.getItems().addAll("/~reset-buttons");
			}
		}
	}
}
