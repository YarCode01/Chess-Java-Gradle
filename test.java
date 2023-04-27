// import java.util.Scanner;
import java.util.*;
import java.io.*;

public class test {

    public static ArrayList<int[]> aaa(){
        return new ArrayList<int[]>();
    }
    public static void main(String[] args){
        try {
            File f = new File("level1.txt");
            Scanner scan = new Scanner(f);
            for(int i=0; i < 14; i++){

                String line = scan.nextLine();
                for (int j = 0; j < line.length(); j++) {
                    char name = line.charAt(j);
                    // System.out.print(name);
                    switch (name){
                        case 'R' :
                            System.out.println("wooow");
                    }
                }
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Integer[] array = {1,2,3};
        // int[][] last_move = null;
        ArrayList<int[]> array_list = new ArrayList<int[]>();
        int[] my_arr = {1,2};
        array_list.add(my_arr);
        System.out.println(array_list.get(0)[0]);
        ArrayList<int[]> s_arr = new ArrayList<int[]>();
        s_arr.add(new int[] {5,3});
        s_arr.add(new int[] {5,3});
        HashSet<int[]> s = new HashSet<int[]>();
        s.add(new int[] {5,3});
        s.add(new int[] {5,3});
        int[][] last_move = new int[][] {null, null};
        if(last_move[0] == null){
            System.out.println("YEEEah");
        }
        last_move[0] = new int[] {1,2};
        last_move[1] = new int[] {1,2};
        System.out.println(last_move[0][0]+last_move[0][1]);
        System.out.println(last_move[1][0]+last_move[1][1]);
    }
    
}
