package com.wireworld;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.SwingUtilities;

import com.wireworld.mvc.controller.SimulationThread;
import com.wireworld.mvc.controller.WorldKeyListener;
import com.wireworld.mvc.model.Wireworld;
import com.wireworld.mvc.view.WireworldGUI;
import com.wireworld.mvc.view.WorldGrid;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				int rows = 64;
				int cols = 128;
				int cellSize = 10;

				WorldGrid worldGrid = new WorldGrid(rows, cols, cellSize);
				Wireworld wireworld = Wireworld.getInstance();

				wireworld.addObserver(worldGrid);
				worldGrid.getChangeGridObserver().addObserver(wireworld);

				SimulationThread simulationThread = new SimulationThread();
				simulationThread.getGameTickSubject().addObserver(wireworld);
				WorldKeyListener keyListener = new WorldKeyListener(simulationThread);

				WireworldGUI wireworldGUI = new WireworldGUI();
				wireworldGUI.getContentPane().add(worldGrid, BorderLayout.CENTER);
				wireworldGUI.addKeyListener(keyListener);
				wireworldGUI.pack();
				wireworldGUI.setResizable(false);
				wireworldGUI.setLocation(
						(Toolkit.getDefaultToolkit().getScreenSize().width - wireworldGUI.getWidth()) / 2,
						(Toolkit.getDefaultToolkit().getScreenSize().height - wireworldGUI.getHeight()) / 2);
				wireworldGUI.setVisible(true);
			}
		});
	}
}