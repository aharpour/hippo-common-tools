package com.tdclighthouse.commons.simpleform.validation.validator.item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tdclighthouse.commons.simpleform.html.FormItem;
import com.tdclighthouse.commons.simpleform.validation.FormItemValidator;

public class DateValidator implements FormItemValidator {

	@Override
	public boolean validate(FormItem item, String param) {
		if (StringUtils.isBlank(param)) {
			throw new IllegalArgumentException(
					"\"param\" argment must contain a date fromat");
		}
		boolean result = true;
		item.setErrorMessage(null);
		String value = item.getValue();
		if (!StringUtils.isBlank(value)) {
			value = value.trim();
			SimpleDateFormat dateFormat = new SimpleDateFormat(param);
			try {
				Date date = dateFormat.parse(value);
				if (!dateFormat.format(date).equals(value)) {
					// in this case the date is of the right format but such a
					// date dose not exist in the calendar
					result = false;
					item.setErrorMessage(getInvalidDateMessage());
				}
			} catch (ParseException e) {
				// the given string is not of the right format
				result = false;
				item.setErrorMessage(getBadFormatMessage(param));
			}
		}
		return result;
	}

	protected String getBadFormatMessage(String format) {
		return "simpleform.validation.badly.formated.date";
	}

	protected String getInvalidDateMessage() {
		return "simpleform.validation.invalid.date";
	}

}
