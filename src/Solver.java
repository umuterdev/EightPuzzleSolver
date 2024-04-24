import java.util.*;

public class Solver {
    // A* search algorithm
    public static void solve(Board initialBoard) {
        PriorityQueue<State> pq = new PriorityQueue<>(); // Priority queue to store states
        Set<Board> visited = new HashSet<>(); // Set to keep track of visited boards
        Map<Board, State> parentMap = new HashMap<>(); // Map to store parent-child relationships for backtracking

        pq.add(new State(initialBoard, 0)); // Add initial state to the priority queue

        while (!pq.isEmpty()) { // Continue until priority queue is empty
            State current = pq.poll(); // Retrieve and remove the state with the lowest priority
            Board currentBoard = current.board; // Retrieve the board from the current state

            if (currentBoard.isGoal()) { // Check if the current board is the goal state
                System.out.println("Solution found in " + current.moves + " moves"); // Print the number of moves required to reach the goal
                drawSolutionPath(parentMap, current); // Draw the solution path
                return; // Exit the method
            }

            visited.add(currentBoard); // Mark the current board as visited

            // Generate possible moves from the current board
            for (Board neighbor : currentBoard.neighbors()) {
                if (!visited.contains(neighbor)) { // Check if the neighbor has not been visited
                    State newState = new State(neighbor, current.moves + 1);
                    pq.add(newState); // Add the neighbor to the priority queue with an updated move count
                    parentMap.put(neighbor, current); // Store the parent-child relationship for backtracking
                }
            }
        }

        System.out.println("No solution found."); // Print a message if no solution is found
    }

    // Method to draw the solution path
    private static void drawSolutionPath(Map<Board, State> parentMap, State finalState) {
        List<Board> path = new ArrayList<>();
        State currentState = finalState;

        // Backtrack from the final state to the initial state
        while (currentState != null) {
            path.add(currentState.board);
            currentState = parentMap.get(currentState.board);
        }

        // Draw each board configuration in the solution path
        StdDraw.clear(); // Clear the canvas
        for (int i = path.size() - 1; i >= 0; i--) {
            path.get(i).draw(); // Draw the current board configuration
            StdDraw.show(); // Show the drawing
            if (i > 0) {
                StdDraw.pause(500); // Pause for a short duration (500 milliseconds) between steps
                StdDraw.clear(); // Clear the canvas for the next drawing
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        // StdDraw setup and board creation
        StdDraw.setCanvasSize(500, 500); // Set canvas size for drawing
        StdDraw.setScale(0.5, 3.5); // Set scale for drawing
        StdDraw.enableDoubleBuffering(); // Enable double buffering for smoother drawing
        Board initialBoard = new Board(); // Create initial board

        // Solve the puzzle
        solve(initialBoard); // Call the solve method to find the solution

        StdDraw.show(); // Display the final solution
    }
}
