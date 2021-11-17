import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/** Hier sind die Attribute für die Fahrer Klasse. Die ersten drei sind dafür da, um die durschnittliche und maximale  Wartezeit
 * vor einer Zapfsäule und der Kasse zu berechnen.
 * Das nächste Attribut ist die Nummer, die jeder Kunde bekommt, damit dieser von anderen unterscheidbar ist.
 * Die nächsten Attribute sind jeweils Objekte der Kasse und Zapfsäule Klassen und deren Verteiler Klassen.
 * */
public class Fahrer implements Runnable {
    private float zeitTanken = 0;
    private float zeitZahlen = 0;
    private int fahrerInsgesamt = 0;
    private List<Float> listeZeitTanken;
    private List<Float> listeZeitZahlen;
    private int kundenNummer;
    private Zapfsaeule zapfsaeule;
    private Kasse kasse;
    private ZapfsaeuleVerteiler zapfsaeuleVerteiler;
    private KasseVerteiler kasseVerteiler;

    /** Konstruktor:
     * @param kundenNummer ordnet jeder Instanz von Fahrer eine unterscheidbare Kundennummer zu.
     * @param zapfsaeuleVerteiler ordnet jeder Instanz von Fahrer einen für diese verantwortliche ZapfsäuleVerteiler zu.
     * @param kasseVerteiler ordnet jeder Instanz von Fahrer einen für diese verantwortliche KasseVerteiler zu.
     */
    public Fahrer(int kundenNummer, ZapfsaeuleVerteiler zapfsaeuleVerteiler, KasseVerteiler kasseVerteiler) {
        this.kundenNummer = kundenNummer;
        this.zapfsaeuleVerteiler = zapfsaeuleVerteiler;
        this.kasseVerteiler = kasseVerteiler;
        this.listeZeitTanken = new ArrayList<Float>();
        this.listeZeitZahlen = new ArrayList<Float>();
    }
    /**
     * Soll die gesammelten Werte innerhalb der Liste listeZeitTanken,
     * welche die Werte speichert wie lange ein Kunde vor einer Zapfsäule warten muss, aufsummieren.
     */
    public int getZeitInsgesamtTanken() {
        int sum = 0;
        for (int i = 0; i < listeZeitTanken.size(); i++)
            sum += listeZeitTanken.get(i);
        return sum;
    }

    /**
     * Berechnet die durchschnittliche Zeit wie lange ein Kunde vor einer Zapfsäule ansteht bevor er an die Reihe kommt.
     */
    public float durchschnittZeitTanken() {
        return getZeitInsgesamtTanken() / fahrerInsgesamt;
    }
    /**
     * Sucht den maximalen Wert in der Liste listeZeitTanken, welches der insgesamt längsten Wartezeit vor einer Zapfsäule entspricht.
     */
    public float maximalZeitTanken() {
        return Collections.max(listeZeitTanken);
    }

    /**
     * Soll die gesammelten Werte innerhalb der Liste listeZeitTanken,
     * welche die Werte speichert wie lange ein Kunde vor einer Zapfsäule warten muss, aufsummieren.
     */
    public int getZeitInsgesamtZahlen() {
        int sum = 0;
        for (int i = 0; i < listeZeitZahlen.size(); i++)
            sum += listeZeitZahlen.get(i);
        return sum;
    }

    /**
     * Berechnet die durchschnittliche Zeit wie lange ein Kunde vor einer Kasse ansteht bevor er an die Reihe kommt.
     */
    public float durchschnittZeitZahlen() {
        return getZeitInsgesamtZahlen() / fahrerInsgesamt;
    }
    /**
     * Sucht den maximalen Wert in der Liste listeZeitZahlen, welches der insgesamt längsten Wartezeit vor einer Kasse entspricht.
     */
    public float maximalZeitZahlen() {
        return Collections.max(listeZeitZahlen);
    }
    /**
     * Das ist die run() Methode aus Runnable, hier soll der Fahrer erstmal eine random Zeit warten, bevor er zum Tanken kommt,
     * dann soll er sich beliebig in eine der Warteschlangen stellen, dann tanken, dann an die Kasse anstellen, zahlen und anschließend die Zapfsäule räumen.
     * Die startZeit und endZeit Attribute werden verwendet, damit die durchschnittliche und maximale Wartezeit vor einer Zapfsäule bzw. Kasse
     * errechnet werden kann. Bricht das Programm nach 5 Minuten Durchlauf ab.
     */
    public void run() {
            final int WARTE_ZEIT = 30000;
            long startZeitTanken;
            long endZeitTanken;
            long startZeitZahlen;
            long endZeitZahlen;
            Random random = new Random(kundenNummer);
            int waitTime = random.nextInt(WARTE_ZEIT);
            try {
                Thread.sleep(waitTime);
                startZeitTanken = (new Date()).getTime();
                System.out.printf("Kunde %d wartet um zu tanken.%n", kundenNummer);
                zapfsaeule = zapfsaeuleVerteiler.getZapfsäule();
                System.out.printf("Kunde %d fährt zu Zapfsäule %s.%n", kundenNummer, zapfsaeule.getNummer());
                endZeitTanken = (new Date()).getTime();
                zeitTanken = (endZeitTanken - startZeitTanken);
                listeZeitTanken.add(zeitTanken);
                zapfsaeule.zapfsaeuleStarten();
                System.out.printf("Kunde %d tankt.%n", kundenNummer);
                zapfsaeule.tanken();
                System.out.println(" Anzahl Wartender vor den Zapfsäulen: " + zapfsaeuleVerteiler.getKunden());
                System.out.println(" Anzahl Wartender vor den Kassen: " + kasseVerteiler.getKunden());
                startZeitZahlen = (new Date()).getTime();
                System.out.printf("Kunde %d will nun zahlen.%n", kundenNummer);
                kasse = kasseVerteiler.getKasse();
                System.out.printf("Kunde %d geht zu Kasse %d.%n", kundenNummer, kasse.getNummer());
                endZeitZahlen = (new Date()).getTime();
                zeitZahlen = (endZeitZahlen - startZeitZahlen);
                listeZeitZahlen.add(zeitZahlen);
                kasse.kasseStarten();
                System.out.printf("Kunde %d zahlt.%n", kundenNummer);
                kasse.zahlen();
                System.out.printf("Kunde %d verlässt die Zapfsäule %d.%n", kundenNummer, zapfsaeule.getNummer());
                zapfsaeule.fertig();
                System.out.printf("Kunde %d verlässt die Tankstelle.%n", kundenNummer);
                kasse.fertig();
                fahrerInsgesamt++;
            } catch (InterruptedException e) {

            } if (kundenNummer >= (Tankstelle.ANZAHL_KUNDEN) -1) {
                System.out.println("\n");
                System.out.println("Durchschnittliche Wartezeit vor einer Zapfsäule: " + (durchschnittZeitTanken() / 1000) / 60);
                System.out.println("Maximale Wartezeit an einer Zapfsäule: " + (maximalZeitTanken() / 1000) / 60);
                System.out.println("Durchschnittliche Wartezeit vor einer Kasse: " + (durchschnittZeitZahlen() / 1000) / 60);
                System.out.println("Maximale Wartezeit vor einer Kasse: " + (maximalZeitTanken() / 1000) / 60);
        }
    }
}


