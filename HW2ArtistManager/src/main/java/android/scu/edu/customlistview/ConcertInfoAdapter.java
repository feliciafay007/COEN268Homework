package android.scu.edu.customlistview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by feliciafay on 4/24/15.
 */
public class ConcertInfoAdapter extends ArrayAdapter<ConcertInfo>{
    private final List<ConcertInfo> concertInfoList;
    private final Context mContext;
    public ConcertInfoAdapter(Context context, int resource, List<ConcertInfo> inputList) {
        super(context, resource, inputList);
        this.concertInfoList = inputList;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.concert_row, null);
        TextView textViewName = (TextView) row.findViewById(R.id.textView3);
        textViewName.setText(concertInfoList.get(position).getArtistName());
        TextView textViewLocation = (TextView) row.findViewById(R.id.textView4);
        textViewLocation.setText(concertInfoList.get(position).getLocation());
        // NOTE:
        // 1. this actually cost lots of my time
        // 2. button is a component in a customized row in a customized listView.
        // 3. when setting the onClikcListener for the button, you might want to used the context of the parent.
        // 4. mContext is used to store the parent context at the time of running the constructor of the adapter.
        // 5. then in onClickListener, in startActivity, you can use the context to start the activity.
        final Button buttonPhone = (Button) row.findViewById(R.id.button2);
        buttonPhone.setText(concertInfoList.get(position).getPhone());
        buttonPhone.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "tel:" + buttonPhone.getText().toString();
                Log.i("Wenyi CallIntent" ,buttonPhone.getText().toString() );
                Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
                mContext.startActivity(dialIntent);
            }
        });
        return row;
    }
}
