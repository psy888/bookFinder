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

//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.Loader;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final int BOOKS_LOADER_ID = 1;
    protected BookAdapter mAdapter;
    String query = "Java";
    //Context context = MainActivity.;

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
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent details = new Intent(MainActivity.this, DetailsActivity.class);
                String bookId = mAdapter.getItem(position).getId();
                details.putExtra("id", bookId);
                startActivity(details);


            }
        });


        //ToDo: add adapter - Done
        //ToDo: Extend Async task loader and add loader - Done


    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle bundle) {
        mAdapter.clear();
        return new BooksLoader(this, query);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        mAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        //mAdapter.clear();
    }
}
