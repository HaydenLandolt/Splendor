import java.util.Collections;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Stack;

public class DeckGenerator
{
    /**
     *  Creates a shuffled deck of cards and returns it as a Stack.
     *  
     *  @return the deck
     */
    public Stack<Card> generateLevel1Deck() throws IOException{
        Stack<Card> deck = new Stack<Card>();
        
        File database = new File("./Resources/Card Databases/Level1Cards.txt"); // loads the correct card database
        shuffle(database); //shuffles the database
        
        File shuffledCards = new File("./Resources/Card Databases/Level1Cards_Shuffled.txt"); // loads the shuffled card database
        Scanner input = new Scanner(shuffledCards);
        //adds the shuffled cards to the deck
        while(input.hasNext()){
            Card card = new Card(input.nextInt(), GemType.valueOf(input.next()), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt());
            deck.push(card);
        }
         
        input.close();
        return deck;
    }
    
    /**
     *  Creates a shuffled deck of cards and returns it as a Stack.
     *  
     *  @return the deck
     */
    public Stack<Card> generateLevel2Deck() throws IOException{
        Stack<Card> deck = new Stack<Card>();
        
        File database = new File("./Resources/Card Databases/Level2Cards.txt"); // loads the correct card database
        shuffle(database); //shuffles the database
        
        File shuffledCards = new File("./Resources/Card Databases/Level2Cards_Shuffled.txt"); // loads the shuffled card database
        Scanner input = new Scanner(shuffledCards);
       //adds the shuffled cards to the deck
        while(input.hasNext()){
            Card card = new Card(input.nextInt(), GemType.valueOf(input.next()), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt());
            deck.push(card);
        }
         
        input.close();
        return deck;
    }
    
    /**
     *  Creates a shuffled deck of cards and returns it as a Stack.
     *  
     *  @return the deck
     */
    public Stack<Card> generateLevel3Deck() throws IOException{
        Stack<Card> deck = new Stack<Card>();
        
        File database = new File("./Resources/Card Databases/Level3Cards.txt"); // loads the correct card database
        shuffle(database); //shuffles the database
        
        File shuffledCards = new File("./Resources/Card Databases/Level3Cards_Shuffled.txt"); // loads the shuffled card database
        Scanner input = new Scanner(shuffledCards);
       //adds the shuffled cards to the deck
        while(input.hasNext()){
            Card card = new Card(input.nextInt(), GemType.valueOf(input.next()), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt());
            deck.push(card);
        }
        
        input.close();
        return deck;
    }
    
    /**
     *  Creates a shuffled deck of nobles and returns it as a Stack.
     *  
     *  @return the deck of nobles
     */
    public Stack<Noble> generateNoblesDeck() throws IOException{
        Stack<Noble> deck = new Stack<Noble>();
        
        File database = new File("./Resources/Card Databases/Nobles.txt"); // loads the correct noble database
        shuffle(database); //shuffles the database
        
        File shuffledCards = new File("./Resources/Card Databases/Nobles_Shuffled.txt"); // loads the shuffled nobles database
        Scanner input = new Scanner(shuffledCards);
        //adds the shuffled nobles to the deck
        while(input.hasNext()){
            Noble noble = new Noble(input.nextInt(), input.next(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt(), input.nextInt());
            deck.push(noble);
        }
         
        input.close();
        return deck;
    }
    
    /**
     *  Shuffles the entries in a given file and prints it to a new file
     *  to be used by the deck generating methods.
     *  
     *  @param file - the file to be shuffled
     */
    private void shuffle(File file) throws IOException{
        ArrayList<String> cardList = new ArrayList<String>();
        Scanner input = new Scanner(file);
        Random random = new Random();
        
        //takes each line of the file and add is to the list
        while(input.hasNext()){
         cardList.add(input.nextLine());   
        }
        
        Collections.shuffle(cardList, random); //shuffles the list

        //prints the shuffled list
        printShuffledDeck(cardList, file.getName().substring(0, file.getName().length() - 4));
        input.close();
    }
    
    /**
     * Prints the shuffled list and appends "_Shuffled" to the file name.
     * 
     * @param arrayList - the list of entries to be shuffled
     * @param name - the name of the database less the ".txt" extension
     */
    private void printShuffledDeck(ArrayList<String> arrayList, String name) throws IOException{
        PrintWriter out = new PrintWriter("./Resources/Card Databases/" + name + "_Shuffled.txt");
        for(int i = 0; i < arrayList.size(); i++){
           out.println(arrayList.get(i)); 
        }
        out.close();
    }
}
