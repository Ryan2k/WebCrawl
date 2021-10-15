//unrelated to homework assignment, just good info to know relating to it

/*import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;*/

import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

public class Parse_JSON{
    //takes link to api as first argument
    public static void main(String[] args){
        String link = args[0];

        /*Need a try catch for URL (MalformedURLException must be caught)
          And for connection.setRequestMethod(protocol exception must be caught)
          And for connections other methods (IOException) must be caught
         */
        try{
            //Step 1: Pass the desired URL as an object
            URL url = new URL(link);

            //Step 2: Type cast the URL object into a HttpURL Connection object.
            //Benefit here is that we can harness features of the httpURLConnection class to do things like set request type or get status code
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            //Steps 3 and 4 are not needed if its a get request as the default is GET

            //Step 3: set the type of request you are sending to the API (in this case, GET)
            connection.setRequestMethod("GET");

            //Step 4: Open a connection stream to the corresponding API
            connection.connect();

            //Step 5: Check Connection
            int status = connection.getResponseCode();

            //Step 6: Check to see if the status code is 200 (successful). If not, throw exception
            if(status != 200){
                throw new RuntimeException("HttpResponseCode: " + status);
            }


            //Step 7: BufferedReader to read all lines
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            String JSONString = "";
            while((line = reader.readLine()) != null){
                JSONString += line + "\n";
            }

            System.out.println("JSONString: " + JSONString);
            /*At this point, the JSON object will be saved in JSONString as a string.
            * Now the data needs to be parsed into a JSON object. In some cases, needs to be stored
            * in a JSON array as well. Java does not have any inbuilt class for providing jsob parsing so
            * we need to import a package called simplejson and download its .jar files.*/

            /*//Step 8: Declare an instance of the JSONParser
            JSONParser parser = new JSONParser();

            //Step 9: Convert the string objects into JSON objects:
            JSONOBject jObj = (JSONObject)parse.parse(JSONString);

            //Step 10: Convert the JSON object into JSONArray object
            JSONArray jsonArr = (JSONArray)jObj.get("result");

            //Step 11: Once the JSON objects are stored in the array, read the corresponding JSONArray objects,
            //and convert it to JSON objects again so you get the elements within the results array
            for(int i = 0; i < jsonArr.size(); i++){
                //store the json objects in an array
                //get the index of the json object and pring the values as per the index
                JSONObject jsonobj_1 = (JSONObject)jsonArr.get(i);
                System.out.println("Elements under results array");
                System.out.println("\nPlace id: " + jsonobj_1.get("place_id"));
                System.out.println("Types: " + jsonobj_1.get("types"));
            }*/
        }
        catch(MalformedURLException mue){
            throw new RuntimeException("No more absolute paths to follow!");
            //System.out.println(mue);
        }
        catch(IOException ie){
            System.out.println(ie);
        }
    }
}