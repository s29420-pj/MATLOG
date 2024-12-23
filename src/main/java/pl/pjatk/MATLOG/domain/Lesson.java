package pl.pjatk.MATLOG.Domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import pl.pjatk.MATLOG.Domain.Exceptions.LessonExceptions.*;

import java.util.UUID;

/**
 * Abstract class that represents all kind of Lessons in the application.
 * It must be extended by classes that want to be considered as Lessons.
 * Mandatory fields of Lesson are:
 * - id
 * - ownerId
 */
@Getter
public abstract class Lesson {
    @Id
    private final String id;
    private final String ownerId;
    private final String title;
    private final String description;
    private final double price;

    /**
     * Constructor of the Lesson. It calls validateFields method to check if all
     * required fields are set.
     * @param builder Builder of the lesson
     */
    protected Lesson(Builder<?> builder) {
        validateFields(builder);
        this.id = UUID.randomUUID().toString();
        this.ownerId = builder.ownerId;
        this.title = builder.title;
        this.description = builder.description;
        this.price = builder.price;
    }

    /**
     * Method that validate if all required fields are set by builder
     * @param builder Builder passed in to constructor
     * @throws IllegalStateException when ownerId is null
     */
    private void validateFields(Builder<?> builder) {
        if (builder.ownerId == null) throw new IllegalStateException("OwnerId is mandatory and must be set");
    }

    /**
     * Object of Lesson can be created only with Builder.
     * Every concrete class have to implement it.
     * @param <T>
     */
    abstract static class Builder<T extends Builder<T>> {
        private String ownerId;
        private String title;
        private String description;
        private double price;

        /**
         * Method that set's Lesson's owner
         * @param ownerId owner (Tutor) id
         * @return Builder
         * @throws LessonInvalidOwnerId when ownerId is null or empty
         */
        public T withOwnerId(String ownerId) {
            if (ownerId == null || ownerId.isEmpty()) {
                throw new LessonInvalidOwnerId();
            }
            this.ownerId = ownerId;
            return self();
        }

        /**
         * Method that sets Lesson's title
         * @param title Title of the lesson
         * @return Builder
         */
        public T withTitle(String title) {
            this.title = title;
            return self();
        }

        /**
         * Method that sets Lesson's description
         * @param description Description of the lesson
         * @return Builder
         */
        public T withDescription(String description) {
            this.description = description;
            return self();
        }

        /**
         * Method that sets Lesson's price
         * @param price Price of the lesson
         * @return Builder
         * @throws LessonInvalidPriceException when price is below 0
         */
        public T withPrice(double price) {
            if (price < 0) {
                throw new LessonInvalidPriceException();
            }
            this.price = price;
            return self();
        }

        /**
         * Method that return implemented Builder by subclasses
         * @return Builder
         */
        abstract T self();

        /**
         * Method that builds up Lesson object from provided attributes
         * @return Lesson object
         */
        protected abstract Lesson build();
    }
}
