/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threadrelay;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author renzetti.alessandro
 */
public class Corridore implements Runnable, Subject {

    private String nome;
    private int numeroRunner;
    private int ritardoMillis;
    private int progresso;
    //Lock su cui questo corridore aspetta il proprio turno
    private Object startLock;
    private boolean startReady;
    private Corridore nextCorridore;
    private StatoStaffetta stato;
    private final List<Observer> observers = new ArrayList<>();

    public Corridore(String nome, int numeroRunner, int ritardoMillis, Object startLock, StatoStaffetta stato) {
        this.nome = nome;
        this.numeroRunner = numeroRunner;
        this.ritardoMillis = ritardoMillis;
        this.startLock = startLock;
        this.stato = stato;
        this.progresso = 0;
        this.startReady = false;
    }

    public void setNextCorridore(Corridore next) {
        this.nextCorridore = next;
    }

    /**
     * Chiamato dal Corridore precedente al 90% Fatto dall'ia
     */
    public void daiVia() {
        synchronized (startLock) {
            startReady = true;
            startLock.notifyAll();
        }
    }

    public int getProgresso() {
        return progresso;
    }

    public int getNumeroRunner() {
        return numeroRunner;
    }

    @Override
    public void run() {
        try {
            synchronized (startLock) {
                while (!startReady && !stato.isFermato()) {
                    startLock.wait();
                }
            }

            if (stato.isFermato()) {
                return;
            }

            System.out.println(nome + " inizia a correre!");

            for (int i = 0; i <= 99 && !stato.isFermato(); i++) {
                stato.attendiSeSospeso();
                progresso = i;
                notifyObservers();
                
                if (i == 90 && nextCorridore != null) {
                    nextCorridore.daiVia();
                }

                Thread.sleep(ritardoMillis);
            }

            if (!stato.isFermato()) {
                System.out.println(nome + " ha completato la corsa!");
            }

        } catch (InterruptedException e) {
            System.out.println(nome + " interrotto");
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void addObserver(Observer o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        List<Observer> copia = new ArrayList<>(observers);
        for (Observer o : copia) {
            o.update(progresso, numeroRunner);
        }
    }
}
