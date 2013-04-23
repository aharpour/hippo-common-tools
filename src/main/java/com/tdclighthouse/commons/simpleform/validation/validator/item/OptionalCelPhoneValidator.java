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
public class OptionalCelPhoneValidator extends ConditionalValidator implements FormItemValidator{
	
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
	
	public String getBadPhoneNumberMessage() {
		return "simpleform.validation.bad.phonenumber";
	}
	
	public String getBadPhoneNumberTenNumbers() {
		return "simpleform.validation.bad.phonenumber.ten.numbers";
	}
	
	public String getBadPhoneNumberDashPosition() {
		return "simpleform.validation.bad.phonenumber.dash.position";
	}
	
	private boolean performValidation(FormItem item, String param){
		
		boolean result = true;
		String providedValue = item.getValue();
		
		if(providedValue!=null && !providedValue.equals("")){
			return realValidation(item, providedValue);
		}else{
			item.setErrorMessage(null);
		}
		
		return result;
	}
	
	private boolean realValidation(FormItem item, String providedValue) {
		boolean result = true;
		
		if(dashPositionValidation(item, providedValue)){
			providedValue = providedValue.replaceAll("-", "");
		
			if(providedValue.length()==10){
				try {
					Long.parseLong(providedValue);			
				} catch (NumberFormatException e) {
					item.setErrorMessage(getBadPhoneNumberMessage());
					return false;
				}
			}else{
				item.setErrorMessage(getBadPhoneNumberTenNumbers());
				return false;
			}
		}else{
			item.setErrorMessage(getBadPhoneNumberDashPosition());
			return false;
		}
		
		item.setErrorMessage(null);
		return result;
	}
	
	private boolean dashPositionValidation(FormItem item, String providedValue){
		boolean result = true;
		
		providedValue = noMoreThanOneDash(item, providedValue);
		if(!providedValue.equals("")){
			int dashindex = providedValue.indexOf("-");
			if(!(dashindex == -1 || dashindex == 2 || dashindex == 3 || dashindex == 4)){
				return false;
			}
		}else{
			return false;
		}

		return result;
	}
	
	private String noMoreThanOneDash(FormItem item, String providedValue){
		if(providedValue.indexOf("-") == providedValue.lastIndexOf("-")){
			return providedValue;
		}else{
			return "";
		}
	}
	
}
