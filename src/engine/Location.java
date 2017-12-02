package engine;

public abstract class Location {

	private String locationName; /* holds a pointer to the strings name */
	private int purchasePrice; /* holds an amount of the price of purchase for the location */
	private int mortgageValue; /* holds an amount of cash given to the player when mortgaged */
	private boolean mortgaged; /* is true if the the location has been mortgaged */
	private int rentValue; /* holds the amount of rent */
	private Player playerOwner; /* holds the unique PlayerNumber of the owner of the location */
	private int landingCount; /* holds the number of times a player has landed on this location */

	public Location() {
		this("",0,0,0);
	}
	
	public Location(String locationName, int purchasePrice, int mortgageValue, int rentValue) {
		this.locationName = locationName;
		this.purchasePrice = purchasePrice;
		this.mortgageValue = mortgageValue;
		this.mortgaged = false;
		this.rentValue = rentValue;
		this.landingCount = 0;
	}
	
	String getLocationName() {
		return locationName;
	}
	
	int getPurchasePrice() {
		return purchasePrice + this.getPopularityBonus(1);
	}
	
	int getMortgageValue() {
		return mortgageValue + this.getPopularityBonus(1);
	}
	
	int getRentValue() {
		return rentValue + this.getPopularityBonus(1);
	}
	
	int getPopularityBonus(int modifier){
		return modifier*landingCount;
	}
	
	Player getPlayerOwner() {
		return playerOwner;
	}
	
	void setPlayerOwner(Player playerOwner) {
		this.playerOwner = playerOwner;
	}
	
	boolean isMortgaged() {
		return mortgaged;
	}
	
	void changeMortgageProperty() {
		if (mortgaged) {
			mortgaged = false;
		}
		else {
			mortgaged = true;
		}
	}
	
	void incrementLandingCount(){
		landingCount++;
	}
	
	int getLandingCount(){
		return landingCount;
	}

	// TODO Make abstract
	public abstract <R> R accept(LocationVisitor<R> v);

}
