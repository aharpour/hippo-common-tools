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
package com.tdclighthouse.commons.simpleform.html;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * @author Gridi Serbo
 * 
 */
public class RadioButtons extends JPanel implements java.awt.event.ActionListener{

	private static final long serialVersionUID = 1L;
	
	private ButtonGroup bgroup;
	private String groupName;
	private String value;

	public void actionPerformed(ActionEvent e) {
		setValue(e.getActionCommand());
	}
	
	public RadioButtons(String buttonsGroupName, String[] buttonValues, boolean[] defalutValue){
		super(new BorderLayout());
		
		this.groupName = buttonsGroupName;
		this.bgroup = new ButtonGroup();
		
		//create the buttons, set value of ActionComand and register a listener for each one of them
		List<JRadioButton> myButtons = new ArrayList<JRadioButton>();
		for (int i=0; i<buttonValues.length; i++){
			JRadioButton button = new JRadioButton(buttonValues[i], defalutValue[i]);
			button.setActionCommand(buttonValues[i]);
			button.addActionListener(this);
			myButtons.add(button);
		}
		
		//group the buttons
		for (int i=0; i<myButtons.size(); i++){
			this.bgroup.add(myButtons.get(i));
		}
	}

	/**
	 * @return the bgroup
	 */
	public ButtonGroup getBgroup() {
		return bgroup;
	}

	/**
	 * @param bgroup the bgroup to set
	 */
	public void setBgroup(ButtonGroup bgroup) {
		this.bgroup = bgroup;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
