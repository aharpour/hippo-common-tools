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
import com.tdclighthouse.commons.simpleform.html.FormItemGroup;

/**
 * @author Gridi Serbo
 */
public class ConditionalValidator {

	/**
	 * Receives as param a string formed this way:		groupName#conditioningItemName#wantedValue#conditional_OR_not
	 * where: 	conditional_OR_not == #conditional# == 	validate only if condition is satisfied
	 * 												otherwise validate regardless of condition  
	 * 			wantedValue == the value that the conditioning item must have, in order to perform the validation
	 * 			conditioningItemName == the name of the item whose value will discriminate the validation execution
	 * 			groupName == the name of the group in which the item is included
	 * 			
	 * 		The validation will be executed only if the conditioning item has the wanted value (not case sensitive)
	 **/
	
	public static final String CONDITIONAL_VALIDATION = "#conditional#";

	public static String getConditioningItemValue(FormItem item, String param, String condition){

		param = removeConditionalFromParam(param, condition);
		param = param.substring(0, param.lastIndexOf("#"));
		
		String itemName = param.substring(param.indexOf("#")+1);
		param = param.substring(0, param.lastIndexOf("#"));
		
		String groupName = param;
		
		FormItemGroup conditioningGroup = item.getGroup();
		
		if(conditioningGroup.getParent() != null){
			conditioningGroup = conditioningGroup.getParent().getSubgroupByName(groupName);
		}		
		
		FormItem conditioningItem = conditioningGroup.getItemByName(itemName);
		String conditioningItemValue = conditioningItem.getValue();
		
		return conditioningItemValue;
	}
	
	public static boolean isConditionSatisfied(FormItem item, String param, String condition){
		boolean result = false;
		
		if(getConditioningItemValue(item, param, condition)!=null){
			if(getConditioningItemValue(item, param, condition).equalsIgnoreCase(getWantedValue(param, condition))){
				result = true;
			}
		}
		return result;
	}
	
	public static boolean isConditionalValidator(String param, String conditional){
		boolean result = false;
		if(param.endsWith(conditional)){
			result = true;
		}
		return result;
	}
	
	public static String getWantedValue(String param, String condition){
		param = removeConditionalFromParam(param, condition);
		String wantedValue = param.substring(param.lastIndexOf("#")+1);
		return wantedValue;
	}
	
	public static String removeConditionalFromParam(String param, String condition){
		return param.substring(0, param.lastIndexOf(condition));
	}
}
