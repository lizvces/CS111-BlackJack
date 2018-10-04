package blackjack;

import java.util.Random;

public class Deck {
	Card[] deck;
	int counter;
	int index;

	public Deck() {
		deck = new Card[52];
		index = 0;
		for (int suit = 0; suit <= 3; suit++) {
			for (int face = 1; face <= 13; face++) {
				this.deck[index] = new Card(suit, face);
				index++;
			}
		}
	}

	public Card deal() {
		int count = 0;
		Card first = deck[0];
		for (int k = 1; k < deck.length; k++) {
			deck[k - 1] = deck[k];
		}
		deck[deck.length - 1] = null;
		index--;
		return first;
	}

	public void printDeck() {
		for (int a = 0; a < deck.length; a++) {
			System.out.println(deck[a].toString());
		}
	}

	public boolean isEmpty() {
		int count = 0;
		for (int x = 0; x < deck.length; x++) {

			if (deck[x] == null)
				count++;
		}

		if (count == deck.length - 1) {
			return true;
		} else {
			return false;
		}

	}

	public void shuffle() {
		// shuffles deck of cards
		Random num = new Random(); // variable that is given a random number

		Card temp;

		int x;

		for (int i = 0; i < deck.length; i++) {

			x = num.nextInt(deck.length);
			// get random card to swap -- nextInt(deck.length) generates random nums from 0
			// to length

			temp = this.deck[i];

			deck[x] = temp;

			deck[i] = temp;

		}

	}

}
