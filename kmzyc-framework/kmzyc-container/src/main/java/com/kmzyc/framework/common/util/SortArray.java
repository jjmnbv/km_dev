package com.kmzyc.framework.common.util;

import java.io.Serializable;
import java.util.Comparator;

public class SortArray implements Comparator,Serializable {

    private int keyColumn = 0;
    private int sortOrder = 1;

    public SortArray() {
    }

    public SortArray(int keyColumn) {
        this.keyColumn = keyColumn;
    }

    public SortArray(int keyColumn, int sortOrder) {
        this.keyColumn = keyColumn;
        this.sortOrder = sortOrder;
    }

    public int compare(Object a, Object b) {
        if (a instanceof String[]) {
            return sortOrder * ((String[]) a)[keyColumn].compareTo(((String[]) b)[keyColumn]);
        } else if (a instanceof int[]) {
            return sortOrder * (((int[]) a)[keyColumn] - ((int[]) b)[keyColumn]);
        } else {
            return 0;
        }
    }

}