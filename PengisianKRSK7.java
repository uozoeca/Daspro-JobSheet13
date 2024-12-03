import java.util.Scanner;

class KRS {
    String namaMahasiswa;
    String nim;
    String kodeMatkul;
    String namaMatkul;
    int sks;

    KRS(String namaMahasiswa, String nim, String kodeMatkul, String namaMatkul, int sks) {
        this.namaMahasiswa = namaMahasiswa;
        this.nim = nim;
        this.kodeMatkul = kodeMatkul;
        this.namaMatkul = namaMatkul;
        this.sks = sks;
    }
}

public class PengisianKRSK7 {
    private static final Scanner sc = new Scanner(System.in);
    private static final int MAX_SKS = 24;
    private static final int MAX_MATAKULIAH = 10;
    private static KRS[][] dataKRS = new KRS[100][MAX_MATAKULIAH];

    public static int hitungTotalSKS(String nim) {
        int totalSKS = 0;
        for (int i = 0; i < dataKRS.length; i++) {
            for (int j = 0; j < MAX_MATAKULIAH; j++) {
                if (dataKRS[i][j] != null && dataKRS[i][j].nim.equals(nim)) {
                    totalSKS += dataKRS[i][j].sks;
                }
            }
        }
        return totalSKS;
    }

    public static void tambahDataKRS() {
        System.out.print("Masukkan Nama Mahasiswa: ");  
        String nama = sc.nextLine();
        System.out.print("Masukkan NIM: ");
        String nim = sc.nextLine();
        int totalSKS = hitungTotalSKS(nim);
        while (true) {
            System.out.print("Masukkan Kode Mata Kuliah: ");
            String kodeMatkul = sc.nextLine();
            System.out.print("Masukkan Nama Mata Kuliah: ");
            String namaMatkul = sc.nextLine();
            System.out.print("Masukkan Jumlah SKS (1-3): ");
            int sks = sc.nextInt();
            sc.nextLine();

            if (sks < 1 || sks > 3) {
                System.out.println("Jumlah SKS Tidak Valid. Harus Antara 1-3.");
                continue;
            }
            if (totalSKS + sks > MAX_SKS) {
                System.out.println("Total SKS Melebihi Batas Maksimal (24 SKS).\nMata Kuliah Tidak Dapat Ditambahkan.");
                break;
            }

            int mahasiswaIndex = -1;
            for (int i = 0; i < dataKRS.length; i++) {
                if (dataKRS[i][0] != null && dataKRS[i][0].nim.equals(nim)) {
                    mahasiswaIndex = i;
                    break;
                }
            }

            if (mahasiswaIndex == -1) {
                for (int i = 0; i < dataKRS.length; i++) {
                    if (dataKRS[i][0] == null) {
                        mahasiswaIndex = i;
                        break;
                    }
                }
                dataKRS[mahasiswaIndex][0] = new KRS(nama, nim, kodeMatkul, namaMatkul, sks);
            } else {
                for (int j = 0; j < MAX_MATAKULIAH; j++) {
                    if (dataKRS[mahasiswaIndex][j] == null) {
                        dataKRS[mahasiswaIndex][j] = new KRS(nama, nim, kodeMatkul, namaMatkul, sks);
                        break;
                    }
                }
            }
            totalSKS += sks;
            System.out.println("Data Berhasil Ditambahkan.");
            System.out.print("Apakah Anda Ingin Menambahkan Mata Kuliah Lagi? (Y/N): ");
            String lanjut = sc.nextLine();
            if (!lanjut.equalsIgnoreCase("Y")) break;
        }
    }

    public static void tampilkanDataKRS() {
        System.out.print("Masukkan NIM: ");
        String nim = sc.nextLine();
        System.out.printf("\nData KRS Untuk NIM %s:\n", nim);
        int totalSKS = 0;
        boolean found = false;
        for (int i = 0; i < dataKRS.length; i++) {
            for (int j = 0; j < MAX_MATAKULIAH; j++) {
                if (dataKRS[i][j] != null && dataKRS[i][j].nim.equals(nim)) {
                    System.out.printf("Kode Matkul: %s | Nama Matkul: %s | SKS: %d\n", dataKRS[i][j].kodeMatkul, dataKRS[i][j].namaMatkul, dataKRS[i][j].sks);
                    totalSKS += dataKRS[i][j].sks;
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("Data Tidak Ditemukan.");
        } else {
            System.out.printf("Total SKS: %d\n", totalSKS);
        }
    }

    public static void analisisSKS() {   
        int jumlahMahasiswa = 0;
        for (int i = 0; i < dataKRS.length; i++) {
            if (dataKRS[i][0] != null) {
                int totalSKS = hitungTotalSKS(dataKRS[i][0].nim);
                if (totalSKS < 20) {
                    jumlahMahasiswa++;
                }
            }
        }
        System.out.printf("Jumlah Mahasiswa Dengan Total SKS Kurang Dari 20: %d\n", jumlahMahasiswa);
    }

    public static void main(String[] args) {
        int pilihan;
        do {
            System.out.println("\nMenu Utama:");
            System.out.println("1. Tambah Data KRS Mahasiswa");
            System.out.println("2. Tampilkan Data KRS Berdasarkan NIM");
            System.out.println("3. Analisis Mahasiswa Dengan SKS < 20");
            System.out.println("4. Keluar");
            System.out.print("Pilih Menu: ");
            pilihan = sc.nextInt();
            sc.nextLine();

            switch (pilihan) {
                case 1:
                    tambahDataKRS();
                    break;
                case 2:
                    tampilkanDataKRS();
                    break;
                case 3:
                    analisisSKS();
                    break;
                case 4:
                    System.out.println("Keluar Dari Program. Matur Suwun!");
                    break;
                default:
                    System.out.println("Pilihan Tidak Valid!");
                    break;
            }            
        } while (pilihan != 4);
    }
}
