package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FRIENDS")
@NamedQueries({
        @NamedQuery(name = "Friend.findAll", query = "select f from Friend f"),
        @NamedQuery(name = "Friend.findById", query = "select f from Friend f where f.id = ?1"),
        @NamedQuery(name = "Friend.findByUser1", query = "select f from Friend f where f.user1 = ?1"),
        @NamedQuery(name = "Friend.deleteByUserId", query = "delete from Friend f where f.user1 = ?1 or f.user2 = ?1"),
        @NamedQuery(name = "Friend.findRelationship", query = "select f from Friend f where f.user1 = ?1 and f.user2 = ?2"),
        @NamedQuery(name = "Friend.delete", query = "delete from Friend f where f.id = ?1")
})
public class Friend extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "friends_id")
    @SequenceGenerator(name="friends_id", sequenceName="friends_id", allocationSize=1)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "ID_USER1", nullable = false)
    private int user1;

    @Column(name = "ID_USER2", nullable = false)
    private int user2;

    public Friend() {
    }

    public Friend(int user1, int user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser1() {
        return user1;
    }

    public void setUser1(int user1) {
        this.user1 = user1;
    }

    public int getUser2() {
        return user2;
    }

    public void setUser2(int user2) {
        this.user2 = user2;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", user1='" + user1 + '\'' +
                ", user2='" + user2 + '\'' +
                '}';
    }
}