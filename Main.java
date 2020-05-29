import com.interactivemesh.jfx.importer.ImportException;

import java.awt.Desktop;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Stack;

import java.util.concurrent.TimeUnit;

import javafx.application.Application;

import javafx.event.EventHandler; 

import javafx.scene.Scene;
import javafx.scene.Group;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.effect.Glow;

import javafx.scene.image.ImageView;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

import javafx.scene.shape.Circle;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import javafx.stage.Stage;

public class Main extends Application
{
    DeckGenerator deckGenerator = new DeckGenerator();

    static int numberOfPlayers;
    static int activePlayer;

    static int marketDiamond;
    static int marketSapphire;
    static int marketEmerald;
    static int marketRuby;
    static int marketOnyx;
    static int marketGold = 5;
    
    String player1Name = null;
    String player2Name = null;
    String player3Name = null;
    String player4Name = null;
    
    TextField player1NameField = new TextField();
    TextField player2NameField = new TextField();
    TextField player3NameField = new TextField();
    TextField player4NameField = new TextField();
    
    static Stage mainStage;
    static Scene mainScene;
    static Group root;
    
    ImageView level1DeckBack;
    ImageView level2DeckBack;
    ImageView level3DeckBack;
    
    static Group winnerRoot;
    static Scene winnerScene;
    static Text victorText = new Text();
    
    GemModels gemModels = new GemModels();
    static GridPane marketGemDisplay;
    
    static Button mainMenuButton;
    
    Scene mainMenu;
    
    static Noble[] noblesMarket;
    static Stack<Noble> nobleDeck;
    static Stack<Card> level1Deck = new Stack<Card>();
    static Stack<Card> level2Deck = new Stack<Card>();
    static Stack<Card> level3Deck = new Stack<Card>();
  
    Card[] level1Market;
    Card[] level2Market;
    Card[] level3Market;
    
    HBox level1Available;
    HBox level2Available;
    HBox level3Available;
    
    static GemType gem1Type;
    static GemType gem2Type;
    static GemType gem3Type;
    
    static Group player1Group;
    static Group player2Group;
    static Group player3Group;
    static Group player4Group;
    
    Button namesDone;
    Text loadingWarning = new Text();
    
    static Text turnMarker;
    static Text instructions;
    static Text optionText;

    static Player[] players;   

    /**
     * Initializes and shows the main stage
     */
    public void start(Stage stage){                           
        mainStage = stage;
        
        //create the instruction display
        Rectangle toolTipDisplay = new Rectangle(140, 275);        
        toolTipDisplay.setArcHeight(10);
        toolTipDisplay.setArcWidth(10);
        toolTipDisplay.setStroke(Color.WHITE);
        toolTipDisplay.setStrokeWidth(3);
        toolTipDisplay.setFill(Color.DIMGREY);
        
        turnMarker = new Text();
        turnMarker.setFont(Font.font("Verdana", 20));
        turnMarker.setTextAlignment(TextAlignment.CENTER);
        turnMarker.setFill(Color.WHITE);
        turnMarker.setTranslateX(25);
        turnMarker.setTranslateY(20);
        
        Text turnText = new Text("Turn");
        turnText.setFont(Font.font("Verdana", 18));
        turnText.setTextAlignment(TextAlignment.CENTER);
        turnText.setFill(Color.WHITE);
        turnText.setTranslateX(45);
        turnText.setTranslateY(40);
        
        //creates instructions
        instructions = new Text("Choose from one of\n the following actions:");
        instructions.setFont(Font.font("Verdana", 12));
        instructions.setTextAlignment(TextAlignment.CENTER);
        instructions.setFill(Color.WHITE);
        instructions.setTranslateX(3);
        instructions.setTranslateY(60);
        
        optionText = new Text("1. Take 3 gems of\n different types\n\n" +
                                   "2. Take 2 gems of the\n same type\n" +
                                   " (Must be 4 tokens of\n that type)\n\n" +
                                   "3. Reserve a card and\n take a Gold\n\n" +
                                   "4. Purchase a card");
        optionText.setFont(Font.font("Verdana", 12));
        optionText.setTextAlignment(TextAlignment.CENTER);
        optionText.setFill(Color.WHITE);
        optionText.setTranslateX(3);
        optionText.setTranslateY(95);
        
        Group toolTipSection = new Group(toolTipDisplay, turnMarker, turnText,
                                         instructions, optionText);
        toolTipSection.setTranslateX(935);
        toolTipSection.setTranslateY(110); 

        //arrays for the market decks
        level1Market = new Card[5];
        level2Market = new Card[5];
        level3Market = new Card[5];

        //displays for the market cards
        level1Available = new HBox();       
        level1Available.setSpacing(5);

        level2Available = new HBox();       
        level2Available.setSpacing(5);

        level3Available = new HBox();       
        level3Available.setSpacing(5);

        VBox availableMarketCards = new VBox(level1Available, level2Available, level3Available);
        availableMarketCards.setTranslateX(245);
        availableMarketCards.setTranslateY(108);
        availableMarketCards.setSpacing(3);     

        //creates a display for the gems available from the market
        marketGemDisplay = new GridPane();
        marketGemDisplay.setTranslateX(945);
        marketGemDisplay.setTranslateY(400);
        marketGemDisplay.setHgap(20);
        marketGemDisplay.setVgap(10);

        //populates the gem display
        for (int i = 0; i < 6; i++){        
            PhongMaterial gemMat = new PhongMaterial();
            Group gemModelGroup = new Group();
            
            //gets the gem model
            try {
                switch(i){
                    case 0:
                    gemModelGroup = gemModels.getGem(GemType.DIAMOND);
                    break;
                    case 1:
                    gemModelGroup = gemModels.getGem(GemType.SAPPHIRE);
                    break;
                    case 2:
                    gemModelGroup = gemModels.getGem(GemType.EMERALD);
                    break;
                    case 3:
                    gemModelGroup = gemModels.getGem(GemType.RUBY);
                    break;
                    case 4:
                    gemModelGroup = gemModels.getGem(GemType.ONYX);
                    break;
                    case 5:
                    gemModelGroup = gemModels.getGem(GemType.GOLD);
                    break;
                }
            }
            catch (ImportException e) {
                e.printStackTrace();
            }

            //applies functionality to the model such that the gem is
            //added to the player's inventory when it is clicked on.
            switch(i){
                case 0:
                gemModelGroup.setOnMousePressed( new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event){
                            if (marketDiamond == 1){                        
                                int success = takeGem(GemType.DIAMOND);  
                                updateGemVisuals();
                                if (success == 2){
                                    nextTurn();
                                }
                            }
                            else if(marketDiamond > 1){
                                int success = takeGem(GemType.DIAMOND);
                                if (success == 2){
                                    nextTurn();
                                }
                            }
                            else {
                                System.out.println("No more gems of that type");
                            }
                        }
                    });
                break;
                case 1:
                gemModelGroup.setOnMousePressed( new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event){
                            if (marketSapphire == 1){                        
                                int success = takeGem(GemType.SAPPHIRE);  
                                updateGemVisuals();
                                if (success == 2){
                                    nextTurn();
                                }
                            }
                            else if(marketSapphire > 1){
                                int success = takeGem(GemType.SAPPHIRE);
                                if (success == 2){
                                    nextTurn();
                                }
                            }
                            else {
                                System.out.println("No more gems of that type");
                            }
                        }
                    });
                break;
                case 2:
                gemModelGroup.setOnMousePressed( new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event){
                            if (marketEmerald == 1){                        
                                int success = takeGem(GemType.EMERALD);  
                                updateGemVisuals();
                                if (success == 2){
                                    nextTurn();
                                }
                            }
                            else if(marketEmerald > 1){
                                int success = takeGem(GemType.EMERALD);
                                if (success == 2){
                                    nextTurn();
                                }
                            }
                            else {
                                System.out.println("No more gems of that type");
                            }
                        }
                    });
                break;
                case 3:
                gemModelGroup.setOnMousePressed( new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event){
                            if (marketRuby == 1){                        
                                int success = takeGem(GemType.RUBY);  
                                updateGemVisuals();
                                if (success == 2){
                                    nextTurn();
                                }
                            }
                            else if(marketRuby > 1){
                                int success = takeGem(GemType.RUBY);
                                if (success == 2){
                                    nextTurn();
                                }
                            }
                            else {
                                System.out.println("No more gems of that type");
                            }
                        }
                    });
                break;
                case 4:
                gemModelGroup.setOnMousePressed( new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event){
                            if (marketOnyx == 1){                        
                                int success = takeGem(GemType.ONYX);  
                                updateGemVisuals();
                                if (success == 2){
                                    nextTurn();
                                }
                            }
                            else if(marketOnyx > 1){
                                int success = takeGem(GemType.ONYX);
                                if (success == 2){
                                    nextTurn();
                                }
                            }
                            else {
                                System.out.println("No more gems of that type");
                            }
                        }
                    });
                break;
                case 5:
                gemModelGroup.setOnMousePressed( new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent event){
                           System.out.println("Must reserve a card first");
                        }
                    });
                break;
            }

            //sets the row and adds the model to the display
            int row;
            if(i < 2)
                row = 0;
            else if (i < 4) 
                row = 1;
            else
                row = 2;
            marketGemDisplay.add(gemModelGroup, i % 2, row);    
        }

        //creates the image for the deck backs and applies functionality
        //such that clicking on a deck back reserves the top card in that deck.
        //Also adds a gold token to the player's inventory if it has space and
        //there is a gem token in the market to be given.
        level1DeckBack = new ImageView("./Resources/CardBack_1.png");
        level1DeckBack.setOnMousePressed( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                    if(gem1Type == null){ //ensures play has not started selecting gems
                    int success = players[activePlayer].addCardToHand(level1Deck.peek());
                    if (success == 1){
                        level1Deck.pop();
                         if (marketGold == 1 && players[activePlayer].getTotalGems() < 10){                        
                                marketGold--;
                                updateGemVisuals();
                                players[activePlayer].addGem(GemType.GOLD);                        
                            }
                            else if(marketGold > 1 && players[activePlayer].getTotalGems() < 10){
                                marketGold--;
                                players[activePlayer].addGem(GemType.GOLD);
                            }
                            else if(players[activePlayer].getTotalGems() == 10){
                                System.out.println("Inventory full");
                            }
                            else {
                                System.out.println("No more gems of that type");
                            }
                        //advance to next player
                        nextTurn();
                    }
                    else {
                        instructions.setText("\n\n   Your hand is full");
                        optionText.setText("");
                    }
                }
            }
            });
        level2DeckBack = new ImageView("./Resources/CardBack_2.png");
        level2DeckBack.setOnMousePressed( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                    if(gem1Type == null){ //ensures play has not started selecting gems
                    int success = players[activePlayer].addCardToHand(level2Deck.peek());
                    if (success == 1){
                        level2Deck.pop();
                         if (marketGold == 1 && players[activePlayer].getTotalGems() < 10){                        
                                marketGold--;
                                updateGemVisuals();
                                players[activePlayer].addGem(GemType.GOLD);                        
                            }
                            else if(marketGold > 1 && players[activePlayer].getTotalGems() < 10){
                                marketGold--;
                                players[activePlayer].addGem(GemType.GOLD);
                            }
                            else if(players[activePlayer].getTotalGems() == 10){
                             System.out.println("Inventory full");   
                            }
                            else {
                                System.out.println("No more gems of that type");
                            }
                        //advance to next player
                        nextTurn();
                    }
                    else {
                        instructions.setText("\n\n   Your hand is full");
                        optionText.setText("");
                    }
                }
                }
            });
        level3DeckBack = new ImageView("./Resources/CardBack_3.png");
        level3DeckBack.setOnMousePressed( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                    if(gem1Type == null){ //ensures play has not started selecting gems
                    int success = players[activePlayer].addCardToHand(level3Deck.peek());
                    if (success == 1){
                        level3Deck.pop();
                         if (marketGold == 1 && players[activePlayer].getTotalGems() < 10){                        
                                marketGold--;
                                updateGemVisuals();
                                players[activePlayer].addGem(GemType.GOLD);                        
                            }
                            else if(marketGold > 1 && players[activePlayer].getTotalGems() < 10){
                                marketGold--;
                                players[activePlayer].addGem(GemType.GOLD);
                            }
                            else if (players[activePlayer].getTotalGems() == 10){
                                System.out.println("Inventory full");
                            }
                            else {
                                System.out.println("No more gems of that type");
                            }
                        //advance to next player
                        nextTurn();
                    }
                    else {
                        instructions.setText("\n\n   Your hand is full");
                        optionText.setText("");
                    }
                }
            }
            });

        VBox marketDecks = new VBox(level1DeckBack, level2DeckBack, level3DeckBack);
        marketDecks.setTranslateX(130);
        marketDecks.setTranslateY(110);
        marketDecks.setSpacing(5);        

        root = new Group(marketDecks, availableMarketCards,
            toolTipSection, marketGemDisplay);

        //sets up the main scene.
        mainScene = new Scene(root, 1200, 650);
        mainScene.setFill(Color.GREEN);
        
        //Online Setup scene setup
        Group onlineRoot = new Group();
        Scene onlineSetup = new Scene(onlineRoot, 1200, 650);
        onlineSetup.setFill(Color.GREEN);
        
        //PLAYER SELECTION SETUP
        Group playerSelectionRoot = new Group();
        Scene playerSelection = new Scene(playerSelectionRoot, 1200, 650);
        playerSelection.setFill(Color.GREEN);
        
        //MAIN MENU
        ImageView mainLogo = new ImageView("./Resources/logo-Splendor.png");
        mainLogo.setScaleX(0.5);
        mainLogo.setScaleY(0.5);
        mainLogo.setTranslateX(-550);
        mainLogo.setTranslateY(-250);
               
        //select type of game buttons
        Button singlePlayer = new Button();
        singlePlayer.setFocusTraversable(false);
        singlePlayer.setText("Single Player");
        singlePlayer.setScaleX(1.42);
        singlePlayer.setScaleY(1.25);
        singlePlayer.setTranslateX(35);
        singlePlayer.setFont(Font.font("Verdana", 20));
        singlePlayer.setTextAlignment(TextAlignment.CENTER);
        singlePlayer.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                    System.out.println("Coming Soon");
                }
            }); 

        
        Button multiplayerLocal = new Button();
        multiplayerLocal.setFocusTraversable(false);
        multiplayerLocal.setText("Multiplayer (Local)");
        multiplayerLocal.setScaleX(1.05);
        multiplayerLocal.setScaleY(1.25);
        multiplayerLocal.setTranslateX(6);
        multiplayerLocal.setFont(Font.font("Verdana", 20));
        multiplayerLocal.setTextAlignment(TextAlignment.CENTER);
        multiplayerLocal.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                    stage.setScene(playerSelection);
                }
            }); 
        
        Button multiplayerOnline = new Button();
        multiplayerOnline.setFocusTraversable(false);
        multiplayerOnline.setText("Multiplayer (Online)");
        multiplayerOnline.setScaleY(1.25);
        multiplayerOnline.setFont(Font.font("Verdana", 20));
        multiplayerOnline.setTextAlignment(TextAlignment.CENTER);
        multiplayerOnline.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                   stage.setScene(onlineSetup);
                }
            }); 
        
        VBox mainMenuOptions = new VBox(singlePlayer, multiplayerLocal, multiplayerOnline);
        mainMenuOptions.setSpacing(40);
        mainMenuOptions.setTranslateX(500);
        mainMenuOptions.setTranslateY(350);
        
        //control buttons
        Button mainMenuHelp = new Button("HELP");
        mainMenuHelp.setTextAlignment(TextAlignment.CENTER);
        mainMenuHelp.setFocusTraversable(false);
        mainMenuHelp.setScaleX(2.5);
        mainMenuHelp.setScaleY(1.75);
        mainMenuHelp.setTranslateX(25);
        mainMenuHelp.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //Opens the instruction manual pdf through your Desktop's
                    //default program for opening a pdf
                           if (Desktop.isDesktopSupported()) {
                    try {
                    Desktop.getDesktop().open(new File("./Resources/Instructions.pdf"));
                    } catch (Exception e) {
                    e.printStackTrace();
                    }
                    }

                }
            }); 

        Button mainMenuExit = new Button("EXIT");
        mainMenuExit.setTextAlignment(TextAlignment.CENTER);
        mainMenuExit.setFocusTraversable(false);
        mainMenuExit.setScaleX(2.75);
        mainMenuExit.setScaleY(1.75);
        mainMenuExit.setTranslateX(27);
        mainMenuExit.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                    System.exit(0);
                }
            }); 

        VBox mainMacroButtons = new VBox(mainMenuHelp, mainMenuExit);
        mainMacroButtons.setSpacing(22);
        mainMacroButtons.setTranslateX(13);
        mainMacroButtons.setTranslateY(565);
        
        Group mainMenuRoot = new Group(mainLogo, mainMenuOptions, mainMacroButtons);
        mainMenu = new Scene(mainMenuRoot, 1200, 650);
        mainMenu.setFill(Color.GREEN);
        
        //MULTIPLAYER ONLINE
        ImageView onlineScreenLogo = new ImageView("./Resources/logo-Splendor.png");
        onlineScreenLogo.setScaleX(0.5);
        onlineScreenLogo.setScaleY(0.5);
        onlineScreenLogo.setTranslateX(-550);
        onlineScreenLogo.setTranslateY(-250);
        
        Button joinGame = new Button();
        joinGame.setFocusTraversable(false);
        joinGame.setText("Join Game");
        joinGame.setScaleX(1.05);
        joinGame.setScaleY(1.25);
        joinGame.setTranslateX(6);
        joinGame.setFont(Font.font("Verdana", 20));
        joinGame.setTextAlignment(TextAlignment.CENTER);
        joinGame.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                    
                }
            }); 
        
        Button hostGame = new Button();
        hostGame.setFocusTraversable(false);
        hostGame.setText("Multiplayer (Online)");
        hostGame.setScaleY(1.25);
        hostGame.setFont(Font.font("Verdana", 20));
        hostGame.setTextAlignment(TextAlignment.CENTER);
        hostGame.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                    
                }
            }); 
        
        VBox onlineOptions = new VBox(joinGame, hostGame);
        onlineOptions.setSpacing(40);
        onlineOptions.setTranslateX(500);
        onlineOptions.setTranslateY(350);
        
        //control buttons
        Button onlineHelp = new Button("HELP");
        onlineHelp.setTextAlignment(TextAlignment.CENTER);
        onlineHelp.setFocusTraversable(false);
        onlineHelp.setScaleX(2.5);
        onlineHelp.setScaleY(1.75);
        onlineHelp.setTranslateX(25);
        onlineHelp.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //Opens the instruction manual pdf through your Desktop's
                    //default program for opening a pdf
                           if (Desktop.isDesktopSupported()) {
                    try {
                    Desktop.getDesktop().open(new File("./Resources/Instructions.pdf"));
                    } catch (Exception e) {
                    e.printStackTrace();
                    }
                    }

                }
            }); 
            
        Button onlineBack = new Button("BACK");
        onlineBack.setTextAlignment(TextAlignment.CENTER);
        onlineBack.setFocusTraversable(false);
        onlineBack.setScaleX(2.35);
        onlineBack.setScaleY(1.75);
        onlineBack.setTranslateX(24);
        onlineBack.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                    stage.setScene(mainMenu);
                }
            }); 

        VBox onlineMacroButtons = new VBox(onlineHelp, onlineBack);
        onlineMacroButtons.setSpacing(22);
        onlineMacroButtons.setTranslateX(13);
        onlineMacroButtons.setTranslateY(565);
        
        onlineRoot.getChildren().addAll(onlineScreenLogo, onlineOptions, onlineMacroButtons);
        
        //WINNER SCREEN
        ImageView winnerScreenLogo = new ImageView("./Resources/logo-Splendor.png");
        winnerScreenLogo.setScaleX(0.5);
        winnerScreenLogo.setScaleY(0.5);
        winnerScreenLogo.setTranslateX(-550);
        winnerScreenLogo.setTranslateY(-250);
        
        mainMenuButton = new Button("Main Menu");
        mainMenuButton.setTextAlignment(TextAlignment.CENTER);
        mainMenuButton.setScaleX(2);
        mainMenuButton.setScaleY(2);
        mainMenuButton.setTranslateX(580);
        mainMenuButton.setTranslateY(500);
        mainMenuButton.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                    stage.setScene(mainMenu);
                }
            }); 
        
        winnerRoot = new Group(mainMenuButton, winnerScreenLogo);
        winnerScene = new Scene(winnerRoot, 1200, 650);
        winnerScene.setFill(Color.GREEN);
            
        //PLAYER SELECTION
        ImageView playerSelectionLogo = new ImageView("./Resources/logo-Splendor.png");
        playerSelectionLogo.setScaleX(0.5);
        playerSelectionLogo.setScaleY(0.5);
        playerSelectionLogo.setTranslateX(-550);
        playerSelectionLogo.setTranslateY(-250);
        
        Button playerSelectionHelp = new Button("HELP");
        playerSelectionHelp.setTextAlignment(TextAlignment.CENTER);
        playerSelectionHelp.setFocusTraversable(false);
        playerSelectionHelp.setScaleX(2.5);
        playerSelectionHelp.setScaleY(1.75);
        playerSelectionHelp.setTranslateX(25);
        playerSelectionHelp.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //Opens the instruction manual pdf through your Desktop's
                    //default program for opening a pdf
                           if (Desktop.isDesktopSupported()) {
                    try {
                    Desktop.getDesktop().open(new File("./Resources/Instructions.pdf"));
                    } catch (Exception e) {
                    e.printStackTrace();
                    }
                    }

                }
            }); 

        Button playerSelectionBack = new Button("BACK");
        playerSelectionBack.setTextAlignment(TextAlignment.CENTER);
        playerSelectionBack.setFocusTraversable(false);
        playerSelectionBack.setScaleX(2.35);
        playerSelectionBack.setScaleY(1.75);
        playerSelectionBack.setTranslateX(24);
        playerSelectionBack.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                    stage.setScene(mainMenu);
                }
            }); 

        VBox playerSelectionMacroButtons = new VBox(playerSelectionHelp, playerSelectionBack);
        playerSelectionMacroButtons.setSpacing(22);
        playerSelectionMacroButtons.setTranslateX(13);
        playerSelectionMacroButtons.setTranslateY(565);
        
        //buttons to select the number of players
        Button twoPlayer = new Button();
        twoPlayer.setFocusTraversable(false);
        twoPlayer.setText("2 Players");
        twoPlayer.setScaleX(1.9);
        twoPlayer.setScaleY(1.25);
        twoPlayer.setTranslateX(56);
        twoPlayer.setFont(Font.font("Verdana", 20));
        twoPlayer.setTextAlignment(TextAlignment.CENTER);
        twoPlayer.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    numberOfPlayers = 2;
                    try{
                    enterPlayerNames(2);
                }
                catch(IOException e){
                e.printStackTrace();
            }
                }
            }); 
      
        Button threePlayer = new Button();
        threePlayer.setFocusTraversable(false);
        threePlayer.setText("3 Players");
        threePlayer.setScaleX(1.88);
        threePlayer.setScaleY(1.25);
        threePlayer.setTranslateX(55);
        threePlayer.setFont(Font.font("Verdana", 20));
        threePlayer.setTextAlignment(TextAlignment.CENTER);
        threePlayer.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                    numberOfPlayers = 3;
                    try{
                    enterPlayerNames(3);
                }
                catch(IOException e){
                e.printStackTrace();
            }
                }
            }); 
        
        Button fourPlayer = new Button();
        fourPlayer.setFocusTraversable(false);
        fourPlayer.setText("4 Players");
        fourPlayer.setScaleX(1.90);
        fourPlayer.setScaleY(1.25);
        fourPlayer.setTranslateX(54);
        fourPlayer.setFont(Font.font("Verdana", 20));
        fourPlayer.setTextAlignment(TextAlignment.CENTER);
        fourPlayer.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                    numberOfPlayers = 4;
                    try{
                    enterPlayerNames(4);
                }
                catch(IOException e){
                e.printStackTrace();
            }
                }
            }); 
            
        VBox playerOptions = new VBox(twoPlayer, threePlayer, fourPlayer);
        playerOptions.setSpacing(40);
        playerOptions.setTranslateX(500);
        playerOptions.setTranslateY(350);
        
        playerSelectionRoot.getChildren().addAll(playerSelectionLogo, playerOptions, playerSelectionMacroButtons);
            
        //ENTERING PLAYER NAMES
        namesDone = new Button("DONE");
        namesDone.setTextAlignment(TextAlignment.CENTER);
        namesDone.setFocusTraversable(false);
        namesDone.setScaleX(2.5);
        namesDone.setScaleY(1.75);
        namesDone.setTranslateX(590);
        namesDone.setTranslateY(600);
        //creates the loading warning
        namesDone.setOnMousePressed( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    loadingWarning.setText("Loading... Please Wait");
                    loadingWarning.setFill(Color.YELLOW);
                    loadingWarning.setFont(Font.font("Verdana", 30));
                    loadingWarning.setTranslateX(450);
                    loadingWarning.setTranslateY(578);
                }
            }); 
        // takes the names and starts a new game
        namesDone.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    try{                        
                        newGame(player1NameField.getText(), player2NameField.getText(),
                            player3NameField.getText(), player4NameField.getText());
                    }
                    catch(IOException e){
                    e.printStackTrace();
                    }
                }
            }); 
        
        //starts the window
        stage.setTitle("Splendor");
        stage.setResizable(false);
        stage.setScene(mainMenu);
        stage.show();
    }

    /**
     * Main method for the program
     * 
     * @param args - arguments
     */
    public static void main(String[] args){
        launch(args);   
    }

    /**
     * Takes a card from the market and ensures the player has the correct
     * number of gems avaiable for market and bonuses.
     * 
     * @param - the card being purchased
     * @return 1 if the card can be purchased, 0 if it cannot.
     */
    private static int purchaseCard(Card card){
        Player player = players[activePlayer]; //creates a player variable for easier reference
        
        //gets the gems available for market from the player
        ArrayList<GemType> gems = player.getGemsForMarket();
        
        int difference = 0;
        int diamond = 0;
        int sapphire = 0;
        int emerald = 0;
        int ruby = 0;
        int onyx = 0;
        int gold = 0;
        
        //determines the exact gems available for market
        for(int i = 0; i < gems.size(); i++){
            GemType gemForMarketType = gems.get(i);
            switch(gemForMarketType){
             case DIAMOND:
             diamond++;
             break;
             case SAPPHIRE:
             sapphire++;
             break;
             case EMERALD:
             emerald++;
             break;
             case RUBY:
             ruby++;
             break;
             case ONYX:
             onyx++;
             break;
             case GOLD:
             gold++;
            }
        }
        
        //determines if the number of gems selected is enough to cover the
        //cost of the card, or if gold tokens are required (difference)
        diamond -= card.getDiamondCost() - player.getDiamondCards();
        if (diamond < 0){
         difference += Math.abs(diamond);
         diamond = 0;
        }
        sapphire -= card.getSapphireCost() - player.getSapphireCards();
        if (sapphire < 0){
         difference += Math.abs(sapphire);
         sapphire = 0;
        }
        emerald -= card.getEmeraldCost() - player.getEmeraldCards();
        if (emerald < 0){
         difference += Math.abs(emerald);
         emerald = 0;
        }
        ruby -= card.getRubyCost() - player.getRubyCards();
        if (ruby < 0){
         difference += Math.abs(ruby);
         ruby = 0;
        }
        onyx -= card.getOnyxCost() - player.getOnyxCards();
        if (onyx < 0){
         difference += Math.abs(onyx);
         onyx = 0;
        }
        
        //if there is enough gold to make up the difference,
        // the card can be purchased
        if (gold >= difference)        
        return 1;
        else
        return 0;
        
    }

    /**
     * Takes a noble from the market and adds it to the player
     * 
     * @param noble - the noble being purchased
     * @return 1 if the noble can be purchased, 0 if it cannot
     */
    private int purchaseNoble(Noble noble){
        //creates a player variable for ease of reference
        Player player = players[activePlayer];
        
        //checks to see if a player has enough bonuses and does
        //not already have a noble
        if(player.getHasNoble())
            return 0;
        else{
            if(player.getDiamondCards() < noble.getDiamondCost())
                return 0;
            if(player.getSapphireCards() < noble.getSapphireCost())
                return 0;
            if(player.getEmeraldCards() < noble.getEmeraldCost())
                return 0;
            if(player.getRubyCards() < noble.getRubyCost())
                return 0;
            if(player.getOnyxCards() < noble.getOnyxCost())
                return 0;
            return 1;
        }
    }

    /**
     * Updates the visuals to reflect the new victory points and
     * bonuses of the player.
     */
    private static void updateGemVisuals(){
        marketGemDisplay.getChildren().get(0).setVisible(marketDiamond > 0);
        marketGemDisplay.getChildren().get(1).setVisible(marketSapphire > 0);
        marketGemDisplay.getChildren().get(2).setVisible(marketEmerald > 0);
        marketGemDisplay.getChildren().get(3).setVisible(marketRuby > 0);
        marketGemDisplay.getChildren().get(4).setVisible(marketOnyx > 0);
        marketGemDisplay.getChildren().get(5).setVisible(marketGold > 0);
    }
    
    /**
     * Adds functionality to the cards in the market.
     * 
     * @param marketDisplay - the display HBox for the appropriate market level
     * @param index - the index of the card
     * @param card - the card to which functionality is being added
     * @param cardVisuals - the visuals to be interacted with
     * @param deck - the appropriate deck from which a replacement card is to be drawn
     * @param marketView - the array of cards that are available in the appropriate level
     */
    private void addMarketCardFunction(HBox marketDisplay, int index, Card card, Group cardVisuals, Stack<Card> deck, Card[] marketView){
         cardVisuals.setOnMousePressed( new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event){
                        if(gem1Type == null){ //ensures play has not started selecting gems
                        if(event.getButton() == MouseButton.PRIMARY){
                            int success = purchaseCard(card);
                            if (success == 1){ // if the card can be purchased
                                if(!deck.isEmpty()){ //if the deck is not empty
                                 marketDisplay.getChildren().remove(index); //remove the card
                            }
                                else{ // the deck is empty therefore make the deck back
                                    // invisible to reflect an empty deck
                                    if( deck == level1Deck){
                                    level1DeckBack.setVisible(false);
                                }
                                else if( deck == level2Deck){
                                    level2DeckBack.setVisible(false);
                                }
                                else{
                                    level3DeckBack.setVisible(false);
                                }
                                //set the card visuals to invisible as the card will not be replaced
                                marketDisplay.getChildren().get(index).setVisible(false);
                            }
                                players[activePlayer].addCard(card); // card is added to the player
                                players[activePlayer].spendGems(returnGems(card)); //gems are spent
                                
                                //a new card is drawn to replace it
                                Card newCard = deck.pop();
                                Group newCardVisuals = newCard.getVisuals();
                                
                                //***************
                                //!!!RECURSION!!!
                                //***************
                                //Recursively adds functionality to the replacement card
                                addMarketCardFunction(marketDisplay, index, newCard, newCardVisuals, deck, marketView);
                                marketView[index] = newCard;
                                marketDisplay.getChildren().add(index, newCardVisuals);
                   
                                nextTurn();
                            }
                            else {
                                //show some warning... (not the following)
                                instructions.setText("\n\n   Not enough gems");
                            optionText.setText("");
                            }
                        }
                        else{
                            int success = players[activePlayer].addCardToHand(card);
                            if (success == 1){ // if the card can be added to the hand
                                if(!deck.isEmpty()) //if the deck is not empty
                                marketDisplay.getChildren().remove(index); //remove the card
                                else // the deck is empty therefore make the deck back
                                marketDisplay.getChildren().get(index).setVisible(false);
                                
                                //add a gold token to the players inventory if there is
                                //space and a gold token is available to be taken from
                                //the market
                                 if (marketGold == 1 && players[activePlayer].getTotalGems() < 10){                        
                                marketGold--;
                                updateGemVisuals();
                                players[activePlayer].addGem(GemType.GOLD);                        
                            }
                            else if(marketGold > 1 && players[activePlayer].getTotalGems() < 10){
                                marketGold--;
                                players[activePlayer].addGem(GemType.GOLD);
                            }
                            else if (players[activePlayer].getTotalGems() == 10){
                                System.out.println("Inventory Full");
                            }
                            else {
                                System.out.println("No more gems of that type");
                            }
                            
                                //a new card is drawn to replace it
                                Card newCard = deck.pop();
                                Group newCardVisuals = newCard.getVisuals();
                                
                                //***************
                                //!!!RECURSION!!!
                                //***************
                                //Recursively adds functionality to the replacement card
                                addMarketCardFunction(marketDisplay, index, newCard, newCardVisuals, deck, marketView);
                                marketView[index] = newCard;
                                marketDisplay.getChildren().add(index, newCardVisuals);
                                
                                nextTurn();
                            }
                            else {
                                //show some warning... (not the following)
                                instructions.setText("\n\n   Your hand is full");
                            optionText.setText("");
                            }
                        }
                    }
                }
                });

    }
    
    /**
     * Returns gems to the user after a purchase.
     * 
     * @param card - the card being purchased
     * @return an list of the unused gems
     */
    private static ArrayList<GemType> returnGems(Card card){
        Player player = players[activePlayer]; // creates a player variable for ease of reference 
       
        //gets the list of gems available for market
        ArrayList<GemType> gems = player.getGemsForMarket();
        
        int difference = 0;
        
        //determines the cost of the cards in actual gems when accounting
        //for bonuses
        int diamondCost = Math.max(0, card.getDiamondCost() - player.getDiamondCards());
        int sapphireCost = Math.max(0, card.getSapphireCost() - player.getSapphireCards());
        int emeraldCost = Math.max(0, card.getEmeraldCost() - player.getEmeraldCards());
        int rubyCost = Math.max(0, card.getRubyCost() - player.getRubyCards());
        int onyxCost = Math.max(0, card.getOnyxCost() - player.getOnyxCards());
        
        int diamondForMarket = 0;
        int sapphireForMarket = 0;
        int emeraldForMarket = 0;
        int rubyForMarket = 0;
        int onyxForMarket = 0;
        int goldForMarket = 0;
        
        //determines the exact number of gems available for the market
        for (int i = 0; i < gems.size(); i++){
            GemType gemType = gems.get(i);
            switch(gemType){
             case DIAMOND:
             diamondForMarket++;
             break;
             case SAPPHIRE:
             sapphireForMarket++;
             break;
             case EMERALD:
             emeraldForMarket++;
             break;
             case RUBY:
             rubyForMarket++;
             break;
             case ONYX:
             onyxForMarket++;
             break;
             case GOLD:
             goldForMarket++;
             break;
            }
        }
        
        int goldCost = 0;
        
        int leftoverDiamonds = 0;
        int leftoverSapphires = 0;
        int leftoverEmeralds = 0;
        int leftoverRubies = 0;
        int leftoverOnyx = 0;
        int leftoverGold = 0;        
        
        //determines the number of excess gems
        if(diamondCost - diamondForMarket > 0){
        goldCost += diamondCost - diamondForMarket;
        marketDiamond += diamondForMarket;
    }
        else if(diamondCost - diamondForMarket < 0){
        leftoverDiamonds += diamondForMarket - diamondCost;
        marketDiamond += diamondForMarket - leftoverDiamonds;
    }
        else
        marketDiamond += diamondForMarket;        
            if(sapphireCost - sapphireForMarket > 0){
        goldCost += sapphireCost - sapphireForMarket;
        marketSapphire += sapphireForMarket;
    }
        else if(sapphireCost - sapphireForMarket < 0){
        leftoverSapphires += sapphireForMarket - sapphireCost;
        marketSapphire += sapphireForMarket - leftoverSapphires;
    }
        else
        marketSapphire += sapphireForMarket;
            if(emeraldCost - emeraldForMarket > 0){
        goldCost += emeraldCost - emeraldForMarket;
        marketEmerald += emeraldForMarket;
    }
        else if(emeraldCost - emeraldForMarket < 0){
        leftoverEmeralds += emeraldForMarket - emeraldCost;
        marketEmerald += emeraldForMarket - leftoverEmeralds;
    }
        else
        marketEmerald += emeraldForMarket;
            if(rubyCost - rubyForMarket > 0){
        goldCost += rubyCost - rubyForMarket;
        marketRuby += rubyForMarket;
    }
        else if(rubyCost - rubyForMarket < 0){
        leftoverRubies += rubyForMarket - rubyCost;
        marketRuby += rubyForMarket - leftoverRubies;
    }
        else
        marketRuby += rubyForMarket;
            if(onyxCost - onyxForMarket > 0){
        onyxCost += onyxCost - onyxForMarket;
        marketOnyx += onyxForMarket;
    }
        else if(onyxCost - onyxForMarket < 0){
        leftoverOnyx += onyxForMarket - onyxCost;
        marketOnyx += onyxForMarket - leftoverOnyx;
    }
        else
        marketOnyx += onyxForMarket;
        if(goldCost - goldForMarket < 0){
        leftoverGold += goldForMarket - goldCost;
        marketGold += goldForMarket - leftoverGold;
    }
        else
        marketGold += goldForMarket;
        
        //creates a list of the unused gems
        ArrayList<GemType> leftoverGems = new ArrayList<GemType>();
        if(leftoverDiamonds > 0){
         for (int i = 0; i < leftoverDiamonds; i++)
            leftoverGems.add(GemType.DIAMOND);
        }
        if(leftoverSapphires > 0){
         for (int i = 0; i < leftoverSapphires; i++)
            leftoverGems.add(GemType.SAPPHIRE);
        }
        if(leftoverEmeralds > 0){
         for (int i = 0; i < leftoverEmeralds; i++)
            leftoverGems.add(GemType.EMERALD);
        }
        if(leftoverRubies > 0){
         for (int i = 0; i < leftoverRubies; i++)
            leftoverGems.add(GemType.RUBY);
        }
        if(leftoverOnyx > 0){
         for (int i = 0; i < leftoverOnyx; i++)
            leftoverGems.add(GemType.ONYX);
        }
        if(leftoverGold > 0){
         for (int i = 0; i < leftoverGold; i++)
            leftoverGems.add(GemType.GOLD);
        }
        
        //updates the market gem display to reflect the return of gems
        updateGemVisuals();
        
        return leftoverGems;
    }
    
    /**
     * Adds functionality to the card in a player's hand
     * 
     * @param cardVisuals - the visuals of the card
     * @param card - the card to which functionality is being added
     * @param cardsInHand - the display of the cards in the player's hand
     * @param index - the index of the card within the player hand
     */
    public static void addCardInHandFunction(Group cardVisuals, Card card, HBox cardsInHand, int index){
        cardVisuals.setOnMousePressed( new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event){
                        if(gem1Type == null){ //ensures play has not started selecting gems
                        int success = purchaseCard(card);
                            if (success == 1){  //if the card can be purchased                       
                                cardsInHand.getChildren().get(index).setVisible(false); //remove the card visuals from the hand
                                players[activePlayer].addCard(card); //applies the card to the player
                                players[activePlayer].spendGems(returnGems(card)); // spends gems
                                players[activePlayer].removeCardFromHand(index); // removes the cards from the hand
                                nextTurn();
                            }
                            else {
                                //show some warning... (not the following)
                                instructions.setText("\n\n   Not enough gems");
                            optionText.setText("");
                            }
                        }
                    }
    });
}

/**
 * Starts the next turn
 */
    public static void nextTurn(){
     players[activePlayer].setUnactivePlayer();
     
     //Updates the active player
     activePlayer++;
     //Checks if there is a winner
     if(activePlayer == numberOfPlayers){
         String winnerName = checkIfWinner();     
         if(winnerName == null)
         activePlayer = 0;
         else
         winner(winnerName);
        }
     players[activePlayer].setActivePlayer();
     
     //updates the text in the tooltip display
     turnMarker.setText(players[activePlayer].getName()  + "'s");
     resetInstructions();
    }
    
    /**
     * Checks if there is a winner.
     *
     * @return the name of the winner, null if there is no winner
     */
    private static String checkIfWinner(){
        ArrayList<Player> victoriousPlayers = new ArrayList<Player>();
        
        //checks to see which players are victorious
        for(int i = 0; i < players.length; i++){
         if(players[i].getVP() >= 15)
            victoriousPlayers.add(players[i]);
        }
        
        if(victoriousPlayers.size() == 0)
        return null; // no winners
        else if(victoriousPlayers.size() == 1)
        return victoriousPlayers.get(0).getName(); // one winner
        else{
            //breaks the tie if there are multiple potential winners
            Player lowestDevCards = null;
            Player tiedDevCards = null;
            for (int i = 0; i < victoriousPlayers.size(); i++){
                if(lowestDevCards == null || lowestDevCards.getTotalDevCards() > victoriousPlayers.get(i).getTotalDevCards()){
                    lowestDevCards = victoriousPlayers.get(i);
                }
                else if (lowestDevCards.getTotalDevCards() == victoriousPlayers.get(i).getTotalDevCards()){
                    tiedDevCards = victoriousPlayers.get(i);
                }
            }
            
            if(tiedDevCards == null){
             return lowestDevCards.getName();   
            }
            else if (lowestDevCards.getTotalDevCards() == tiedDevCards.getTotalDevCards()){
               return "Tie";
            }
            else{
             return lowestDevCards.getName();   
            }
        }
    }
    
    /**
     * Adds a gem to the player and removes it from the market
     * 
     * @param gem - the type of gem being taken
     * @return 0 if taking the gem is unsuccessful, 1 if taking the gem does not end the turn, and 2 if it does
     */
    private int takeGem(GemType gem){
        if(players[activePlayer].getTotalGems() == 10) //checks to see if a player's inventory is full
        return 0;
        
        //checks how many different types of gems are available
        int difGemTypesAvailable = 0;
        if (marketDiamond > 0)
        difGemTypesAvailable++;
        if (marketSapphire > 0)
        difGemTypesAvailable++;
        if (marketEmerald > 0)
        difGemTypesAvailable++;
        if (marketRuby > 0)
        difGemTypesAvailable++;
        if (marketOnyx > 0)
        difGemTypesAvailable++;
        
        
        if(gem1Type == null) //first gem is always allowed
        gem1Type = gem;
        else if(gem2Type == null){
            if(gem == gem1Type && gem1Type != null){ //if the player is trying to take two gems of the same type
                //make sure there were initially 4 gems of that type in the market
                switch(gem){
                 case DIAMOND:
                 if(marketDiamond < 3){
                     return 0;
                    }
                    else
                    gem2Type = gem;
                 break;
                 case SAPPHIRE:
                 if(marketSapphire < 3){
                     return 0;
                    }
                    else
                    gem2Type = gem;
                 break;
                 case EMERALD:
                 if(marketEmerald < 3){
                     return 0;
                    }
                    else
                    gem2Type = gem;
                 break;
                 case RUBY:
                 if(marketRuby < 3){
                     return 0;
                    }
                    else
                    gem2Type = gem;
                 break;
                 case ONYX:
                 if(marketOnyx < 3){
                     return 0;
                    }
                    else
                    gem2Type = gem;
                 break;
                }
            }
            else
            gem2Type = gem;
        }
        else{
            if (gem1Type == gem || gem2Type == gem){ //makes sure the player isn't trying to double up on a type of gem
             return 0;   
            }
            else{
             gem3Type = gem;   
            }
        }
        
        switch(gem){
         case DIAMOND:
         marketDiamond--;
         break;
         case SAPPHIRE:
         marketSapphire--;
         break;
         case EMERALD:
         marketEmerald--;
         break;
         case RUBY:
         marketRuby--;
         break;
         case ONYX:
         marketOnyx--;
         break;
         case GOLD:
         marketGold--;
         break;
        }
        players[activePlayer].addGem(gem);        
        
        if (gem2Type != null && gem2Type != gem1Type){
         instructions.setText("\n    Select one more \nunique gem");
         optionText.setText("");
        }
        else if(gem1Type != null && difGemTypesAvailable == 2){
            instructions.setText("\n    Select one more \nunique gem");
         optionText.setText("");
        }
        else if(gem1Type != null){
         instructions.setText("\n  Select two more \nunique gems \n\n or \n\n Select another gem \nof the same type");
         optionText.setText("");
        }
        
        /*these statements check the various conditions the market could be in and ends the turn accordingly
        *the turn will end if:
        * A player has two gems and there are only two types of gems available in the market
        * A player has one gem and there is only one type of gem available in the market
        * There are no gems left in the market
        * The first two gems taken were of the same type
        * The player's inventory becomes full
        * Or the player takes a 3rd gem
        **/
        if(gem2Type != null && difGemTypesAvailable == 2){
            gem1Type = null;
            gem2Type = null;
            gem3Type = null;
        return 2;
        }
        else if(gem1Type != null && difGemTypesAvailable == 1){
            gem1Type = null;
            gem2Type = null;
            gem3Type = null;
        return 2;
        }
        else if(marketDiamond + marketSapphire + marketEmerald + marketRuby + marketOnyx == 0){
            gem1Type = null;
            gem2Type = null;
            gem3Type = null;
        return 2;
        }
       else if(gem1Type == gem2Type){
            gem1Type = null;
            gem2Type = null;
            gem3Type = null;
        return 2;
        }
       else if(players[activePlayer].getTotalGems() == 10){
            gem1Type = null;
            gem2Type = null;
            gem3Type = null;
        return 2;
    }
    else if(gem3Type != null){
        gem1Type = null;
            gem2Type = null;
            gem3Type = null;
        return 2;
    }
        else
        return 1; // success, and you can pick more gems
    }
    
    /**
     * Changes the scene and declares the winner
     * 
     * @param name - the name of the winner
     */
    public static void winner(String name){                
        Glow glow = new Glow();
        glow.setLevel(5);
        
        if(winnerRoot.getChildren().contains(victorText))
        winnerRoot.getChildren().remove(victorText);
        
        if(!name.equals("Tie")){
        victorText.setText(name + " Wins!");
    }
    else{
        victorText.setText(name);
    }
        victorText.setFont(Font.font("Verdana", 52));
        victorText.setFill(Color.WHITE);
        victorText.setTextAlignment(TextAlignment.CENTER);
        victorText.setTranslateX(450);
        victorText.setTranslateY(420);
        victorText.setEffect(glow);
        
        winnerRoot.getChildren().addAll(victorText);
        mainStage.setScene(winnerScene);
    }
    
    /**
     * Reinitializes game-specific components to reset the program for
     * a new game.
     * 
     * @param player1Name - the name of player 1
     * @param player2Name - the name of player 2
     * @param player3Name - the name of player 3
     * @param player4Name - the name of player 4
     */
    private void newGame(String player1Name, String player2Name, String player3Name, String player4Name) throws IOException{
        players = new Player[numberOfPlayers];
        activePlayer = 0;
        
        loadingWarning.setText(""); //resets the loading text
        
        //places the appropriate number of gems in the market
        if(numberOfPlayers == 4){
            marketDiamond = 7;
            marketSapphire = 7;
            marketEmerald = 7;
            marketRuby = 7;
            marketOnyx = 7;
        }
        else if(numberOfPlayers == 3){
            marketDiamond = 5;
            marketSapphire = 5;
            marketEmerald = 5;
            marketRuby = 5;
            marketOnyx = 5;
        }
        else {
            marketDiamond = 4;
            marketSapphire = 4;
            marketEmerald = 4;
            marketRuby = 4;
            marketOnyx = 4;
        }
        
        //creates the players
        players[0] = new Player(0, player1Name);
        players[0].setActivePlayer();
        players[1] = new Player(1, player2Name);
        if(numberOfPlayers >= 3){
            players[2] = new Player(2, player3Name);
        }
        if (numberOfPlayers == 4){
         players[3] = new Player(3, player4Name);   
        }
        
        //removes any existing play visuals from the scene
        if(root.getChildren().contains(player1Group))
        root.getChildren().remove(player1Group);
        if(root.getChildren().contains(player2Group))
        root.getChildren().remove(player3Group);
        if(root.getChildren().contains(player3Group))
        root.getChildren().remove(player3Group);
        if(root.getChildren().contains(player4Group))
        root.getChildren().remove(player4Group);
        
        
        //adds player visuals to the scene
        player1Group = players[0].getVisuals();
        player1Group.setTranslateY(550);
        root.getChildren().add(player1Group);

        player2Group = players[1].getVisuals();
        root.getChildren().add(player2Group);

        if (numberOfPlayers > 2){
            player3Group = players[2].getVisuals();
            root.getChildren().add(player3Group);
        }
        if (numberOfPlayers > 3){
            player4Group = players[3].getVisuals();
            player4Group.setTranslateX(1084);
            root.getChildren().add(player4Group);
        }  
        
        //clears the decks
        if(!level1Deck.isEmpty())
        level1Deck.clear();
        if(!level2Deck.isEmpty())
        level2Deck.clear();
        if(!level3Deck.isEmpty())
        level3Deck.clear();
        
        //regenerates the decks
        level1Deck = deckGenerator.generateLevel1Deck();
        level2Deck = deckGenerator.generateLevel2Deck();
        level3Deck = deckGenerator.generateLevel3Deck();
        nobleDeck = deckGenerator.generateNoblesDeck();
        
        //clears the market displays
        level1Available.getChildren().clear();
        level2Available.getChildren().clear();
        level3Available.getChildren().clear();
        
        //repopulates the market displays
        for(int i = 0; i < level1Market.length; i++){
            Card card = level1Deck.pop();
            Group cardVisuals = card.getVisuals();
            addMarketCardFunction(level1Available, i, card, cardVisuals, level1Deck, level1Market);
            level1Market[i] = card;
            level1Available.getChildren().add(cardVisuals);
        }
        
        for(int i = 0; i < level2Market.length; i++){
            Card card = level2Deck.pop();
            Group cardVisuals = card.getVisuals();
            addMarketCardFunction(level2Available, i, card, cardVisuals, level2Deck, level2Market);
            level2Market[i] = card;
            level2Available.getChildren().add(cardVisuals);
        }
        
        for(int i = 0; i < level3Market.length; i++){
            final int index = i;
            Card card = level3Deck.pop();
            Group cardVisuals = card.getVisuals();
            addMarketCardFunction(level3Available, i, card, cardVisuals, level3Deck, level3Market);
            level3Market[i] = card;
            level3Available.getChildren().add(cardVisuals);
        }
        
        //sets the correct number of noble spaces in the market
        noblesMarket = new Noble[numberOfPlayers];
        
        VBox availableNobles = new VBox();
        availableNobles.setTranslateX(820);
        availableNobles.setTranslateY(110);
        availableNobles.setSpacing(2);

        //populates the noble market and adds functinoality to the noble tiles
        for(int i = 0; i < noblesMarket.length; i++){
            final int index = i;
            final Noble noble = nobleDeck.pop();
            Group nobleVisuals = noble.getVisuals();
            nobleVisuals.setOnMousePressed( new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event){
                        if(gem1Type == null){ //ensures play has not started selecting gems
                        int success = purchaseNoble(noble);
                        if (success == 1){//if the noble can be purchased
                            availableNobles.getChildren().get(index).setVisible(false); //set the noble to be invisible
                            players[activePlayer].addNoble(noble); //add the noble to the player
                            nextTurn();
                        }
                        else {
                            instructions.setText("\n\n Not enough bonuses");
                            optionText.setText("");
                        }
                    }
                }
                });
            noblesMarket[i] = noble;
            availableNobles.getChildren().add(nobleVisuals);
        }
        
        turnMarker.setText(players[activePlayer].getName() + "'s");
        
        //creates the Help and Exit buttons
        
        Button help = new Button("HELP");
        help.setTextAlignment(TextAlignment.CENTER);
        help.setFocusTraversable(false);
        help.setScaleX(2.5);
        help.setScaleY(1.75);
        help.setTranslateX(25);
        help.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //Opens the instruction manual pdf through your Desktop's
                    //default program for opening a pdf
                    if (Desktop.isDesktopSupported()) {
                    try {
                    Desktop.getDesktop().open(new File("./Resources/Instructions.pdf"));
                    } catch (Exception e) {
                    e.printStackTrace();
                    }
                    } 

                }
            }); 
            

        Button exit = new Button("EXIT");
        exit.setTextAlignment(TextAlignment.CENTER);
        exit.setFocusTraversable(false);
        exit.setScaleX(2.75);
        exit.setScaleY(1.75);
        exit.setTranslateX(27);
        exit.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){
                    mainStage.setScene(mainMenu);
                }
            }); 

        VBox macroButtons = new VBox(help, exit);
        macroButtons.setSpacing(22);
        macroButtons.setTranslateX(13);
        macroButtons.setTranslateY(565);
        root.getChildren().addAll(macroButtons, availableNobles);
        
        //sets the scene
        mainStage.setScene(mainScene);
    }
    
    /**
     * Sets player names from the appropriate text fields
     * 
     * @param localPlayers - the number of players on the client's computer
     */
    public void enterPlayerNames(int localPlayers) throws IOException{
        ImageView enterNamesLogo = new ImageView("./Resources/logo-Splendor.png");
        enterNamesLogo.setScaleX(0.5);
        enterNamesLogo.setScaleY(0.5);
        enterNamesLogo.setTranslateX(-550);
        enterNamesLogo.setTranslateY(-250);
              
        VBox playerNameTextField = new VBox();
        playerNameTextField.setSpacing(10);
        playerNameTextField.setTranslateX(540);
        playerNameTextField.setTranslateY(350);        
        
        //displays as many text fields as there are local players
        for (int i = 0; i < localPlayers; i++){            
            Label playerLabel = new Label("Player " + (i+1) + " Name");
            VBox playerNameFields = new VBox(playerLabel);
            
            switch(i){
             case 0:
             player1NameField.clear();
             playerNameFields.getChildren().add(player1NameField);
             break;
             case 1:
             player2NameField.clear();
             playerNameFields.getChildren().add(player2NameField);
             break;
             case 2:
             player3NameField.clear();
             playerNameFields.getChildren().add(player3NameField);
             break;
             case 3:
             player4NameField.clear();
             playerNameFields.getChildren().add(player4NameField);
             break;
             
            }
            
            playerNameTextField.getChildren().add(playerNameFields);
        }
        
        //sets the scene
        Group enterNamesRoot = new Group(namesDone, enterNamesLogo, playerNameTextField, loadingWarning);
        Scene enterNamesScene = new Scene(enterNamesRoot, 1200, 650);
        enterNamesScene.setFill(Color.GREEN);
        mainStage.setScene(enterNamesScene);
    }
    
    private static void resetInstructions(){
     instructions.setText("Choose from one of\n the following actions:");
     optionText.setText("1. Take 3 gems of\n different types\n\n" +
                                   "2. Take 2 gems of the\n same type\n" +
                                   " (Must be 4 tokens of\n that type)\n\n" +
                                   "3. Reserve a card and\n take a Gold\n\n" +
                                   "4. Purchase a card");
    }
}
