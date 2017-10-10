package Model.DataStructures.AVL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * This class represents the data of the operation done in a {@code AVLTree}that each {@code Block}
 * of a {@code Blockchain} stores.
 */
public class AVLOperationData<T extends Serializable> implements Serializable{
    static final long serialVersionUID = 42L;
    private List<T> modifiedElements ;
    private T element;
    private String operation;
    private boolean result;

    static final String insert = "Added";
    static final String remove = "Removed";
    static final String search = "Searched";

    /**
     *Returns a new {@code AVLOperationData} object.
     */
    public AVLOperationData(){
        result = false;
        modifiedElements = new ArrayList<T>();
    }

    /**
     * This method return the element.
     * @return a generic type with the element.
     */
    public T getElement() {
        return element;
    }

    /**
     * This method set an element to be operated.
     * @param element a generic type with a specified element.
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Returns true if the Operation in the {@code AVLTree} result successful. Return false otherwise.
     * @return a boolean with the state of the success of putting into effect an Operation linked with a {@code AVLTree}
     * object.
     */
    public boolean getResult(){
        return result;
    }

    /**
     * This method set the state of the implementation of an Operation in a {@code AVLTree}.
     * @param result the state of the success of putting into effect an Operation linked with a {@code AVLTree}
     * object.
     */
    public void setResult(boolean result) {
        this.result = result;
    }

    
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * This method add a modified element to the internal list of elements that has been modified.
     * @param element the modified element.
     */
    public void addModified(T element){
        modifiedElements.add(element);
    }

    /**
     * This method returns true if the object o is contained in the searched added or removed element of the
     * {@code AVLOperationData} or if o is contained in the list of modified elements.
     * @param o an {@code Object} that is the element to evaluate.
     * @return a boolean that is true if the element is contained.
     */
    public boolean contains(Object o){
        if(o == null ){
            return false;
        }
        if(o.equals(element)){
            return true;
        }
        return modifiedElements.contains(o);
    }

    /**
     * This method returns the {@code String} representation of the {@code AVLOperationData}.
     * @return a {@code String} that represents the {@code AVLOperationData} object.
     */
    @Override
    public String toString(){
        return operation + ": " + element + " - Modified Elements: " + modifiedElements + " - Result: " + result;
    }

}
