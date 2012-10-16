package com.tdclighthouse.commons.simpleform.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class FormItem {

	private String name;
	private final Type type;
	private DataType dataType;
	private String value;
	private String label;
	private String hint;
	private List<String> validatorClassNames;
	private List<String> validationCriteria;
	private String errorMessage;
	private Boolean mandatory;
	private Boolean disabled = false;
	private final List<Option> options = new ArrayList<Option>();
	private Map<String, Object> attributes = new HashMap<String, Object>();
	private FormItemGroup group;

	public FormItem(Type type, String name) {
		this.type = type;
		this.name = name;
		validatorClassNames = new ArrayList<String>();
		validationCriteria = new ArrayList<String>();
		typeSpecificInitialization(type, name);
	}

	public FormItem(String name, Type type, String value, String label,
			String hint, List<String> validatorClassName,
			List<String> validationCriterion) {
		if (validatorClassName.size() != validationCriterion.size()) {
			throw new IllegalArgumentException("validatorClassName and"
					+ " validationCriterion has to be of the same size");

		}
		this.name = name;
		this.type = type;
		this.value = value;
		this.label = label;
		this.hint = hint;
		this.validatorClassNames = validatorClassName;
		this.validationCriteria = validationCriterion;
		typeSpecificInitialization(type, name);
	}

	public FormItem(String name, Type type, String value, String label,
			String hint, List<String> validatorClassName,
			List<String> validationCriterion, Boolean mandatory) {
		if (validatorClassName.size() != validationCriterion.size()) {
			throw new IllegalArgumentException("validatorClassName and"
					+ " validationCriterion has to be of the same size");

		}
		this.name = name;
		this.type = type;
		this.value = value;
		this.label = label;
		this.hint = hint;
		this.validatorClassNames = validatorClassName;
		this.validationCriteria = validationCriterion;
		this.mandatory = mandatory;
		typeSpecificInitialization(type, name);
	}

	public FormItem(String name, Type type, String value, String label,
			String hint, Boolean mandatory) {
		this.name = name;
		this.type = type;
		this.value = value;
		this.label = label;
		this.hint = hint;
		this.mandatory = mandatory;
		validatorClassNames = new ArrayList<String>();
		validationCriteria = new ArrayList<String>();
		typeSpecificInitialization(type, name);
	}

	private void typeSpecificInitialization(Type type, String name) {
	}

	public void addValidator(String validatorClassName,
			String validationCriterion) {
		this.validatorClassNames.add(validatorClassName);
		this.validationCriteria.add(validationCriterion);

	}

	
	/**
	 * @param group
	 */
	void setGroup(FormItemGroup group) {
		this.group = group;
	}
	
	/**
	 * @return group
	 */
	public FormItemGroup getGroup() {
		return group;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = (value == null ? null : value.trim());
	}

	/**
	 * @return the validatorClassNames
	 */
	public List<String> getValidatorClassNames() {
		return validatorClassNames;
	}

	/**
	 * @return the validationCriteria
	 */
	public List<String> getValidationCriteria() {
		return validationCriteria;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the hint
	 */
	public String getHint() {
		return hint;
	}

	/**
	 * @param hint
	 *            the hint to set
	 */
	public void setHint(String hint) {
		this.hint = hint;
	}

	/**
	 * @return the mandatory
	 */
	public boolean getMandatory() {
		boolean result = false;
		if (mandatory != null && mandatory) {
			result = mandatory;
		}
		return result;
	}

	/**
	 * @param mandatory
	 *            the mandatory to set
	 */
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	/**
	 * @return the disabled
	 */
	public boolean getDisabled() {
		boolean result = false;
		if (this.disabled != null && disabled) {
			result = this.disabled;
		}
		return result;
	}

	/**
	 * @param disabled
	 *            the disabled to set
	 */
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public void addOptions(List<Option> options) {
		this.options.addAll(options);
	}

	public void addOption(Option option) {
		this.options.add(option);
	}

	public Option getSelectedOption() {
		Option result = null;
		if (value != null) {
			for (Option option : options) {
				if (value.equals(option.getValue())) {
					result = option;
				}
			}
		}
		return result;
	}

	/**
	 * @return the options
	 */
	@SuppressWarnings("unchecked")
	public List<Option> getOptions() {
		return ListUtils.unmodifiableList(options);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void addAttribute(String key, Object value) {
		attributes.put(key, value);
	}

	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	/**
	 * this method returns the attributes map
	 * 
	 * @param attributesMap
	 */
	public void setAttributes(Map<String, Object> attributesMap) {
		this.attributes = attributesMap;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public static enum Type {
		TEXT("text"), TEXTFIELD("textfield"), PASSWORD("password"), SELECT(
				"select"), HIDDEN("hidden"), SIMPLECHECKBOX("checkbox");

		private final String type;

		private Type(String type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return this.type;
		}

	}

	public static enum DataType {
		PLAIN("plain"), DATEFIELD("datefield");

		private final String dataType;

		private DataType(String type) {
			this.dataType = type;
		}

		@Override
		public String toString() {
			return this.dataType;
		}

	}

	/**
	 * @return the dataType
	 */
	public DataType getDataType() {
		DataType result;
		if (this.dataType == null) {
			result = DataType.PLAIN;
		} else {
			result = this.dataType;
		}
		return result;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

}
