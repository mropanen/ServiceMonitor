package fi.bunnyfeet.servicemonitor.app;



import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ServiceFragment extends ListFragment {

    private List serviceListItemList;
    private ServiceListAdapter slAdapter;

    public ServiceFragment() {
        // Required empty public constructor
    }

    public List getItems() {
        return serviceListItemList;
    }
    public ServiceListAdapter getAdapter() {
        return slAdapter;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("servicefrag", "oncreate");
        super.onCreate(savedInstanceState);

        serviceListItemList = new ArrayList();
        serviceListItemList.add(new ServiceListItem("Google", "http://google.fi"));
        serviceListItemList.add(new ServiceListItem("Bunnyfeet", "http://bunnyfeet.fi"));
        serviceListItemList.add(new ServiceListItem("Service 3", "http://goat.cx"));

        slAdapter = new ServiceListAdapter(getActivity(), serviceListItemList);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(slAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.service_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
       ServiceListItem item = (ServiceListItem)serviceListItemList.get(position);
       Log.d("yo", item.getItemTitle() + " tapped.");
    }

    public void addItem(ServiceListItem item) {
        serviceListItemList.add(item);
        Log.d("added", item.getItemTitle());
    }


}
