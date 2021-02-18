import java.util.EventObject;

/** Erstellt ein EventObject für eine Zapfsäule. */
public class ZapfsaeuleEvent extends EventObject {
    private Zapfsaeule zapfsaeule;

    /**
     *
     * @param source ordnet das Event dem Objekt zu, an welchem es auftrat.
     * @param zapfsaeule ordnet der Instanz von ZapfsaeuleEvent einer bestimmten Zapfsaeule hinzu.
     */
    public ZapfsaeuleEvent(Object source, Zapfsaeule zapfsaeule) {
        super(source);
        this.zapfsaeule = zapfsaeule;
    }
    public Zapfsaeule getZapfsaeule() {
        return zapfsaeule;
    }
}
