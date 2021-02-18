import java.util.ArrayList;
import java.util.List;

/** Enthält die Attribute verfügbareZapfsaeulen, welches am Anfang auf 0 initialisiert wird und je eine Liste von Kunden und Zapfsäulen.  */
public class ZapfsaeuleVerteiler implements ZapfsaeuleListener {
    private List kunden;
    private List zapfsaeulen;
    private int verfuegbareZapfsaeulen = 0;

    /**
     Konstruktor:
     */
    public ZapfsaeuleVerteiler() {
        this.kunden = new ArrayList();
        this.zapfsaeulen = new ArrayList();
        this.verfuegbareZapfsaeulen = verfuegbareZapfsaeulen;
    }
    public int getKunden(){
        return kunden.size();
    }
    /** Stellt eine freie Zapfsäule zur Nutzung zur Verfügung. */
    public Zapfsaeule getZapfsäule() {
        Object notifyObject = new Object();
        synchronized (notifyObject) {
            synchronized (this) {
                verfuegbareZapfsaeulen--;
                if (verfuegbareZapfsaeulen < 0) {
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
            return (Zapfsaeule) (zapfsaeulen.remove(0));
        }
    }
    /** Gibt eine momentan benutzte Zapfsäule nach der Nutzung wieder frei. */
    public synchronized void zapfsaeuleFrei(ZapfsaeuleEvent e) {
        zapfsaeulen.add(e.getZapfsaeule());
        verfuegbareZapfsaeulen++;
        if (!kunden.isEmpty()) {
            Object notifyObject = kunden.remove(0);
            synchronized (notifyObject) {
                notifyObject.notify();
            }
        }
    }
}

