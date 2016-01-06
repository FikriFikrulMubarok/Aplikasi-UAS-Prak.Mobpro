package aapm.id.ac.uinsgd;


public class Mahasiswa {
    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama_mahasiswa() {
        return nama_mahasiswa;
    }

    public void setNama_mahasiswa(String nama_mahasiswa) {
        this.nama_mahasiswa = nama_mahasiswa;
    }

    private String nim;

    public Mahasiswa(String nim, String nama_mahasiswa) {
        this.nim = nim;
        this.nama_mahasiswa = nama_mahasiswa;
    }

    private String nama_mahasiswa;
}
