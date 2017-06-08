package gilday;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Authors publish blogs and books
 */
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    /**
     * no-args ctor for JPA
     */
    protected Author() { }

    public Author(final String name) {
        this.name = name;
        publications = new LinkedList<>();
    }

    public String getName() { return name; }

    @OneToMany(mappedBy = "author")
    private Collection<Publication> publications;
    public Collection<Publication> getPublications() { return Collections.unmodifiableCollection(publications); }

    public void addPublication(final Publication publication) {
        publication.setAuthor(this);
        publications.add(publication);
    }
}
