package psy888.gbookfinder;

import java.net.URL;
import java.util.Date;

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
    private String mTitle; //"title" Book Title
    private String[] mAuthors; //"authors" []
    private String mDescription;
    private String[] mCategories; //"categories" []
    private String mPublisher; //"publisher": "Издательский дом \"Питер\"",
    private Date mPublishedDate;//"publishedDate": "2012-05-11" or "publishedDate": "2012"
    private float mRating; // 5 stars "averageRating"
    private URL mSmallThumbnail; //"imageLinks" -> "smallThumbnail"
    private URL mThumbnail; //"imageLinks" -> "small"
    private URL mInfoLink; //infoLink - https://books.google.com.ua/books?id=...
    private double mPrice;
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
    public Book(String title, String[] authors, String description, String[] categories, String publisher, Date publishedDate, float rating, URL smallThumbnail, URL thumbnail, URL infoLink, double price, String currencyCode) {
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mCategories = categories;
        mPublisher = publisher;
        mPublishedDate = publishedDate;
        mRating = rating;
        mSmallThumbnail = smallThumbnail;
        mThumbnail = thumbnail;
        mInfoLink = infoLink;
        mPrice = price;
        mCurrencyCode = currencyCode;
    }

    //ToDo: add getters
    public String getTitle() {
        return mTitle;
    }

    public String[] getAuthors() {
        return mAuthors;
    }

    public String[] getCategories() {
        return mCategories;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public Date getPublishedDate() {
        return mPublishedDate;
    }

    public float getRating() {
        return mRating;
    }

    public URL getSmallThumbnail() {
        return mSmallThumbnail;
    }

    public URL getThumbnail() {
        return mThumbnail;
    }

    public URL getInfoLink() {
        return mInfoLink;
    }

    public double getPrice() {
        return mPrice;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }
}
