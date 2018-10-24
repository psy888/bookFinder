package psy888.gbookfinder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, int resource) {
        super(context, resource);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //ToDo: Write adapter
        return super.getView(position, convertView, parent);
    }
}
