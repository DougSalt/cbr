/**
 * 
 */


import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

import java.util.LinkedList;

import org.junit.Test;


/**
 * @author gp40285
 *
 */
public class FeatureSetTest {

	/**
	 * Test method for {@link uk.ac.hutton.caber.FeatureSet#FeatureSet()}.
	 */
	@Test
	public void testFeatureSet() {
		FeatureSet fs = new FeatureSet();
		try {
			fs.addFeature(new Feature<String>("StringFeature", String.class));
			fs.addFeature(new Feature<Integer>("IntegerFeature", Integer.class));
		} catch (CaseBaseException e) {
			fail("Could not create a feature set: " + e);
		}
		System.out.println(fs);
	}

	/**
	 * Test method for {@link uk.ac.hutton.caber.FeatureSet#FeatureSet(uk.ac.hutton.caber.Feature<?>[])}.
	 */
	@Test
	public void testFeatureSetFeatureOfQArray() {
		try {
			FeatureSet fs = new FeatureSet(new Feature<?>[] {
					new Feature<String>("StringFeature1", String.class),
					new Feature<String>("StringFeature2", String.class),
					new Feature<String>("StringFeature3", String.class) });
			System.out.println(fs);
		} catch (CaseBaseException e) {
			fail("Could not create a feature set of array: " + e);
		}
	}

	/**
	 * Test method for {@link uk.ac.hutton.caber.FeatureSet#FeatureSet(java.lang.Iterable)}.
	 */
	@Test
	public void testFeatureSetIterableOfFeatureOfQ() {
		LinkedList<Feature<?>> fl = new LinkedList<Feature<?>>();
		
		for(int i = 1; i < 100; i++) {
			fl.addLast(new Feature<String>("StringFeature" + i, String.class));
		}
		
		try {
			new FeatureSet(fl);
		} catch (CaseBaseException e) {
			fail("Could not create a feature set of iterable features: " + e);
		}
	}

	/**
	 * Test method for {@link uk.ac.hutton.caber.FeatureSet#addFeature(uk.ac.hutton.caber.Feature)}.
	 */
	@Test
	public void testAddFeature() {
		FeatureSet fs = new FeatureSet();
		
		try {
			fs.addFeature("StringFeature", String.class);
		}
		catch(CaseBaseException e) {
			fail("Could not add the first feature: " + e);
		}
		
		try {
			fs.addFeature(null);
			fail("Successfully added a null feature");
		}
		catch(IllegalArgumentException e) {
			// OK
		}
		catch(CaseBaseException e) {
			// OK
		}
		
		try {
			fs.addFeature("StringFeature", String.class);
			fail("Successfully added a new feature with the same name as the previous");
		}
		catch(CaseBaseException e) {
			// OK
		}
		
		try {
			fs.addFeature("StringFeature", Integer.class);
			fail("Successfully added a new feature with the same name as the previous, but a different type");
		}
		catch(CaseBaseException e) {
			// OK
		}
	}

	/**
	 * Test method for {@link uk.ac.hutton.caber.FeatureSet#hasFeature(java.lang.String)}.
	 */
	@Test
	public void testHasFeature() {
		Feature<String> f1 = new Feature<String>("StringFeature", String.class);
		Feature<Long> f2 = new Feature<Long>("LongFeature", Long.class);
		
		FeatureSet fs = new FeatureSet();
		
		assertFalse(fs.hasFeature("StringFeature"));
		assertFalse(fs.hasFeature("LongFeature"));
		
		fs.add(f1);
		
		assertTrue(fs.hasFeature("StringFeature"));
		assertFalse(fs.hasFeature("LongFeature"));
		
		fs.add(f2);
		
		assertTrue(fs.hasFeature("StringFeature"));
		assertTrue(fs.hasFeature("LongFeature"));
	}

	/**
	 * Test method for {@link uk.ac.hutton.caber.FeatureSet#getFeature(java.lang.String)}.
	 */
	@Test
	public void testGetFeatureString() {
		Feature<String> f1 = new Feature<String>("StringFeature", String.class);
		Feature<Long> f2 = new Feature<Long>("LongFeature", Long.class);
		FeatureSet fs = new FeatureSet();
		fs.add(f1);
		fs.add(f2);
		try {
			assertEquals(f1, fs.getFeature("StringFeature"));
		} catch (CaseBaseException e) {
			fail("No feature named StringFeature");
		}
		
		try {
			assertEquals(f2, fs.getFeature("LongFeature"));
		} catch (CaseBaseException e) {
			fail("No feature named LongFeature");
		}
		
		try {
			fs.getFeature("IntegerFeature");
			fail("There is a feature called IntegerFeature when there shouldn't be");
		} catch (CaseBaseException e) {
			// OK
		}

		try {
			fs.getFeature((String)null);
			fail("There is a null feature when there shouldn't be");
		} catch (CaseBaseException e) {
			// OK
		}
	}

	/**
	 * Test method for {@link uk.ac.hutton.caber.FeatureSet#getFeature(int)}.
	 */
	@Test
	public void testGetFeatureInt() {
		Feature<String> f1 = new Feature<String>("StringFeature", String.class);
		Feature<Long> f2 = new Feature<Long>("LongFeature", Long.class);
		FeatureSet fs = new FeatureSet();
		fs.add(f1);
		fs.add(f2);
		try {
			assertEquals(f1, fs.getFeature(0));
			assertNotEquals(f2, fs.getFeature(0));
		} catch (CaseBaseException e) {
			fail("No feature with index 0");
		}
		
		try {
			assertEquals(f2, fs.getFeature(1));
			assertNotEquals(f1, fs.getFeature(1));
		} catch (CaseBaseException e) {
			fail("No feature with index 1");
		}
		
		try {
			fs.getFeature(2);
			fail("There is a feature with index 2 when there shouldn't be");
		} catch (CaseBaseException e) {
			// OK
		}
		
		try {
			fs.getFeature(-1);
			fail("There is a feature with index -1 when there shouldn't be");
		} catch (CaseBaseException e) {
			// OK
		}
		
	}

	/**
	 * Test method for {@link uk.ac.hutton.caber.FeatureSet#hasType(java.lang.String, java.lang.Class)}.
	 * @throws CaseBaseException 
	 */
	@Test
	public void testHasTypeStringClassOfQ() throws CaseBaseException {
		Feature<String> f1 = new Feature<String>("StringFeature", String.class);
		Feature<Long> f2 = new Feature<Long>("LongFeature", Long.class);
		FeatureSet fs = new FeatureSet();
		fs.add(f1);
		fs.add(f2);
		assertTrue(fs.hasType("StringFeature", String.class));
		assertFalse(fs.hasType("StringFeature", Long.class));
		assertTrue(fs.hasType("LongFeature", Long.class));
		assertFalse(fs.hasType("LongFeature", String.class));
	}

	/**
	 * Test method for {@link uk.ac.hutton.caber.FeatureSet#hasType(int, java.lang.Class)}.
	 * @throws CaseBaseException 
	 */
	@Test
	public void testHasTypeIntClassOfQ() throws CaseBaseException {
		Feature<String> f1 = new Feature<String>("StringFeature", String.class);
		Feature<Long> f2 = new Feature<Long>("LongFeature", Long.class);
		FeatureSet fs = new FeatureSet();
		fs.add(f1);
		fs.add(f2);
		assertTrue(fs.hasType(0, String.class));
		assertFalse(fs.hasType(0, Long.class));
		assertTrue(fs.hasType(1, Long.class));
		assertFalse(fs.hasType(1, String.class));
	}

	@Test
	public void testSetValuesArrayOfObject() throws CaseBaseException {
		Feature<String> f1 = new Feature<String>("StringFeature", String.class);
		Feature<Long> f2 = new Feature<Long>("LongFeature", Long.class);
		FeatureSet fs = new FeatureSet();
		fs.add(f1);
		fs.add(f2);
		
		int howFar = 0;
		try {
			FeatureSet.Value fsv = fs.setValues("aString", -213423L);
			howFar++;
			System.out.println(fsv);
			assertEquals("aString", fsv.get(f1));
			howFar++;
			assertEquals("aString", fsv.get(0, new String[0]));
			howFar++;
			assertEquals("aString", fsv.get("StringFeature", new String[0]));
			howFar++;
			assertTrue(-213423L == fsv.get(f2));
			howFar++;
			assertTrue(-213423L == fsv.get(1, new Long[0]));
			howFar++;
			assertTrue(-213423L == fsv.get("LongFeature", new Long[0]));
			howFar++;
		} catch (CaseBaseException e) {
			fail(e.getMessage() + " how far = " + howFar);
		}
		
		
	}
}
