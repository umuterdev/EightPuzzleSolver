import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

// A class that is used for modeling the board in the 8 puzzle.
public class Board {
   // Constants for colors and line thickness
   private static final Color backgroundColor = new Color(145, 234, 255);
   private static final Color boxColor = new Color(31, 160, 239);
   private static final double lineThickness = 0.02;

   // Array to hold the tiles and position of the empty cell
   private Tile[][] tiles = new Tile[3][3];
   private int emptyCellRow, emptyCellCol;

   // Constructor to generate a solvable board
   public Board() {
      generateSolvableBoard();
   }

   // Method to shuffle an array randomly
   private void randomShuffling(int[] array) {
      for (int i = 0; i < array.length; i++) {
         int randIndex = (int) (Math.random() * array.length);
         if (i != randIndex) {
            int temp = array[i];
            array[i] = array[randIndex];
            array[randIndex] = temp;
         }
      }
   }

   // Move the empty cell right if possible
   public boolean moveRight() {
      if (emptyCellCol == 2)
         return false;
      tiles[emptyCellRow][emptyCellCol] = tiles[emptyCellRow][emptyCellCol + 1];
      tiles[emptyCellRow][emptyCellCol + 1] = null;
      emptyCellCol++;
      return true;
   }

   // Move the empty cell left if possible
   public boolean moveLeft() {
      if (emptyCellCol == 0)
         return false;
      tiles[emptyCellRow][emptyCellCol] = tiles[emptyCellRow][emptyCellCol - 1];
      tiles[emptyCellRow][emptyCellCol - 1] = null;
      emptyCellCol--;
      return true;
   }

   // Move the empty cell up if possible
   public boolean moveUp() {
      if (emptyCellRow == 0)
         return false;
      tiles[emptyCellRow][emptyCellCol] = tiles[emptyCellRow - 1][emptyCellCol];
      tiles[emptyCellRow - 1][emptyCellCol] = null;
      emptyCellRow--;
      return true;
   }

   // Move the empty cell down if possible
   public boolean moveDown() {
      if (emptyCellRow == 2)
         return false;
      tiles[emptyCellRow][emptyCellCol] = tiles[emptyCellRow + 1][emptyCellCol];
      tiles[emptyCellRow + 1][emptyCellCol] = null;
      emptyCellRow++;
      return true;
   }

   // Draw the board
   public void draw() {
      StdDraw.clear(backgroundColor);
      for (int row = 0; row < 3; row++)
         for (int col = 0; col < 3; col++) {
            if (tiles[row][col] == null)
               continue;
            Point tilePosition = getTilePosition(row, col);
            tiles[row][col].draw(tilePosition.x, tilePosition.y);
         }
      StdDraw.setPenColor(boxColor);
      StdDraw.setPenRadius(lineThickness);
      StdDraw.square(2, 2, 1.5);
      StdDraw.setPenRadius();
   }

   // Get the position of a tile on the screen
   private Point getTilePosition(int rowIndex, int columnIndex) {
      int posX = columnIndex + 1, posY = 3 - rowIndex;
      return new Point(posX, posY);
   }

   // Calculate the Manhattan distance of the board
   public int manhattanDistance() {
      int distance = 0;
      for (int row = 0; row < 3; row++) {
         for (int col = 0; col < 3; col++) {
            Tile tile = tiles[row][col];
            if (tile != null) {
               int value = tile.getNumber() - 1; // Correct position index
               int targetRow = value / 3;
               int targetCol = value % 3;
               distance += Math.abs(row - targetRow) + Math.abs(col - targetCol);
            }
         }
      }
      return distance;
   }

   // Check if the board is in the goal state
   public boolean isGoal() {
      int count = 1;
      for (int row = 0; row < 3; row++) {
         for (int col = 0; col < 3; col++) {
            Tile tile = tiles[row][col];
            if (tile != null) {
               if (tile.getNumber() != count) {
                  return false;
               }
               count++;
            }
         }
      }
      // Check if the empty space is in the bottom right corner
      return emptyCellRow == 2 && emptyCellCol == 2;
   }

   // Get neighbors of the current board
   public List<Board> neighbors() {
      List<Board> neighbors = new ArrayList<>();
      if (emptyCellRow > 0) {
         Board upNeighbor = cloneBoard();
         upNeighbor.moveUp();
         neighbors.add(upNeighbor);
      }
      if (emptyCellRow < 2) {
         Board downNeighbor = cloneBoard();
         downNeighbor.moveDown();
         neighbors.add(downNeighbor);
      }
      if (emptyCellCol > 0) {
         Board leftNeighbor = cloneBoard();
         leftNeighbor.moveLeft();
         neighbors.add(leftNeighbor);
      }
      if (emptyCellCol < 2) {
         Board rightNeighbor = cloneBoard();
         rightNeighbor.moveRight();
         neighbors.add(rightNeighbor);
      }
      return neighbors;
   }

   // Clone the current board
   private Board cloneBoard() {
      Board clonedBoard = new Board();
      for (int row = 0; row < 3; row++) {
         for (int col = 0; col < 3; col++) {
            Tile tile = tiles[row][col];
            if (tile != null) {
               clonedBoard.tiles[row][col] = new Tile(tile.getNumber());
            }
         }
      }
      clonedBoard.emptyCellRow = emptyCellRow;
      clonedBoard.emptyCellCol = emptyCellCol;
      return clonedBoard;
   }

   // Check if the current board configuration is solvable
   private boolean isSolvable() {
      int inversionCount = 0;
      int[] tilesArray = new int[9]; // Set the array size to 9

      int index = 0;
      for (int row = 0; row < 3; row++) {
         for (int col = 0; col < 3; col++) {
            Tile tile = tiles[row][col];
            if (tile != null && tile.getNumber() != 0) {
               tilesArray[index] = tile.getNumber();
               index++;
            }
         }
      }

      for (int i = 0; i < 8; i++) { // Change loop size to 8
         for (int j = i + 1; j < 9; j++) { // Change loop size to 9
            if (tilesArray[i] > tilesArray[j]) {
               inversionCount++;
            }
         }
      }

      // Depending on the size of the board, inversion count should be even or odd.
      return inversionCount % 2 == 0;
   }

   // Generate a solvable board configuration
   private void generateSolvableBoard() {
      do {
         int[] numbers = new int[9]; // Set the array size to 9
         for (int i = 0; i < 9; i++)
            numbers[i] = i;
         randomShuffling(numbers);

         int arrayIndex = 0;
         for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++) {
               if (numbers[arrayIndex] != 0)
                  tiles[row][col] = new Tile(numbers[arrayIndex]);
               else {
                  emptyCellRow = row;
                  emptyCellCol = col;
               }
               arrayIndex++;
            }
      } while (!isSolvable()); // Repeat until a solvable state is achieved
   }
}