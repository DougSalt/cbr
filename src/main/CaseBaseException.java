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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOASE.  See the
 * GNU General Public License for more detials.
 *
 * You should have received a copy of the GNU General Public License
 * along with caber.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) The James Hutton Institute 2018
 */


public class CaseBaseException extends java.lang.Exception {

	/**
	 * stupid serialization ID thing
	 */
	private static final long serialVersionUID = 6711721917439731904L;

	public CaseBaseException(String msg) {
		super(msg);
	}
}
