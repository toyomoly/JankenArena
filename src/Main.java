import java.util.ArrayList;
import java.util.List;

import algorithms.Algorithm00Pa;
import algorithms.Algorithm00Sample;
import algorithms.JankenAlgorithm;

public class Main {

	public static void main(String[] args) {

		List<Class<?>> algorithms = new ArrayList<>();

		try {
			// アルゴリズムのリストにエントリー
			algorithms.add(Algorithm00Pa.class);
			algorithms.add(Algorithm00Sample.class);

			start(algorithms);

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	private static void start(List<Class<?>> algorithms) throws Exception {

		int roundMax = 1000;
		String formatParam = "%-16s";

		// ヘッダー部
		System.out.print(String.format(formatParam, "-"));
		for (Class<?> c1 : algorithms) {
			System.out.print(String.format(formatParam, c1.getName().substring(20)));
		}
		System.out.println();

		// ボディ部
		for (Class<?> c1 : algorithms) {
			System.out.print(String.format(formatParam, c1.getName().substring(20)));
			for (Class<?> c2 : algorithms) {
				if (c1 == c2) {
					System.out.print(String.format(formatParam, "-"));
				} else {
					JankenAlgorithm p1 = (JankenAlgorithm) c1.newInstance();
					JankenAlgorithm p2 = (JankenAlgorithm) c2.newInstance();
					System.out.print(String.format(formatParam, match(p1, p2, roundMax)));
				}
			}
			System.out.println();
		}
	}

	private static String match(JankenAlgorithm p1, JankenAlgorithm p2, int roundMax) {

		int lastP1Hand = 0;
		int lastP2Hand = 0;
		boolean lastP1Won = false;
		boolean lastP2Won = false;

		int countP1 = 0;
		int countP2 = 0;
		int countDraw = 0;

		for (int round = 1; round <= roundMax; round++) {
			int hand1 = p1.janken(round, lastP1Hand, lastP2Hand, lastP1Won);
			int hand2 = p2.janken(round, lastP2Hand, lastP1Hand, lastP2Won);

			int result = judge(hand1, hand2);

			lastP1Hand = hand1;
			lastP2Hand = hand2;
			lastP1Won = false;
			lastP2Won = false;

			if (result == 0) {
				countDraw++;
			} else if (result == 1) {
				countP1++;
				lastP1Won = true;
			} else if (result == 2) {
				countP2++;
				lastP2Won = true;
			} else if (result == 3) {
				// nothing
			}
		}

		return countP1 + "-" + countP2 + " (" + countDraw + ")";
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

}
