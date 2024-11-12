package pl.pjatk.MATLOG.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public final class TutorUser extends User {

    private final Set<LocalDateTime> privateLessons = new HashSet<>();

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

    public Set<LocalDateTime> getPrivateLessons() {
        return Set.copyOf(privateLessons);
    }

    private TutorUser(TutorBuilder builder) {
        super(builder);
    }
}
