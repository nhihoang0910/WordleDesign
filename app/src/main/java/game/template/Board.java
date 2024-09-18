package game.template;

// import java.io.FileInputStream;
// import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Board {
    private char[][] board;
    private char[] answer; //store each character of the keyword
    private int numHints;
    private static final Random RANDOM = new Random();
    private static List<String> words; //already in alphabetical order?
    private Map<Integer, Character> myGuess = new HashMap<>(); //key is the index of the character
    private Set<String> hints = new HashSet<>(); // storing hints
    private Set<Character> falseChars = new HashSet<>();
    private Set<Character> falseInd = new HashSet<>();
    private static String key;

    public Board() {
        board = new char[6][5];
        answer = new char[5];
        words = new ArrayList<String>();
        numHints = 1;
    }

    //method to load the list of all the words into the game
    public static void loadWords(InputStream in) {
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine()) {
            words.add(sc.nextLine().toLowerCase());
        }
        sc.close();
    }

    public void setCell(int row, int col, char c){
        board[row][col] = Character.toLowerCase(c);
    }
    
    //get a random word from input file
    public String getRandomWord() {
        //get a random index from the list and return the word at that index
        int index = RANDOM.nextInt(words.size());
        key = words.get(index);
        //put each character in the answer array
        for(int i = 0; i < 5; i++){
            answer[i] = key.charAt(i);
        }
        return key;
    }
    public int getNumHints(){
        return numHints;
    }

    //check if the guess is the correct character
    public int checkGuess(int row, int col, char guess){
        for(int i = 0; i < 5; i++){
            if(Character.toLowerCase(guess) == answer[i]){
                if(i == col){
                    if(!myGuess.containsKey(i)){
                        myGuess.put(i, guess);
                    }
                    return 2; //correct character and correct place
                }
                    falseInd.add(guess);
                    System.out.println("false index: " + falseInd);
                    return 1; //correct character but wrong place
            }
        }
        falseChars.add(guess);
        System.out.println("falseChars: " + falseChars);
        return 0; //wrong character
    }

    // private String getFalseChars(){
    //         StringBuilder sb = new StringBuilder();
    //         sb.append("False characters: \n");
    //         for(char c:falseChars){
    //             sb.append(c + "\n ");
    //         }
    //         return sb.toString();
    // }

    // public String getFalseInd(){
    //     StringBuilder sb = new StringBuilder();
    //     sb.append("False characters in wrong place: \n");
    //     for(char c:falseInd){
    //         sb.append(c + "\n ");
    //     }
    //     return sb.toString();
    // }
    
    // public Set<String> getHints(){ 
    //     //return the first 10 words that user can use
    //     //based on what they have guessed correctly and correctly so far
    //     while(hints.size() < 10){
    //         for(String s:words){
    //             // for(char c:falseChars){
    //                 if (falseChars.stream().map(String::valueOf).anyMatch(s::contains)) {
    //                     //convert each char in falseChars to string and check if it is in the word
    //                     continue;
    //                 }
    //                 //case when the user has guessed some characters correctly
    //                 else{
    //                     if(!myGuess.isEmpty()){
    //                         boolean match = true;
    //                         for(Map.Entry<Integer, Character> entry: myGuess.entrySet()){
    //                             char guess = entry.getValue();
    //                             int index = entry.getKey();
    //                             if(s.charAt(index) != guess){
    //                                 match = false;
    //                                 break;
    //                             }
    //                             if(match){
    //                                 if (hints.size() == 10) {
    //                                     break;
    //                                 }
    //                                 hints.add(s);
    //                             }
    //                         }   
    //                     }
    //                     //case when the user has not guessed any character correctly
    //                     //choose random words that have characters from char[] answer
    //                     else{
    //                         if(!falseInd.isEmpty()){
    //                             for(char c:falseInd){
    //                                 if(s.contains(String.valueOf(c))){
    //                                     if (hints.size() == 10) {
    //                                         break;
    //                                     }
    //                                     hints.add(s);
    //                                 }
    //                             }
    //                         }
    //                         else{
    //                             for(char c:answer){
    //                                 if(s.contains(String.valueOf(c))){
    //                                     if (hints.size() == 10) {
    //                                         break;
    //                                     }
    //                                 hints.add(s);
    //                             }
    //                         }
    //                     }
    //                 }
    //             } 
    //         }            
    //     }
    //     return hints;   
    // }
    
    public Set<String> getHints() {
        // return less than 30 words that user can use
        // based on what they have guessed correctly and incorrectly so far
        for (String s : words) {
            if (falseChars.stream().map(String::valueOf).anyMatch(s::contains)) {
                // convert each char in falseChars to string and check if it is in the word
            continue;
            } 
            else {
                if (!myGuess.isEmpty()) {
                    correctCharhint(s);
                } 
                else {
                    if (!falseInd.isEmpty()) {
                        falseIndHint(s);
                    } 
                    else {
                        // choose random words that have characters from char[] answer
                            int index = RANDOM.nextInt(5);
                            if (s.charAt(index) == answer[index]) {
                                if (hints.size() < 30){
                                    hints.add(s);
                                }
                            }
                        }
                }
            }
        }
        //}
        numHints--;
        return hints;
    }

    private void correctCharhint(String s){
        boolean match = true;
            for(Map.Entry<Integer, Character> entry: myGuess.entrySet()){
                char guess = entry.getValue();
                int index = entry.getKey();
                if(s.charAt(index) != guess){
                    match = false;
                    break;
                }
                continue;
            }
                if(match){
                    if(hints.size() <30)
                        hints.add(s);
                }   
    }

    private void falseIndHint(String s){
        boolean match = true;
        for(char c:falseInd){
            int index = getIndex(c);
            if(s.charAt(index) != c){
                match = false;
                break;
            }
            continue;
        }
        if(match){
            if(hints.size() <30)
                hints.add(s);
        }
    }
    
    private int getIndex(char c){ //get the index of the character in the answer array
        for(int i = 0; i < 5; i++){
            if(answer[i] == c){
                return i;
            }
        }
        return -1;
    }
}
