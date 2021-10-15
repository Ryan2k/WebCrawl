/* Ryan Khamneian
   Dr. Dimpsey
   CSS 436
   October 15, 2021
 */
import java.util.*;
import java.lang.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

public class WebCrawl{
    public static void main(String[] args) throws IOException{
        //throws error if the input is valid (description of errors in block above method)
        argumentsAreValid(args);

        String link = args[0];
        int num_hops = Integer.parseInt(args[1]);
        HashSet<String> visitedPages = new HashSet<>();
        nextURL(link, visitedPages);

        String next = link;

        for(int i = 0; i <= num_hops; i++){
            //returns na if there are no more valid paths so break the loop
            if(next.equals("na")){
                break;
            }
            System.out.println((i + 1) + ": " + next);
            visitedPages.add(next);
            next = nextURL(next, visitedPages);
            System.out.println("");
        }
    }

    public static String nextURL(String link, HashSet<String> visitedPages){
        try{

            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            int status = connection.getResponseCode();
            if(!visitedPages.isEmpty()){
                System.out.println("status code: " + status);
            }

            //buffer to read in lines of the webpage's html file
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            //sets line to the next line of the object in the reader(html file of web page)
            //if it is null we have reached the end of the page
            while((line = reader.readLine()) != null){
                //System.out.println(line);
                //split the line into seperates strings with each tag as its own
                String[] elements = line.split("<");
                //loop through the strings to see if one of them has an '<a href="http' as its first characters
                //that will mean that an absolute path is linked.
                for(int i = 1; i < elements.length; i++){
                    if(elements[i].length() > 13 && elements[i].substring(0, 12).equals("a href=\"http")){
                        //System.out.println("full line: " + elements[i]);
                        String[] hrefElements = elements[i].split("\"");
                        String next = hrefElements[1];
                        //System.out.println("next: " + next);
                        //System.out.println("found: " + next);

                        //dont return this as the next page if we have already visisted it.
                        if(visitedPages.contains(next)){
                            continue;
                        }

                        //find the status code and do something (not sure yet what) if unsuccessful
                        URL nextLink = new URL(next);
                        HttpURLConnection nextConnection = (HttpURLConnection)nextLink.openConnection();

                        //get respose code as if its 3xx we need to redirect, and if over 400 (client or server error) continue to next link
                        int newStatus = nextConnection.getResponseCode();
                        if(newStatus > 399){
                            //System.out.println("Couldn't connect to: " + next + " status code: " + nextConnection.getResponseCode());
                            continue;
                        }

                        //status code 301 means the url was moved permanently and the new link lays in the location header field
                        //status code 302 means the url was moved temperarily and the new linke also lays in the location header field
                        if(newStatus > 299 && newStatus < 400){
                            System.out.println(next + " status code: " + newStatus);
                            next = nextConnection.getHeaderField("Location");
                            System.out.println("redirected to: " + next);
                            if(visitedPages.contains(next)){
                                continue;
                            }
                        }

                        return next;

                    }
                }
            }
        }
        //gets a malformedurlexception when there are no more valid paths to follow
        catch(MalformedURLException mue){
            System.out.println("Ran out of unvisited links!");
            return "na";
            //throw new RuntimeException("No more absolute paths to follow!");
            //System.out.println(mue);
        }
        catch(IOException ie){
            System.out.println(ie);
        }

        return ""; //no valid URL was found with a succesful connection that we have not traversed already
    }

    /* Called in the begining of main to check if the arguments were valid
        2 invalid sets of arguments each one throws a different exception message and both crash the program
            1. The number of arguments is not 2
            2. The url is either invalid or cannot be connected to (The response code is in the 400s or 500s)
        if the arguments are valid, it does nothing and continues with the program
     */
    public static void argumentsAreValid(String[] args){
        try{
            if(args.length != 2){
                throw new RuntimeException();
            }
        }
        catch(Exception e){
            System.out.println("Must have 2 arguments to this program");
            System.out.println("1. URl to web page");
            System.out.println("2. Number of hops to other web pages");
            throw new RuntimeException();
        }
        try{
            URL url = new URL(args[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            if(connection.getResponseCode() >= 400){
                throw new RuntimeException();
            }
        }
        catch(Exception e){
            System.out.println("The url is either invalid or the server cannot be connected to");
            throw new RuntimeException();
        }
    }
}