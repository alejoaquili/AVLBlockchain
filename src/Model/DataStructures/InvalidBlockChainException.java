package Model.DataStructures;

public class InvalidBlockChainException extends Exception{

    public InvalidBlockChainException(){
        super();
    }
    public InvalidBlockChainException(String msg){
        super(msg);
    }
}
