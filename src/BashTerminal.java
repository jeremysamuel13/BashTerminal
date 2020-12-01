/*
 * @author Jeremy Samuel
 * E-mail: jeremy.samuel@stonybrook.edu
 * Stony Brook ID: 113142817
 * CSE 214
 * Recitation Section 3
 * Recitation TA: Dylan Andres
 * HW #5
 */

import java.util.Scanner;

/**
 * BashTerminal class
 * Takes all terminal inputs, in which alters the DirectoryTree.
 */
public class BashTerminal {

    /**
     * main method
     */
    public static void main(String[] args) {
        DirectoryTree g = new DirectoryTree();

        Scanner scan = new Scanner(System.in);
        boolean active = true;
        String input;
        System.out.println("Starting bash terminal.");

        //loop that allows user to continually execute commands until they
        // type "exit"
        while(active) {
            System.out.print("[jesamuel@home]: $ ");
            input = scan.nextLine();
            try {
                if (input.equals("exit")) {
                    System.out.println("Program terminating normally");
                    active = false;
                } else if (input.equals("pwd")) {
                    System.out.println(g.presentWorkingDirectory());
                } else if (input.equals("ls")) {
                    System.out.println(g.listDirectory());
                } else if (input.equals("ls -R")) {
                    g.printDirectoryTree();
                } else if (input.startsWith("cd ")) {
                    try {
                        g.changeDirectory(input.substring(3));
                    }catch(NotADirectoryException e){
                        System.out.println(e.getMessage());
                    }
                } else if (input.startsWith("mkdir ")) {
                    try {
                        g.makeDirectory(input.substring(6));
                    }catch (IllegalArgumentException | FullDirectoryException e){
                        System.out.println(e.getMessage());
                    }
                } else if (input.startsWith("touch ")) {
                    try {
                        g.makeFile(input.substring(6));
                    }catch (IllegalArgumentException | FullDirectoryException |
                            NotADirectoryException e){
                        System.out.println(e.getMessage());
                    }
                } else if (input.startsWith("mv ")) {


                    String i = input.substring(3);
                    String[] s = i.split("root");
                    if(s.length == 3) {
                        try {
                            g.moveNodeTo("root" + s[1].trim(),
                                    "root" + s[2].trim());
                        }catch (FullDirectoryException e){
                            System.out.println(e.getMessage());
                        }
                    }else{
                        System.out.println("Invalid input");
                    }

                }else if(input.startsWith("find ")){
                    try {
                        if(!(g.findNode(input.substring(5), g.root, false)))
                            System.out.println("ERROR: No such file exits.");
                    }catch (NotADirectoryException e){
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Input is invalid");
                }
            }catch (StringIndexOutOfBoundsException e){
                System.out.println("Input is invalid");
            }
        }
    }
}
