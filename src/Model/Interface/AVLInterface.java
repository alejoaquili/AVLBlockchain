package Model.Interface;

import Model.AVLBlockchain;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by SB on 10/10/2017.
 */
public class AVLInterface {
    public static void main(String [] args) {
        System.out.println("initializing blockchain... ");
        System.out.println("how many zeros do you want the blocks to mine for? ");

        Scanner sc = new Scanner(System.in);

        int zeros = sc.nextInt();
        try {
            AVLBlockchain<Integer> b = new AVLBlockchain<>(zeros, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1 - o2;
                }
            });


            while (true) {
                System.out.print("what do you want to do? :> ");
                String answer = sc.next().toLowerCase();

                switch (answer) {

                    case "add":
                        System.out.println("which element? :> ");

                        int elementToAdd = sc.nextInt();
                        b.add(elementToAdd);
                        break;

                    case "remove":
                        System.out.println("which element? :> ");

                        int elementToRemove = sc.nextInt();

                        b.remove(elementToRemove);
                        break;
                    case "lookup":
                        System.out.println("which element? :> ");

                        int elementToSearch = sc.nextInt();

                        List<Long> result =b.lookup(elementToSearch);
                        System.out.println("here are the blocks");
                        System.out.println(result);


                        break;

                    case "modify":
                        System.out.println("which block? ");
                        int index = sc.nextInt();
                        System.out.println("which file_path ( leave black to replace for an empty block)");
                        String path = sc.next();
                        System.out.println(path);
                        if(path.matches("^[./].*"))
                            b.modify(index, path);
                        else
                            b.modify(index);

                        break;

                    case "verify":
                        System.out.println("the blockchain is ...");

                        if(b.validate()){
                            System.out.println("Safe");
                        }else{
                            System.out.println("Raped.... so sorry");
                        }


                }
            }

        } catch (NoSuchAlgorithmException e) {
            System.out.println("fuck man ... there was something wrong");

        }
        catch (IllegalArgumentException e){
            System.out.println("you enter an invalid path");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
