package ru.notes.model.note;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author azelentsov
 */
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    @Lob
    private String content;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Attachment> attachments;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Tag> tags;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Change> changes;

    public Note(String title, String content, List<Tag> tags, List<Attachment> attachments) {
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.attachments = attachments;
    }

    public Note() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Change> getChanges() {
        return changes;
    }

    public void setChanges(List<Change> changes) {
        this.changes = changes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (content != null ? !content.equals(note.content) : note.content != null) return false;
        if (title != null ? !title.equals(note.title) : note.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 31 + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", attachments=" + attachments +
                ", tags=" + tags +
                '}';
    }
}
