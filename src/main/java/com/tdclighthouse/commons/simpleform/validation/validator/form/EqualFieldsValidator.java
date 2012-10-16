package com.tdclighthouse.commons.simpleform.validation.validator.form;

import com.tdclighthouse.commons.simpleform.html.Form;
import com.tdclighthouse.commons.simpleform.validation.FormValidator;

public class EqualFieldsValidator implements FormValidator {

	protected final int fieldNumber1;
	protected final int fieldNumber2;

	public EqualFieldsValidator(int fieldNumber1, int fieldNumber2) {
		this.fieldNumber1 = fieldNumber1;
		this.fieldNumber2 = fieldNumber2;
	}

	public boolean validate(Form form) {
		boolean result;
		int max = Math.max(fieldNumber1, fieldNumber2);
		if (form.getItems().size() < max) {
			throw new IllegalArgumentException(
					"this form does not have enough number of formItems");
		} else {
			String value1 = form.getItems().get(fieldNumber1).getValue();
			String value2 = form.getItems().get(fieldNumber2).getValue();
			if (value1 != null) {
				result = value1.equals(value2);
			} else {
				result = (value2 == null);
			}
		}
		return result;
	}

	public String getErrorMessage(Form form) {
		String label1 = form.getItems().get(fieldNumber1).getLabel();
		String label2 = form.getItems().get(fieldNumber2).getLabel();
		return String.format("the value of the fields %s and %s are not the same.", label1, label2);
	}

}
