import javafx.scene.Group;

import javafx.scene.image.ImageView;

public class Noble extends MarketObject
{
    /**
     *  Constructor for a noble.
     *  
     *  @param vp - victory points awarded by the noble
     *  @param path - the path to the visual resource for the noble
     *  @param diamond - the diamond cost of the noble
     *  @param sapphire - the sapphire cost of the noble
     *  @param emerald - the emerald cost of the noble
     *  @param ruby - the ruby cost of the noble
     *  @param onyx - the onyx cost of the noble 
     */
    public Noble(int vp, String path, int diamond, int sapphire, int emerald, int ruby, int onyx){
        super(vp, diamond, sapphire, emerald, ruby, onyx); //calls the market object constructor
        
        //sets the objects visuals to the image resource for the noble
        visuals = new Group(new ImageView("./Resources/" + path));
    }
    
}
