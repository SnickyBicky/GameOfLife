/**
 * Class representing the game's logic
 */
public class GameLogic {

    private static int width  = 21;
    private static int height = 21;
    private int[][] board;

    public GameLogic(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    /**
     * Prints out the board.
     */
    public void printBoard(){
        System.out.println("__________________________");
        for (int y = 0; y < height; y++) {
            String line = "|";    //sets line to | so we have a nice border
            for (int x = 0; x < width; x++) {
                if(this.board[x][y] == 0){    //if cell is dead print [ ]
                    line += "[ ]";
                }else {     //if cell is alive add [X]
                    line += "[X]";
                }
            }
            line += "|";
            System.out.println(line);
        }
        System.out.println("--------------------------");
    }

    /**
     * Sets given cell to 1.
     * @param x coordinateX
     * @param y coordinateY
     */
    public void setAlive(int x, int y){
        this.board[x][y] = 1;
    }

    /**
     * Sets given cell to 0.
     * @param x coordinateX
     * @param y coordinateY
     */
    public void setDead(int x, int y){
        this.board[x][y] = 0;
    }

    /**
     * Counts alive neighbours starting from x-1 y-1 (upper left corner from the alive cell)
     * @param x
     * @param y
     * @return number of alive neighbours
     */
    public int countAliveNeighbours(int x, int y) {
        int count = 0;

            //above the cell
            count += this.board[x - 1][y - 1];
            count += this.board[x][y - 1];
            count += this.board[x + 1][y - 1];

            //left and right to the cell
            count += this.board[x - 1][y];
            count += this.board[x + 1][y];

            //below the cell
            count += this.board[x - 1][y + 1];
            count += this.board[x][y + 1];
            count += this.board[x + 1][y + 1];

        return count;
    }

    /**
     * Updates the board while applying the rules of the game.
     * 1. Any live cell with fewer than two live neighbours dies, as if by underpopulation.
     * 2. Any live cell with two or three live neighbours lives on to the next generation.
     * 3. Any live cell with more than three live neighbours dies, as if by overpopulation.
     * 4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
     */
    public void step(){
        int[][] newBoard = new int[width][height]; //create a new board state

        for (int y = 1; y < height-1; y++) {
            for (int x = 1; x < width-1; x++) { //iterate through the board
                int aliveNeighbours = countAliveNeighbours(x, y); //count neighbours for each cell

                if (this.board[x][y] == 1) {
                    if (aliveNeighbours < 2 || aliveNeighbours > 3) { //rules 1, 2, 3
                        newBoard[x][y] = 0;
                    } else {
                        newBoard[x][y] = 1;
                    }
                } else {
                    if(aliveNeighbours == 3){ //rule 4
                        newBoard[x][y] = 1;
                    }
                }

            }
        }

        this.board = newBoard; //save current state of the board
    }

    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic(8,8);

        //mind: indexing starts from 0, so if we want third cell in third row to be alive, we have to go for 2 2
        gameLogic.setAlive(2,2);
        gameLogic.setAlive(3,2);
        gameLogic.setAlive(4,2);

        gameLogic.printBoard();

        gameLogic.step();

        gameLogic.printBoard();

        gameLogic.step();

        gameLogic.printBoard();
    }

}

