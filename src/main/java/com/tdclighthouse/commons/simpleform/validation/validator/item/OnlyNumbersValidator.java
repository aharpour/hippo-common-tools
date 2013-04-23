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
import com.tdclighthouse.commons.simpleform.validation.FormItemValidator;

/**
 * @author Gridi Serbo
 * 
 */
public class OnlyNumbersValidator extends ConditionalValidator implements FormItemValidator{
	
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
	
	public String getBadFromatedMessage() {
		return "simpleform.validation.onlynumbers.not.matched";
	}
	
	private boolean performValidation(FormItem item, String param){
		boolean result = true;
		String providedValue = item.getValue();
		
		try {
			Integer.parseInt(providedValue);				
		} catch (NumberFormatException e) {
			item.setErrorMessage(getBadFromatedMessage());
			return false;
		}
		
		item.setErrorMessage(null);
		return result;
	}
	
}
