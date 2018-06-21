import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithms.Algorithm00Sample;
import algorithms.Algorithm51Hanako;
import algorithms.Algorithm52Paman;
import algorithms.JankenAlgorithm;
import algorithms.JankenAlgorithm.LastStatus;

public class Main {

	public static void main(String[] args) {

		List<Class<?>> algorithms = new ArrayList<>();

		try {
			// アルゴリズムのリストにエントリー
			algorithms.add(Algorithm00Sample.class);
			algorithms.add(Algorithm51Hanako.class);
			algorithms.add(Algorithm52Paman.class);

			start(algorithms);

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	private static void start(List<Class<?>> algorithms) throws Exception {

		int roundMax = 1000;
		String formatParam = "%-14s";

		Map<String, Score> scores = new HashMap<>();
		// 総当り実施
		for (Class<?> c1 : algorithms) {
			for (Class<?> c2 : algorithms) {
				if (c1 != c2) {
					String key = getScoreKey(c1, c2);

					if (scores.get(key) == null) {
						JankenAlgorithm p1 = (JankenAlgorithm) c1.newInstance();
						JankenAlgorithm p2 = (JankenAlgorithm) c2.newInstance();
						scores.put(key, match(p1, p2, roundMax));
					}
				}
			}
		}

		// ヘッダー部
		System.out.print(String.format(formatParam, ""));
		for (Class<?> c1 : algorithms) {
			System.out.print(String.format(formatParam, c1.getSimpleName().substring(9)));
		}
		System.out.print(String.format(formatParam, "Total"));
		System.out.println();

		// ボディ部
		for (Class<?> c1 : algorithms) {
			StringBuilder row1 = new StringBuilder();
			StringBuilder row2 = new StringBuilder();
			row1.append(String.format(formatParam, c1.getSimpleName().substring(9)));
			row2.append(String.format(formatParam, ""));

			int totalWin = 0;
			int totalLose = 0;
			int totalDraw = 0;

			for (Class<?> c2 : algorithms) {
				if (c1 == c2) {
					row1.append(String.format(formatParam, ""));
					row2.append(String.format(formatParam, ""));
				} else {
					String key = getScoreKey(c1, c2);
					Score score = scores.get(key);
					String w = String.format("%4s", score.getWin(c1.getSimpleName()));
					String l = String.format("%4s", score.getLose(c1.getSimpleName()));
					String d = String.format("%4s", score.getDraw());

					row1.append(String.format(formatParam, w + " -" + l));
					row2.append(String.format(formatParam, "  (" + d + " )"));

					totalWin += score.getWin(c1.getSimpleName());
					totalLose += score.getLose(c1.getSimpleName());
					totalDraw += score.getDraw();
				}
			}

			row1.append(String.format("%5s", totalWin) + " - " + String.format("%5s", totalLose));
			row2.append("   (" + String.format("%5s", totalDraw) + " )");

			System.out.println(row1.toString());
			System.out.println(row2.toString());
		}
	}

	private static String getScoreKey(Class c1, Class c2) {
		String s1 = c1.getSimpleName();
		String s2 = c2.getSimpleName();
		if (s1.compareTo(s2) > 0) {
			return s2 + s1;
		} else {
			return s1 + s2;
		}
	}

	private static Score match(JankenAlgorithm p1, JankenAlgorithm p2, int roundMax) {

		LastStatus lastP1 = new LastStatus(0, 0, false, false, false);
		LastStatus lastP2 = new LastStatus(0, 0, false, false, false);

		int countP1Win = 0;
		int countP1Lose = 0;
		int countP2Win = 0;
		int countP2Lose = 0;
		int countDraw = 0;

		for (int round = 1; round <= roundMax; round++) {
			int hand1 = p1.janken(round, lastP1);
			int hand2 = p2.janken(round, lastP2);

			// System.out.println(hand1 + " - " + hand2);

			lastP1 = getStatus(hand1, hand2);
			lastP2 = getStatus(hand2, hand1);

			if (lastP1.isDraw()) {
				countDraw++;
			}
			if (lastP1.isWin()) {
				countP1Win++;
			} else if (lastP1.isLose()) {
				countP1Lose++;
			}
			if (lastP2.isWin()) {
				countP2Win++;
			} else if (lastP2.isLose()) {
				countP2Lose++;
			}
		}

		return new Score(p1.getClass().getSimpleName(), p2.getClass().getSimpleName(), countP1Win, countP2Win,
				countP1Lose, countP2Lose, countDraw);
	}

	// 0:あいこ, 1:hand1の勝ち, 2:hand1の負け, 3:両方負け
	private static int judge(int hand1, int hand2) {
		if (check(hand1)) {
			if (check(hand2)) {
				// じゃんけん成立
				return (hand2 - hand1 + 3) % 3;
			} else {
				// hand2が不正
				return 1; // hand1の勝ち
			}
		} else {
			if (check(hand2)) {
				// hand1が不正
				return 2; // hand1の負け
			} else {
				// 両方が不正
				return 3; // 両方負け
			}
		}
	}

	// 入力値が正しいか？
	private static boolean check(int hand) {
		return (1 <= hand) && (hand <= 3);
	}

	private static LastStatus getStatus(int myHand, int hisHand) {
		int result = judge(myHand, hisHand);
		boolean win = (result == 1);
		boolean lose = (result > 1);
		boolean draw = (result == 0);
		// 不正な手は0にする
		if (!check(myHand)) {
			myHand = 0;
		}
		if (!check(hisHand)) {
			hisHand = 0;
		}
		return new LastStatus(myHand, hisHand, win, lose, draw);
	}

	private static class Score {
		private String className1;
		private String className2;
		private int win1;
		private int win2;
		private int lose1;
		private int lose2;
		private int draw;

		Score(String className1, String className2, int win1, int win2, int lose1, int lose2, int draw) {
			this.className1 = className1;
			this.className2 = className2;
			this.win1 = win1;
			this.win2 = win2;
			this.lose1 = lose1;
			this.lose2 = lose2;
			this.draw = draw;
		}

		public int getWin(String className) {
			if (className.equals(this.className2)) {
				return win2;
			} else {
				return win1;
			}
		}

		public int getLose(String className) {
			if (className.equals(this.className2)) {
				return lose2;
			} else {
				return lose1;
			}
		}

		public int getDraw() {
			return draw;
		}
	}

}
