package me.frankhan2004.test.utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class IOUtil {
    private final ClassLoader classLoader;
    public IOUtil() {
        classLoader = this.getClass().getClassLoader();
    }
    protected final ClassLoader getClassLoader() {
        return classLoader;
    }
    public InputStream getResource(String filename) {
        if (filename == null) throw new IllegalArgumentException("Filename cannot be null");
        try {
            URL url = getClassLoader().getResource(filename);
            if (url == null) return null;
            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException ex) {
            return null;
        }
    }
    public File getResourceFile(String filename) {
        if (filename == null) throw new IllegalArgumentException("Filename cannot be null");
        try {
            URL url = getClassLoader().getResource(filename);
            if (url == null) return null;
            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return new File(connection.getURL().getFile());
        } catch (IOException ex) {
            return null;
        }
    }
    public void saveResource(String resourcePath, boolean replace) {
        if (resourcePath == null || resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = getResource(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found");
        }

        File outFile = new File(resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(resourcePath.substring(0, Math.max(lastIndex, 0)));

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (!outFile.exists() || replace) {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            }
        } catch (IOException ex) {
            System.out.println("Could not save " + outFile.getName() + " to " + outFile);
        }
    }
}
