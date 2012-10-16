package com.tdclighthouse.commons.simpleform.html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

public class FormItemGroup {

	private final LinkedHashMap<String, FormItem> items;
	private final LinkedHashMap<String, FormItemGroup> subgroups;
	private final FormItemGroup parent;
	private final String name;
	private String label;
	private final GroupType groupType;

	FormItemGroup(String groupName, String groupLabel, FormItemGroup parent) {
		this(groupName, groupLabel, parent, null);
	}

	FormItemGroup(String groupName, String groupLabel, FormItemGroup parent, GroupType groupType) {
		this.items = new LinkedHashMap<String, FormItem>();
		this.subgroups = new LinkedHashMap<String, FormItemGroup>();
		this.parent = parent;
		this.name = groupName;
		this.label = groupLabel;
		this.groupType = groupType;
	}

	/**
	 * Adds a single form item to the FormItemGroup items
	 * 
	 * @param item
	 */
	public void addItem(FormItem item) {
		item.setGroup(this);
		this.items.put(item.getName(), item);
	}

	/**
	 * this method get a collection of items and add them collectively to the
	 * FromItemGroup items
	 * 
	 * @param items
	 */
	public void addItems(Collection<FormItem> items) {
		for (Iterator<FormItem> iterator = items.iterator(); iterator.hasNext();) {
			FormItem formItem = iterator.next();
			addItem(formItem);
		}
	}

	/**
	 * adds a new formItemGroup to the current group and return it as an object
	 * 
	 * @param groupName
	 * @param label
	 * @return newly created FormItemGroup object
	 * @throws IllegalArgumentException
	 */
	public FormItemGroup addSubgroup(String groupName, String label) throws IllegalArgumentException {
		return addSubgroup(groupName, label, null);
	}

	public FormItemGroup addSubgroup(String groupName, String label, GroupType groupType)
			throws IllegalArgumentException {
		FormItemGroup child = new FormItemGroup(groupName, label, this, groupType);
		this.subgroups.put(groupName, child);
		return child;
	}

	public GroupType getGroupType() {
		return groupType;
	}

	/**
	 * @return the items
	 */
	public List<FormItem> getItems() {
		List<FormItem> result = new ArrayList<FormItem>();
		Map<String, FormItem> itemsMap = getItemsMap();
		Iterator<String> iterator = itemsMap.keySet().iterator();
		while (iterator.hasNext()) {
			result.add(itemsMap.get(iterator.next()));
		}
		return result;
	}

	public Map<String, FormItem> getItemsMap() {
		LinkedHashMap<String, FormItem> result = new LinkedHashMap<String, FormItem>();
		Iterator<String> iterator = this.subgroups.keySet().iterator();
		while (iterator.hasNext()) {
			FormItemGroup formItemGroup = this.subgroups.get(iterator.next());
			result.putAll(formItemGroup.getItemsMap());
		}
		result.putAll(this.items);
		return result;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the parent
	 */
	public FormItemGroup getParent() {
		return parent;
	}

	/**
	 * @param name
	 * @return
	 */
	public FormItemGroup getSubgroupByName(String name) {
		return this.subgroups.get(name);
	}

	/**
	 * @return the children
	 */
	@SuppressWarnings("unchecked")
	public Map<String, FormItemGroup> getSubgroups() {
		return MapUtils.unmodifiableMap(this.subgroups);
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	public enum GroupType {
		ONE_COLUMN("one-column"), TWO_COLUMN("two-column"), THREE_COLUMN("three-column");

		private final String label;

		private GroupType(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return label;
		}
	}

}
