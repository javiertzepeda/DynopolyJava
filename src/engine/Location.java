package engine;

public class Location {

	public enum Type {
		GO, PROPERTY, JAIL, GOTOJAIL, FREEPARKING
	};

	private String locationName; /* holds a pointer to the strings name */
	private Type locationType; /* holds a number representing the type of location */
	private int purchasePrice; /* holds an amount of the price of purchase for the location */
	private int mortgageValue; /* holds an amount of cash given to the player when mortgaged */
	private boolean mortgaged; /* is true if the the location has been mortgaged */
	private int rentValue; /* holds the amount of rent */
	private int playerOwner; /* holds the unique PlayerNumber of the owner of the location */
	private int landingCount; /* holds the number of times a player has landed on this location */

	public Location() {
		locationName = "";
		locationType = Type.PROPERTY;
		purchasePrice = 0;
		mortgageValue = 0;
		mortgaged = false;
		rentValue = 0;
		playerOwner = -1;
		landingCount = 0;
	}
	
	Location(String locationName, Type locationType, int purchasePrice, int mortgageValue, int rentValue) {
		this.locationName = locationName;
		this.locationType = locationType;
		this.purchasePrice = purchasePrice;
		this.mortgageValue = mortgageValue;
		this.mortgaged = false;
		this.rentValue = rentValue;
		this.playerOwner = -1;
		this.landingCount = 0;
	}
	
	String getLocationName() {
		return locationName;
	}
	
	Type getLocationType() {
		return locationType;
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
	
	int getPlayerOwner() {
		return playerOwner;
	}
	
	void setPlayerOwner(int playerOwner) {
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



}
