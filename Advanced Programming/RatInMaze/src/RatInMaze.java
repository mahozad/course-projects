public class RatInMaze {

    private static final boolean[][] MAZE = {{true, true, true, false, false}, {false, false,
            true, true, true}, {false, false, false, true, true}, {false, false, false, true,
            true}, {false, false, false, false, true}};
    private int[][] PATH = new int[MAZE.length * MAZE.length][2];
    private static final int SIZE = 5;
    private int Distance = 0;

    public void move(int x, int y) {
        if (x == getSIZE() - 1 && y == getSIZE() - 1) {
            printPath();
            System.exit(0);
//          if it is required that all the possible paths are printed, remove System.exit()
//          statement above and modify the code a little bit to print all the paths correctly
        } else {
            Distance++;
            if (y + 1 < getSIZE() && MAZE[x][y + 1]) {
                PATH[Distance][0] = x;
                PATH[Distance][1] = y + 1;
                move(x, y + 1);
            }
            if (x + 1 < getSIZE() && MAZE[x + 1][y]) {
                PATH[Distance][0] = x + 1;
                PATH[Distance][1] = y;
                move(x + 1, y);
            }
        }
    }

    private void printPath() {
        System.out.println("\nA path was found in the maze:\n");
        for (int i = 0; i < Distance + 1; i++) {
            System.out.print(PATH[i][0] + ", " + PATH[i][1] +
                    (PATH[i][0] == PATH[i + 1][0] ? "\t" : "\n"));
        }
    }

    public int getSIZE() {
        return SIZE;
    }
}
