package aapm.id.ac.uinsgd;


public class DaftarNilai {
    public DaftarNilai(String nim, String nama, String nama_kelas, String persentase_kehadiran, String nilai) {
        this.nim = nim;
        this.nama = nama;
        this.nama_kelas = nama_kelas;
        this.persentase_kehadiran = persentase_kehadiran;
        this.nilai = nilai;
    }

    String nim;

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

    public String getPersentase_kehadiran() {
        return persentase_kehadiran;
    }

    public void setPersentase_kehadiran(String persentase_kehadiran) {
        this.persentase_kehadiran = persentase_kehadiran;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    String nama;
    String nama_kelas;
    String persentase_kehadiran;
    String nilai;
}
