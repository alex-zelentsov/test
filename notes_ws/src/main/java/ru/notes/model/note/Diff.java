package ru.notes.model.note;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * @author azelentsov
 */
@Entity
public class Diff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String type;
    @Lob
    private String before;
    @Lob
    private String after;

    public Diff() {
    }

    public Diff(String type, String before, String after) {
        this.type = type;
        this.after = after;
        this.before = before;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Diff diff = (Diff) o;

        if (after != null ? !after.equals(diff.after) : diff.after != null) return false;
        if (before != null ? !before.equals(diff.before) : diff.before != null) return false;
        if (type != null ? !type.equals(diff.type) : diff.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (before != null ? before.hashCode() : 0);
        result = 31 * result + (after != null ? after.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Diff{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", before='" + before + '\'' +
                ", after='" + after + '\'' +
                '}';
    }
}
