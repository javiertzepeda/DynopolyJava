package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Engine {
	static final int MIN_PLAYERS = 2;
	static final int MAX_PLAYERS = 10;

	int die1 = 0;
	int die2 = 0;
	private int currentPlayer;
	private boolean currentPlayerRolled;
	private int maxDieNum = 6;
	private int startMoney = 1500;

	Player[] players;
	Board board;
	Random rand;

	public Engine(int numberOfPlayers, Board board, int startingMoney, int maxDie) {
		players = new Player[numberOfPlayers];
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(Integer.toString(i+1));
		}
		startMoney = startingMoney;
		maxDieNum = maxDie;
		for(int i = 0; i < numberOfPlayers; i++) {
			players[i].changeMoney(startMoney - 1500);
		}
		this.board = board;
		this.rand = new Random();
	}

	public boolean hasGameEnded() {
		int numberOfPlayersInGame = 0;
		for (int i = players.length; i > 0; i--) {
			if (players[i - 1].isPlaying()) {
				numberOfPlayersInGame++;
			}
		}
		return numberOfPlayersInGame == 1;
	}

	public void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % players.length;
		currentPlayerRolled = false;
	}

	public int getMortgagedCount() {
		int count = 0;
		for (int i = 0; i < Board.MAX_LOCATION; i++) {
			if (board.board[i].getPlayerOwner() == getCurrentPlayer() && board.board[i].isMortgaged()) {
				count++;
			}
		}
		return count;
	}

	public int getUnmortgagedCount() {
		int count = 0;
		for (int i = 0; i < Board.MAX_LOCATION; i++) {
			if (board.board[i].getPlayerOwner() == getCurrentPlayer() && !board.board[i].isMortgaged()) {
				count++;
			}
		}
		return count;
	}
	
	public List<Location> getMortgagedProperties() {
		ArrayList<Location> al = new ArrayList<>();
		for (int i = 0; i < Board.MAX_LOCATION; i++) {
			if (board.board[i].getPlayerOwner() == getCurrentPlayer() && board.board[i].isMortgaged()) {
				al.add(board.board[i]);
			}
		}
		return al;
	}
	
	public List<Location> getUnmortgagedProperties() {
		ArrayList<Location> al = new ArrayList<>();
		for (int i = 0; i < Board.MAX_LOCATION; i++) {
			if (board.board[i].getPlayerOwner() == getCurrentPlayer() && !board.board[i].isMortgaged()) {
				al.add(board.board[i]);
			}
		}
		return al;
	}

	public Player getCurrentPlayer() {
		return players[currentPlayer];
	}

	public Location getCurrentPlayerLocation() {
		return board.board[getCurrentPlayer().getLocation()];
	}

	public void rollDie() {
		die1 = (rand.nextInt(50) + 1) % maxDieNum + 1;
		die2 = (rand.nextInt(50) + 1) % maxDieNum + 1;
		currentPlayerRolled = true;
	}
	
	public void selectRandomPlayer() {
		currentPlayer = (rand.nextInt(50) + 1) % players.length;
	}
	
	public boolean canCurrentPlayerRoll() {
		return !currentPlayerRolled;
	}
}