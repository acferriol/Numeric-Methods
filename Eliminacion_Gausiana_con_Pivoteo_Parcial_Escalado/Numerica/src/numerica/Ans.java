/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package numerica;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JOptionPane;

public class Ans extends javax.swing.JFrame {

    public int n;
    public int[] it;
    public double[] s;
    public double[][] M;
    
    public Ans(int n, int[] it, double[] s, double[][] M) {
        this.n=n;
        this.it=it;
        this.s=s;
        this.M=M;
        initComponents();
        escalar();
    }
    
    private void escalar(){
        for (int i = 0; i < n - 1; i++) {
            double maxCoefficient = 0;
            int maxRow = i;

            for (int j = i; j < n; j++) {
                M[it[j]][i]=M[it[j]][i];
                if(s[it[j]]==0 && M[it[j]][n]!=0){
                    JOptionPane.showMessageDialog(null, "El sistema es incompatible.","Respuesta",JOptionPane.PLAIN_MESSAGE);
                    System.exit(0);
                }
                double scaledCoefficient = Math.abs(M[it[j]][i]) / s[it[j]];
                scaledCoefficient = scaledCoefficient;
                if (scaledCoefficient > maxCoefficient) {
                    maxCoefficient = scaledCoefficient;
                    maxRow = j;
                }
            }

            // Intercambiar filas en el índice
            int temp = it[i];
            it[i] = it[maxRow];
            it[maxRow] = temp;

            // Eliminar elementos debajo de la diagonal
            for (int j = i + 1; j < n; j++) {
                if (M[it[i]][i] == 0) continue;
                double m = M[it[j]][i] / M[it[i]][i];
                for (int k = i; k <= n; k++) {
                    M[it[j]][k] -= m * M[it[i]][k];
                    M[it[j]][k] = M[it[j]][k];
                }
            }
        }
        
        
        // Mostrar Matriz escalonada
        resultArea.setText(""); // Limpiar resultados anteriores
        resultArea.append("Matriz escalonada:\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                resultArea.append("  " + M[it[i]][j]);
            }
            resultArea.append("\n");
        }
        compatibilidad();
        
    }
    
    private void compatibilidad(){
        boolean ind = false;
        for (int i = 0; i < n; i++) {
            boolean allZero = true;
            for (int j = 0; j < n; j++) {
                if (M[it[i]][j] != 0) {
                    allZero = false;
                    break;
                }
            }
            if (allZero && M[it[i]][n] != 0) {
                ind = true;
                break;
            }
        }

        if (ind) {
            JOptionPane.showMessageDialog(null, "El sistema es incompatible.","Respuesta",JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }

        if (M[it[n - 1]][n - 1] == 0) {
            JOptionPane.showMessageDialog(null, "El sistema es compatible indeterminado.","Respuesta",JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }
        
        // Sustitución hacia atrás
        double[] x = new double[n];
        x[n - 1] = M[it[n - 1]][n] / M[it[n - 1]][n - 1];
        x[n - 1] = x[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += M[it[i]][j] * x[j];
            }
            x[i] = (M[it[i]][n] - sum) / M[it[i]][i];
        }
        
        resultArea.append("\n");
        for (int i = 0; i < n; i++) {
            resultArea.append("x_" + (i+1) + " = " + x[i] + "\n");
        }
        
    } 
            
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        resultArea = new java.awt.TextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Respuesta:");

        resultArea.setEditable(false);
        resultArea.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resultArea, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(resultArea, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private java.awt.TextArea resultArea;
    // End of variables declaration//GEN-END:variables
}
