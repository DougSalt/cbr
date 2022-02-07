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
import org.nlogo.nvm.Context;
import org.nlogo.agent.World2D;
import org.nlogo.agent.Turtle2D;

/**
 * <!-- YesTest -->
 *
 * @author Doug salt
 *
 */
public class YesTest {

	/**
	 * Test method for
	 * {@link ../src/Yes.java }
	 */
	@Test
	public final void testValue() {
        World2D world = new World2D();
        Turtle2D turtle = new Turtle2D(world, world.getBreed("some"), 0.0, 0.0);
        Context context = new Context(new Job(), turtle, 0, new Activation());
        Yes tester = new Yes();
        Object[] someRandomArgs = new Object[]{"a","b","c"};
        //assertEquals("yes", tester.report(context, someRandomArgs), "This constant value must be 'yes'.");
	}
}
