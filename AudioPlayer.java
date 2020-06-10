import javax.sound.sampled.*;
import javax.sound.sampled.Control;
import java.io.File;
public class AudioPlayer
{
    Clip clip;
    
    FloatControl musicVolume;
    
    //gain levels for the audio
    private int musicGain = -10;
    private int fxGain = -8;
    
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
        clip.loop(Clip.LOOP_CONTINUOUSLY); //sets the background audio file to loop
        
        //sets the volume of the music
        musicVolume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        musicVolume.setValue(musicGain);
        
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
        
        //sets the effect volume
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
        stop(); //stops the background music
        
        Clip victoryClip;
        
        AudioInputStream ais;      
        ais = AudioSystem.getAudioInputStream(new File("./Resources/Sounds/victoryFanfare.wav")); 
        
        victoryClip = AudioSystem.getClip();
        
        //after the victory fanfare is played, restart the background music
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
        
        //set the volume of the victory fanfare (same volume as the FX)
        FloatControl gainControl = (FloatControl) victoryClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(fxGain);
        
        victoryClip.start();
    }
    
    /**
     * Stops the background music.
     */
    public void stop(){
        clip.stop(); // stop the background music
    }
    
    /**
     * Sets the gain on the background music.
     * 
     * @param gain - the gain of the background music
     */
    public void setMusicVol(int gain){
       if(musicVolume != null){
       musicVolume.setValue(gain);
       musicGain = gain;
    }
    }
    
    /**
     * Sets the gain on the the effects and victory fanfare.
     * 
     * @param gain - the gain of the effects and victory fanfare
     */
    public void setFXVol(int gain){
       fxGain = gain;
    }
    
    /**
     * Getter for the current gain on the backGround music, used when
     * reinstatiating the sound controls in a new window.
     * 
     * @return the gain on the background music
     */
    public int getMusicGain(){
     return musicGain;   
    }
    
    /**
     * Getter for the current gain on the effects and victory fanfare, used when
     * reinstantiating the sound controls in a new window.
     * 
     * @return the gain on the effects and victory fanfare
     */
    public int getFXGain(){
     return fxGain;   
    }
}
