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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class MahasiswaActivity extends Activity{
    private DB_Function akses_DB;
    private EditText cariMahasiswa, nama_mahasiswa_dialog,nim_mahasiswa_dialog;
    private ListView daftarMahasiswa;
    private Button tambahMahasiswa;
    private ArrayList<Mahasiswa> mahasiswaArrayList;
    private LayoutInflater li;
    private View promptsView;
    private ContentValues update;
    private String nim_mahasiswa,nama_mahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        akses_DB = new DB_Function(this);
        inisialisasi();
        setDaftarMahasiswa("select * from mahasiswa");
        tambahMahasiswaonClick();
        daftarMahasiswaitemOnClick();
        daftarMahasiswaitemonLongClick();
        cariMahasiswaonTextChanged();
    }

    private void inisialisasi(){
        cariMahasiswa = (EditText)findViewById(R.id.editText2);
        daftarMahasiswa = (ListView)findViewById(R.id.listView2);
        tambahMahasiswa = (Button)findViewById(R.id.button8);
        mahasiswaArrayList = new ArrayList<Mahasiswa>();

    }

    private void setDaftarMahasiswa(String perintahSQL){
        akses_DB.ambilData(perintahSQL);
        daftarMahasiswa.setAdapter(new CustomListViewAdapterMahasiswa(this,getMahasiswaArrayList()));
    }

    private ArrayList<Mahasiswa> getMahasiswaArrayList(){
        mahasiswaArrayList.clear();
        while(akses_DB.resultSet.moveToNext()){
            mahasiswaArrayList.add(new Mahasiswa(akses_DB.resultSet.getString(0),akses_DB.resultSet.getString(1)));
        }
        return mahasiswaArrayList;
    }

    private void tambahMahasiswaonClick(){
        tambahMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                li = LayoutInflater.from(MahasiswaActivity.this);
                promptsView = li.inflate(R.layout.dialog_mahasiswa, null);
                nim_mahasiswa_dialog = (EditText) promptsView.findViewById(R.id.nim_mahasiswa_dialog);
                nama_mahasiswa_dialog = (EditText) promptsView.findViewById(R.id.nama_mahasiswa_dialog);
                new AlertDialog.Builder(MahasiswaActivity.this).setView(promptsView).setTitle("Tambah Mahasiswa").setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (nim_mahasiswa_dialog.getText().toString().isEmpty() || nama_mahasiswa_dialog.getText().toString().isEmpty()) {
                            Toast.makeText(MahasiswaActivity.this, "Isi nim dan nama terlebih dahulu!", Toast.LENGTH_SHORT).show();
                        } else {
                            akses_DB.simpanData("insert into mahasiswa(nim,nama) values('" + nim_mahasiswa_dialog.getText() + "','" + nama_mahasiswa_dialog.getText() + "')");
                            if (akses_DB.noerror) {
                                setDaftarMahasiswa("select * from mahasiswa");
                                Toast.makeText(MahasiswaActivity.this, nim_mahasiswa_dialog.getText() + "\n" + nama_mahasiswa_dialog.getText() + "\ntelah tersimpan", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MahasiswaActivity.this, nim_mahasiswa_dialog.getText() + "\n" + nama_mahasiswa_dialog.getText() + "tidak tersimpan atau sudah ada", Toast.LENGTH_SHORT).show();
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

    private void daftarMahasiswaitemOnClick(){
        daftarMahasiswa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nim_mahasiswa = mahasiswaArrayList.get(position).getNim();
                nama_mahasiswa = mahasiswaArrayList.get(position).getNama_mahasiswa();
                li = LayoutInflater.from(MahasiswaActivity.this);
                promptsView = li.inflate(R.layout.dialog_mahasiswa, null);
                nim_mahasiswa_dialog = (EditText) promptsView.findViewById(R.id.nim_mahasiswa_dialog);
                nama_mahasiswa_dialog = (EditText) promptsView.findViewById(R.id.nama_mahasiswa_dialog);
                nim_mahasiswa_dialog.setText(nim_mahasiswa);
                nama_mahasiswa_dialog.setText(nama_mahasiswa);
                nim_mahasiswa_dialog.setEnabled(false);
                new AlertDialog.Builder(MahasiswaActivity.this).setView(promptsView).setTitle("Edit Mahasiswa").setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (nim_mahasiswa_dialog.getText().toString().isEmpty() || nama_mahasiswa_dialog.getText().toString().isEmpty()) {
                            Toast.makeText(MahasiswaActivity.this, "Isi nim dan nama terlebih dahulu!", Toast.LENGTH_SHORT).show();
                        } else {
                            update = new ContentValues();
                            update.put("nama", nama_mahasiswa_dialog.getText().toString());
                            akses_DB.updateData("mahasiswa", update, "nim='" + nim_mahasiswa + "'");
                            if (akses_DB.noerror) {
                                setDaftarMahasiswa("select * from mahasiswa");
                                Toast.makeText(MahasiswaActivity.this, nim_mahasiswa_dialog.getText() + "\n" + nama_mahasiswa_dialog.getText() + "\ntelah teredit", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MahasiswaActivity.this, nim_mahasiswa_dialog.getText() + "\n" + nama_mahasiswa_dialog.getText() + "\ntidak teredit atau sudah ada", Toast.LENGTH_SHORT).show();
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

    private void daftarMahasiswaitemonLongClick(){
        daftarMahasiswa.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                nim_mahasiswa = mahasiswaArrayList.get(position).getNim();
                nama_mahasiswa = mahasiswaArrayList.get(position).getNama_mahasiswa();
                new AlertDialog.Builder(MahasiswaActivity.this).setTitle("Hapus Mahasiswa").setMessage("Apakah anda ingin menghapus mahasiswa " +nim_mahasiswa+" "+ nama_mahasiswa + " ?").setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        akses_DB.hapusData("mahasiswa", "nim='" + nim_mahasiswa + "'");
                        if (akses_DB.noerror) {
                            setDaftarMahasiswa("select * from mahasiswa");
                            Toast.makeText(MahasiswaActivity.this, nim_mahasiswa+" "+ nama_mahasiswa + " telah terhapus", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MahasiswaActivity.this, nim_mahasiswa+" "+ nama_mahasiswa + " tidak terhapus", Toast.LENGTH_SHORT).show();
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

    private void cariMahasiswaonTextChanged(){
        cariMahasiswa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cariMahasiswa.getText().toString().equalsIgnoreCase("")) {
                    setDaftarMahasiswa("select * from mahasiswa");
                } else {
                    setDaftarMahasiswa("select * from mahasiswa where nim like '%" + cariMahasiswa.getText() + "%' or nama like '%" + cariMahasiswa.getText() + "%'");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
