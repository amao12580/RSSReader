package com.rometools.rome.io.impl;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateParser {
	private static final String[] RFC822_MASKS = { "EEE, dd MMM yy HH:mm:ss z", "EEE, dd MMM yy HH:mm z",
			"dd MMM yy HH:mm:ss z", "dd MMM yy HH:mm z" };
	private static final String[] W3CDATETIME_MASKS = { "yyyy-MM-dd'T'HH:mm:ss.SSSz", "yyyy-MM-dd't'HH:mm:ss.SSSz",
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd't'HH:mm:ss.SSS'z'", "yyyy-MM-dd'T'HH:mm:ssz",
			"yyyy-MM-dd't'HH:mm:ssz", "yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd't'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm:ss'Z'",
			"yyyy-MM-dd't'HH:mm:ss'z'", "yyyy-MM-dd'T'HH:mmz", "yyyy-MM'T'HH:mmz", "yyyy'T'HH:mmz",
			"yyyy-MM-dd't'HH:mmz", "yyyy-MM-dd'T'HH:mm'Z'", "yyyy-MM-dd't'HH:mm'z'", "yyyy-MM-dd", "yyyy-MM", "yyyy" };
	private static final String[] masks = { "yyyy-MM-dd'T'HH:mm:ss.SSSz", "yyyy-MM-dd't'HH:mm:ss.SSSz",
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd't'HH:mm:ss.SSS'z'", "yyyy-MM-dd'T'HH:mm:ssz",
			"yyyy-MM-dd't'HH:mm:ssz", "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd't'HH:mm:ss'z'", "yyyy-MM-dd'T'HH:mmz",
			"yyyy-MM-dd't'HH:mmz", "yyyy-MM-dd'T'HH:mm'Z'", "yyyy-MM-dd't'HH:mm'z'", "yyyy-MM-dd", "yyyy-MM", "yyyy" };
	private static String[] ADDITIONAL_MASKS = PropertiesLoader.getPropertiesLoader()
			.getTokenizedProperty("datetime.extra.masks", "|");

	private static Date parseUsingMask(String[] masks, String sDate, Locale locale) {
		// System.out.println("masks:" + masks.toString() + ",sDate=" + sDate +
		// ",locale=" + locale.toString());
		if (sDate.indexOf("+") < 0 && sDate.indexOf("-") < 0 && !sDate.endsWith("GMT")) {
			sDate = sDate + " GMT";
		}
		if (sDate != null) {
			sDate = sDate.trim();
		}
		ParsePosition pp = null;
		Date d = null;
		for (int i = 0; (d == null) && (i < masks.length); i++) {
			DateFormat df = new SimpleDateFormat(masks[i], locale);

			df.setLenient(true);
			try {
				pp = new ParsePosition(0);
				d = df.parse(sDate, pp);
				if (pp.getIndex() != sDate.length()) {
					d = null;
				}
			} catch (Exception localException) {
			}
		}
		// if(d!=null){
		// System.out.println("Result:"+d.toString());
		// }else{
		// System.out.println("Result:null");
		// }
		return d;
	}

	public static Date parseRFC822(String sDate, Locale locale) {
		int utIndex = sDate.indexOf(" UT");
		if (utIndex > -1) {
			String pre = sDate.substring(0, utIndex);
			String post = sDate.substring(utIndex + 3);
			sDate = pre + " GMT" + post;
		}
		return parseUsingMask(RFC822_MASKS, sDate, locale);
	}

	public static Date parseW3CDateTime(String sDate, Locale locale) {
		int tIndex = sDate.indexOf("T");
		if (tIndex > -1) {
			if (sDate.endsWith("Z")) {
				sDate = sDate.substring(0, sDate.length() - 1) + "+00:00";
			}
			int tzdIndex = sDate.indexOf("+", tIndex);
			if (tzdIndex == -1) {
				tzdIndex = sDate.indexOf("-", tIndex);
			}
			if (tzdIndex > -1) {
				String pre = sDate.substring(0, tzdIndex);
				int secFraction = pre.indexOf(",");
				if (secFraction > -1) {
					pre = pre.substring(0, secFraction);
				}
				String post = sDate.substring(tzdIndex);
				sDate = pre + "GMT" + post;
			}
		} else {
			sDate = sDate + "T00:00GMT";
		}
		return parseUsingMask(W3CDATETIME_MASKS, sDate, locale);
	}

	public static Date parseDate(String sDate, Locale locale) {
		Date date = parseW3CDateTime(sDate, locale);
		if (date == null) {
			date = parseRFC822(sDate, locale);
			if ((date == null) && (ADDITIONAL_MASKS.length > 0)) {
				date = parseUsingMask(ADDITIONAL_MASKS, sDate, locale);
			}
		}
		return date;
	}

	public static String formatRFC822(Date date, Locale locale) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", locale);
		dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormater.format(date);
	}

	public static String formatW3CDateTime(Date date, Locale locale) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", locale);
		dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormater.format(date);
	}
}
