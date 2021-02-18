import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/** Enthält die Attribute nummer, für welche Nummer die jeweilige Instanz hat, eine List (die ich direkt als ArrayList definieren muss, weil sonst
 * in der notifyZapfsaeuleFrei() Methode das .clone() nicht zu funktionieren scheint) von EventListener und je eine
 * random Zahl zwischen 200 und 300s und 30 und 70s für tanken bzw. zum Verlassen der Tankstelle. */
class Zapfsaeule {
    private int nummer;
    private ArrayList zapfsaeuleListener;
    private int tankzeit = (int) (2000/**00*/ + (Math.random() * 3000/**00*/));
    private int verlassen = (int) (3000/**0*/ + (Math.random() * 7000/**0*/));


    /** Konstruktor:
     *
     * @param nummer ordnet jeder Instanz von Zapfsäule eine unterscheidbare Nummer zu.
     */
    public Zapfsaeule(int nummer) {
        this.zapfsaeuleListener = new ArrayList<>();
        this.nummer = nummer;
        this.tankzeit = tankzeit;
        this.verlassen = verlassen;
    }
    public int getNummer() {
        return nummer;
    }
    /** Fügt einer Zapfsäule einen EventListener hinzu. */
    public void addZapfsaeuleListener(ZapfsaeuleListener a) {
        zapfsaeuleListener.add(a);
    }
    /** Entfernt eninen EventListener von einer Zapfsäule. */
    public void removeZapfsäuleListener(ZapfsaeuleListener a) {
        zapfsaeuleListener.remove(a);
    }
    /** Methode signalisiert, dass Zapfsäule frei ist. */
    public void notifyZapfsaeuleFrei() {
        ZapfsaeuleEvent zv = new ZapfsaeuleEvent(this, this);
        ArrayList a;
        synchronized (this) {
            a = (ArrayList) zapfsaeuleListener.clone();
        }
        Enumeration e1 = Collections.enumeration(a);
        while (e1.hasMoreElements()) {
            ZapfsaeuleListener Z = (ZapfsaeuleListener)(e1.nextElement());
            Z.zapfsaeuleFrei(zv);
        }
    }
    /** Stellt die Zapfsäule "an", beim initialisieren der Zapfsäulen, indem es die notifyZapfsäuleFrei() Methode aufruft. */
    public void zapfsaeuleAn() {
        notifyZapfsaeuleFrei();
    }
    /** Simuliert die Zeit, die es dauert, um zur Zapfsäule zu fahren. */
    public void zapfsaeuleStarten() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }
    /** Simuliert die Zeit, die es dauert, um zu tanken. */
    public void tanken() {
        try {
            Thread.sleep(tankzeit);
        } catch (InterruptedException e) {
        }
    }

    /** Simuliert die Zeit, die es dauert, sich von der Zapfsäule zu entfernen und ruft notifyZapfsäuleFrei() Methode auf,
     * um zu signalisieren, dass diese Zapfsäule wieder frei ist.
     */
    public void fertig() {
        try {
            Thread.sleep(verlassen);
            notifyZapfsaeuleFrei();
        } catch (InterruptedException e) {

        }
    }
}

