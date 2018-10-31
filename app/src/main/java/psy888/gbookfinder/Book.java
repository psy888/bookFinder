package psy888.gbookfinder;

import android.graphics.Bitmap;

import java.net.URL;

public class Book {
    /**
     * required fields
     * title String - V
     * authors String[] - ??V need to test
     * description String - V
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
    String LOG_TAG = getClass().getSimpleName();
    private String mId;
    private String mTitle; //"title" Book Title
    private String mAuthors; //"authors"
    private String mDescription;
    private String mCategories; //"categories"
    private String mPublisher; //"publisher": "Издательский дом \"Питер\"",
    private String mPublishedDate;//"publishedDate": "2012-05-11" or "publishedDate": "2012"
    private float mRating; // 5 stars "averageRating"
    private Bitmap mSmallThumbnail; //"imageLinks" -> "smallThumbnail"
    private URL mThumbnail; //"imageLinks" -> "small"
    private URL mInfoLink; //infoLink - https://books.google.com.ua/books?id=...
    private String mPrice;
    private String mCurrencyCode;

    /**
     * Constructor
     *
     * @param title          - Book title (String)
     * @param authors        - Book authors (String array)
     * @param description    - Book description (String)
     * @param categories     - Book topic categories (String array)
     * @param publisher      - Book publisher (String)
     * @param publishedDate  - Book published date (Date)
     * @param rating         - book rating (double) value or 0.0 if not rated
     * @param smallThumbnail - small thumbnail (URL)
     * @param thumbnail      - thumbnail (URL)
     * @param infoLink       - link to book information on play.google.com (URL)
     * @param price          - retail price (double)
     * @param currencyCode   - currency of price (String)
     */
    public Book(String id, String title, String authors, String description, String categories, String publisher, String publishedDate, float rating, String price, String currencyCode, Bitmap smallThumbnail) {
        mId = id;
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mCategories = categories;
        mPublisher = publisher;
        mPublishedDate = publishedDate;
        mRating = rating;
        mSmallThumbnail = smallThumbnail;
        //   mThumbnail = thumbnail;
        //   mInfoLink = infoLink;
        mPrice = price;
        mCurrencyCode = currencyCode;
    }

    //ToDo: add getters

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthors() {
        return mAuthors;
    }

    public String getCategories() {
        return mCategories;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public float getRating() {
        return mRating / 5 * 3;
    }

    public Bitmap getSmallThumbnail() {
        return mSmallThumbnail;
    }

    public URL getThumbnail() {
        return mThumbnail;
    }

    public URL getInfoLink() {
        return mInfoLink;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }
}
