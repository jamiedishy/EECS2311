package enamel;

import java.util.ArrayList;

public class QuizModule extends ScenarioModule {
	
	private String question;
	private ArrayList<String> audioCues;
	private ArrayList<String> responses;

	public QuizModule(int cc, int bc) {
		super(cc, bc);
		
		SKIP_TAG = new ArrayList<String>();
		for (int i = 0; i < BUTTON_COUNT; i++)
			SKIP_TAG.add("SKIP" + (i + 1));
	}

	public void setQuestion(String q) { this.question = q; }
	public void setAudioCues(ArrayList<String> ac) { this.audioCues = ac; }
	public void setResponses(ArrayList<String> resp) { this.responses = resp; }
	
	public String getQuestion() { return question; }
	public ArrayList<String> getAudioCues() { return audioCues; }
	public ArrayList<String> getResponses() { return responses; }
	
	@Override
	public String header() {
		String result = "";
		int choiceCount = 0;		
			
		for (int i = 0; i < BUTTON_COUNT; i++) {		
			if (audioCues.get(i) != null || responses.get(i) != null)
				choiceCount++;
		}
		
		if (choiceCount > 0)
			result = "Quiz with " + choiceCount + " choice(s)";
		else
			result = "Question";
		
		return result;
	}

	@Override
	public ArrayList<String> body() {
		ArrayList<String> result = new ArrayList<String>();
		boolean isCueON = false;
		boolean isReplyON = false;
		
		if (question != null)
			result.add(question);			
			
		for (int i = 0; i < BUTTON_COUNT; i++) {
			if (!isCueON && audioCues.get(i) != null)
				isCueON = true;
			
			if (!isReplyON && responses.get(i) != null)
				isReplyON = true;
			
			if (audioCues.get(i) != null || responses.get(i) != null)
				result.add("/~skip-button:" + i + " " + SKIP_TAG.get(i));
		}
		
		if (isCueON || isReplyON)
			result.add("/~user-input");
		
		for (int i = 0; i < BUTTON_COUNT; i++) {
			
			if (audioCues.get(i) != null || responses.get(i) != null)
				result.add("/~" + SKIP_TAG.get(i));
				
			if (audioCues.get(i) != null)
				result.add("/~sound:" + audioCues.get(i).toString());
			
			if (responses.get(i) != null)
				result.add(responses.get(i));
			
			if (audioCues.get(i) != null || responses.get(i) != null)
				result.add("/~skip:" + JUMP_TAG);
			
		}			
		
		if (isCueON || isReplyON) {
			result.add("/~" + JUMP_TAG);
			result.add("/~pause:" + PAUSE);
			result.add("/~reset-buttons");
		}
		
		return result;
	}
	
}
