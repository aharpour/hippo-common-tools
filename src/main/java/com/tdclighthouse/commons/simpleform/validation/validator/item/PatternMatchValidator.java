package com.tdclighthouse.commons.simpleform.validation.validator.item;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.tdclighthouse.commons.simpleform.html.FormItem;
import com.tdclighthouse.commons.simpleform.validation.FormItemValidator;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class PatternMatchValidator implements FormItemValidator {

	@Override
	public boolean validate(FormItem item, String param) {
		boolean result = true;
		item.setErrorMessage(null);
		String value = item.getValue();
		if (!StringUtils.isBlank(value)) {
			value = value.trim();
			Pattern pattern = Pattern.compile(param);
			Matcher matcher = pattern.matcher(value);
			if (matcher.find()) {
				result = ((matcher.start() == 0) && (matcher.end() == value
						.length()));
			} else {
				result = false;
				item.setErrorMessage(getMessage());
			}
		}
		return result;
	}

	protected String getMessage() {
		return "simpleform.validation.pattern.does.not.match";
	}

}
