import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//coding along with Dr. Dimpsey doing the webcrawler. I began my webcrawler first.
public class InClassWork{
    public static void main(String[] args) throws IOException{
        //first argument is a string with the url we are trying to get
        String stringURL = args[0];

        //creating a url object with the argument url as input
        URL url = new URL(stringURL);

        //builds connection object from the URL, can use it to get status code, get request method, etc.
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        //gives the status code when we try to ping the connection (ex: 200 is success and 404 is not found)
        int statusCode = connection.getResponseCode();
        System.out.println(statusCode);

        //reads text from a character-input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;

        //prints every line of the html that was in that url
        while((line = reader.readLine()) != null){
            System.out.println(line);
        }
    }
}