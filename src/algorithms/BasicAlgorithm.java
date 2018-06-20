package algorithms;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicAlgorithm implements JankenAlgorithm {

	private int wonCount;
	private int loseCount;
	private List<Integer> myHands;
	private List<Integer> hisHands;

	// コンストラクタ
	public BasicAlgorithm() {
		this.wonCount = 0;
		this.loseCount = 0;
		this.myHands = new ArrayList<Integer>();
		this.hisHands = new ArrayList<Integer>();
	}

	// メンバ変数のセットなどを行うのみで、ロジックは algorithm にまかせる
	public int janken(int round, int lastMyHand, int lastHisHand, boolean lastWon) {

		if (round > 1) {
			this.myHands.add(lastMyHand);
			this.hisHands.add(lastHisHand);

			if (lastWon) {
				this.wonCount++;
			} else {
				this.loseCount++;
			}
		}
		return algorithm(round, lastMyHand, lastHisHand, lastWon);
	};

	// 継承先の子クラスでオーバーライドするメソッド
	public abstract int algorithm(int round, int lastMyHand, int lastHisHand, boolean lastWon);

}
