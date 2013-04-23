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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.tdclighthouse.commons.simpleform.html.FormItem;
import com.tdclighthouse.commons.simpleform.validation.FormItemValidator;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class PatternMatchValidator extends ConditionalValidator implements FormItemValidator {

	@Override
	public boolean validate(FormItem item, String param) {
		if (isConditionalValidator(param, CONDITIONAL_VALIDATION)){
			if(isConditionSatisfied(item, param, CONDITIONAL_VALIDATION)){
				return performValidation(item, param);
			}else{
				item.setErrorMessage(null);
				return true;
			}
		}else{
			return performValidation(item, param);
		}
	}
	
	private boolean performValidation(FormItem item, String param) {
		boolean result = true;
		item.setErrorMessage(null);
		String value = item.getValue();
		if (!StringUtils.isBlank(value)) {
			value = value.trim();
			Pattern pattern = Pattern.compile(param);
			Matcher matcher = pattern.matcher(value);
			if (matcher.find()) {
				result = ((matcher.start() == 0) && (matcher.end() == value.length()));
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
