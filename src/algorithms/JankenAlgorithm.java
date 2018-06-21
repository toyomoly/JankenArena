package algorithms;

public interface JankenAlgorithm {

	// ジャンケンの処理
	// 戻り値 1: グー
	//        2: チョキ
	//        3: パー
	public int janken(int round, LastStatus last);

	public class LastStatus {
		private int myHand;
		private int hisHand;
		private boolean win;
		private boolean lose;
		private boolean draw;

		public LastStatus(int myHand, int hisHand, boolean win, boolean lose, boolean draw) {
			this.myHand = myHand;
			this.hisHand = hisHand;
			this.win = win;
			this.lose = lose;
			this.draw = draw;
		}

		public int getMyHand() {
			return myHand;
		}

		public int getHisHand() {
			return hisHand;
		}

		public boolean isWin() {
			return win;
		}

		public boolean isLose() {
			return lose;
		}

		public boolean isDraw() {
			return draw;
		}
	}
}
