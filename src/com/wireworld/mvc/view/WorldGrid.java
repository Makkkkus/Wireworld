package com.wireworld.mvc.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.wireworld.core.Cell;
import com.wireworld.core.Coordinate;
import com.wireworld.core.Grid;
import com.wireworld.mvc.controller.WorldMouseListener;
import com.wireworld.mvc.model.WorldCell;
import com.wireworld.utils.Pair;

public class WorldGrid extends JPanel implements Observer {
	private static final long serialVersionUID = 8591351883511324431L;

	private WorldCell[][] worldGrid;
	private ChangeGridSubject observable;

	public WorldGrid(int row, int col, int cellWidth) {
		worldGrid = new WorldCell[row][col];
		observable = new ChangeGridSubject();

		WorldMouseListener mouseListener = new WorldMouseListener(this);

		Dimension cellSize = new Dimension(cellWidth, cellWidth);

		setLayout(new GridLayout(row, col));

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				worldGrid[i][j] = new WorldCell(i, j);
				worldGrid[i][j].setOpaque(true);
				worldGrid[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
				worldGrid[i][j].addMouseListener(mouseListener);
				worldGrid[i][j].setPreferredSize(cellSize);
				add(worldGrid[i][j]);
			}
		}
	}

	public ChangeGridSubject getChangeGridObserver() {
		return this.observable;
	}

	public void labelPressed(WorldCell worldCell, Cell.State state) {
		worldCell.setState(state);
		observable.setChanged();
		observable.notifyObservers(new Pair(new Coordinate(worldCell.getRow(), worldCell.getCol()), new Cell(state)));
	}

	public void update(Grid grid) {
		for (int i = 0; i < worldGrid.length; i++) {
			for (int j = 0; j < worldGrid[0].length; j++) {
				labelPressed(worldGrid[i][j], grid.getCellState(i, j));
			}
		}
	}

	@Override
	public void update(Observable observable, Object o) {
		if (o instanceof Grid) {
			update((Grid) o);
		}
	}
}
