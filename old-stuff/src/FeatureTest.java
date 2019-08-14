/**
 * 
 */


import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

/**
 * @author gp40285
 *
 */
public class FeatureTest {
	private Feature<Integer> fint = new Feature<Integer>("IntegerFeature",
			Integer.class);
	private Feature<String> fstr = new Feature<String>("StringFeature",
			String.class);
	@SuppressWarnings("unchecked")
	private Feature<Double> fdbl = (Feature<Double>) Feature
			.fromString("Double Feature (java.lang.Double)");
	@SuppressWarnings("unchecked")
	private Feature<URL> furl = (Feature<URL>) Feature
			.fromString("URL Feature (java.net.URL)");

	/**
	 * Test method for
	 * {@link uk.ac.hutton.caber.Feature#Feature(java.lang.String, java.lang.Class)}
	 * .
	 */
	@SuppressWarnings("unused")
	@Test
	public void testFeature() {
		fint = new Feature<Integer>("IntegerFeature", Integer.class);
		fstr = new Feature<String>("StringFeature", String.class);
		try {
			Feature<Double> dummyf = new Feature<Double>(null, Double.class);
			fail("I can create a feature with a null name");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<Double> dummyf = new Feature<Double>("", Double.class);
			fail("I can create a feature with an empty name");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<Double> dummyf = new Feature<Double>(" ", Double.class);
			fail("I can create a feature with a space for name");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<Double> dummyf = new Feature<Double>("Dummy", null);
			fail("I can create a feature with a null class");
		} catch (IllegalArgumentException e) {
			// OK
		}

	}

	/**
	 * Test method for
	 * {@link uk.ac.hutton.caber.Feature#fromString(java.lang.String)}.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void testFromString() {
		fdbl = (Feature<Double>) Feature
				.fromString("Double Feature (java.lang.Double)");
		furl = (Feature<URL>) Feature.fromString("URL Feature (java.net.URL)");
        fstr = (Feature<String>) Feature.fromString("String Feature (java.lang.String)");
        System.out.println("NAME " + fstr.getName() + " TYPE " + fstr.getType());
		try {
			Feature<?> dummy = Feature.fromString(null);
			fail("I can create a feature using fromString with a null argument");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<?> dummy = Feature.fromString("");
			fail("I can create a feature using fromString with an empty argument");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<?> dummy = Feature.fromString("blah(blah)");
			fail("I can create a feature using fromString with no space between label and class");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<?> dummy = Feature.fromString("(blah)");
			fail("I can create a feature with no label (no space)");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<?> dummy = Feature.fromString(" (blah)");
			fail("I can create a feature with no label (one space)");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<?> dummy = Feature.fromString("  (blah)");
			fail("I can create a feature with no label (two spaces)");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<?> dummy = Feature.fromString("blah ()");
			fail("I can create a feature with no class (no space)");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<?> dummy = Feature.fromString("blah ( )");
			fail("I can create a feature with no class (one space)");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<?> dummy = Feature.fromString("blah (  )");
			fail("I can create a feature with no class (two spaces)");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<?> dummy = Feature.fromString("blah Blah)");
			fail("I can create a feature with no open bracket");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<?> dummy = Feature.fromString("blah (Blah");
			fail("I can create a feature with no close bracket");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<?> dummy = Feature.fromString("blah (blah.blah.Blah)");
			fail("I can create a feature with an unknown class");
		} catch (IllegalArgumentException e) {
			// OK
		}
		try {
			Feature<Integer> dummy = (Feature<Integer>) Feature
					.fromString("blah (java.lang.String)");
			// fail("I can create a feature casting to Integer from String generics")
			// You can, of course do this, because at runtime the generics
			// disappear
			assertEquals(String.class, dummy.getType());
			dummy.getValue(2);
			// The above should cause a fail
			fail("I can assign an integer to a string typed feature");
			// Interestingly, failure will also occur if the feature has type
			// Long and the value has type Integer
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test method for {@link uk.ac.hutton.caber.Feature#getType()}.
	 */
	@Test
	public void testGetType() {
		assertEquals(fint.getType(), Integer.class);
		assertEquals(fstr.getType(), String.class);
		assertEquals(fdbl.getType(), Double.class);
		assertEquals(furl.getType(), URL.class);
	}

	/**
	 * Test method for {@link uk.ac.hutton.caber.Feature#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("IntegerFeature", fint.getName());
		assertEquals("Double Feature", fdbl.getName());
		assertEquals("StringFeature", fstr.getName());
		assertEquals("URL Feature", furl.getName());
	}

	/**
	 * Test method for
	 * {@link uk.ac.hutton.caber.Feature#getValue(java.lang.Object)}.
	 * 
	 * @throws MalformedURLException
	 */
	@Test
	public void testGetValue() throws MalformedURLException {
		Feature<Integer>.Value vint = fint.getValue(2);
		Feature<Double>.Value vdbl = fdbl.getValue(Math.PI);
		Feature<String>.Value vstr = fstr.getValue("test");
		Feature<URL>.Value vurl = furl.getValue(new URL(
				"http://www.hutton.ac.uk/"));

		assertTrue(vint.getValue() == 2);
		assertTrue(vdbl.getValue() == Math.PI);
		assertTrue(vstr.getValue().equals("test"));
		assertTrue(vurl.getValue().equals(new URL("http://www.hutton.ac.uk/")));

		assertTrue(vint.getFeature() == fint);
		assertTrue(vdbl.getFeature() == fdbl);
		assertTrue(vstr.getFeature() == fstr);
		assertTrue(vurl.getFeature() == furl);
	}

	/**
	 * Test method for {@link uk.ac.hutton.caber.Feature#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals("IntegerFeature (java.lang.Integer)", fint.toString());
		assertEquals("Double Feature (java.lang.Double)", fdbl.toString());
		assertEquals("StringFeature (java.lang.String)", fstr.toString());
		assertEquals("URL Feature (java.net.URL)", furl.toString());
	}

	/**
	 * Test method for
	 * {@link uk.ac.hutton.caber.Feature#equals(uk.ac.hutton.caber.Feature)}.
	 * 
	 * @throws MalformedURLException
	 */
	@Test
	public void testEqualsFeatureOfQ() throws MalformedURLException {
		Feature<Long> flng = new Feature<Long>("IntegerFeature", Long.class);

		assertFalse(flng.equals(fint));
		assertFalse(fint.equals(flng));

		Feature<Integer> fint2 = new Feature<Integer>("Integer Feature",
				Integer.class);

		assertFalse(fint.equals(fint2));
		assertFalse(fint2.equals(fint));

		Feature<Integer> fint3 = new Feature<Integer>("IntegerFeature",
				Integer.class);

		assertTrue(fint.equals(fint3));
		assertTrue(fint3.equals(fint));

		assertTrue(fint.equals(fint));
		assertTrue(flng.equals(flng));
		assertTrue(fint2.equals(fint2));
		assertTrue(fint3.equals(fint3));
		assertTrue(fdbl.equals(fdbl));
		assertTrue(fstr.equals(fstr));
		assertTrue(furl.equals(furl));

		Feature<Integer>.Value vint = fint.getValue(10248576);
		Feature<Integer>.Value wint = fint.getValue(-3276753);
		Feature<Long>.Value vlng = flng.getValue(10248576L);
		Feature<Long>.Value wlng = flng.getValue(-3276753L);
		Feature<Integer>.Value vint2 = fint2.getValue(10248576);
		Feature<Integer>.Value wint2 = fint2.getValue(-3276753);
		Feature<Integer>.Value vint3 = fint3.getValue(10248576);
		Feature<Integer>.Value wint3 = fint3.getValue(-3276753);

		assertFalse(vint.equals(vlng));
		assertFalse(vlng.equals(vint));
		assertFalse(wint.equals(wlng));
		assertFalse(wlng.equals(wint));
		assertFalse(vint.equals(wint));
		assertFalse(wint.equals(vint));
		assertFalse(vint.equals(vint2));
		assertFalse(vint2.equals(vint));
		assertFalse(wint.equals(wint2));
		assertFalse(wint2.equals(wint));
		assertFalse(vint2.equals(wint2));
		assertFalse(wint2.equals(vint2));
		assertFalse(vint3.equals(wint3));
		assertFalse(wint3.equals(vint3));

		assertTrue(vint.equals(vint3));
		assertTrue(vint3.equals(vint));
		assertTrue(wint.equals(wint3));
		assertTrue(wint3.equals(wint));

		assertTrue(vint.equals(vint));
		assertTrue(wint.equals(wint));
		assertTrue(vlng.equals(vlng));
		assertTrue(wlng.equals(wlng));
		assertTrue(vint2.equals(vint2));
		assertTrue(wint2.equals(wint2));
		assertTrue(vint3.equals(vint3));
		assertTrue(wint3.equals(wint3));

		Feature<URL>.Value vurl = furl.getValue(new URL(
				"http://www.hutton.ac.uk/"));
		Feature<URL>.Value wurl = furl.getValue(new URL(
				"http://www.macaulay.ac.uk/"));

		Feature<URL> furl2 = new Feature<URL>("URL Feature", URL.class);
		Feature<URL>.Value vurl2 = furl2.getValue(new URL(
				"http://www.hutton.ac.uk/"));
		Feature<URL>.Value wurl2 = furl2.getValue(new URL(
				"http://www.macaulay.ac.uk/"));

		Feature<URL> furl3 = new Feature<URL>("URLFeature", URL.class);
		Feature<URL>.Value vurl3 = furl3.getValue(new URL(
				"http://www.hutton.ac.uk/"));
		Feature<URL>.Value wurl3 = furl3.getValue(new URL(
				"http://www.macaulay.ac.uk/"));

		assertTrue(furl.equals(furl2));
		assertTrue(furl2.equals(furl));

		assertFalse(furl.equals(furl3));
		assertFalse(furl3.equals(furl));
		assertFalse(furl2.equals(furl3));
		assertFalse(furl3.equals(furl2));

		assertTrue(vurl.equals(vurl2));
		assertTrue(vurl2.equals(vurl));
		assertTrue(wurl.equals(wurl2));
		assertTrue(wurl2.equals(wurl));

		assertTrue(vurl.equals(vurl));
		assertTrue(wurl.equals(wurl));
		assertTrue(vurl2.equals(vurl2));
		assertTrue(wurl2.equals(wurl2));
		assertTrue(vurl3.equals(vurl3));
		assertTrue(wurl3.equals(wurl3));

		assertFalse(vurl.equals(wurl));
		assertFalse(wurl.equals(vurl));
		assertFalse(vurl2.equals(wurl2));
		assertFalse(wurl2.equals(vurl2));
		assertFalse(vurl3.equals(wurl3));
		assertFalse(wurl3.equals(vurl3));

		assertFalse(vurl.equals(wurl2));
		assertFalse(wurl2.equals(vurl));
		assertFalse(vurl.equals(vurl3));
		assertFalse(vurl3.equals(vurl));

	}

}
