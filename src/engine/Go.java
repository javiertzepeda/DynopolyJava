package engine;

public class Go extends Location {

	public Go(String string, int i, int j, int k) {
		super(string, i, j, k);
	}

	@Override
	public <R> R accept(LocationVisitor<R> v) {
		// TODO Auto-generated method stub
		return null;
	}

}
