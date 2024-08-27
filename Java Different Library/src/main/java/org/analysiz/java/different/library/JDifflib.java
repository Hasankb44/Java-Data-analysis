package org.analysiz.java.different.library;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

public class JDifflib implements JDifferent {

    public JDifflib() throws ClassNotFoundException {
        throw new ClassNotFoundException("You can't use constructor!");
    }

    public final Integer n = 3;

    public final Double Cutoff = 0.6;

    public String[] possibilities = null;

    // TODO
    public static Object[] get_close_matches(String word, String[] possibilities, Integer n, Double cutoff) {
        if (cutoff == null || n == null || possibilities == null || word == null) {
            throw new NullPointerException("One or more arguments are null");
        }

        // HashMap to store Levenshtein distance and the corresponding word
        HashMap<Integer, String> map = new HashMap<>();

        // Compute the Levenshtein distance for each possibility
        for (String possibility : possibilities) {
            int distance = levenshtein_distance(word, possibility);
            if (distance <= cutoff * word.length()) {
                map.put(distance, possibility);
            }
        }

        // Sort the entries by distance
        List<Map.Entry<Integer, String>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Map.Entry.comparingByKey());

        // Prepare the result array, ensuring we don't exceed 'n' matches
        Object[] result = new Object[Math.min(n, entries.size())];
        for (int i = 0; i < result.length; i++) {
            result[i] = entries.get(i).getValue();
        }

        return result;
    }


    public static Object[] get_close_matches(String word, String[] possibilities, Double Cutoff) {
        return get_close_matches(word,possibilities,JDifferent.n,Cutoff);
    }


    public static Object[] get_close_matches(String word, String[] possibilities, Integer n) {
        return JDifflib.get_close_matches(word,possibilities,n,JDifferent.Cutoff);
    }


    public static Object[] get_close_matches(String word, String[] possibilities) {
        return get_close_matches(word,possibilities,JDifferent.n,JDifferent.Cutoff);
    }

    public static Integer levenshtein_distance(String s1, String s2) {

        AtomicReference<Integer> reference = new AtomicReference<>(0);

        int lenght1 = s1.length()-1;
        int lenght2 = s2.length()-1;

        char[] b1 = s1.toCharArray();
        char[] b2 = s2.toCharArray();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                AtomicReference<char[]> b1reference = new AtomicReference<>(b1);
                AtomicReference<char[]> b2reference = new AtomicReference<>(b2);

                AtomicReference<Integer> lenght = new AtomicReference<>(
                        Math.min(b1reference.get().length, b2reference.get().length)
                );



                for (int i = 0; i < lenght.get()-1; i++) {
                    if (b1reference.get()[i] != b2reference.get()[i]) {
                        reference.set(reference.get() + 1);
                    }
                }
            }
        };

        if (lenght1 > lenght2) {
            reference.set(reference.get() + lenght1 - lenght2);

            runnable.run();

        } else if (lenght1 == lenght2) {

            runnable.run();

        } else if (lenght1 < lenght2) {
            reference.set(reference.get() + lenght2 - lenght1);

            runnable.run();
        }

        return reference.get();
    }

}
