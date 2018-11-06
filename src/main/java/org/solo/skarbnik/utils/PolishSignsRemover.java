package org.solo.skarbnik.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class PolishSignsRemover {

    private final static Map<String, String> mappings = polishSignsMappings();

    private static Map<String, String> polishSignsMappings() {
        Map<String, String> lowercaseMappings = new HashMap<>();
        Map<String, String> uppercaseMappings = new HashMap<>();
        lowercaseMappings.put("ą","a");
        lowercaseMappings.put("ć","c");
        lowercaseMappings.put("ę","e");
        lowercaseMappings.put("ł","l");
        lowercaseMappings.put("ń","n");
        lowercaseMappings.put("ó","o");
        lowercaseMappings.put("ś","s");
        lowercaseMappings.put("ź","z");
        lowercaseMappings.put("ż","z");

        lowercaseMappings.forEach((k,v) -> uppercaseMappings.put(k.toUpperCase(), v.toUpperCase()));

        lowercaseMappings.putAll(uppercaseMappings);
        return lowercaseMappings;
    }


    public static String map(String input){
        String result = input;
        for(Map.Entry<String, String> mapping : mappings.entrySet()){
            result = result.replaceAll(mapping.getKey(), mapping.getValue());
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/marek/git/skarbnik/src/main/resources/users.sql"));
        String line = null;
        do {
            line = bufferedReader.readLine();
            System.out.println(map(line));
        } while (line !=null);
    }
}
