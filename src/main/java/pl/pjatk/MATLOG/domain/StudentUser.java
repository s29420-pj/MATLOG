package pl.pjatk.MATLOG.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Concrete class representing Student in application
 */
public final class StudentUser extends User {

    private final Set<Lesson> assignedLessons;

    /**
     * Method that adds lesson to Set containing assignedLessons.
     * @param lesson lesson to be added.
     * @return true if lesson was added, false otherwise.
     */
    public boolean addAssignedLesson(Lesson lesson) {
        return assignedLessons.add(lesson);
    }

    /**
     * Method that removes lesson from Set containing assignedLessons.
     * @param lesson lesson to be removed.
     * @return true if lesson was successfully added, false otherwise.
     */
    public boolean removeAssignedLesson(Lesson lesson) {
        return assignedLessons.remove(lesson);
    }

    /**
     * StudentUser constructor that creates object.
     * It adds authority as STUDENT_USER and sets role to Student.
     * If in builder wasn't provided set with assigned lessons then new hash set is created.
     * @param builder StudentBuilder with set attributes
     */
    private StudentUser(StudentUserBuilder builder) {
        super(builder);
        addAuthority(new SimpleGrantedAuthority("STUDENT_USER"));
        this.assignedLessons = Objects.requireNonNullElseGet(builder.assignedLessons, HashSet::new);
    }

    /**
     * Method that returns builder and starts chaining creation of the object
     * @return StudentUserBuilder
     */
    public static StudentUserBuilder builder() {
        return new StudentUserBuilder();
    }

    /**
     * Concrete implementation of the Builder in User abstract class
     */
    public static class StudentUserBuilder extends Builder<StudentUserBuilder> {

        private Set<Lesson> assignedLessons;

        /**
         * Method that sets Student assignedLessons set.
         * @param assignedLessons lessons that student is assigned to.
         * @return StudentUserBuilder
         */
        public StudentUserBuilder withAssignedLessons(Set<Lesson> assignedLessons) {
            this.assignedLessons = assignedLessons;
            return self();
        }

        @Override
        protected StudentUserBuilder self() {
            return this;
        }

        /**
         * Sets user's role to STUDENT and builds the object.
         * @return StudentUser
         */
        @Override
        public StudentUser build() {
            withRole(Role.STUDENT);
            return new StudentUser(this);
        }
    }
}
