/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MazeApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Aya Kurtam
 */

/*
(*)-> represent a block
(.) represent an empty area
c -> represent car 
e -> represent the exit 
*/
public class MazeApplication {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    static boolean x;
    static int exit_i,exit_j;
    static int start_i = 0,start_j = 0;
    static int sizecol = 0;
    static int sizerow=0;
    final static char block='*';
    final static char path='1';
    static char [][] matrix ;
    static char[][] solution;  // matrix to store the solution
    public static void main(String[] args) throws FileNotFoundException {
        MazeDirections obj = new MazeDirections();
        String s;
        int Flag =0;
        char [] c ;
        ArrayList <String> Maze = new ArrayList<>();
        File file = new File("Maze 4.txt");
        Scanner scan = new Scanner(file);
        while(scan.hasNext()){                                      //O(sizerow)
            s = scan.next(); // to scan the characters of the file
            sizecol = s.length(); 
            Maze.add(s); // Add the text to the maze arraylist
            sizerow++; // To count the number of iterations
        }
        matrix = new char[sizerow][sizecol];
        solution = new char[sizerow][sizecol];
        for(int i=0;i<sizerow;i++){                     //O(m * n)
            c = Maze.get(i).toCharArray();
            for(int u=0;u<sizecol;u++){
                matrix[i][u]=c[u];
            }
        }
        System.out.println("The Maze");           // this is optional no need foe its complexity
        for(int i=0;i<sizerow;i++){
            for(int u=0;u<sizecol;u++){
                System.out.print(matrix[i][u]+"  ");
            }
            System.out.println();
        }
        start_End_points(matrix,sizerow,sizecol);
        LinkedList<MazeDirections> list = new LinkedList<MazeDirections>();
        list.add(new MazeDirections(start_i, start_j));
        MazeDirections current,next;
        int N=1,E=0,W=0,S=0;
        while (!list.isEmpty()) { //worst case O(m * n)
            //get current position
            current = list.removeFirst();
            //System.out.println("The coordinates are"+current.i+","+current.j);
            // to be sure if it reach the goal
            if (isFinal(current)) { //if the goal is reached then exit, no need for further exploration.
                x=true;
                break; 
            }
            solution[current.i][current.j]= (char)mark(current, path);
            //add its neighbors in the queue
            next = current.north();    //move up
            if (isInMaze(next) && isClear(next)&&((N==1||W==1))/*||Flag==0*/) { 
                list.add(next);
                //Flag = 1;
                N=1; E=1; W=0; S=0;
            }
            next = current.east();    //move right
            if (isInMaze(next) && isClear(next)&&(E==1||N==1)) {
                list.add(next);
                N=0; E=1; S=1; W=0;
            }
            next = current.south();   //move down
            if (isInMaze(next) && isClear(next)&&(S==1||E==1)) {
                list.add(next);
                E=0; W=1; S=1; N=0;
            }
            next = current.west();    //move left 
            if (isInMaze(next) && isClear(next)&&(W==1||S==1)) {
                list.add(next);
                N=1; S=0; W=1; E=0;
            }
        }
        if (!list.isEmpty()) {               //you exited before the list was emptied, meaning that you found the solution and exited.
            System.out.println("You Got it :)\n");
        }else if (x){
            System.out.println("You Got it :)\n");
        }else {                             //the list is empty, meaning that you went through all nodes and didn't find the solution.
            System.out.println("You Are stuck in the maze\n");
        }
        System.out.println("The path of the BFS algorithm\n");
        for (int i = 0; i < sizerow ; i++) {      //go to every row     // optinal
            for (int J = 0; J < sizecol ; J++) {  //go to every element in the row
                System.out.print(matrix[i][J]);   //print the element
                System.out.print(' ');          //print space
            }
            System.out.println();               // go to new line
        }
        System.out.println();
        if (x){
            printSolution(); // to print the shortest way
        } 
    }
    //functions
    public static void start_End_points(char [][] matrix,int row_size,int col_size){ // O(m*n)
        for(int i=0 ; i<row_size ; i++){
            for(int j=0 ; j<col_size ; j++){
                if (matrix[i][j]=='c'){
                    start_i = i;
                    start_j = j;
                    solution[i][j]='c';
                    System.out.println("\nStart point coordinations ("+i +","+ j+")\n");
                }
                if(matrix[i][j]=='e'){
                    exit_i = i;
                    exit_j = j;
                    solution[i][j]='e';
                    System.out.println("\nGoal coordinations ("+i +","+ j+")\n");
                }
            }
        }
    }
    static public boolean isFinal (int i, int j){  //Q(1)
        boolean x;
        if(exit_i == i && exit_j ==j){
            x= true;
        }else {
            x=false;
        }
        return x;
    }
    // O(1)
    static public boolean isFinal(MazeDirections pos) {  //overloaded of isFinal(int i, int j) , parameter is the node itself
        return isFinal(pos.i(), pos.j());  //extract the position of the cell (i and j) and call the first method isFinal(int i, int j)
    }
    // O(1)
    static public boolean isInMaze(int i, int j) {  //parameters are the position (i and j) of the cell

        if (i >= 0 && i < sizerow && j >= 0 && j < sizerow) {
            return true;
        } else {
            return false;
        }
    }
    // O(1)
    static public boolean isInMaze(MazeDirections pos) {   //overloaded of isInMaze(int i, int j) , parameter is the node itself
        return isInMaze(pos.i(), pos.j());   //extract the position of the cell (i and j) and call the first method isInMaze(int i, int j)
    }
    // return true if the node is equal to 0 (White, Unexplored)
    static public boolean isClear(int i, int j) { // O(1)
        assert (isInMaze(i, j));
        return (matrix[i][j] != block && matrix[i][j] != path);
    }
    // O(1)
    static public boolean isClear(MazeDirections pos) {   //overloaded of isClear(int i, int j) , parameter is the node itself
        return isClear(pos.i(), pos.j());   //extract the position of the cell (i and j) and call the first method isClear(int i, int j)
    }
    // O(1)
    static public char mark(int i, int j, char value) {
        assert (isInMaze(i, j));  // it is used for test. if the condition is false it will throw an error named AssertionError.
        char temp = matrix[i][j];    // store the original value in temp
        matrix[i][j] = value;       // put the value from the parameter in maze cell with corresponding i,j
        return temp;              // return original value
    }
    // O(1)
    static public int mark(MazeDirections pos, char value) {   //overloaded of mark(int i, int j, int value) , parameter is the node itself and the value we want to insert
        return mark(pos.i(), pos.j(), value);   //extract the position of the cell (i and j) and call the first method mark(int i, int j, int value)
    }
    static void printSolution()  // O(m*n)
    {
        System.out.println("The shortest path of the BFS algorithm");
        for(int i=0;i<sizerow;i++){
            for(int j=0;j<sizecol;j++){
                System.out.print(solution[i][j]+" ");
            }
            System.out.println();
        }
    }
}