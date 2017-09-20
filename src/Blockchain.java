
/*
* Created by levensworth
* */
public class Blockchain <T> {

    private Node lastNode;
    public final String GENESIS = "0000000000000000000000000000000";
    private int zeros;

    public Blockchain(int zeros){
        if( zeros < 0){
            throw  new IllegalArgumentException();
        }
        this.zeros = zeros;
        lastNode = null;

    }

    public void add(T data){
        Block<T> b = null;
        if(lastNode == null){
            b = createGenesis(data);
        }else{
            Block<T> prev = lastNode.block;
            b = new Block<T>(prev.getIndex()+1, data, prev.getHash(), zeros);
            b.mine();
        }

        Node n = new Node(b);
        n.next = lastNode;
        lastNode = n;
    }

    public Block<T> createGenesis(T data){
        Block<T> b = new Block<T>(0, data, GENESIS, zeros);
        b.mine();
        return b;
    }

    public boolean verify(){
        if(lastNode == null || lastNode.block.getPrevHash().equals(GENESIS)){
            return true;
        }

        Node current = lastNode;
        Block<T> b ;
        while( current != null && current.next != null){
            b = current.block;
            Block<T> next = current.next.block;
            if(!b.getPrevHash().equals(next.getHash())){
                return false;
            }
            current = current.next;
        }
        return true;
    }



    /*
    * @deprecated
    * do not use , stack memory problems with chains bigger than 10 000 elements
    * */
    private boolean verify(Node n, String hash){
        if(n == null || n.block.getPrevHash().equals(GENESIS)){
            return  true;
        }

        if(n.block.getHash().equals(hash)){
            return verify(n.next, n.block.getPrevHash());
        }
        return  false;
    }


    public static void  main(String[] args){
        Blockchain<Integer> b = new Blockchain<>(7);
        for (int i = 0; i < 10; i++) {
            b.add(i);
            System.out.println("finish proccessing "+ i);
        }
        System.out.println(b.verify());
    }

    private class Node {

        public Block<T> block;
        public Node next;

        public Node(Block<T> block){
            if(block == null){
                throw  new IllegalArgumentException("a block must not be null");
            }
            this.block = block;
        }
    }

}