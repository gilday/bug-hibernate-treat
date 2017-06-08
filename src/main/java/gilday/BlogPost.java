package gilday;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * A BlogPost is published by an {@link Author}
 */

@Entity
@DiscriminatorValue("B")
public class BlogPost extends Publication {

    @Column
    private String url;

    protected BlogPost(final String title, final String url) {
        super(title);
        this.url = url;
    }

    /**
     * no-args ctor for JPA
     */
    protected BlogPost() { }

    public String getURL() {
        return url;
    }
}
