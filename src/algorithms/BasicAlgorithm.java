package algorithms;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicAlgorithm implements JankenAlgorithm {

	protected int winCount;
	protected int loseCount;
	protected int drawCount;
	protected List<Integer> myHands;
	protected List<Integer> hisHands;

	// コンストラクタ
	public BasicAlgorithm() {
		this.winCount = 0;
		this.loseCount = 0;
		this.drawCount = 0;
		this.myHands = new ArrayList<Integer>();
		this.hisHands = new ArrayList<Integer>();
	}

	// メンバ変数のセットなどを行うのみで、ロジックは algorithm にまかせる
	public int janken(int round, LastStatus last) {

		if (round > 1) {
			this.myHands.add(last.getMyHand());
			this.hisHands.add(last.getHisHand());

			if (last.isWin()) {
				this.winCount++;
			}
			if (last.isLose()) {
				this.loseCount++;
			}
			if (last.isDraw()) {
				this.drawCount++;
			}
		}
		return algorithm(round, last);
	};

	// 継承先の子クラスでオーバーライドするメソッド
	protected abstract int algorithm(int round, LastStatus last);

}
