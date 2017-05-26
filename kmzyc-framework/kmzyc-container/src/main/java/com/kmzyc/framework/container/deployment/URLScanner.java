package com.kmzyc.framework.container.deployment;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */
public class URLScanner extends AbstractScanner {
    private static final Log log = LogFactory.getLog(AbstractScanner.class);


    public URLScanner(ClassLoader classLoader, Map<String, ScanHandler> handlers) {
        super(classLoader, handlers);
    }

    public void scanDirectories(Iterable<File> directories) {
        for (File directory : directories) {
            handleDirectory(directory, null);
        }
    }

    public void scanResources(Map<String, ConfigHandler> configs) {
        Set<String> paths = new HashSet<String>();
        String resourceName;
        ConfigHandler conHandler;
        for (Map.Entry<String, ConfigHandler> me : configs.entrySet()) {
            resourceName = me.getKey();
            conHandler = me.getValue();
            try {
                Enumeration<URL> urlEnum = classLoader.getResources(resourceName);
                URL url;
                while (urlEnum.hasMoreElements()) {
                    if (null == (url = urlEnum.nextElement())) continue;
                    if (null != conHandler) conHandler.handle(url);

                    String urlPath = url.getFile();
                    urlPath = URLDecoder.decode(urlPath, "UTF-8");
                    if (urlPath.startsWith("file:")) {
                        urlPath = urlPath.substring(5);
                    }
                    if (urlPath.indexOf('!') > 0) {
                        urlPath = urlPath.substring(0, urlPath.indexOf('!'));
                    } else {
                        File dirOrArchive = new File(urlPath);
                        if (resourceName != null && resourceName.lastIndexOf('/') > 0) {
                            //for META-INF/components.xml
                            dirOrArchive = dirOrArchive.getParentFile();
                        }
                        urlPath = dirOrArchive.getParent();
                    }
                    paths.add(urlPath);
                }
            } catch (IOException ioe) {
                log.warn("Could not read: " + resourceName, ioe);
            }
        }
        handle(paths);
    }

    protected void handle(Set<String> paths) {
        for (String urlPath : paths) {
            try {
                if (log.isDebugEnabled()) log.debug("Scanning: " + urlPath);
                File file = new File(urlPath);
                if (file.isDirectory()) handleDirectory(file, null);
                else handleArchiveByFile(file);
            } catch (IOException ioe) {
                log.warn("could not read entries", ioe);
            }
        }
    }

    private void handleArchiveByFile(File file) throws IOException {
        try {
            if (log.isDebugEnabled()) log.debug("Archive: " + file);
            ZipFile zip = new ZipFile(file);
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                handleItem(name);
            }
        } catch (ZipException e) {
            throw new RuntimeException("Error handling file " + file, e);
        }
    }

    private void handleDirectory(File file, String path) {
        if (log.isDebugEnabled()) log.debug("Directory: " + file);
        for (File child : file.listFiles()) {
            String newPath = path == null ? child.getName() : path + "/" + child.getName();
            if (child.isDirectory()) {
                handleDirectory(child, newPath);
            } else {
                handleItem(newPath);
            }
        }
    }

}
