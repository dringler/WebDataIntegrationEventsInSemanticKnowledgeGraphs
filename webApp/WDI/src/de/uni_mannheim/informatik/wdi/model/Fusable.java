/** 
 *
 * Copyright (C) 2015 Data and Web Science Group, University of Mannheim, Germany (code@dwslab.de)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package de.uni_mannheim.informatik.wdi.model;

/**
 * Interface representing all fusable records
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * @author Daniel Ringler
 * 
 */
public interface Fusable<SchemaElementType> {

	/**
	 * Returns the names of all attributes of the record. Required for
	 * generating the fusion reports.
	 * 
	 * @return
	 */
//	Collection<SchemaElementType> getAttributes();

	/**
	 * Returns whether the attribute has a value. Required for the calculation
	 * of density.
	 * 
	 * @param attribute
	 * @return
	 */
	boolean hasValue(SchemaElementType attribute);


	/**
	 * Returns the number of attributes. Required for the calculation of
	 * the attribute/value distribution
	 * @param attribute
	 * @return
	 */
	int getNumberOfValues(SchemaElementType attribute);

}
