/**
 * This file is part of caber.
 *
 * caber is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * caber is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOASE. See the
 * GNU General Public License for more detials.
 *
 * You should have received a copy of the GNU General Public License
 * along with caber. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) The James Hutton Institute 2018
 */


import static org.junit.Assert.*;

import org.junit.Test;

/**
 * <!-- StringInitializerTest -->
 *
 * @author GP40285
 *
 */
public class StringInitializerTest {

	/**
	 * Test method for
	 * {@link uk.ac.hutton.caber.StringInitializer#StringInitializer(java.lang.String, java.lang.Class)}.
	 */
	@Test
	public final void testStringInitializerStringClassOfT() {
		StringInitializer<String> si = new StringInitializer<String>("Test", String.class);
		StringInitializer<Double> di = new StringInitializer<Double>("2.0", Double.class);
		System.out.println(si);
		System.out.println(di);
	}

	/**
	 * Test method for
	 * {@link uk.ac.hutton.caber.StringInitializer#StringInitializer(java.lang.String, boolean)}.
	 */
	@Test
	public final void testStringInitializerStringBoolean() {
		StringInitializer<String> si = new StringInitializer<String>("java.lang.String(Another test with some () in)");
		System.out.println(si);
		StringInitializer<Double> di = new StringInitializer<Double>("java.lang.Double(" + Math.PI + ")");
		System.out.println(di + " = " + Math.PI + "?");
	}

	/**
	 * Test method for
	 * {@link uk.ac.hutton.caber.StringInitializer#StringInitializer(java.lang.Object)}.
	 */
	@Test
	public final void testStringInitializerT() {
		StringInitializer<Long> li = new StringInitializer<Long>(false, new Long(1234567890123456789L));
		assertFalse(li.isBase64());
		System.out.println(li);
		StringInitializer<Integer> ii = new StringInitializer<Integer>(true, new Integer(0));
		assertTrue(ii.isBase64());
		System.out.println(ii);
		StringInitializer<Integer[]> ai = new StringInitializer<Integer[]>(false, new Integer[] { 0, 1, 2, 3, 4 });
		// Can't be confident that this will not result in a Base64 string
		System.out.println(ai);
		ai = new StringInitializer<Integer[]>(true, new Integer[] { 0, 1, 2, 3, 4 });
		assertTrue(ai.isBase64());
		System.out.println(ai);
	}

	/**
	 * Test method for
	 * {@link uk.ac.hutton.caber.StringInitializer#isInitializable(java.lang.Class)}.
	 */
	@Test
	public final void testIsInitializableClassOfQ() {
		assertTrue(StringInitializer.isInitializable(String.class));
		assertTrue(StringInitializer.isInitializable(Double.class));
		assertTrue(StringInitializer.isInitializable(Long.class));
		assertTrue(StringInitializer.isInitializable(StringInitializer.class));
		assertFalse(StringInitializer.isInitializable(Object.class));
		assertFalse(StringInitializer.isInitializable(Math.class));
	}

	/**
	 * Test method for
	 * {@link uk.ac.hutton.caber.StringInitializer#newInstance(java.lang.Class, java.lang.String)}.
	 */
	@Test
	public final void testNewInstanceClassOfVString() {
		assertEquals(new Double(1e-9), StringInitializer.newInstance(Double.class, "1e-9"));
		assertEquals("java.lang.String", StringInitializer.newInstance(String.class, "java.lang.String"));
	}

	/**
	 * Test method for
	 * {@link uk.ac.hutton.caber.StringInitializer#parseNewInstance(java.lang.String)}.
	 */
	@Test
	public final void testParseNewInstance() {
		try {
			assertEquals(2.0, StringInitializer.parseNewInstance("java.lang.Double(2.0)"));
			assertEquals(1e-9, StringInitializer.parseNewInstance("java.lang.Double(1e-9)"));
			assertEquals("(hello world)", StringInitializer.parseNewInstance("java.lang.String((hello world))"));
		}
		catch(ClassNotFoundException e) {
			fail(e.getMessage());
		}

		try {
			StringInitializer.parseNewInstance("WibbleYak(blah blah)");
			fail("Class WibbleYak was found!");
		}
		catch(ClassNotFoundException e) {
			// OK
		}
	}

	/**
	 * Test method for
	 * {@link uk.ac.hutton.caber.StringInitializer#newBase64Instance(java.lang.String)}.
	 */
	@Test
	public final void testNewBase64Instance() {
		try {
			Object p = StringInitializer.newBase64Instance(StringInitializer.BASE64_START
					+ "rO0ABXNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAAAA"
					+ StringInitializer.BASE64_END);
			assertEquals(0, p);
			// Array handling not brilliant, but this works:
			Object[] o = (Object[])StringInitializer.newBase64Instance(StringInitializer.BASE64_START
					+ "rO0ABXVyABRbTGphdmEubGFuZy5JbnRlZ2VyO/6XraABg+IbAgAAeHAAAAAFc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAABzcQB+AAIAAAABc3EAfgACAAAAAnNxAH4AAgAAAANzcQB+AAIAAAAE"
					+ StringInitializer.BASE64_END);
			Integer[] ary = new Integer[] { 0, 1, 2, 3, 4 };
			assertArrayEquals(ary, o);
		}
		catch(ClassNotFoundException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link uk.ac.hutton.caber.StringInitializer#toBase64String(java.lang.Object)}.
	 */
	@Test
	public final void testToBase64String() {
		for(int i = 0; i < 10000; i++) {
			Double r = Math.random();
			String rb64 = StringInitializer.toBase64String(r);
			try {
				assertEquals(r, StringInitializer.newBase64Instance(rb64));
			}
			catch(ClassNotFoundException e) {
				fail(e.getMessage());
			}
		}
	}

	/**
	 * Test method for
	 * {@link uk.ac.hutton.caber.StringInitializer#isInitializable()}.
	 */
	@Test
	public final void testIsInitializable() {
		StringInitializer<Double> di = new StringInitializer<Double>("2.0", Double.class);
		assertTrue(di.isInitializable());
		StringInitializer<Double[]> dia = new StringInitializer<Double[]>(false, new Double[] {2.0, 1.4, 89.7, 1.234567e10});
		assertFalse(dia.isInitializable());
	}

	/**
	 * Test method for {@link uk.ac.hutton.caber.StringInitializer#newInstance()}.
	 */
	@Test
	public final void testNewInstance() {
		for(int i = 0; i < 10000; i++) {
			long l = (long)(Math.random() * (double)Long.MAX_VALUE);
			StringInitializer<Long> li = new StringInitializer<Long>(Long.toString(l), Long.class);
			assertTrue(l == li.newInstance());
		}
	}

	/**
	 * Test method for {@link uk.ac.hutton.caber.StringInitializer#toString()} and
	 * {@link uk.ac.hutton.caber.StringInitializer#restores(java.lang.Object)}.
	 */
	@Test
	public final void testToString() {
		for(int i = 0; i < 10000; i++) {
			Double d = Math.random() / Math.random();
			StringInitializer<Double> di = new StringInitializer<Double>(false, d);
			String dstr = di.toString();
			StringInitializer<Double> di2 = new StringInitializer<Double>(dstr);
			assertTrue(di.equals(di2));
			assertEquals(d, di2.newInstance());
			assertTrue(di.restores(d));
			assertTrue(di2.restores(d));
		}
	}

	/**
	 * Test method for
	 * {@link uk.ac.hutton.caber.StringInitializer#toInitializableString(java.lang.Object)}.
	 */
	@Test
	public final void testToInitializableString() {
		try {
			assertEquals(2.0, StringInitializer.parseNewInstance(StringInitializer.toInitializableString(new Double(2.0))));
		}
		catch(ClassNotFoundException e) {
			fail(e.getMessage());
		}
	}

}
