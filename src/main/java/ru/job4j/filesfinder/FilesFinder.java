package ru.job4j.filesfinder;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FilesFinder {
    private static ArgsName argsName;

    private static String getRegexFromMask(String mask) {
        mask = mask.replace(".", "[.]")
                .replace("*", ".+")
                .replace("?", ".");
        mask = "^%s$".formatted(mask);
        return mask;
    }

    private static void filesFind() throws IOException {
        Predicate<Path> predicate = null;
        List<Path> resultOfSearch = null;
        if ("mask".equals(argsName.get("t"))) {
            Pattern pattern = Pattern.compile(getRegexFromMask(argsName.get("n")));
            predicate = new Predicate<Path>() {
                @Override
                public boolean test(Path path) {
                    return pattern.matcher(path.toFile().getName()).matches();
                }
            };
        }
        if ("name".equals(argsName.get("t"))) {
            String name = argsName.get("n");
            predicate = new Predicate<Path>() {
                @Override
                public boolean test(Path path) {
                    return name.equals(path.toFile().getName());
                }
            };
        }
        if ("regex".equals(argsName.get("t"))) {
            Pattern pattern = Pattern.compile(argsName.get("n"));
            predicate = new Predicate<Path>() {
                @Override
                public boolean test(Path path) {
                    return pattern.matcher(path.toFile().getName()).matches();
                }
            };
        }
        resultOfSearch = Searcher.search(Path.of(argsName.get("d")), predicate);
        if (resultOfSearch.size() > 0) {
            saveDataToFile(resultOfSearch.stream()
                    .map(path -> path.toString())
                    .collect(Collectors.toList()));
        }
    }

    private static void saveDataToFile(List<String> resultOfFinding) {
        if (resultOfFinding != null) {
            try (PrintWriter printWriter = new PrintWriter(new FileWriter(argsName.get("o"), true))) {
                resultOfFinding.forEach(printWriter::println);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            System.out.println("No files found.");
        }
    }

    private static ArgsName getVerifiedParams(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
        argsName = ArgsName.of(args);
        if (argsName.get("d") == null
                || argsName.get("n") == null
                || argsName.get("t") == null
                || argsName.get("o") == null) {
            throw new IllegalArgumentException("Not all arguments are provided");
        }
        String mode = argsName.get("t");
        if (!"name".equals(mode)
                && !"mask".equals(mode)
                && !"regex".equals(mode)) {
            throw new IllegalArgumentException("Invalid search type");
        }
        return argsName;
    }

    private static void printHint() {
        System.out.println("Программа должна запускаться с параметрами: ");
        System.out.println("Ключи \n"
                + "-d - директория, в которой начинать поиск.\n"
                + "-n - имя файла, маска, либо регулярное выражение.\n"
                + "-t - тип поиска: mask искать по маске, name по полному совпадение имени, regex по регулярному выражению.\n"
                + "-o - результат записать в файл.\n");
        System.out.println("Например:  -d=c:  -n=*.?xt -t=mask -o=log.txt");
    }

    public static void main(String[] args) throws IOException {
        printHint();
        argsName = getVerifiedParams(args);
        filesFind();
    }
}
