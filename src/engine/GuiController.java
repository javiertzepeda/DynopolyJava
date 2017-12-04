package engine;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class GuiController {
	@FXML
	TextArea mainTextArea;
	@FXML
	ComboBox<Location> mortgageComboBox;
	@FXML
	ComboBox<Location> unmortgageComboBox;

	private Engine engine;

	@FXML
	public void initialize() {
		engine = new Engine(2, Board.defaultBoard());
		mainTextArea.appendText(String.format("It is player %s's turn.%n", engine.getCurrentPlayer()));
	}

	public void rollOnAction() {
		if (engine.canCurrentPlayerRoll()) {
			engine.rollDie();
			mainTextArea.appendText(String.format("Player %s has rolled a %d and a %d.%n", engine.getCurrentPlayer(),
					engine.die1, engine.die2));
			processLeaving();
			if (!engine.getCurrentPlayer().isJailed()) {
				engine.getCurrentPlayer().moveLocation(engine.die1 + engine.die2);
				engine.getCurrentPlayerLocation().incrementLandingCount();
				processLanding();
			}
		}
	}

	public void endTurnOnAction() {
		engine.nextPlayer();
		mortgageComboBox.getItems().clear();
		unmortgageComboBox.getItems().clear();
		mortgageComboBox.getItems().addAll(engine.getMortgagedProperties());
		unmortgageComboBox.getItems().addAll(engine.getUnmortgagedProperties());
		mainTextArea.appendText(String.format("It is player %s's turn.%n", engine.getCurrentPlayer()));
	}

	public void buyOnAction() {
		if (engine.getCurrentPlayerLocation() instanceof Property
				&& engine.getCurrentPlayerLocation().getPlayerOwner() == null) {
			if (engine.getCurrentPlayer().getMoney() < engine.getCurrentPlayerLocation().getPurchasePrice()) {
				mainTextArea
						.appendText(String.format("Player %s does not have enough money to purchase this property.%n",
								engine.getCurrentPlayer().getName()));
			} else {
				engine.getCurrentPlayerLocation().setPlayerOwner(engine.getCurrentPlayer());
				engine.getCurrentPlayer().changeMoney(-engine.getCurrentPlayerLocation().getPurchasePrice());
				mainTextArea.appendText(String.format("Player %s now owns %s%n", engine.getCurrentPlayer().getName(),
						engine.getCurrentPlayerLocation().getLocationName()));
				mortgageComboBox.getItems().add(engine.getCurrentPlayerLocation());
			}
		} else {
			mainTextArea.appendText("You cannot buy this space!" + System.lineSeparator());
		}
	}

	public void displayPriceOnAction() {
		if (engine.getCurrentPlayerLocation() instanceof Property) {
			mainTextArea
					.appendText(String.format("%s costs $%d.%n", engine.getCurrentPlayerLocation().getLocationName(),
							engine.getCurrentPlayerLocation().getPurchasePrice()));
		} else {
			mainTextArea.appendText("You cannot buy this space!" + System.lineSeparator());
		}
	}

	public void displayMoneyOnAction() {
		mainTextArea.appendText(String.format("Current player has $%d cash.%n", engine.getCurrentPlayer().getMoney()));
	}

	public void mortgageOnAction() {
		mortgageComboBox.getValue().changeMortgageProperty();
		mainTextArea.appendText(String.format("%s has been mortgaged.%n", mortgageComboBox.getValue()));
		engine.getCurrentPlayer().changeMoney(mortgageComboBox.getValue().getMortgageValue());
		unmortgageComboBox.getItems().add(mortgageComboBox.getValue());
		mortgageComboBox.getItems().remove(mortgageComboBox.getValue());
	}

	public void unmortgageOnAction() {
		int unmortgageAmount = (unmortgageComboBox.getValue().getMortgageValue()
				+ (int) (unmortgageComboBox.getValue().getMortgageValue() / 10));
		if (engine.getCurrentPlayer().getMoney() < engine.getCurrentPlayerLocation().getPurchasePrice()) {
			mainTextArea.appendText(String.format("Player %s does not have enough money to unmortgage this property.%n",
					engine.getCurrentPlayer().getName()));
		} else {
			unmortgageComboBox.getValue().changeMortgageProperty();
			mainTextArea.appendText(String.format("%s has been unmortgaged.%n", unmortgageComboBox.getValue()));
			engine.getCurrentPlayer().changeMoney(-unmortgageAmount);
			mortgageComboBox.getItems().add(unmortgageComboBox.getValue());
			unmortgageComboBox.getItems().remove(unmortgageComboBox.getValue());
		}
	}

	private void processLeaving() {
		LeavingVisitor lv = new LeavingVisitor(engine);
		LeavingMessageVisitor lmv = new LeavingMessageVisitor(engine);
		engine.getCurrentPlayerLocation().accept(lv);
		String message = engine.getCurrentPlayerLocation().accept(lmv);
		if (message != null) {
			mainTextArea.appendText(message);
			mainTextArea.appendText(System.lineSeparator());
		}
	}

	private void processLanding() {
		LandingVisitor lv = new LandingVisitor(engine);
		LandingMessageVisitor lmv = new LandingMessageVisitor(engine);
		engine.getCurrentPlayerLocation().accept(lv);
		mainTextArea.appendText(engine.getCurrentPlayerLocation().accept(lmv));
		mainTextArea.appendText(System.lineSeparator());
	}
}
