package com.tdclighthouse.commons.simpleform.validation.validator.item;

import com.tdclighthouse.commons.simpleform.html.FormItem;

public class DutchPostalCodeValidator extends PatternMatchValidator {

	private static final String REGEX_PATTERN = "\\d{4}\\s?[a-zA-Z]{2}\\s{0,}";

	/* (non-Javadoc)
	 * @see com.tdclighthouse.commons.simpleform.validation.validator.item.PatternMatchValidator#validate(com.tdclighthouse.commons.simpleform.html.FormItem, java.lang.String)
	 */
	@Override
	public boolean validate(FormItem item, String param) {
		return super.validate(item, REGEX_PATTERN);
	}

	/* (non-Javadoc)
	 * @see com.tdclighthouse.commons.simpleform.validation.validator.item.PatternMatchValidator#getMessage()
	 */
	@Override
	protected String getMessage() {
		return "simpleform.validation.invalid.postalcode";
	}

}
