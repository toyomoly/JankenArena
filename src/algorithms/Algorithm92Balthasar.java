package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Algorithm92Balthasar extends BasicAlgorithm {

	private Map<String, Choice> history = new HashMap<>();
	private List<String> hisStatuses = new ArrayList<>();

	protected int algorithm(int round, LastStatus last) {

		String lastHisStatus = this.getHisStatus(last);
		if (round > 1) {
			this.hisStatuses.add(lastHisStatus);
		}

		if (round < 3) {
			return 3;
		}

		int beforeLastHisHand = 0;
		String beforeLastHisStatus = "";
		int maxWeight = 3;

		if (round > 3) {
			beforeLastHisHand = this.hisHands.get(round - 3);
			beforeLastHisStatus = this.hisStatuses.get(round - 3);
			maxWeight = 6;
		}

		this.setHistoryList(round, maxWeight, last);

		KeyStore store = new KeyStore(last.getHisHand(), lastHisStatus, beforeLastHisHand, beforeLastHisStatus);

		int result = 3;

		for (int weight = 1; weight < maxWeight; weight++) {
			String key = store.getKey(weight);
			int expected = this.getExpectedFromHistory(key);
			if (expected > 0) {
				result = this.getMyHand(expected);
			}
		}

		return result;
	}

	private void setHistoryList(int round, int maxWeight, LastStatus last) {

		int hisHand2 = this.hisHands.get(round - 3);
		String hisStatus2 = this.hisStatuses.get(round - 3);

		int hisHand3 = 0;
		String hisStatus3 = "";

		if (round > 3) {
			hisHand3 = this.hisHands.get(round - 4);
			hisStatus3 = this.hisStatuses.get(round - 4);
		}

		KeyStore store = new KeyStore(hisHand2, hisStatus2, hisHand3, hisStatus3);

		for (int weight = 1; weight < maxWeight; weight++) {
			String key = store.getKey(weight);
			this.setHistory(key, last.getHisHand());
		}
	}

	private int getMyHand(int hisHand) {

		if (hisHand == 2) {
			return 1;
		} else if (hisHand == 3) {
			return 2;
		} else {
			return 3;
		}
	}

	private String getHisStatus(LastStatus last) {

		if (last.isDraw()) {
			return "D";
		} else if (last.isLose()) {
			return "W";
		} else if (last.isWin()) {
			return "L";
		} else {
			return "";
		}
	}

	private void setHistory(String key, int hand) {

		Choice choice;

		if (history.containsKey(key)) {
			choice = history.get(key);
		} else {
			choice = new Choice();
			history.put(key, choice);
		}

		choice.add(hand);
	}

	private int getExpectedFromHistory(String key) {

		if (history.containsKey(key)) {
			return history.get(key).getExpected();
		} else {
			return 0;
		}
	}

	private class KeyStore {

		int lastHand;
		int beforeLastHand;

		String lastStatus;
		String beforeLastStatus;

		KeyStore(int lastHand, String lastStatus, int beforeLastHand, String beforeLastStatus) {
			this.lastHand = lastHand;
			this.lastStatus = lastStatus;
			this.beforeLastHand = beforeLastHand;
			this.beforeLastStatus = beforeLastStatus;
		}

		public String getKey(int weight) {

			switch (weight) {
			case 1:
				return "" + this.lastHand;
			case 2:
				return this.lastHand + this.lastStatus;
			case 3:
				return "" + this.lastHand + this.beforeLastHand;
			case 4:
				return this.lastHand + this.lastStatus + this.beforeLastHand;
			default:
				return this.lastHand + this.lastStatus + this.beforeLastHand + this.beforeLastStatus;
			}
		}
	}

	private class Choice {
		private int count0 = 0;
		private int count1 = 0;
		private int count2 = 0;
		private int count3 = 0;

		public void add(int hand) {
			switch (hand) {
			case 1:
				this.count1++;
				break;
			case 2:
				this.count2++;
				break;
			case 3:
				this.count3++;
				break;
			default:
				this.count0++;
				break;
			}
		}

		public int getExpected() {

			int max = 1;

			if ((count2 > count1) && (count2 > count3)) {
				max = 2;
			}
			if ((count3 > count1) && (count3 > count2)) {
				max = 3;
			}

			return max;
		}
	}
}
