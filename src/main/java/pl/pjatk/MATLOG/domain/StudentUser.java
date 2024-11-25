package pl.pjatk.MATLOG.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Concrete class representing Student in application
 */
@Document("student_user")
public final class StudentUser extends User {

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
    static class StudentUserBuilder extends Builder<StudentUserBuilder> {

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
        addAuthority(new SimpleGrantedAuthority("STUDENT_USER"));
    }

}
