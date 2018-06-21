package algorithms;

public class Algorithm51Hanako extends BasicAlgorithm {

	protected int algorithm(int round, LastStatus last) {

		if (this.winCount > this.loseCount) {
			return 3; // 勝ってる時はパー
		} else {
			return 1; // 負けてる時はグー
		}
	}
}
