package psy888.gbookfinder;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final int BOOKS_LOADER_ID = 1;
    protected BookAdapter mAdapter;
    protected List<Book> books;
    public static Book dBook;
    String query = "Java";
    //Context context = MainActivity.;

    public static Book getdBook() {
        return dBook;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        //Find ListView
        ListView booksListView = findViewById(R.id.list);
        //set BookAdapter to ListView
        booksListView.setAdapter(mAdapter);
        if (getLoaderManager().getLoader(BOOKS_LOADER_ID) != null) // On Rotate or etc.
        {
            getLoaderManager().initLoader(BOOKS_LOADER_ID, null, this);
        }

        final EditText editText = findViewById(R.id.searchQuery);


        ImageButton searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = editText.getText().toString().trim();
                // Log.d("Query", query);
                if (getLoaderManager().getLoader(BOOKS_LOADER_ID) != null) {
                    getLoaderManager().restartLoader(BOOKS_LOADER_ID, null, MainActivity.this);
                    //getLoaderManager().destroyLoader(BOOKS_LOADER_ID);
                }
                getLoaderManager().initLoader(BOOKS_LOADER_ID, null, MainActivity.this);
            }
        });
        //Go to Details Activity with details of this Book
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                dBook = mAdapter.getItem(position);
                Intent details = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(details);
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle bundle) {
        mAdapter.clear();
        return new BooksLoader(this, query);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        this.books = books;
        this.mAdapter.clear();
        if (this.books != null && !this.books.isEmpty()) {
            mAdapter.addAll(this.books);
        } else {
            //TODO Set text to emptyView "no results"
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
