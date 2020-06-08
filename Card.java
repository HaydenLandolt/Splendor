import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.io.File;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Card extends MarketObject
{
    GemType gemType;    

    GemModels gemModels = new GemModels();

    /**
     *  Constructor for a market card.
     *  
     *  @param vp - victory points awarded by the card
     *  @param gem - the type of gem the card awards a bonus for
     *  @param diamond - the diamond cost of the card
     *  @param sapphire - the sapphire cost of the card
     *  @param emerald - the emerald cost of the card
     *  @param ruby - the ruby cost of the card
     *  @param onyx - the onyx cost of the card 
     */
    public Card(int vp, GemType gem, int diamond, int sapphire, int emerald, int ruby, int onyx){
        super(vp, diamond, sapphire, emerald, ruby, onyx); // calls the market object contructor
        
        gemType = gem;
      
        Group gemModelGroup = gemModels.getGem(gem); // gets the gem model for the card
        
        Rectangle cardRect = new Rectangle(106, 140); //sets up the card base and fills it with the proper colour
        if(gem == GemType.DIAMOND){
            cardRect.setFill(Color.THISTLE);
        }
        else if(gem == GemType.SAPPHIRE){
            cardRect.setFill(Color.BLUE);
        }
        else if(gem == GemType.EMERALD){
            cardRect.setFill(Color.GREEN);
        }
        else if(gem == GemType.RUBY){
            cardRect.setFill(Color.RED);
        }
        else {
            cardRect.setFill(Color.BLACK);
        }
        cardRect.setArcHeight(10);
        cardRect.setArcWidth(10);
        cardRect.setStroke(Color.WHITE);
        cardRect.setStrokeWidth(3);

        Text victoryPointsText = new Text("" + victoryPoints); // set the text to display the victory points for the card
        if(victoryPoints == 0){ // only displays if there are victory points to be awarded
            victoryPointsText.setVisible(false);   
        }
        else{
            victoryPointsText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            victoryPointsText.setFill(Color.WHITE);
            victoryPointsText.setTranslateX(15);
            victoryPointsText.setTranslateY(30);
        }
        
        int row = 0;
        int col = 0;

        //Sets up the display for the cost of the card
        GridPane cost = new GridPane();
        cost.setTranslateX(15);
        cost.setTranslateY(55);
        cost.setHgap(10);
        cost.setVgap(10);

        if (diamondCost > 0){
            Circle container = new Circle(15);
            container.setStroke(Color.WHITE);
            container.setStrokeWidth(2);
            container.setFill(Color.THISTLE);
            Text costText = new Text("" + diamondCost);
            costText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            costText.setFill(Color.WHITE);
            costText.setTranslateX(-7);
            costText.setTranslateY(6.5);
            Group costIcon = new Group(container, costText);
            cost.add(costIcon, col, row);
            row++;
        }
        if (sapphireCost > 0){
            Circle container = new Circle(15);
            container.setStroke(Color.WHITE);
            container.setStrokeWidth(2);
            container.setFill(Color.BLUE);
            Text costText = new Text("" + sapphireCost);
            costText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            costText.setFill(Color.WHITE);
            costText.setTranslateX(-7);
            costText.setTranslateY(6.5);
            Group costIcon = new Group(container, costText);
            cost.add(costIcon, col, row);
            if(row == 0)
                row++;
            else
                col++;
        }
        if (emeraldCost > 0){
            Circle container = new Circle(15);
            container.setStroke(Color.WHITE);
            container.setStrokeWidth(2);
            container.setFill(Color.GREEN);
            Text costText = new Text("" + emeraldCost);
            costText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            costText.setFill(Color.WHITE);
            costText.setTranslateX(-7);
            costText.setTranslateY(6.5);
            Group costIcon = new Group(container, costText);
            cost.add(costIcon, col, row);
            if(row == 0)
                row++;
            else if (row == 1 & col == 0)
                col++;
            else
                row--;
        }
        if (rubyCost > 0){
            Circle container = new Circle(15);
            container.setStroke(Color.WHITE);
            container.setStrokeWidth(2);
            container.setFill(Color.RED);
            Text costText = new Text("" + rubyCost);
            costText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            costText.setFill(Color.WHITE);
            costText.setTranslateX(-7);
            costText.setTranslateY(6.5);
            Group costIcon = new Group(container, costText);
            cost.add(costIcon, col, row);
            if(row == 0)
                row++;
            else if (row == 1 & col == 0)
                col++;
            else
                row--;
        }
        if (onyxCost > 0){
            Circle container = new Circle(15);
            container.setStroke(Color.WHITE);
            container.setStrokeWidth(2);
            container.setFill(Color.BLACK);
            Text costText = new Text("" + onyxCost);
            costText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            costText.setFill(Color.WHITE);
            costText.setTranslateX(-7);
            costText.setTranslateY(6.5);
            Group costIcon = new Group(container, costText);
            cost.add(costIcon, col, row);
            if(row == 0)
                row++;
            else if (row == 1 & col == 0)
                col++;
            else
                row--;
        }

        //sets the object's visuals to be the card visuals
        visuals = new Group(cardRect, victoryPointsText, cost, gemModelGroup);
    }
    
    /**
     * Getter for the gem bonus awarded by the card.
     * 
     * @return the gem bonus awarded by the card
     */
    public GemType getGemType(){
     return gemType;   
    }
    
}
