package fi.bunnyfeet.servicemonitor.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mikko on 26.3.2014.
 */
public class ServiceListAdapter extends ArrayAdapter {

    private Context context;

    public ServiceListAdapter(Context context, List items) {
        super(context, android.R.layout.simple_expandable_list_item_1, items);
        this.context = context;
    }

    private class ViewHolder {
        TextView status;
        TextView name;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ServiceListItem item = (ServiceListItem)getItem(position);

        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.service_item, null);
            holder = new ViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.service_item_name);
            holder.status = (TextView)convertView.findViewById(R.id.service_item_status);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(item.getItemTitle());
        //holder.status.setText(item.getItemUrl());

        Intent intent = new Intent(context, CheckService.class);
        intent.putExtra("url", item.getItemUrl());
        context.startService(intent);

        return convertView;
    }
}
