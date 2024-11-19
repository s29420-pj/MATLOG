package pl.pjatk.MATLOG.domain;

import org.springframework.data.mongodb.core.mapping.Document;

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

        @Override
        public StudentUser build() {
            return new StudentUser(this);
        }
    }
    private StudentUser(StudentUserBuilder builder) {
        super(builder);
    }

}
