/*
Game class - Treasure Hunting
A player will move around a grid, digging in spots to try and find treasure

Author - Isaac Wu

Change history:
5/16: Created (Was not here 5/12)
5/17: Tried to do UI & KeyListening
5/19: Gave up on UI and returned to the text version
Full change log in git
*/

/**
 * Design
 * Overall map: private String[][]
 * Player coordinates: private int
 * Treasure location: private int
 * Turn counter: private int
 * Score keeper: private static HashMap<String, Integer>
 * 
 * Methods:
 * -Turn, handle logic of movement and digging
 * -Print out the map
 * -Validate coordinates?

peer reviewer: keira
notes: maybe instead of counting turns, count how many digs

1. You have comments in the header of each file saying what it does and who is the author
2. The purpose of the program and the input required is obvious by just using the program; use print statements to make it obvious to the end user what is going on without them having to read the code
3. All user input has been validated, with clear error messages when the user gives invalid input
4. Uses try/catch (OK to do this indirectly by using the Utils.inputNum()) method. 
5. Uses a switch statement
6. Uses a hash map

 */
public class Game {
    private String[][] map;
    // Player coordinates, Treasure coordinates, Turn number
    private int x, y, tx, ty, digs;
    HighScores hs;

    // Constructor, to initialize the instance variable
    public Game() {
        // Initialize the map into a blank state
        map = new String[10][10];
        hs = HighScores.restore();
        if(hs == null) { hs = new HighScores(); }
    }

    // Initial method, sets up while loop for turns
    public void play() {
        System.out.println("Welcome to Treasure Hunter, where you look for treasure in a 10x10 grid!");
        while(true) {
            switch(Utils.inputStr("P to play, S to look at the current scoreboard, Q to quit\n").toLowerCase()) {
                case "p":
                    initField();
                    System.out.println("Use NESW (North, East, South, West) to move, and D to dig at a location");
                    System.out.println("An H identifies where you are, an X represents a spot that has already been dug, and an ! means that you are at a spot that has already been dug");
                    System.out.println("The \"tile units away\" printed when you dig at a location is the x and y offset added together\n");
                    do {
                        System.out.println(digs+" dig"+(digs==1?"":"s"));
                        displayMap();
                    } while(!action());
                    System.out.println("Congrats, you found the treasure in " + digs + " digs");
                    hs.save(new Score(Utils.inputStr("Enter your name: "), digs));                    
                    break;
                case "s":
                    System.out.println(hs);
                    break;
                case "q":
                    return;
                default:
                    System.out.println("Invalid input");
            }

        }
    }

    private boolean action() {
        switch(Utils.inputStr(">").toLowerCase()) {
            case "north":
            case "n":
                try {
                    map[x-1][y] = (map[x-1][y].equals("X")? "!": "H");
                    map[x][y] = (map[x][y].equals("!")? "X": "-");
                    x--;
                } catch (Exception e) {
                    System.out.println("Invalid movement");
                }
                break;
            case "east":
            case "e":
                try {
                    map[x][y+1] = (map[x][y+1].equals("X")? "!": "H");
                    map[x][y] = (map[x][y].equals("!")? "X": "-");
                    y++;
                } catch (Exception e) {
                    System.out.println("Invalid movement");
                }
                break;
            case "south":
            case "s":
                try {
                    map[x+1][y] = (map[x+1][y].equals("X")? "!": "H");
                    map[x][y] = (map[x][y].equals("!")? "X": "-");
                    x++;
                } catch (Exception e) {
                    System.out.println("Invalid movement");
                }
                break;
            case "west":
            case "w":
                try {
                    map[x][y-1] = (map[x][y-1].equals("X")? "!": "H");
                    map[x][y] = (map[x][y].equals("!")? "X": "-");
                    y--;
                } catch (Exception e) {
                    System.out.println("Invalid movement");
                }
                break;
            case "dig":
            case "d":
                if(map[x][y].equals("H")) {
                    digs++;
                    if(x == tx && y == ty) {
                        map[x][y] = "T";
                        return true;
                    } else {
                        map[x][y] = "!";
                        System.out.println("Unlucky, seems like you were "+(Math.abs(x-tx)+Math.abs(y-ty))+" tile units away");
                    }
                } else {
                    System.out.println("You've already dug here");
                }
                break;
            default:
                System.out.println("Invalid action");
        }
        System.out.println();
        return false;
    }

    private void displayMap() {
        for (String[] row : map) {
            for (String spot : row) {
                System.out.print(spot + " ");
            }
            System.out.println();
        }
    }

    // Initialize the map at the beginning of the game
    private void initField() {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                map[i][j] = "-";
            }
        }
        // Loop is to ensure that you don't spawn on top of the treausre
        do {
            x = (int) (Math.random() * 10);
            y = (int) (Math.random() * 10);
            map[x][y] = "H";
            tx = (int) (Math.random() * 10);
            ty = (int) (Math.random() * 10);
        } while(x == tx && y == ty);
        digs = 0;
    }
}