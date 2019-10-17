package com.mycompany.jaxb.javafx.sample;

import com.mycompany.jaxb.javafx.sample.model.SubjectType;
import com.mycompany.jaxb.javafx.sample.model.Subject;
import com.mycompany.jaxb.javafx.sample.model.ExaminationCard;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.ByteStringConverter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.FloatStringConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class FXMLController implements Initializable {

    @FXML
    private TableView examCardTableView;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn teacher;
    @FXML
    private TableColumn subjectType;
    @FXML
    private TableColumn ects;
    @FXML
    private TableColumn grade;
    @FXML
    private TableColumn date;
    @FXML
    private TableColumn isConfirmed;

    private ObservableList<Subject> subjects;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        subjects = FXCollections.observableArrayList();
        examCardTableView.setItems(subjects);

        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(new EventHandler<CellEditEvent<Subject, String>>() {
            @Override
            public void handle(CellEditEvent<Subject, String> t) {
                Subject p = t.getRowValue();
                String newTitle = t.getNewValue();
                p.name = newTitle;
            }
        });

        teacher.setCellFactory(TextFieldTableCell.forTableColumn());
        teacher.setOnEditCommit(new EventHandler<CellEditEvent<Subject, String>>() {
            @Override
            public void handle(CellEditEvent<Subject, String> t) {
                Subject p = t.getRowValue();
                String newTitle = t.getNewValue();
                p.teacher = newTitle;
            }
        });

        subjectType.setCellValueFactory(new PropertyValueFactory<Subject, SubjectType>("subjectType"));
        subjectType.setCellFactory(TextFieldTableCell.forTableColumn());
        subjectType.setOnEditCommit(new EventHandler<CellEditEvent<Subject, String>>() {
            @Override
            public void handle(CellEditEvent<Subject, String> t) {
                Subject p = t.getRowValue();
                String newTitle = t.getNewValue();
                p.setSubjectType(newTitle);

                examCardTableView.setItems(null);
                examCardTableView.setItems(subjects);
            }
        });

        ects.setCellFactory(TextFieldTableCell.forTableColumn(new ByteStringConverter()));
        ects.setOnEditCommit(new EventHandler<CellEditEvent<Subject, Byte>>() {
            @Override
            public void handle(CellEditEvent<Subject, Byte> t) {
                Subject p = t.getRowValue();
                byte newTitle = t.getNewValue();
                p.ects = newTitle;
            }
        });

        grade.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        grade.setOnEditCommit(new EventHandler<CellEditEvent<Subject, Float>>() {
            @Override
            public void handle(CellEditEvent<Subject, Float> t) {
                Subject p = t.getRowValue();
                float newTitle = t.getNewValue();
                p.grade = newTitle;
            }
        });

        date.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        date.setOnEditCommit(new EventHandler<CellEditEvent<Subject, Date>>() {
            @Override
            public void handle(CellEditEvent<Subject, Date> t) {
                Subject p = t.getRowValue();
                Date newTitle = t.getNewValue();
                p.date = newTitle;
            }
        });

        isConfirmed.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        isConfirmed.setOnEditCommit(new EventHandler<CellEditEvent<Subject, Boolean>>() {
            @Override
            public void handle(CellEditEvent<Subject, Boolean> t) {
                Subject p = t.getRowValue();
                boolean newTitle = t.getNewValue();
                p.setIsConfirmed(newTitle);
            }
        });
    }

    @FXML
    private void loadXmlAction(ActionEvent event) throws JAXBException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml"));
        File file = fileChooser.showOpenDialog(null);

        if (file == null) {
            return;
        }

        JAXBContext context = JAXBContext.newInstance("com.mycompany.jaxb.javafx.sample");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ExaminationCard ke = (ExaminationCard) unmarshaller.unmarshal(file);

        subjects.clear();
        subjects.addAll(ke.getSubjects());

    }

    @FXML
    private void saveXmlAction(ActionEvent event) throws JAXBException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml"));
        File file = fileChooser.showSaveDialog(null);

        if (file == null) {
            return;
        }

        if (!file.getName().contains(".")) {
            file = new File(file.getAbsolutePath() + ".xml");
        }

        ExaminationCard ec = new ExaminationCard();
        ec.getSubjects().addAll(subjects);

        JAXBContext context = JAXBContext.newInstance("com.mycompany.jaxb.javafx.sample");
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(ec, file);
    }

    @FXML
    private void addSubjectAction(ActionEvent event) {
        Subject p = new Subject();
        p.subjectType = com.mycompany.jaxb.javafx.sample.model.SubjectType.N;
        p.isConfirmed = false;
        subjects.add(p);
    }

    @FXML
    private void deleteSubjectAction(ActionEvent event) {
        Object o = examCardTableView.getSelectionModel().getSelectedItem();
        if (o == null) {
            return;
        }
        subjects.remove(o);
    }

    @FXML
    private void loadDefaultContentAction(ActionEvent event) {

        subjects.removeAll(subjects);

        Subject p1 = new Subject();
        p1.subjectType = com.mycompany.jaxb.javafx.sample.model.SubjectType.L;
        p1.name = "Bazy Danych";
        p1.teacher = "Cezary Wtorek";
        p1.ects = 5;
        p1.grade = 4.0f;
        p1.isConfirmed = true;
        p1.date = new Date();
        subjects.add(p1);

        Subject p2 = new Subject();
        p2.subjectType = com.mycompany.jaxb.javafx.sample.model.SubjectType.P;
        p2.name = "Aplikacje usług internetowych";
        p2.teacher = "Mariusz Nowak";
        p2.ects = 4;
        p2.grade = 4.5f;
        p2.isConfirmed = false;
        p2.date = new Date();
        subjects.add(p2);

        Subject p3 = new Subject();
        p3.subjectType = com.mycompany.jaxb.javafx.sample.model.SubjectType.C;
        p3.name = "Multimedia i interfejsy";
        p3.teacher = "Ryszard Kowal";
        p3.ects = 3;
        p3.grade = 5.0f;
        p3.isConfirmed = true;
        p3.date = new Date();
        subjects.add(p3);
    }

}
