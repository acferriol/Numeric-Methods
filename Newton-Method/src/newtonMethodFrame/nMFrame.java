package newtonMethodFrame;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.lsmp.djep.djep.DJep;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;

/**
 *
 * @author dlam
 */
public class nMFrame extends javax.swing.JFrame {

    private JEP jep;
    private DJep djep;

    public nMFrame() {
        initComponents();
        initializeJEP();
        initializeDJep();
        initializeGraph();
    }

    private void initializeJEP() {
        jep = new JEP();
        jep.addStandardFunctions();
        jep.addStandardConstants();
    }

    private void initializeDJep() {
        djep = new DJep();
        djep.addStandardFunctions();
        djep.addStandardConstants();
        djep.addComplex();
        djep.setAllowUndeclared(true);
        djep.setAllowAssignment(true);
        djep.setImplicitMul(true);
        djep.addStandardDiffRules();
    }

    private void initializeGraph() {
        XYSeries series = new XYSeries("");
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = createGraphic(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(397, 502));
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setDisplayToolTips(false);
        chartPanel.setPopupMenu(null);
        graphPanel.setLayout(new java.awt.BorderLayout());
        graphPanel.add(chartPanel, BorderLayout.CENTER);
        graphPanel.validate();
    }

    private JFreeChart createGraphic(XYSeriesCollection dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart("", "", "", dataset, PlotOrientation.VERTICAL, false, false, false);

        XYPlot plot = chart.getXYPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        rangeAxis.setRange(-10, 10);
        domainAxis.setRange(-10, 10);
        plot.getDomainAxis().setLabel("");
        plot.getRangeAxis().setLabel("");
        plot.setBackgroundPaint(Color.WHITE);
        chart.setBackgroundPaint(new Color(242, 242, 242));

        ValueMarker markerX = new ValueMarker(0);
        markerX.setPaint(Color.BLACK);
        markerX.setStroke(new BasicStroke(1.0f));
        plot.addRangeMarker(markerX);

        ValueMarker markerY = new ValueMarker(0);
        markerY.setPaint(Color.BLACK);
        markerY.setStroke(new BasicStroke(1.0f));
        plot.addDomainMarker(markerY);

        return chart;
    }

    private XYSeriesCollection assignValues(String expresion) {
        XYSeries series = new XYSeries(expresion);
        for (double x = -10; x <= 10; x += 0.1) {
            djep.addVariable("x", x);
            djep.parseExpression(expresion);
            double y = djep.getValue();
            series.add(x, y);
        }
        return new XYSeriesCollection(series);
    }

    private String derivate(String expression) {
        try {
            Node functionNode = djep.parse(expression);
            Node derivativeNode = djep.differentiate(functionNode, "x");
            Node simplifiedNode = djep.simplify(derivativeNode);
            String derivative = djep.toString(simplifiedNode);
            return derivative;
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Error parsing the expression: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private double evaluate(String expression, double xValue) {
        djep.addVariable("x", xValue);
        djep.parseExpression(expression);
        return djep.getValue();
    }

    private double newtonRaphson(String function, String functionDerivative, double approx, double tol, int maxIterations) {
        int count = 0;
        double p;

        while (count < maxIterations) {
            double fValue = evaluate(function, approx);
            double fDerivativeValue = evaluate(functionDerivative, approx);

            if (fDerivativeValue == 0) {
                JOptionPane.showMessageDialog(this, "Derivative equal to zero, division by zero.", "Error", JOptionPane.ERROR_MESSAGE);
                return Double.NaN;
            }

            p = approx - fValue / fDerivativeValue;

            resultsTextArea.append("p" + (count + 1) + " = " + p + "\n");

            if (Math.abs(p - approx) < tol) {
                JOptionPane.showMessageDialog(this, "Converged to root: " + p, "Result", JOptionPane.INFORMATION_MESSAGE);
                return p;
            }

            count++;
            approx = p;
        }

        JOptionPane.showMessageDialog(this, "The method did not converge within the maximum number of iterations.", "Warning", JOptionPane.WARNING_MESSAGE);
        return Double.NaN;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        graphPanel = new javax.swing.JPanel();
        functionTextField = new javax.swing.JTextField();
        derivativeTextField = new javax.swing.JTextField();
        aprox = new javax.swing.JTextField();
        tol = new javax.swing.JTextField();
        no = new javax.swing.JTextField();
        graphButton = new javax.swing.JButton();
        derivateButton = new javax.swing.JButton();
        calculateButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultsTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Newton-Raphson");

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        functionTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        functionTextField.setText("type your function");
        functionTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                functionTextFieldFocusGained(evt);
            }
        });
        functionTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                functionTextFieldMouseClicked(evt);
            }
        });
        functionTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                functionTextFieldActionPerformed(evt);
            }
        });

        derivativeTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        derivativeTextField.setFocusable(false);
        derivativeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                derivativeTextFieldActionPerformed(evt);
            }
        });

        aprox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        aprox.setText("type your aprx value");
        aprox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                aproxFocusGained(evt);
            }
        });
        aprox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aproxMouseClicked(evt);
            }
        });
        aprox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aproxActionPerformed(evt);
            }
        });

        tol.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tol.setText("type your tolerance");
        tol.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tolFocusGained(evt);
            }
        });
        tol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tolMouseClicked(evt);
            }
        });
        tol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tolActionPerformed(evt);
            }
        });

        no.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        no.setText("type the iterations limit");
        no.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                noFocusGained(evt);
            }
        });
        no.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noMouseClicked(evt);
            }
        });
        no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noActionPerformed(evt);
            }
        });

        graphButton.setText("Graph");
        graphButton.setFocusable(false);
        graphButton.setRequestFocusEnabled(false);
        graphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graphButtonActionPerformed(evt);
            }
        });

        derivateButton.setText("Derivate");
        derivateButton.setFocusable(false);
        derivateButton.setRequestFocusEnabled(false);
        derivateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                derivateButtonActionPerformed(evt);
            }
        });

        calculateButton.setText("Calculate");
        calculateButton.setFocusable(false);
        calculateButton.setRequestFocusEnabled(false);
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateButtonActionPerformed(evt);
            }
        });

        resultsTextArea.setEditable(false);
        resultsTextArea.setColumns(10);
        resultsTextArea.setRows(5);
        resultsTextArea.setFocusable(false);
        jScrollPane1.setViewportView(resultsTextArea);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(graphButton, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(derivateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calculateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(aprox)
                        .addComponent(tol)
                        .addComponent(no, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(functionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(derivativeTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(aprox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(tol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addComponent(graphButton)
                .addGap(18, 18, 18)
                .addComponent(derivateButton)
                .addGap(18, 18, 18)
                .addComponent(calculateButton)
                .addGap(30, 30, 30))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(functionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(derivativeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void functionTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_functionTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_functionTextFieldActionPerformed

    private void derivativeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_derivativeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_derivativeTextFieldActionPerformed

    private void graphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graphButtonActionPerformed
        if (functionTextField.getText().isEmpty() || functionTextField.getText().equalsIgnoreCase("type your function")) {
            JOptionPane.showMessageDialog(null, "You haven't typed a function, please do so.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String expression = functionTextField.getText();
            XYSeriesCollection dataset = assignValues(expression);
            JFreeChart chart = createGraphic(dataset);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(290, 290));
            chartPanel.setMouseWheelEnabled(true);
            chartPanel.setDisplayToolTips(false);
            chartPanel.setPopupMenu(null);
            graphPanel.removeAll();
            graphPanel.setLayout(new java.awt.BorderLayout());
            graphPanel.add(chartPanel, BorderLayout.CENTER);
            graphPanel.validate();
        }
    }//GEN-LAST:event_graphButtonActionPerformed

    private void aproxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aproxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aproxActionPerformed

    private void functionTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_functionTextFieldMouseClicked
        functionTextField.setText("");
        derivativeTextField.setText("");
    }//GEN-LAST:event_functionTextFieldMouseClicked

    private void aproxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aproxMouseClicked
        aprox.setText("");
    }//GEN-LAST:event_aproxMouseClicked

    private void tolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tolMouseClicked
        tol.setText("");
    }//GEN-LAST:event_tolMouseClicked

    private void tolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tolActionPerformed

    private void noMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noMouseClicked
        no.setText("");
    }//GEN-LAST:event_noMouseClicked

    private void noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noActionPerformed

    private void derivateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_derivateButtonActionPerformed
        if (functionTextField.getText().isEmpty() || functionTextField.getText().equalsIgnoreCase("type your function")) {
            JOptionPane.showMessageDialog(null, "You haven't typed a function, please do so.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else {
            derivativeTextField.setText(derivate(functionTextField.getText()));
        }
    }//GEN-LAST:event_derivateButtonActionPerformed

    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateButtonActionPerformed
        if (functionTextField.getText().isEmpty() || functionTextField.getText().equalsIgnoreCase("type your function") || derivativeTextField.getText().isEmpty() || aprox.getText().isEmpty() || aprox.getText().equalsIgnoreCase("type your aprx value") || tol.getText().isEmpty() || tol.getText().equalsIgnoreCase("type your tolerance") || no.getText().isEmpty() || no.getText().equalsIgnoreCase("type the iteration limit")) {
            JOptionPane.showMessageDialog(null, "Fill Empty Fields", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                resultsTextArea.setText("");
                newtonRaphson(functionTextField.getText(), derivativeTextField.getText(), Double.parseDouble(aprox.getText()), Double.parseDouble(tol.getText()), Integer.parseInt(no.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter valid numerical values for approximation, tolerance, and iteration limit.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_calculateButtonActionPerformed

    private void functionTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_functionTextFieldFocusGained
        functionTextField.setText("type your function");
    }//GEN-LAST:event_functionTextFieldFocusGained

    private void aproxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_aproxFocusGained
        aprox.setText("type your aprx value");
    }//GEN-LAST:event_aproxFocusGained

    private void tolFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tolFocusGained
        tol.setText("type your tolerance");
    }//GEN-LAST:event_tolFocusGained

    private void noFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_noFocusGained
        no.setText("type the iterations limit");
    }//GEN-LAST:event_noFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        FlatLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new nMFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aprox;
    private javax.swing.JButton calculateButton;
    private javax.swing.JButton derivateButton;
    private javax.swing.JTextField derivativeTextField;
    private javax.swing.JTextField functionTextField;
    private javax.swing.JButton graphButton;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField no;
    private javax.swing.JTextArea resultsTextArea;
    private javax.swing.JTextField tol;
    // End of variables declaration//GEN-END:variables
}
