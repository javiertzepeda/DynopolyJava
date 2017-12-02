package engine;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		IOUtil.printLogo();

		Board board = new Board();
		/* Sets up board */
		board.board[0] = new Go("Go", 0, 0, 0);
		board.board[1] = new Property("Mediterranean Ave.", 60, 50, 2);
		board.board[2] = new Property("Baltic Ave.", 60, 50, 4);
		board.board[3] = new Property("Oriental Ave.", 100, 50, 6);
		board.board[4] = new Property("Vermont Ave.", 100, 50, 6);
		board.board[5] = new Property("Connecticut Ave.", 120, 50, 8);
		board.board[6] = new Jail("Jail", 0, 0, 0);
		board.board[7] = new Property("St. Charles Place", 140, 50, 10);
		board.board[8] = new Property("States Ave.", 140, 0, 10);
		board.board[9] = new Property("Virginia Ave.", 160, 50, 12);
		board.board[10] = new Property("St. James Place", 180, 50, 14);
		board.board[11] = new Property("Tennessee Ave.", 180, 50, 14);
		board.board[12] = new Property("New York Ave.", 200, 50, 16);
		board.board[13] = new Property("Free Parking", 0, 0, 0);
		board.board[14] = new Property("Kentucky Ave.", 220, 50, 18);
		board.board[15] = new Property("Indiana Ave.", 220, 50, 18);
		board.board[16] = new Property("Illinois Ave.", 240, 50, 20);
		board.board[17] = new Property("Atlantic Ave.", 260, 50, 22);
		board.board[18] = new Property("Ventnor Ave.", 260, 50, 22);
		board.board[19] = new Property("Marvin Gardens", 280, 50, 24);
		board.board[20] = new GoToJail("Go To Jail", 0, 0, 0);
		board.board[21] = new Property("Pacific Ave.", 300, 50, 26);
		board.board[22] = new Property("North Carolina Ave.", 300, 50, 26);
		board.board[23] = new Property("Pennsylvania Ave.", 320, 50, 28);
		board.board[24] = new Property("Park Place", 350, 50, 35);
		board.board[25] = new Property("Boardwalk", 400, 50, 50);

		System.out.print("How many players: ");
		int players = 0;
		try {
			players = Integer.parseInt(in.nextLine());
		} catch (NumberFormatException e) {

		}
		while (players < Engine.MIN_PLAYERS && players > Engine.MAX_PLAYERS) {
			System.out.println("Please choose a number from 2 through 10.");
			System.out.print("How many players: ");
			try {
				players = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {

			}
		}
		engine = new Engine(players, board);

		System.out.printf("Player %s has been randomly selected to go first.%n", engine.getCurrentPlayer());

		while (!engine.hasGameEnded()) {
			System.out.printf("It is player %s's turn.%n", engine.getCurrentPlayer());

			engine.rollDie();
			System.out.printf("Player %s has rolled a %d and a %d.%n", engine.getCurrentPlayer(), engine.die1,
					engine.die2);

			processLeaving();

			if (engine.getCurrentPlayer().isJailed())
				askPlayerToPayJailFee();

			if (!engine.getCurrentPlayer().isJailed()) {
				engine.getCurrentPlayer().moveLocation(engine.die1 + engine.die2);
				engine.getCurrentPlayerLocation().incrementLandingCount();
				processLanding();
			}

			askPlayerToMortgageProperty();
			if (askPlayerToBuyProperty())
				askPlayerToMortgageProperty();

			engine.nextPlayer();
		}
		in.close();
	}

	public static void processLeaving() {
		LeavingVisitor lv = new LeavingVisitor(engine);
		LeavingMessageVisitor lmv = new LeavingMessageVisitor(engine);
		engine.getCurrentPlayerLocation().accept(lv);
		String message = engine.getCurrentPlayerLocation().accept(lmv);
		if (message != null)
			System.out.println(message);
	}

	static Scanner in = new Scanner(System.in);
	private static Engine engine;

	static void askPlayerToPayJailFee() {
		if (engine.getCurrentPlayer().getMoney() > 50) {
			System.out.printf("Would Player %s like to pay $50 and get out of jail? Current player has $%d cash.%n",
					engine.getCurrentPlayer().getName(), engine.getCurrentPlayer().getMoney());
			if (IOUtil.askUserQuestion(in)) {
				engine.getCurrentPlayer().changeMoney(-50);
				System.out.printf("Player %s has paid the fine and is now out of jail.%n",
						engine.getCurrentPlayer().getName());
				engine.getCurrentPlayer().changeJailStatus();
			} else {
				System.out.printf("Player %s will not pay the fine and will remain in jail.%n",
						engine.getCurrentPlayer().getName());
			}
		} else {
			System.out.printf("Player %s cannot pay the fine will remain in jail.%n",
					engine.getCurrentPlayer().getName());
		}
	}

	static void processLanding() {
		LandingVisitor lv = new LandingVisitor(engine);
		LandingMessageVisitor lmv = new LandingMessageVisitor(engine);
		engine.getCurrentPlayerLocation().accept(lv);
		System.out.println(engine.getCurrentPlayerLocation().accept(lmv));
	}

	static boolean askPlayerToBuyProperty() {
		if (engine.getCurrentPlayerLocation() instanceof Property) {
			Player currentLocationOwner = engine.getCurrentPlayerLocation().getPlayerOwner();
			if (currentLocationOwner == null) {
				System.out.printf("Would player %s like to purchase it for $%d? Current player has $%d cash.%n",
						engine.getCurrentPlayer().getName(), engine.getCurrentPlayerLocation().getPurchasePrice(),
						engine.getCurrentPlayer().getMoney());
				if (IOUtil.askUserQuestion(in)) {
					if (engine.getCurrentPlayer().getMoney() < engine.getCurrentPlayerLocation().getPurchasePrice()) {
						System.out.printf("Player %s does not have enough money to purchase this property.%n",
								engine.getCurrentPlayer().getName());
						return false;
					}
					engine.getCurrentPlayerLocation().setPlayerOwner(engine.getCurrentPlayer());
					engine.getCurrentPlayer().changeMoney(-1 * engine.getCurrentPlayerLocation().getPurchasePrice());
					System.out.printf("Player %s now owns %s%n", engine.getCurrentPlayer().getName(),
							engine.getCurrentPlayerLocation().getLocationName());
					return true;
				}
			}
		}
		return false;
	}

	static void askPlayerToMortgageProperty() {
		if (engine.getUnmortgagedCount() > 0) {
			System.out.printf(
					"You own %d unmortgaged location(s). Would you like to mortgage any of them? Current player has $%d cash.%n",
					engine.getUnmortgagedCount(), engine.getCurrentPlayer().getMoney());
			if (IOUtil.askUserQuestion(in)) {
				for (int i = 0; i < Board.MAX_LOCATION; i++) {
					if (engine.board.board[i].getPlayerOwner() == engine.getCurrentPlayer()
							&& !engine.board.board[i].isMortgaged()) {
						System.out.printf("Would you like to mortgage %s? Current player has $%d cash.%n",
								engine.board.board[i].getLocationName(), engine.getCurrentPlayer().getMoney());
						if (IOUtil.askUserQuestion(in)) {
							engine.board.board[i].changeMortgageProperty();
							engine.getCurrentPlayer().changeMoney(engine.board.board[i].getMortgageValue());
						}
					}
				}
			}
		}
		if (engine.getMortgagedCount() > 0) {
			System.out.printf(
					"You own %d mortgaged location(s). Would you like to unmortgage any of them? Current player has $%d cash.%n",
					engine.getMortgagedCount(), engine.getCurrentPlayer().getMoney());
			if (IOUtil.askUserQuestion(in)) {
				for (int i = 0; i < Board.MAX_LOCATION; i++) {
					if (engine.board.board[i].getPlayerOwner() == engine.getCurrentPlayer()
							&& engine.board.board[i].isMortgaged()) {
						int unmortgageAmount = (engine.board.board[i].getMortgageValue()
								+ (int) (engine.board.board[i].getMortgageValue() / 10));
						System.out.printf("Would you like to unmortgage %s for $%d? Current player has $%d cash.",
								engine.board.board[i].getLocationName(), unmortgageAmount,
								engine.getCurrentPlayer().getMoney());
						if (IOUtil.askUserQuestion(in)) {
							if (engine.getCurrentPlayer().getMoney() < engine.getCurrentPlayerLocation()
									.getPurchasePrice()) {
								System.out.printf("Player %s does not have enough money to unmortgage this property.%n",
										engine.getCurrentPlayer().getName());
								return;
							}
							engine.board.board[i].changeMortgageProperty();
							engine.getCurrentPlayer().changeMoney(-1 * unmortgageAmount);
						}
					}
				}
			}
		}
	}

}
