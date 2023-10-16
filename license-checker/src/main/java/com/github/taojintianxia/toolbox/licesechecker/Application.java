package com.github.taojintianxia.toolbox.licesechecker;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 */
public class Application {
    public static void main(String[] args) throws IOException {
        Map<String, String> pomMap = readPomMap();
        Map<String, String> licenseMap = readLicense();
        
        for (Map.Entry<String, String> entry : pomMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.contains("-plugin")) {
                continue;
            }
            if (!licenseMap.containsKey(key)) {
                System.out.println("key is not in LICENSE file: " + key);
            } else if (!licenseMap.get(key).equals(value)) {
                System.out.println("key is not equal: " + key);
            }
        }
    }
    
    private static Map<String, String> readPomMap() throws IOException {
        Map<String, String> result = new HashMap<>();
        File pomFile = new File("/Users/nianjun/Work/mission/release-5.4.1/License/pom.xml");
        Files.readLines(pomFile, Charsets.UTF_8).stream().filter(line -> line.contains(".version>")).forEach(line -> {
            String temp = line.substring(line.indexOf("<") + 1, line.indexOf(">"));
            String key = temp.substring(0, temp.indexOf("."));
            temp = line.substring(line.indexOf(">") + 1);
            String value = temp.substring(0, temp.indexOf("<"));
            result.put(key, value);
        });
        return result;
    }
    
    private static Map<String, String> readLicense() throws IOException {
        Map<String, String> result = new HashMap<>();
        File pomFile = new File("/Users/nianjun/Work/mission/release-5.4.1/License/License-5.4.1");
        Files.readLines(pomFile, Charsets.UTF_8).stream().filter(line -> line.contains("https")).forEach(line -> {
            String temp = line.trim().substring(0, line.trim().indexOf(":"));
            if (!temp.contains(" ")) {
                System.out.println(temp);
                return;
            }
            String key = temp.substring(0, temp.indexOf(" "));
            String value = temp.substring(temp.indexOf(" ") + 1);
            result.put(key, value);
        });
        return result;
    }
}
