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
package de.uni_mannheim.informatik.wdi.usecase.events.model;

import java.io.Serializable;

import org.joda.time.DateTime;

import de.uni_mannheim.informatik.wdi.model.DefaultSchemaElement;
import de.uni_mannheim.informatik.wdi.model.Record;

/**
 * A {@link Record} which represents an actor
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class Actor extends Record<DefaultSchemaElement> implements Serializable {

	/*
	 * example entry <actor> <name>Janet Gaynor</name>
	 * <birthday>1906-01-01</birthday> <birthplace>Pennsylvania</birthplace>
	 * </actor>
	 */

	private static final long serialVersionUID = 1L;
	private String name;
	private DateTime birthday;
	private String birthplace;

	public Actor(String identifier, String provenance) {
		super(identifier, provenance);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(DateTime birthday) {
		this.birthday = birthday;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = 31 + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static final DefaultSchemaElement NAME = new DefaultSchemaElement("Name");
	public static final DefaultSchemaElement BIRTHPLACE = new DefaultSchemaElement("Birthplace");
	public static final DefaultSchemaElement BIRTHDATE = new DefaultSchemaElement("Birthdate");
	
	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.model.Record#hasValue(java.lang.Object)
	 */
	@Override
	public boolean hasValue(DefaultSchemaElement attribute) {
		if(attribute==NAME)
			return name!=null;
		else if(attribute==BIRTHPLACE) 
			return birthplace!=null;
		else if(attribute==BIRTHDATE)
			return birthday!=null;
		return false;
	}

	@Override
	public int getNumberOfValues(DefaultSchemaElement attribute) {
		if(attribute==NAME && name!=null)
			return 1;
		else if(attribute==BIRTHPLACE && birthplace!=null)
			return 1;
		else if(attribute==BIRTHDATE && birthday!=null)
			return 1;
		return 0;
	}

	@Override
	public DefaultSchemaElement[] getDefaultSchemaElements() {
		return new DefaultSchemaElement[0];
	}
}
