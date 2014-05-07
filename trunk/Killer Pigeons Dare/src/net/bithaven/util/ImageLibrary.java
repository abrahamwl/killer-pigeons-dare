package net.bithaven.util;

import java.util.TreeMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public final class ImageLibrary {
	private ImageLibrary() {
	}
	static final TreeMap<String,Image> map = new TreeMap<String,Image>();
	
	public static Image load (String file) {
		Image out = map.get(file);
		if (out != null) return out;
		try {
			out = new Image(file);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put(file, out);
		return out;
	}
}
