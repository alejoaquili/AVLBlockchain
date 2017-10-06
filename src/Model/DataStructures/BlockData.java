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
    private List<T> modifiedBlocks ;
    private T addedElemnt;
    private  T removedElement;
    public BlockData(){
        modifiedBlocks = new ArrayList<T>();
    }

    public void addModified(T element){
        modifiedBlocks.add(element);
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
        String result = " Modified: ";
        result += modifiedBlocks;

        result+= "\n" + "added: " + getAddedElemnt();

        result += "\n" + "removed: " + getRemovedElement();

        return  result;

    }
}
