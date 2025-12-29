package itdestekuygulamasi;

import java.util.Scanner;

public class ItDestekUygulamasý {

    static Scanner scanner = new Scanner(System.in);

    static String[] talepId = new String[100];
    static String[] adSoyad = new String[100];
    static String[] problem = new String[100];
    static String[] durum = new String[100];

    static int talepSayisi = 0;

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

    static void talepEkle() {
        if (talepSayisi >= 100) {
            System.out.println("Talep kapasitesi dolu.");
            return;
        }

        System.out.print("Talep ID: ");
        String id = scanner.nextLine();

        for (int i = 0; i < talepSayisi; i++) {
            if (id.equals(talepId[i])) {
                System.out.println("Bu ID zaten mevcut.");
                return;
            }
        }

        talepId[talepSayisi] = id;

        System.out.print("Ad Soyad: ");
        adSoyad[talepSayisi] = scanner.nextLine();

        System.out.print("Problem: ");
        problem[talepSayisi] = scanner.nextLine();

        durum[talepSayisi] = "Açýk";
        talepSayisi++;

        System.out.println("Talep baþarýyla eklendi.");
    }

    static void talepSil() {
        System.out.print("Silinecek Talep ID: ");
        String id = scanner.nextLine();

        for (int i = 0; i < talepSayisi; i++) {
            if (id.equals(talepId[i])) {

                for (int j = i; j < talepSayisi - 1; j++) {
                    talepId[j] = talepId[j + 1];
                    adSoyad[j] = adSoyad[j + 1];
                    problem[j] = problem[j + 1];
                    durum[j] = durum[j + 1];
                }

                talepSayisi--;
                System.out.println("Talep silindi.");
                return;
            }
        }
        System.out.println("Talep bulunamadý.");
    }

    static void talepGuncelle() {
        System.out.print("Güncellenecek Talep ID: ");
        String id = scanner.nextLine();

        for (int i = 0; i < talepSayisi; i++) {
            if (id.equals(talepId[i])) {
                System.out.print("Yeni Durum (Yapýlmadý / Yapýldý): ");
                durum[i] = scanner.nextLine();
                System.out.println("Talep güncellendi.");
                return;
            }
        }
        System.out.println("Talep bulunamadý.");
    }

    static void talepAra() {
        System.out.print("Aranan Talep ID: ");
        String id = scanner.nextLine();

        for (int i = 0; i < talepSayisi; i++) {
            if (id.equals(talepId[i])) {
                System.out.println(
                        talepId[i] + " | " +
                        adSoyad[i] + " | " +
                        problem[i] + " | " +
                        durum[i]
                );
                return;
            }
        }
        System.out.println("Talep bulunamadý.");
    }

    static void talepleriListele() {
        if (talepSayisi == 0) {
            System.out.println("Kayýt bulunmamaktadýr.");
            return;
        }

        System.out.println("ID | Ad Soyad | Problem | Durum");
        System.out.println("--------------------------------");

        for (int i = 0; i < talepSayisi; i++) {
            System.out.println(
                    talepId[i] + " | " +
                    adSoyad[i] + " | " +
                    problem[i] + " | " +
                    durum[i]
            );
        }
    }

    public static void main(String[] args) {
        int secim;

        do {
            menuGoster();
            secim = scanner.nextInt();
            scanner.nextLine();

            switch (secim) {
                case 1:
                    talepEkle();
                    break;
                case 2:
                    talepSil();
                    break;
                case 3:
                    talepGuncelle();
                    break;
                case 4:
                    talepAra();
                    break;
                case 5:
                    talepleriListele();
                    break;
                case 0:
                    System.out.println("Programdan çýkýlýyor...");
                    break;
                default:
                    System.out.println("Hatalý seçim.");
            }

        } while (secim != 0);
    }
}
