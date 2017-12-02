package engine;

public class LandingMessageVisitor implements LocationVisitor<String> {
	private Engine engine;

	public LandingMessageVisitor(Engine engine) {
		this.engine = engine;
	}

	@Override
	public String visit(Property property) {
		String result = String.format("Player %s moves %d spaces and lands on %s.", engine.getCurrentPlayer(),
				engine.die1 + engine.die2, engine.getCurrentPlayerLocation().getLocationName());
		Player currentLocationOwner = engine.getCurrentPlayerLocation().getPlayerOwner();
		if (currentLocationOwner == null) {
			result += String.format(
					"%nNo one owns this location.");
		} else if (currentLocationOwner == engine.getCurrentPlayer()) {
			result += String.format(
					"%nPlayer %s currently owns this location, and checks on all the employees to make sure they are happy.",
					engine.getCurrentPlayer().getName());
		} else if (!engine.getCurrentPlayerLocation().isMortgaged()) {
			result += String.format(
					"%nPlayer %s currently owns this location, and so player %s must pay the rent amount of $%d.",
					currentLocationOwner.getName(), engine.getCurrentPlayer().getName(),
					engine.getCurrentPlayerLocation().getRentValue());
		} else {
			result += String.format(
					"%nPlayer %s currently owns this location, but it is currently mortgaged so player %s does not have to pay rent.",
					currentLocationOwner.getName(), engine.getCurrentPlayer().getName());
		}
		return result;
	}

	@Override
	public String visit(GoToJail goToJail) {
		String result = String.format("Player %s moves %d spaces and lands on %s.", engine.getCurrentPlayer(),
				engine.die1 + engine.die2, engine.getCurrentPlayerLocation().getLocationName());
		for (int i = 0; i < Board.MAX_LOCATION; i++) {
			if (engine.board.board[i] instanceof Jail) {
				result += String.format("%nPlayer %s has been moved to jail.", engine.getCurrentPlayer().getName());
			}
		}
		return result;
	}

	@Override
	public String visit(Go go) {
		return String.format("Player %s moves %d spaces and lands on %s.", engine.getCurrentPlayer(),
				engine.die1 + engine.die2, engine.getCurrentPlayerLocation().getLocationName());
	}

	@Override
	public String visit(Jail jail) {
		return String.format("Player %s moves %d spaces and lands on %s.", engine.getCurrentPlayer(),
				engine.die1 + engine.die2, engine.getCurrentPlayerLocation().getLocationName());
	}

}
