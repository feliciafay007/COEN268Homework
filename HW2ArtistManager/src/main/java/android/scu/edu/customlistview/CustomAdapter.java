package android.scu.edu.customlistview;

import android.content.Context;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ArrayAdapter;
//import android.graphics.drawable.Drawable;
//import java.io.IOException;
//import java.io.InputStream;
import java.util.List;

/**
 * Created by feliciafay on 4/22/15.
 */
public class CustomAdapter extends ArrayAdapter<Person> {
    private final List<Person> persons;

    public CustomAdapter(Context context, int resource, List<Person> persons) {
        super(context, resource, persons);
        this.persons = persons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_row, null);
        TextView textView = (TextView) row.findViewById(R.id.rowText);
        textView.setText(persons.get(position).getName());
        ImageView imageView = (ImageView) row.findViewById(R.id.rowImage);
        // NOTE:
        // 1. used setImageResource(R.id.XXX) to set image.
        imageView.setImageResource(persons.get(position).getResIDThumbnail());
        return row;
    }
}
