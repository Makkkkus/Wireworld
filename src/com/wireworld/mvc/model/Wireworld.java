package com.wireworld.mvc.model;

import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import com.wireworld.core.Cell;
import com.wireworld.core.Coordinate;
import com.wireworld.core.Grid;
import com.wireworld.mvc.controller.GameTickSubject;
import com.wireworld.mvc.view.ChangeGridSubject;
import com.wireworld.utils.Pair;

public class Wireworld extends Observable implements Observer {
	private static Wireworld instance;
	private Grid gameGrid;

	private Wireworld() {
		this.gameGrid = new Grid();
	}

	public static Wireworld getInstance() {
		if (instance == null) {
			synchronized (Wireworld.class) {
				instance = new Wireworld();
			}
		}
		return instance;
	}

	public void setCell(int x, int y, Cell.State state) {
		this.gameGrid.setCellState(x, y, state);
	}

	public void setGrid(Grid grid) {
		this.gameGrid = grid;
		setChanged();
		notifyObservers(gameGrid);
	}

	public Grid getGrid() {
		return this.gameGrid;
	}

	public void tick() {
		gameGrid.countNeighbourgs();
		Collection<Cell> cells = gameGrid.getGrid().values();
		for (Cell cell : cells) {
			switch (cell.getState()) {
			case ELECTRON_HEAD:
				cell.setState(Cell.State.ELECTRON_TAIL);
				break;
			case ELECTRON_TAIL:
				cell.setState(Cell.State.CONDUCTOR);
				break;
			case CONDUCTOR:
				if (cell.getNeighbourgs() == 1 || cell.getNeighbourgs() == 2)
					cell.setState(Cell.State.ELECTRON_HEAD);
				break;
			default:
			}
		}
		setChanged();
		notifyObservers(gameGrid);
	}

	public String toString() {
		return gameGrid.toString();
	}

	@Override
	public void update(Observable observable, Object o) {
		if (observable instanceof ChangeGridSubject) {
			if (o instanceof Pair) {
				Pair p = (Pair) o;
				Coordinate co = p.getCoordinate();
				Cell ce = p.getCell();
				setCell(co.getX(), co.getY(), ce.getState());
			}
		}

		if (observable instanceof GameTickSubject) {
			tick();
		}
	}
}
