package engine;



public class Player {
	private static final int GoLocation = 0;
	private static final int NumLocations = 26; /* Number of locations on the board */
	private static final int INITIALMONEYAMOUNT = 1500; /* Initial amount for each player at the start of the game */

	private int PlayerNumber; /* holds the players unique number for identification */
	private int money; /* holds the players total money amount */
	private int location; /* holds the players current location */
	private boolean isInJail; /* flag that specifies whether the player is in jail and hasn't paid the fine yet */
	private boolean isPlaying; /* if a flag that is true if the player is still playing */
	
	public Player(){
		PlayerNumber = 0;
		money = INITIALMONEYAMOUNT;
		location = GoLocation;
		isInJail = false;
		isPlaying = true;
	}

	
	void SetPlayerNumber(int Number) {
		PlayerNumber = Number;
	}
	
	int GetPlayerNumber() {
		return PlayerNumber;
	}
	
	void MoveLocation(int LocationsToMove) {
		if ((location + LocationsToMove) > (NumLocations - 1)) {
			ChangeMoney(200);
			location = (location + LocationsToMove) % (NumLocations);
		}
		else {
			location = location + LocationsToMove;
		}
	}
	
	int GetLocation() {
		return location;
	}
	
	void SetLocation(int Position) {
		location = Position;
	}
	
	int GetMoney() {
		return money;
	}
	
	void ChangeMoney(int Amount) {
		money = money + Amount;
		if (money < 0) {
			System.out.println(String.format("Player %d is out of the game!", GetPlayerNumber()));
			isPlaying = false;
		}
	}
	boolean PlayingStatus() {
		return isPlaying;
	}
	boolean GetJailStatus() {
		return isInJail;
	}
	void ChangeJailStatus() {
		if (isInJail == true) {
			isInJail = false;
		}
		else {
			isInJail = true;
		}
	}
}
