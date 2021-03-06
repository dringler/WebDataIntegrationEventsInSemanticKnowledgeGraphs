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
package de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.numeric;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.uni_mannheim.informatik.wdi.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.wdi.model.Fusable;
import de.uni_mannheim.informatik.wdi.model.FusableValue;
import de.uni_mannheim.informatik.wdi.model.FusedValue;
import de.uni_mannheim.informatik.wdi.model.Matchable;

/**
 * Median {@link ConflictResolutionFunction}: Returns the median of all values
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 * @param <RecordType>
 */
public class Median<RecordType extends Matchable & Fusable<SchemaElementType>, SchemaElementType> extends
		ConflictResolutionFunction<Double, RecordType, SchemaElementType> {

	@Override
	public FusedValue<Double, RecordType, SchemaElementType> resolveConflict(
			Collection<FusableValue<Double, RecordType, SchemaElementType>> values) {

		List<Double> list = new LinkedList<>();

		for (FusableValue<Double, RecordType, SchemaElementType> value : values) {
			list.add(value.getValue());
		}

		Collections.sort(list);

		boolean isEven = list.size() % 2 == 0;
		if (list.size() == 0) {
			return new FusedValue<>((Double) null);
		} else if (isEven) {
			double middle = ((double) list.size() + 1.0) / 2.0;
			double median1 = list.get((int) Math.floor(middle) - 1);
			double median2 = list.get((int) Math.ceil(middle) - 1);

			return new FusedValue<>((median1 + median2) / 2.0);
		} else {
			int middle = list.size() / 2;

			return new FusedValue<>(list.get(middle - 1));
		}
	}
}
