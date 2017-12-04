package engine;

public class Jail extends Location {

	public Jail(String string, int i, int j, int k) {
		super(string, i, j, k);
	}

	@Override
	public <R> R accept(LocationVisitor<R> v) {
		return v.visit(this);
	}

}
