package pl.pjatk.MATLOG.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Concrete class representing Student in application
 */
@Document("student_user")
public final class StudentUser extends User {

    /**
     * Concrete implementation of the Builder in User abstract class
     */
    public static class StudentUserBuilder extends Builder<StudentUserBuilder> {

        @Override
        protected StudentUserBuilder self() {
            return this;
        }

        @Override
        protected User build() {
            return new StudentUser(this);
        }
    }
    private StudentUser(StudentUserBuilder builder) {
        super(builder);
    }

}
