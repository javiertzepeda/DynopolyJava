package engine;

import static engine.Location.Type.FREEPARKING;
import static engine.Location.Type.GO;
import static engine.Location.Type.GOTOJAIL;
import static engine.Location.Type.JAIL;
import static engine.Location.Type.PROPERTY;

import java.util.Random;

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
	
		
		/* prompts user for number of players in the game */
		System.out.println("How many players? ");
		Engine.numberOfPlayers = Engine.in.nextInt(); Engine.in.nextLine();
		
	
		while (Engine.numberOfPlayers <= 1 || Engine.numberOfPlayers > Engine.MAX_PLAYERS) {
			System.out.println("Please choose a number from 2 through 10. How many players? ");
			Engine.numberOfPlayers = Integer.parseInt(Engine.in.next().trim());
		}
		/* populates PlayerDB with the number of players specified above */
		Engine.setupPlayers(Engine.numberOfPlayers);
	
		/* Sets up board */
		String loc0 = "Go";
		Board.board[0] = new Location(loc0, GO, 0, 0, 0);
		String loc1 = "Mediterranean Ave.";
		Board.board[1] = new Location(loc1, PROPERTY, 60, 50, 2);
		String loc2 = "Baltic Ave.";
		Board.board[2] = new Location(loc2, PROPERTY, 60, 50, 4);
		String loc3 = "Oriental Ave.";
		Board.board[3] = new Location(loc3, PROPERTY, 100, 50, 6);
		String loc4 = "Vermont Ave.";
		Board.board[4] = new Location(loc4, PROPERTY, 100, 50, 6);
		String loc5 = "Connecticut Ave.";
		Board.board[5] = new Location(loc5, PROPERTY, 120, 50, 8);
		String loc6 = "Jail";
		Board.board[6] = new Location(loc6, JAIL, 0, 0, 0);
		String loc7 = "St. Charles Place";
		Board.board[7] = new Location(loc7, PROPERTY, 140, 50, 10);
		String loc8 = "States Ave.";
		Board.board[8] = new Location(loc8, PROPERTY, 140, 0, 10);
		String loc9 = "Virginia Ave.";
		Board.board[9] = new Location(loc9, PROPERTY, 160, 50, 12);
		String loc10 = "St. James Place";
		Board.board[10] = new Location(loc10, PROPERTY, 180, 50, 14);
		String loc11 = "Tennessee Ave.";
		Board.board[11] = new Location(loc11, PROPERTY, 180, 50, 14);
		String loc12 = "New York Ave.";
		Board.board[12] = new Location(loc12, PROPERTY, 200, 50, 16);
		String loc13 = "Free Parking";
		Board.board[13] = new Location(loc13, FREEPARKING, 0, 0, 0);
		String loc14 = "Kentucky Ave.";
		Board.board[14] = new Location(loc14, PROPERTY, 220, 50, 18);
		String loc15 = "Indiana Ave.";
		Board.board[15] = new Location(loc15, PROPERTY, 220, 50, 18);
		String loc16 = "Illinois Ave.";
		Board.board[16] = new Location(loc16, PROPERTY, 240, 50, 20);
		String loc17 = "Atlantic Ave.";
		Board.board[17] = new Location(loc17, PROPERTY, 260, 50, 22);
		String loc18 = "Ventnor Ave.";
		Board.board[18] = new Location(loc18, PROPERTY, 260, 50, 22);
		String loc19 = "Marvin Gardens";
		Board.board[19] = new Location(loc19, PROPERTY, 280, 50, 24);
		String loc20 = "Go To Jail";
		Board.board[20] = new Location(loc20, GOTOJAIL, 0, 0, 0);
		String loc21 = "Pacific Ave.";
		Board.board[21] = new Location(loc21, PROPERTY, 300, 50, 26);
		String loc22 = "North Carolina Ave.";
		Board.board[22] = new Location(loc22, PROPERTY, 300, 50, 26);
		String loc23 = "Pennsylvania Ave.";
		Board.board[23] = new Location(loc23, PROPERTY, 320, 50, 28);
		String loc24 = "Park Place";
		Board.board[24] = new Location(loc24, PROPERTY, 350, 50, 35);
		String loc25 = "Boardwalk";
		Board.board[25] = new Location(loc25, PROPERTY, 400, 50, 50);
	
		/* Randomly choose a player to go first */
		Random rand = new Random();
		Engine.currentPlayer = (rand.nextInt(50) + 1) % Engine.numberOfPlayers;
		System.out.printf("Player %d has been randomly selected to go first.%n", Engine.currentPlayer+1);
	
		/* Infinitely loops game until only one player is left */
		while (!Engine.hasGameEnded()) {
			Engine.die1 = (rand.nextInt(50) + 1) % 6 + 1;
			Engine.die2 = (rand.nextInt(50) + 1) % 6 + 1;
			/* Slight delay between each player's turn */
			System.out.printf("It is player %d's turn.%n", Engine.currentPlayer + 1);
			//MortgageCheck(); /* Uncomment to ask player if they would like to mortgage/unmortgage before they roll */
			System.out.printf("Player %d has rolled a %d and a %d.%n", Engine.currentPlayer + 1, Engine.die1, Engine.die2);
			if (!Engine.isPlayerJailed()) { // Before moving player, checks to see if a Player is in jail and needs to pay a fine
				Engine.players[Engine.currentPlayer].moveLocation(Engine.die1 + Engine.die2); // Moves Player to new location, checks if they passed go (+$200)
				String currentPlayerLocation = Board.board[Engine.players[Engine.currentPlayer].getLocation()].getLocationName(); // gets string data for the location that CurrentPlayer moved to 
				Board.board[Engine.players[Engine.currentPlayer].getLocation()].incrementLandingCount(); // increase variable in location that stores the number of times a player has landed on it
				System.out.printf("Player %d moves %d spaces and lands on %s.%n", Engine.currentPlayer + 1, Engine.die1 + Engine.die2, currentPlayerLocation);
				Engine.landingOnCheck(); // Processes location to see if user landed on jail, on another player's property, an unpurchased property, or a property that doesn't require action
			}
			
			Engine.mortgageCheck(); // If applicable, asks the user if they would like to mortgage or unmortgage properties
	
			Engine.nextPlayer(); // Calculates next player and restarts loop
		}
		Engine.in.close();
	}

}
