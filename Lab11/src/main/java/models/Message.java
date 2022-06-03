package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MESSAGES")
@NamedQueries({
        @NamedQuery(name = "Message.findAll", query = "select m from Message m"),
        @NamedQuery(name = "Message.findByHashtag",
                query = "select m from Message m where m.message like CONCAT('%', CONCAT(?1, '%')) and (m.fromId = ?2 or m.toId = ?2)"),
        @NamedQuery(name = "Message.findByFromId", query = "select m from Message m where m.fromId = ?1"),
        @NamedQuery(name = "Message.findByToId", query = "select m from Message m where m.toId = ?1"),
        @NamedQuery(name = "Message.findByType", query = "select m from Message m where m.type = ?1"),
        @NamedQuery(name = "Message.findById", query = "select m from Message m where m.id = ?1"),
        @NamedQuery(name = "Message.delete", query = "delete from Message m where m.id = ?1"),
        @NamedQuery(name = "Message.deleteByUserId", query = "delete from Message m where m.fromId = ?1 or m.toId = ?1")
})
public class Message extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "message_id")
    @SequenceGenerator(name = "message_id", sequenceName = "message_id", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "FROM_ID", nullable = false)
    private int fromId;

    @Column(name = "TO_ID", nullable = false)
    private int toId;

    @Column(name = "MESSAGE", nullable = false, length = 300)
    private String message;

    @Column(name = "TYPE", nullable = false)
    private int type;

    public Message() {
    }

    public Message(int fromId, int toId, String message, int type) {
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}