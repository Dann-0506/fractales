import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Mandelbrot1 extends JFrame {

    private final int MAX_ITER = 570;
    private final double ZOOM = 200;
    private BufferedImage I;
    private double zx, zy, cX, cY;

    public Mandelbrot1() {
        super("Mandelbrot Set - Z² + C | Escala de Grises");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                zx = zy = 0;
                cX = (x - 400) / ZOOM;
                cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;

                while (zx * zx + zy * zy < 4 && iter > 0) {
                    // Z^2 + C  (clasico Mandelbrot)
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }

                int color;
                if (iter == 0) {
                    color = 0x000000;
                } else {
                    double t = (double) iter / MAX_ITER;
                    t = Math.sqrt(t);
                    int g = (int)(t * 255);
                    color = (g << 16) | (g << 8) | g;
                }
                I.setRGB(x, y, color);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {
        new Mandelbrot1().setVisible(true);
    }
}