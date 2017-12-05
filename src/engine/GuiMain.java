package engine;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GuiMain extends Application {
	
	static double screenWidth = Screen.getPrimary().getBounds().getWidth();				// width of user's screen
	static double screenHeight = Screen.getPrimary().getBounds().getHeight();
	static boolean shuffleLocations = false;
	static int diceMax = 6;
	static int startingAmount = 2000; 
	static int players = 2;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

    	Stage privateStage = new Stage();
		setConfiguration(privateStage);       
		/* if (shuffleLocations == false) {
				new Engine(players, Board.defaultBoard(), startingAmount, diceMax);
		} 
		else {
				new Engine(players, Board.randomBoard(), startingAmount, diceMax);
		} */
		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

		Scene scene = new Scene(root, 1024, 768);
		
		primaryStage.setTitle("Dynopoly");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	
	
	public static void setConfiguration(Stage privateStage){
		int rowTracker = 0;
		privateStage.setX(screenWidth/3);
		privateStage.setY(0);
		privateStage.setWidth(screenWidth/4);
		privateStage.setHeight(screenHeight/1.8);
		GridPane controller = new GridPane();
		controller.setVgap(10);
		controller.setHgap(20);

		controller.setAlignment(Pos.TOP_CENTER);
		
		Label verticalSpacer = new Label(" ");
		controller.add(verticalSpacer, 0, rowTracker++);
		Text title = new Text("Configuration Options");
		title.setFont(Font.font("Rosewood Std", FontWeight.NORMAL, 20));
		controller.add(title, 0, rowTracker++);
		
		Text textShuffle = new Text("Shuffle board locations?");
		textShuffle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
		controller.add(textShuffle, 0, rowTracker, 1, 1);

		Button buttonShuffle = new Button("Shuffle");
		
		HBox hBoxShuffle = new HBox();
		hBoxShuffle.setAlignment(Pos.BASELINE_LEFT);
	
		hBoxShuffle.getChildren().add(buttonShuffle);
		hBoxShuffle.setSpacing(10);
		
		buttonShuffle.setOnAction(new EventHandler <ActionEvent>(){
	
			@Override
			public void handle(ActionEvent event) {
					try{ 
						if(shuffleLocations){
							shuffleLocations = false;
						}else{
							shuffleLocations = true;
						}
						
					} catch(Exception e){
						
					}
					
				
			}
		});
		
		controller.add(hBoxShuffle, 1, rowTracker++, 1, 1);
		
		Text titleSetStartingAmount = new Text("Set New Starting Cash Value");
		titleSetStartingAmount.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
		final Label labelSetStartingAmount = new Label("Enter amount: ");	
		labelSetStartingAmount.setMinWidth(120);
		final TextField fieldSetStartingAmount = new TextField();
		fieldSetStartingAmount.setMaxWidth(120.0);
		Button buttonSetStartingAmount = new Button("Set");
		
		HBox hBoxSetStartingAmount = new HBox();
		hBoxSetStartingAmount.setAlignment(Pos.BASELINE_LEFT);
		hBoxSetStartingAmount.getChildren().add(labelSetStartingAmount);
		hBoxSetStartingAmount.getChildren().add(fieldSetStartingAmount);
		hBoxSetStartingAmount.getChildren().add(buttonSetStartingAmount);
		hBoxSetStartingAmount.setSpacing(10);

		buttonSetStartingAmount.setOnAction(new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				try{
					startingAmount = Integer.parseInt(fieldSetStartingAmount.getText());
					labelSetStartingAmount.setText("Starting amount changed.");
				} catch(Exception e){
					labelSetStartingAmount.setText("Not a number.");
				}
				System.out.println(fieldSetStartingAmount.getText());
			}
			
		});
		
		Label verticalSpacer2 = new Label(" ");
		controller.add(verticalSpacer2, 0, rowTracker++);
		controller.add(titleSetStartingAmount, 0, rowTracker++, 2, 1);
		controller.add(hBoxSetStartingAmount, 0, rowTracker++, 2, 1);
		
		Text titleSetPlayerNumber = new Text("Set Number of Players");
		titleSetPlayerNumber.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
		final Label labelSetPlayerNumber = new Label("How many players? ");	
		labelSetPlayerNumber.setMinWidth(120);
		final TextField fieldSetPlayerNumber = new TextField();
		fieldSetPlayerNumber.setMaxWidth(120.0);
		Button buttonSetPlayerNumber = new Button("Set");
		
		HBox hBoxSetPlayerNumber = new HBox();
		hBoxSetPlayerNumber.setAlignment(Pos.BASELINE_LEFT);
		hBoxSetPlayerNumber.getChildren().add(labelSetPlayerNumber);
		hBoxSetPlayerNumber.getChildren().add(fieldSetPlayerNumber);
		hBoxSetPlayerNumber.getChildren().add(buttonSetPlayerNumber);
		hBoxSetPlayerNumber.setSpacing(10);

		buttonSetPlayerNumber.setOnAction(new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				try{
					players = Integer.parseInt(fieldSetPlayerNumber.getText());
					labelSetPlayerNumber.setText("Player number changed.");
				} catch(Exception e){
					labelSetPlayerNumber.setText("Not a number.");
				}
				System.out.println(fieldSetPlayerNumber.getText());
			}
			
		});
		
		Label verticalSpacer3 = new Label(" ");
		controller.add(verticalSpacer3, 0, rowTracker++);
		controller.add(titleSetPlayerNumber, 0, rowTracker++, 2, 1);
		controller.add(hBoxSetPlayerNumber, 0, rowTracker++, 2, 1);
		
		Text titleMaxDice = new Text("Set Max Dice Roll");
		titleMaxDice.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
		
		Button buttonMaxDice = new Button(" - ");
		HBox hBoxButtonMaxDice = new HBox();
		hBoxButtonMaxDice.setAlignment(Pos.CENTER);
		hBoxButtonMaxDice.getChildren().add(buttonMaxDice);
		Button buttonIncrement = new Button(" + ");
		HBox hBoxButtonIncrement = new HBox();
	
		final Slider sliderMaxDice = new Slider();
		Label labelMaxDice = new Label("Max Dice Roll: ");				
		final TextField fieldMaxDice = new TextField(""+ diceMax);
		fieldMaxDice.setMaxWidth(50);
		
		Label verticalSpacer4 = new Label(" ");
		controller.add(verticalSpacer4, 0, rowTracker++);
		controller.add(titleMaxDice, 0, rowTracker++, 2, 1);
		
		sliderMaxDice.setMin(1);
		sliderMaxDice.setMax(200);
		sliderMaxDice.setValue(6);
		sliderMaxDice.setMajorTickUnit(20);
		sliderMaxDice.setBlockIncrement(1);
		sliderMaxDice.setShowTickLabels(false);
		sliderMaxDice.setShowTickMarks(true);
		sliderMaxDice.setSnapToTicks(false);
		hBoxButtonMaxDice.setSpacing(10);
		
		sliderMaxDice.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observableValue,
                Number oldValue, Number newValue) {
                diceMax = newValue.intValue();
            	fieldMaxDice.setText(""+ diceMax);
            }
        });
		
		hBoxButtonMaxDice.getChildren().add(sliderMaxDice);
		sliderMaxDice.setMinWidth(150);
		hBoxButtonMaxDice.getChildren().add(buttonIncrement);
		
		controller.add(hBoxButtonMaxDice, 0, rowTracker++, 2, 1);
		
		
		buttonMaxDice.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				if(diceMax >= 1){
					diceMax--;
				}
				
				try{
					
					sliderMaxDice.setValue(diceMax);
					fieldMaxDice.setText(""+diceMax);
				} catch(Exception e){
				}
				
				
		}});
		
		buttonIncrement.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent event) {
				if(diceMax <= 200){
					diceMax++;
				}
				
				
				try{
					sliderMaxDice.setValue(diceMax);
					fieldMaxDice.setText(""+diceMax);
				} catch(Exception e){
				}
		}});
		
		controller.add(labelMaxDice, 0, rowTracker);
		controller.add(fieldMaxDice, 1, rowTracker++);
		
		Scene privateStageScene = new Scene(controller, 500, 500);
		privateStage.setScene(privateStageScene);
		privateStage.showAndWait();
		
	}
}
