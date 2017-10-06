package Model.DataStructures;



import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the data that each block of a blockchain stores
 *
 *
 */
public class BlockData <T> {
    // these are the modify blocks , we store their content as a way of searching them later
    private List<T> modifiedElements ;
    private T addedElemnt;
    private  T removedElement;

    public BlockData(){
        modifiedElements = new ArrayList<T>();
    }

    public void addModified(T element){
        modifiedElements.add(element);
    }

    public T getAddedElemnt() {
        return addedElemnt;
    }

    public void setAddedElemnt(T addedElemnt) {
        this.addedElemnt = addedElemnt;
    }

    public T getRemovedElement() {
        return removedElement;
    }

    public void setRemovedElement(T removedElement) {
        this.removedElement = removedElement;
    }

    public String toString(){
        String result = (addedElemnt == null) ? "Added: " + addedElemnt: "Removed: " + removedElement;

        result += "- Modified: " + modifiedElements;

        return  result;

    }
}
