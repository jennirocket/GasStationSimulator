import java.util.ArrayList;
import java.util.List;

/** Enthält die Attribute verfügbareKassen, welches am Anfang auf 0 initialisiert wird und je eine Liste von Kunden und Kassen.  */
public class KasseVerteiler implements KasseListener {
    private List kunden;
    private List kassen;
    private int verfuegbareKassen = 0;

    /** Konstruktor:
      */
    public KasseVerteiler() {
        this.kunden = new ArrayList();
        this.kassen = new ArrayList();
        this.verfuegbareKassen = verfuegbareKassen;
    }
    public int getKunden(){
        return kunden.size();
    }
 /** Stellt eine freie Kasse zur Nutzung zur Verfügung. */
    public Kasse getKasse() {
        Object notifyObject = new Object();
        synchronized (notifyObject) {
            synchronized (this) {
                verfuegbareKassen--;
                if (verfuegbareKassen < 0) {
                    kunden.add(notifyObject);
                } else {
                    notifyObject = null;
                }
            }
            if (notifyObject != null)
                try {
                    notifyObject.wait();
                } catch (InterruptedException e) {
                }
            return (Kasse) (kassen.remove(0));
        }
    }
    /** Gibt eine momentan benutzte Kasse nach der Nutzung wieder frei. */
    public synchronized void kasseFrei(KasseEvent e) {
        kassen.add(e.getKasse());
        verfuegbareKassen++;
        if (!kunden.isEmpty()) {
            Object notifyObject = kunden.remove(0);
            synchronized (notifyObject) {
                notifyObject.notify();
            }
        }
    }
}
