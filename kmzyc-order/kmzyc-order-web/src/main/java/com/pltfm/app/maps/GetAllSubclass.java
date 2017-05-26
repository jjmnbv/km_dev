package com.pltfm.app.maps;

import java.io.File;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;

@SuppressWarnings({"unused", "hiding"})
public class GetAllSubclass {

    private List<T> list;

    public static void main(String[] args) {
        File rootFile = new File(GetAllSubclass.class.getResource("/").getFile().replaceFirst("/", ""));

        setSonList(rootFile, rootFile.getPath() + "\\", GetAllSubclass.class);
    }

    public static <T> void setSonList(File rootFile, String parentDirectory, Class<T> parentClass) {
        if (rootFile.isDirectory()) {
            File[] files = rootFile.listFiles();
            if (files != null)
                for (File file : files) {
                    setSonList(file, parentDirectory, parentClass);
                }
        } else {
            String className = null;
            try {
                if (rootFile.getPath().indexOf(".class") != -1) {
                    className =
                            rootFile.getPath().replace(parentDirectory, "").replace(".class", "").replace("\\",
                                    ".");
                    Class<?> classObject = Class.forName(className);
                    classObject.asSubclass(parentClass);
                }
            } catch (ClassNotFoundException e) {
                System.err.println("找不到类 " + className);
            } catch (ClassCastException e) {
                System.err.println(className + " 不是 " + parentClass + " 的子类");
            }
        }
    }
}
