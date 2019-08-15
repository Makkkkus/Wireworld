package com.wireworld.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.wireworld.core.Grid;

public class GridImporter {
	private static GridImporter instance;

	private GridImporter() {
	}

	public static GridImporter getInstance() {
		if (instance == null) {
			synchronized (GridImporter.class) {
				instance = new GridImporter();
			}
		}
		return instance;
	}

	public Grid gridImport(File file) throws IOException, ClassNotFoundException {
		ObjectInputStream inputStream = null;
		try {
			inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			return (Grid) inputStream.readObject();
		} finally {
			if (inputStream != null)
				inputStream.close();
		}

	}
}
