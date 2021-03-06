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
package de.uni_mannheim.informatik.wdi.similarity;

/**
 * A {@link SimilarityMeasure} that checks two records for equality.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 * @param <DataType>
 */
public class EqualsSimilarity<DataType> extends SimilarityMeasure<DataType> {

	private static final long serialVersionUID = 1L;

	@Override
	public double calculate(DataType first, DataType second) {
		if (first == null || second == null) {
			return 0.0;
		} else {
			return first.equals(second) ? 1.0 : 0.0;
		}
	}

}
