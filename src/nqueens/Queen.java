//Christopher Kilian
//CS 420 - Spring 2018
//Programming Project 2 - N-Queens

package nqueens;

//This class describes a Queen object, which has a column and row value associated with it, along with the dimensions of the board it is to be placed on.
//A queen can move itself up or down in its given column, but cannot move from one column to another.
//Queens can also check whether they are attacking another queen or not.
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
    
    
    //Check if this queen is attacking the indicated queen or not based on their row and column values.
    //Return true if this queen is attacking the other, false if not.
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
    
    
    //Move a queen one space up in her column.
    //Allows wrapping (if moving up/down past bottom/top of board, shift to other end of column).
    public void moveUpColumn(){
        this.row++;
        if(this.row >= boardDimension){
            this.row = 0;
        }
    }
    
    
    //Move a queen one space down in her column.
    //Allows wrapping (if moving up/down past bottom/top of board, shift to other end of column).
    public void moveDownColumn(){
        this.row--;
        if(this.row < 0){
            this.row = boardDimension - 1;
        }
    }

    
    //Getter for row
    public int getRow() {
        return row;
    }
    
    //Getter for column
    public int getColumn() {
        return column;
    }
    
    //Getter for associated board dimension
    public int getBoardDimension(){
        return boardDimension;
    }
     
}
