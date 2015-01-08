package com.distributedlife.theweekbefore.base.abstractions;

public class StaticDictionaryFilenameStore {
    public static String theFilename;

    public static void setFilename(String filename) {
        theFilename = filename;
    }

    public static String getFilename() {
        return theFilename;
    }
}
