package psy888.gbookfinder;

import android.net.ParseException;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class QueryUtils {
    static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     *
     * @param queryStr - from EditText @+id/searchQuery
     * @return Query Url
     */
    public URL getUrl (String queryStr)
    {
        //ToDo: Modify query parameters
        final String queryGoogleBooks = "https://www.googleapis.com/books/v1/volumes?q=";
        URL url = null;
        try
        {
            url = new URL(queryGoogleBooks + queryStr);
        }
        catch (MalformedURLException e)
        {
            Log.e(LOG_TAG, "Problem building url ",e);
            e.printStackTrace();
        }
        return url;
    }

    public static String makeHttpRequest (URL url) throws IOException
    {
        String jsonResponse = "";

        //If url is null return empty jsonResponse
        if(url == null)
        {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000);//Milliseconds
            urlConnection.setConnectTimeout(1500);//milliseconds
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //if connection is success (CODE = 200) get input stream and parse json
            if(urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else
            {
                Log.e(LOG_TAG, "Connection error, response code: " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG , "Connection error ", e );
        }
        finally
        {
            //if urlConnection not closed automatically
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if(inputStream != null)
            {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     *
     * @param inputStream - from makeHttpRequst()
     * @return - String json object
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
        return output.toString();
    }
    //Todo: 1.parse urls method -- done!
    //TODO: 2.HTTPconnection method -- done!
    //Todo 2.1 ReadFromStream method -- done!
    //Todo: 3.Parse json add data to Book object and fill ArrayList
}
