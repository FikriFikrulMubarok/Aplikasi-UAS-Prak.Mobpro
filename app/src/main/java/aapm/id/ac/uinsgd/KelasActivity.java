package aapm.id.ac.uinsgd;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class KelasActivity extends Activity{

    private DB_Function akses_DB;
    private EditText cariKelas,nama_kelas_dialog;
    private ListView daftarKelas;
    private Button tambahKelas;
    private ArrayList<String> kelasArrayList;
    private LayoutInflater li;
    private View promptsView;
    private ContentValues update;
    private String nama_kelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas);

        akses_DB = new DB_Function(this);
        inisialisasi();
        setDaftarKelas("select nama_kelas from kelas");
        tambahKelasonClick();
        daftarKelasitemOnClick();
        daftarKelasitemonLongClick();
        cariKelasonTextChanged();
    }

    private void inisialisasi(){
        cariKelas = (EditText)findViewById(R.id.editText);
        daftarKelas = (ListView)findViewById(R.id.listView);
        tambahKelas = (Button)findViewById(R.id.button5);
        kelasArrayList = new ArrayList<String>();

    }

    private void setDaftarKelas(String perintahSQL){
        akses_DB.ambilData(perintahSQL);
        daftarKelas.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getKelasArrayList()));
    }

    private ArrayList<String> getKelasArrayList(){
        kelasArrayList.clear();
        while(akses_DB.resultSet.moveToNext()){
            kelasArrayList.add(akses_DB.resultSet.getString(0));
        }
        return kelasArrayList;
    }

    private void tambahKelasonClick(){
        tambahKelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                li = LayoutInflater.from(KelasActivity.this);
                promptsView = li.inflate(R.layout.dialog_kelas, null);
                nama_kelas_dialog = (EditText) promptsView.findViewById(R.id.nama_kelas_dialog);
                new AlertDialog.Builder(KelasActivity.this).setView(promptsView).setTitle("Tambah Kelas").setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (nama_kelas_dialog.getText().toString().isEmpty()) {
                            Toast.makeText(KelasActivity.this, "Isi nama kelas terlebih dahulu!", Toast.LENGTH_SHORT).show();
                        } else {
                            akses_DB.simpanData("insert into kelas(nama_kelas,pertemuan_ke) values('" + nama_kelas_dialog.getText().toString() + "','0')");
                            if (akses_DB.noerror) {
                                setDaftarKelas("select nama_kelas from kelas");
                                Toast.makeText(KelasActivity.this, nama_kelas_dialog.getText().toString() + " telah tersimpan", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(KelasActivity.this, nama_kelas_dialog.getText().toString() + " tidak tersimpan atau sudah ada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
    }

    private void daftarKelasitemOnClick(){
        daftarKelas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nama_kelas = daftarKelas.getItemAtPosition(position).toString();
                li = LayoutInflater.from(KelasActivity.this);
                promptsView = li.inflate(R.layout.dialog_kelas, null);
                nama_kelas_dialog = (EditText) promptsView.findViewById(R.id.nama_kelas_dialog);
                nama_kelas_dialog.setText(nama_kelas);
                new AlertDialog.Builder(KelasActivity.this).setView(promptsView).setTitle("Edit Kelas").setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (nama_kelas_dialog.getText().toString().isEmpty()) {
                            Toast.makeText(KelasActivity.this, "Isi nama kelas terlebih dahulu!", Toast.LENGTH_SHORT).show();
                        } else {
                            update = new ContentValues();
                            update.put("nama_kelas", nama_kelas_dialog.getText().toString());
                            akses_DB.updateData("kelas", update, "nama_kelas='" + nama_kelas + "'");
                            if (akses_DB.noerror) {
                                setDaftarKelas("select nama_kelas from kelas");
                                Toast.makeText(KelasActivity.this, nama_kelas_dialog.getText().toString() + " telah teredit", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(KelasActivity.this, nama_kelas_dialog.getText().toString() + " tidak teredit atau sudah ada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
    }

    private void daftarKelasitemonLongClick(){
        daftarKelas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                nama_kelas = daftarKelas.getItemAtPosition(position).toString();
                new AlertDialog.Builder(KelasActivity.this).setTitle("Hapus Kelas").setMessage("Apakah anda ingin menghapus kelas " + nama_kelas+ " ?").setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        akses_DB.hapusData("kelas", "nama_kelas='" +nama_kelas+ "'");
                        if (akses_DB.noerror) {
                            setDaftarKelas("select nama_kelas from kelas");
                            Toast.makeText(KelasActivity.this, nama_kelas+ " telah terhapus", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(KelasActivity.this, nama_kelas+ " tidak terhapus", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                return false;
            }
        });
    }

    private void cariKelasonTextChanged(){
        cariKelas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(cariKelas.getText().toString().equalsIgnoreCase("")){
                    setDaftarKelas("select nama_kelas from kelas");
                }else{
                    setDaftarKelas("select nama_kelas from kelas where nama_kelas like '%"+cariKelas.getText()+"%'");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
