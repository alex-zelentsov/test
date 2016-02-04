package ru.notes.model.note;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author azelentsov
 */
@Entity
public class Change {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String type;
    private LocalDateTime date;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Diff> diffs;

    public Change() {
    }

    public Change(String type, LocalDateTime date, List<Diff> diffs) {
        this.type = type;
        this.date = date;
        this.diffs = diffs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Diff> getDiffs() {
        return diffs;
    }

    public void setDiffs(List<Diff> diffs) {
        this.diffs = diffs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Change change = (Change) o;

        if (date != null ? !date.equals(change.date) : change.date != null) return false;
        if (diffs != null ? !diffs.equals(change.diffs) : change.diffs != null) return false;
        if (type != null ? !type.equals(change.type) : change.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (diffs != null ? diffs.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Change{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", diffs=" + diffs +
                '}';
    }
}
