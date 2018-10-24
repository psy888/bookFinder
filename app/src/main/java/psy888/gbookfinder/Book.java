package psy888.gbookfinder;

import android.net.Uri;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Book {
    String LOG_TAG = getClass().getSimpleName();
    private String mTitle; //"title" Book Title
    private String[] mAuthors; //"authors" []
    private String[] mCategories; //"categories" []
    private String mPublisher; //"publisher": "Издательский дом \"Питер\"",
    private Date mPublishedDate;//"publishedDate": "2012-05-11" or "publishedDate": "2012"
    private double mRating; // 5 stars "averageRating"
    private Uri mSmallThumbnail; //"imageLinks" -> "smallThumbnail"
    private Uri mThumbnail; //"imageLinks" -> "small"
    private Uri mInfoLink; //infoLink - https://books.google.com.ua/books?id=...

    public Book(String title, String[] authors, String[] categories, String publisher, String publishedDate, String rating, String smallThumbnail, String thumbnail, String infoLink) {
        mTitle = title;
        mAuthors = authors;
        mCategories = categories;
        mPublisher = publisher;
        // Parse date from string
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            mPublishedDate = simpleDateFormat.parse(publishedDate);
        } catch (ParseException e) {
            mPublishedDate = null;
            Log.d(LOG_TAG, "Book:  " + e);
        }

        mRating = Double.parseDouble(rating);
        mSmallThumbnail = Uri.parse(smallThumbnail);
        mThumbnail = Uri.parse(thumbnail);
        mInfoLink = Uri.parse(infoLink);
    }

    //ToDo: add getters
    public String getmTitle() {
        return mTitle;
    }

    public String[] getmAuthors() {
        return mAuthors;
    }

    public String[] getmCategories() {
        return mCategories;
    }
}
