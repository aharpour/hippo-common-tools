package com.tdclighthouse.commons.simpleform.html;

public class Option {
	private final String value;
	private String label;
	private String text;
	private boolean disabled;
	
	public Option(String value, String text) {
		super();
		this.value = value;
		this.text = text;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}
	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	

}
