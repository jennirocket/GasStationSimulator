import java.util.EventObject;

/** Erstellt ein EventObject für eine Kasse. */
public class KasseEvent extends EventObject {
    private Kasse kasse;

    /**
     *
     * @param source ordnet das Event dem Objekt zu, an welchem es auftrat.
     * @param kasse ordnet der Instanz von ZapfsaeuleEvent einer bestimmten Zapfsaeule hinzu.
     */
    public KasseEvent(Object source, Kasse kasse) {
        super(source);
        this.kasse = kasse;
    }
    public Kasse getKasse() {
        return kasse;
    }
}
