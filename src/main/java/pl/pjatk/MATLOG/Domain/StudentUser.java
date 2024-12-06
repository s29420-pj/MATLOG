package pl.pjatk.MATLOG.Domain;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.pjatk.MATLOG.Domain.Enums.Role;

import java.util.Set;

/**
 * Concrete class representing Student in application
 */
public final class StudentUser extends User {

    private final Set<PrivateLesson> privateLessons;

    /**
     * Method that returns private lessons
     * @return Copy of set containing private lessons
     */
    public Set<PrivateLesson> getPrivateLessons() {
        return Set.copyOf(privateLessons);
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

        private Set<PrivateLesson> privateLessons;

        public StudentUserBuilder withPrivateLessons(Set<PrivateLesson> privateLessons) {
            this.privateLessons = privateLessons;
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

    /**
     * StudentUser constructor that creates object.
     * It adds authority as STUDENT_USER and sets role to Student.
     * @param builder StudentBuilder with set attributes
     */
    private StudentUser(StudentUserBuilder builder) {
        super(builder);
        this.privateLessons = builder.privateLessons;
        addAuthority(new SimpleGrantedAuthority("STUDENT_USER"));
    }

}
