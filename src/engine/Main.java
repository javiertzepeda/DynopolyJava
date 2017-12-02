package engine;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static engine.Location.Type.*;

public class Main{
	/* definition of Number of Locations and Maximum Number of Players, used for board creation */
	private static final int NumLocations  = 26;
	private static final int PlayerMax = 10;
	
	/* global variables */
	static int Die1 = 0;
	static int Die2 = 0;
	static int NumberPlayers;
	static int CurrentPlayer;
	
	static Player[] PlayerDB = new Player[PlayerMax]; //contains player data
	
	static Location[] LocationDB = new Location[NumLocations]; //contains location data
	
	/* Adds the number of players to PlayerDB as specified by the integer input
		Variables changed - PlayerDB */
	static void SetupPlayers(int NumberPlayers) {
		int NumberPlayersCreated = 1;
		while (NumberPlayers > 0) {
			PlayerDB[NumberPlayersCreated - 1] = new Player();
			PlayerDB[NumberPlayersCreated - 1].SetPlayerNumber(NumberPlayersCreated);
			System.out.println(String.format("Added player %d to the game. \n", NumberPlayersCreated));
			NumberPlayersCreated++;
			NumberPlayers--;
		}
	}
	
	/* Checks to see if atleast two players are still playing
		Variables changed - None */
	static boolean WinnerIfTrue() {
		int NumberOfPlayersInGame = 0;
		for (int i = NumberPlayers; i > 0; i--) {
			if (PlayerDB[i - 1].PlayingStatus()) {
				NumberOfPlayersInGame++;
			}
		}
		if (NumberOfPlayersInGame > 1) {
			return false;
		}
	    return true;
	}
	
	/* Calculates the next player
		Variables changed - CurrentPlayer */
	static void NextPlayer(){
		CurrentPlayer++;
		if (CurrentPlayer == NumberPlayers) {
			CurrentPlayer = 0;
		}
	}
	
	/* Waits for keyboard input of user for a Yes or No question and returns response
		Variables changed - None */
	static boolean AskUserQuestion() {
		char Answer = 'q';
		while (Answer != 'Y' && Answer != 'y' && Answer != 'N' && Answer != 'n') {
			Scanner reader = new Scanner(System.in);
			Answer = reader.next().trim().charAt(0);
			
			if (Answer != 'Y' && Answer != 'y' && Answer != 'N' && Answer != 'n' && Answer != 'q') {
				System.out.println("Please enter either Y/N.\n");
			}
		}
		if (Answer == 'Y' || Answer == 'y') {
			return true;
		}
		if (Answer == 'N' || Answer == 'n') {
			return false;
		}
	    return false;
	}
	
	/* Calculates if CurrentPlayer has any properties that can be mortgaged or unmortgaged and asks player
		for if they would like to mortgage or unmortgage any properties.
		Possible variables changed - A properties' mortgage status, or a players' total money*/
	static void MortgageCheck(){
		int UnMortgagedPropertiesCount = 0;
		int MortgagedPropertiesCount = 0;
		for (int i = 0; i < NumLocations; i++) {
			if (LocationDB[i].GetPlayerOwner() == CurrentPlayer && LocationDB[i].GetMortgageStatus() == false) {
				UnMortgagedPropertiesCount++;
			}
			if (LocationDB[i].GetPlayerOwner() == CurrentPlayer && LocationDB[i].GetMortgageStatus() == true) {
				MortgagedPropertiesCount++;
			}
		}
		if (UnMortgagedPropertiesCount > 0) {
			System.out.println(String.format("You own %d unmortgaged location(s). Would you like to mortgage any of them? Current player has $%d cash.\n", UnMortgagedPropertiesCount, PlayerDB[CurrentPlayer].GetMoney()));
			boolean answer = AskUserQuestion();
			if (answer == true) {
				for (int i = 0; i < NumLocations; i++) {
					if (LocationDB[i].GetPlayerOwner() == CurrentPlayer && LocationDB[i].GetMortgageStatus() == false) {
						System.out.println(String.format("Would you like to mortgage %s? Current player has $%d cash.\n", LocationDB[i].GetLocationName(), PlayerDB[CurrentPlayer].GetMoney()));
						boolean answer2 = AskUserQuestion();
						if (answer2 == true) {
							LocationDB[i].ChangeMortgageProperty();
							PlayerDB[CurrentPlayer].ChangeMoney(LocationDB[i].GetMortgageValue());
						}
					}
				}
			}
		}
		if (MortgagedPropertiesCount > 0) {
			System.out.println(String.format("You own %d mortgaged location(s). Would you like to unmortgage any of them? Current player has $%d cash.\n", MortgagedPropertiesCount, PlayerDB[CurrentPlayer].GetMoney()));
			boolean answer = AskUserQuestion();
			if (answer == true) {
				for (int i = 0; i < NumLocations; i++) {
					if (LocationDB[i].GetPlayerOwner() == CurrentPlayer && LocationDB[i].GetMortgageStatus() == true) {
						int UnMortgageAmount = (LocationDB[i].GetMortgageValue() + (int)(LocationDB[i].GetMortgageValue() / 10));
						System.out.println(String.format("Would you like to unmortgage %s for $%d? Current player has $%d cash.\n", LocationDB[i].GetLocationName(), UnMortgageAmount, PlayerDB[CurrentPlayer].GetMoney()));
						boolean answer2 = AskUserQuestion();
						if (answer2 == true) {
							if (PlayerDB[CurrentPlayer].GetMoney() < LocationDB[PlayerDB[CurrentPlayer].GetLocation()].GetPurchasePrice()) {
								System.out.println(String.format("Player %d does not have enough money to unmortgage this property.\n", CurrentPlayer + 1));
								return;
							}
							LocationDB[i].ChangeMortgageProperty();
							PlayerDB[CurrentPlayer].ChangeMoney(-1 * UnMortgageAmount);
						}
					}
				}
			}
		}
	}
	
	/* Checks whether CurrentPlayer is in Jail and hasn't paid and checks if they rolled doubles or want to pay to get out
		Possible variables changed - Jail Property player owner, players' total money, Array JailDB values */
	static boolean InJailCheck() {
		if (LocationDB[PlayerDB[CurrentPlayer].GetLocation()].GetLocationType() == JAIL && PlayerDB[CurrentPlayer].GetJailStatus()) {
			System.out.println(String.format("Player %d is in jail.\n", CurrentPlayer + 1));
			if (Die1 == Die2) {
				System.out.println(String.format("Since player %d has rolled doubles, they are now out of jail.\n", CurrentPlayer + 1));
				PlayerDB[CurrentPlayer].ChangeJailStatus();
				return true;
			}else if (PlayerDB[CurrentPlayer].GetMoney() > 50) {
				System.out.println(String.format("Would Player %d like to pay $50 and get out of jail? Current player has $%d cash.\n", CurrentPlayer + 1,PlayerDB[CurrentPlayer].GetMoney()));
				boolean answer = AskUserQuestion();
				if (answer == true) {
					PlayerDB[CurrentPlayer].ChangeMoney(-50);
					System.out.println(String.format("Player %d has paid the fine and is now out of jail.\n", CurrentPlayer + 1));
					PlayerDB[CurrentPlayer].ChangeJailStatus();
					return true;
				}
				else {
					System.out.println(String.format("Player %d will not pay the fine and will remain in jail.\n", CurrentPlayer + 1));
					return true;
				}
			}
			else {
				System.out.println(String.format("Player %d cannot pay the fine will remain in jail.\n", CurrentPlayer + 1));
				return true;
			}
		}
		else {
			return false;
		}
	}
	
	/* Checks current location of CurrentPlayer and prompts for actions if appropriate */
	static void LandingOnCheck() {
		if (LocationDB[PlayerDB[CurrentPlayer].GetLocation()].GetLocationType() == PROPERTY) {
			int CurrentLocationOwner = LocationDB[PlayerDB[CurrentPlayer].GetLocation()].GetPlayerOwner();
			if (CurrentLocationOwner == -1) {
				System.out.println(String.format("No one owns this location. Would player %d like to purchase it for $%d? Current player has $%d cash.\n", CurrentPlayer + 1, LocationDB[PlayerDB[CurrentPlayer].GetLocation()].GetPurchasePrice(), PlayerDB[CurrentPlayer].GetMoney()));
				MortgageCheck();
				boolean answer = AskUserQuestion();
				if (answer == true) {
					if (PlayerDB[CurrentPlayer].GetMoney() < LocationDB[PlayerDB[CurrentPlayer].GetLocation()].GetPurchasePrice()) {
						System.out.println(String.format("Player %d does not have enough money to purchase this property.", CurrentPlayer + 1));
						return;
					}
					LocationDB[PlayerDB[CurrentPlayer].GetLocation()].SetPlayerOwner(CurrentPlayer);
					PlayerDB[CurrentPlayer].ChangeMoney(-1 * LocationDB[PlayerDB[CurrentPlayer].GetLocation()].GetPurchasePrice());
					System.out.println(String.format("Player %d now owns %s\n", CurrentPlayer+1, LocationDB[PlayerDB[CurrentPlayer].GetLocation()].GetLocationName()));
					return;
				}
			}
			else if (CurrentLocationOwner == CurrentPlayer) {
				System.out.println(String.format("Player %d currently owns this location, and checks on all the employees to make sure they are happy.\n", CurrentPlayer+1));
				return;
			}
			else if (LocationDB[PlayerDB[CurrentPlayer].GetLocation()].GetMortgageStatus()==false){
				int RentValue = LocationDB[PlayerDB[CurrentPlayer].GetLocation()].GetRentValue();
				System.out.println(String.format("Player %d currently owns this location, and so player %d must pay the rent amount of $%d.\n", CurrentLocationOwner + 1, CurrentPlayer + 1, RentValue));
				PlayerDB[CurrentPlayer].ChangeMoney(-1*RentValue);
				PlayerDB[CurrentLocationOwner].ChangeMoney(RentValue);
				return;
			}
			else {
				System.out.println(String.format("Player %d currently owns this location, but it is currently mortgaged so player %d does not have to pay rent.\n", CurrentLocationOwner + 1, CurrentPlayer + 1));
			}
		}
		else if (LocationDB[PlayerDB[CurrentPlayer].GetLocation()].GetLocationType() == GOTOJAIL) {
			for (int i = 0; i < NumLocations; i++) {
				if (LocationDB[i].GetLocationType() == JAIL) {
					PlayerDB[CurrentPlayer].ChangeJailStatus();
					PlayerDB[CurrentPlayer].SetLocation(i);
					System.out.println(String.format("Player %d has been moved to jail.\n", CurrentPlayer + 1));
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
		NumberPlayers = Integer.parseInt(reader.next().trim());
		
	
		while (NumberPlayers <= 1 || NumberPlayers > PlayerMax) {
			System.out.println("Please choose a number from 2 through 10. How many players? ");
			NumberPlayers = Integer.parseInt(reader.next().trim());		
		}
		/* populates PlayerDB with the number of players specified above */
		SetupPlayers(NumberPlayers);
	
		/* Sets up board */
		String Loc0 = "Go";
		LocationDB[0] = new Location(Loc0, GO, 0, 0, 0);
		String Loc1 = "Mediterranean Ave.";
		LocationDB[1] = new Location(Loc1, PROPERTY, 60, 50, 2);
		String Loc2 = "Baltic Ave.";
		LocationDB[2] = new Location(Loc2, PROPERTY, 60, 50, 4);
		String Loc3 = "Oriental Ave.";
		LocationDB[3] = new Location(Loc3, PROPERTY, 100, 50, 6);
		String Loc4 = "Vermont Ave.";
		LocationDB[4] = new Location(Loc4, PROPERTY, 100, 50, 6);
		String Loc5 = "Connecticut Ave.";
		LocationDB[5] = new Location(Loc5, PROPERTY, 120, 50, 8);
		String Loc6 = "Jail";
		LocationDB[6] = new Location(Loc6, JAIL, 0, 0, 0);
		String Loc7 = "St. Charles Place";
		LocationDB[7] = new Location(Loc7, PROPERTY, 140, 50, 10);
		String Loc8 = "States Ave.";
		LocationDB[8] = new Location(Loc8, PROPERTY, 140, 0, 10);
		String Loc9 = "Virginia Ave.";
		LocationDB[9] = new Location(Loc9, PROPERTY, 160, 50, 12);
		String Loc10 = "St. James Place";
		LocationDB[10] = new Location(Loc10, PROPERTY, 180, 50, 14);
		String Loc11 = "Tennessee Ave.";
		LocationDB[11] = new Location(Loc11, PROPERTY, 180, 50, 14);
		String Loc12 = "New York Ave.";
		LocationDB[12] = new Location(Loc12, PROPERTY, 200, 50, 16);
		String Loc13 = "Free Parking";
		LocationDB[13] = new Location(Loc13, FREEPARKING, 0, 0, 0);
		String Loc14 = "Kentucky Ave.";
		LocationDB[14] = new Location(Loc14, PROPERTY, 220, 50, 18);
		String Loc15 = "Indiana Ave.";
		LocationDB[15] = new Location(Loc15, PROPERTY, 220, 50, 18);
		String Loc16 = "Illinois Ave.";
		LocationDB[16] = new Location(Loc16, PROPERTY, 240, 50, 20);
		String Loc17 = "Atlantic Ave.";
		LocationDB[17] = new Location(Loc17, PROPERTY, 260, 50, 22);
		String Loc18 = "Ventnor Ave.";
		LocationDB[18] = new Location(Loc18, PROPERTY, 260, 50, 22);
		String Loc19 = "Marvin Gardens";
		LocationDB[19] = new Location(Loc19, PROPERTY, 280, 50, 24);
		String Loc20 = "Go To Jail";
		LocationDB[20] = new Location(Loc20, GOTOJAIL, 0, 0, 0);
		String Loc21 = "Pacific Ave.";
		LocationDB[21] = new Location(Loc21, PROPERTY, 300, 50, 26);
		String Loc22 = "North Carolina Ave.";
		LocationDB[22] = new Location(Loc22, PROPERTY, 300, 50, 26);
		String Loc23 = "Pennsylvania Ave.";
		LocationDB[23] = new Location(Loc23, PROPERTY, 320, 50, 28);
		String Loc24 = "Park Place";
		LocationDB[24] = new Location(Loc24, PROPERTY, 350, 50, 35);
		String Loc25 = "Boardwalk";
		LocationDB[25] = new Location(Loc25, PROPERTY, 400, 50, 50);
	
		/* Randomly choose a player to go first */
		Random rand = new Random();
		CurrentPlayer = (rand.nextInt(50) + 1) % NumberPlayers; 
		System.out.println(String.format("Player %d has been randomly selected to go first.\n", CurrentPlayer+1));
	
		/* Infinitely loops game until only one player is left */
		while (WinnerIfTrue() == false) {
			Die1 = (rand.nextInt(50) + 1) % 6 + 1;
			Die2 = (rand.nextInt(50) + 1) % 6 + 1;
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				
			} /* Slight delay between each player's turn */
			System.out.println(String.format("It is player %d's turn.\n", CurrentPlayer + 1));
			//MortgageCheck(); /* Uncomment to ask player if they would like to mortgage/unmortgage before they roll */
			System.out.println(String.format("Player %d has rolled a %d and a %d.\n", CurrentPlayer + 1, Die1, Die2));
			if (InJailCheck() == false) { // Before moving player, checks to see if a Player is in jail and needs to pay a fine
				PlayerDB[CurrentPlayer].MoveLocation(Die1 + Die2); // Moves Player to new location, checks if they passed go (+$200)
				String CurrentPlayerLocation = LocationDB[PlayerDB[CurrentPlayer].GetLocation()].GetLocationName(); // gets string data for the location that CurrentPlayer moved to 
				LocationDB[PlayerDB[CurrentPlayer].GetLocation()].IncrementLandingCount(); // increase variable in location that stores the number of times a player has landed on it
				System.out.println(String.format("Player %d moves %d spaces and lands on %s.\n", CurrentPlayer + 1, Die1 + Die2, CurrentPlayerLocation));
				LandingOnCheck(); // Processes location to see if user landed on jail, on another player's property, an unpurchased property, or a property that doesn't require action
			}
			
			MortgageCheck(); // If applicable, asks the user if they would like to mortgage or unmortgage properties
	
			NextPlayer(); // Calculates next player and restarts loop
		}
		
	}
}



