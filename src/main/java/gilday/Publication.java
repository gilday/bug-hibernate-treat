package gilday;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Publication is an abstraction over blog posts and books published by authors
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "type")
@Table(name = "publications")
public abstract class Publication {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;
    public String getTitle() { return title; }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authors_id")
    private Author author;
    void setAuthor(final Author author) { this.author = author; }

    Publication(final String title) {
        this.title = title;
    }

    /**
     * no-args ctor for JPA
     */
    protected Publication() { }
}
