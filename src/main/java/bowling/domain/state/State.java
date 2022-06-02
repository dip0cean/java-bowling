package bowling.domain.state;

import bowling.domain.score.Score;
import bowling.domain.state.exception.BonusBowlingException;

public abstract class State {

    private static final int ZERO = 0;

    public abstract State bowling(int pins);

    public abstract String symbol();

    public abstract int totalScore();

    public abstract Score calculateScore(Score before);

    public boolean isReady() {
        return false;
    }

    public boolean isFirstPitch() {
        return false;
    }

    public boolean isGutter() {
        return false;
    }

    public boolean isMiss() {
        return false;
    }

    public boolean isSpare() {
        return false;
    }

    public boolean isStrike() {
        return false;
    }

    public boolean isBonus() {
        return false;
    }

    public boolean isFinishBowling() {
        return false;
    }

    public Score createScore() {
        return new Score(ZERO, this.totalScore());
    }

    public State bonusBowling(int pins) {
        throw new BonusBowlingException();
    }
}
