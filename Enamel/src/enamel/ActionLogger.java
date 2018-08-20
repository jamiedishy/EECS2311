package enamel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;

public class ActionLogger implements Serializable {

	private static final long serialVersionUID = 1L;
	final String defaultName;
	final File defaultFile;
	HashMap<Actions, Integer> data;
		
	public ActionLogger(File dataDir) {	
		defaultName = "log.dat";
/*		defaultDir = new File(System.getProperty("user.dir") + File.separator + "data");*/
		defaultFile = new File(dataDir.getAbsolutePath() + File.separator + defaultName);
		
/*		if (!defaultDir.exists())
			defaultDir.mkdir();*/
		
		if (defaultFile.exists()) {
			try {
				deserialize();
				return;
			} catch (Exception e) {
				// empty
			}
		}
		
		data = new HashMap<Actions, Integer>();		
		for (Actions a : Actions.values())
			data.put(a, 0);
	}
	
	public void add(Actions key) {		
		data.put(key, data.get(key) + 1);
	}
	
    public void serialize() {
    	try {
        	FileOutputStream fs = new FileOutputStream(defaultFile);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(data);
            os.close();
            fs.close();
    	}
    	catch (IOException e) {
    		// 
    	}
    }

    public void deserialize() throws Exception {
    	FileInputStream fs = new FileInputStream(defaultFile);
        ObjectInputStream is = new ObjectInputStream(fs);
        data = (HashMap<Actions, Integer>) is.readObject();
        is.close();
        fs.close();
    }
    
    public String properName(Actions a) {
    	String name = "";
    	
    	switch(a) {
	    	case NEW_SCENARIO:
	    		name = "Creating new scenario";
	    		break;
	    	case OPEN_SCENARIO:
	    		name = "Opening an existing scenario";
	    		break;
	    	case SAVE_SCENARIO:
	    		name = "Saving current scenario";
	    		break;
	    	case PLAY_SCENARIO:
	    		name = "Playing an existing scenario";
	    		break;
	    	case UPLOAD_AUDIO:
	    		name = "Uploading audio to repository";
	    		break;
	    	case RECORD_AUDIO:
	    		name = "Recording new audio for repository";
	    		break;
	    	case EDIT_SCENE:
	    		name = "Editing a scene from list";
	    		break;
	    	case DELETE_SCENE:
	    		name = "Deleting a scene from list";
	    		break;
	    	case UP_SCENE:
	    		name = "Moving a scene higher on the list";
	    		break;
	    	case DOWN_SCENE:
	    		name = "Moving a scene lower on the list";
	    		break;
	    	case NEW_STORY:
	    		name = "Adding a story module to the list";
	    		break;
	    	case NEW_QUIZ:
	    		name = "Adding a quiz module to the list";
	    		break;
	    	case NEW_BRAILLE:
	    		name = "Adding braille module to the list";
	    		break;
    	}

    	return name;
    }
}
