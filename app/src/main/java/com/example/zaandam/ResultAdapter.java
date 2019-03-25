package com.example.zaandam;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ResultAdapter extends ArrayAdapter<Row> {

    public ResultAdapter(Context context, List<Row> rows) {
        super(context, 0, rows);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_results, parent, false);
        }

        ResultViewHolder viewHolder = (ResultViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ResultViewHolder();
            viewHolder.distance = (TextView) convertView.findViewById(R.id.distance);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.latitude = (TextView) convertView.findViewById(R.id.latitude);
            viewHolder.longitude = (TextView) convertView.findViewById(R.id.longitude);
            viewHolder.go = (Button) convertView.findViewById(R.id.go);
            viewHolder.go.setOnClickListener(ItenaryClick);
            convertView.setTag(viewHolder);
        }


        Row row = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.distance.setText(String.valueOf(row.getDistance()));
        viewHolder.name.setText(row.getName());
        viewHolder.description.setText(row.getDescription());
        viewHolder.latitude.setText(row.getLatitude().toString());
        viewHolder.longitude.setText(row.getLongitude().toString());
        viewHolder.go.setTag(position);

        return convertView;
    }

	
	private View.OnClickListener ItenaryClick= new View.OnClickListener()
    {
		@Override
        public void onClick(View view)
        {
			int position=(Integer) view.getTag();
			Row row = getItem(position);
			Intent intent = new Intent(getContext(), DirectionActivity.class);
			intent.putExtra("latitude",row.getLatitude().toString());
			intent.putExtra("longitude",row.getLongitude().toString());
			getContext().startActivity(intent);
		}

    };

    private class ResultViewHolder {
        public TextView distance;
        public TextView name;
        public TextView description;
        public TextView latitude;
        public TextView longitude;
        public Button go;
    }
}
