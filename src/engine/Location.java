package engine;

public class Location {

	public enum Type {
		GO, PROPERTY, JAIL, GOTOJAIL, FREEPARKING
	};

	private String LocationName; /* holds a pointer to the strings name */
	private Type LocationType; /* holds a number representing the type of location */
	private int PurchasePrice; /* holds an amount of the price of purchase for the location */
	private int MortgageValue; /* holds an amount of cash given to the player when mortgaged */
	private boolean isMortgaged; /* is true if the the location has been mortgaged */
	private int RentValue; /* holds the amount of rent */
	private int PlayerOwner; /* holds the unique PlayerNumber of the owner of the location */
	private int LandingCount; /* holds the number of times a player has landed on this location */

	public Location() {
		LocationName = "";
		LocationType = Type.PROPERTY;
		PurchasePrice = 0;
		MortgageValue = 0;
		isMortgaged = false;
		RentValue = 0;
		PlayerOwner = -1;
		LandingCount = 0;
	}
	
	Location(String LocationNameInput, Type LocationTypeInput, int PurchasePriceInput, int MortgageValueInput, int RentValueInput) {
		LocationName = LocationNameInput;
		LocationType = LocationTypeInput;
		PurchasePrice = PurchasePriceInput;
		MortgageValue = MortgageValueInput;
		isMortgaged = false;
		RentValue = RentValueInput;
		PlayerOwner = -1;
		LandingCount = 0;
	}
	
	String GetLocationName() {
		return LocationName;
	}
	
	Type GetLocationType() {
		return LocationType;
	}
	
	int GetPurchasePrice() {
		return PurchasePrice + this.GetPopularityBonus(1);
	}
	
	int GetMortgageValue() {
		return MortgageValue + this.GetPopularityBonus(1);
	}
	
	int GetRentValue() {
		return RentValue + this.GetPopularityBonus(1);
	}
	
	int GetPopularityBonus(int modifier){
		return modifier*LandingCount;
	}
	
	int GetPlayerOwner() {
		return PlayerOwner;
	}
	
	void SetPlayerOwner(int Player) {
		PlayerOwner = Player;
	}
	
	boolean GetMortgageStatus() {
		return isMortgaged;
	}
	
	void ChangeMortgageProperty() {
		if (isMortgaged) {
			isMortgaged = false;
		}
		else {
			isMortgaged = true;
		}
	}
	
	void IncrementLandingCount(){
		LandingCount++;
	}
	
	int GetLandingCount(){
		return LandingCount;
	}



}
