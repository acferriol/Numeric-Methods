
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class Input_Panel extends JPanel {
    private final Dimension input_space = new Dimension(480, 320);
    private final Font standar_font = new Font("Times New Roman", Font.PLAIN, 15);
    private final Font title_font = new Font("Calibri", Font.PLAIN, 12);
    private Double_TF[][] input_matrix;
    private JComboBox<String> input_size;
    private JComboBox<String> input_error;
    private JSpinner input_it;
    private JMenuBar bar;

    public Input_Panel() {
        setBorder(new BevelBorder(2));
        setLayout(null);
        setSize(500, 360);
        bar = new JMenuBar();
        bar.setBounds(110, 364, 365, 50);
        setBorder(new LineBorder(Color.WHITE));

        // ------------------------------------------------------------- Items_Barra
        input_size = new JComboBox<>();
        input_error = new JComboBox<>();
        input_it = new JSpinner(new SpinnerNumberModel(10, 1, 1_000, 1));
        for (int i = 2; i <= 8; i++) {
            input_size.addItem(i + "x" + i);
        }
        for (int i = 1; i <= 15; i++) {
            input_error.addItem(10 + "^-" + i);
        }
        input_size.setBorder(BorderFactory.createTitledBorder(new EmptyBorder(new Insets(1, 1, 1, 1)), "Dimensiones",
                TitledBorder.CENTER, TitledBorder.TOP, title_font));
        input_error.setBorder(BorderFactory.createTitledBorder(new EmptyBorder(new Insets(1, 1, 1, 1)), "Tolerancia",
                TitledBorder.CENTER, TitledBorder.TOP, title_font));
        input_it.setBorder(BorderFactory.createTitledBorder(new EmptyBorder(new Insets(1, 1, 1, 1)), "Iteraciones",
                TitledBorder.CENTER, TitledBorder.TOP, title_font));
        input_size.setFont(standar_font);
        input_error.setFont(standar_font);
        input_it.setFont(standar_font);
        var spinner_text = ((JSpinner.NumberEditor) input_it.getEditor()).getTextField();
        spinner_text.setHorizontalAlignment(JLabel.CENTER);
        spinner_text.setBackground(new Color(238, 238, 238));
        spinner_text.setEditable(false);
        ((JLabel) input_size.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        ((JLabel) input_error.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        input_size.setSelectedIndex(4);
        input_size.addItemListener((_) -> update());
        bar.add(input_size);
        bar.add(input_error);
        bar.add(input_it);
        // ---------------------------------------------------------------
        update();

        add(bar);
    }

    public void update() {
        removeAll();
        int size = get_input_size();
        input_matrix = new Double_TF[size][size];
        int x_corner = (input_space.width - size * 60) / 2 + 50;
        int y_corner = (input_space.height - size * 40) / 2 + 40;
        for (int i = 0; i < size; i++) {
            JLabel l1 = new JLabel(i + "");
            JLabel l2 = new JLabel("" + i);
            l1.setFont(standar_font.deriveFont(20f));
            l2.setFont(standar_font.deriveFont(20f));
            l1.setBounds(x_corner - 20, y_corner + 10 + i * 40, 15, 20);
            l2.setBounds(x_corner + 20 + i * 60, y_corner - 22, 15, 20);
            add(l1);
            add(l2);
            for (int j = 0; j < size; j++) {
                var jtf = new Double_TF();
                jtf.setLocation(j * 60 + x_corner, i * 40 + y_corner);
                input_matrix[i][j] = jtf;
                add(jtf);
            }
        }
        add(bar);
        repaint();
    }

    public double[][] get_matrix() throws IllegalArgumentException {
        double[][] matrix = new double[get_input_size()][get_input_size()];
        for (int i = 0; i < get_input_size(); i++) {
            for (int j = 0; j < get_input_size(); j++) {
                matrix[i][j] = input_matrix[i][j].getValue();
            }
        }
        return matrix;
    }

    public int get_input_size() {
        return input_size.getSelectedIndex() + 2;
    }

    public int get_num_it() {
        return (int) input_it.getValue();
    }

    public double get_error_range() {
        return 1 / Math.pow(10, input_error.getSelectedIndex() + 1);
    }

    public void fill() {
        for (int i = 0; i < get_input_size(); i++) {
            for (int j = 0; j < get_input_size(); j++) {
                try {
                    input_matrix[i][j].getValue();
                } catch (IllegalArgumentException _) {
                    input_matrix[i][j].setText("0.0");
                }
            }
        }
    }
}