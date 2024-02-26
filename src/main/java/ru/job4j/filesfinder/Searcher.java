package ru.job4j.filesfinder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class Searcher {
    private static void validateArgs(String[] arg) {
        File file = new File(arg[0]);
        if (!file.exists()) {
            throw new IllegalArgumentException(String.format("Not exist %s", file.getAbsoluteFile()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(String.format("Not directory %s", file.getAbsoluteFile()));
        }
        if (!Character.isLetterOrDigit(arg[1].charAt(0))) {
            throw new IllegalArgumentException(String.format("Invalid file extension \"%s\"", arg[1]));
        }
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        FindVisitor searcher = new FindVisitor(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }
}
