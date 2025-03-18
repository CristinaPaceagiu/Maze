package ro.tefacprogramator.m4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CityMap {
    private char[][] maze;
    private int rows;
    private int cols;    

    public char[][] getMaze() {
        return maze;
    }

    public void setMaze(char[][] maze) {
        this.maze = maze;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void readFile() {

        try {
            var lines = Files.readAllLines(Path.of("mazein.txt"));            
            // System.out.println("lines: " + lines);

            String size = lines.subList(0, 1).toString();
            String mazeSize = size.substring(1, size.length()-1);
            
            // System.out.println("mazeSize: " + mazeSize);

            String[] parts = mazeSize.split(" ");
            rows = Integer.parseInt(parts[0]);
            cols = Integer.parseInt(parts[1]);

            System.out.println("rows = " + rows);
            System.out.println("cols = " + cols);

            var map = lines.subList(1, 6);
            // System.out.println("map: " + map);

            maze = new char[rows][cols];
            int i = 0;           
            for (String line : map) {                
                char[] parts2 = line.toCharArray();
                for (int j = 0; j < cols; j++) {
                    // System.out.println(parts2[j]);                   
                    maze[i][j] = parts2[j]; 
                }
                i++;                    
            }             
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }  
    
    public void writeFile(int step, int x, int y) {
        try {
            String line = step + " " + x + " " + y + "\n";

            Files.writeString(
                Path.of("mazeout.txt"), 
                line, 
                StandardOpenOption.APPEND, 
                StandardOpenOption.CREATE
            );
        } catch (IOException e) {
            e.printStackTrace();        
        }
    }

}
