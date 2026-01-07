package itdestekuygulamasi;

import java.util.Scanner;
import java.io.*; // Dosya okuma yazma iþlemleri için gerekli kütüphane 

public class ÝtDestekUygulamasý {

   
    static Scanner scanner = new Scanner(System.in);
    
    // Verilerin tutulduðu diziler 
    static String[] talepId = new String[100];
    static String[] adSoyad = new String[100];
    static String[] problem = new String[100];
    static String[] durum = new String[100];
    
    static int talepSayisi = 0; // Mevcut toplam kayýt sayýsýný tutar
    static String dosyaAdi = "talepler.txt"; // Veritabaný görevi gören metin dosyasý

    /*
      Program açýldýðýnda 'talepler.txt' dosyasýndaki verileri okur.
   */
    static void dosyadanOku() {
        try {
            File dosya = new File(dosyaAdi);
            if (!dosya.exists()) return; // Dosya yoksa okuma yapmadan çýk

            Scanner dosyaOkuyucu = new Scanner(dosya);
            while (dosyaOkuyucu.hasNextLine() && talepSayisi < 100) {
                String satir = dosyaOkuyucu.nextLine();
                // Verileri '|' karakterine göre parçalara ayýrýr
                String[] parcalar = satir.split("\\|"); 
                if (parcalar.length == 4) {
                    talepId[talepSayisi] = parcalar[0].trim();
                    adSoyad[talepSayisi] = parcalar[1].trim();
                    problem[talepSayisi] = parcalar[2].trim();
                    durum[talepSayisi] = parcalar[3].trim();
                    talepSayisi++;
                }
            }
            dosyaOkuyucu.close();
        } catch (FileNotFoundException e) {
            System.out.println("Dosya okunurken hata oluþtu!");
        }
    }

    /*
      Dizilerdeki güncel verileri dosyaya kalýcý olarak yazar.
      Her ekleme, silme ve güncelleme iþleminden sonra çaðrýlýr. 
     */
    static void dosyayaKaydet() {
        try {
            FileWriter yazici = new FileWriter(dosyaAdi);
            for (int i = 0; i < talepSayisi; i++) {
                // Verileri belirli bir formatta dosyaya yazar 
                yazici.write(talepId[i] + " | " + adSoyad[i] + " | " + problem[i] + " | " + durum[i] + "\n");
            }
            yazici.close();
        } catch (IOException e) {
            System.out.println("Dosyaya yazýlýrken bir hata oluþtu!");
        }
    }

    /*
      Kullanýcýya ana menü seçeneklerini gösterir.
     */
    static void menuGoster() {
        System.out.println("\n--- IT DESTEK SÝSTEMÝ ---");
        System.out.println("1- Talep Ekle");
        System.out.println("2- Talep Sil");
        System.out.println("3- Talep Güncelle");
        System.out.println("4- Talep Ara");
        System.out.println("5- Talepleri Listele");
        System.out.println("0- Çýkýþ");
        System.out.print("Seçiminiz: ");
    }

    /*
      Yeni bir destek talebi alýr ve hem diziye hem dosyaya kaydeder.
     */
    static void talepEkle() {
        if (talepSayisi >= 100) {
            System.out.println("Hata: Kapasite dolu!");
            return;
        }

        System.out.print("Talep ID: ");
        String id = scanner.nextLine();

        // Ayný ID'ye sahip baþka bir kayýt olup olmadýðýný kontrol eder 
        for (int i = 0; i < talepSayisi; i++) {
            if (id.equals(talepId[i])) {
                System.out.println("Bu ID zaten kullanýmda!");
                return;
            }
        }

        talepId[talepSayisi] = id;
        System.out.print("Ad Soyad: ");
        adSoyad[talepSayisi] = scanner.nextLine();
        System.out.print("Problem: ");
        problem[talepSayisi] = scanner.nextLine();
        durum[talepSayisi] = "Açýk"; // Varsayýlan durum
        
        talepSayisi++;
        dosyayaKaydet(); // Veriyi dosyaya yedekle
        System.out.println("Talep sisteme kaydedildi.");
    }

    /*
     Belirtilen ID'ye sahip talebi siler ve diziyi kaydýrarak boþluðu doldurur.
     */
    static void talepSil() {
        System.out.print("Silinecek Talep ID: ");
        String id = scanner.nextLine();

        for (int i = 0; i < talepSayisi; i++) {
            if (id.equals(talepId[i])) {
                // Silinen öðeden sonrakileri bir öne kaydýrýr 
                for (int j = i; j < talepSayisi - 1; j++) {
                    talepId[j] = talepId[j + 1];
                    adSoyad[j] = adSoyad[j + 1];
                    problem[j] = problem[j + 1];
                    durum[j] = durum[j + 1];
                }
                talepSayisi--;
                dosyayaKaydet(); // Güncel listeyi dosyaya kaydet
                System.out.println("Talep baþarýyla silindi.");
                return;
            }
        }
        System.out.println("Kayýt bulunamadý.");
    }

    /*
      Mevcut bir talebin durumunu günceller.
     */
    static void talepGuncelle() {
        System.out.print("Güncellenecek Talep ID: ");
        String id = scanner.nextLine();

        for (int i = 0; i < talepSayisi; i++) {
            if (id.equals(talepId[i])) {
                System.out.print("Yeni Durum: ");
                durum[i] = scanner.nextLine();
                dosyayaKaydet(); // Deðiþikliði dosyaya yansýt
                System.out.println("Durum güncellendi.");
                return;
            }
        }
        System.out.println("Kayýt bulunamadý.");
    }

    /*
      Girilen ID'ye göre dizi içinde arama yapar.
     */
    static void talepAra() {
        System.out.print("Aranan Talep ID: ");
        String id = scanner.nextLine();

        for (int i = 0; i < talepSayisi; i++) {
            if (id.equals(talepId[i])) {
                System.out.println("Sonuç: " + talepId[i] + " - " + adSoyad[i] + " - " + durum[i]);
                return;
            }
        }
        System.out.println("Aranan ID bulunamadý.");
    }

    /*
      Tüm talepleri tablo þeklinde ekrana yazdýrýr.
     */
    static void talepleriListele() {
        if (talepSayisi == 0) {
            System.out.println("Sistemde hiç kayýt yok.");
            return;
        }
        System.out.println("\nID | AD SOYAD | PROBLEM | DURUM");
        for (int i = 0; i < talepSayisi; i++) {
            System.out.println(talepId[i] + " | " + adSoyad[i] + " | " + problem[i] + " | " + durum[i]);
        }
    }

   
    public static void main(String[] args) {
        dosyadanOku(); // Uygulama baþlarken verileri yükle
        int secim;

        do {
            menuGoster();
            secim = scanner.nextInt();
            scanner.nextLine(); // Scanner hatasýný önlemek için (buffer temizleme)

            switch (secim) { // Karar yapýsý: switch-case 
                case 1: talepEkle(); break;
                case 2: talepSil(); break;
                case 3: talepGuncelle(); break;
                case 4: talepAra(); break;
                case 5: talepleriListele(); break;
                case 0: System.out.println("Sistem kapatýlýyor..."); break;
                default: System.out.println("Lütfen geçerli bir seçim yapýn.");
            }
        } while (secim != 0); // Döngü yapýsý: do-while 
    }
}