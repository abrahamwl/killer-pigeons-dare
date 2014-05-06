package net.bithaven.efficiencyrpg;

import java.io.File;
import java.io.FilenameFilter;

public class RegexpFilter implements FilenameFilter {
	String regexp = null; 
	
	public RegexpFilter(String regexp) {
		this.regexp = regexp;
	}
	
	public boolean accept(File arg0, String arg1) {
		return arg1.matches(regexp);
	}
}
