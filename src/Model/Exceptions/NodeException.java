package Model.Exceptions;

import javax.xml.soap.Node;

/**
 * Created by SB on 06/10/2017.
 */
public class NodeException extends Throwable {


    public NodeException(String msg){
        super(msg);
    }

    public NodeException(){
        super();
    }
}
