/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nqueens;

/**
 *
 * @author Chris
 */
public class Queen {
    private int row;
    private int column;
    private int boardDimension;
    
    //generate a queen from scratch using provided values
    public Queen(int row, int column, int boardDimension){
        this.row = row;
        this.column = column;
        this.boardDimension = boardDimension;
    }
    
    //create a copy of another queen object
    public Queen(Queen toCopy){
        this.row = toCopy.getRow();
        this.column = toCopy.getColumn();
        this.boardDimension = toCopy.getBoardDimension();
    }
    
    public boolean checkAttack(Queen otherQueen){
        boolean attacked = false;
        int otherRow = otherQueen.getRow();
        int otherColumn = otherQueen.getColumn();
        
        //check if the other queen is in a row, column, or diagonal with this queen
        //Note that two queens share a diagonal if the row and column differences between the two are the same
        if((otherColumn == this.column) || (otherRow == this.row) ||
                (Math.abs(otherRow - this.row) == Math.abs(otherColumn - this.column))){
            attacked = true;
        }
        
        return attacked;
    }
    
    //for both moves, allow wrapping (if moving up/down past bottom/top of board, shift to other end of column)
    public void moveUpColumn(){
        this.row++;
        if(this.row >= boardDimension){
            this.row = 0;
        }
    }
    
    public void moveDownColumn(){
        this.row--;
        if(this.row < 0){
            this.row = boardDimension - 1;
        }
    }

    
    
    public int getRow() {
        return row;
    }
    
    
    public int getColumn() {
        return column;
    }
    
    public int getBoardDimension(){
        return boardDimension;
    }

    
    public void setRow(int row) {
        if(row < boardDimension){
            this.row = row;
        }
    }

//    public void setColumn(int column) {
//        if(column < boardDimension){
//            this.column = column;
//        }
//    }
    
    
}
