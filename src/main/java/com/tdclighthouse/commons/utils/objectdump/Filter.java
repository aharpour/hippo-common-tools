package com.tdclighthouse.commons.utils.objectdump;

import java.util.Collection;

/**
 * @author Ebrahim Aharpour
 *
 */
public interface Filter extends Cloneable {
	
	public Collection<String> getFilteredMethods();

}
