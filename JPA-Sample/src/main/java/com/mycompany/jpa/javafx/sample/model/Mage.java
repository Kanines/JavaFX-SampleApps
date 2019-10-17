package com.mycompany.jpa.javafx.sample.model;

import java.io.Serializable;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "mage")
@NamedQuery(name = "Mage.findAll", query = "select m from Mage m")
public class Mage implements Serializable, Comparable<Mage> {

    private Integer id;
    private StringProperty name = new SimpleStringProperty();
    private int level;
    private Circle circle;

    private ObservableList<Spell> spells = FXCollections.observableArrayList();

    public Mage() {
    }

    public Mage(String name, int level) {
        this.name.set(name);
        this.level = level;
    }

    public Mage(String name, int level, Circle circle) {
        this.name.set(name);
        this.level = level;
        this.circle = circle;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Column
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Column
    @Enumerated(EnumType.STRING)
    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mage")
    public List<Spell> getSpells() {
        return spells;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = FXCollections.observableArrayList(spells);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + this.level;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mage other = (Mage) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.level != other.level) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Mage o) {
        return name.get().compareTo(o.name.get());
    }

    @Override
    public String toString() {
        return "Mage{" + "name=" + name.get() + ", level=" + level + ", circle=" + circle + '}';
    }
}
