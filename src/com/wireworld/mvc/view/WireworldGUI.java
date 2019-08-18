package com.wireworld.mvc.view;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

import com.wireworld.Main;
import com.wireworld.core.Grid;
import com.wireworld.mvc.model.Wireworld;
import com.wireworld.utils.GridExporter;
import com.wireworld.utils.GridImporter;

public class WireworldGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = -5371032858444158195L;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem newPage, open, save, saveAs;
	
	private static File openFile;

	public WireworldGUI() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception ignored) {}
		
		setTitle("Wireworld");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// menu
		menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);

		file = new JMenu("File");
		menuBar.add(file);
		
		newPage = new JMenuItem("New");
		newPage.addActionListener(this);
		
		open = new JMenuItem("Open...");
		open.addActionListener(this);
		
		saveAs = new JMenuItem("Save As...");
		saveAs.addActionListener(this);
		
		save = new JMenuItem("Save...");
		save.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		save.addActionListener(this);
		
		file.add(newPage);
		file.add(open);
		file.add(save);
		file.add(saveAs);
		
		}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		Object source = actionEvent.getSource();
		JFileChooser fileChooser = new JFileChooser();
		Wireworld wireworld = Wireworld.getInstance();
		
		
		fileChooser.setFileFilter(new FileFilter() {
			public String getDescription() {
				return "Wireworld file (*" + Main.fileType +")";
			}

			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					return f.getName().toLowerCase().endsWith(Main.fileType);
				}
			}
		});
		
		
		if (source == open) {
			int status = fileChooser.showOpenDialog(this);
			if (status == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				GridImporter importer = GridImporter.getInstance();
				
				openFile = file;
				try {
					wireworld.setGrid(importer.gridImport(file));
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (source == saveAs) {
			int status = fileChooser.showSaveDialog(this);
			if (status == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				GridExporter exporter = GridExporter.getInstance();
				try {
					exporter.gridExport(Wireworld.getInstance(), file);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (source == save) {
			if (openFile != null) {
				try {
					GridExporter exporter = GridExporter.getInstance();
					exporter.gridExport(Wireworld.getInstance(), openFile);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				FileNotFoundException e = new FileNotFoundException();
				JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}else if (source == newPage) {
			JOptionPane.showConfirmDialog(this, "Are you sure you want to create a new grid?");
			
			wireworld.setGrid(new Grid());
			JOptionPane.showMessageDialog(this, "Created New Grid!");
		}
	}
}
