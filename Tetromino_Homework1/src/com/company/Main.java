package com.company;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
	// write your code here
       // System.out.println(args[0]);
        //System.out.println(args[1]);
        //System.out.println(args[2]);


        int rows = Integer.parseInt(args[0]);
        int columns = Integer.parseInt(args[1]);

        char [][] board = new char[rows][columns];
        for(int i = 0; i < board.length ;++i){
            for(int j = 0; j < board[0].length; ++j){
                board[i][j] = '\0';
            }
        }

        AllPieces pieces = new AllPieces(args[2]);

        //displayBoard(board);
        if(recursiveFunction(board,pieces)){
            System.out.println("------------Solution-------------");
            correctOutputFormat(board);
            displayBoard(board);
        }else{
            System.out.println("?");
        }


    }

    public static boolean recursiveFunction(char[][]board, AllPieces pieces){
        if(pieces.isBoardComplete() == true){
            return true;
        }
        char lastPieceNoGood = '\0';
        int [] coord = findUpperleftMost(board);

        for(int i=0;i< pieces.getNum_pieces();++i){
            Tetromino curPiece = pieces.getNextPiece(i);
            if(curPiece == null){
                return false;
            }

            if(lastPieceNoGood == curPiece.getPiece()){
                pieces.switchLastPieceState(curPiece.getFillChar());
                continue;
            }
            lastPieceNoGood = '\0';

            switch(curPiece.getPiece()){
                case 'I':
                    //Try Horizontal and Recurse
                    if(insertNHorizontally(board,4,curPiece.getFillChar(),coord)) {
                        if(recursiveFunction(board, pieces) == true){
                            return true;
                        }
                        cleanNHorizontally(board,4,coord); // cleanup on the recursive return
                    }
                    //Try vertical and Recurse
                    if(insertNVertically(board,4,curPiece.getFillChar(),coord)) {
                        if(recursiveFunction(board, pieces) == true){
                            return true;
                        }
                        cleanNVertically(board,4,coord); // cleanup on the recursive return
                    }
                    lastPieceNoGood = curPiece.getPiece();
                    pieces.switchLastPieceState(curPiece.getFillChar());
                    // how do I mark the piece as unused and move on??????
                    break;
                case 'O':
                    // there is only one possible way to orient this shit
                    if(insertNHorizontally(board,2,curPiece.getFillChar(),coord)){
                        coord[0] += 1;
                        if(insertNHorizontally(board,2,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces)){
                                return true;
                            }
                            cleanNHorizontally(board,2,coord);
                        }
                        coord[0] -= 1;
                        cleanNHorizontally(board,2,coord);
                    }
                    lastPieceNoGood = curPiece.getPiece();
                    pieces.switchLastPieceState(curPiece.getFillChar());
                    break;
                case 'L':
                    //There is gonna be four orientations here
                    if(insertNVertically(board,3,curPiece.getFillChar(),coord)){
                        coord[0]+=2;coord[1]+=1;
                        if(insertNHorizontally(board,1,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNHorizontally(board,1,coord);
                        }
                        coord[0] -= 2;coord[1]-=1;
                        cleanNVertically(board,3,coord);
                    }
                    if(insertNHorizontally(board,3,curPiece.getFillChar(),coord)){
                        coord[0]+=1;
                        if(insertNVertically(board,1,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNVertically(board,1,coord);
                        }
                        coord[0] -= 1;
                        cleanNHorizontally(board,3,coord);
                    }
                    if(insertNHorizontally(board,2,curPiece.getFillChar(),coord)){
                        coord[1]+=1;coord[0]+=1;
                        if(insertNVertically(board,2,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNVertically(board,2,coord);
                        }
                        coord[1] -= 1;coord[0]-=1;
                        cleanNHorizontally(board,2,coord);
                    }
                    if(insertNVertically(board,2,curPiece.getFillChar(),coord)){
                        coord[0]+=1;coord[1]-=2;
                        if(insertNHorizontally(board,2,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNHorizontally(board,2,coord);
                        }
                        coord[0] -= 1;coord[1]+=2;
                        cleanNVertically(board,2,coord);
                    }
                    lastPieceNoGood = curPiece.getPiece();
                    pieces.switchLastPieceState(curPiece.getFillChar());
                    break;
                case 'P':
                    //There is gonna be four orientations here
                    if(insertNVertically(board,3,curPiece.getFillChar(),coord)){
                        coord[1]+=1;
                        if(insertNHorizontally(board,1,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNHorizontally(board,1,coord);
                        }
                        coord[1]-=1;
                        cleanNVertically(board,3,coord);
                    }
                    if(insertNHorizontally(board,3,curPiece.getFillChar(),coord)){
                        coord[1]+=2;coord[0]+=1;
                        if(insertNVertically(board,1,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNVertically(board,1,coord);
                        }
                        coord[0] -= 1;coord[1]-=2;
                        cleanNHorizontally(board,3,coord);
                    }
                    if(insertNVertically(board,3,curPiece.getFillChar(),coord)){
                        coord[1]-=1;coord[0]+=2;
                        if(insertNVertically(board,1,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNVertically(board,1,coord);
                        }
                        coord[1] += 1;coord[0]-=2;
                        cleanNVertically(board,3,coord);
                    }
                    if(insertNVertically(board,2,curPiece.getFillChar(),coord)){
                        coord[0]+=1;coord[1]+=1;
                        if(insertNHorizontally(board,2,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNHorizontally(board,2,coord);
                        }
                        coord[0]-=1;coord[1]-=1;
                        cleanNVertically(board,2,coord);
                    }
                    lastPieceNoGood = curPiece.getPiece();
                    pieces.switchLastPieceState(curPiece.getFillChar());
                    break;
                case 'T':
                    //There is gonna be four orientations here
                    if(insertNHorizontally(board,3,curPiece.getFillChar(),coord)){
                        coord[1]+=1;coord[0]+=1;
                        if(insertNVertically(board,1,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNVertically(board,1,coord);
                        }
                        coord[1]-=1;coord[0]-=1;
                        cleanNHorizontally(board,3,coord);
                    }
                    if(insertNVertically(board,3,curPiece.getFillChar(),coord)){
                        coord[1]-=1;coord[0]+=1;
                        if(insertNHorizontally(board,1,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNHorizontally(board,1,coord);
                        }
                        coord[0]-=1;coord[1]+=1;
                        cleanNVertically(board,3,coord);
                    }
                    if(insertNHorizontally(board,1,curPiece.getFillChar(),coord)){
                        coord[1]-=1;coord[0]+=1;
                        if(insertNHorizontally(board,3,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNHorizontally(board,3,coord);
                        }
                        coord[1] += 1;coord[0]-=1;
                        cleanNHorizontally(board,1,coord);
                    }
                    if(insertNVertically(board,3,curPiece.getFillChar(),coord)){
                        coord[0]+=1;coord[1]+=1;
                        if(insertNHorizontally(board,1,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNHorizontally(board,1,coord);
                        }
                        coord[0]-=1;coord[1]-=1;
                        cleanNVertically(board,3,coord);
                    }
                    lastPieceNoGood = curPiece.getPiece();
                    pieces.switchLastPieceState(curPiece.getFillChar());
                    break;
                case '2':
                    if(insertNHorizontally(board,2,curPiece.getFillChar(),coord)){
                        coord[1]+=1;coord[0]+=1;
                        if(insertNHorizontally(board,2,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNHorizontally(board,2,coord);
                        }
                        coord[1]-=1;coord[0]-=1;
                        cleanNHorizontally(board,2,coord);
                    }
                    if(insertNVertically(board,2,curPiece.getFillChar(),coord)){
                        coord[0]+=1;coord[1]-=1;
                        if(insertNVertically(board,2,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNVertically(board,2,coord);
                        }
                        coord[0]-=1;coord[1]+=1;
                        cleanNVertically(board,2,coord);
                    }
                    pieces.switchLastPieceState(curPiece.getFillChar());
                    break;
                case '5':
                    if(insertNHorizontally(board,2,curPiece.getFillChar(),coord)){
                        coord[1]-=1;coord[0]+=1;
                        if(insertNHorizontally(board,2,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNHorizontally(board,2,coord);
                        }
                        coord[1]+=1;coord[0]-=1;
                        cleanNHorizontally(board,2,coord);
                    }
                    if(insertNVertically(board,2,curPiece.getFillChar(),coord)){
                        coord[0]+=1;coord[1]+=1;
                        if(insertNVertically(board,2,curPiece.getFillChar(),coord)){
                            if(recursiveFunction(board,pieces) == true){
                                return true;
                            }
                            cleanNVertically(board,2,coord);
                        }
                        coord[0]-=1;coord[1]-=1;
                        cleanNVertically(board,2,coord);
                    }
                    lastPieceNoGood = curPiece.getPiece();
                    pieces.switchLastPieceState(curPiece.getFillChar());
                    break;
            }

        }
        return false;

    }

    /*
    public static boolean add5(Tetromino curPiece, char[][] board, AllPieces pieces, int[] coord){


    }*/

    //-----------------------------------------------------------------------------------------------------------------
    //left to right
    // there is checks that check whether this orientation is possible and returns false if it is not possible
    public static boolean insertNHorizontally(char[][] board, int n, char c, int []startPoint){
        if(startPoint[0] < 0|| startPoint[1]<0)
            return false;
        if(startPoint[0] >=board.length || startPoint[1] >= board[0].length)
            return false;
        for(int i = 0; i< n; ++i){
            if( startPoint[1]+i >= board[0].length || board[startPoint[0]][startPoint[1]+i] != '\0'){
                return false;
            }
        }

        for(int i = 0; i< n; ++i){
            board[startPoint[0]][startPoint[1]+i] = c;
        }

        return true;
    }
    public static boolean cleanNHorizontally(char[][] board, int n, int []startPoint){
        for(int i = 0; i< n; ++i){
            if( startPoint[1]+i >= board[0].length){
                return false;
            }
        }

        for(int i = 0; i< n; ++i){
            board[startPoint[0]][startPoint[1]+i] = '\0';
        }

        return true;
    }
    public static boolean insertNVertically(char[][] board, int n, char c, int []startPoint){
        if(startPoint[0] < 0|| startPoint[1]<0)
            return false;
        if(startPoint[0] >=board.length || startPoint[1] >= board[0].length)
            return false;
        for(int i = 0; i< n; ++i){
            if(startPoint[0]+i >= board.length || board[startPoint[0]+i][startPoint[1]] != '\0'){
                return false;
            }
        }

        for(int i = 0; i< n; ++i){
            board[startPoint[0]+i][startPoint[1]] = c;
        }

        return true;
    }
    public static boolean cleanNVertically(char[][] board, int n, int []startPoint){
        for(int i = 0; i< n; ++i){
            if(startPoint[0]+i >= board.length){
                return false;
            }
        }

        for(int i = 0; i< n; ++i){
            board[startPoint[0]+i][startPoint[1]] = '\0';
        }

        return true;
    }
    //-----------------------------------------------------------------------------------------------------------------
    public static int [] findUpperleftMost(char[][] board){
        int[] ret = new int[2];

        for(int i = 0; i < board.length ;++i){
            for(int j = 0; j < board[0].length; ++j){
                if (board[i][j] == '\0'){
                    ret[0] = i;
                    ret[1] = j;
                    return ret;
                }
            }
        }

        return ret;

    }
    public static boolean isBoardComplete(char[][] board){
        for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board[0].length; ++j){
                if(board[i][j]== '\0'){
                    return false;
                }
            }
        }
        return true;
    }
    public static void displayBoard(char[][] board){
         for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board[0].length; ++j){
                System.out.print(board[i][j]);
            }
             System.out.println();
         }

    }
//---------------------------------------------------------------------------------------------------------------------
    public static void correctOutputFormat(char[][] board){
        HashSet<Character> letters = new HashSet<Character>();
        char toFill = 'a';
        for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board[0].length; ++j){
                if(!letters.contains(board[i][j])){
                    replaceAll(board,board[i][j],toFill);
                    toFill+=1;
                    letters.add(board[i][j]);
                }
            }
        }
    }

    public static void replaceAll(char[][] board, char now, char replaceWith){
        for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board[0].length; ++j){
                if(board[i][j] == now){
                    board[i][j] = replaceWith;
                }
            }
        }
    }
}
