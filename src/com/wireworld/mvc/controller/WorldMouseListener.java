package com.wireworld.mvc.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.wireworld.core.Cell;
import com.wireworld.mvc.model.WorldCell;
import com.wireworld.mvc.view.WorldGrid;

/**
 * Created by nicola on 29/08/14.
 */
public class WorldMouseListener extends MouseAdapter {
	private WorldGrid worldGrid;
	private boolean mouseButton1Pressed;

	public WorldMouseListener(WorldGrid worldGrid) {
		this.worldGrid = worldGrid;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseButton1Pressed = true;

		WorldCell worldCell = (WorldCell) e.getSource();
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (worldCell.getState() == Cell.State.CONDUCTOR) {
				worldGrid.labelPressed(worldCell, Cell.State.EMPTY);
			} else
				worldGrid.labelPressed(worldCell, Cell.State.CONDUCTOR);
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			if (worldCell.getState() == Cell.State.CONDUCTOR) {
				worldGrid.labelPressed(worldCell, Cell.State.ELECTRON_HEAD);
			} else if (worldCell.getState() == Cell.State.ELECTRON_HEAD) {
				worldGrid.labelPressed(worldCell, Cell.State.ELECTRON_TAIL);
			} else {
				worldGrid.labelPressed(worldCell, Cell.State.CONDUCTOR);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		WorldCell worldCell = (WorldCell) e.getSource();
		if (mouseButton1Pressed) {
			if (worldCell.getState() == Cell.State.CONDUCTOR) {
				worldGrid.labelPressed(worldCell, Cell.State.EMPTY);
			} else
				worldGrid.labelPressed(worldCell, Cell.State.CONDUCTOR);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseButton1Pressed = false;
	}
}
