import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import com.interactivemesh.jfx.importer.ImportException;

import java.io.File;

import javafx.scene.Group;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

import javafx.scene.shape.MeshView;

public class GemModels
{
     ObjModelImporter objImporter = new ObjModelImporter(); //initializes the model importer
     
    /**
     *  Returns the model for a gem.
     *  
     *  @param gemType - the type of gem to retrieve a model for
     *  @return the model for the desired gem
     */
    public Group getGem(GemType gem){
        Group gemGroup = new Group();
        GemType gemType = gem;
        PhongMaterial gemMat = new PhongMaterial(); 
        File gemFile;
        
        //sets the gemFile to the appropriate resource OBJ file
        // and sets the material for the gem to the correct colour
        switch(gemType){
            case DIAMOND:
            gemFile = new File("./Resources/Diamond.obj");
            gemMat.setDiffuseColor(Color.WHITE);
            break;
            case SAPPHIRE:
            gemFile = new File("./Resources/Sapphire.obj");
            gemMat.setDiffuseColor(Color.BLUE);
            break;
            case EMERALD:
            gemFile = new File("./Resources/Emerald.obj");
            gemMat.setDiffuseColor(Color.GREEN);
            break;
            case RUBY:
            gemFile = new File("./Resources/Ruby.obj");
            gemMat.setDiffuseColor(Color.RED);
            break;
            case ONYX:
            gemFile = new File("./Resources/Onyx.obj");
            gemMat.setDiffuseColor(Color.DIMGREY);
            break;
            case GOLD:
            gemFile = new File("./Resources/Gold.obj");
            gemMat.setDiffuseColor(Color.GOLD);
            break;
            default:
            gemFile = null;
        }
        
        //imports the resource file
        try {            
            objImporter.clear();
            objImporter.read(gemFile);        
        }
        catch (ImportException e) {
            e.printStackTrace();
        }
        
        //combines the various meshes into a single object
        MeshView[] meshViews = objImporter.getImport();
        for (int j = 0; j < meshViews.length; j++){
            meshViews[j].setTranslateX(75);
            meshViews[j].setTranslateY(30);
            meshViews[j].setMaterial(gemMat);
            gemGroup.getChildren().add(meshViews[j]);
        }
        
        return gemGroup; // return the gem model
    }
}
