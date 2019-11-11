package br.com.dsd.app.server.helper;

import javax.swing.JTextField;

/**
 * Classe com os métodos utilitários
 * 
 * @author kl
 *
 */
public class Util {

	public static boolean isInteger(String s) {
		return isInteger(s, 10);
	}

	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

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

	public static void clearFields(JTextField... fields) {
		for (JTextField field : fields) {
			field.setText("");
		}
	}

	public static boolean isNotNullFields(JTextField... fields) {
		for (JTextField field : fields) {
			if ("".equals(field.getText())) {
				return false;
			}
		}
		return true;
	}

}
