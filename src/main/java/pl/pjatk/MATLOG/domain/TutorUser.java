package pl.pjatk.MATLOG.domain;

import java.util.*;

public final class TutorUser extends User {

    private final Set<PrivateLesson> privateLessons;
    private final List<Review> reviews;

    static class TutorBuilder extends Builder<TutorBuilder> {

        private Set<PrivateLesson> privateLessons;
        private List<Review> reviews;

        public TutorBuilder withPrivateLessons(Set<PrivateLesson> privateLessons) {
            this.privateLessons = privateLessons;
            return self();
        }

        public TutorBuilder withReviews(List<Review> reviews) {
            this.reviews = reviews;
            return self();
        }

        @Override
        TutorBuilder self() {
            return this;
        }

        @Override
        protected TutorUser build() {
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

    public List<Review> getReviews() {
        return List.copyOf(reviews);
    }

    /**
     * Method that adds lesson to set
     * @param privateLesson instantiated private lesson that will be added to set
     * @return boolean - true if set doesn't contain specified private lesson or false if lesson couldn't be added
     */
    public boolean addPrivateLesson(PrivateLesson privateLesson) {
        return privateLessons.add(privateLesson);
    }

    private TutorUser(TutorBuilder builder) {
        super(builder);
        this.privateLessons = Objects.requireNonNullElseGet(builder.privateLessons, HashSet::new);
        this.reviews = Objects.requireNonNullElseGet(builder.reviews, ArrayList::new);
    }
}
