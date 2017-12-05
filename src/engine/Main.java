package engine;

import java.util.Scanner;

public class Main {
	static Scanner in = new Scanner(System.in);
	private static Engine engine;
	
	public static void main(String[] args) {
		IOUtil.printLogo();
		
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
		engine = new Engine(players, Board.defaultBoard(), 2000, 6);

		engine.selectRandomPlayer();
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
