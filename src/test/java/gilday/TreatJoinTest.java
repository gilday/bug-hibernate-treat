package gilday;

import org.junit.Rule;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test exposes a bug with JPA 2.1 Criteria API treat function using Joins
 */
public class TreatJoinTest {

    @Rule
    public final DBResetRule reset = new DBResetRule();

    @Test
    public void treatJoin() {

        final EntityManager em = reset.getEntityManager();
        final Author author = new Author("Johnathan");
        final BlogPost post = new BlogPost("Hibernate Bug", "http://bugs.com/hibernate-treat-bug.html");
        final Book book = new Book("Pizza and You", "ffff-ffff-ffff-ffff");
        final BookSigning signing = book.addSigning(LocalDateTime.now(), "The Strand");
        author.addPublication(post);
        author.addPublication(book);

        em.getTransaction().begin();
        Arrays.asList(author, post, book, signing).forEach(em::persist);
        em.getTransaction().commit();

        final List<SingingLocationAuthorCounts> countsList = countAuthorsBySigningLocation();
        System.out.println(countsList);
        assertThat(countsList.size(), equalTo(1));
        final SingingLocationAuthorCounts counts = countsList.iterator().next();
        assertThat(counts.authors, equalTo(1L));
        assertThat(counts.location, equalTo("The Strand"));
    }

    private List<SingingLocationAuthorCounts> countAuthorsBySigningLocation() {
        final CriteriaBuilder cb = reset.getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<SingingLocationAuthorCounts> query = cb.createQuery(SingingLocationAuthorCounts.class);
        final Root<Author> root = query.from(Author.class);
        final CollectionJoin<Author, Book> booksJoin = cb.treat(root.join(Author_.publications), Book.class);
        final CollectionJoin<Book, BookSigning> signingsJoin = booksJoin.join(Book_.signings);
        query
                .multiselect(signingsJoin.get(BookSigning_.location), cb.countDistinct(root.get(Author_.id)))
                .groupBy(signingsJoin.get(BookSigning_.location));
        return reset.getEntityManager().createQuery(query).getResultList();
    }

    public static class SingingLocationAuthorCounts {
        private final String location;
        private final long authors;

        public SingingLocationAuthorCounts(final String location, final long authors) {
            this.location = location;
            this.authors = authors;
        }

        @Override
        public String toString() {
            return "SingingLocationAuthorCounts{" +
                    "location='" + location + '\'' +
                    ", authors=" + authors +
                    '}';
        }
    }
}
