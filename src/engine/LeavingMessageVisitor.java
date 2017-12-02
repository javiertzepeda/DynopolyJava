package engine;

public class LeavingMessageVisitor implements LocationVisitor<String> {

	private Engine engine;
	
	public LeavingMessageVisitor(Engine engine) {
		this.engine = engine;
	}
	
	@Override
	public String visit(Property property) {
		return null;
	}

	@Override
	public String visit(GoToJail goToJail) {
		return null;
	}

	@Override
	public String visit(Go go) {
		return null;
	}

	@Override
	public String visit(Jail jail) {
		if (engine.getCurrentPlayer().isJailed()) {
			String result = String.format("Player %s is in jail.%n", engine.getCurrentPlayer());
			if (engine.die1 == engine.die2) {
				result += String.format("%nSince player %s has rolled doubles, they are now out of jail.", engine.getCurrentPlayer().getName());
			}
			return result;
		} else {
			return null;
		}
	}

}
