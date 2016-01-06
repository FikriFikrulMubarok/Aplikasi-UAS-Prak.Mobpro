package aapm.id.ac.uinsgd;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;

public class DaftarNilaiActivity extends Activity {
    private DB_Function akses_DB;
    private ListView daftarNilai;
    private ArrayList<DaftarNilai> daftarNilaiItem;
    private EditText cariMahasiswa;
    private Spinner daftarKelas;
    private ArrayList<String> daftarKelasItem;
    private Button lihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_nilai);

        akses_DB = new DB_Function(this);
        inisialisasi();
        setDaftarKelas();
        lihatonClick();
        setDaftarNilai("select nim,nama,nama_kelas,(select (p1+p2+p3+p4+p5+p6+p7+p8+p9+p10+p11+p12+p13+p14+p15+p16) from absensi where (absensi.nim=nilai.nim and absensi.nama=nilai.nama and absensi.nama_kelas=nilai.nama_kelas)),nilai_mahasiswa from nilai");

    }

    private void inisialisasi(){
        daftarNilai = (ListView)findViewById(R.id.listView5);
        daftarNilaiItem = new ArrayList<DaftarNilai>();
        cariMahasiswa = (EditText)findViewById(R.id.editText7);
        daftarKelas = (Spinner)findViewById(R.id.spinner6);
        daftarKelasItem = new ArrayList<String>();
        lihat = (Button)findViewById(R.id.button13);
    }

    private void lihatonClick(){
        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cariMahasiswa.getText().toString().isEmpty()){
                    Toast.makeText(DaftarNilaiActivity.this, "Masukkan NIM atau Nama Mahasiswa terlebih dahulu!", Toast.LENGTH_SHORT).show();
                }else{
                    setDaftarNilai("select nim,nama,nama_kelas,(select (p1+p2+p3+p4+p5+p6+p7+p8+p9+p10+p11+p12+p13+p14+p15+p16) from absensi where ((nim='"+cariMahasiswa.getText()+"' or nama='"+cariMahasiswa.getText()+"') and nama_kelas='"+daftarKelas.getSelectedItem().toString()+"')),nilai_mahasiswa from nilai where ((nim='"+cariMahasiswa.getText()+"' or nama='"+cariMahasiswa.getText()+"') and nama_kelas='"+daftarKelas.getSelectedItem().toString()+"')");
                }
            }
        });
    }

    private void setDaftarKelas(){
        daftarKelasItem.clear();
        akses_DB.ambilData("select distinct nama_kelas from nilai");
        while(akses_DB.resultSet.moveToNext()){
            daftarKelasItem.add(akses_DB.resultSet.getString(0));
        }
        daftarKelas.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item,daftarKelasItem));
    }

    private void setDaftarNilai(String perintahSQL){
        daftarNilaiItem.clear();
        akses_DB.ambilData(perintahSQL);
        while(akses_DB.resultSet.moveToNext()){
            daftarNilaiItem.add(new DaftarNilai(akses_DB.resultSet.getString(0),akses_DB.resultSet.getString(1),akses_DB.resultSet.getString(2),akses_DB.resultSet.getString(3),akses_DB.resultSet.getString(4)));
        }
        daftarNilai.setAdapter(new CustomListViewAdapterDaftarNilai(this,daftarNilaiItem));
    }
}
