package aapm.id.ac.uinsgd;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import java.util.ArrayList;

public class MahasiswaKelasActivity extends Activity {
    private DB_Function akses_DB;
    private EditText cariMahasiswa;
    private TextView nim,nama;
    private ArrayList<String> namaKelas;
    private Spinner kelas;
    private Button simpan,lihatDaftar;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswakelas);

        akses_DB = new DB_Function(this);
        inisialisasi();
        cariMahasiswaonTextChanged();
        setSpinnerItem();
        simpanonClick();
        lihatDaftaronClick();
    }

    private void inisialisasi(){
        cariMahasiswa = (EditText)findViewById(R.id.editText3);
        nim = (TextView)findViewById(R.id.textView3);
        nama = (TextView)findViewById(R.id.textView4);
        namaKelas = new ArrayList<String>();
        kelas = (Spinner)findViewById(R.id.spinner);
        simpan = (Button)findViewById(R.id.button9);
        lihatDaftar = (Button)findViewById(R.id.button6);
    }

    private void cariMahasiswaonTextChanged(){
        cariMahasiswa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cariMahasiswa.getText().toString().equalsIgnoreCase("")) {
                    nim.setText("NIM");
                    nama.setText("Nama");
                } else {
                    akses_DB.ambilData("select * from mahasiswa where nim like '%" + cariMahasiswa.getText() + "%' or nama like '%" + cariMahasiswa.getText() + "%'");
                    if(akses_DB.resultSet.moveToNext()){
                        nim.setText(akses_DB.resultSet.getString(0));
                        nama.setText(akses_DB.resultSet.getString(1));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setSpinnerItem(){
        akses_DB.ambilData("select nama_kelas from kelas");
        while(akses_DB.resultSet.moveToNext()){
            namaKelas.add(akses_DB.resultSet.getString(0));
        }
        kelas.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, namaKelas));
    }

    private void simpanonClick(){
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nim.getText().toString().equalsIgnoreCase("NIM") || nama.getText().toString().equalsIgnoreCase("Nama")) {
                    Toast.makeText(MahasiswaKelasActivity.this, "Pilih satu orang mahasiswa terlebih dahulu!", Toast.LENGTH_SHORT).show();
                } else {
                    akses_DB.simpanData("insert into absensi(nim,nama,nama_kelas) values('" + nim.getText() + "','" + nama.getText() + "','" + kelas.getSelectedItem().toString() + "')");
                    if (akses_DB.noerror) {
                        Toast.makeText(MahasiswaKelasActivity.this, nim.getText() + "\n" + nama.getText() + "\ntelah dimasukkan ke kelas " + kelas.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MahasiswaKelasActivity.this, nim.getText() + "\n" + nama.getText() + "\ngagal dimasukkan ke kelas " + kelas.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void lihatDaftaronClick(){
        lihatDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MahasiswaKelasActivity.this,DaftarMahasiswaKelasAcitivity.class);
                startActivity(intent);
            }
        });
    }
}
