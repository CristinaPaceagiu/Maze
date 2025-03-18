package ro.tefacprogramator.m4;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        
        // Game game = new Game();
        // game.play();

        CityMap cm1 = new CityMap();
        cm1.readFile();        

        char[][] grid = cm1.getMaze();      
        printGrid(grid);

        int[][] mapIntRomeo = new int[cm1.getRows()][cm1.getCols()];
        int[][] mapIntJuliet = new int[cm1.getRows()][cm1.getCols()];
        int[][] mapRomeo = new int[cm1.getRows()][cm1.getCols()];
        int[][] mapJuliet = new int[cm1.getRows()][cm1.getCols()];

        int xRomeo = 0;
        int yRomeo = 0;
        int xJuliet = 0;
        int yJuliet = 0;
        
        for (int i = 0; i < mapIntRomeo.length; i++) {
            for (int j = 0; j < mapIntRomeo[0].length; j++) {
                if (grid[i][j] == '#') {
                    mapIntRomeo[i][j] = -2;
                    mapIntJuliet[i][j] = -2;
                } else if (grid[i][j] == 'R') {
                    mapIntRomeo[i][j] = -1;
                    mapIntJuliet[i][j] = -1;
                    xRomeo = i;
                    yRomeo = j;
                } else if (grid[i][j] == 'J') {
                    mapIntRomeo[i][j] = -1;
                    mapIntJuliet[i][j] = -1;
                    xJuliet = i;
                    yJuliet = j;
                } else {
                    mapIntRomeo[i][j] = -1;
                    mapIntJuliet[i][j] = -1;
                }
            }
        }        

        System.out.println("mapInt: ");
        printGrid(mapIntRomeo); 
        System.out.println("xRomeo = " + xRomeo);
        System.out.println("yRomeo = " + yRomeo);
        System.out.println("xJuliet = " + xJuliet);
        System.out.println("yJuliet = " + yJuliet);
        
        Character Romeo = new Character("Romeo", xRomeo, yRomeo);
        Character Juliet = new Character("Juliet", xJuliet, yJuliet);
        
        mapRomeo = play(mapIntRomeo, Romeo.getX(), Romeo.getY());
        System.out.println("mapRomeo: ");
        printGrid(mapRomeo);
             
        mapJuliet = play(mapIntJuliet, Juliet.getX(), Juliet.getY());        
        System.out.println("mapJuliet: ");
        printGrid(mapJuliet);

        int xMeet = -1;
        int yMeet = -1;
        int step = -1;

        for (int i = 0; i < mapRomeo.length; i++) {
            for (int j = 0; j < mapRomeo[0].length; j++) {
                if (mapRomeo[i][j] >= 0 && mapJuliet[i][j] >= 0 && mapRomeo[i][j] == mapJuliet[i][j]){
                    xMeet = i+1;
                    yMeet = j+1;
                    step = mapRomeo[i][j];
                    break;
                }
            }
        }

        if (xMeet >= 0 && yMeet >= 0 && step >= 0) {
            System.out.println("Meeting point: " + step + " " + xMeet + " " + yMeet);
            cm1.writeFile(step, xMeet, yMeet);
        } else {
            System.out.println("No meeting point");
        }

    }

    public static int[][] play(int[][] grid, int xStart, int yStart) {

        final int[][] DIRECTIONS = {
            {0, 1}, // right
            {1, 0}, // down
            {0, -1}, // left
            {-1, 0}, // up
        };

        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{xStart, yStart});
        grid[xStart][yStart] = 0;

        //printGrid(grid);

        while (!queue.isEmpty()) {
            int[] current = queue.poll();            
            int x = current[0];
            int y = current[1];

            for (int[] direction : DIRECTIONS) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (isValid(newX, newY, grid.length, grid[0].length) && grid[newX][newY] == -1) {
                    grid[newX][newY] = grid[x][y] + 1;
                    queue.offer(new int[]{newX, newY});
                }
            }
        }

        return grid;
    }

    public static boolean isValid(int x, int y, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    public static void printGrid(char[][] grid) {        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.printf("%3c", grid[i][j]); 
            }
            System.out.println();
        }
    }

    public static void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.printf("%3d", grid[i][j]); 
            }
            System.out.println();
        }
    }
}
