package Model.DataStructures;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the data that each {@code Block} of a {@code Blockchain} stores.
 */
public class BlockData <T> implements BlockDataInterface {
    private List<T> modifiedElements ;
    private T addedElemnt;
    private  T removedElement;
    private T searchElement;
    private boolean result;

    /**
     *Returns a new {@code BlocData} object.
     */
    public BlockData(){
        result = false;
        modifiedElements = new ArrayList<T>();
    }

    /**
     * This method return the search element.
     * @return a generic type with the search element.
     */
    public T getSearched() {
        return searchElement;
    }

    /**
     * This method set an element to be searched.
     * @param searched a generic type with a specified element.
     */
    public void setSearchElement(T searched) {
        this.searchElement = searched;
    }

    /**
     * Returns true if the Operation in the {@code AVLTree} result successful. Return false otherwise.
     * @return a boolean with the state of the success of putting into effect an Operation linked with a {@AVLTree}
     * object.
     */
    public boolean getResult(){
        return result;
    }

    /**
     * This method set the state of the implementation of an Operation in a {@code AVLTree}.
     * @param result the state of the success of putting into effect an Operation linked with a {@AVLTree}
     * object.
     */
    public void setResult(boolean result) {
        this.result = result;
    }

    /**
     * This method add a modified element to the internal list of elements that has been modified.
     * @param element the modified element.
     */
    public void addModified(T element){
        modifiedElements.add(element);
    }

    /**
     * This method returns the added element in the {@code BlockData} if there are a added element.
     * @return the added element.
     */
    public T getAddedElemnt() {
        return addedElemnt;
    }

    /**
     * Set the added element.
     * @param addedElemnt the added element.
     */
    public void setAddedElemnt(T addedElemnt) {
        this.addedElemnt = addedElemnt;
    }

    /**
     * This method return the removed element.
     * @return the removed element in the {@code BlockData}.
     */
    public T getRemovedElement() {
        return removedElement;
    }

    /**
     * Set the removed element.
     * @param removedElement the removed element.
     */
    public void setRemovedElement(T removedElement) {
        this.removedElement = removedElement;
    }

    /**
     * This method returns true if the object o is contained in the searched added or removed element of the
     * {@code BlockData} or if o is contained in the list of modified elements.
     * @param o an {@code Object} that is the element to evaluate.
     * @return a boolean that is true if the element is contained.
     */
    public boolean contains(Object o){
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

    /**
     * This method returns the {@code String} representation of the {@code BlockData}.
     * @return a {@code String} that represents the {@code BlockData} object.
     */
    @Override
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

}
