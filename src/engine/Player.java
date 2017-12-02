package engine;



public class Player {
	private static final int INITIAL_MONEY_AMOUNT = 1500; /* Initial amount for each player at the start of the game */

	private int playerNumber; /* holds the players unique number for identification */
	private int money; /* holds the players total money amount */
	private int location; /* holds the players current location */
	private boolean jailed; /* flag that specifies whether the player is in jail and hasn't paid the fine yet */
	private boolean playing; /* if a flag that is true if the player is still playing */
	
	public Player(){
		playerNumber = 0;
		money = INITIAL_MONEY_AMOUNT;
		location = Board.GO_LOCATION;
		jailed = false;
		playing = true;
	}

	
	void setPlayerNumber(int number) {
		playerNumber = number;
	}
	
	int getPlayerNumber() {
		return playerNumber;
	}
	
	void moveLocation(int locationsToMove) {
		if ((location + locationsToMove) > (Board.MAX_LOCATION - 1)) {
			changeMoney(200);
			location = (location + locationsToMove) % (Board.MAX_LOCATION);
		}
		else {
			location = location + locationsToMove;
		}
	}
	
	int getLocation() {
		return location;
	}
	
	void setLocation(int location) {
		this.location = location;
	}
	
	int getMoney() {
		return money;
	}
	
	void changeMoney(int amount) {
		money = money + amount;
		if (money < 0) {
			System.out.println(String.format("Player %d is out of the game!", getPlayerNumber()));
			playing = false;
		}
	}
	boolean isPlaying() {
		return playing;
	}
	boolean isJailed() {
		return jailed;
	}
	void changeJailStatus() {
		if (jailed == true) {
			jailed = false;
		}
		else {
			jailed = true;
		}
	}
}
