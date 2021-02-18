import java.util.EventListener;

/** Erstellt einen ZapfsäuleListener für ZapfsäuleEvent. */
public interface ZapfsaeuleListener extends EventListener {
    public void zapfsaeuleFrei(ZapfsaeuleEvent e);
}
