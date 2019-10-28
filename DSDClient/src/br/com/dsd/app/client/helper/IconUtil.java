package br.com.dsd.app.client.helper;

import javax.swing.ImageIcon;

public class IconUtil {
	private static final String FORMAT 			= ".png";
	private static final String PATH 			= "./ico/";
	
	public static ImageIcon getIcon(String image) {
		return new ImageIcon(PATH + image + FORMAT);
	}
	
}
