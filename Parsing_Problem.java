import java.util.*;
public class Parsing_Problem{
    public static void main(String[] args){

    }

    //assuming input is acct_123,social_security_number,12345
    public static void printResults(String[] input){
        HashSet<Integer> Ids = new HashSet<>();
        HashMap<Integer, String> IdToOutput = new HashMap<>();
        HashMap<Integer, HashMap<String, String>> map = new HashMap<>();

        //loop through input array and store all key value pairs
        for(int i = 0; i < input.length; i++){
            String[] elements = input.split(",");
            int id = Integer.parseInt(elements[0].substring(6));
            if(!map.containsKey(id)){
                HashMap<String, String> val = new HashMap<>();
                val.put(elements[1], elements[2]);
                map.put(id, val);
            }
            else{
                map.get(id).put(elements[1], elements[2]);
            }
        }

        //loop through all id's and find its response string
        for(Map.Entry<Integer, HashMap<String, String>>)
    }
}