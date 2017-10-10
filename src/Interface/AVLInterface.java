package Interface;

import Model.AVLBlockchain;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class AVLInterface {
    public static void main(String [] args) {
        System.out.println("Initializing blockchain... ");
        System.out.print("How many zeros do you want the blocks to mine for? :> ");

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
                System.out.print("What do you want to do? (add, remove, lookup, modify, verify, save or read) :> ");
                String answer = sc.next().toLowerCase();

                switch (answer) {

                    case "add":
                        System.out.print("Which element? :> ");
                        if (!isValidData(sc)) {
                            System.out.println(" Not valid element");
                            break;
                        }
                        int elementToAdd = sc.nextInt();
                        b.add(elementToAdd);
                        break;

                    case "remove":
                        System.out.print("Which element? :> ");
                        if (!isValidData(sc))
                            break;
                        int elementToRemove = sc.nextInt();

                        b.remove(elementToRemove);
                        break;
                    case "lookup":
                        System.out.print("Which element? :> ");
                        if (!isValidData(sc))
                            break;
                        int elementToSearch = sc.nextInt();

                        List<Long> result =b.lookup(elementToSearch);
                        System.out.println("Here are the blocks");
                        System.out.println(result);


                        break;

                    case "modify":
                        System.out.print("Which block? :> ");
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

                        if(b.validate()){
                            System.out.println("Safe");
                        }else{
                            System.out.println("Raped.... so sorry");
                        }
                        break;

                    case "save":
                        System.out.println("Specify path :> ");
                        String pathToSave = sc.next();
                        b.save(pathToSave);
                        break;

                    case "read":
                        System.out.println("Getting the blockchain from..");
                        System.out.println("Specify the path");
                        if (!sc.hasNext())
                            break;
                        String pathToRead = sc.next();

                        b.read(pathToRead);
                        break;
                    default:
                        break;
                }
            }

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Fuck man ... there was something wrong");

        }
        catch (IllegalArgumentException e){
            System.out.println("You enter an invalid path");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
