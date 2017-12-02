package engine;

import static engine.Location.Type.FREEPARKING;
import static engine.Location.Type.GO;
import static engine.Location.Type.GOTOJAIL;
import static engine.Location.Type.JAIL;
import static engine.Location.Type.PROPERTY;

import java.util.Scanner;

public class Main {

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
	
		Board board = new Board();
		/* Sets up board */
		board.board[0] = new Location("Go", GO, 0, 0, 0);
		board.board[1] = new Location("Mediterranean Ave.", PROPERTY, 60, 50, 2);
		board.board[2] = new Location("Baltic Ave.", PROPERTY, 60, 50, 4);
		board.board[3] = new Location("Oriental Ave.", PROPERTY, 100, 50, 6);
		board.board[4] = new Location("Vermont Ave.", PROPERTY, 100, 50, 6);
		board.board[5] = new Location("Connecticut Ave.", PROPERTY, 120, 50, 8);
		board.board[6] = new Location("Jail", JAIL, 0, 0, 0);
		board.board[7] = new Location("St. Charles Place", PROPERTY, 140, 50, 10);
		board.board[8] = new Location("States Ave.", PROPERTY, 140, 0, 10);
		board.board[9] = new Location("Virginia Ave.", PROPERTY, 160, 50, 12);
		board.board[10] = new Location("St. James Place", PROPERTY, 180, 50, 14);
		board.board[11] = new Location("Tennessee Ave.", PROPERTY, 180, 50, 14);
		board.board[12] = new Location("New York Ave.", PROPERTY, 200, 50, 16);
		board.board[13] = new Location("Free Parking", FREEPARKING, 0, 0, 0);
		board.board[14] = new Location("Kentucky Ave.", PROPERTY, 220, 50, 18);
		board.board[15] = new Location("Indiana Ave.", PROPERTY, 220, 50, 18);
		board.board[16] = new Location("Illinois Ave.", PROPERTY, 240, 50, 20);
		board.board[17] = new Location("Atlantic Ave.", PROPERTY, 260, 50, 22);
		board.board[18] = new Location("Ventnor Ave.", PROPERTY, 260, 50, 22);
		board.board[19] = new Location("Marvin Gardens", PROPERTY, 280, 50, 24);
		board.board[20] = new Location("Go To Jail", GOTOJAIL, 0, 0, 0);
		board.board[21] = new Location("Pacific Ave.", PROPERTY, 300, 50, 26);
		board.board[22] = new Location("North Carolina Ave.", PROPERTY, 300, 50, 26);
		board.board[23] = new Location("Pennsylvania Ave.", PROPERTY, 320, 50, 28);
		board.board[24] = new Location("Park Place", PROPERTY, 350, 50, 35);
		board.board[25] = new Location("Boardwalk", PROPERTY, 400, 50, 50);
		
		/* prompts user for number of players in the game */
		System.out.print("How many players: ");
		int players = 0;
		try {
			players = Integer.parseInt(in.nextLine());
		} catch (NumberFormatException e) {
			
		}
		while(players < Engine.MIN_PLAYERS && players > Engine.MAX_PLAYERS) {
			System.out.println("Please choose a number from 2 through 10.");
			System.out.print("How many players: ");
			try {
				players = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				
			}
		}
		engine = new Engine(players, board);
	

	
		/* Randomly choose a player to go first */
		System.out.printf("Player %s has been randomly selected to go first.%n", engine.getCurrentPlayer());
	
		/* Infinitely loops game until only one player is left */
		while (!engine.hasGameEnded()) {
			engine.rollDie();
			System.out.printf("It is player %s's turn.%n", engine.getCurrentPlayer());
			
			System.out.printf("Player %s has rolled a %d and a %d.%n", engine.getCurrentPlayer(), engine.die1, engine.die2);
			if (!isPlayerJailed()) { // Before moving player, checks to see if a Player is in jail and needs to pay a fine
				engine.getCurrentPlayer().moveLocation(engine.die1 + engine.die2); // Moves Player to new location, checks if they passed go (+$200)
				String currentPlayerLocation = engine.getCurrentPlayerLocation().getLocationName(); // gets string data for the location that CurrentPlayer moved to 
				engine.getCurrentPlayerLocation().incrementLandingCount(); // increase variable in location that stores the number of times a player has landed on it
				System.out.printf("Player %s moves %d spaces and lands on %s.%n", engine.getCurrentPlayer(), engine.die1 + engine.die2, currentPlayerLocation);
				Main.landingOnCheck(); // Processes location to see if user landed on jail, on another player's property, an unpurchased property, or a property that doesn't require action
			}
			
			Main.mortgageCheck(); // If applicable, asks the user if they would like to mortgage or unmortgage properties
	
			engine.nextPlayer(); // Calculates next player and restarts loop
		}
		Main.in.close();
	}

	private static Scanner in = new Scanner(System.in);
	private static Engine engine;

	/*
	 * Waits for keyboard input of user for a Yes or No question and returns
	 * response Variables changed - None
	 */
	static boolean askUserQuestion() {
		String answer = in.nextLine();
		while (!(answer.startsWith("Y") || answer.startsWith("N"))) {
			System.out.println("Please enter either Y/N.");
			answer = in.nextLine();
		}
		return answer.startsWith("Y");
	}

	/*
	 * Checks whether CurrentPlayer is in Jail and hasn't paid and checks if they
	 * rolled doubles or want to pay to get out Possible variables changed - Jail
	 * Property player owner, players' total money, Array JailDB values
	 */
	static boolean isPlayerJailed() {
		if (engine.getCurrentPlayerLocation().getLocationType() == JAIL && engine.getCurrentPlayer().isJailed()) {
			System.out.printf("Player %s is in jail.%n", engine.getCurrentPlayer());
			if (engine.die1 == engine.die2) {
				System.out.printf("Since player %s has rolled doubles, they are now out of jail.%n", engine.getCurrentPlayer().getName());
				engine.getCurrentPlayer().changeJailStatus();
				return true;
			} else if (engine.getCurrentPlayer().getMoney() > 50) {
				System.out.printf("Would Player %s like to pay $50 and get out of jail? Current player has $%d cash.%n",
						engine.getCurrentPlayer().getName(), engine.getCurrentPlayer().getMoney());
				if (askUserQuestion()) {
					engine.getCurrentPlayer().changeMoney(-50);
					System.out.printf("Player %s has paid the fine and is now out of jail.%n", engine.getCurrentPlayer().getName());
					engine.getCurrentPlayer().changeJailStatus();
					return true;
				} else {
					System.out.printf("Player %s will not pay the fine and will remain in jail.%n", engine.getCurrentPlayer().getName());
					return true;
				}
			} else {
				System.out.printf("Player %s cannot pay the fine will remain in jail.%n", engine.getCurrentPlayer().getName());
				return true;
			}
		} else {
			return false;
		}
	}

	/*
	 * Checks current location of CurrentPlayer and prompts for actions if
	 * appropriate
	 */
	static void landingOnCheck() {
		if (engine.getCurrentPlayerLocation().getLocationType() == PROPERTY) {
			Player currentLocationOwner = engine.getCurrentPlayerLocation().getPlayerOwner();
			if (currentLocationOwner == null) {
				System.out.printf(
						"No one owns this location. Would player %s like to purchase it for $%d? Current player has $%d cash.%n",
						engine.getCurrentPlayer().getName(), engine.getCurrentPlayerLocation().getPurchasePrice(),
						engine.getCurrentPlayer().getMoney());
				Main.mortgageCheck();
				if (askUserQuestion()) {
					if (engine.getCurrentPlayer().getMoney() < engine.getCurrentPlayerLocation().getPurchasePrice()) {
						System.out.printf("Player %s does not have enough money to purchase this property.%n",
								engine.getCurrentPlayer().getName());
						return;
					}
					engine.getCurrentPlayerLocation().setPlayerOwner(engine.getCurrentPlayer());
					engine.getCurrentPlayer().changeMoney(-1 * engine.getCurrentPlayerLocation().getPurchasePrice());
					System.out.printf("Player %s now owns %s%n", engine.getCurrentPlayer().getName(),
							engine.getCurrentPlayerLocation().getLocationName());
					return;
				}
			} else if (currentLocationOwner == engine.getCurrentPlayer()) {
				System.out.printf(
						"Player %s currently owns this location, and checks on all the employees to make sure they are happy.%n",
						engine.getCurrentPlayer().getName());
				return;
			} else if (!engine.getCurrentPlayerLocation().isMortgaged()) {
				int rentValue = engine.getCurrentPlayerLocation().getRentValue();
				System.out.printf(
						"Player %s currently owns this location, and so player %s must pay the rent amount of $%d.%n",
						currentLocationOwner.getName(), engine.getCurrentPlayer().getName(), rentValue);
				engine.getCurrentPlayer().changeMoney(-1 * rentValue);
				currentLocationOwner.changeMoney(rentValue);
				return;
			} else {
				System.out.printf(
						"Player %s currently owns this location, but it is currently mortgaged so player %s does not have to pay rent.%n",
						currentLocationOwner.getName(), engine.getCurrentPlayer().getName());
			}
		} else if (engine.getCurrentPlayerLocation().getLocationType() == GOTOJAIL) {
			for (int i = 0; i < Board.MAX_LOCATION; i++) {
				if (engine.board.board[i].getLocationType() == JAIL) {
					engine.getCurrentPlayer().changeJailStatus();
					engine.getCurrentPlayer().setLocation(i);
					System.out.printf("Player %s has been moved to jail.%n", engine.getCurrentPlayer().getName());
					return;
				}
			}
		}
	}

	/*
	 * Calculates if CurrentPlayer has any properties that can be mortgaged or
	 * unmortgaged and asks player for if they would like to mortgage or unmortgage
	 * any properties. Possible variables changed - A properties' mortgage status,
	 * or a players' total money
	 */
	static void mortgageCheck() {
		if (engine.getUnmortgagedCount() > 0) {
			System.out.printf(
					"You own %d unmortgaged location(s). Would you like to mortgage any of them? Current player has $%d cash.%n",
					engine.getUnmortgagedCount(), engine.getCurrentPlayer().getMoney());
			if (askUserQuestion()) {
				for (int i = 0; i < Board.MAX_LOCATION; i++) {
					if (engine.board.board[i].getPlayerOwner() == engine.getCurrentPlayer() && !engine.board.board[i].isMortgaged()) {
						System.out.printf("Would you like to mortgage %s? Current player has $%d cash.%n",
								engine.board.board[i].getLocationName(), engine.getCurrentPlayer().getMoney());
						if (askUserQuestion()) {
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
			if (askUserQuestion()) {
				for (int i = 0; i < Board.MAX_LOCATION; i++) {
					if (engine.board.board[i].getPlayerOwner() == engine.getCurrentPlayer() && engine.board.board[i].isMortgaged()) {
						int unmortgageAmount = (engine.board.board[i].getMortgageValue()
								+ (int) (engine.board.board[i].getMortgageValue() / 10));
						System.out.printf("Would you like to unmortgage %s for $%d? Current player has $%d cash.",
								engine.board.board[i].getLocationName(), unmortgageAmount,engine.getCurrentPlayer().getMoney());
						if (askUserQuestion()) {
							if (engine.getCurrentPlayer().getMoney() < engine.getCurrentPlayerLocation().getPurchasePrice()) {
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
