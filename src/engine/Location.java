package engine;

public class Location {
	private String LocationName; /* holds a pointer to the strings name */
	private int LocationType; /* holds a number representing the type of location */
	private int PurchasePrice; /* holds an amount of the price of purchase for the location */
	private int MortgageValue; /* holds an amount of cash given to the player when mortgaged */
	private boolean isMortgaged; /* is true if the the location has been mortgaged */
	private int RentValue; /* holds the amount of rent */
	private int PlayerOwner; /* holds the unique PlayerNumber of the owner of the location */

	public Location() {
		LocationName = "";
		LocationType = 0;
		PurchasePrice = 0;
		MortgageValue = 0;
		isMortgaged = false;
		RentValue = 0;
		PlayerOwner = -1;
	}
	
	Location(String LocationNameInput, int LocationTypeInput, int PurchasePriceInput, int MortgageValueInput, int RentValueInput) {
		LocationName = LocationNameInput;
		LocationType = LocationTypeInput;
		PurchasePrice = PurchasePriceInput;
		MortgageValue = MortgageValueInput;
		isMortgaged = false;
		RentValue = RentValueInput;
		PlayerOwner = -1;
	}
	
	String GetLocationName() {
		return LocationName;
	}
	
	int GetLocationType() {
		return LocationType;
	}
	
	int GetPurchasePrice() {
		return PurchasePrice;
	}
	
	int GetMortgageValue() {
		return MortgageValue;
	}
	
	int GetRentValue() {
		return RentValue;
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



}
