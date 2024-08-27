package org.analysiz.java.different.library;

interface JDifferent {

    public final Integer n = 3;
    public final Double Cutoff = 0.6;
    public String[] possibilities = null;

    static Object get_close_matches(String word, String[] possibilities, Integer n, Double Cutoff) {return null;}

    static Object get_close_matches(String word, String[] possibilities, Double Cutoff) {return null;}

    static Object get_close_matches(String word, String[] possibilities, Integer n) {return null;}

    static Object get_close_matches(String word, String[] possibilities) {return null;}

    static Integer levenshtein_distance(String s1, String s2) {return null;}

}
