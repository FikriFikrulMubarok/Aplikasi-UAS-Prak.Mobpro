package aapm.id.ac.uinsgd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.content.ContentValues;
import java.util.ArrayList;

public class NilaiActivity extends Activity {
    private DB_Function akses_DB;
    private Spinner pilihKelas;
    private ArrayList<String> pilihKelasItem;
    private EditText cariMahasiswa,nilai;
    private TextView nim,nama,persentaseKehadiran;
    private Button simpan,lihatNilai;
    private ContentValues update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai);

        akses_DB = new DB_Function(this);
        inisialisasi();
        setPilihKelas();
        cariMahasiswaonTextChanged();
        simpanonClick();
        lihatNilaionClick();
    }

    private void inisialisasi(){
        pilihKelas=(Spinner)findViewById(R.id.spinner3);
        pilihKelasItem = new ArrayList<String>();
        cariMahasiswa = (EditText)findViewById(R.id.editText4);
        nim = (TextView)findViewById(R.id.textView8);
        nama = (TextView)findViewById(R.id.textView9);
        persentaseKehadiran = (TextView)findViewById(R.id.textView10);
        nilai = (EditText)findViewById(R.id.editText5);
        simpan = (Button)findViewById(R.id.button12);
        lihatNilai = (Button)findViewById(R.id.button11);
    }

    private void lihatNilaionClick(){
        lihatNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NilaiActivity.this,DaftarNilaiActivity.class));
            }
        });
    }

    private void simpanonClick(){
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nim.getText().toString().equalsIgnoreCase("NIM")||nama.getText().toString().equalsIgnoreCase("Nama")||persentaseKehadiran.getText().toString().equalsIgnoreCase("Persentase Kehadiran")||persentaseKehadiran.getText().toString().equalsIgnoreCase("Selesaikan absensi hingga 16 pertemuan!")||nilai.getText().toString().isEmpty()){
                    Toast.makeText(NilaiActivity.this, "Pilih 1 mahasiswa atau lengkapi absensi hingga 16 pertemuan", Toast.LENGTH_SHORT).show();
                }else{
                    update = new ContentValues();
                    update.put("nilai_mahasiswa",nilai.getText().toString());
                    akses_DB.updateData("nilai",update,"(nim='"+nim.getText()+"' and nama='"+nama.getText()+"' and nama_kelas='"+pilihKelas.getSelectedItem().toString()+"')");
                    if(akses_DB.noerror){
                        cariMahasiswa.setText("");
                        Toast.makeText(NilaiActivity.this, "Nilai baru sudah disimpan", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(NilaiActivity.this, "Nilai baru tidak tersimpan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void cariMahasiswaonTextChanged(){
        cariMahasiswa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(cariMahasiswa.getText().toString().isEmpty()){
                    nim.setText("NIM");
                    nama.setText("Nama");
                    persentaseKehadiran.setText("Persentase Kehadiran");
                    nilai.setText("");
                }else{
                    akses_DB.ambilData("select nim,nama,(select (p1+p2+p3+p4+p5+p6+p7+p8+p9+p10+p11+p12+p13+p14+p15+p16) from absensi where ((nim like '%"+cariMahasiswa.getText()+"%' or nama like '%"+cariMahasiswa.getText()+"%') and nama_kelas='"+pilihKelas.getSelectedItem().toString()+"')),nilai_mahasiswa from nilai where ((nim like '%"+cariMahasiswa.getText()+"%' or nama like '%"+cariMahasiswa.getText()+"%') and nama_kelas='"+pilihKelas.getSelectedItem().toString()+"')");
                    if(akses_DB.resultSet.moveToNext()){
                        nim.setText(akses_DB.resultSet.getString(0));
                        nama.setText(akses_DB.resultSet.getString(1));
                        if(akses_DB.resultSet.getString(2)==null){
                            persentaseKehadiran.setText("Selesaikan absensi hingga 16 pertemuan!");
                        }else{
                            persentaseKehadiran.setText(akses_DB.resultSet.getString(2)+"%");
                        }
                        if(akses_DB.resultSet.getString(3)==null){
                            nilai.setText("");
                        }else{
                            nilai.setText(akses_DB.resultSet.getString(3));
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setPilihKelas(){
        akses_DB.ambilData("select distinct nama_kelas from absensi");
        while(akses_DB.resultSet.moveToNext()){
            pilihKelasItem.add(akses_DB.resultSet.getString(0));
        }
        pilihKelas.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item,pilihKelasItem));
    }
}
