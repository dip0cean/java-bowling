package bowling.domain.pitch;

import static bowling.domain.pitch.PitchResult.GUTTER;
import static bowling.domain.pitch.PitchResult.MISS;
import static bowling.domain.pitch.PitchResult.SPARE;
import static bowling.domain.pitch.PitchResult.STRIKE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("투구 결과")
class PitchResultTest {

    public static Stream<Arguments> firstPitches() {
        return Stream.of(
            Arguments.of(10, STRIKE),
            Arguments.of(5, MISS),
            Arguments.of(5, MISS),
            Arguments.of(0, GUTTER)
        );
    }

    @DisplayName("[성공] 가져오기 - 첫번째")
    @ParameterizedTest
    @MethodSource("firstPitches")
    public void find_first(final int first, final PitchResult expected) {
        // given

        // when
        final PitchResult result = PitchResult.findByPitch(first);

        // then
        assertThat(result).isEqualTo(expected);
    }

    public static Stream<Arguments> secondPitches() {
        return Stream.of(
            Arguments.of(5, 5, SPARE),
            Arguments.of(5, 3, MISS),
            Arguments.of(0, 0, GUTTER),
            Arguments.of(5, 0, GUTTER)
        );
    }

    @DisplayName("[성공] 가져오기 - 두번째")
    @ParameterizedTest
    @MethodSource("secondPitches")
    public void find_second(final int first, final int second, final PitchResult expected) {
        // given

        // when
        final PitchResult result = PitchResult.findByPitch(first, second);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
