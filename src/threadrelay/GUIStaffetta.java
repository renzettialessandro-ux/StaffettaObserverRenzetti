/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package threadrelay;

import java.awt.*;
import java.awt.Image;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 *
 * @author renzetti.alessandro
 */
public class GUIStaffetta extends javax.swing.JFrame implements Observer {

    private Staffetta staffetta;
    private Image imgCorridore;

    /**
     * Creates new form GUIStaffetta
     */
    public GUIStaffetta() {
        initComponents();
        imgCorridore = new ImageIcon(getClass().getResource("corridore.png")).getImage().getScaledInstance(50, -1, Image.SCALE_SMOOTH);
        inizializzaComponenti();
        setControlliEsecuzione(false);
    }

    private void impostaUICorridore(JProgressBar pb, Color colore) {
        final Color c = colore;
        final Image img = imgCorridore;

        pb.setUI(new BasicProgressBarUI() {

            @Override
            protected Color getSelectionForeground() {
                return Color.WHITE;
            }

            @Override
            protected Color getSelectionBackground() {
                return Color.WHITE;
            }

            @Override
            public void paintDeterminate(Graphics g, JComponent comp) {
                Insets b = progressBar.getInsets();
                int barW = progressBar.getWidth() - b.left - b.right;
                int barH = progressBar.getHeight() - b.top - b.bottom;
                int statoPb = getAmountFull(b, barW, barH);

                // Sfondo
                g.setColor(new Color(210, 215, 225));
                g.fillRect(b.left, b.top, barW, barH);

                // Barra colorata
                g.setColor(c);
                g.fillRect(b.left, b.top, statoPb, barH);

                // Immagine corridore in coda alla barra
                if (img != null) {
                    int carW = 50;
                    int carH = barH;
                    int carX = b.left + statoPb - carW;
                    if (carX < b.left) {
                        carX = b.left;
                    }
                    g.drawImage(img, carX, b.top, carW, carH, null);
                }

                if (progressBar.isStringPainted()) {
                    paintString(g, b.left, b.top, barW, barH, statoPb, b);
                }
            }
        });
    }

    private void inizializzaComponenti() {
        pbRunner1.setMinimum(0);
        pbRunner1.setMaximum(99);
        pbRunner1.setStringPainted(true);
        pbRunner2.setMinimum(0);
        pbRunner2.setMaximum(99);
        pbRunner2.setStringPainted(true);
        pbRunner3.setMinimum(0);
        pbRunner3.setMaximum(99);
        pbRunner3.setStringPainted(true);
        pbRunner4.setMinimum(0);
        pbRunner4.setMaximum(99);
        pbRunner4.setStringPainted(true);

        impostaUICorridore(pbRunner1, new Color(209, 209, 209));
        impostaUICorridore(pbRunner2, new Color(209, 209, 209));
        impostaUICorridore(pbRunner3, new Color(209, 209, 209));
        impostaUICorridore(pbRunner4, new Color(209, 209, 209));

        cmbVelocita.setModel(new DefaultComboBoxModel<>(new String[]{"Lenta", "Normale", "Veloce"}));
        cmbVelocita.setSelectedIndex(1);
        aggiornaLabel(lblRunner1, "Runner 1", 0, false);
        aggiornaLabel(lblRunner2, "Runner 2", 0, false);
        aggiornaLabel(lblRunner3, "Runner 3", 0, false);
        aggiornaLabel(lblRunner4, "Runner 4", 0, false);
    }

    private void aggiornaLabel(javax.swing.JLabel label, String nome, int progresso, boolean finito) {
        if (finito) {
            label.setText(nome + "                " + "Fine");
        } else {
            label.setText(nome + "                " + progresso);
        }
    }

    private void avviaStaffetta() {
        if (staffetta != null) {
            return;
        }
        resetProgressBar();
        aggiornaLabel(lblRunner1, "Runner 1", 0, false);
        aggiornaLabel(lblRunner2, "Runner 2", 0, false);
        aggiornaLabel(lblRunner3, "Runner 3", 0, false);
        aggiornaLabel(lblRunner4, "Runner 4", 0, false);
        int ritardo = getVelocitaRitardoMillis();
        staffetta = new Staffetta(ritardo);

        for (Corridore c : staffetta.getCorridori()) {
            c.addObserver(this);
        }
        setControlliEsecuzione(true);

        Thread launcher = new Thread(() -> {
            try {
                staffetta.avviaStaffetta();
            } finally {
                SwingUtilities.invokeLater(() -> {
                    setControlliEsecuzione(false);
                    staffetta = null;
                });
            }
        }, "Staffetta-Launcher");
        launcher.start();
    }

    private int getVelocitaRitardoMillis() {
        return switch ((String) cmbVelocita.getSelectedItem()) {
            case "Lenta" ->
                150;
            case "Veloce" ->
                40;
            default ->
                90;
        };
    }

    private void resetProgressBar() {
        pbRunner1.setValue(0);
        pbRunner2.setValue(0);
        pbRunner3.setValue(0);
        pbRunner4.setValue(0);
    }

    private void setControlliEsecuzione(boolean inCorso) {
        btnAvvia.setEnabled(!inCorso);
        cmbVelocita.setEnabled(!inCorso);
        btnSospende.setEnabled(inCorso);
        btnRiprende.setEnabled(inCorso);
        btnFerma.setEnabled(inCorso);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar2 = new javax.swing.JProgressBar();
        pbRunner1 = new javax.swing.JProgressBar();
        pbRunner2 = new javax.swing.JProgressBar();
        pbRunner3 = new javax.swing.JProgressBar();
        pbRunner4 = new javax.swing.JProgressBar();
        lblRunner1 = new javax.swing.JLabel();
        lblRunner2 = new javax.swing.JLabel();
        lblRunner3 = new javax.swing.JLabel();
        lblRunner4 = new javax.swing.JLabel();
        cmbVelocita = new javax.swing.JComboBox<>();
        btnAvvia = new javax.swing.JButton();
        btnSospende = new javax.swing.JButton();
        btnRiprende = new javax.swing.JButton();
        btnFerma = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pbRunner1.setMinimumSize(new java.awt.Dimension(395, 79));
        pbRunner1.setPreferredSize(new java.awt.Dimension(395, 79));

        pbRunner2.setMinimumSize(new java.awt.Dimension(395, 79));
        pbRunner2.setPreferredSize(new java.awt.Dimension(395, 79));

        pbRunner3.setMinimumSize(new java.awt.Dimension(395, 79));
        pbRunner3.setPreferredSize(new java.awt.Dimension(395, 79));

        lblRunner1.setText("Runner 1");
        lblRunner1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblRunner2.setText("Runner 2");
        lblRunner2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblRunner3.setText("Runner 3");
        lblRunner3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblRunner4.setText("Runner 4");
        lblRunner4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cmbVelocita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAvvia.setText("Avvia");
        btnAvvia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvviaActionPerformed(evt);
            }
        });

        btnSospende.setText("Sospende");
        btnSospende.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSospendeActionPerformed(evt);
            }
        });

        btnRiprende.setText("Riprende");
        btnRiprende.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRiprendeActionPerformed(evt);
            }
        });

        btnFerma.setText("Ferma");
        btnFerma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFermaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pbRunner4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbRunner3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbRunner2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbRunner1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRunner1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRunner2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRunner3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRunner4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(cmbVelocita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAvvia)
                .addGap(18, 18, 18)
                .addComponent(btnSospende)
                .addGap(18, 18, 18)
                .addComponent(btnRiprende)
                .addGap(18, 18, 18)
                .addComponent(btnFerma)
                .addContainerGap(76, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblRunner1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbRunner1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblRunner2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbRunner2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblRunner3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbRunner3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pbRunner4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRunner4, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbVelocita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAvvia)
                    .addComponent(btnSospende)
                    .addComponent(btnRiprende)
                    .addComponent(btnFerma))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAvviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvviaActionPerformed
        // TODO add your handling code here:
        avviaStaffetta();
        btnRiprende.setEnabled(false);
    }//GEN-LAST:event_btnAvviaActionPerformed

    private void btnSospendeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSospendeActionPerformed
        // TODO add your handling code here:
        if (staffetta != null) {
            staffetta.sospendi();
        }
        btnRiprende.setEnabled(true);
    }//GEN-LAST:event_btnSospendeActionPerformed

    private void btnRiprendeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRiprendeActionPerformed
        // TODO add your handling code here:
        if (staffetta != null)
            staffetta.riprendi();
    }//GEN-LAST:event_btnRiprendeActionPerformed

    private void btnFermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFermaActionPerformed
        // TODO add your handling code here:
        if (staffetta != null)
            staffetta.ferma();
    }//GEN-LAST:event_btnFermaActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvvia;
    private javax.swing.JButton btnFerma;
    private javax.swing.JButton btnRiprende;
    private javax.swing.JButton btnSospende;
    private javax.swing.JComboBox<String> cmbVelocita;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JLabel lblRunner1;
    private javax.swing.JLabel lblRunner2;
    private javax.swing.JLabel lblRunner3;
    private javax.swing.JLabel lblRunner4;
    private javax.swing.JProgressBar pbRunner1;
    private javax.swing.JProgressBar pbRunner2;
    private javax.swing.JProgressBar pbRunner3;
    private javax.swing.JProgressBar pbRunner4;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(int progresso, int numeroCorridore) {
        SwingUtilities.invokeLater(() -> {
            switch (numeroCorridore) {
                case 1 -> {
                    pbRunner1.setValue(progresso);
                    aggiornaLabel(lblRunner1, "Runner 1", progresso, progresso >= 99);
                }
                case 2 -> {
                    pbRunner2.setValue(progresso);
                    aggiornaLabel(lblRunner2, "Runner 2", progresso, progresso >= 99);
                }
                case 3 -> {
                    pbRunner3.setValue(progresso);
                    aggiornaLabel(lblRunner3, "Runner 3", progresso, progresso >= 99);
                }
                case 4 -> {
                    pbRunner4.setValue(progresso);
                    aggiornaLabel(lblRunner4, "Runner 4", progresso, progresso >= 99);
                }
            }
        });
    }
}
