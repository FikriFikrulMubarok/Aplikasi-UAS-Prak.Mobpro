package aapm.id.ac.uinsgd;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListViewAdapterDaftarNilai extends ArrayAdapter<DaftarNilai> {
    private Context context;
    private ArrayList<DaftarNilai> itemArrayList;

    public CustomListViewAdapterDaftarNilai(Context context, ArrayList<DaftarNilai> itemArrayList) {
        super(context, R.layout.listview_daftar_nilai,itemArrayList);
        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listview_daftar_nilai, parent, false);
        TextView nimMahasiswa = (TextView) rowView.findViewById(R.id.textView6);
        TextView namaMahasiswa = (TextView) rowView.findViewById(R.id.textView18);
        TextView namaKelasMahasiswa = (TextView) rowView.findViewById(R.id.textView19);
        TextView persentaseKehadiranMahasiswa = (TextView) rowView.findViewById(R.id.textView20);
        TextView nilaiMahasiswa = (TextView) rowView.findViewById(R.id.textView21);
        nimMahasiswa.setText(itemArrayList.get(position).getNim());
        namaMahasiswa.setText(itemArrayList.get(position).getNama());
        namaKelasMahasiswa.setText(itemArrayList.get(position).getNama_kelas());
        persentaseKehadiranMahasiswa.setText(itemArrayList.get(position).getPersentase_kehadiran());
        nilaiMahasiswa.setText(itemArrayList.get(position).getNilai());
        return rowView;
    }

}
