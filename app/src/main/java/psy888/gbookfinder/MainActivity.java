package psy888.gbookfinder;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

//import android.support.v4.app.LoaderManager;
//import android.support.v4.content.Loader;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final int BOOKS_LOADER_ID = 1;
    String query = "android";
    BookAdapter mAdapter;

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


        final EditText editText = findViewById(R.id.searchQuery);


        ImageButton searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                query = String.valueOf(editText.getText());
                Log.d("Query", query);


            }
        });

        //find ListView
        ListView list = findViewById(R.id.list);

        //ToDo: add adapter
        //ToDo: Extend Async task loader and add loader
        getLoaderManager().initLoader(BOOKS_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle bundle) {
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

    }
}
