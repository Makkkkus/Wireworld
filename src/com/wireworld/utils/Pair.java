package com.wireworld.utils;

import com.wireworld.core.Cell;
import com.wireworld.core.Coordinate;

/**
 * Created by nicola on 23/11/14.
 */
public class Pair {
	private Coordinate coordinate;
	private Cell cell;

	public Pair(Coordinate coordinate, Cell cell) {
		this.coordinate = coordinate;
		this.cell = cell;
	}

	public Coordinate getCoordinate() {
		return this.coordinate;
	}

	public Cell getCell() {
		return this.cell;
	}
}
