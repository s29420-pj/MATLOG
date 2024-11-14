package pl.pjatk.MATLOG.domain;

public enum Stars {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

    final int rate;

    Stars(int rate) {
        this.rate = rate;
    }
}
