import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

public class Principal_Panel extends JPanel {

    private Input_Panel in_panel;
    private Print_Panel print_panel;
    private XYChart convergence;
    private JPanel distribution_panel;

    public Principal_Panel() {
        // --------------------------------------------------------------------Distribucion

        setLayout(new Prop_Layout());
        distribution_panel = new JPanel();
        distribution_panel.setLayout(new GridLayout(2, 1, 5, 5));
        in_panel = new Input_Panel();
        print_panel = new Print_Panel();
        distribution_panel.add(in_panel);
        add(distribution_panel);
        JPanel distribution_panel2 = new JPanel();
        distribution_panel2.setLayout(new BorderLayout());
        JScrollPane jsc = new JScrollPane(print_panel);
        jsc.setViewportView(print_panel);
        jsc.setBorder(new LineBorder(Color.WHITE));
        distribution_panel2.add(jsc, BorderLayout.CENTER);
        JButton start = new JButton("Start!");
        JPanel distribution3 = new JPanel();
        distribution3.add(start);
        distribution_panel2.add(distribution3, BorderLayout.SOUTH);
        add(distribution_panel2);
        // ---------------------------------------------------------------------Grafica
        convergence = new XYChartBuilder()
                .width(600)
                .height(500)
                .title("Convergencia")
                .xAxisTitle("Iteración")
                .yAxisTitle("Distancia entre las normas")
                .build();
        Styler styler = convergence.getStyler();
        styler.setChartBackgroundColor(new Color(246, 246, 246));
        styler.setChartTitleFont(new java.awt.Font("Calibri", java.awt.Font.PLAIN, 18));
        styler.setChartFontColor(java.awt.Color.BLACK);
        styler.setLegendVisible(false);
        convergence.addSeries("||X||2", new double[] { 0 });
        distribution_panel.add(new XChartPanel<>(convergence));

        start.addActionListener(new QR_Operation());

    }

    private void update(List<Double> norms) {
        convergence.removeSeries("||X||2");
        List<Integer> x_axis = new ArrayList<>();
        for (int i = 0; i < norms.size(); i++) {
            x_axis.add(i);
        }
        convergence.addSeries("||X||2", x_axis, norms);
        distribution_panel.remove(1);
        distribution_panel.add(new XChartPanel<>(convergence));
        distribution_panel.revalidate();
        distribution_panel.repaint();
    }

    private class QR_Operation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            print_panel.clean();
            double[][] matrix = new double[in_panel.get_input_size()][in_panel.get_input_size()];
            try {
                matrix = in_panel.get_matrix();
            } catch (IllegalArgumentException _) {
                int decision = JOptionPane.showConfirmDialog(null,
                        "Existen espacios vacíos en la matriz de entrada.\nDesea que sean considerados como '0' ?",
                        "Empty Slots", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (decision == 0) {
                    in_panel.fill();
                    matrix = in_panel.get_matrix();
                } else {
                    return;
                }
            }
            if (QRDescomposition.singular(matrix)) {
                JOptionPane.showMessageDialog(null, "La matriz de entrada es singualar.No es posible realizar el procedimiento", "|A|=0",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                start(matrix);
            }
        }

        private void start(double[][] A) {
            ArrayList<Double> distancias = new ArrayList<>();
            double[] diagonal = new double[A.length];
            for (int i = 0; i < diagonal.length; i++) {
                diagonal[i] = A[i][i];
            }
            distancias.add(QRDescomposition.norma(diagonal));
            double norma_anterior = distancias.get(0);
            int j = 0;
            while (j < in_panel.get_num_it() && distancias.get(j) > in_panel.get_error_range()) {
                A = QRDescomposition.QR_Fact(A);
                print_panel.print_Marix(A, j);
                for (int i = 0; i < diagonal.length; i++) {
                    diagonal[i] = A[i][i];
                }
                double norma_actual = QRDescomposition.norma(diagonal);
                distancias.add(Math.abs((norma_actual - norma_anterior) / norma_actual));
                norma_anterior = norma_actual;
                j++;
            }
            update(distancias);
            if (distancias.get(j) < in_panel.get_error_range()) {
                JOptionPane.showMessageDialog(null,
                        "El Procedimiento ha sido exitoso.\nHa convergido en " + (j) + " iteraciones", "Resultados",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "El Procedimiento ha fallado.\nEl método no ha convergido en " + (j) + " iteraciones",
                        "Resultados",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

}
