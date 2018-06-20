package algorithms;

public class Algorithm00Sample 
implements JankenAlgorithm {

	public int janken(int round, int lastMyHand, int lastHisHand, boolean lastWon) {

		int result = 0;

		switch (round % 3) {
		case 1:
			result = 1; // グー
			break;
		case 2:
			result = 2; // チョキ
			break;
		default:
			result = 3; // パー
			break;
		}

		return result;
	}
}