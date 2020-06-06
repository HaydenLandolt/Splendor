import javax.sound.sampled.*;
import javax.sound.sampled.Control;
import java.io.File;
public class AudioPlayer
{
    Clip clip;
    
    FloatControl musicVolume;
    
    //Set initial volume levels
    private int musicGain = -10;
    private int fxGain = -6;
    
    /**
     *  Continuously plays the background music when called.
     *  
     *  @param file - the background music.
     */
    public void play(File file) throws Exception{
        AudioInputStream ais;
      
        ais = AudioSystem.getAudioInputStream(file); 
        
        clip = AudioSystem.getClip();
  
        clip.open(ais);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        
        musicVolume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        
        clip.start();
    }

    /**
     *  Plays an audio file passed to it by another method.
     *  
     *  @param file - the audio file to be played.
     */
    public void playEffect(File file) throws Exception{
        Clip effectClip;
        
        AudioInputStream ais;
      
        ais = AudioSystem.getAudioInputStream(file); 
        
        effectClip = AudioSystem.getClip();
  
        effectClip.open(ais);
        
        FloatControl gainControl = (FloatControl) effectClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(fxGain);
        
        effectClip.start();
    }
    
    /**
     *  Stops the background music and plays the victory file.
     *  
     *  @param file - the background music.
     */
    public void victoryFanfare() throws Exception{
        stop();
        
        Clip victoryClip;
        
        AudioInputStream ais;      
        ais = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/victory.wav")); 
        
        victoryClip = AudioSystem.getClip();
        
        victoryClip.addLineListener(e -> {
    if (e.getType() == LineEvent.Type.STOP && Main.backgroundMusicIsPlaying) {
      try{
          play(Main.berwickCourt);
        }
        catch(Exception exc){
         exc.printStackTrace();   
        }
    }
});
  
        victoryClip.open(ais);
        
        FloatControl gainControl = (FloatControl) victoryClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(fxGain);
        
        victoryClip.start();
    }
    
    public void stop(){
        clip.stop();
    }
    
    public void setMusicVol(int gain){
       if(musicVolume != null){
       musicVolume.setValue(gain);
       musicGain = gain;
    }
    }
    
    public void setFXVol(int gain){
       fxGain = gain;
    }
    
    public int getMusicGain(){
     return musicGain;   
    }
    
    public int getFXGain(){
     return fxGain;   
    }
}
