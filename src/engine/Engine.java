package engine;

import static engine.Location.Type.GOTOJAIL;
import static engine.Location.Type.JAIL;
import static engine.Location.Type.PROPERTY;

import java.util.Scanner;

public class Engine{
	static final int MAX_PLAYERS = 10;
	
	/* global variables */
	static int die1 = 0;
	static int die2 = 0;
	static int numberOfPlayers;
	static int currentPlayer;
	
	static Player[] players = new Player[MAX_PLAYERS]; //contains player data
	
	static Scanner in = new Scanner(System.in);
	/* Adds the number of players to PlayerDB as specified by the integer input
		Variables changed - PlayerDB */
	static void setupPlayers(int numberOfPlayers) {
		int numberOfPlayersCreated = 1;
		while (numberOfPlayers > 0) {
			players[numberOfPlayersCreated - 1] = new Player();
			players[numberOfPlayersCreated - 1].setPlayerNumber(numberOfPlayersCreated);
			System.out.printf("Added player %d to the game. %n", numberOfPlayersCreated);
			numberOfPlayersCreated++;
			numberOfPlayers--;
		}
	}
	
	/* Checks to see if at least two players are still playing
		Variables changed - None */
	static boolean hasGameEnded() {
		int numberOfPlayersInGame = 0;
		for (int i = numberOfPlayers; i > 0; i--) {
			if (players[i - 1].isPlaying()) {
				numberOfPlayersInGame++;
			}
		}
		return numberOfPlayersInGame == 1;
	}
	
	/* Calculates the next player
		Variables changed - CurrentPlayer */
	static void nextPlayer(){
		currentPlayer = (currentPlayer + 1) % numberOfPlayers;
	}
	
	/* Waits for keyboard input of user for a Yes or No question and returns response
		Variables changed - None */
	static boolean askUserQuestion() {
		String answer = in.nextLine();;
		while(!(answer.startsWith("Y") || answer.startsWith("N"))) {
			System.out.println("Please enter either Y/N.");
			answer = in.nextLine();
		}
		return answer.startsWith("Y");
	}
	
	/* Calculates if CurrentPlayer has any properties that can be mortgaged or unmortgaged and asks player
		for if they would like to mortgage or unmortgage any properties.
		Possible variables changed - A properties' mortgage status, or a players' total money*/
	static void mortgageCheck(){
		if (getUnmortgagedCount() > 0) {
			System.out.printf("You own %d unmortgaged location(s). Would you like to mortgage any of them? Current player has $%d cash.%n", getUnmortgagedCount(), getCurrentPlayer().getMoney());
			if (askUserQuestion()) {
				for (int i = 0; i < Board.MAX_LOCATION; i++) {
					if (Board.board[i].getPlayerOwner() == currentPlayer && !Board.board[i].isMortgaged()) {
						System.out.printf("Would you like to mortgage %s? Current player has $%d cash.%n", Board.board[i].getLocationName(), getCurrentPlayer().getMoney());
						if (askUserQuestion()) {
							Board.board[i].changeMortgageProperty();
							getCurrentPlayer().changeMoney(Board.board[i].getMortgageValue());
						}
					}
				}
			}
		}
		if (getMortgagedCount() > 0) {
			System.out.printf("You own %d mortgaged location(s). Would you like to unmortgage any of them? Current player has $%d cash.%n", getMortgagedCount(), getCurrentPlayer().getMoney());
			if (askUserQuestion()) {
				for (int i = 0; i < Board.MAX_LOCATION; i++) {
					if (Board.board[i].getPlayerOwner() == currentPlayer && Board.board[i].isMortgaged()) {
						int unmortgageAmount = (Board.board[i].getMortgageValue() + (int)(Board.board[i].getMortgageValue() / 10));
						System.out.printf("Would you like to unmortgage %s for $%d? Current player has $%d cash.", Board.board[i].getLocationName(), unmortgageAmount, getCurrentPlayer().getMoney());
						if (askUserQuestion()) {
							if (getCurrentPlayer().getMoney() < getCurrentPlayerLocation().getPurchasePrice()) {
								System.out.printf("Player %d does not have enough money to unmortgage this property.%n", currentPlayer + 1);
								return;
							}
							Board.board[i].changeMortgageProperty();
							getCurrentPlayer().changeMoney(-1 * unmortgageAmount);
						}
					}
				}
			}
		}
	}

	public static int getMortgagedCount() {
		int count = 0;
		for (int i = 0; i < Board.MAX_LOCATION; i++) {
			if (Board.board[i].getPlayerOwner() == currentPlayer && Board.board[i].isMortgaged()) {
				count++;
			}
		}
		return count;
	}
	
	public static int getUnmortgagedCount() {
		int count = 0;
		for (int i = 0; i < Board.MAX_LOCATION; i++) {
			if (Board.board[i].getPlayerOwner() == currentPlayer && !Board.board[i].isMortgaged()) {
				count++;
			}
		}
		return count;
	}
	
	/* Checks whether CurrentPlayer is in Jail and hasn't paid and checks if they rolled doubles or want to pay to get out
		Possible variables changed - Jail Property player owner, players' total money, Array JailDB values */
	static boolean isPlayerJailed() {
		if (getCurrentPlayerLocation().getLocationType() == JAIL && getCurrentPlayer().isJailed()) {
			System.out.printf("Player %d is in jail.%n", currentPlayer + 1);
			if (die1 == die2) {
				System.out.printf("Since player %d has rolled doubles, they are now out of jail.%n", currentPlayer + 1);
				getCurrentPlayer().changeJailStatus();
				return true;
			}else if (getCurrentPlayer().getMoney() > 50) {
				System.out.printf("Would Player %d like to pay $50 and get out of jail? Current player has $%d cash.%n", currentPlayer + 1,getCurrentPlayer().getMoney());
				if (askUserQuestion()) {
					getCurrentPlayer().changeMoney(-50);
					System.out.printf("Player %d has paid the fine and is now out of jail.%n", currentPlayer + 1);
					getCurrentPlayer().changeJailStatus();
					return true;
				}
				else {
					System.out.printf("Player %d will not pay the fine and will remain in jail.%n", currentPlayer + 1);
					return true;
				}
			}
			else {
				System.out.printf("Player %d cannot pay the fine will remain in jail.%n", currentPlayer + 1);
				return true;
			}
		}
		else {
			return false;
		}
	}

	public static Player getCurrentPlayer() {
		return players[currentPlayer];
	}

	public static Location getCurrentPlayerLocation() {
		return Board.board[getCurrentPlayer().getLocation()];
	}
	
	/* Checks current location of CurrentPlayer and prompts for actions if appropriate */
	static void landingOnCheck() {
		if (getCurrentPlayerLocation().getLocationType() == PROPERTY) {
			int currentLocationOwner = getCurrentPlayerLocation().getPlayerOwner();
			if (currentLocationOwner == -1) {
				System.out.printf("No one owns this location. Would player %d like to purchase it for $%d? Current player has $%d cash.%n", currentPlayer + 1, getCurrentPlayerLocation().getPurchasePrice(), getCurrentPlayer().getMoney());
				mortgageCheck();
				if (askUserQuestion()) {
					if (getCurrentPlayer().getMoney() < getCurrentPlayerLocation().getPurchasePrice()) {
						System.out.printf("Player %d does not have enough money to purchase this property.%n", currentPlayer + 1);
						return;
					}
					getCurrentPlayerLocation().setPlayerOwner(currentPlayer);
					getCurrentPlayer().changeMoney(-1 * getCurrentPlayerLocation().getPurchasePrice());
					System.out.printf("Player %d now owns %s%n", currentPlayer+1, getCurrentPlayerLocation().getLocationName());
					return;
				}
			}
			else if (currentLocationOwner == currentPlayer) {
				System.out.printf("Player %d currently owns this location, and checks on all the employees to make sure they are happy.%n", currentPlayer+1);
				return;
			}
			else if (!getCurrentPlayerLocation().isMortgaged()){
				int rentValue = getCurrentPlayerLocation().getRentValue();
				System.out.printf("Player %d currently owns this location, and so player %d must pay the rent amount of $%d.%n", currentLocationOwner + 1, currentPlayer + 1, rentValue);
				getCurrentPlayer().changeMoney(-1*rentValue);
				players[currentLocationOwner].changeMoney(rentValue);
				return;
			}
			else {
				System.out.printf("Player %d currently owns this location, but it is currently mortgaged so player %d does not have to pay rent.%n", currentLocationOwner + 1, currentPlayer + 1);
			}
		}
		else if (getCurrentPlayerLocation().getLocationType() == GOTOJAIL) {
			for (int i = 0; i < Board.MAX_LOCATION; i++) {
				if (Board.board[i].getLocationType() == JAIL) {
					getCurrentPlayer().changeJailStatus();
					getCurrentPlayer().setLocation(i);
					System.out.printf("Player %d has been moved to jail.%n", currentPlayer + 1);
					return;
				}
			}
		}
	}
}



