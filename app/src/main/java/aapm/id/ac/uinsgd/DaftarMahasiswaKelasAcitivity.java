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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class DaftarMahasiswaKelasAcitivity extends Activity {
    private ListView daftarMahasiswaKelas;
    private EditText cariMahasiswaKelas;
    private DB_Function akses_DB;
    private ArrayList<MahasiswaKelas> mahasiswaKelas;
    private String nim,nama,nama_kelas;
    private LayoutInflater li;
    private View promptsView;
    private TextView textViewnim,textViewnama;
    private Spinner spinnerNama_Kelas;
    private ArrayList<String> spinnerNama_KelasItem;
    private ArrayAdapter spinnerNama_KelasAdapter;
    private ContentValues update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_mahasiswa_kelas);

        akses_DB = new DB_Function(this);
        inisialisasi();
        setDaftarMahasiswaKelas("select nim,nama,nama_kelas from absensi");
        daftarMahasiswaKelasonClick();
        daftarMahasiswaonLongClick();
        cariMahasiswaKelasonTextChanged();
    }

    private void inisialisasi(){
        daftarMahasiswaKelas = (ListView)findViewById(R.id.listView4);
        cariMahasiswaKelas = (EditText)findViewById(R.id.editText6);
        mahasiswaKelas = new ArrayList<MahasiswaKelas>();
        spinnerNama_KelasItem = new ArrayList<String>();

    }

    private void cariMahasiswaKelasonTextChanged(){
        cariMahasiswaKelas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(cariMahasiswaKelas.getText().toString().equalsIgnoreCase("")){
                    setDaftarMahasiswaKelas("select nim,nama,nama_kelas from absensi");
                }else{
                    setDaftarMahasiswaKelas("select nim,nama,nama_kelas from absensi where nim like '%" + cariMahasiswaKelas.getText() + "%' or nama like '%" + cariMahasiswaKelas.getText()+"%' or nama_kelas like '%"+cariMahasiswaKelas.getText()+"%'");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setDaftarMahasiswaKelas(String perintahSQL){
        akses_DB.ambilData(perintahSQL);
        daftarMahasiswaKelas.setAdapter(new CustomListViewAdapterMahasiswaKelas(this,setmahasiswaKelas()));
    }

    private ArrayList<MahasiswaKelas> setmahasiswaKelas(){
        mahasiswaKelas.clear();
        while(akses_DB.resultSet.moveToNext()){
            mahasiswaKelas.add(new MahasiswaKelas(akses_DB.resultSet.getString(0),akses_DB.resultSet.getString(1),akses_DB.resultSet.getString(2)));
        }
        return mahasiswaKelas;
    }

    private ArrayAdapter setspinnerNama_KelasAdapter(){
        akses_DB.ambilData("select nama_kelas from kelas");
        while(akses_DB.resultSet.moveToNext()){
            spinnerNama_KelasItem.add(akses_DB.resultSet.getString(0));
        }
        spinnerNama_KelasAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item,spinnerNama_KelasItem);
        return spinnerNama_KelasAdapter;
    }

    private void daftarMahasiswaKelasonClick(){
        daftarMahasiswaKelas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nim = mahasiswaKelas.get(position).getNim();
                nama=mahasiswaKelas.get(position).getNama();
                nama_kelas=mahasiswaKelas.get(position).getNama_kelas();
                li = LayoutInflater.from(DaftarMahasiswaKelasAcitivity.this);
                promptsView = li.inflate(R.layout.dialog_mahasiswa_kelas, null);
                textViewnim = (TextView)promptsView.findViewById(R.id.textView16);
                textViewnama = (TextView)promptsView.findViewById(R.id.textView17);
                spinnerNama_Kelas = (Spinner)promptsView.findViewById(R.id.spinner4);
                textViewnim.setText(nim);
                textViewnama.setText(nama);
                spinnerNama_Kelas.setAdapter(setspinnerNama_KelasAdapter());
                spinnerNama_Kelas.setSelection(((ArrayAdapter<String>)spinnerNama_Kelas.getAdapter()).getPosition(nama_kelas));
                new AlertDialog.Builder(DaftarMahasiswaKelasAcitivity.this).setView(promptsView).setTitle("Edit Kelas dari Mahasiswa").setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        update = new ContentValues();
                        update.put("nama_kelas", spinnerNama_Kelas.getSelectedItem().toString());
                        akses_DB.updateData("absensi", update, "(nim='" + nim + "' and nama='" + nama + "' and nama_kelas='" + nama_kelas + "')");
                        if (akses_DB.noerror) {
                            setDaftarMahasiswaKelas("select nim,nama,nama_kelas from absensi");
                            Toast.makeText(DaftarMahasiswaKelasAcitivity.this, nim + "\n" + nama + "\n" + nama_kelas + "\ntelah teredit", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DaftarMahasiswaKelasAcitivity.this, nim + "\n" + nama + "\n" + nama_kelas + "\ntidak teredit", Toast.LENGTH_SHORT).show();
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

    private void daftarMahasiswaonLongClick(){
        daftarMahasiswaKelas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                nim = mahasiswaKelas.get(position).getNim();
                nama=mahasiswaKelas.get(position).getNama();
                nama_kelas=mahasiswaKelas.get(position).getNama_kelas();
                new AlertDialog.Builder(DaftarMahasiswaKelasAcitivity.this).setTitle("Hapus Mahasiswa dari Kelas").setMessage("Apakah anda akan mengahapus \n"+nim+"\n"+nama+"\ndari kelas "+nama_kelas+"?").setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        akses_DB.hapusData("absensi","(nim='" + nim + "' and nama='" + nama + "' and nama_kelas='" + nama_kelas + "')");
                        if(akses_DB.noerror){
                            setDaftarMahasiswaKelas("select nim,nama,nama_kelas from absensi");
                            Toast.makeText(DaftarMahasiswaKelasAcitivity.this, nim + "\n" + nama + "\n" + nama_kelas + "\ntelah terhapus", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(DaftarMahasiswaKelasAcitivity.this, nim + "\n" + nama + "\n" + nama_kelas + "\ntidak terhapus", Toast.LENGTH_SHORT).show();
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
}
