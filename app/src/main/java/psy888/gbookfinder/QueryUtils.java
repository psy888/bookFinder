package psy888.gbookfinder;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryUtils {
    static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     *
     * @param urlString - String url
     * @return - url or null if error
     */
    static public URL getUrl(String urlString)
    {
        URL url = null;
        try
        {
            url = new URL(urlString);
        }
        catch (MalformedURLException e)
        {
            Log.e(LOG_TAG, "Problem building url ",e);
            e.printStackTrace();
        }
        return url;
    }

    /**
     * @param queryStr - from EditText @+id/searchQuery
     * @return Query Url
     */
    public static URL getQueryUrl(String queryStr) {
        //ToDo: Modify query parameters
        final String queryGoogleBooks = "https://www.googleapis.com/books/v1/volumes?q=";
        URL url = null;
        try {
            url = new URL(queryGoogleBooks + queryStr + "&maxResults=40");
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building url ", e);
            e.printStackTrace();
        }
        return url;
    }

    /**
     * @param url - Server API query url
     * @return String JsonResponse
     * @throws IOException - connection exception
     */
    public static String makeHttpRequest (URL url) throws IOException {
        String jsonResponse = "";

        //If url is null return empty jsonResponse
        if(url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000);//Milliseconds
            urlConnection.setConnectTimeout(1500);//milliseconds
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //if connection is success (CODE = 200) get input stream and parse json
            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Connection error, response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG , "Connection error ", e );
        } finally {
            //if urlConnection not closed automatically
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     *
     * @param inputStream - from makeHttpRequest()
     * @return - String jsonResponse for makeHttpRequest()
     * @throws IOException
     */
    public static String readFromStream (InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();
        if(inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line!=null)
            {
                output.append(line);
                line=bufferedReader.readLine();
            }
        }
        Log.e(LOG_TAG, "OUTPUT" + output.toString());
        return output.toString();
    }

    /**
     *
     * @param jsonOutput - received from server
     * @return - ArrayList of BOOKS
     */
    public static ArrayList<Book> extractBooksFromJson (String jsonOutput)
    {
        //Check jsonOutput is not Empty
        if(TextUtils.isEmpty(jsonOutput))
        {
            return null;
        }

        //Create ArrayList empty arrayList that we can start adding Books to
        ArrayList<Book> booksList = new ArrayList<>();

        //Try to parse Json object from string. If there is a problem with the way the JSON
        //is formatted, a JSONException object will be thrown.
        //Catch the exception so the app doesn't crash, and print the error message to the logs.
        try
        {
            // Parse the response
            JSONObject jsonResponse = new JSONObject(jsonOutput);

            JSONArray items = jsonResponse.optJSONArray("items");

            //for each item (Book) create Book object
            for (int i = 0; i < items.length(); i++)
            {
                JSONObject currentBook = items.optJSONObject(i);
                /**
                 * required fields
                 * title String - V
                 * authors String[] - ??V need to test
                 * description - V
                 * categories String[]
                 * Publisher String - V
                 * publishedDate Date -V
                 * rating double -V
                 * smallThumbnail uri -V
                 * thumbnail uri -V
                 * infoLink uri -V
                 * price double - V
                 * currencyCode string - V
                 */
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                // Extract the value for the key called "title"
                String title = volumeInfo.getString("title");

                //extract authors to array of strings
                JSONArray authorsArray = volumeInfo.optJSONArray("authors");
                String[] authors = new String[authorsArray.length()];
                for (int j = 0; j < authorsArray.length(); j++) {
                        authors[j]=authorsArray.getString(j);//Todo: Check this!
                }
                // extract categories to array of strings
                JSONArray categoriesArray = volumeInfo.optJSONArray("categories");
                String[] categories = new String[categoriesArray.length()];
                for (int j = 0; j < categoriesArray.length(); j++) {
                    categories[j] = categoriesArray.getString(j);
                }
                //Description
                String description = volumeInfo.optString("description");

                //Extract Publisher
                String publisher = volumeInfo.optString("publisher");

                // Parse date from string if ok extracting
                SimpleDateFormat sdfFullDate = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdfYearOnly = new SimpleDateFormat("yyyy");
                Date publishedDate = null;
                try {
                    SimpleDateFormat simpleDateFormat = (volumeInfo.optString("publishedDate").length() > 4) ? sdfFullDate : sdfYearOnly;
                    publishedDate = simpleDateFormat.parse(volumeInfo.optString("publishedDate"));
                } catch (java.text.ParseException e) {
                    Log.d(LOG_TAG, "extractBooksFromJson:  ", e);
                }

                //Extracting Rating
                float rating = Float.parseFloat(currentBook.optString("averageRating", "0.0"));

                //smallThumbnail
                URL smallThumnail = getUrl(volumeInfo.optJSONObject("imageLinks").optString("smallThumbnail"));
                //Thumbnail
                URL thumbnail = getUrl(volumeInfo.optJSONObject("imageLinks").optString("thumbnail"));
                //infoLink url
                URL infoLink = getUrl(volumeInfo.optJSONObject("imageLinks").optString("infoLink"));


                double price = 0.0; // if NOT_FOR_SALE
                String currencyCode = "";
                JSONObject saleInfo = currentBook.optJSONObject("saleInfo");
                if (saleInfo.optString("saleability").contentEquals("FOR_SALE")) {
                    price = Double.parseDouble(saleInfo.optJSONObject("retailPrice").optString("amount"));
                    currencyCode = saleInfo.optJSONObject("retailPrice").optString("currencyCode");
                }

                Book curBookObj = new Book(title, authors, description, categories, publisher, publishedDate, rating, smallThumnail, thumbnail, infoLink, price, currencyCode);
                booksList.add(curBookObj);
            }
        }
        catch (JSONException e)
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return booksList;
    }


    /**
     *  Fetch data from server
     * @param query
     * @return List of book objects
     */
    public static List<Book> fetchBooksData(String query) {
        //Create query url
        URL queryUrl = getQueryUrl(query);

        //Perform HTTP request and receive JSON response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(queryUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        //Extract relevant fields from the JSON response and create a list of books
        List<Book> bookList = extractBooksFromJson(jsonResponse);

        return bookList;

    }
    //Todo: 1.parse urls method -- done!
    //TODO: 2.makeHttpConnection method -- done!
    //Todo 2.1 ReadFromStream method -- done!
    //Todo: 3.Parse json add data to Book object and fill ArrayList -- done!
}
