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
	
	static Location[] board = new Location[Board.MAX_LOCATION]; //contains location data

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
		int unmortgagedPropertiesCount = 0;
		int mortgagedPropertiesCount = 0;
		for (int i = 0; i < Board.MAX_LOCATION; i++) {
			if (board[i].getPlayerOwner() == currentPlayer && board[i].isMortgaged()) {
				mortgagedPropertiesCount++;
			}
			else {
				unmortgagedPropertiesCount++;
			}
		}
		if (unmortgagedPropertiesCount > 0) {
			System.out.printf("You own %d unmortgaged location(s). Would you like to mortgage any of them? Current player has $%d cash.%n", unmortgagedPropertiesCount, players[currentPlayer].getMoney());
			if (askUserQuestion()) {
				for (int i = 0; i < Board.MAX_LOCATION; i++) {
					if (board[i].getPlayerOwner() == currentPlayer && !board[i].isMortgaged()) {
						System.out.printf("Would you like to mortgage %s? Current player has $%d cash.%n", board[i].getLocationName(), players[currentPlayer].getMoney());
						if (askUserQuestion()) {
							board[i].changeMortgageProperty();
							players[currentPlayer].changeMoney(board[i].getMortgageValue());
						}
					}
				}
			}
		}
		if (mortgagedPropertiesCount > 0) {
			System.out.printf("You own %d mortgaged location(s). Would you like to unmortgage any of them? Current player has $%d cash.%n", mortgagedPropertiesCount, players[currentPlayer].getMoney());
			if (askUserQuestion()) {
				for (int i = 0; i < Board.MAX_LOCATION; i++) {
					if (board[i].getPlayerOwner() == currentPlayer && board[i].isMortgaged()) {
						int unmortgageAmount = (board[i].getMortgageValue() + (int)(board[i].getMortgageValue() / 10));
						System.out.printf("Would you like to unmortgage %s for $%d? Current player has $%d cash.", board[i].getLocationName(), unmortgageAmount, players[currentPlayer].getMoney());
						if (askUserQuestion()) {
							if (players[currentPlayer].getMoney() < board[players[currentPlayer].getLocation()].getPurchasePrice()) {
								System.out.printf("Player %d does not have enough money to unmortgage this property.%n", currentPlayer + 1);
								return;
							}
							board[i].changeMortgageProperty();
							players[currentPlayer].changeMoney(-1 * unmortgageAmount);
						}
					}
				}
			}
		}
	}
	
	/* Checks whether CurrentPlayer is in Jail and hasn't paid and checks if they rolled doubles or want to pay to get out
		Possible variables changed - Jail Property player owner, players' total money, Array JailDB values */
	static boolean isPlayerJailed() {
		if (board[players[currentPlayer].getLocation()].getLocationType() == JAIL && players[currentPlayer].isJailed()) {
			System.out.printf("Player %d is in jail.%n", currentPlayer + 1);
			if (die1 == die2) {
				System.out.printf("Since player %d has rolled doubles, they are now out of jail.%n", currentPlayer + 1);
				players[currentPlayer].changeJailStatus();
				return true;
			}else if (players[currentPlayer].getMoney() > 50) {
				System.out.printf("Would Player %d like to pay $50 and get out of jail? Current player has $%d cash.%n", currentPlayer + 1,players[currentPlayer].getMoney());
				if (askUserQuestion()) {
					players[currentPlayer].changeMoney(-50);
					System.out.printf("Player %d has paid the fine and is now out of jail.%n", currentPlayer + 1);
					players[currentPlayer].changeJailStatus();
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
	
	/* Checks current location of CurrentPlayer and prompts for actions if appropriate */
	static void landingOnCheck() {
		if (board[players[currentPlayer].getLocation()].getLocationType() == PROPERTY) {
			int CurrentLocationOwner = board[players[currentPlayer].getLocation()].getPlayerOwner();
			if (CurrentLocationOwner == -1) {
				System.out.printf("No one owns this location. Would player %d like to purchase it for $%d? Current player has $%d cash.%n", currentPlayer + 1, board[players[currentPlayer].getLocation()].getPurchasePrice(), players[currentPlayer].getMoney());
				mortgageCheck();
				if (askUserQuestion()) {
					if (players[currentPlayer].getMoney() < board[players[currentPlayer].getLocation()].getPurchasePrice()) {
						System.out.printf("Player %d does not have enough money to purchase this property.%n", currentPlayer + 1);
						return;
					}
					board[players[currentPlayer].getLocation()].setPlayerOwner(currentPlayer);
					players[currentPlayer].changeMoney(-1 * board[players[currentPlayer].getLocation()].getPurchasePrice());
					System.out.printf("Player %d now owns %s%n", currentPlayer+1, board[players[currentPlayer].getLocation()].getLocationName());
					return;
				}
			}
			else if (CurrentLocationOwner == currentPlayer) {
				System.out.printf("Player %d currently owns this location, and checks on all the employees to make sure they are happy.%n", currentPlayer+1);
				return;
			}
			else if (!board[players[currentPlayer].getLocation()].isMortgaged()){
				int RentValue = board[players[currentPlayer].getLocation()].getRentValue();
				System.out.printf("Player %d currently owns this location, and so player %d must pay the rent amount of $%d.%n", CurrentLocationOwner + 1, currentPlayer + 1, RentValue);
				players[currentPlayer].changeMoney(-1*RentValue);
				players[CurrentLocationOwner].changeMoney(RentValue);
				return;
			}
			else {
				System.out.printf("Player %d currently owns this location, but it is currently mortgaged so player %d does not have to pay rent.%n", CurrentLocationOwner + 1, currentPlayer + 1);
			}
		}
		else if (board[players[currentPlayer].getLocation()].getLocationType() == GOTOJAIL) {
			for (int i = 0; i < Board.MAX_LOCATION; i++) {
				if (board[i].getLocationType() == JAIL) {
					players[currentPlayer].changeJailStatus();
					players[currentPlayer].setLocation(i);
					System.out.printf("Player %d has been moved to jail.%n", currentPlayer + 1);
					return;
				}
			}
		}
	}
}



