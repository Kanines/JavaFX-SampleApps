package com.mycompany.dirlist.javafx.sample.model;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class DiskFile implements Comparable<DiskFile>, Serializable {

    private FileType fileType;
    private String fileName;
    private Date date;
    private File file;
    private Set<DiskFile> filesSet;

    public DiskFile(String path, boolean sorted) {
        this.file = new File(path);
        this.fileName = file.getName();
        this.date = new Date(file.lastModified());

        if (file.isFile()) {
            fileType = FileType.FILE;
        } else {
            fileType = FileType.DIRECTORY;
        }
        if (sorted == true) {
            filesSet = new TreeSet<DiskFile>();
        } else {
            filesSet = new HashSet<DiskFile>();
        }
        if (file.isFile() == false) {
            readDirectory(sorted);
        }
    }

    private void readDirectory(boolean sort) {
        File files[] = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            filesSet.add(new DiskFile(files[i].getAbsolutePath(), sort));
        }

    }

    public int compareTo(DiskFile obj) {
        if (fileType != obj.fileType) {
            if (fileType == FileType.FILE) {
                return 1;
            } else {
                return -1;
            }
        }
        return date.compareTo(obj.date);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        DiskFile porownanie = (DiskFile) obj;
        return (fileName.equals(porownanie.fileName));
    }

    @Override
    public int hashCode() {
        return fileName.hashCode();
    }

    public void print(boolean sorted) {
        String mark;
        if (this.fileType == FileType.DIRECTORY) {
            mark = "Katalog  ";
        } else {
            mark = "Plik     ";
        }

        System.out.printf("%-41s  %1s %16s%n", fileName, mark, formatDate());

        Set<DiskFile> printedSet;
        if (sorted) {
            printedSet = new TreeSet<>(filesSet);
        } else {
            printedSet = filesSet;
        }
        Iterator it = printedSet.iterator();

        while (it.hasNext()) {
            DiskFile file = (DiskFile) it.next();

            file.print(1, sorted);
        }
    }

    public void print(int interline, boolean sorted) {
        Set<DiskFile> printedSet;
        if (sorted) {
            printedSet = new TreeSet<>(filesSet);
        } else {
            printedSet = filesSet;
        }
        Iterator it = printedSet.iterator();

        String mark;
        if (this.fileType == FileType.DIRECTORY) {
            mark = "Katalog  ";
        } else {
            mark = "Plik     ";
        }

        System.out.print("|\n+");
        String fullName = "";

        for (int i = 0; i < interline; i++) {
            fullName += "-";
        }
        fullName += this.fileName;

        System.out.printf("%-40s  %1s %16s%n", fullName, mark, this.formatDate());

        while (it.hasNext()) {
            DiskFile file = (DiskFile) it.next();

            file.print(interline + 1, sorted);
        }
    }

    private String formatDate() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return this.fileName;
    }

    public DiskFile[] getChildren() {
        DiskFile[] x = new DiskFile[1];
        return this.filesSet.toArray(x);
    }

    public FileType getFileType() {
        return this.fileType;
    }

    public void setRoot() {
        this.fileType = FileType.ROOT;
    }

    public File getFile() {
        return this.file;
    }

    public void addChild(DiskFile df) {
        this.filesSet.add(df);
    }
}
