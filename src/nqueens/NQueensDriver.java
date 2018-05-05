/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nqueens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        
        System.out.println("Number of attacking queens is: " + board.getNumAttacking());
        
        NQueens test = null;
        System.out.println("Attempting to solve...");

//        if(test.checkIfSolved()){
//            System.out.println("Board was solved!");
//        }else{
//            System.out.println("UNABLE TO SOLVE BOARD");
//        }
//        System.out.println("Number of tests: " + test.getTestIterations());
//        System.out.println("Final board configuration:");
//        printBoard(boardDimension, test.getBestBoard().getBoardStatus());
        while(trys < 100){
            board = new ChessBoard(boardDimension);
            //test = new NQueensSimAn(board);
            test = new NQueensGenetic(board);
            test.solveBoard();
            trys++;
            if(test.checkIfSolved()){
                solved++;
                totalIterations += test.getTestIterations();
            }
            //System.out.println("Test failed - trying again...");
            //System.out.println("Failed with " + test.getTestIterations() + " iterations");
        }
        //System.out.println("Solution found in " + trys + " number of trys.");
        //System.out.println("Number of tests: " + test.getTestIterations());
        //System.out.println("Final board configuration:");
        //printBoard(boardDimension, test.getBestBoard().getBoardStatus());
        System.out.println("Total trys: " + trys);
        System.out.println("Total solved: " + solved);
        averageIterations = totalIterations/solved;
        System.out.println("Average iterations for solution: " + averageIterations);
        printBoard(boardDimension, test.getBestBoard().getBoardStatus());
        
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
