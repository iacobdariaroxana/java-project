package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Users")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "select u from User u"),
        @NamedQuery(name = "User.findByUsername", query = "select u from User u where upper(u.username) = upper(?1)"),
        @NamedQuery(name = "User.findById", query = "select u from User u where u.id = ?1"),
        @NamedQuery(name = "User.updateByUsername", query = "update User set username = ?2 where id = ?1"),
        @NamedQuery(name = "User.delete", query = "delete from User u where u.id = ?1"),
        @NamedQuery(name = "User.deleteByUsername", query = "delete from User u where u.username = ?1")
})
public class User extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_id")
    @SequenceGenerator(name = "user_id", sequenceName = "user_id", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "USERNAME", nullable = false, length = 30)
    private String username;

    @Column(name="PASSWORD", nullable = false, length = 30)
    private String password;

    public User(String username){
        this.username = username;
    }
    public User(String username, String password) {
        this.username = username;
        this.setPassword(password);
    }

    public User(int id, String username, String messages) {
        this.id = id;
        this.username = username;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}