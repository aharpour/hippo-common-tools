/*
 *  Copyright 2012 Finalist B.V.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.tdclighthouse.commons.simpleform.validation.validator.item;

import com.tdclighthouse.commons.simpleform.html.FormItem;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class DutchPostalCodeValidator extends PatternMatchValidator {

	private static final String REGEX_PATTERN = "\\d{4}\\s?[a-zA-Z]{2}\\s{0,}";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tdclighthouse.commons.simpleform.validation.validator.item.PatternMatchValidator#validate(com.tdclighthouse
	 * .commons.simpleform.html.FormItem, java.lang.String)
	 */
	@Override
	public boolean validate(FormItem item, String param) {
		if (ConditionalValidator.isConditionalValidator(param, CONDITIONAL_VALIDATION)){
			if(ConditionalValidator.isConditionSatisfied(item, param, CONDITIONAL_VALIDATION)){
				return performValidation(item);
			}else{
				item.setErrorMessage(null);
				return true;
			}
		}else{
			return performValidation(item);
		}
	}

	private boolean performValidation(FormItem item) {
		if(item.getValue().length()<=6){
			return super.validate(item, REGEX_PATTERN);
		}else{
			item.setErrorMessage(getMessage());
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tdclighthouse.commons.simpleform.validation.validator.item.PatternMatchValidator#getMessage()
	 */
	@Override
	protected String getMessage() {
		return "simpleform.validation.invalid.postalcode";
	}

}
