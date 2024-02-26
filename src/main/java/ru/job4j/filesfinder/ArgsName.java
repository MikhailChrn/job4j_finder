package ru.job4j.filesfinder;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException(
                    format("\"This key: '%s' is missing\"", key));
        }
        return values.get(key);
    }

    private String[] spliting(String str) {
        String[] arg = str.split("=", 2);
        if (arg.length < 2) {
            throw new IllegalArgumentException(
                    format("Error: This argument '%s' does not contain an equal sign", str));
        }
        if (!arg[0].startsWith("-") || arg.length != 2) {
            throw new IllegalArgumentException(
                    format("Error: This argument '%s' does not start with a '-' character", str));
        }
        if (arg[0].length() == 1) {
            throw new IllegalArgumentException(
                    format("Error: This argument '%s' does not contain a key", str));
        }
        if (arg[1].length() == 0) {
            throw new IllegalArgumentException(
                    format("Error: This argument '%s' does not contain a value", str));
        }
        return arg;
    }

    private void parse(String[] args) {
        for (String str : args) {
            String[] arg = spliting(str);
            values.put(arg[0].substring(1), arg[1]);
        }
    }

    public static ArgsName of(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }
}