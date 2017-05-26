package com.kmzyc.b2b.model;

import java.io.Serializable;

public class ExpressPathItem implements Comparable<ExpressPathItem>, Serializable {
    private static final long serialVersionUID = 1L;
    private String time;
    private String context;
    private String ftime;
    private String opera;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getOpera() {
        return opera;
    }

    public void setOpera(String opera) {
        this.opera = opera;
    }

    @Override
    public int compareTo(ExpressPathItem o) {
        return time.compareTo(o.getTime());
    }

    @Override
    public String toString() {
        return "ExpressPathItem [time=" + time + ", context=" + context + ", ftime=" + ftime
                + ", opera=" + opera + "]";
    }

}
