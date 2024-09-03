package org.qenherkhopeshef.svgnormalize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;

import jsesh.graphics.glyphs.model.SVGSignSource;
import jsesh.hieroglyphs.graphics.ShapeChar;


/**
 * Build an index of the SVG files contained in a directory. Useful for web
 * publication.
 * 
 * Oups. This class is an abomination. I'm ashamed I wrote it. There are statics
 * everywhere.
 * 
 * @author Serge Rosmorduc
 * 
 */
public class Main {

    static final String USAGE = "usage : \n"
            + "java org.qenherkhopeshef.svgnormalize.Main FOLDER\n"
            + "creates a folder called 'out' in the current folder with normalized versions of SVG files in FOLDER";

    private static final String XSLFILTER = ""
            + "<xsl:stylesheet version=\"1.0\"  xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"  xmlns:dc='http://purl.org/dc/elements/1.1/' >"
            + "<xsl:output method='text'/>"
            // Erase text
            + "<xsl:template match='text()|@*'>"
            + "</xsl:template>"
            // get description
            + "<xsl:template match='//dc:description'>"
            + "<xsl:value-of select='.'/>" + "</xsl:template>"
            + "</xsl:stylesheet>";

    /**
     * Read a file, containing a number of directory names.
     * 
     * @param file
     */
    private static void parseDirectoryList(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String line;
            while ((line = reader.readLine()) != null) {
                File dir = new File(line);
                if (dir.isDirectory()) {
                    System.out.println("working on " + dir);
                    normalize(dir);
                } else {
                    errorLog("" + dir
                            + " does not exists or is not a directory");
                }
            }
        } catch (FileNotFoundException e) {
            errorLog(e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void normalize(File directory) throws IOException {
        File[] files = directory.listFiles(
                pathname -> pathname.getName().toLowerCase(Locale.ENGLISH).endsWith(".svg"));

        Files.createDirectory(Path.of("out"));

        Arrays.sort(files, new GardinerFileOrderComparator());
        for (File f: files) {
            File outsvg = new File("out", f.getName());
            try {                
                buildImageFile(f, outsvg);
            } catch (Exception e) {
                errorLog("Could not build " + outsvg);
                e.printStackTrace();
            }
        }

    }

    private static void buildImageFile(File svgFile, File outFile)
            throws IOException {
        // Do not recreate existing svg...
        if (outFile.exists() && outFile.length() > 0
                && outFile.lastModified() > svgFile.lastModified())
            return;
        errorLog("working on " + svgFile);
        SVGSignSource source = new SVGSignSource(svgFile);
        if (source.hasNext()) {
            source.next();
            ShapeChar shapeChar = source.getCurrentShape();
            try (OutputStream outStream = new FileOutputStream(outFile)) {
                shapeChar.exportToSVG(outStream, "UTF-8", false);
            }
        }
    }

    private static void displayUsage() {
        errorLog(USAGE);
        System.exit(1);
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            displayUsage();
        }
        int start = 0;

        File directories[] = new File[args.length];
        for (int i = start; i < args.length; i++) {
            directories[i] = new File(args[i]);
            if (!directories[i].isDirectory()) {
                if (!directories[i].exists()) {
                    errorLog(directories[i].getName()
                            + " is not a directory or a text file with a list of directories in it.");
                    displayUsage();
                } else
                    parseDirectoryList(directories[i]);
            } else {
                println("working on " + directories[i].getName());
                normalize(directories[i]);
            }
        }
    }

    private static void errorLog(String message) {
        System.err.println(message);
    }

    private static void println(String s) {
        System.out.println(s);
    }
}
