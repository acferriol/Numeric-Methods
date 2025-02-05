
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Print_Panel extends JPanel {
    private final Font standar_font = new Font("Times New Roman", Font.PLAIN, 15);
    private JLabel title;

    public Print_Panel() {
        setLayout(null);
        setBorder(new LineBorder(Color.WHITE));
        title = new JLabel("ITERACIONES");
        title.setBounds(250, 10, 200, 30);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(standar_font.deriveFont(25f));
        add(title);

    }

    void print_Marix(double[][] A, int it) {
        JLabel num_it = new JLabel(
                String.format("<html>A<sup><span style='font-size: 0.6em;'>%d</span></sup></html>", it + 1));
        int x_corner = (630 - (A.length * 65)) / 2;
        int y_corner = 80 + it * ((A.length + 1) * 40);
        num_it.setBounds(x_corner - 40, y_corner + ((A.length * 40) / 2) - 25, 50, 50);
        num_it.setFont(standar_font.deriveFont(25f));
        add(num_it);
        add(matrix_panel(A, x_corner, y_corner));
        for (int i = 0; i < A.length; i++) {
            var jl = Format_Label(A[i][i], i);
            jl.setLocation(x_corner + (A.length * 65) + 30, y_corner + i * 40);
            add(jl);
        }
        setPreferredSize(new Dimension(0, y_corner + ((A.length + 1) * 40)));
        revalidate();
        repaint();
    }

    private JLabel Double_Format_Label(double num) {
        var jl = new JLabel(String.format("%.5f", num));
        jl.setHorizontalAlignment(JLabel.CENTER);
        jl.setToolTipText("<html><b>Número completo:</b><br>" + num + "</html>");
        jl.setSize(60, 35);
        jl.setBorder(null);
        return jl;
    }

    private JLabel Format_Label(double num, int index) {
        String formattedNumber = String.format("%.5f", num);
        JLabel jl = new JLabel(String.format(
                "<html><span style='font-family:Times New Roman; font-size:15px;'>&#955;</span>" +
                        "<span style='font-family:Times New Roman; font-size:12px; vertical-align:sub;'>%d</span>" +
                        "<span style='font-family:Times New Roman; font-size:15px;'>= </span>" +
                        "<span style='font-family:Times New Roman; font-size:12px;'>%s</span></html>",
                index + 1,
                formattedNumber));
        jl.setHorizontalAlignment(JLabel.CENTER);
        jl.setToolTipText("<html><b>Número completo:</b><br>" + num + "</html>");
        jl.setSize(120, 40);
        jl.setBorder(null);
        jl.setFont(standar_font);
        return jl;
    }

    private JPanel matrix_panel(double[][] A, int x, int y) {
        JPanel jp = new JPanel();
        jp.setLayout(null);
        jp.setBorder(new BracketBorder());
        jp.setBounds(x, y, A.length * 65 + 10, A.length * 40);
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                var dfl = Double_Format_Label(A[i][j]);
                dfl.setLocation(j * 65 + 10, i * 40);
                jp.add(dfl);
            }
        }
        return jp;
    }

    private class BracketBorder implements Border {
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(10, 10, 10, 10);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.BLACK);
            g2d.drawLine(x, y, x, y + height);
            g2d.drawLine(x, y, x + 30, y);
            g2d.drawLine(x, y + height, x + 30, y + height);
            g2d.drawLine(x + width, y, x + width, y + height);
            g2d.drawLine(x + width, y, x + width - 30, y);
            g2d.drawLine(x + width, y + height, x + width - 30, y + height);
        }
    }

    public void clean() {
        removeAll();
        add(title);
        setPreferredSize(new Dimension(1, 1));
    }
}
