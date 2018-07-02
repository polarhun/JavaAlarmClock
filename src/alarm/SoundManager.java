package alarm;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.*;

public class SoundManager {
	private static SoundManager instance;
	private Clip audioClip;
	private String defaultPath;

	private SoundManager() {
		this.defaultPath = "src\\res\\beep.wav";
	}

	public static SoundManager getInstance() {
		if (instance == null) {
			instance = new SoundManager();
		}
		return instance;
	}

	public void playBeep(String path) {
		String beepPath = path;
		File beepFile;
		if (!path.equals("")) {
			beepFile = new File(beepPath);
		} else {
			beepFile = new File(defaultPath);
		}

		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(beepFile);
			audioClip = (Clip) AudioSystem.getClip();
			audioClip.open(audioStream);
			audioClip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		{

		}
	}

	public void stopBeep() {
		if (audioClip != null) {
			audioClip.close();
		}
	}
}
