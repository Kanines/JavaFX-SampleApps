package com.mycompany.dirlist.javafx.sample;

import com.mycompany.dirlist.javafx.sample.model.DiskFile;
import com.mycompany.dirlist.javafx.sample.model.FileType;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class FXMLController implements Initializable {

    @FXML
    private TreeView<DiskFile> filesTreeView;

    private class FileCell extends TreeCell<DiskFile> {

        @Override
        protected void updateItem(DiskFile file, boolean empty) {
            super.updateItem(file, empty);
            if (file != null) {
                setText(file.getFileName()
                );
                String path = "";

                switch (file.getFileType()) {
                    case ROOT:
                        path = "/images/root.png";
                        break;

                    case DIRECTORY:
                        path = "/images/dir.png";
                        break;

                    case FILE:
                        path = "/images/file.png";
                        break;

                }
                ImageView imgView = new ImageView(new Image(MainApp.class.getResource(path).toExternalForm()));
                setGraphic(imgView);
            } else {
                setText(null);
                setGraphic(null);
            }
        }
    }

    private class FileCellFactory implements Callback<TreeView<DiskFile>, TreeCell<DiskFile>> {

        @Override
        public TreeCell<DiskFile> call(TreeView<DiskFile> p) {
            return new FileCell();
        }
    }

    public void addRecursive(TreeItem<DiskFile> parent) {
        DiskFile current = parent.getValue();
        DiskFile[] children = current.getChildren();

        if(children[0] == null)
            return;
        
        for (int i = 0; i < children.length; i++) {
            TreeItem<DiskFile> ti = new TreeItem<>();
            ti.setValue(children[i]);
            parent.getChildren().add(ti);

            if (children[i].getFileType() == FileType.DIRECTORY) {
                addRecursive(ti);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(null);

        filesTreeView.setCellFactory(new FileCellFactory());
        TreeItem<DiskFile> root = new TreeItem<>();
        DiskFile f = new DiskFile(file.getAbsolutePath(), true);
        f.setRoot();
        root.setValue(f);
        filesTreeView.setRoot(root);
        addRecursive(root);
    }

    @FXML
    private void newCatalogAction(ActionEvent event) {
        TreeItem<DiskFile> selected = filesTreeView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        TreeItem<DiskFile> parent;
        if (selected.getValue().getFileType() == FileType.FILE) {
            parent = selected.getParent();
        } else {
            parent = selected;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(parent.getValue().getFile());
        File newFile = fileChooser.showSaveDialog(null);

        if (newFile == null) {
            return;
        }

        try {
            if (newFile.mkdir() == false) {
                throw new IOException();
            }

            TreeItem<DiskFile> ti = new TreeItem<>();
            DiskFile df = new DiskFile(newFile.getAbsolutePath(), true);
            ti.setValue(df);
            parent.getChildren().add(ti);
            parent.getValue().addChild(df);
        } catch (IOException ex) {
            System.out.println("Nie można utworzyć katalogu");
        }
    }

    private void recursiveDeleteFiles(DiskFile current) {
        if (current.getFileType() == FileType.FILE) {
            current.getFile().delete();
        } else {
            DiskFile[] children = current.getChildren();
            for (int i = 0; i < children.length; i++) {
                recursiveDeleteFiles(children[i]);
            }
        }
    }

    private void recursiveDelete(DiskFile current) throws IOException {
        if (current == null) {
            return;
        }
        DiskFile[] children = current.getChildren();

        if (children != null) {
            for (int i = 0; i < children.length; i++) {
                recursiveDelete(children[i]);
            }
        }
        current.getFile().delete();
    }

    @FXML
    private void deleteAction(ActionEvent event) throws IOException {
        TreeItem<DiskFile> selected = filesTreeView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        if (selected.getValue().getFileType() == FileType.FILE) {
            File f = selected.getValue().getFile();
            f.delete();

        } else {
            recursiveDelete(selected.getValue());
        }
        selected.getParent().getChildren().remove(selected);
    }

    @FXML
    private void newFileAction(ActionEvent event) {

        TreeItem<DiskFile> selected = filesTreeView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        TreeItem<DiskFile> parent;
        if (selected.getValue().getFileType() == FileType.FILE) {
            parent = selected.getParent();
        } else {
            parent = selected;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(parent.getValue().getFile());
        File newFile = fileChooser.showSaveDialog(null);

        if (newFile == null) {
            return;
        }

        try {

            if (newFile.createNewFile() == false) {
                throw new IOException();
            }

            TreeItem<DiskFile> ti = new TreeItem<>();
            DiskFile df = new DiskFile(newFile.getAbsolutePath(), true);
            ti.setValue(df);
            parent.getChildren().add(ti);

            parent.getValue().addChild(df);

        } catch (IOException ex) {
            System.out.println("Nie można utworzyć pliku");
        }
    }
}
