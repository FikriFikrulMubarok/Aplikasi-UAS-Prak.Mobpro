package aapm.id.ac.uinsgd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private DB_Function akses_DB;
    private Intent intent;
    private Button kelas,mahasiswa,mahasiswakelas,absensi,nilai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        akses_DB = new DB_Function(this);
        try {
            akses_DB.createDataBase();
        }
        catch (Exception ioe) {
            new AlertDialog.Builder(this).setTitle("Error @ CreateDataBase").setMessage(ioe.toString()).setNeutralButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
        }
        try {
            akses_DB.openDataBase();
        }
        catch(SQLException sqle){
            new AlertDialog.Builder(this).setTitle("Error @ opendatabase").setMessage(sqle.toString()).setNeutralButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
        }

        inisialisasi();
        kelasOnClick();
        mahasiswaOnClick();
        mahasiswaKelasOnClick();
        absensiOnClick();
        nilaiOnClick();
    }

    private void inisialisasi(){
        kelas = (Button)findViewById(R.id.button);
        mahasiswa = (Button)findViewById(R.id.button2);
        mahasiswakelas = (Button)findViewById(R.id.button3);
        absensi= (Button)findViewById(R.id.button4);
        nilai = (Button)findViewById(R.id.button5);
    }

    private void kelasOnClick(){
        kelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, KelasActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mahasiswaOnClick(){
        mahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, MahasiswaActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mahasiswaKelasOnClick(){
        mahasiswakelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MahasiswaKelasActivity.class));
            }
        });
    }

    private void absensiOnClick(){
        absensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AbsensiActivity.class));
            }
        });
    }

    private void nilaiOnClick(){
        nilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NilaiActivity.class));
            }
        });
    }

}
