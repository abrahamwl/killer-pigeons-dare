package game;

import java.io.File;
import java.util.TreeMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public final class SoundLibrary {
	private SoundLibrary (){
	}
	
	private static final TreeMap<String,Sound> library = new TreeMap<String, Sound>();
	
	public static Sound getSound(String fileName) {
		Sound sound = library.get(fileName);
		
		if (sound == null) {
			String extension = "ogg";
			File[] f = (new File("./res/")).listFiles(new RegexpFilter(".*aif"));
			if(f.length != 0) extension = "aif";
			
			try {
				sound = new Sound(fileName + "." + extension);
			} catch (SlickException e) {
				e.printStackTrace();
				return null;
			}
			library.put(fileName, sound);
		}
		return sound;
	}
}
