package com.wireworld.mvc.view;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import com.wireworld.Main;
import com.wireworld.core.Grid;
import com.wireworld.mvc.model.Wireworld;
import com.wireworld.utils.GridExporter;
import com.wireworld.utils.GridImporter;

public class WireworldGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = -5371032858444158195L;
	private static JMenuBar menuBar;
	private static JMenu file;
	private static JMenuItem newGrid, open, save, saveAs;

	private static File openFile;

	private static JFileChooser fileChooser = new JFileChooser();
	private static Wireworld wireworld = Wireworld.getInstance();

	public WireworldGUI() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println(e);
		}

		setTitle(Main.WINDOW_TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// menu
		menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);

		file = new JMenu("File");
		menuBar.add(file);

		newGrid = new JMenuItem("New");
		newGrid.addActionListener(this);

		open = new JMenuItem("Open...");
		open.addActionListener(this);

		saveAs = new JMenuItem("Save As...");
		saveAs.addActionListener(this);

		save = new JMenuItem("Save...");
		save.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		save.addActionListener(this);

		file.add(newGrid);
		file.add(open);
		file.add(save);
		file.add(saveAs);

	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		Object source = actionEvent.getSource();
		
		fileChooser.setFileFilter(new FileFilter() {
			public String getDescription() {
				return "Wireworld file (*" + Main.FILE_EXTENTION + ")";
			}

			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					return f.getName().toLowerCase().endsWith(Main.FILE_EXTENTION);
				}
			}
		});

		if (source == open) {
			Open();
		} else if (source == saveAs) {
			SaveAs();
		} else if (source == save) {
			Save();
		} else if (source == newGrid) {
			NewGrid();
		}
	}

	private void Open() {
		int status = fileChooser.showOpenDialog(this);
		if (status == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			GridImporter importer = GridImporter.getInstance();

			openFile = file;
			try {
				wireworld.setGrid(importer.gridImport(file));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e, Main.WINDOW_TITLE, JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(this, e, Main.WINDOW_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void SaveAs() {
		int status = fileChooser.showSaveDialog(this);
		if (status == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			GridExporter exporter = GridExporter.getInstance();
			try {
				exporter.gridExport(Wireworld.getInstance(), file);
				JOptionPane.showMessageDialog(this, "Saved!", Main.WINDOW_TITLE, JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e, Main.WINDOW_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void Save() {
		if (openFile != null) {
			try {
				GridExporter exporter = GridExporter.getInstance();
				exporter.gridExport(Wireworld.getInstance(), openFile);
				JOptionPane.showMessageDialog(this, "Saved!", Main.WINDOW_TITLE, JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e, Main.WINDOW_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		} else SaveAs();
	}

	private void NewGrid() {
		int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to create a new grid?",
				Main.WINDOW_TITLE, JOptionPane.YES_NO_OPTION);

		if (dialogResult == JOptionPane.YES_OPTION) {
			wireworld.setGrid(new Grid());
			JOptionPane.showMessageDialog(this, "Created New Grid!", Main.WINDOW_TITLE, JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
