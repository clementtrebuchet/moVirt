package org.ovirt.mobile.movirt.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.ovirt.mobile.movirt.R;
import org.ovirt.mobile.movirt.rest.Cluster;

public class ClusterListAdapter extends ArrayAdapter<Cluster> {

    public static final String TAG = ClusterListAdapter.class.getSimpleName();

    public ClusterListAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.cluster_list_item, viewGroup, false);
        }

        Cluster cluster = getItem(i);
        TextView textView = (TextView) view.findViewById(R.id.cluster_view);
        if (cluster == null) {
            textView.setText(R.string.all_clusters);
        } else {
            textView.setText(cluster.getName());
        }

        return view;
    }
}