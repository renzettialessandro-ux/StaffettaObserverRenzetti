/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threadrelay;

/**
 *
 * @author renzetti.alessandro
 */
public class Staffetta {

    private int nCorridori = 4;
    private Corridore[] corridori;
    private Thread[] threads;
    private StatoStaffetta stato;

    public Staffetta(int ritardoMillis) {
        stato = new StatoStaffetta();
        corridori = new Corridore[nCorridori];
        threads = new Thread[nCorridori];

        for (int i = 0; i < nCorridori; i++) {
            Object lock = new Object();
            corridori[i] = new Corridore(
                "Corridore " + (i + 1),
                i + 1,
                ritardoMillis,
                lock,
                stato
            );
            threads[i] = new Thread(corridori[i], "Thread-Corridore-" + (i + 1));
        }

        // Collega la catena: corridore[i] al 90% sveglia corridore[i+1]
        for (int i = 0; i < nCorridori - 1; i++) {
            corridori[i].setNextCorridore(corridori[i + 1]);
        }

        // Il primo corridore può partire subito
        corridori[0].daiVia();
    }

    // Restituisce i corridori alla GUI per il polling del progresso
    public Corridore[] getCorridori() {
        return corridori;
    }

    public void avviaStaffetta() {
        System.out.println("La staffetta inizia!");
        long inizio = System.currentTimeMillis();

        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Staffetta interrotta durante il join");
                Thread.currentThread().interrupt();
            }
        }

        long fine = System.currentTimeMillis();
        System.out.println("Staffetta finita! Tempo: " + (fine - inizio) + " ms");
    }

    public void sospendi() { stato.sospendi(); }
    public void riprendi() { stato.riprendi(); }

    public void ferma() {
        stato.ferma();
        for (Thread t : threads) {
            t.interrupt();
        }
    }
}

