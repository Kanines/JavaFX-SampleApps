package com.mycompany.jpa.javafx.sample;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import com.mycompany.jpa.javafx.sample.model.Circle;
import com.mycompany.jpa.javafx.sample.model.Mage;
import com.mycompany.jpa.javafx.sample.model.Spell;

public class FXMLController implements Initializable {

    private EntityManager em;

    @FXML
    private TableColumn mageNameColumn;
    @FXML
    private TableColumn mageCircleColumn;
    @FXML
    private TableColumn mageLevelColumn;
    @FXML
    private TableView<Mage> magesTableView;
    @FXML
    private TableView<Spell> spellsTableView;

    private ObservableList<Mage> mages = FXCollections.observableArrayList();

    ListProperty<Spell> spells = new SimpleListProperty<>();

    private static final Logger log = Logger.getLogger(FXMLController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        em = Persistence.createEntityManagerFactory("JPASamplePU").createEntityManager();
        magesTableView.setItems(mages);
        spellsTableView.itemsProperty().bind(spells);

        List<Mage> dbMages = em.createNamedQuery("Mage.findAll").getResultList();
        mages.addAll(dbMages);

        magesTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Mage>() {
            @Override
            public void changed(ObservableValue<? extends Mage> value, Mage oldValue, Mage newValue) {
                if (newValue != null) {
                    spells.set((ObservableList<Spell>) newValue.getSpells());
                } else {
                    spells.setValue(null);
                }
            }
        });

        magesTableView.setEditable(true);

        mageNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        mageNameColumn.setOnEditCommit(new EventHandler<CellEditEvent<Mage, String>>() {
            @Override
            public void handle(CellEditEvent<Mage, String> t) {
                t.getRowValue().setName(t.getNewValue());
                update(t.getRowValue());
            }
        });

        mageCircleColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(Circle.values()));
        mageCircleColumn.setOnEditCommit(new EventHandler<CellEditEvent<Mage, Circle>>() {
            @Override
            public void handle(CellEditEvent<Mage, Circle> t) {
                t.getRowValue().setCircle(t.getNewValue());
                update(t.getRowValue());
            }
        });

        mageLevelColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        mageLevelColumn.setOnEditCommit(new EventHandler<CellEditEvent<Mage, Integer>>() {
            @Override
            public void handle(CellEditEvent<Mage, Integer> t) {
                t.getRowValue().setLevel(t.getNewValue());
                update(t.getRowValue());
            }
        });

    }

    @FXML
    private void newMageAction(ActionEvent ae) {
        Mage m = new Mage();
        m.setName("NAME");
        m.setLevel(0);
        m.setCircle(Circle.INITIAL);
        persist(m);
        mages.add(m);
    }

    @FXML
    private void deleteMageAction(ActionEvent ae) {
        Mage mage = magesTableView.getSelectionModel().getSelectedItem();
        if (mage != null) {
            mages.remove(mage);
            remove(mage);
            magesTableView.getSelectionModel().clearSelection();
        }
    }

    private void remove(Mage mage) {
        try {
            em.getTransaction().begin();
            em.remove(em.merge(mage));
            em.getTransaction().commit();
        } catch (Exception ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
        }
    }

    private void persist(Mage mage) {
        try {
            em.getTransaction().begin();
            em.persist(mage);
            em.getTransaction().commit();
        } catch (Exception ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
        }
    }

    private void update(Mage mage) {
        try {
            em.getTransaction().begin();
            em.merge(mage);
            em.getTransaction().commit();
        } catch (Exception ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
        }
    }
}
