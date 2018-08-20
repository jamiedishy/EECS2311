package enamel;

import java.util.ArrayList;

public class StoryModule extends ScenarioModule {
	
	private String text;
	private String audio;
	private boolean isRepeat;
	private boolean isWait;

	public StoryModule(int cc, int bc) {
		super(cc, bc);		
	}

	public void setText(String text) { this.text = text; }
	public void setRepeat(boolean repeat) { this.isRepeat = repeat; }
	public void setWait(boolean wait) { this.isWait = wait; }
	public void setAudio(String audio) { this.audio = audio; }
	
	public String getText() { return this.text; }
	public boolean getRepeat() { return this.isRepeat; }
	public boolean getWait() { return this.isWait; }
	public String getAudio() { return this.audio; }

	@Override
	public String header() {
		String result = "";

		if (audio != null) {
			result += "audio";
		}
		
		if (text != null) {
			if (!result.isEmpty())
				result += " + ";	
			
			if (isRepeat)
				result += "repeat text";
			else
				result += "text";			
		}
		else {
			if (!result.isEmpty() && isRepeat)
				result += " + ";
			
			if (isRepeat)
				result += "repeat prompt";				
		}
		
		if (isWait) {
			if (!result.isEmpty())
				result += " ";
			
			result += "prompt";
		}
				
		return result;
	}

	@Override
	public ArrayList<String> body() {
		ArrayList<String> result = new ArrayList<String>();
		
		if (audio != null)
			result.add("/~sound:" + audio);
		
		if (isRepeat)
			result.add("/~repeat");
	
		if (text != null)
			result.add(text);
				
		if (isWait || isRepeat) {		
			result.add("Press 1 to continue.");
			if (isRepeat) {
				result.add("Press 2 to repeat.");				
				result.add("/~endrepeat");
				result.add("/~repeat-button:1");
			}						
			result.add("/~skip-button:0 " + JUMP_TAG);
			result.add("/~user-input");
			result.add("/~" + JUMP_TAG);
			result.add("/~pause:" + PAUSE);
			result.add("/~reset-buttons");
		}		
		
		return result;
	}

}
