package pl.pjatk.MATLOG.domain;

import java.util.HashSet;
import java.util.Set;

public final class TutorUser extends User {

    private final Set<PrivateLesson> privateLessons = new HashSet<>();

    static class TutorBuilder extends Builder<TutorBuilder> {

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
    }
}
