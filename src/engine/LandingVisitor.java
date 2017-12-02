package engine;

public class LandingVisitor implements LocationVisitor<Boolean> {

	private Engine engine;
	
	public LandingVisitor(Engine engine) {
		this.engine = engine;
	}
	
	@Override
	public Boolean visit(Property p) {
		Player currentLocationOwner = engine.getCurrentPlayerLocation().getPlayerOwner();
		if (currentLocationOwner == null) {
			return false;
		} else if (!engine.getCurrentPlayerLocation().isMortgaged()) {
			int rentValue = engine.getCurrentPlayerLocation().getRentValue();
			engine.getCurrentPlayer().changeMoney(-1 * rentValue);
			currentLocationOwner.changeMoney(rentValue);
			return true;
		}
		return false;
	}

	@Override
	public Boolean visit(GoToJail goToJail) {
		for (int i = 0; i < Board.MAX_LOCATION; i++) {
			if (engine.board.board[i] instanceof Jail) {
				engine.getCurrentPlayer().changeJailStatus();
				engine.getCurrentPlayer().setLocation(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean visit(Go go) {
		return false;
	}

	@Override
	public Boolean visit(Jail jail) {
		return false;
	}

}
