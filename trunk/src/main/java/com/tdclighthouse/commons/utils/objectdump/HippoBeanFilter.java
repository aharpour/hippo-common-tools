package com.tdclighthouse.commons.utils.objectdump;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class HippoBeanFilter implements Filter {

	public Collection<String> getFilteredMethods() {
		List<String> result = new ArrayList<String>();
		result.add("getCanonicalHandleUUID");
		result.add("getContextualBean");
		result.add("getContextualParentBean");
		result.add("getEqualComparator");
		result.add("getLocalizedName");
		result.add("getNode");
		result.add("getObjectConverter");
		result.add("getParentBean");
		result.add("getProperties");
		result.add("getProperty");
		result.add("getValueProvider");
		return result;
	}

}
