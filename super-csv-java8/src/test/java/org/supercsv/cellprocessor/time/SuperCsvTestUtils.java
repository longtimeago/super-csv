/*
 * Copyright 2007 Kasper B. Graversen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.supercsv.cellprocessor.time;

import static org.junit.Assert.assertEquals;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

/**
 * Utility methods and constants for tests.
 * 
 * @author James Bassett
 */
public class SuperCsvTestUtils {
	
	public static final CsvContext ANONYMOUS_CSVCONTEXT = new CsvContext(1, 2, 3);
	
	/**
	 * Asserts that the processor gives the expected output for the supplied input.
	 * 
	 * @param processor
	 *            the processor
	 * @param input
	 *            the input
	 * @param expectedOutput
	 *            the expected output
	 */
	public static void assertExecution(final CellProcessor processor, final String input, final Object expectedOutput) {
		assertEquals(expectedOutput, processor.execute(input, ANONYMOUS_CSVCONTEXT));
	}
	
	
	
}
