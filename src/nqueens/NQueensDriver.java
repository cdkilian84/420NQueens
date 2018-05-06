//Christopher Kilian
//CS 420 - Spring 2018
//Programming Project 2 - N-Queens

package nqueens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Chris
 */
public class NQueensDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int boardDimension = 21;
        ChessBoard board = new ChessBoard(boardDimension);
        Queen[] queens = board.getBoardStatus();
        int trys = 0;
        int solved = 0;
        double totalIterations = 0.0;
        double averageIterations;
        
//        Random rand = new Random();
//        for(int i = 0; i < 100; i++){
//            double randomVal = rand.nextDouble() * (0.75);
//            System.out.println("randomVal is " + randomVal);
//        }
        
//        List<ChessBoard> tempTest = new ArrayList<>();
//        tempTest.add(new ChessBoard(21));
//        tempTest.add(new ChessBoard(21));
//        tempTest.add(new ChessBoard(21));
//        tempTest.add(new ChessBoard(21));
//        tempTest.add(new ChessBoard(21));
//        for(int i = 0; i < 5; i++){
//            double val = 0.1 + i;
//            tempTest.get(i).setFitness(val);
//        }
//        Collections.shuffle(tempTest);
//        System.out.println("Initially:");
//        for(int i = 0; i < 5; i++){
//            System.out.println("Board fitness: " + tempTest.get(i).getFitness());
//        }
//        System.out.println("After Sorting:");
//        Collections.sort(tempTest, new SortByFitness());
//        for(int i = 0; i < 5; i++){
//            System.out.println("Board fitness: " + tempTest.get(i).getFitness());
//        }
        
        
        //printBoard(boardDimension, queens);
//        double x = 0.1;
//        int count = 0;
//        while(x > 0.0000001){
//            x = 0.98*x;
//            count++;
//        }
//        System.out.println("Count: " + count);
        
//        NQueens test = null;
//        System.out.println("Attempting to solve...");
//        long totalTime = 0;
//        
//        
//
//        while(trys < 500){
//            board = new ChessBoard(boardDimension);
//            //test = new NQueensSimAn(board);
//            test = new NQueensGenetic(boardDimension, 100, 0.3);
//            long startTime = System.nanoTime();
//            test.solveBoard();
//            long elapsedTimeNanos = System.nanoTime() - startTime;
//            totalTime += elapsedTimeNanos;
//            trys++;
//            if(test.checkIfSolved()){
//                solved++;
//                totalIterations += test.getTestIterations();
//            }
//        }
//        long avgTime = totalTime / trys;
//        
//        printBoard(boardDimension, test.getBestBoard().getBoardStatus());
//        System.out.println("Total trys: " + trys);
//        System.out.println("Total solved: " + solved);
//        averageIterations = totalIterations/solved;
//        System.out.println("Average iterations for solution: " + averageIterations);
//        System.out.println("Average time for solution (ns):" + avgTime);
        
        Finder findBestResults = new Finder();
        findBestResults.runTest();

        
    }
    
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
