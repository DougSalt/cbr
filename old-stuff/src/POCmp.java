/**
 * POCmp.java, uk.ac.hutton.caber
 *
 * Copyright (C) The James Hutton Institute 2017
 *
 * This file is part of caber
 *
 * caber is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * caber is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * caber. If not, see <http://www.gnu.org/licenses/>. 
 */



/**
 * <!-- POCmp -->
 * 
 * Partial order comparison result
 *
 * @author Gary Polhill
 */
public enum POCmp {
	LT, EQ, GT, NO;
	
	public boolean comparable() {
		return this != NO;
	}
	
	public boolean incomparable() {
		return this == NO;
	}
	
	public boolean gt() {
		return this == GT;
	}
	
	public boolean ge() {
		return ((this == GT) || (this == EQ));
	}
	
	public boolean eq() {
		return this == EQ;
	}
	
	public boolean ne() {
		return ((this == LT) || (this == GT));
	}
	
	public boolean lt() {
		return this == LT;
	}
	
	public boolean le() {
		return ((this == LT) || (this == EQ));
	}
}
