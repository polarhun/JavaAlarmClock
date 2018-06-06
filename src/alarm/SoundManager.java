package alarm;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.*;

public class SoundManager {
	private static SoundManager instance;
	private HashMap<String, String> dictionary;
	private Clip audioClip;

	private SoundManager() {
		dictionary = new HashMap<>();
		dictionary.put("Beep", "src\\res\\beep.wav");
	}

	public static SoundManager getInstance() {
		if (instance == null) {
			instance = new SoundManager();
		}
		return instance;
	}

	public void playBeep(String clip) {
		String beepPath = dictionary.get(clip);
		File beepFile = new File(beepPath);
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(beepFile);
			audioClip = (Clip) AudioSystem.getClip();
			audioClip.open(audioStream);
			audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		}catch (Exception e) {
			e.printStackTrace();
		}{
			
		}
	}
	
	public void stopBeep() {
		if(audioClip != null) {
			audioClip.close();
		}
	}
}
