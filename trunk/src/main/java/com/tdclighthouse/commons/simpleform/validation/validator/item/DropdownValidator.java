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

import org.apache.commons.lang.StringUtils;

import com.tdclighthouse.commons.simpleform.html.FormItem;
import com.tdclighthouse.commons.simpleform.html.FormItem.Type;
import com.tdclighthouse.commons.simpleform.html.Option;
import com.tdclighthouse.commons.simpleform.validation.FormItemValidator;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class DropdownValidator extends ConditionalValidator implements FormItemValidator {

	@Override
	public boolean validate(FormItem item, String param) {
		if (ConditionalValidator.isConditionalValidator(param, CONDITIONAL_VALIDATION)){
			if(ConditionalValidator.isConditionSatisfied(item, param, CONDITIONAL_VALIDATION)){
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
		if (item.getType() != Type.SELECT) {
			throw new IllegalArgumentException("this validator is" + " only applicable to FormItems of the type SELECT");
		}
		boolean result;
		if (StringUtils.isBlank(item.getValue())) {
			result = true;
		} else {
			result = false;
			for (Option option : item.getOptions()) {
				if (item.getValue().equals(option.getValue())) {
					result = true;
					break;
				}
			}
		}
		if (!result) {
			item.setErrorMessage(getMessage());
		} else {
			item.setAttributes(null);
		}
		return result;
	}

	protected String getMessage() {
		return "simpleform.validation.posted.value.not.valid";
	}

}
