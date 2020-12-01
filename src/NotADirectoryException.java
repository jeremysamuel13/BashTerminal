/*
 * @author Jeremy Samuel
 * E-mail: jeremy.samuel@stonybrook.edu
 * Stony Brook ID: 113142817
 * CSE 214
 * Recitation Section 3
 * Recitation TA: Dylan Andres
 * HW #5
 */

/**
 * Exception for when a node is a file and not a directory, or when a path
 * does not exist
 */
public class NotADirectoryException extends RuntimeException {
    public NotADirectoryException(String message){
        super(message);
    }
}
