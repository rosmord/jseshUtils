package org.qenherkhopeshef.largefontimporter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

public class Main {
    	public static void main(String[] args) throws InterruptedException,
			InvocationTargetException {
		SwingUtilities.invokeAndWait(() -> {
                    try {
                        new LargeFontImporter();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
	}
}
