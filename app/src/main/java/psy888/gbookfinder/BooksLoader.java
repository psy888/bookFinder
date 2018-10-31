package psy888.gbookfinder;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BooksLoader extends AsyncTaskLoader<List<Book>> {
    /**
     * Tag for log messages
     */
    String LOG_TAG = getClass().getSimpleName();
    /**
     * Query url
     */
    String query;

    public BooksLoader(Context context, String query) {
        super(context);
        this.query = query;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (query.isEmpty() || query == null) {
            return null;
        }
        List<Book> bookList = QueryUtils.fetchBooksData(query);

        return bookList;

    }

}
