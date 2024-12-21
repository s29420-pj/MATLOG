package pl.pjatk.MATLOG.Domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.pjatk.MATLOG.Domain.Enums.Role;

import java.util.*;

/**
 * Class that represents Tutor in application. It extends User abstract class.
 * All conditions of User class must be met. If set of private lessons or list of reviews
 * is not specified then the new, empty one is created
 */
public final class TutorUser extends User {

    private final Set<PrivateLesson> privateLessons;
    private final List<Review> reviews;
    private String biography;

    /**
     * Method that returns private lessons
     * @return Copy of set containing private lessons
     */
    public Set<PrivateLesson> getPrivateLessons() {
        return Set.copyOf(privateLessons);
    }

    /**
     * Method that returns reviews
     * @return Copy of list containing reviews from students
     */
    public List<Review> getReviews() {
        return List.copyOf(reviews);
    }

    /**
     * Method that returns biography of the Tutor
     * @return biography as String representation.
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Method that adds lesson to set
     * @param privateLesson instantiated private lesson that will be added to set
     * @return boolean - true if set doesn't contain specified private lesson and was added
     * or false if lesson couldn't be added
     */
    public boolean addPrivateLesson(PrivateLesson privateLesson) {
        return privateLessons.add(privateLesson);
    }

    /**
     * Method that changes biography of the Tutor.
     * @param biography Biography to set.
     */
    public void changeBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Private constructor that creates object of TutorUser.
     * It adds authority as TUTOR_USER and sets role to Tutor.
     * If privateLessons or reviews has not been initialized (or are set to null), creates empty collections.
     * Either way initialize collections with set ones in the builder.
     * @param builder - TutorBuilder with set attributes
     */
    private TutorUser(TutorBuilder builder) {
        super(builder);
        addAuthority(new SimpleGrantedAuthority("TUTOR_USER"));
        this.privateLessons = Objects.requireNonNullElseGet(builder.privateLessons, HashSet::new);
        this.reviews = Objects.requireNonNullElseGet(builder.reviews, ArrayList::new);
        this.biography = Objects.requireNonNullElseGet(builder.biography, String::new);
    }

    /**
     * Method that returns builder and starts chaining creation of the object
     * @return TutorBuilder
     */
    public static TutorBuilder builder() {
        return new TutorBuilder();
    }

    /**
     * Concrete representation of the builder in User abstract class.
     * It creates TutorUser with desired attributes.
     */
    public static class TutorBuilder extends Builder<TutorBuilder> {

        private Set<PrivateLesson> privateLessons;
        private List<Review> reviews;
        private String biography;

        /**
         * Method that initialize set
         * @param privateLessons Set with private lessons
         * @return TutorBuilder
         */
        public TutorBuilder withPrivateLessons(Set<PrivateLesson> privateLessons) {
            this.privateLessons = privateLessons;
            return self();
        }

        /**
         * Method that initialize reviews
         * @param reviews List with Tutor reviews
         * @return TutorBuilder
         */
        public TutorBuilder withReviews(List<Review> reviews) {
            this.reviews = reviews;
            return self();
        }

        /**
         * Method that initializes biography.
         * @param biography Biography of the Tutor
         * @return TutorBuilder
         */
        public TutorBuilder withBiography(String biography) {
            this.biography = biography;
            return self();
        }

        @Override
        protected TutorBuilder self() {
            return this;
        }

        /**
         * Sets user's role to TUTOR and builds the object.
         * @return TutorUser
         */
        @Override
        public TutorUser build() {
            return new TutorUser(this);
        }
    }
}
