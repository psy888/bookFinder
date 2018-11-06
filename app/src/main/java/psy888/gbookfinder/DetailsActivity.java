package psy888.gbookfinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class DetailsActivity extends AppCompatActivity {

    String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //Get current (clicked) book object
        final Book currentBook = MainActivity.getdBook();
        //Find views
        ImageView thumbnail = findViewById(R.id.thumbnail);
        TextView title = findViewById(R.id.title);
        TextView authors = findViewById(R.id.authors);
        TextView price = findViewById(R.id.price);
        RatingBar ratingBar = findViewById(R.id.rating);
        TextView description = findViewById(R.id.description);
        Button shareLink = findViewById(R.id.shareLink);
        Button buyLink = findViewById(R.id.buyLink);

        //Thumbnail
        thumbnail.setImageBitmap(currentBook.getThumbBitmap());
        //Title
        title.setText(currentBook.getTitle());
        authors.setText("by " + currentBook.getAuthors());
        price.setText(currentBook.getPrice() + " " + currentBook.getCurrencyCode());
        ratingBar.setRating(currentBook.getRating());
        description.setText(currentBook.getDescription());


        //On Click Listener Share link
        shareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = currentBook.getInfoLink();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, currentBook.getTitle() + "\n" + link);
                if (share.resolveActivity(getPackageManager()) != null) {
                    startActivity(share);
                }
            }
        });

        //On click Listener BuyLink
        buyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buy = new Intent(Intent.ACTION_VIEW, Uri.parse(currentBook.getInfoLink()));
                if (buy.resolveActivity(getPackageManager()) != null) {
                    startActivity(buy);
                }
            }
        });

    }
}

