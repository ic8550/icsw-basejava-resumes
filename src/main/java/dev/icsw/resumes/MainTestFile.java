package dev.icsw.resumes;

import java.io.File;
import java.io.IOException;

public class MainTestFile {
    public static void main(String[] args) {
        printDir("./src", getStringIndent(0), 4);
    }

    public static void printDir(String dirPath, String initialIndent, int indentValue) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File inode : files) {
            if (inode.isFile()) {
                System.out.println(initialIndent + inode.getName());
            } else if (inode.isDirectory()) {
                System.out.println(initialIndent + inode.getName() + "/");
                try {
                    printDir(inode.getCanonicalPath(), initialIndent + getStringIndent(indentValue), indentValue);
                } catch (IOException e) {
                    throw new RuntimeException("Error getting directory's canonical path", e);
                }
            }
        }
    }

    public static String getStringIndent(int indentValue) {
        char[] arrayOfChar = new char[indentValue];
        for (int i = 0; i < indentValue; i++) {
            arrayOfChar[i] = ' ';
        }
        return String.valueOf(arrayOfChar);
    }
}
