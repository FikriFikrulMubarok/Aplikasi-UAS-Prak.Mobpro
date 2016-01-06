package aapm.id.ac.uinsgd;


public class MahasiswaKelas {
    public MahasiswaKelas(String nim, String nama, String nama_kelas) {
        this.nim = nim;
        this.nama = nama;
        this.nama_kelas = nama_kelas;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_kelas() {
        return nama_kelas;
    }

    public void setNama_kelas(String nama_kelas) {
        this.nama_kelas = nama_kelas;
    }

    String nim,nama,nama_kelas;
}
