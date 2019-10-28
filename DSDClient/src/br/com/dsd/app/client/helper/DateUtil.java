package br.com.dsd.app.client.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	public static String getChatDate(Date date) {
		return sdf.format(date);
	}

}
