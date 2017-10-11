package Interface;

import Model.AVLBlockchain;
import Model.Exceptions.InvalidAVLOperationDataException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class AVLInterface {

    private static boolean isRunning = true;

    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        AVLBlockchain<Integer> b = startWithSavedBlockchain(sc);
        System.out.println("Initializing blockchain... ");
        System.out.print("How many zeros do you want the blocks to mine for? :> ");

        try {
            if(b == null){
                while(!sc.hasNextInt()){
                    System.out.println("Not a number. Please say how many zeros you want the blocks to mine for");
                       sc.next();
                }
            int zeros = sc.nextInt();
            b = new AVLBlockchain<>(zeros, (Integer i1, Integer i2)->(i1-i2));
            }


            while (isRunning) {
                System.out.print("What do you want to do? (add, remove, lookup, modify, verify, save or read, printblockchain, printtree, exit) :> ");
                String answer = sc.next().toLowerCase();

                switch (answer) {

                    case "add":
                        if (!isValidData(sc)) {
                            System.out.println(" Not valid element");
                            break;
                        }
                        int elementToAdd = sc.nextInt();
                        b.add(elementToAdd);
                        break;

                    case "remove":

                        if (!isValidData(sc))
                            break;
                        int elementToRemove = sc.nextInt();

                        b.remove(elementToRemove);
                        break;
                    case "lookup":
                        if (!isValidData(sc))
                            break;
                        int elementToSearch = sc.nextInt();

                        List<Long> result =b.lookup(elementToSearch);
                        System.out.println("Here are the blocks");
                        System.out.println(result);


                        break;

                    case "modify":
                        if (!isValidData(sc))
                            break;
                        int index = sc.nextInt();
                        System.out.println("Which file_path (leave black to replace for an empty block)");
                        String path = sc.next();
                        System.out.println(path);
                        if(path.matches("^[./].*"))
                            b.modify(index, path);
                        else
                            b.modify(index);

                        break;

                    case "verify":
                        System.out.println("The blockchain is ...");

                        if(b.verify()){
                            System.out.println("Safe");
                        }else{
                            System.out.println("Raped.... so sorry");
                        }
                        break;

                    case "save":
                        System.out.println("Specify path :> ");
                        String pathToSave = sc.next();
                        b.save(pathToSave);
                        System.out.println("network saved!");
                        break;

                    case "read":
                        System.out.println("Getting the blockchain from..");
                        System.out.println("Specify the path");
                        if (!sc.hasNext())
                            break;
                        String pathToRead = sc.next();
                        if(b.read(pathToRead)){
                            System.out.println("File read correctly");
                        }else{
                            System.out.println("The file was corrupted");
                        }
                        break;

                    case "printtree":
                        b.printTree();
                        break;

                    case "printblockchain":
                        b.printBlockchain();
                        break;
                    case "exit":
                        isRunning = false;
                    default:
                        break;
                }
            }

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Fuck man ... there was something wrong");

        }
        catch (IllegalArgumentException e){
            System.out.println("You enter an invalid path");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidAVLOperationDataException e) {
            e.printStackTrace();
        }

    }


    private static AVLBlockchain startWithSavedBlockchain(Scanner sc){

        AVLBlockchain<Integer> b = null;
        System.out.println("Do you want to rebuild a blockchain? (Y or press any other key) ");
        String ans = sc.next();
        ans =ans.toUpperCase();
        if( ans.equals("Y")){
            System.out.println("entre");
            try {
                b = new AVLBlockchain<>(1, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o1 - o2;
                    }
                });
                System.out.println("Getting the blockchain from..");
                System.out.println("Specify the path");
                if (sc.hasNext()) {
                    String pathToRead = sc.next();
                    if (b.read(pathToRead)) {
                        System.out.println("File read correctly");
                    } else {
                        System.out.println("The file was corrupted");
                    }
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidAVLOperationDataException e) {
                e.printStackTrace();
            }

        }

        return b;

    }

    private static boolean isValidData(Scanner sc){
        if (!sc.hasNextInt()){
            return false;
        }
        if (sc.hasNext("[a-zA-Z]?")){
            return false;
        }
        if (sc.hasNext("\\W")){
            return false;
        }
        return true;
    }
}
