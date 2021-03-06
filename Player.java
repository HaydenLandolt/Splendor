import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.effect.Glow;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Player
{
    private int id;
    private String name;
    private boolean isAI;
    private int victoryPoints = 0;
    private int diamond = 0;
    private int sapphire = 0;
    private int emerald = 0;
    private int ruby = 0;
    private int onyx = 0; 
    private int gold = 0;
    private int diamondCards = 0;
    private int sapphireCards = 0;
    private int emeraldCards = 0;
    private int rubyCards = 0;
    private int onyxCards = 0;  
    private Card[] hand = new Card[3];
    private boolean hasNoble = false;

    Group playerVisuals = new Group();
    Pane firstInventory;
    Pane secondInventory;

    HBox cardsInHand;

    ArrayList<GemType> gemsForMarket = new ArrayList<GemType>();

    Random random = new Random();

    GemModels gemModels = new GemModels();

    Rectangle border;

    /**
     * Constructor for a Player.
     * 
     * @param id - the player's id, also serves as the player's index within the main class
     * @param name - player name.
     */
    public Player(int id, String name, boolean isAI){
        this.id = id;
        this.name = name;
        this.isAI = isAI;

        /*
         * There are several if statements to see if id < 2.
         * This is to see whether or not the player is the 3rd
         * or 4th player which would have their visuals arranged
         * in a vertical fashion, as opposed to the horizontal fashion
         * of players 1 and 2.
         */
        if (id < 2){
            firstInventory = new HBox(14);
            secondInventory = new HBox(14);
        }
        else{
            firstInventory = new VBox(14);
            secondInventory = new VBox(14);
        }

        //creates the border
        if(id < 2)
            border = new Rectangle(1200, 100);
        else
            border = new Rectangle(116, 650);
        border.setFill(Color.GREY);

        //displays player name
        Text nameText = new Text(this.name);
        nameText.setFont(Font.font("Verdana", 20));
        nameText.setTextAlignment(TextAlignment.CENTER);
        nameText.setTranslateX(-20);

        //displays victory points
        Label vpLabel = new Label("Victory Points");
        vpLabel.setTranslateX(-20);
        Text vpText = new Text("" + victoryPoints);
        vpText.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        vpText.setFill(Color.WHITE);
        VBox vpLayout = new VBox(nameText, vpLabel, vpText);
        if(id < 2){
            vpLayout.setTranslateX(300);
            vpLayout.setTranslateY(10);
        }
        else{
            vpLayout.setTranslateX(40);
            vpLayout.setTranslateY(50);
        }

        //creats the player's inventory of gems
        Rectangle gemPane;        
        if(id < 2){
            gemPane = new Rectangle(315, 90);
            gemPane.setFill(Color.WHITE);
            gemPane.setTranslateX(370);
            gemPane.setTranslateY(5);
            firstInventory.setTranslateX(370);
            firstInventory.setTranslateY(5);
            secondInventory.setTranslateX(370);
            secondInventory.setTranslateY(50);
        }
        else{            
            gemPane = new Rectangle(90,250);
            gemPane.setFill(Color.WHITE);
            gemPane.setTranslateX(10);
            gemPane.setTranslateY(135);
            firstInventory.setTranslateX(10);
            firstInventory.setTranslateY(135);
            secondInventory.setTranslateX(55);
            secondInventory.setTranslateY(135);
        }

        //sets up the display of player bonuses
        Rectangle diamondBorder = new Rectangle(90, 20);
        diamondBorder.setFill(Color.LIGHTGREY);
        Group diamondIcon = gemModels.getGem(GemType.DIAMOND);
        diamondIcon.setScaleX(0.45);
        diamondIcon.setScaleY(0.45);
        diamondIcon.setTranslateX(-58);
        diamondIcon.setTranslateY(-20);
        Text diamondCardCount = new Text("" + diamondCards);
        diamondCardCount.setFill(Color.WHITE);
        diamondCardCount.setFont(Font.font("Verdana", 18));
        diamondCardCount.setTranslateX(65);
        diamondCardCount.setTranslateY(17);
        Group diamondCards = new Group(diamondBorder, diamondIcon, diamondCardCount);

        Rectangle sapphireBorder = new Rectangle(90, 20);
        sapphireBorder.setFill(Color.BLUE);
        Group sapphireIcon = gemModels.getGem(GemType.SAPPHIRE);
        sapphireIcon.setScaleX(0.45);
        sapphireIcon.setScaleY(0.45);
        sapphireIcon.setTranslateX(-58);
        sapphireIcon.setTranslateY(-20);
        Text sapphireCardCount = new Text("" + sapphireCards);
        sapphireCardCount.setFill(Color.WHITE);
        sapphireCardCount.setFont(Font.font("Verdana", 18));
        sapphireCardCount.setTranslateX(65);
        sapphireCardCount.setTranslateY(17);
        Group sapphireCards = new Group(sapphireBorder, sapphireIcon, sapphireCardCount);

        Rectangle emeraldBorder = new Rectangle(90, 20);
        emeraldBorder.setFill(Color.GREEN);
        Group emeraldIcon = gemModels.getGem(GemType.EMERALD);
        emeraldIcon.setScaleX(0.45);
        emeraldIcon.setScaleY(0.45);
        emeraldIcon.setTranslateX(-58);
        emeraldIcon.setTranslateY(-20);
        Text emeraldCardCount = new Text("" + emeraldCards);
        emeraldCardCount.setFill(Color.WHITE);
        emeraldCardCount.setFont(Font.font("Verdana", 18));
        emeraldCardCount.setTranslateX(65);
        emeraldCardCount.setTranslateY(17);
        Group emeraldCards = new Group(emeraldBorder, emeraldIcon, emeraldCardCount);

        Rectangle rubyBorder = new Rectangle(90, 20);
        rubyBorder.setFill(Color.RED);
        Group rubyIcon = gemModels.getGem(GemType.RUBY);
        rubyIcon.setScaleX(0.45);
        rubyIcon.setScaleY(0.45);
        rubyIcon.setTranslateX(-58);
        rubyIcon.setTranslateY(-20);
        Text rubyCardCount = new Text("" + rubyCards);
        rubyCardCount.setFill(Color.WHITE);
        rubyCardCount.setFont(Font.font("Verdana", 18));
        rubyCardCount.setTranslateX(65);
        rubyCardCount.setTranslateY(17);
        Group rubyCards = new Group(rubyBorder, rubyIcon, rubyCardCount);

        Rectangle onyxBorder = new Rectangle(90, 20);
        onyxBorder.setFill(Color.BLACK);
        Group onyxIcon = gemModels.getGem(GemType.ONYX);
        onyxIcon.setScaleX(0.45);
        onyxIcon.setScaleY(0.45);
        onyxIcon.setTranslateX(-58);
        onyxIcon.setTranslateY(-20);
        Text onyxCardCount = new Text("" + onyxCards);
        onyxCardCount.setFill(Color.WHITE);
        onyxCardCount.setFont(Font.font("Verdana", 18));
        onyxCardCount.setTranslateX(65);
        onyxCardCount.setTranslateY(17);
        Group onyxCards = new Group(onyxBorder, onyxIcon, onyxCardCount);

        // Button to display the player's hand
        Button handButton = new Button("HAND");
        handButton.setFocusTraversable(false);
        handButton.setTranslateX(20);
        handButton.setTranslateY(-7);
        handButton.setScaleX(1.75);
        handButton.setScaleY(1.5);         
        handButton.setOnMouseClicked( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {                     

                    if(Main.activePlayer == id){
                        //plays appropriate sound effect
                        if(Main.soundEffectsAllowed){
                            try{
                                Main.audio.playEffect(Main.buttonClick);
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }
                        }

                        cardsInHand = new HBox(); // display for reserved cards
                        cardsInHand.setSpacing(4);
                        cardsInHand.setTranslateX(35);
                        cardsInHand.setTranslateY(70);
                        for (int i = 0; i < hand.length; i++){
                            if(hand[i] != null){ // adds the card visuals to the display
                                Group cardVisual = hand[i].getVisuals();                        
                                Main.addCardInHandFunction(cardVisual, hand[i], cardsInHand, i); // adds functionality to the visuals
                                cardsInHand.getChildren().add(cardVisual);
                            }
                        }

                        //creates and opens a new window to display the hand
                        Group handRoot = new Group(cardsInHand);
                        Scene handScene = new Scene(handRoot, 400, 300);
                        handScene.setFill(Color.GREEN);
                        Stage handStage = new Stage();
                        handStage.setTitle(name + "'s Hand");
                        handStage.setScene(handScene);
                        handStage.show();
                    }
                }
            }); 

        // creates VBoxes to display the player's bonuses
        VBox cardDisplay = new VBox(handButton, diamondCards, sapphireCards);
        VBox sideCardDisplay = new VBox(emeraldCards, rubyCards, onyxCards);
        cardDisplay.setSpacing(2);
        if (id < 2){
            cardDisplay.setTranslateX(690);
            cardDisplay.setTranslateY(20);
            sideCardDisplay.setTranslateX(790);
            sideCardDisplay.setTranslateY(20);
        }
        else{
            cardDisplay.setTranslateX(10);
            cardDisplay.setTranslateY(400);
            sideCardDisplay.setTranslateX(10);
            sideCardDisplay.setTranslateY(475);
        }

        //add the elements to the player's visuals
        playerVisuals.getChildren().addAll(border, vpLayout, gemPane, cardDisplay,
            sideCardDisplay, firstInventory, secondInventory);
    }

    /**
     * Adds a card to the player's hand.
     * 
     * @param card - the card being added to the hand
     * @return - returns 1 if the card was successfully added, 0 if it was not
     */
    public int addCardToHand(Card card){
        if (hand[0] == null){
            hand[0] = card;
            return 1;
        }
        else if (hand[1] == null){
            hand[1] = card;
            return 1;
        }
        else if (hand[2] == null){
            hand[2] = card;
            return 1;
        }
        else 
            return 0;
    }

    /**
     * Adds a gem to the players' inventory.
     * 
     * @param gem - the type of gem being added
     */
    public void addGem(GemType gem){
        Group gemModel = gemModels.getGem(gem);
        //adjusts the appropriate gem counter
        if(gem == GemType.DIAMOND)
            diamond++;
        else if (gem == GemType.SAPPHIRE)
            sapphire++;
        else if (gem == GemType.EMERALD)
            emerald++;
        else if (gem == GemType.RUBY)
            ruby++;
        else if (gem == GemType.ONYX)
            onyx++;
        else 
            gold++;

        //allows the gem to be clicked on and to be used for purchases
        //when the gem is to be used for purchases, the gem is clicked making
        // it "glow", therefore, when a player clicks a non-glowing gem, it
        //is added to the list of gems being used for purchases. Likewise, when
        // a glowing gem is clicked, it is removed from the list of gems
        // being used for purchases, and it's effect is removed.
        gemModel.setOnMousePressed( new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event){                                     

                    System.out.println("Gem Clicked " + gemModel);

                    if(Main.activePlayer == id){
                        //plays appropriate sound effect
                        if(Main.soundEffectsAllowed){
                            try{
                                Main.audio.playEffect(Main.selectGem);
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }                
                        }   

                        if (gemModel.getEffect() == null){
                            Glow glow = new Glow();
                            glow.setLevel(3);
                            gemModel.setEffect(glow);
                            gemsForMarket.add(gem);
                        }
                        else{
                            gemModel.setEffect(null);
                            gemsForMarket.remove(gem);
                        }
                    }
                }
            });

        //adds the gem to the inventory
        if(firstInventory.getChildren().size() < 5)
            firstInventory.getChildren().add(gemModel);
        else
            secondInventory.getChildren().add(gemModel);
        /*
        if(id < 2)
        inventory.add(gemModel, inventoryRow, inventoryCol);
        else
        inventory.add(gemModel, inventoryCol, inventoryRow);
         */
    }

    /**
     * Adds a noble from the market.
     * 
     * @param - the noble being added
     */
    public void addNoble(Noble noble){
        victoryPoints += noble.getVictoryPoints();

        //plays appropriate sound effect
        if(!isAI){
            if(Main.soundEffectsAllowed){
                try{
                    Main.audio.playEffect(Main.scorePoint);
                }
                catch(Exception e){
                    e.printStackTrace();
                }                
            }
        }

        hasNoble = true;
        updateStatVisuals();
    }

    /**
     * Applies the bonuses of a card to the player.
     * 
     * @param card - the card purchased from the market
     */
    public void addCard(Card card){
        victoryPoints += card.getVictoryPoints();

        //plays appropriate sound effect
        if(card.getVictoryPoints() > 0){
            if(Main.soundEffectsAllowed){
                try{
                    Main.audio.playEffect(Main.scorePoint);
                }
                catch(Exception e){
                    e.printStackTrace();
                }                
            }   
        }

        if (card.getGemType() == GemType.DIAMOND){
            diamondCards++;
        }
        else if (card.getGemType() == GemType.SAPPHIRE){
            sapphireCards++;
        }
        else if (card.getGemType() == GemType.EMERALD){
            emeraldCards++;
        }
        else if (card.getGemType() == GemType.RUBY){
            rubyCards++;
        }
        else
            onyxCards++;

    }

    /**
     * Getter for the hasNoble boolean.
     * 
     * @return hasNoble
     */
    public boolean getHasNoble(){
        return hasNoble;
    }

    /**
     * Getter for the player's hand.
     * 
     * @return the players hand as a Card[]
     */
    public Card[] getHand(){
        return hand;   
    }

    /**
     * Getter for the player's name;
     * 
     * @return the player's name
     */
    public String getName(){
        return name;    
    }

    /**
     * Getter for the player's visuals.
     * 
     * @return the player's visuals
     */
    public Group getVisuals(){
        return playerVisuals;
    }

    /**
     * Getter for the ArrayList of gems available to spend in the market.
     * 
     * @return gemsForMarket
     */
    public ArrayList<GemType> getGemsForMarket(){
        return gemsForMarket ;  
    }

    /**
     * Getter for the player's diamond bonuses.
     * 
     * @return the player's diamond bonuses
     */
    public int getDiamondCards(){
        return diamondCards;
    }

    /**
     * Getter for the player's sapphire bonuses.
     * 
     * @return the player's sapphire bonuses
     */
    public int getSapphireCards(){
        return sapphireCards;
    }

    /**
     * Getter for the player's emerald bonuses.
     * 
     * @return the player's emerald bonuses
     */
    public int getEmeraldCards(){
        return emeraldCards;
    }

    /**
     * Getter for the player's ruby bonuses.
     * 
     * @return the player's ruby bonuses
     */
    public int getRubyCards(){
        return rubyCards;   
    }

    /**
     * Getter for the player's onyx bonuses.
     * 
     * @return the player's onyx bonuses
     */
    public int getOnyxCards(){
        return onyxCards;
    }

    /**
     * Getter for the player's victory points.
     * 
     * @return the player's victory points
     */
    public int getVP(){
        return victoryPoints;
    }

    /**
     * Getter for the player's isAI boolean
     * 
     * @return true if the player is AI
     */
    public boolean getIsAI(){
        return isAI;    
    }

    /**
     * Getter for the player's total development card, used for
     * breaking ties when determining a winner.
     * 
     * @return the player's total development cards
     */
    public int getTotalDevCards(){
        return diamondCards + sapphireCards + emeraldCards + rubyCards + onyxCards;
    }

    /**
     * Getter for the player's total gems. Used to while adding gems
     * to the inventory.
     * 
     * @return the player's total development cards
     */
    public int getTotalGems(){
        return diamond + sapphire + emerald + ruby + onyx + gold;   
    }

    /**
     * Sets the colour of the player's border to yellow to indicate
     * they are the active player. The method will also trigger the
     * AI turn in a single player game.
     */
    public void setActivePlayer(){
        border.setFill(Color.YELLOW);
        if(isAI)
            Main.doAITurn();
    }

    /**
     * Sets the colour of the player's border back to grey to indicate
     * they are no longer the active player.
     */
    public void setUnactivePlayer(){
        border.setFill(Color.GREY);
    }

    /**
     * Getter for the visual display of a players hand,
     * 
     * @return the player's hand HBox
     */
    public HBox getCardsInHand(){
        return cardsInHand;
    }

    /**
     * Runs through the ArrayList of gems being used for purchases
     * and returns any unsused gems to the player's inventory.
     * 
     * @param unsuedGems - a list of unused gems
     */
    public void spendGems(ArrayList<GemType> unusedGems){
        //removes the unused gems
        for (int i = 0; i < unusedGems.size(); i++){
            gemsForMarket.remove(unusedGems.get(i));   
        }

        int diamondCost = 0;
        int sapphireCost = 0;
        int emeraldCost = 0;
        int rubyCost = 0;
        int onyxCost = 0;
        int goldCost = 0;

        //tracks the actual cost of the card
        for (int i = 0; i < gemsForMarket.size(); i++){
            GemType gemType = gemsForMarket.get(i);
            switch(gemType){
                case DIAMOND:
                diamondCost++;
                break;
                case SAPPHIRE:
                sapphireCost++;
                break;
                case EMERALD:
                emeraldCost++;
                break;
                case RUBY:
                rubyCost++;
                break;
                case ONYX:
                onyxCost++;
                break;
                case GOLD:
                goldCost++;
                break;
            }
        }

        //removes the appropriate number of gems from the player's
        // gem counters
        diamond -= diamondCost;
        sapphire -= sapphireCost;
        emerald -= emeraldCost;
        ruby -= rubyCost;
        onyx -= onyxCost;
        gold -= goldCost;

        //updates the player's bonuses and victory points
        updateStatVisuals();

        firstInventory.getChildren().clear(); 
        secondInventory.getChildren().clear();
        for(int i = 0; i < diamond; i++){
            Group gemModel = gemModels.getGem(GemType.DIAMOND);
            if(firstInventory.getChildren().size() < 5)
                firstInventory.getChildren().add(gemModel);
            else
                secondInventory.getChildren().add(gemModel);
        }
        for(int i = 0; i < sapphire; i++){
            Group gemModel = gemModels.getGem(GemType.SAPPHIRE);
            if(firstInventory.getChildren().size() < 5)
                firstInventory.getChildren().add(gemModel);
            else
                secondInventory.getChildren().add(gemModel);
        }
        for(int i = 0; i < emerald; i++){
            Group gemModel = gemModels.getGem(GemType.EMERALD);
            if(firstInventory.getChildren().size() < 5)
                firstInventory.getChildren().add(gemModel);
            else
                secondInventory.getChildren().add(gemModel);
        }
        for(int i = 0; i < ruby; i++){
            Group gemModel = gemModels.getGem(GemType.RUBY);
            if(firstInventory.getChildren().size() < 5)
                firstInventory.getChildren().add(gemModel);
            else
                secondInventory.getChildren().add(gemModel);
        }
        for(int i = 0; i < onyx; i++){
            Group gemModel = gemModels.getGem(GemType.ONYX);
            if(firstInventory.getChildren().size() < 5)
                firstInventory.getChildren().add(gemModel);
            else
                secondInventory.getChildren().add(gemModel);
        }
        for(int i = 0; i < gold; i++){
            Group gemModel = gemModels.getGem(GemType.GOLD);
            if(firstInventory.getChildren().size() < 5)
                firstInventory.getChildren().add(gemModel);
            else
                secondInventory.getChildren().add(gemModel);
        }         

        gemsForMarket.clear();
    }

    /**
     * Updates the visuals to reflect the player's bonuses and
     * victory points.
     */
    private void updateStatVisuals(){
        //Update victory points
        VBox vpDisplay = (VBox)playerVisuals.getChildren().get(1);
        ((Text)vpDisplay.getChildren().get(2)).setText("" + victoryPoints);

        //Update the bonuses display
        VBox cardDisplay = (VBox)playerVisuals.getChildren().get(3);
        Group diamondDisplay = (Group)cardDisplay.getChildren().get(1);
        ((Text)diamondDisplay.getChildren().get(2)).setText("" + diamondCards);
        Group sapphireDisplay = (Group)cardDisplay.getChildren().get(2);
        ((Text)sapphireDisplay.getChildren().get(2)).setText("" + sapphireCards);

        VBox sideCardDisplay = (VBox)playerVisuals.getChildren().get(4);
        Group emeraldDisplay = (Group)sideCardDisplay.getChildren().get(0);
        ((Text)emeraldDisplay.getChildren().get(2)).setText("" + emeraldCards);
        Group rubyDisplay = (Group)sideCardDisplay.getChildren().get(1);
        ((Text)rubyDisplay.getChildren().get(2)).setText("" + rubyCards);
        Group onyxDisplay = (Group)sideCardDisplay.getChildren().get(2);
        ((Text)onyxDisplay.getChildren().get(2)).setText("" + onyxCards);

    }

    /**
     * Removes a card from the player's hand.
     * 
     * @param index - the index of the card being removed
     */
    public void removeCardFromHand(int index){
        hand[index] = null;           
    }

    /**
     * Determines if the player's hand is full
     * 
     * @return true if the player's hand is full
     */
    public boolean isHandFull(){
        if(hand[0] == null || hand[1] == null || hand[2] == null)
            return false;
        else
            return true;
    }

    /**
     *  In the case of an AI, where the computer is not programmed to think strategically about what to buy,
     *  the AI sends all of its gems to market, buys what it can, and is usually left with a lot of change.
     */
    public void addAllGemsForMarket(){
        for(int i = 0; i < diamond; i++){
            gemsForMarket.add(GemType.DIAMOND);
        }
        for(int i = 0; i < sapphire; i++){
            gemsForMarket.add(GemType.SAPPHIRE);
        }
        for(int i = 0; i < emerald; i++){
            gemsForMarket.add(GemType.EMERALD);
        }
        for(int i = 0; i < ruby; i++){
            gemsForMarket.add(GemType.RUBY);
        }
        for(int i = 0; i < onyx; i++){
            gemsForMarket.add(GemType.ONYX);
        }
        for(int i = 0; i < gold; i++){
            gemsForMarket.add(GemType.GOLD);
        }
    }

    /**
     * When a player enters their name as "Hodgson", the cheat is activiated giving them enough
     * bonuses to buy any card for free.
     */
    public void enableCheat(){
        diamondCards = 7;
        sapphireCards = 7;
        emeraldCards = 7;
        rubyCards = 7;
        onyxCards = 7;
        updateStatVisuals();
    }
}
