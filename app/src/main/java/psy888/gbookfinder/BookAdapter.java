package psy888.gbookfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private Context mContext;
    private List<Book> mBooksList;

    public BookAdapter(Context context, ArrayList<Book> booksList) {
        super(context, 0, booksList);
        mBooksList = booksList;
        mContext = context;
    }

    //Helper method Array of strings to String
    private static String arrayToString(String[] arr) {
        StringBuilder stringBuilder = new StringBuilder();
        String divider = ", ";
        for (int i = 0; i < arr.length; i++) {
            if (i != arr.length - 1) {
                stringBuilder.append(arr[i] + divider);
            } else {
                stringBuilder.append(arr[i]);
            }
        }
        return stringBuilder.toString();
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }

        //get current Book object
        Book currentBook = mBooksList.get(position);

        //Get layout view resources links
        TextView title = listItem.findViewById(R.id.title);
        TextView authors = listItem.findViewById(R.id.authors);
        RatingBar ratingBar = listItem.findViewById(R.id.rating);
        TextView price = listItem.findViewById(R.id.price);
        ImageView bookSmallThumbnail = listItem.findViewById(R.id.bookSmallThumbnail);

        //Set data from currentBook Object to layout views
        title.setText(currentBook.getTitle());
        authors.setText(arrayToString(currentBook.getAuthors()));
        ratingBar.setRating(currentBook.getRating());
        price.setText(currentBook.getPrice() + " " + currentBook.getCurrencyCode()); // Todo: change currency code to Localized currency representation
        // bookSmallThumbnail.setImageURI(currentBook.getSmallThumbnail().toURI()); // Todo: write asyncTask to get image

        //ToDo: Write adapter -- done
        return listItem;
    }
}
