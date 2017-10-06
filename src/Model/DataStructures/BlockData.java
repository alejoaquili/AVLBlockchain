package Model.DataStructures;



import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the data that each block of a blockchain stores.
 */

public class BlockData <T> {
    // these are the modify blocks , we store their content as a way of searching them later
    private List<T> modifiedElements ;
    private T addedElemnt;
    private  T removedElement;
    private T searchElement;
    private boolean result;

    public T getSearched() {
        return searchElement;
    }

    public void setSearchElement(T searched) {
        this.searchElement = searched;
    }

    public boolean getResult(){
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public BlockData(){
        result = false;
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
        String result;
        if(searchElement != null){
               result = "Searched Element: " + searchElement;
        }else {
            result = (addedElemnt == null) ? "Added: " + addedElemnt : "Removed: " + removedElement;
            result += "- Modified: " + modifiedElements + "\n";
        }

        result += " -  result: "+ this.result;
        return  result;

    }


    public boolean equals(Object o){
        if(o == null ){
            return false;
        }

        if(o.equals(searchElement) || o.equals(addedElemnt) || o.equals(removedElement)){
            return true;
        }

        if(modifiedElements.contains(o)){
            return true;
        }

        return false;
    }
}
