package engine;

public interface LocationVisitor<R> {
	public R visit(Property property);
	public R visit(GoToJail goToJail);
	public R visit(Go go);
	public R visit(Jail jail);
}
