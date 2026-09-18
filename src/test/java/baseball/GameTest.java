package baseball;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class GameTest {

	private Game game;

	@BeforeEach
	void setUp() {
		game = new Game();
	}

	private void generateQuestion(String questionNumber) {
		game.question = questionNumber;
	}

	private void assertIllegalArgument(String guessNumber) {
		assertThatThrownBy(() -> game.guess(guessNumber))
				.isInstanceOf(IllegalArgumentException.class);
	}

	private void assertMatchedNumber(GuessResult result, boolean solved, int strikes, int balls) {
		assertThat(result).isNotNull();
		assertThat(result.isSolved()).isEqualTo(solved);
		assertThat(result.getStrikes()).isEqualTo(strikes);
		assertThat(result.getBalls()).isEqualTo(balls);
	}

	@Test
	public void 입력값이_없을_경우() {
		assertIllegalArgument(null);
	}

	@Test
	public void 입력값_자리수가_세자리가_아닐_경우() {
		assertIllegalArgument("12");
	}

	@Test
	public void 입력값에_숫자_외의_문자가_입력될_경우() {
		assertIllegalArgument("12s");
	}

	@Test
	public void 입력값에_중복된_숫자가_입력될_경우() {
		assertIllegalArgument("121");
	}

	@Test
	public void 숫자_세개가_전부_일치_할_경우_3_strike() {
		generateQuestion("123");
		assertMatchedNumber(game.guess("123"), true, 3, 0);
	}

	@Test
	public void 숫자_세개가_전부_일치_하지_않을_경우_0_strike_0_ball() {
		generateQuestion("123");
		assertMatchedNumber(game.guess("456"), false, 0, 0);
	}

	@Test
	public void 스트라이크만_있을_경우_1_strike_0_ball() {
		generateQuestion("123");
		assertMatchedNumber(game.guess("145"), false, 1, 0);
	}

	@Test
	public void 볼만_있을_경우_0_strike_1_ball() {
		generateQuestion("123");
		assertMatchedNumber(game.guess("304"), false, 0, 1);
	}

	@Test
	public void 볼과_스트라이크가_함께_있을_경우_1_strike_1_ball() {
		generateQuestion("123");
		assertMatchedNumber(game.guess("139"), false, 1, 1);
	}
}