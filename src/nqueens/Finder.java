/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nqueens;

import java.io.File;
import java.io.PrintWriter;

/**
 *
 * @author Chris
 */
public class Finder {
    
    private static int START_POP_RANGE = 10;
    private static int END_POP_RANGE = 90;
    private static double START_MUTATION_RANGE = 0.09;
    private static double END_MUTATION_RANGE = 0.45;
    
    public void runTest(){
        
        StringBuilder output = new StringBuilder();
        int i = START_POP_RANGE;
        
        while(i < END_POP_RANGE){
            double mutation = START_MUTATION_RANGE;
            while(mutation <= END_MUTATION_RANGE){
                NQueens test = null;
                System.out.println("Attempting to solve for population " + i + " and mutation rate " + mutation);
                int trys = 0;
                int solved = 0;
                int totalIterations = 0;
                long totalTime = 0;
                
                while(trys < 800){
                    test = new NQueensGenetic(21, i, mutation);
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
                double averageIterations = 0.0;
                if(solved > 0){
                    averageIterations = totalIterations/solved;
                }
                
                output.append(i).append(",").append(mutation).append(",").append(solved).append(",").append(averageIterations).append(",").append(avgTime).append("\n");
                System.out.println("Results:");
                System.out.println("Solved: " + solved + "/800, Average Iterations: " + averageIterations);
                outputResults(output.toString(), "Genetic-Algorithm-Results.csv");
                mutation += 0.03;
            }
            
            
            i += 5;
        }
        
        outputResults(output.toString(), "Genetic-Algorithm-Results.csv");
    }
    
    
    //Method to output test results to a file. Used to generate CSV files for runtime/cost analysis.
    public static void outputResults(String resultsString, String fileName){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(new File(fileName));
            StringBuilder output = new StringBuilder();
            output.append("Population Size,");
            output.append("Mutation Rate,");
            output.append("Total Solved,");
            output.append("Average Iterations,");
            output.append("Average Time,");
            output.append("\n");
            
            output.append(resultsString);
        
            pw.write(output.toString());
            pw.close();
        }catch(Exception e){
            System.out.println("PROBLEM OUTPUTTING VALUES");
            System.out.println(e.getMessage());
        }
    }
    
}
