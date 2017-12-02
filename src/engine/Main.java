package engine;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static engine.Location.Type.*;

public class Main{
	private static final int MAX_PLAYERS = 10;
	
	/* global variables */
	static int die1 = 0;
	static int die2 = 0;
	static int numberOfPlayers;
	static int currentPlayer;
	
	static Player[] players = new Player[MAX_PLAYERS]; //contains player data
	
	static Location[] board = new Location[Board.MAX_LOCATION]; //contains location data
	
	/* Adds the number of players to PlayerDB as specified by the integer input
		Variables changed - PlayerDB */
	static void setupPlayers(int numberOfPlayers) {
		int numberOfPlayersCreated = 1;
		while (numberOfPlayers > 0) {
			players[numberOfPlayersCreated - 1] = new Player();
			players[numberOfPlayersCreated - 1].setPlayerNumber(numberOfPlayersCreated);
			System.out.println(String.format("Added player %d to the game. \n", numberOfPlayersCreated));
			numberOfPlayersCreated++;
			numberOfPlayers--;
		}
	}
	
	/* Checks to see if atleast two players are still playing
		Variables changed - None */
	static boolean hasGameEnded() {
		int numberOfPlayersInGame = 0;
		for (int i = numberOfPlayers; i > 0; i--) {
			if (players[i - 1].isPlaying()) {
				numberOfPlayersInGame++;
			}
		}
		if (numberOfPlayersInGame > 1) {
			return false;
		}
	    return true;
	}
	
	/* Calculates the next player
		Variables changed - CurrentPlayer */
	static void nextPlayer(){
		currentPlayer++;
		if (currentPlayer == numberOfPlayers) {
			currentPlayer = 0;
		}
	}
	
	/* Waits for keyboard input of user for a Yes or No question and returns response
		Variables changed - None */
	static boolean askUserQuestion() {
		char answer = 'q';
		while (answer != 'Y' && answer != 'y' && answer != 'N' && answer != 'n') {
			Scanner reader = new Scanner(System.in);
			answer = reader.next().trim().charAt(0);
			
			if (answer != 'Y' && answer != 'y' && answer != 'N' && answer != 'n' && answer != 'q') {
				System.out.println("Please enter either Y/N.\n");
			}
		}
		if (answer == 'Y' || answer == 'y') {
			return true;
		}
		if (answer == 'N' || answer == 'n') {
			return false;
		}
	    return false;
	}
	
	/* Calculates if CurrentPlayer has any properties that can be mortgaged or unmortgaged and asks player
		for if they would like to mortgage or unmortgage any properties.
		Possible variables changed - A properties' mortgage status, or a players' total money*/
	static void mortgageCheck(){
		int unmortgagedPropertiesCount = 0;
		int mortgagedPropertiesCount = 0;
		for (int i = 0; i < Board.MAX_LOCATION; i++) {
			if (board[i].getPlayerOwner() == currentPlayer && board[i].isMortgaged() == false) {
				unmortgagedPropertiesCount++;
			}
			if (board[i].getPlayerOwner() == currentPlayer && board[i].isMortgaged() == true) {
				mortgagedPropertiesCount++;
			}
		}
		if (unmortgagedPropertiesCount > 0) {
			System.out.println(String.format("You own %d unmortgaged location(s). Would you like to mortgage any of them? Current player has $%d cash.\n", unmortgagedPropertiesCount, players[currentPlayer].getMoney()));
			boolean answer = askUserQuestion();
			if (answer == true) {
				for (int i = 0; i < Board.MAX_LOCATION; i++) {
					if (board[i].getPlayerOwner() == currentPlayer && board[i].isMortgaged() == false) {
						System.out.println(String.format("Would you like to mortgage %s? Current player has $%d cash.\n", board[i].getLocationName(), players[currentPlayer].getMoney()));
						boolean answer2 = askUserQuestion();
						if (answer2 == true) {
							board[i].changeMortgageProperty();
							players[currentPlayer].changeMoney(board[i].getMortgageValue());
						}
					}
				}
			}
		}
		if (mortgagedPropertiesCount > 0) {
			System.out.println(String.format("You own %d mortgaged location(s). Would you like to unmortgage any of them? Current player has $%d cash.\n", mortgagedPropertiesCount, players[currentPlayer].getMoney()));
			boolean answer = askUserQuestion();
			if (answer == true) {
				for (int i = 0; i < Board.MAX_LOCATION; i++) {
					if (board[i].getPlayerOwner() == currentPlayer && board[i].isMortgaged() == true) {
						int unmortgageAmount = (board[i].getMortgageValue() + (int)(board[i].getMortgageValue() / 10));
						System.out.println(String.format("Would you like to unmortgage %s for $%d? Current player has $%d cash.\n", board[i].getLocationName(), unmortgageAmount, players[currentPlayer].getMoney()));
						boolean answer2 = askUserQuestion();
						if (answer2 == true) {
							if (players[currentPlayer].getMoney() < board[players[currentPlayer].getLocation()].getPurchasePrice()) {
								System.out.println(String.format("Player %d does not have enough money to unmortgage this property.\n", currentPlayer + 1));
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
			System.out.println(String.format("Player %d is in jail.\n", currentPlayer + 1));
			if (die1 == die2) {
				System.out.println(String.format("Since player %d has rolled doubles, they are now out of jail.\n", currentPlayer + 1));
				players[currentPlayer].changeJailStatus();
				return true;
			}else if (players[currentPlayer].getMoney() > 50) {
				System.out.println(String.format("Would Player %d like to pay $50 and get out of jail? Current player has $%d cash.\n", currentPlayer + 1,players[currentPlayer].getMoney()));
				boolean answer = askUserQuestion();
				if (answer == true) {
					players[currentPlayer].changeMoney(-50);
					System.out.println(String.format("Player %d has paid the fine and is now out of jail.\n", currentPlayer + 1));
					players[currentPlayer].changeJailStatus();
					return true;
				}
				else {
					System.out.println(String.format("Player %d will not pay the fine and will remain in jail.\n", currentPlayer + 1));
					return true;
				}
			}
			else {
				System.out.println(String.format("Player %d cannot pay the fine will remain in jail.\n", currentPlayer + 1));
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
				System.out.println(String.format("No one owns this location. Would player %d like to purchase it for $%d? Current player has $%d cash.\n", currentPlayer + 1, board[players[currentPlayer].getLocation()].getPurchasePrice(), players[currentPlayer].getMoney()));
				mortgageCheck();
				boolean answer = askUserQuestion();
				if (answer == true) {
					if (players[currentPlayer].getMoney() < board[players[currentPlayer].getLocation()].getPurchasePrice()) {
						System.out.println(String.format("Player %d does not have enough money to purchase this property.", currentPlayer + 1));
						return;
					}
					board[players[currentPlayer].getLocation()].setPlayerOwner(currentPlayer);
					players[currentPlayer].changeMoney(-1 * board[players[currentPlayer].getLocation()].getPurchasePrice());
					System.out.println(String.format("Player %d now owns %s\n", currentPlayer+1, board[players[currentPlayer].getLocation()].getLocationName()));
					return;
				}
			}
			else if (CurrentLocationOwner == currentPlayer) {
				System.out.println(String.format("Player %d currently owns this location, and checks on all the employees to make sure they are happy.\n", currentPlayer+1));
				return;
			}
			else if (board[players[currentPlayer].getLocation()].isMortgaged()==false){
				int RentValue = board[players[currentPlayer].getLocation()].getRentValue();
				System.out.println(String.format("Player %d currently owns this location, and so player %d must pay the rent amount of $%d.\n", CurrentLocationOwner + 1, currentPlayer + 1, RentValue));
				players[currentPlayer].changeMoney(-1*RentValue);
				players[CurrentLocationOwner].changeMoney(RentValue);
				return;
			}
			else {
				System.out.println(String.format("Player %d currently owns this location, but it is currently mortgaged so player %d does not have to pay rent.\n", CurrentLocationOwner + 1, currentPlayer + 1));
			}
		}
		else if (board[players[currentPlayer].getLocation()].getLocationType() == GOTOJAIL) {
			for (int i = 0; i < Board.MAX_LOCATION; i++) {
				if (board[i].getLocationType() == JAIL) {
					players[currentPlayer].changeJailStatus();
					players[currentPlayer].setLocation(i);
					System.out.println(String.format("Player %d has been moved to jail.\n", currentPlayer + 1));
					return;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		//dynopoly ascii wording developed using http://patorjk.com/software/taag/#p=display&f=Mer&t=Dynopoly!%20
		//world ascii art found at http://www.chris.com/ascii/
		System.out.println("             __gggrgM**M#mggg__");
		System.out.println("         __wgNN@\"B*P\"\"mp\"\"@d#\"@N#Nw__");
		System.out.println("       _g#@0F_a*F#  _*F9m_ ,F9*__9NG#g_");
		System.out.println("    _mN#F  aM\"    #p\"    !q@    9NL \"9#Qu_");
		System.out.println("   g#MF _pP\"L  _g@\"9L_  _g\"\"#__  g\"9w_ 0N#p");
		System.out.println(" _0F jL*\"   7_wF     #_gF     9gjF   \"bJ  9h_");
		System.out.println("j#  gAF    _@NL     _g@#_      J@u_    2#_  #_");
		System.out.println(",FF_#\" 9_ _#\"  \"b_  g@   \"hg  _#\"  !q_ jF \"*_09_");
		System.out.println("F N\"    #p\"      Ng@       `#g\"      \"w@    \"# t");
		System.out.println("j p#    g\"9_     g@\"9_      gP\"#_     gF\"q    Pb L");
		System.out.println("0J  k _@   9g_ j#\"   \"b_  j#\"   \"b_ _d\"   q_ g  ##");
		System.out.println("#F  `NF     \"#g\"       \"Md\"       5N#      9W\"  j#");
		System.out.println("  _____                                _       _  ");
		System.out.println(" |  __ \\                              | |     | | ");
		System.out.println(" | |  | |_   _ _ __   ___  _ __   ___ | |_   _| |");
		System.out.println(" | |  | | | | | '_ \\ / _ \\| '_ \\ / _ \\| | | | | | ");
		System.out.println(" | |__| | |_| | | | | (_) | |_) | (_) | | |_| |_| ");
		System.out.println(" |_____/ \\__, |_| |_|\\___/| .__/ \\___/|_|\\__, (_) ");
		System.out.println("          __/ |           | |             __/ | ");
		System.out.println("         |___/            |_|            |___/   ");
		
		System.out.println("#k  jFb_    g@\"q_     _*\"9m_     _*\"R_    _#Np  J#");
		System.out.println("tApjF  9g  J\"   9M_ _m\"    9%_ _*\"   \"#  gF  9_jNF");
		System.out.println("k`N    \"q#       9g@        #gF       ##\"    #\"j");
		System.out.println("`_0q_   #\"q_    _&\"9p_    _g\"`L_    _*\"#   jAF,'");
		System.out.println("9# \"b_j   \"b_ g\"    *g _gF    9_ g#\"  \"L_*\"qNF");
		System.out.println(" \"b_ \"#_    \"NL      _B#      _I@     j#\" _#\"");
		System.out.println("   NM_0\"*g_ j\"\"9u_  gP  q_  _w@ ]_ _g*\"F_g@");
		System.out.println("    \"NNh_ !w#_   9#g\"    \"m*\"   _#*\" _dN@\"");
		System.out.println("       9##g_0@q__ #\"4_  j*\"k __*NF_g#@P\"");
		System.out.println("         \"9NN#gIPNL_ \"b@\" _2M\"Lg#N@F\"");
		System.out.println("             \"\"P@*NN#gEZgNN@#@P\"\"");

		
		/* prompts user for number of players in the game */
		System.out.println("How many players? ");
		Scanner reader = new Scanner(System.in);
		numberOfPlayers = Integer.parseInt(reader.next().trim());
		
	
		while (numberOfPlayers <= 1 || numberOfPlayers > MAX_PLAYERS) {
			System.out.println("Please choose a number from 2 through 10. How many players? ");
			numberOfPlayers = Integer.parseInt(reader.next().trim());
		}
		/* populates PlayerDB with the number of players specified above */
		setupPlayers(numberOfPlayers);
	
		/* Sets up board */
		String loc0 = "Go";
		board[0] = new Location(loc0, GO, 0, 0, 0);
		String loc1 = "Mediterranean Ave.";
		board[1] = new Location(loc1, PROPERTY, 60, 50, 2);
		String loc2 = "Baltic Ave.";
		board[2] = new Location(loc2, PROPERTY, 60, 50, 4);
		String loc3 = "Oriental Ave.";
		board[3] = new Location(loc3, PROPERTY, 100, 50, 6);
		String loc4 = "Vermont Ave.";
		board[4] = new Location(loc4, PROPERTY, 100, 50, 6);
		String loc5 = "Connecticut Ave.";
		board[5] = new Location(loc5, PROPERTY, 120, 50, 8);
		String loc6 = "Jail";
		board[6] = new Location(loc6, JAIL, 0, 0, 0);
		String loc7 = "St. Charles Place";
		board[7] = new Location(loc7, PROPERTY, 140, 50, 10);
		String loc8 = "States Ave.";
		board[8] = new Location(loc8, PROPERTY, 140, 0, 10);
		String loc9 = "Virginia Ave.";
		board[9] = new Location(loc9, PROPERTY, 160, 50, 12);
		String loc10 = "St. James Place";
		board[10] = new Location(loc10, PROPERTY, 180, 50, 14);
		String loc11 = "Tennessee Ave.";
		board[11] = new Location(loc11, PROPERTY, 180, 50, 14);
		String loc12 = "New York Ave.";
		board[12] = new Location(loc12, PROPERTY, 200, 50, 16);
		String loc13 = "Free Parking";
		board[13] = new Location(loc13, FREEPARKING, 0, 0, 0);
		String loc14 = "Kentucky Ave.";
		board[14] = new Location(loc14, PROPERTY, 220, 50, 18);
		String loc15 = "Indiana Ave.";
		board[15] = new Location(loc15, PROPERTY, 220, 50, 18);
		String loc16 = "Illinois Ave.";
		board[16] = new Location(loc16, PROPERTY, 240, 50, 20);
		String loc17 = "Atlantic Ave.";
		board[17] = new Location(loc17, PROPERTY, 260, 50, 22);
		String loc18 = "Ventnor Ave.";
		board[18] = new Location(loc18, PROPERTY, 260, 50, 22);
		String loc19 = "Marvin Gardens";
		board[19] = new Location(loc19, PROPERTY, 280, 50, 24);
		String loc20 = "Go To Jail";
		board[20] = new Location(loc20, GOTOJAIL, 0, 0, 0);
		String loc21 = "Pacific Ave.";
		board[21] = new Location(loc21, PROPERTY, 300, 50, 26);
		String loc22 = "North Carolina Ave.";
		board[22] = new Location(loc22, PROPERTY, 300, 50, 26);
		String loc23 = "Pennsylvania Ave.";
		board[23] = new Location(loc23, PROPERTY, 320, 50, 28);
		String loc24 = "Park Place";
		board[24] = new Location(loc24, PROPERTY, 350, 50, 35);
		String loc25 = "Boardwalk";
		board[25] = new Location(loc25, PROPERTY, 400, 50, 50);
	
		/* Randomly choose a player to go first */
		Random rand = new Random();
		currentPlayer = (rand.nextInt(50) + 1) % numberOfPlayers;
		System.out.println(String.format("Player %d has been randomly selected to go first.\n", currentPlayer+1));
	
		/* Infinitely loops game until only one player is left */
		while (hasGameEnded() == false) {
			die1 = (rand.nextInt(50) + 1) % 6 + 1;
			die2 = (rand.nextInt(50) + 1) % 6 + 1;
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				
			} /* Slight delay between each player's turn */
			System.out.println(String.format("It is player %d's turn.\n", currentPlayer + 1));
			//MortgageCheck(); /* Uncomment to ask player if they would like to mortgage/unmortgage before they roll */
			System.out.println(String.format("Player %d has rolled a %d and a %d.\n", currentPlayer + 1, die1, die2));
			if (isPlayerJailed() == false) { // Before moving player, checks to see if a Player is in jail and needs to pay a fine
				players[currentPlayer].moveLocation(die1 + die2); // Moves Player to new location, checks if they passed go (+$200)
				String currentPlayerLocation = board[players[currentPlayer].getLocation()].getLocationName(); // gets string data for the location that CurrentPlayer moved to 
				board[players[currentPlayer].getLocation()].incrementLandingCount(); // increase variable in location that stores the number of times a player has landed on it
				System.out.println(String.format("Player %d moves %d spaces and lands on %s.\n", currentPlayer + 1, die1 + die2, currentPlayerLocation));
				landingOnCheck(); // Processes location to see if user landed on jail, on another player's property, an unpurchased property, or a property that doesn't require action
			}
			
			mortgageCheck(); // If applicable, asks the user if they would like to mortgage or unmortgage properties
	
			nextPlayer(); // Calculates next player and restarts loop
		}
		
	}
}



