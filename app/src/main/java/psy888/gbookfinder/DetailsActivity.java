package psy888.gbookfinder;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final int DETAILS_LOADER_ID = 2;
    String LOG_TAG = getClass().getSimpleName();
    private String bookId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        bookId = intent.getStringExtra("id");
        //Todo Create AsyncTask to Load book info with given Id

        getLoaderManager().initLoader(DETAILS_LOADER_ID, null, this);
    }


    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG, "Create loader with id " + bookId);
        return new DetailsLoader(this, bookId);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        Log.e(LOG_TAG, "onLoadFinished with id " + bookId);
        if (books != null) {
            // Book currentBook = books.get(0);
            Book currentBook = MainActivity.getdBook();
            //Find views
            ImageView thumbnail = findViewById(R.id.thumbnail);
            TextView title = findViewById(R.id.title);
            TextView authors = findViewById(R.id.authors);
            TextView price = findViewById(R.id.price);
            RatingBar ratingBar = findViewById(R.id.rating);
            TextView description = findViewById(R.id.description);


            //Thumbnail
            new QueryUtils.DownloadThumbnailTask(thumbnail).execute(currentBook.getThumbnail());
            //Title
            title.setText(currentBook.getTitle());
            authors.setText("by " + currentBook.getAuthors());
            price.setText(currentBook.getPrice() + " " + currentBook.getCurrencyCode());
            ratingBar.setRating(currentBook.getRating());
            description.setText(currentBook.getDescription());
        } else {
            //TODO Set text to emptyView "no results"
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

    }
}
