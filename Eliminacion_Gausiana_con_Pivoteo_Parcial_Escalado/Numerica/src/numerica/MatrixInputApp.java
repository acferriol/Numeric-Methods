package numerica;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class MatrixInputApp {
    private JFrame frame;
    private JPanel inputPanel;
    private JTextField sizeField;
    private JButton createButton;
    private JTable matrixTable;
    private JScrollPane scrollPane;
    private JButton submitButton;

    public MatrixInputApp() {
        frame = new JFrame("Eliminacion Gausiana con Pivoteo Parcial Escalado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Panel para entrada del tamaño de la matriz
        JPanel sizePanel = new JPanel(new FlowLayout());
        JLabel sizeLabel = new JLabel("Ingrese el numero de ecuaciones y a continuacion los coeficientes de la matriz ampliada:");
        sizeField = new JTextField(5);
        createButton = new JButton("Crear Matriz");
        sizePanel.add(sizeLabel);
        sizePanel.add(sizeField);
        sizePanel.add(createButton);
        frame.add(sizePanel, BorderLayout.NORTH);

        // Panel para la tabla de la matriz
        inputPanel = new JPanel(new BorderLayout());
        frame.add(inputPanel, BorderLayout.CENTER);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createMatrix();
            }
        });

        frame.setVisible(true);
    }

    private void createMatrix() {
        try {
            int n = Integer.parseInt(sizeField.getText());
            if (n <= 0) {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear tabla con dimensiones n × (n+1)
            String[] columnNames = new String[n + 1];
            for (int i = 0; i < n; i++) {
                columnNames[i] = "x_" + (i + 1);
            }columnNames[n] = "Terms Indep";

            Object[][] data = new Object[n][n + 1];
            matrixTable = new JTable(data, columnNames);
            matrixTable.setCellSelectionEnabled(true);
            matrixTable.getTableHeader().setReorderingAllowed(false);
            
            // Centrar el contenido de las celdas
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < matrixTable.getColumnCount(); i++) {
                matrixTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }

            // Actualizar el panel
            if (scrollPane != null) {
                inputPanel.remove(scrollPane);
            }
            scrollPane = new JScrollPane(matrixTable);
            inputPanel.add(scrollPane, BorderLayout.CENTER);

            // Botón para procesar la matriz
            if (submitButton == null) {
                submitButton = new JButton("Continuar");
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        resolver();
                    }
                });
                inputPanel.add(submitButton, BorderLayout.SOUTH);
            }

            inputPanel.revalidate();
            inputPanel.repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resolver() {
        int m = matrixTable.getColumnCount();
        int n = matrixTable.getRowCount();
        int[] it = new int[n];
        double[] s = new double[n];
        double[][] M = new double[n][m];

        try {
            for (int i = 0; i < n; i++) {
                it[i] = i;
                for (int j = 0; j < m; j++) {
                    Object value = matrixTable.getValueAt(i, j);
                    if (value == null || value.toString().isEmpty()) {
                        throw new IllegalArgumentException("Todos los campos deben ser llenados.");
                    }
                    M[i][j] = Double.parseDouble(value.toString());
                    if (j < n) s[i] = Math.max(s[i], Math.abs(M[i][j]));
                }
            }
            
            Ans v = new Ans(n,it,s,M);
            v.setLocationRelativeTo(null);
            v.setVisible(true);
            frame.dispose();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Por favor, ingrese solo números en la matriz.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MatrixInputApp::new);
    }
}