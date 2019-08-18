package com.wireworld;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.wireworld.mvc.controller.SimulationThread;
import com.wireworld.mvc.controller.WorldKeyListener;
import com.wireworld.mvc.model.Wireworld;
import com.wireworld.mvc.view.WireworldGUI;
import com.wireworld.mvc.view.WorldGrid;

public class Main {
	public static final String fileType = ".w";
	private static int rows = 64, cols = 128, cellSize = 12;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				rows = 64;
				cols = 128;
				cellSize = 12;
				
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