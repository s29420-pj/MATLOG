package pl.pjatk.MATLOG.Domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.pjatk.MATLOG.Domain.Enums.SchoolSubject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class that represents Tutor in application. It extends User abstract class.
 * All conditions of User class must be met. If set of private lessons or list of reviews
 * is not specified then the new, empty one is created
 */
public final class TutorUser extends User {

    private String biography;
    private final Set<SchoolSubject> specializations;
    private final Set<Review> reviews;

    /**
     * Method that returns specializationList.
     * @return Copy of set containing specializations.
     */
    public Set<SchoolSubject> getSpecializations() {
        return Set.copyOf(specializations);
    }

    /**
     * Method that returns biography of the Tutor
     * @return biography as String representation.
     */
    public String getBiography() {
        return biography;
    }


    /**
     * Method that adds specialization item to the list of specializations.
     * @param specialization Specialization to add.
     * @return boolean - true if specialization has been added. False otherwise.
     */
    public boolean addSpecializationItem(SchoolSubject specialization) {
        return specializations.add(specialization);
    }

    /**
     * Method that adds all specializations from collection to the specializations set.
     * @param collectionOfSpecializations Collection of specializations of Tutor.
     * @return boolean - true if all items have been added. False otherwise
     */
    public boolean addSpecializationItem(Collection<SchoolSubject> collectionOfSpecializations) {
        return specializations.addAll(collectionOfSpecializations);
    }

    /**
     * Method that removes specialization from the specializations set.
     * @param specialization Specialization that needs to be removed.
     * @return boolean - true if specialization has been successfully removed. False otherwise.
     */
    public boolean removeSpecializationItem(SchoolSubject specialization) {
        return specializations.remove(specialization);
    }

    /**
     * Method that removes all provided specializations from specializations set.
     * @param collectionOfSpecializations Collection of specializations to remove.
     * @return boolean - true if all items have been removed. False otherwise.
     */
    public boolean removeSpecializationItem(Collection<SchoolSubject> collectionOfSpecializations) {
        return specializations.removeAll(collectionOfSpecializations);
    }

    /**
     * Method that changes biography of the Tutor.
     * @param biography Biography to set.
     */
    public void changeBiography(String biography) {
        this.biography = biography;
    }

    public boolean addReview(Review review) {
        return reviews.add(review);
    }

    public boolean removeReview(Review review) {
        return reviews.remove(review);
    }

    public Set<Review> getReviews() {
        return Set.copyOf(reviews);
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
        if (!getAuthorities().contains(new SimpleGrantedAuthority("TUTOR_USER")))
            addAuthority(new SimpleGrantedAuthority("TUTOR_USER"));
        this.biography = Objects.requireNonNullElseGet(builder.biography, String::new);
        this.specializations = Objects.requireNonNullElseGet(builder.specializations, HashSet::new);
        this.reviews = Objects.requireNonNullElseGet(builder.reviews, HashSet::new);
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

        private String biography;
        private Set<SchoolSubject> specializations;
        private Set<Review> reviews;

        /**
         * Method that initializes biography.
         * @param biography Biography of the Tutor
         * @return TutorBuilder
         */
        public TutorBuilder withBiography(String biography) {
            this.biography = biography;
            return self();
        }

        /**
         * Method that initializes specializations set.
         * @param specializations Set containing SchoolSubjects.
         * @return TutorBuilder
         */
        public TutorBuilder withSpecializations(Set<SchoolSubject> specializations) {
            this.specializations = specializations;
            return self();
        }

        public TutorBuilder withReviews(Set<Review> reviews) {
            this.reviews = reviews;
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
