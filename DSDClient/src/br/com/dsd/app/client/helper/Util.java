package br.com.dsd.app.client.helper;

import javax.swing.JTextField;

/**
 * Classe com os métodos utilitários
 * 
 * @author kl
 *
 */
public class Util {
	
	public static boolean isValidIP(String ip) {
		if (ip == null || ip.isEmpty()) {
			return false;
		}

		String[] octets = ip.split("\\.");
		if (octets.length != 4) {
			return false;
		}

		try {
			for (String octet : octets) {
				int i = Integer.parseInt(octet);
				if (i < 0 || i > 255) {
					return false;
				}
			}
		} catch (NumberFormatException e) {
			return false;
		}

		if (ip.endsWith(".")) {
			return false;
		}

		return true;
	}
	
	public static boolean isNotNullFields(JTextField... fields) {
		for(JTextField field : fields) {
			if("".equals(field.getText())) {
				return false;
			}
		}
		return true;
	}
	
	public static void clearFields(JTextField... fields) {
		for(JTextField field : fields) {
			field.setText("");
		}
	}

}
