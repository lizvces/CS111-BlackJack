package blackjack;

public class Wager {

	private int[] bets;
	private int[] current = { 0, 500, 500, 500, 500, 500, 500, 500 };

	public Wager(int players) {
		this.bets = new int[players];
	}

	public boolean checkBet(int player, int bet) {
		if (bet <= 0 || bet > current[player])
			return false;
		else
			return true;
	}

	public void setBet(int player, int bet) {
		bets[player] = bet;
		current[player] = current[player] - bet;
	}

	public int getBet(int player) {
		return bets[player];
	}

	public void setCurr(int player) {
		current[player] = 0;
	}

	public int updateCurr(int player, boolean didWin, boolean didTie, int betAmount) {
		if (didWin) {
			current[player] += 1.5 * betAmount;
			return current[player];
		} else if (didTie) {
			return current[player] + bets[player];
		} else
			return current[player];

	}

	public void printWager(int bet, int player) {
		System.out.println(bets[player]);

	}

	public void printCurr(int player) {
		System.out.println(current[player]);
	}

	public int getCurr(int player) {
		return current[player];

	}

}
