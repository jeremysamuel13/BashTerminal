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
 * Exception for when the children array of a node is full
 */
public class FullDirectoryException extends RuntimeException {
    public FullDirectoryException(String message){
        super(message);
    }
}
