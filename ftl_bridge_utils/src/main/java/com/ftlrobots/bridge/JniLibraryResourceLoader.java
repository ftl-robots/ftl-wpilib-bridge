package com.ftlrobots.bridge;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.wpi.first.wpiutil.RuntimeDetector;

public final class JniLibraryResourceLoader {

    private static final Logger sLogger = LogManager.getLogger(JniLibraryResourceLoader.class);

    private static final File TEMP_DIR_ROOT;
    private static final File TEMP_DIR;
    private static final Set<String> LOADED_LIBS;

    static {
        TEMP_DIR_ROOT = new File("temp");
        removeOldLibraries(TEMP_DIR_ROOT);

        long randNum = new Random().nextLong();
        TEMP_DIR = new File(TEMP_DIR_ROOT, Long.toString(randNum));
        if (!TEMP_DIR.mkdirs()) {
            sLogger.log(Level.ERROR, "Could not create temp directory");
        }
        TEMP_DIR.deleteOnExit();

        LOADED_LIBS = new HashSet<>();
    }

    private JniLibraryResourceLoader() {}

    private static void removeOldLibraries(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File childFile : children) {
                    removeOldLibraries(childFile);
                }
            }
        }

        if (!file.delete()) {
            sLogger.log(Level.ERROR, "Could not delete old temp directory");
        }
    }

    public static boolean copyResourceFromJar(String resourceName, File resourceFile) throws IOException {
        return copyResourceFromJar(resourceName, resourceFile, true);
    }

    public static boolean copyResourceFromJar(String resourceName, File resourceFile, boolean deleteOnExit) throws IOException {
        boolean success = false;

        try (InputStream is = JniLibraryResourceLoader.class.getResourceAsStream(resourceName)) {
            if (is == null) {
                sLogger.log(Level.FATAL, "Could not find resource at " + resourceName);
            }
            else {
                if (deleteOnExit) {
                    resourceFile.deleteOnExit();
                }
                OutputStream os = new FileOutputStream(resourceFile);

                byte[] buffer = new byte[1024];
                int readBytes;
                try {
                    while ((readBytes = is.read(buffer)) != -1) {
                        os.write(buffer, 0, readBytes);
                    }

                    success = true;
                }
                finally {
                    os.close();
                    is.close();
                }

                sLogger.log(Level.INFO, "Copied resource to " + resourceFile.getAbsolutePath() + " from resource " + resourceName);
            }
        }

        return success;
    }

    private static boolean createAndLoadTempLibrary(File tempDir, String resourceName) throws IOException {
        String fileName = resourceName.substring(resourceName.lastIndexOf('/') + 1);
        File resourceFile = new File(tempDir, fileName);
        boolean success = false;

        if (copyResourceFromJar(resourceName, resourceFile)) {
            System.load(resourceFile.getAbsolutePath());
            success = true;
        }

        return success;
    }

    private static boolean loadLibrary(File tempDir, String libraryName) {
        if (LOADED_LIBS.contains(libraryName)) {
            sLogger.log(Level.TRACE, "Already loaded " + libraryName);
            return true;
        }

        boolean output = false;
        String resname = RuntimeDetector.getLibraryResource(libraryName);

        try {
            output = createAndLoadTempLibrary(tempDir, resname);
            if (output) {
                LOADED_LIBS.add(libraryName);
            }
        }
        catch (Exception e) {
            sLogger.log(Level.ERROR, e);
        }

        return output;
    }

    public static boolean loadLibrary(String libraryName) {
        // return loadLibrary(TEMP_DIR, libraryName);
        try {
            System.loadLibrary(libraryName);
            return true;
        }
        catch (Exception | UnsatisfiedLinkError ex) {
            sLogger.log(Level.ERROR, ex);
        }
        return false;
    }
}