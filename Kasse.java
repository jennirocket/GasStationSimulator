import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class Kasse {

    /** Enthält die Attribute nummer, für welche Nummer die jeweilige Instanz hat, eine List (die ich direkt als ArrayList definieren muss, weil sonst
     * in der notifyKasseFrei() Methode das .clone() nicht zu funktionieren scheint) von EventListener und eine
     * random Zahl zwischen 20 und 50s zum zahlen. */
        private ArrayList kasseListener;
        private int nummer;
        private int zahlzeit = (int) (2000/**0*/ + (Math.random() * 5000/**0*/));


    /** Konstruktor:
     *
     * @param nummer ordnet jeder Instanz von Kasse eine unterscheidbare Nummer zu.
     */
    public Kasse(int nummer) {
            this.kasseListener = new ArrayList<>();
            this.nummer = nummer;
            this.zahlzeit = zahlzeit;
        }
        public int getNummer() {
            return nummer;
        }
    /** Fügt einer Kasse einen EventListener hinzu. */
        public void addKasseListener(KasseListener a) {
            kasseListener.add(a);
        }
    /** Entfernt eninen EventListener von einer Kasse. */
        public void removeKasseListener(ZapfsaeuleListener a) {
            kasseListener.remove(a);
        }
    /** Methode signalisiert, dass Kasse frei ist. */
        public void notifyKasseFrei() {
            KasseEvent kv = new KasseEvent(this, this);
            ArrayList a;
            synchronized (this) {
                a = (ArrayList) kasseListener.clone();
            }
            Enumeration e1 = Collections.enumeration(a);
            while (e1.hasMoreElements()) {
                KasseListener K = (KasseListener)(e1.nextElement());
                K.kasseFrei(kv);
            }
        }
    /** Stellt die Kasse "an", beim initialisieren der Kasse(n), indem es die notifyKasseFrei() Methode aufruft. */
        public void kasseAn() {
            notifyKasseFrei();
        }
    /** Simuliert die Zeit, die es dauert, um zur Kasse zu gehen. */
        public void kasseStarten() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        }
    /** Simuliert die Zeit, die es dauert, um zu zahlen. */
        public void zahlen() {
            try {
                Thread.sleep(zahlzeit);
            } catch (InterruptedException e) {
            }
        }
    /** Simuliert die Zeit, die es dauert, sich von der Kasse zu entfernen (und damit der Tankstelle) und ruft notifyKasseFrei() Methode auf,
     * um zu signalisieren, dass die Kasse wieder frei ist.
     */
        public void fertig() {
            try {
                Thread.sleep(4000);
                notifyKasseFrei();
            } catch (InterruptedException e) {

            }
        }
}
