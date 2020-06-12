import javafx.scene.Group;

public class MarketObject
{
    Group visuals;    
    int victoryPoints;
    int diamondCost;
    int sapphireCost;
    int emeraldCost;
    int rubyCost;
    int onyxCost;
    
    /**
     *  Constructor for the MarketObject class.
     *  
     *  @param vp - victory points awarded by the object
     *  @param diamond - the diamond cost of the object
     *  @param sapphire - the sapphire cost of the object
     *  @param emerald - the emerald cost of the object
     *  @param ruby - the ruby cost of the object
     *  @param onyx - the onyx cost of the object
     */
    public MarketObject(int vp, int diamond, int sapphire, int emerald, int ruby, int onyx){
        victoryPoints = vp;
        diamondCost = diamond;
        sapphireCost = sapphire;
        emeraldCost = emerald;
        rubyCost = ruby;
        onyxCost = onyx;
    }
    
    /**
     * Getter for the visuals of the object.
     * 
     * @return the visuals for the object.
     */
    public Group getVisuals(){
        return visuals;
    }
    
    /**
     *  Getter for the vicroty points awarded by the object.
     *  
     *  @return the victory points awarded by the object
     */
    public int getVictoryPoints(){
        return victoryPoints;
    }
    
    /**
     * Getter for the diamond cost of the object.
     * 
     * @return the diamond cost of the object.
     */
    public int getDiamondCost(){
     return diamondCost;   
    }
    
    /**
     * Getter for the sapphire cost of the object.
     * 
     * @return the sapphire cost of the object.
     */
    public int getSapphireCost(){
     return sapphireCost;   
    }
    
    /**
     * Getter for the emerald cost of the object.
     * 
     * @return the emerald cost of the object.
     */
    public int getEmeraldCost(){
     return emeraldCost;   
    }
    
    /**
     * Getter for the ruby cost of the object.
     * 
     * @return the ruby cost of the object.
     */
    public int getRubyCost(){
     return rubyCost;   
    }
    
    /**
     * Getter for the onyx cost of the object.
     * 
     * @return the onyx cost of the object.
     */
    public int getOnyxCost(){
     return onyxCost;   
    }
}
