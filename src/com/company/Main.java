package com.company;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
        File file = new File("problems/book58.sud");
        String content = new Scanner(file).useDelimiter("\\Z").next();
        Board board = new Board(content);

        Solver solver = new Solver();
        solver.Solve(board);


    }
}
