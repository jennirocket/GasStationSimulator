import java.lang.System;
import java.util.Collections;


public class Tankstelle {
    private static int ankunft = (int) (2100/**00*/ + (Math.random() * 2400/**00*/));

    /**
     * Erstellt eine Anzahl von Kunden, welche dann durchschnittlich alle 4 Minuten an der Tankstelle eintreffen
     * sowie verschiedene Instanzen von Zapfsäule und Kasse für den Durchlauf des Programmes (hier 1 Kasse, 3 Zapfsäulen),
     * deren EventListener und je ein KasseVerteiler und ZapfsäuleVerteiler. Stellt außerdem die Kasse und Zapfsäulen "an".
     * Erstellt dann einen Thread pro Fahrer Instanz und gibt am Ende die durchschnittliche sowie maximale Zeit in den
     * Warteschlangen in Minuten vor der Kasse sowie den Zapfsäulen aus.
     */
    public static int ANZAHL_KUNDEN = 10;

    public static void main(String[] args) throws InterruptedException {
        ZapfsaeuleVerteiler zapfsaeuleVerteiler = new ZapfsaeuleVerteiler();
        KasseVerteiler kasseVerteiler = new KasseVerteiler();
        Zapfsaeule zapfsaeule1 = new Zapfsaeule(1);
        zapfsaeule1.addZapfsaeuleListener(zapfsaeuleVerteiler);
        zapfsaeule1.zapfsaeuleAn();
        Zapfsaeule zapfsaeule2 = new Zapfsaeule(2);
        zapfsaeule2.addZapfsaeuleListener(zapfsaeuleVerteiler);
        zapfsaeule2.zapfsaeuleAn();
        Zapfsaeule zapfsaeule3 = new Zapfsaeule(3);
        zapfsaeule3.addZapfsaeuleListener(zapfsaeuleVerteiler);
        zapfsaeule3.zapfsaeuleAn();
        Kasse kasse1 = new Kasse(1);
        kasse1.addKasseListener(kasseVerteiler);
        kasse1.kasseAn();
            Thread[] threadArray = new Thread[ANZAHL_KUNDEN];
            for (int i = 0; i < ANZAHL_KUNDEN; i++) {
                threadArray[i] = new Thread(new Fahrer(i, zapfsaeuleVerteiler, kasseVerteiler));
                threadArray[i].start();
                Thread.sleep(ankunft);
            }
            for (int i = 0; i < ANZAHL_KUNDEN; i++) {
                try {
                    threadArray[i].join();
                } catch (InterruptedException e) {

                }
            }
    }
}
