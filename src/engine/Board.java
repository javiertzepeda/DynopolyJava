package engine;

public class Board {
	static final int GO_LOCATION = 0;
	static final int MAX_LOCATION = 26; /* Number of locations on the board */
	Location[] board; //contains location data

	private Board() {
		board = new Location[MAX_LOCATION];
	}
	
	public static Board defaultBoard() {
		Board board = new Board();
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
		return board;
	}
}
