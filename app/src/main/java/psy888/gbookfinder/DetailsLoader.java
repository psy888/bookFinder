package psy888.gbookfinder;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class DetailsLoader extends AsyncTaskLoader<List<Book>> {
    String LOG_TAG = getClass().getSimpleName();
    String bookId;

    public DetailsLoader(Context context, String id) {
        super(context);
        this.bookId = id;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (bookId.isEmpty() || bookId == null) {
            return null;
        }
        List<Book> bookList = QueryUtils.fetchBooksData(bookId);

        if (bookList == null || bookList.size() < 1) {
            return null;
        }
        Log.e(LOG_TAG, "BOOKS LIST SIZE" + bookList.size());
        return bookList;
    }
}
