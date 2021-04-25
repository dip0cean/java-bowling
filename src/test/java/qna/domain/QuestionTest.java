package qna.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qna.CannotDeleteException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    public static Question Q1 = new Question("title1", "contents1").writeBy(UserTest.JAVAJIGI);
    public static Question Q2 = new Question("title2", "contents2").writeBy(UserTest.SANJIGI);
    public static Question Q3 = new Question("title3", "contents3").writeBy(UserTest.SANJIGI);

    @BeforeEach
    public void beforeEach() {
        Q1 = new Question("title1", "contents1").writeBy(UserTest.JAVAJIGI);
        Q2 = new Question("title2", "contents2").writeBy(UserTest.SANJIGI);
        Q3 = new Question("title3", "contents3").writeBy(UserTest.SANJIGI);

        Q1.addAnswer(AnswerTest.A1);
        Q2.addAnswer(AnswerTest.A1);
        Q2.addAnswer(AnswerTest.A1);
    }

    @Test
    public void checkIsOwner_다른사람이_쓴_글() throws Exception {
        // given

        // when

        // then
        assertThatThrownBy(() -> {
            Q1.checkIsOwner(UserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
        assertThatThrownBy(() -> {
            Q2.checkIsOwner(UserTest.JAVAJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    public void deleteQuestion_같은_사람이_쓴_질문() throws Exception {
        // given
        DeleteHistory expectDeleteHistoryQ1 = new DeleteHistory(Q1, UserTest.JAVAJIGI);
        DeleteHistory expectDeleteHistoryQ2 = new DeleteHistory(Q2, UserTest.SANJIGI);
        // when
        DeleteHistory resultDeleteHistoryQ1 = Q1.deleteQuestion(UserTest.JAVAJIGI);
        DeleteHistory resultDeleteHistoryQ2 = Q2.deleteQuestion(UserTest.SANJIGI);
        // then
        assertThat(resultDeleteHistoryQ1).isEqualTo(expectDeleteHistoryQ1);
        assertThat(resultDeleteHistoryQ2).isEqualTo(expectDeleteHistoryQ2);
        assertThat(Q1.isDeleted()).isTrue();
        assertThat(Q2.isDeleted()).isTrue();
    }

    @Test
    public void deleteQuestion_다른_사람이_쓴_질문() throws Exception {
        // given

        // when

        // then
        assertThatThrownBy(() -> {
            Q1.deleteQuestion(UserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
        assertThatThrownBy(() -> {
            Q2.deleteQuestion(UserTest.JAVAJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }


    @Test
    public void deleteQuestionAndAnswers_질문자_답변자_동일함() throws Exception {
        // given
        List<DeleteHistory> expectDeleteHistoriesQ1List = new ArrayList<>();
        expectDeleteHistoriesQ1List.add(new DeleteHistory(Q1, UserTest.JAVAJIGI));
        expectDeleteHistoriesQ1List.add(new DeleteHistory(AnswerTest.A1, UserTest.JAVAJIGI));
        DeleteHistories expectDeleteHistoriesQ1 = new DeleteHistories(expectDeleteHistoriesQ1List);


        // when
        DeleteHistories resultDeleteHistoriesQ1 = Q1.delete(UserTest.JAVAJIGI);

        // then
        assertThat(resultDeleteHistoriesQ1).isEqualTo(expectDeleteHistoriesQ1);
    }

    @Test
    public void deleteQuestionAndAnswers_질문자_답변자_동일하지않음() throws Exception {
        // given

        // when

        // then
        assertThatThrownBy(() -> {
            Q2.delete(UserTest.JAVAJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    public void deleteQuestionAndAnswers_답변_없음() throws Exception {
        // given
        DeleteHistories expectDeleteHistories = new DeleteHistories(Arrays.asList(new DeleteHistory(Q3, UserTest.SANJIGI)));

        // when
        DeleteHistories resultDeleteHistoriesQ3 = Q3.delete(UserTest.SANJIGI);

        // then
        assertThat(resultDeleteHistoriesQ3).isEqualTo(expectDeleteHistories);
    }


}

