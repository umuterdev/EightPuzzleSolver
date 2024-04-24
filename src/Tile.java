import java.awt.Color;
import java.awt.Font;

// A class that is used for modeling the numbered square tiles in the 8 puzzle.
public class Tile {
   // Constants for tile colors, number color, box color, line thickness, and font
   private static final Color tileColor = new Color(15, 76, 129);
   private static final Color numberColor = new Color(31, 160, 239);
   private static final Color boxColor = new Color(31, 160, 239);
   private static final double lineThickness = 0.01;
   private static final Font numberFont = new Font("Arial", Font.BOLD, 50);
   private int number; // The number on the tile

   // Constructor to initialize the tile with a number
   public Tile(int number) {
      this.number = number;
   }

   // Getter method for the number on the tile
   public int getNumber() {
      return number;
   }

   // Method to draw the tile on the screen
   public void draw(int posX, int posY) {
      // Draw the filled square representing the tile
      StdDraw.setPenColor(tileColor);
      StdDraw.filledSquare(posX, posY, 0.5);

      // Draw the square border
      StdDraw.setPenColor(boxColor);
      StdDraw.setPenRadius(lineThickness);
      StdDraw.square(posX, posY, 0.5);
      StdDraw.setPenRadius();

      // Draw the number on the tile
      StdDraw.setPenColor(numberColor);
      StdDraw.setFont(numberFont);
      StdDraw.text(posX, posY, String.valueOf(number));
   }
}