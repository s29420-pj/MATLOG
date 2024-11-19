package pl.pjatk.MATLOG.domain;

import java.util.*;

/**
 * Class that represents Tutor in application. It extends User abstract class.
 * All conditions of User class must be met. If set of private lessons or list of reviews
 * is not specified then the new one is created
 */
public final class TutorUser extends User {

    private final Set<PrivateLesson> privateLessons;
    private final List<Review> reviews;

    /**
     * Concrete representation of the builder in User abstract class.
     * It creates TutorUser with desired attributes.
     */
    static class TutorBuilder extends Builder<TutorBuilder> {

        private Set<PrivateLesson> privateLessons;
        private List<Review> reviews;

        /**
         * Method that initialize set
         * @param privateLessons Set with private lessons
         * @return Builder
         */
        public TutorBuilder withPrivateLessons(Set<PrivateLesson> privateLessons) {
            this.privateLessons = privateLessons;
            return self();
        }

        /**
         * Method that initialize reviews
         * @param reviews List with Tutor reviews
         * @return Builder
         */
        public TutorBuilder withReviews(List<Review> reviews) {
            this.reviews = reviews;
            return self();
        }

        @Override
        TutorBuilder self() {
            return this;
        }

        @Override
        protected TutorUser createUser() {
            return new TutorUser(this);
        }
    }

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
     * Method that adds lesson to set
     * @param privateLesson instantiated private lesson that will be added to set
     * @return boolean - true if set doesn't contain specified private lesson and was added
     * or false if lesson couldn't be added
     */
    public boolean addPrivateLesson(PrivateLesson privateLesson) {
        return privateLessons.add(privateLesson);
    }

    /**
     * Private constructor that creates object of TutorUser.
     * If privateLessons or reviews has not been initialized (or are set to null), creates empty collections.
     * Either way initialize collections with set ones in the builder.
     * @param builder - Builder with set attributes
     */
    private TutorUser(TutorBuilder builder) {
        super(builder);
        this.privateLessons = Objects.requireNonNullElseGet(builder.privateLessons, HashSet::new);
        this.reviews = Objects.requireNonNullElseGet(builder.reviews, ArrayList::new);
    }
}
