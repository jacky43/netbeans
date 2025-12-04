package Domaine;

import Domaine.Entite.Piece;
import java.util.Stack;

public class ActionHistory {
    
    private Stack<Piece> undoStack;
    private Stack<Piece> redoStack;
    
    public ActionHistory() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }
    
    public void saveState(Piece piece) {
        Piece copie = piece.clone();
        undoStack.push(copie);
        redoStack.clear();
    }
    
    public Piece undo(Piece currentPiece) {
        if (canUndo()) {
            redoStack.push(currentPiece.clone());
            Piece previous = undoStack.pop();
            return previous;
        }
        return null;
    }
    
    public Piece redo(Piece currentPiece) {
        
        if (canRedo()) {
            undoStack.push(currentPiece.clone());
            Piece next = redoStack.pop();
            System.out.println("Redo reussi, taille redoStack apres: " + redoStack.size());
            return next;
        }
        return null;
    }
    
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}
