package blackjack;

import java.util.Scanner;

public class Blackjack {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		String playAgain = "", endGame = "", splits = "", insuranceDeff;
		;
		int allDone = 0, bet = 0, secBet, insurance = 0;
		boolean playing = false, doubleDown = false, doubleDownFirstHand = false, doubleDownSecHand = false,
				cont = true;
		Wager wager;
		System.out.println("WELCOME TO BLACKJACK! PLEASE ENTER HOW MANY PLAYERS THERE ARE:");
		int player = sc.nextInt();
		wager = new Wager(player + 1);
		Player[] players = new Player[player];
		players[0] = new Player("Dealer");
		if (playAgain != "yes") {

			for (int x = 1; x <= player; x++) {
				System.out.println("WHAT IS PLAYER " + x + "'S NAME?");
				String name = sc.next();
				players[x] = new Player(name);
			}

			do {

				for (int x = 1; x <= player; x++) {

					System.out.println(players[x].getName() + ", YOUR STARTING AMOUNT IS " + wager.getCurr(x));
					System.out.println(players[x].getName() + ", HOW MUCH DO YOU WANT TO BET?");
					bet = sc.nextInt();

					while (wager.checkBet(x, bet) == false) {

						if (wager.getCurr(x) == 0) {
							System.out.println("SORRY YOU HAVE NO MORE MONEY TO BET. YOU CANNOT PLAY");
							players[x].isPlaying(x, true);
							break;
						} else {
							System.out.println("ERROR PLEASE ENTER A NUMBER GREATER THAN 0 AND LESS THAN OR EQUAL TO "
									+ wager.getCurr(x));
							System.out.println(players[x].getName() + ", HOW MUCH DO YOU WANT TO BET?");
							bet = sc.nextInt();
						}
					}
					wager.setBet(x, bet);
					System.out.println();
					if (wager.getBet(x) == 0 && wager.getCurr(x) == 0) {
						System.out.println("SORRY YOU CANNOT PLAY! NO MONEY IN BANKROLL!");
						players[x].isPlaying(x, true);
						allDone++;

					}
				}

				Deck gameDeck = new Deck();
				gameDeck.shuffle();

				players[0].newCard(gameDeck.deal());
				players[0].printHand(true);
				System.out.println();

				if (players[0].getCardinHand(0).getValue() == 1) {

					for (int x = 1; x <= player; x++) {

						System.out.println("DEALER HAS BEEN DEALT AN ACE." + players[x].getName()
								+ ", WOULD YOU LIKE TO PLACE INSURANCE?");
						insuranceDeff = sc.next();
						while (!(insuranceDeff.equalsIgnoreCase("no")) && !(insuranceDeff.equalsIgnoreCase("yes"))) {
							System.out.println("ERROR! PLEASE ENTER YES OR NO!");
							insuranceDeff = sc.next();
						}
						if (insuranceDeff.equalsIgnoreCase("yes")) {

							insurance = wager.getBet(x) / 2;

						}

					}

					players[0].newCard(gameDeck.deal());

					if (players[0].getCardinHand(1).getValue() == 10) {
						System.out
								.println("SORRY! DEALER HAS BLACKJACK. YOU WILL LOSE YOUR BET BUT GET YOUR INSURANCE.");

						for (int x = 1; x < players.length; x++) {

							wager.updateCurr(x, false, false, bet);
							wager.updateCurr(x, true, false, insurance * 2);
							cont = false;

						}
					}

				}
				if (cont == true) {
					for (int x = 1; x <= player; x++) {

						players[x].newCard(gameDeck.deal());
						players[x].newCard(gameDeck.deal());
						System.out.println(players[x].getName() + ", YOUR NEW HAND IS: ");
						players[x].printHand(false);

						System.out.println("WOULD YOU LIKE A HINT?");
						String hint = sc.next();

						if (hint.equalsIgnoreCase("yes"))
							System.out.println(HintSystem.hint(players[x].getCardinHand(0).getValue(),
									players[x].getCardinHand(1).getValue(), players[0].getCardinHand(0).getValue(),
									players[x].sumOfCards(players[x].getHand())));

						if (players[x].getCardinHand(0).getValue() == players[x].getCardinHand(1).getValue()) {

							System.out.println("YOU HAVE TWO CARDS WITH THE SAME VALUE. WOULD YOU LIKE TO SPLIT?");
							splits = sc.next();

							if (splits.equalsIgnoreCase("yes")) {

								players[x].split(players[x].getHand(), wager.getBet(x));

							}

						}

					}

					for (int x = 1; x <= player; x++) {

						if (players[x].getStatus(x) == true) {

							if (players[x].didSplit()) {

								System.out.println(players[x].getName() + ", YOUR FIRST HAND IS: ");
								players[x].printHand(false);
								System.out.println("SUM OF HAND: " + players[x].sumOfCards(players[x].getHand()));
								System.out.println();
								System.out.println(players[x].getName() + ", YOUR SECOND HAND IS: ");
								players[x].printSecHand();
								System.out.println("SUM OF HAND: " + players[x].sumOfSecCards(players[x].getSecHand()));

								System.out.println(players[x].getName()
										+ ", WOULD YOU LIKE TO DOUBLE DOWN YOUR FIRST BET? YES OR NO?");
								String ddown = sc.next();

								// note to Liz: make sure to add a while loop to check if someone enters
								// something that is neither yes nor no

								if (ddown.equalsIgnoreCase("yes")) {
									System.out.println("YOUR FIRST BET WILL NOW INCREASE TO 100%");
									bet = wager.getCurr(x) + wager.getBet(x);
									doubleDownFirstHand = true;
								} else if (ddown.equalsIgnoreCase("no")) {
									doubleDownFirstHand = false;
								}

								System.out.println(players[x].getName()
										+ ", WOULD YOU LIKE TO DOUBLE DOWN YOUR SECOND BET? YES OR NO?");
								ddown = sc.next();

								if (ddown.equalsIgnoreCase("yes")) {

									System.out.println("YOUR SECOND BET WILL NOW INCREASE TO 100%");
									secBet = wager.getCurr(x) + players[x].getSecBet();
									doubleDownSecHand = true;
								} else if (ddown.equalsIgnoreCase("no")) {
									doubleDownSecHand = false;
								}

							} else {

								System.out.println(players[x].getName() + ", YOUR HAND IS: ");
								players[x].printHand(false);
								System.out.println("SUM OF HAND: " + players[x].sumOfCards(players[x].getHand()));

								System.out
										.println(players[x].getName() + ", WOULD YOU LIKE TO DOUBLE DOWN? YES OR NO?");
								String ddown = sc.next();

								if (ddown.equalsIgnoreCase("yes")) {
									System.out.println("YOUR BET WILL NOW INCREASE TO 100%"); // fix ddown
									bet = wager.getCurr(x) + wager.getBet(x);
									doubleDown = true;
									// note to Liz: after double down, you can't ask to hit or stand--automatically
									// stand.
								} else if (ddown.equalsIgnoreCase("no")) {
									doubleDown = false;
								}
							}
							if (players[x].didSplit()) {

								if (doubleDownFirstHand == true) {

									System.out.println(
											"YOU DOUBLED DOWN ON YOUR FIRST HAND. HERE IS YOUR FINAL CARD AND HAND.");
									players[x].newCard(gameDeck.deal());
									System.out.println();
									System.out.println("FINAL HAND: ");
									players[x].printHand(false);
									System.out.println("SUM OF CARDS: " + players[x].sumOfCards(players[x].getHand()));
									System.out.println();

								}
								if (doubleDownFirstHand == false) {

									System.out.println();
									System.out.println("WOULD YOU LIKE TO HIT OR STAND ON YOUR FIRST HAND?");
									String choice = sc.next();

									while (choice.equalsIgnoreCase("hit")) {
										System.out.println();
										System.out.println(players[x].getName() + ", YOUR NEW HAND IS: ");
										players[x].newCard(gameDeck.deal());
										players[x].printHand(false);
										System.out.println("HAND = " + players[x].sumOfCards(players[x].getHand()));
										if (players[x].sumOfCards(players[x].getHand()) > 21) {
											System.out.println();
											System.out.println("SORRY! YOU BUSTED ON YOUR FIRST HAND! FINAL HAND: ");
											System.out.println();
											players[x].printHand(false);
											System.out.println(
													"SUM OF CARDS: " + players[x].sumOfCards(players[x].getHand()));
											System.out.println();
											break;
										} else {
											System.out.println("WOULD YOU LIKE A HINT?");
											String hint = sc.next();

											if (hint.equalsIgnoreCase("yes"))
												System.out
														.println(HintSystem.hint(players[x].getCardinHand(0).getValue(),
																players[x].getCardinHand(1).getValue(),
																players[0].getCardinHand(0).getValue(),
																players[x].sumOfCards(players[x].getHand())));

											System.out.println("WOULD YOU LIKE TO HIT OR STAND ON YOUR FIRST HAND? ");
											choice = sc.next();

											while (!(choice.equalsIgnoreCase("hit"))
													&& !(choice.equalsIgnoreCase("stand"))) {
												System.out.println("ERROR! PLEASE ENTER EITHER HIT OR STAND");
												choice = sc.next();
											}
										}

									}

									while (choice.equalsIgnoreCase("stand")) {
										System.out.println();
										System.out.println("FINAL HAND: ");
										players[x].printHand(false);
										System.out.println(
												"SUM OF CARDS: " + players[x].sumOfCards(players[x].getHand()));
										System.out.println();
										break;
									}
								}

								// check why it skips this whole section

								if (doubleDownSecHand == true) {
									System.out.println(
											"YOU DOUBLED DOWN ON YOUR SECOND HAND. HERE IS YOUR FINAL CARD AND HAND.");
									players[x].newCardSecHand(gameDeck.deal());
									System.out.println();
									System.out.println("FINAL HAND: ");
									players[x].printHand(false);
									System.out.println(
											"SUM OF CARDS: " + players[x].sumOfSecCards(players[x].getSecHand()));
									System.out.println();

								} else {

									System.out.println("WOULD YOU LIKE TO HIT OR STAND ON YOUR SECOND HAND?");
									String choice = sc.next();

									while (choice.equalsIgnoreCase("hit")) {
										System.out.println();
										System.out.println(players[x].getName() + ", YOUR NEW HAND IS");
										players[x].newCardSecHand(gameDeck.deal());
										players[x].printSecHand();
										System.out.println("SUM OF SECOND HAND= "
												+ players[x].sumOfSecCards(players[x].getSecHand()));

										if (players[x].sumOfSecCards(players[x].getSecHand()) > 21) {
											System.out.println();
											System.out.println("SORRY! YOU BUSTED ON YOUR SECOND HAND! FINAL HAND: ");
											System.out.println();
											players[x].printSecHand();
											System.out.println("SUM OF CARDS: "
													+ players[x].sumOfSecCards(players[x].getSecHand()));
											System.out.println();
											break;

										} else {
											System.out.println("WOULD YOU LIKE A HINT?");
											String hint = sc.next();

											if (hint.equalsIgnoreCase("yes"))
												System.out.println(
														HintSystem.hint(players[x].getCardSecHand(0).getValue(),
																players[x].getCardSecHand(1).getValue(),
																players[0].getCardinHand(0).getValue(),
																players[x].sumOfSecCards(players[x].getSecHand())));
											System.out.println();
											System.out.println("WOULD YOU LIKE TO HIT OR STAND ON YOUR SECOND HAND?");
											choice = sc.next();
										}
									}

									while (choice.equalsIgnoreCase("stand")) {
										System.out.println();
										System.out.println("FINAL HAND: ");
										players[x].printSecHand();
										System.out.println(
												"SUM OF CARDS: " + players[x].sumOfSecCards(players[x].getSecHand()));
										System.out.println();
										System.out.println();
										break;
									}

									while (!(choice.equalsIgnoreCase("hit")) && !(choice.equalsIgnoreCase("stand"))) {
										System.out.println("ERROR! ERROR PLEASE ENTER EITHER HIT OR STAND");
										choice = sc.next();
									}

								}

							} else {

								if (doubleDown == true) {
									System.out.println("YOU DOUBLED DOWN. HERE IS YOUR FINAL CARD AND HAND.");
									players[x].newCard(gameDeck.deal());
									System.out.println();
									System.out.println("FINAL HAND: ");
									players[x].printHand(false);
									System.out.println("SUM OF CARDS: " + players[x].sumOfCards(players[x].getHand()));
									System.out.println();

								} else if (doubleDown == false) {
									System.out.println("WOULD YOU LIKE A HINT?");
									String hint = sc.next();

									if (hint.equalsIgnoreCase("yes"))
										System.out.println(HintSystem.hint(players[x].getCardinHand(0).getValue(),
												players[x].getCardinHand(1).getValue(),
												players[0].getCardinHand(0).getValue(),
												players[x].sumOfCards(players[x].getHand())));

									System.out.println();
									System.out.println(players[x].getName() + ", WOULD YOU LIKE TO HIT OR STAND?");
									String choice = sc.next();

									while (choice.equalsIgnoreCase("hit")) {
										System.out.println();
										System.out.println(players[x].getName() + ", YOUR NEW HAND IS: ");
										players[x].newCard(gameDeck.deal());
										players[x].printHand(false);
										System.out.println("Hand = " + players[x].sumOfCards(players[x].getHand()));
										if (players[x].sumOfCards(players[x].getHand()) > 21) {
											System.out.println();
											System.out.println("SORRY! YOU BUSTED! YOUR FINAL HAND IS: ");
											System.out.println();
											players[x].printHand(false);
											System.out.println(
													"SUM OF CARDS: " + players[x].sumOfCards(players[x].getHand()));
											System.out.println();
											break;

										} else {

											System.out.println("WOULD YOU LIKE A HINT?");
											hint = sc.next();

											if (hint.equalsIgnoreCase("yes"))
												System.out
														.println(HintSystem.hint(players[x].getCardinHand(0).getValue(),
																players[x].getCardinHand(1).getValue(),
																players[0].getCardinHand(0).getValue(),
																players[x].sumOfCards(players[x].getHand())));
											System.out.println();
											System.out.println(
													players[x].getName() + ", WOULD YOU LIKE TO HIT OR STAND?");
											choice = sc.next();

										}
									}

									while (choice.equalsIgnoreCase("stand")) {

										System.out.println();
										System.out.println("FINAL HAND: ");
										players[x].printHand(false);
										System.out.println(
												"SUM OF CARDS: " + players[x].sumOfCards(players[x].getHand()));
										System.out.println();
										break;

									}
									while (!(choice.equalsIgnoreCase("hit")) && !(choice.equalsIgnoreCase("stand"))) {

										System.out.println("ERROR! PLEASE ENTER EITHER HIT OR STAND:");
										choice = sc.next();
									}

								}
							}

						}

						else {
							continue;
						}
					}

					// revealing dealer's hand

					System.out.println("DEALER'S HAND IS: ");
					players[0].newCard(gameDeck.deal());
					players[0].printHand(false);

					while (players[0].sumOfCards(players[0].getHand()) < 17) {

						System.out.println();
						System.out.println("DEALER'S HAND IS NOT GREATER THAN 17.");
						System.out.println("NEW CARD HAS BEEN DEALT");
						players[0].newCard(gameDeck.deal());

					}
					System.out.println();
					System.out.println("DEALER'S FINAL HAND: ");
					players[0].printHand(false);
					System.out.println("SUM OF DEALER'S CARDS: ");
					System.out.println(players[0].sumOfCards(players[0].getHand()));

					for (int x = 1; x <= player; x++) {

						if (players[x].getStatus(x)) {

							if (players[x].didSplit()) {

								System.out.println();
								if (players[x].sumOfCards(players[x].getHand()) <= 21
										&& players[0].sumOfCards(players[0].getHand()) > 21) {
									System.out.println("CONGRATULATIONS " + players[x].getName()
											+ " YOU HAVE WON ON YOUR FIRST HAND! YOU'RE NEW BALANCE IS: ");
									System.out.print("$");
									if (doubleDownFirstHand == true) {
										wager.updateCurr(x, true, false, wager.getBet(x) * 2);
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, true, false, wager.getBet(x));
										wager.printCurr(x);
									}
								} else if (players[x].sumOfCards(players[x].getHand()) > 21
										&& (players[0].sumOfCards(players[0].getHand()) <= 21
												|| players[0].sumOfCards(players[0].getHand()) > 21)) {
									System.out.println("SORRY, " + players[x].getName()
											+ ", YOU HAVE LOST ON YOUR FIRST HAND. YOUR NEW BALANCE IS: ");
									System.out.print("$");
									if (doubleDownFirstHand == true) {
										wager.updateCurr(x, true, false, wager.getBet(x) * 2);
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, true, false, wager.getBet(x));
										wager.printCurr(x);
									}
								} else if (players[x].sumOfCards(players[x].getHand()) > players[0]
										.sumOfCards(players[0].getHand())) {
									System.out.println("CONGRATULATIONS, " + players[x].getName()
											+ ", YOU HAVE WON ON YOUR FIRST HAND! YOUR NEW BALANCE IS: ");
									System.out.print("$");
									if (doubleDownFirstHand == true) {
										wager.updateCurr(x, true, false, wager.getBet(x) * 2);
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, true, false, wager.getBet(x));
										wager.printCurr(x);
									}
								} else if (players[x].sumOfCards(players[x].getHand()) < players[0]
										.sumOfCards(players[0].getHand())) {
									System.out.println("SORRY, " + players[x].getName()
											+ ", YOU HAVE LOST ON YOUR FIRST HAND. YOUR NEW BALANCE IS: ");
									System.out.print("$");
									if (doubleDownFirstHand == true) {
										wager.updateCurr(x, false, false, wager.getBet(x) * 2);
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, false, false, wager.getBet(x));
										wager.printCurr(x);
									}
								} else if (players[x].sumOfCards(players[x].getHand()) > 21
										&& players[0].sumOfCards(players[0].getHand()) > 21) {
									System.out.println("SORRY, " + players[x].getName()
											+ ", YOU HAVE LOST ON YOUR FIRST HAND. YOUR NEW BALANCE IS: ");
									System.out.print("$");
									if (doubleDownFirstHand == true) {
										wager.updateCurr(x, false, false, wager.getBet(x) * 2);
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, false, false, wager.getBet(x));
										wager.printCurr(x);
									}
									// note to liz: google what happens when both dealer and player bust.
								} else {

									System.out.println(players[x].getName()
											+ ", YOU AND THE DEALER HAVE TIED (PUSHED) ON YOUR FIRST HAND. YOUR NEW BALANCE IS: ");
									System.out.print("$");
									if (doubleDownFirstHand == true) {
										wager.updateCurr(x, false, true, wager.getBet(x) * 2);
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, false, true, wager.getBet(x));
										wager.printCurr(x);
									}
									;
								}

								if (players[x].sumOfSecCards(players[x].getSecHand()) <= 21
										&& players[0].sumOfCards(players[0].getHand()) > 21) {
									System.out.println("CONGRATULATIONS " + players[x].getName()
											+ "! YOU HAVE WON ON YOUR SECOND HAND! YOUR NEW BALANCE IS:");
									if (doubleDownSecHand == true) {
										wager.updateCurr(x, true, false, players[x].getSecBet() * 2);
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, true, false, players[x].getSecBet());
										wager.printCurr(x);
									}
								} else if (players[x].sumOfSecCards(players[x].getSecHand()) > 21
										&& players[0].sumOfCards(players[0].getHand()) <= 21) {
									System.out.println("SORRY " + players[x].getName()
											+ ", YOU HAVE LOST ON YOUR SECOND HAND! YOUR NEW BALANCE IS: ");
									if (doubleDownSecHand == true) {
										wager.updateCurr(x, false, false, players[x].getSecBet());
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, false, false, players[x].getSecBet());
										wager.printCurr(x);
									}

								} else if (players[x].sumOfSecCards(players[x].getSecHand()) > players[0]
										.sumOfCards(players[0].getHand())) {
									System.out.println("CONGRATULATIONS " + players[x].getName()
											+ "! YOU HAVE WON ON YOUR SECOND HAND!YOUR NEW BALANCE IS: ");
									if (doubleDownSecHand == true) {
										wager.updateCurr(x, true, false, players[x].getSecBet());
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, true, false, players[x].getSecBet());
										wager.printCurr(x);
									}
								} else if (players[x].sumOfSecCards(players[x].getSecHand()) < players[0]
										.sumOfCards(players[0].getHand())) {
									System.out.println("SORRY " + players[x].getName()
											+ ", YOU HAVE LOST ON YOUR SECOND HAND. YOUR NEW BALANCE IS: ");
									if (doubleDownSecHand == true) {
										wager.updateCurr(x, false, false, players[x].getSecBet());
										wager.printCurr(x);
									}
								} else {
									System.out.println(players[x].getName()
											+ ", YOU AND THE DEALER HAVE TIED (PUSHED) ON YOUR SECOND HAND. YOU NEW BALANCE IS: ");
									if (doubleDownSecHand == true) {
										wager.updateCurr(x, false, true, players[x].getSecBet());
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, false, true, players[x].getSecBet());
										wager.printCurr(x);
									}
								}

								System.out.println();

							} else {

								System.out.println();
								if (players[x].sumOfCards(players[x].getHand()) <= 21
										&& players[0].sumOfCards(players[0].getHand()) > 21) {
									System.out.println("CONGRATULATIONS " + players[x].getName()
											+ " YOU HAVE WON! YOU'RE NEW BALANCE IS: ");
									System.out.print("$");
									if (doubleDown == true) {
										wager.updateCurr(x, true, false, wager.getBet(x));
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, true, false, wager.getBet(x));
										wager.printCurr(x);
									}
								} else if (players[x].sumOfCards(players[x].getHand()) > 21
										&& (players[0].sumOfCards(players[0].getHand()) <= 21
												|| players[0].sumOfCards(players[0].getHand()) > 21)) {
									System.out.println("SORRY, " + players[x].getName()
											+ ", YOU HAVE LOST. YOUR NEW BALANCE IS: ");
									System.out.print("$");
									if (doubleDown == true) {
										wager.updateCurr(x, false, false, wager.getBet(x));
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, false, false, wager.getBet(x));
										wager.printCurr(x);
									}
								} else if (players[x].sumOfCards(players[x].getHand()) > players[0]
										.sumOfCards(players[0].getHand())) {
									System.out.println("CONGRATULATIONS, " + players[x].getName()
											+ ", YOU HAVE WON! YOUR NEW BALANCE IS: ");
									System.out.print("$");
									if (doubleDown == true) {
										wager.updateCurr(x, true, false, wager.getBet(x));
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, true, false, wager.getBet(x));
										wager.printCurr(x);
									}
								} else if (players[x].sumOfCards(players[x].getHand()) < players[0]
										.sumOfCards(players[0].getHand())) {
									System.out.println("SORRY, " + players[x].getName()
											+ ", YOU HAVE LOST. YOUR NEW BALANCE IS: ");
									System.out.print("$");
									if (doubleDown == true) {
										wager.updateCurr(x, false, false, wager.getBet(x));
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, false, false, wager.getBet(x));
										wager.printCurr(x);
									}
								} else if (players[x].sumOfCards(players[x].getHand()) > 21
										&& players[0].sumOfCards(players[0].getHand()) > 21) {
									System.out.println("SORRY, " + players[x].getName()
											+ ", YOU HAVE LOST. YOUR NEW BALANCE IS: ");
									System.out.print("$");
									if (doubleDown == true) {
										wager.updateCurr(x, false, false, wager.getBet(x));
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, false, false, wager.getBet(x));
										wager.printCurr(x);
									}
								} else if (players[x].sumOfCards(players[x].getHand()) == players[0]
										.sumOfCards(players[0].getHand())) {

									System.out.println(players[x].getName()
											+ ", YOU AND THE DEALER HAVE TIED (PUSHED). YOUR NEW BALANCE IS: ");
									System.out.print("$");
									if (doubleDown == true) {
										wager.updateCurr(x, false, true, wager.getBet(x));
										wager.printCurr(x);
									} else {
										wager.updateCurr(x, false, true, wager.getBet(x));
										wager.printCurr(x);
									}
									;
								}
								System.out.println();

							}
						} else {
							if (x == player) {
								System.out.println("SORRY" + players[x].getName() + ", YOU ARE NO LONGER PLAYING");
								wager.setCurr(x);
								endGame = "end"; // make this int.
							}

						}
					}

				}

				if (!endGame.equalsIgnoreCase("end")) {

					System.out.println("DO YOU WANT TO PLAY AGAIN? YES OR NO?");
					playAgain = sc.next();

					if (playAgain.equalsIgnoreCase("yes")) {

						for (int x = 0; x <= player; x++) {

							if (players[x].didSplit() == true) {

								players[x].emptyHand();
								players[x].emptySecHand();

							}

							players[x].emptyHand();

						}
					}

					if (playAgain.equalsIgnoreCase("no"))
						System.out.println("THANK YOU FOR PLAYING! HAVE A NICE DAY!");
				} else {
					System.out.println("SORRY GAME OVER!");
					playAgain = "no";
				}

			} while (playAgain.equalsIgnoreCase("yes"));
		}

	}
}
