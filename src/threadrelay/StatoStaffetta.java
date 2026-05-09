/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threadrelay;

public class StatoStaffetta {

    private boolean sospeso = false;
    private boolean fermato = false;

    public synchronized void sospendi() {
        sospeso = true;
    }

    public synchronized void riprendi() {
        sospeso = false;
        notifyAll();
    }

    public synchronized void ferma() {
        fermato = true;
        sospeso = false;
        notifyAll();
    }

    public synchronized void reset() {
        fermato = false;
        sospeso = false;
        notifyAll();
    }

    
    public synchronized void attendiSeSospeso() throws InterruptedException {
        while (sospeso && !fermato) {
            wait();
        }
        if (fermato) {
            throw new InterruptedException("Staffetta fermata");
        }
    }

    public synchronized boolean isFermato() {
        return fermato;
    }
}
