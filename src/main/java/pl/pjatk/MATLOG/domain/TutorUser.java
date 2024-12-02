package pl.pjatk.MATLOG.domain;

import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

/**
 * Class that represents Tutor in application. It extends User abstract class.
 * All conditions of User class must be met. If set of private lessons or list of reviews
 * is not specified then the new, empty one is created
 */
public final class TutorUser extends User {

    private final Set<Lesson> lessons;
    private final List<Review> reviews;

    /**
     * Method that returns lessons.
     * @return Copy of set containing lessons.
     */
    public Set<Lesson> getPrivateLessons() {
        return Set.copyOf(lessons);
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
     * @param lesson instantiated lesson that will be added to set
     * @return boolean - true if set doesn't contain specified lesson and was added
     * or false if lesson couldn't be added
     */
    public boolean addLesson(Lesson lesson) {
        return lessons.add(lesson);
    }

    /**
     * Method that removes provided lesson from Set.
     * @param lesson lesson that needs to be removed
     * @return true if lesson was removed, false otherwise.
     */
    public boolean removeLesson(Lesson lesson) {
        return lessons.remove(lesson);
    }

    /**
     * Method that removes selected lessons from Set.
     * @param lesson lessons that need to be removed.
     * @return true if all the lessons were removed, false otherwise.
     */
    public boolean removeSelectedLessons(Lesson ... lesson) {
        List.of(lesson).forEach(lessons::remove);
        return !lessons.containsAll(List.of(lesson));
    }

    /**
     * Private constructor that creates object of TutorUser.
     * It adds authority as TUTOR_USER and sets role to Tutor.
     * If privateLessons or reviews has not been initialized (or are set to null), creates empty collections.
     * Either way initialize collections with set ones in the builder.
     * @param builder - TutorBuilder with set attributes.
     */
    private TutorUser(TutorBuilder builder) {
        super(builder);
        addAuthority(new SimpleGrantedAuthority("TUTOR_USER"));
        this.lessons = Objects.requireNonNullElseGet(builder.lessons, HashSet::new);
        this.reviews = Objects.requireNonNullElseGet(builder.reviews, ArrayList::new);
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

        private Set<Lesson> lessons;
        private List<Review> reviews;

        /**
         * Method that initialize set
         * @param lessons Set with lessons
         * @return TutorBuilder
         */
        public TutorBuilder withPrivateLessons(Set<Lesson> lessons) {
            this.lessons = lessons;
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
            withRole(Role.TUTOR);
            return new TutorUser(this);
        }
    }
}
