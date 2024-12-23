package pl.pjatk.MATLOG.Domain.Enums;

/**
 * Enum that represents rate of the tutor as stars
 */
public enum Stars {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

    final int rate;

    Stars(int rate) {
        this.rate = rate;
    }
}
