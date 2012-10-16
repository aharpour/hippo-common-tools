package com.tdclighthouse.commons.utils.objectdump;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Ebrahim Aharpour
 *
 */
public class ObjectFilter implements Filter {

	public Collection<String> getFilteredMethods() {
		List<String> result = new ArrayList<String>();
		result.add("getClass");
		return result;
	}

}
