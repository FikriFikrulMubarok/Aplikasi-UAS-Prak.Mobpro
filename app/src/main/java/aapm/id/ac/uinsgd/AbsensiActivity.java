package aapm.id.ac.uinsgd;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.util.Log;
import java.util.ArrayList;

public class AbsensiActivity extends Activity{
    private Spinner pilihKelas,keteranganKehadiran;
    private DB_Function akses_DB;
    private ArrayList<String> pilihKelasItem;
    private TextView pertemuan;
    private ListView absensi;
    private Button simpan,lihatAbsen;
    private String nama_kelas;
    private ArrayList<Mahasiswa> absensiItem;
    private int pertemuanke = 0;
    private ContentValues update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);

        akses_DB = new DB_Function(this);
        inisialisasi();
        setPilihKelas();
        lihatAbsenonClick();
        simpanonClick();
    }

    private void inisialisasi(){
        pilihKelas = (Spinner)findViewById(R.id.spinner2);
        pilihKelasItem = new ArrayList<String>();
        pertemuan = (TextView)findViewById(R.id.textView7);
        absensi = (ListView)findViewById(R.id.listView3);
        simpan = (Button)findViewById(R.id.button10);
        absensiItem = new ArrayList<Mahasiswa>();
        lihatAbsen = (Button)findViewById(R.id.button7);
    }

    private void setPilihKelas(){
        akses_DB.ambilData("select distinct nama_kelas from absensi");
        pilihKelasItem.add("Pilih Kelas");
        while(akses_DB.resultSet.moveToNext()){
            pilihKelasItem.add(akses_DB.resultSet.getString(0));
        }
        pilihKelas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, pilihKelasItem));
    }

    private int setPertemuan(){
        akses_DB.ambilData("select pertemuan_ke from kelas where nama_kelas='" + nama_kelas + "'");
        if(akses_DB.resultSet.moveToNext()){
            pertemuanke=akses_DB.resultSet.getInt(0)+1;
        }
        return pertemuanke;
    }

    private void setabsensi(String perintahSQL){
        akses_DB.ambilData(perintahSQL);
        absensi.setAdapter(new CustomListViewAdapterAbsensi(this, getAbsensiItem()));
    }

    private ArrayList<Mahasiswa> getAbsensiItem(){
        absensiItem.clear();
        while(akses_DB.resultSet.moveToNext()){
            absensiItem.add(new Mahasiswa(akses_DB.resultSet.getString(0),akses_DB.resultSet.getString(1)));
        }
        return absensiItem;
    }

    private void simpanonClick(){
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < absensi.getChildCount(); i++) {
                    keteranganKehadiran = (Spinner) absensi.getChildAt(i).findViewById(R.id.spinner5);
                    update = new ContentValues();
                    Log.d("Mahasiswa",String.valueOf(keteranganKehadiran.getSelectedItemPosition()));
                    switch (keteranganKehadiran.getSelectedItemPosition()){
                        case 0:
                            update.put("p" + pertemuanke, "6.25");
                            break;
                        case 1:
                            update.put("p" + pertemuanke, "3.125");
                            break;
                        case 2:
                            update.put("p" + pertemuanke, "3.125");
                            break;
                        case 3:
                            update.put("p" + pertemuanke, "0");
                            break;
                        default:
                            break;
                    }
                    akses_DB.updateData("absensi", update, "(nim='" + absensiItem.get(i).getNim() + "' and nama='" + absensiItem.get(i).getNama_mahasiswa() + "' and nama_kelas='" + pilihKelas.getSelectedItem().toString() + "')");
                }
                Toast.makeText(AbsensiActivity.this, "Absensi untuk mata kuliah " + pilihKelas.getSelectedItem().toString() + " pertemuan ke-" + pertemuanke + " telah tersimpan", Toast.LENGTH_SHORT).show();
                update = new ContentValues();
                update.put("pertemuan_ke", pertemuanke);
                akses_DB.updateData("kelas", update, "nama_kelas='" + pilihKelas.getSelectedItem().toString() + "'");
                pilihKelas.setSelection(0);
                invisible();
            }
        });
    }

    private void invisible(){
        pertemuan.setVisibility(View.INVISIBLE);
        absensi.setVisibility(View.INVISIBLE);
        simpan.setVisibility(View.INVISIBLE);
    }

    private void lihatAbsenonClick(){
        lihatAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pilihKelas.getSelectedItemPosition()==0){
                    invisible();
                }else{
                    nama_kelas=pilihKelas.getSelectedItem().toString();
                    setabsensi("select nim,nama from absensi where nama_kelas='" + nama_kelas + "'");
                    if (setPertemuan()<=16){
                        pertemuan.setText("Pertemuan ke-"+(pertemuanke));
                        pertemuan.setVisibility(View.VISIBLE);
                        absensi.setVisibility(View.VISIBLE);
                        simpan.setVisibility(View.VISIBLE);
                    }else{
                        new AlertDialog.Builder(AbsensiActivity.this).setTitle("Error!").setMessage("Kelas ini telah mencapai 16 pertemuan, anda tidak bisa melakukan lagi absensi!").setNeutralButton("Kembali", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                    }
                }
            }
        });
    }
}
