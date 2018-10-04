package blackjack;

public class Card {
	// This class represents one playing card.

	// Card suits (provided for your convenience - use is optional)
	public static final int SPADES = 0;
	public static final int HEARTS = 1;
	public static final int CLUBS = 2;
	public static final int DIAMONDS = 3;

	// Card faces (provided for your convenience - use is optional)

	public static final int ACE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;

	// define fields here
	private int suit;
	private int face;

	// This constructor builds a card with the given suit and face, turned face
	// down.
	public Card(int cardSuit, int cardFace) {
		this.suit = cardSuit;
		if (cardFace >= 1 && cardFace <= 13)
			this.face = cardFace;
		else
			System.out.println("Error. " + cardFace + " not valid.");
	}

	// This method retrieves the suit (spades, hearts, etc.) of this card.
	public int getSuit() {
		return suit;
	}

	// This method retrieves the face (ace through king) of this card.
	public int getFace() {
		return face;
	}

	// This method retrieves the numerical value of this card
	// (usually same as card face, except 1 for ace and 10 for jack/queen/king)
	public int getValue() {
		if (face == KING || face == QUEEN || face == JACK) {
			return 10;
		} else if (face == ACE) {
			return 1;
		} else {
			return face;
		}
	}

	public String suitToString(int suit) {
		String st = "";
		switch (suit) {
		case 0:
			st = "Spades";
			break;
		case 1:
			st = "Hearts";
			break;
		case 2:
			st = "Clubs";
			break;
		case 3:
			st = "Diamonds";
			break;
		default:
			System.err.println("Invalid Argument");
		}

		return st;

	}

	public String toString() {
		String str = "";
		switch (this.face) {
		case 2:
			str = "Two";
			break;
		case 3:
			str = "Three";
			break;
		case 4:
			str = "Four";
			break;
		case 5:
			str = "Five";
			break;
		case 6:
			str = "Six";
			break;
		case 7:
			str = "Seven";
			break;
		case 8:
			str = "Eight";
			break;
		case 9:
			str = "Nine";
			break;
		case 10:
			str = "Ten";
			break;
		case 11:
			str = "Jack";
			break;
		case 12:
			str = "Queen";
			break;
		case 13:
			str = "King";
			break;
		case 1:
			str = "Ace";
			break;
		default:
			System.err.println("Invalid Argument");
		}

		return str + " of " + suitToString(suit);

	}
}
