package bowling.domain.frame;

import bowling.domain.frame.exception.UnableBowlingException;
import bowling.domain.frame.exception.UnableCreateFrameException;
import bowling.domain.score.Score;
import bowling.domain.state.Ready;
import bowling.domain.state.State;

import java.util.concurrent.atomic.AtomicInteger;

public class FinalFrame implements Frame {

    private static final int LIMIT_COUNT = 2;
    private static final int MINIMUM_COUNT = 0;

    private final int round;
    private final AtomicInteger count;
    private State state;

    private FinalFrame(int round, int pins) {
        this.round = round;
        this.count = new AtomicInteger();
        this.state = Ready.of(pins);
    }

    protected static FinalFrame lastBowling(int round, int pins) {
        return new FinalFrame(round, pins);
    }

    @Override
    public Frame bowling(int pins) {
        if (this.isFinishBowling()) {
            throw new UnableBowlingException();
        }

        count.incrementAndGet();
        if (this.state.isStrike() || this.state.isSpare() || this.state.isBonus()) {
            this.state = this.state.bonusBowling(pins);
            return this;
        }

        this.state = this.state.bowling(pins);

        return this;
    }

    @Override
    public Frame next(int pins) {
        if (!this.isFinishBowling()) {
            return this.bowling(pins);
        }

        throw new UnableCreateFrameException();
    }

    @Override
    public int round() {
        return this.round;
    }

    @Override
    public boolean isFinalFrame() {
        return true;
    }

    @Override
    public boolean isFinishBowling() {
        return this.state.isMiss()
                || this.state.isGutter()
                || (this.count.get() == LIMIT_COUNT && this.state.isBonus());
    }

    @Override
    public boolean isPrinting() {
        return this.isFinishBowling();
    }

    @Override
    public State state() {
        return this.state;
    }

    @Override
    public int score() {
        return this.state.totalScore();
    }

    @Override
    public int nextScore(Score before) {

        if (before.left() == LIMIT_COUNT && this.count.get() == MINIMUM_COUNT) {
            before = before.nextScore(MINIMUM_COUNT);
        }

        Score after = this.state.calculateScore(before);

        if (after.doNotCalculate()) {
            return after.getScore();
        }

        return this.nextScore(after);
    }
}
