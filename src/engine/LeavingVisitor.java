package engine;

public class LeavingVisitor implements LocationVisitor<Boolean> {

	private Engine engine;
	
	public LeavingVisitor(Engine engine) {
		this.engine = engine;
	}
	
	@Override
	public Boolean visit(Property property) {
		return false;
	}

	@Override
	public Boolean visit(GoToJail goToJail) {
		return false;
	}

	@Override
	public Boolean visit(Go go) {
		return false;
	}

	@Override
	public Boolean visit(Jail jail) {
		if (engine.getCurrentPlayer().isJailed() && engine.die1 == engine.die2) {
			engine.getCurrentPlayer().changeJailStatus();
			return true;
		} else {
			return false;
		}
	}

}
