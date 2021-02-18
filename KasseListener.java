import java.util.EventListener;

/** Erstellt einen EventListener für KasseEvent. */
public interface KasseListener extends EventListener {
    public void kasseFrei(KasseEvent e);
}