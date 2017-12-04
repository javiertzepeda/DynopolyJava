package engine;

public class Property extends Location {
	public Property(String string, int i, int j, int k) {
		super(string, i, j, k);
	}

	@Override public <R> R accept(LocationVisitor<R> v) {
		return v.visit(this);
	}
	
	@Override public String toString() {
		return this.getLocationName();
	}
}
