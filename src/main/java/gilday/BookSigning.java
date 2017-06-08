package gilday;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Book Signing events for an {@link Author}'s {@link Book}
 */
@Entity
public class BookSigning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column
    private String location;

    @ManyToOne
    @JoinColumn(name = "books_id")
    private Book book;

    BookSigning(final Book book, final LocalDateTime dateTime, final String location) {
        this.book = book;
        this.dateTime = dateTime;
        this.location = location;
    }

    /**
     * no-args ctor for JPA
     */
    protected BookSigning() { }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }
}
