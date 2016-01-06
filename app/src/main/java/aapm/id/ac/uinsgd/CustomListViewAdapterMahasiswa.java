package aapm.id.ac.uinsgd;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListViewAdapterMahasiswa extends ArrayAdapter<Mahasiswa> {
    private Context context;
    private ArrayList<Mahasiswa> itemArrayList;

    public CustomListViewAdapterMahasiswa(Context context, ArrayList<Mahasiswa> itemArrayList) {
        super(context, R.layout.listview_mahasiswa,itemArrayList);
        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listview_mahasiswa, parent, false);
        TextView nimMahasiswa = (TextView) rowView.findViewById(R.id.textView12);
        TextView namaMahasiswa = (TextView) rowView.findViewById(R.id.textView13);
        nimMahasiswa.setText(itemArrayList.get(position).getNim());
        namaMahasiswa.setText(itemArrayList.get(position).getNama_mahasiswa());
        return rowView;
    }

}
