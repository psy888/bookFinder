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


public class BookAdapter extends ArrayAdapter<Book> {
    private Context mContext;
    private ArrayList<Book> mBooksList;

    public BookAdapter(Context context, ArrayList<Book> booksList) {
        super(context, 0, booksList);
        mBooksList = booksList;
        mContext = context;
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
        TextView ratingNum = listItem.findViewById(R.id.ratingNum);
        TextView price = listItem.findViewById(R.id.price);
        ImageView bookSmallThumbnail = listItem.findViewById(R.id.bookSmallThumbnail);

        //Set data from currentBook Object to layout views
        title.setText(currentBook.getTitle());
        authors.setText(currentBook.getAuthors());
        ratingBar.setRating(currentBook.getRating());
        ratingNum.setText(currentBook.getRatingNum() + "/5");
        price.setText(currentBook.getPrice() + " " + currentBook.getCurrencyCode()); // Todo: change currency code to Localized currency representation
        bookSmallThumbnail.setTag(currentBook);
        bookSmallThumbnail.animate();
        //if Image already downloaded set it to ImageView
        if (currentBook.equals(bookSmallThumbnail.getTag()) && currentBook.getThumbBitmap() != null) {
            bookSmallThumbnail.setImageBitmap(currentBook.getThumbBitmap());
        } else { // Else null ImageView source and create a new AsyncTask to download image
            bookSmallThumbnail.setImageDrawable(null);
            //bookSmallThumbnail.setImageResource(R.drawable.outline_broken_image_black_48); //if book has no cover
            new QueryUtils.DownloadThumbnailTask(mBooksList, bookSmallThumbnail, position).execute(currentBook.getSmallThumbnail());
        }

        return listItem;
    }
}
