package com.mycompany.jaxb.javafx.sample.model;

import com.mycompany.jaxb.javafx.sample.DateAdapter;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "subject", propOrder = {"name", "teacher", "subjectType", "ects", "grade", "date", "isConfirmed"})
public class Subject implements Serializable, Comparable<Subject> {

    public String name;
    public String teacher;
    public SubjectType subjectType;
    public byte ects;
    public float grade;
    public Date date;
    public boolean isConfirmed;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @XmlAttribute
    public String getSubjectType() {
        return subjectType.toString();
    }

    public void setSubjectType(String type) {
        switch (type) {
            case "N":
                this.subjectType = SubjectType.N;
                break;
            case "L":
                this.subjectType = SubjectType.L;
                break;
            case "C":
                this.subjectType = SubjectType.C;
                break;
            case "P":
                this.subjectType = SubjectType.P;
                break;
            case "S":
                this.subjectType = SubjectType.S;
                break;
            case "W":
                this.subjectType = SubjectType.W;
                break;
            default:
                this.subjectType = SubjectType.N;
                break;
        }
    }

    @XmlAttribute
    public byte getEcts() {
        return ects;
    }

    public void setEcts(byte ects) {
        this.ects = ects;
    }

    @XmlAttribute
    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    @XmlAttribute
    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getDate() {
        return date;
    }

    public void setDate(Date data) {
        this.date = data;
    }

    @XmlAttribute
    public boolean getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (int) this.grade;
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
        final Subject other = (Subject) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.grade != other.grade) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Subject o) {
        return name.compareTo(o.name);
    }
}
