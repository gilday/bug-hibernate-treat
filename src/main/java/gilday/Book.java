package gilday;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Books are published by an {@link Author}
 */
@Entity
@DiscriminatorValue("P")
public class Book extends Publication {

    @Column
    private String isbn;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<BookSigning> signings;

    public Book(final String title, final String isbn) {
        super(title);
        this.isbn = isbn;
        this.signings = new LinkedList<>();
    }

    /**
     * no-args ctor for JPA
     */
    protected Book() { }

    public String getISBN() { return isbn; }

    public BookSigning addSigning(final LocalDateTime dateTime, final String location) {
        return new BookSigning(this, dateTime, location);
    }
}
