package pl.pjatk.MATLOG.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Concrete class representing Student in application
 */
@Document("student_user")
public final class StudentUser extends User {

    public static class StudentUserBuilder extends Builder<StudentUserBuilder> {

        public StudentUserBuilder(String firstName, String lastName, String emailAddress) {
            super(firstName, lastName, emailAddress);
        }

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
