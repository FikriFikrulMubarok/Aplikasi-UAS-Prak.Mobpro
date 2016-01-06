package aapm.id.ac.uinsgd;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListViewAdapterMahasiswaKelas extends ArrayAdapter<MahasiswaKelas> {
    private Context context;
    private ArrayList<MahasiswaKelas> itemArrayList;

    public CustomListViewAdapterMahasiswaKelas(Context context, ArrayList<MahasiswaKelas> itemArrayList) {
        super(context, R.layout.listview_daftar_mahasiswa_kelas,itemArrayList);
        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listview_daftar_mahasiswa_kelas, parent, false);
        TextView nimMahasiswa = (TextView) rowView.findViewById(R.id.textView11);
        TextView namaMahasiswa = (TextView) rowView.findViewById(R.id.textView14);
        TextView namaKelasMahasiswa = (TextView) rowView.findViewById(R.id.textView15);
        nimMahasiswa.setText(itemArrayList.get(position).getNim());
        namaMahasiswa.setText(itemArrayList.get(position).getNama());
        namaKelasMahasiswa.setText(itemArrayList.get(position).getNama_kelas());
        return rowView;
    }

}
