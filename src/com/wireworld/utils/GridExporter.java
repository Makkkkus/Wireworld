package com.wireworld.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.wireworld.mvc.model.Wireworld;

public class GridExporter {
	private static GridExporter instance;

	private GridExporter() {
	}

	public static GridExporter getInstance() {
		if (instance == null) {
			synchronized (GridExporter.class) {
				instance = new GridExporter();
			}
		}
		return instance;
	}

	public void gridExport(Wireworld wireworld, File file) throws IOException {
		ObjectOutputStream outputStream = null;
		try {
			outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			outputStream.writeObject(wireworld.getGrid());
		} finally {
			if (outputStream != null) {
				outputStream.flush();
				outputStream.close();
			}
		}

	}
}
