//Christopher Kilian
//CS 420 - Spring 2018
//Programming Project 2 - N-Queens

package nqueens;


//Driver class for the NQueens project - this driver is set up to run 1000 tests on both simulated annealing and genetic algorithms for
//a 21 queens problem and display the results of these tests. To see the printout of a final board state (the last one tested) for each algorithm,
//uncomment the "printBoard" lines.
public class NQueensDriver {

    public static void main(String[] args) {
        int boardDimension = 21;
        ChessBoard board;
        int trys = 0;
        int solved = 0;
        double totalIterations = 0.0;
        double averageIterations = 0.0;
        long totalTime = 0;
        NQueens test = null;
        
        //Run the test for simulated annealing 1000 times and get averages
        System.out.println("Running 1000 tests with Simulated Annealing...");
        while(trys < 1000){
            board = new ChessBoard(boardDimension);
            test = new NQueensSimAn(board);
            long startTime = System.nanoTime();
            test.solveBoard();
            long elapsedTimeNanos = System.nanoTime() - startTime;
            totalTime += elapsedTimeNanos;
            trys++;
            if(test.checkIfSolved()){
                solved++;
                totalIterations += test.getTestIterations();
            }
        }
        long avgTime = totalTime / trys;
        
        //printBoard(boardDimension, test.getBestBoard().getBoardStatus());
        if(solved > 0){
            averageIterations = totalIterations/solved;
        }
        System.out.println("Total trys: " + trys);
        System.out.println("Total solved: " + solved);
        System.out.println("Average iterations for solution: " + averageIterations);
        System.out.println("Average time for solution (ns): " + avgTime + "\n\n");
        //printBoard(boardDimension, test.getBestBoard().getBoardStatus());
        
        //reset for Genetic algorithm
        trys = 0;
        solved = 0;
        totalIterations = 0.0;
        averageIterations = 0.0;
        totalTime = 0;
        avgTime = 0;
        
        System.out.println("Running 1000 tests with Genetic algorithm...");
        while(trys < 1000){
            test = new NQueensGenetic(boardDimension);
            long startTime = System.nanoTime();
            test.solveBoard();
            long elapsedTimeNanos = System.nanoTime() - startTime;
            totalTime += elapsedTimeNanos;
            trys++;
            if(test.checkIfSolved()){
                solved++;
                totalIterations += test.getTestIterations();
            }
        }
        avgTime = totalTime / trys;
        if(solved > 0){
            averageIterations = totalIterations/solved;
        }
        
        System.out.println("Total trys: " + trys);
        System.out.println("Total solved: " + solved);
        System.out.println("Average iterations for solution: " + averageIterations);
        System.out.println("Average time for solution (ns): " + avgTime);
        //printBoard(boardDimension, test.getBestBoard().getBoardStatus());
        
    }
    
    
    //Method to print out a visual representation of the ChessBoard
    public static void printBoard(int boardDimension, Queen[] queens){
        for(int i = boardDimension-1; i >= 0; i--){
            StringBuilder boardRow = new StringBuilder();
            for(int j = 0; j < boardDimension; j++){
                boolean queenFlag = false;
                for(Queen current : queens){
                    if((current.getRow() == i) && (current.getColumn() == j)){
                        queenFlag = true;
                        break;
                    }
                }
                
                if(queenFlag){
                    boardRow.append(" Q ");
                }else{
                    boardRow.append(" - ");
                }
            }
            System.out.println(boardRow.toString());
        }
    }
    
}
