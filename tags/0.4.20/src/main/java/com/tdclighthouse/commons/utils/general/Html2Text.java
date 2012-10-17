package com.tdclighthouse.commons.utils.general;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Html2Text extends HTMLEditorKit.ParserCallback {

	public static Logger log = LoggerFactory.getLogger(Html2Text.class);

	private static Html2Text instance = new Html2Text();
	StringBuffer s;

	public Html2Text() {
	}

	public void parse(Reader in) throws IOException {
		s = new StringBuffer();
		ParserDelegator delegator = new ParserDelegator();
		// the third parameter is TRUE to ignore charset directive
		delegator.parse(in, this, Boolean.TRUE);
	}

	public void handleText(char[] text, int pos) {
		s.append(text);
	}

	public String getText() {
		return s.toString();
	}

	public static String getText(String html) {
		String result;
		try {
			result = Jsoup.parse(html).text();
		} catch (Exception e) {
			try {
				Reader htmlReader = new StringReader(html);
				instance.parse(htmlReader);
				htmlReader.close();
				result = instance.getText();
			} catch (IOException e1) {
				log.error("an IOException was thrown while trying"
						+ " to strip a piece of html of its tags", e1);
				result = html;
			}
		}
		return result;
	}
}