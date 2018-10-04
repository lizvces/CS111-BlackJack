package blackjack;

public class Player {
	private String playerName;

	private Card[] hand = new Card[20];
	private int cardsInHand = 0;
	private int cardsInSecHand = 0;
	private boolean status = true;
	private Card[] newHand = new Card[20];
	private int newBet = 0;

	/*
	 * creates an array of 20 cards for the hand. 20 was chosen because it is very
	 * unlikely for a person to have more than 20 cards without busting. Therefore,
	 * their hand will not reach more than 20. cardsInHand keeps track of how many
	 * cards are currently in the player's hand.
	 */

	public Player(String playerName) {

		this.playerName = playerName;
		emptyHand();
		// gives a new player a name as well as an empty hand
	}

	public String getName() {
		return this.playerName.toUpperCase();
	}

	public Card[] emptyHand() {
		// set any values that may have been in hand back to 0 so new players always
		// have empty hands
		for (int x = 0; x < hand.length; x++) {
			hand[x] = null;
		}
		cardsInHand = 0;
		return hand;
	}

	public Card[] emptySecHand() {
		// set any values that may have been in hand back to 0 so new players always
		// have empty hands
		for (int x = 0; x < newHand.length; x++) {
			newHand[x] = null;
		}
		cardsInSecHand = 0;
		return newHand;
	}

	public boolean newCard(Card a) {

		// gives the player a new card (card that was dealt).
		// boolean was chosen as return type bcuz if successful, return true, else
		// return false.

		if (cardsInHand == this.hand.length)
			return false;

		// checks if there are too many cards on hand.

		this.hand[cardsInHand] = a;
		cardsInHand++;
		return true;

		/*
		 * if there are open spots, sets the dealt card into the first open spot and
		 * increments the number of cards in the player's hand.
		 */
	}

	public boolean newCardSecHand(Card a) {

		// copy and pasted the same code as newCard. Changed hand to newHand + changed
		// cardsInHand to cardsInSecHand

		if (cardsInSecHand == this.newHand.length)
			return false;

		this.newHand[cardsInSecHand] = a;
		cardsInSecHand++;
		return true;

	}

	public Card[] getHand() {
		return hand;
	}

	public Card getCardinHand(int x) {
		return hand[x];
	}

	public Card getCardSecHand(int x) {
		return newHand[x];
	}

	public int sumOfCards(Card[] hand) {
		/*
		 * sums up all the cards. will be used when deciding value of ACE as well as
		 * when deciding whether to hit or stand
		 */
		int sum = 0;
		for (int x = 0; x < cardsInHand; x++) {
			sum += hand[x].getValue();
		}
		return sum;
		// need to account for ace and face cards
	}

	public int sumOfSecCards(Card[] hand) {
		// same as sumOfCards
		int sum = 0;
		for (int x = 0; x < cardsInSecHand; x++) {
			sum += hand[x].getValue();
		}
		return sum;
	}

	public Card[] getSecHand() {
		return newHand;
	}

	public boolean compareValue(Card[] hand) {
		int sum = 0;
		// checks if the sum of the hand is greater than 21
		for (int x = 0; x < hand.length; x++) {
			sum += hand[x].getValue();
		}
		return (sum <= 21);

	}

	public int getSecBet() {
		return newBet;
	}

	public boolean sameCard(Card a, Card b) {
		if (a.getValue() == b.getValue()) {
			return true;
		} else
			return false;
	}

	public boolean didWin() {
		if (sumOfCards(hand) > 21) {
			return false;
		} else {
			return true;
		}
	}

	public void printHand(boolean dontShowOne) {
		/*
		 * prints hand of players dontShow is represents if the player is the dealer,
		 * one card is hidden.
		 * 
		 */

		for (int d = 0; d < cardsInHand; d++) {
			if (d == 0 && dontShowOne) {
				System.out.println("Dealer's Hand: ");
				System.out.println(" [NOT SHOWN]");
				System.out.println(hand[0].toString());
			} else
				System.out.println(hand[d].toString());
		}

	}

	public void printSecHand() {
		for (int d = 0; d < cardsInSecHand; d++) {
			System.out.println(newHand[d].toString());
		}
	}

	public void isPlaying(int player, boolean done) {
		if (done) {
			status = false;
		}
	}

	public boolean getStatus(int player) {
		return status;
	}

	public void split(Card[] x, int bet) {
		newHand[0] = x[1];
		hand[1] = null;
		newBet = bet;
		cardsInSecHand++;
		cardsInHand--;
	}

	public boolean didSplit() {
		if (newHand[0] != null) {
			return true;
		} else {
			return false;
		}
	}
}
