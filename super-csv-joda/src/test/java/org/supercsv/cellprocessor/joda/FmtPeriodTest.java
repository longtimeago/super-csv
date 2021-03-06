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
package org.supercsv.cellprocessor.joda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.supercsv.cellprocessor.joda.SuperCsvTestUtils.ANONYMOUS_CSVCONTEXT;

import java.util.Arrays;
import java.util.List;

import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.junit.Before;
import org.junit.Test;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.joda.mock.IdentityTransform;
import org.supercsv.exception.SuperCsvCellProcessorException;

/**
 * Tests the FmtPeriod cell processor.
 */
public class FmtPeriodTest {

	private static final String PERIOD_STRING = "P1Y2M3DT4H5M6S";
	private static final Period PERIOD = new Period(1, 2, 0, 3, 4, 5, 6, 0);

	private FmtPeriod processor1;
	private FmtPeriod processor2;
	private FmtPeriod processorChain1;
	private FmtPeriod processorChain2;
	private List<FmtPeriod> processors;
	private PeriodFormatter formatter;

	@Before
	public void setUp() {
		formatter = ISOPeriodFormat.standard();
		processor1 = new FmtPeriod();
		processor2 = new FmtPeriod(formatter);
		processorChain1 = new FmtPeriod(new IdentityTransform());
		processorChain2 = new FmtPeriod(formatter, new IdentityTransform());
		processors = Arrays.asList(processor1, processor2, processorChain1,
				processorChain2);
	}

	@Test
	public void testValidPeriodString() {
		for (CellProcessor p : processors) {
			assertEquals(PERIOD_STRING, p.execute(PERIOD, ANONYMOUS_CSVCONTEXT));
		}
	}

	@Test
	public void testNullInput() {
		for (CellProcessor p : processors) {
			try {
				p.execute(null, ANONYMOUS_CSVCONTEXT);
				fail("expecting SuperCsvCellProcessorException");
			} catch (SuperCsvCellProcessorException e) {
				assertEquals(
						"this processor does not accept null input - "
								+ "if the column is optional then chain an Optional() processor before this one",
						e.getMessage());
			}
		}
	}

	@Test
	public void testNonPeriodInput() {
		for (CellProcessor p : processors) {
			try {
				p.execute(123, ANONYMOUS_CSVCONTEXT);
				fail("expecting SuperCsvCellProcessorException");
			} catch (SuperCsvCellProcessorException e) {
				assertEquals(
						"the input value should be of type org.joda.time.Period but is java.lang.Integer",
						e.getMessage());
			}
		}
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor2WithNullNext() {
		new FmtPeriod((CellProcessor) null);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor3WithNullFormatter() {
		new FmtPeriod((PeriodFormatter) null);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor4WithNullFormatter() {
		new FmtPeriod((PeriodFormatter) null, new IdentityTransform());
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor4WithNullNext() {
		new FmtPeriod(formatter, null);
	}

}
