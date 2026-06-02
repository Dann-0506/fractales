import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Mandelbrot2 extends JFrame {

    private final int MAX_ITER = 570;
    private final double ZOOM = 200;
    private BufferedImage I;
    private double zx, zy, cX, cY;

    public Mandelbrot2() {
        super("Mandelbrot Set - Z³ + C");
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
                    double powX = 1, powY = 0;
                    for (int i = 0; i < 3; i++) {
                        double nextX = powX * zx - powY * zy;
                        double nextY = powX * zy + powY * zx;
                        powX = nextX;
                        powY = nextY;
                    }
                    zx = powX + cX;
                    zy = powY + cY;
                    iter--;
                }

                int color;
                if (iter == 0) {
                    color = 0x000000;
                } else {
                    double t = (double) iter / MAX_ITER;
                    t = Math.sqrt(t);

                    int r, g, b;
                    if (t < 0.33) {
                        double s = t / 0.33;
                        r = (int)(s * 200);
                        g = 0;
                        b = 0;
                    } else if (t < 0.66) {
                        double s = (t - 0.33) / 0.33;
                        r = (int)(200 + s * 55);
                        g = (int)(s * 165);
                        b = 0;
                    } else {
                        double s = (t - 0.66) / 0.34;
                        r = 255;
                        g = (int)(165 + s * 90);
                        b = (int)(s * 200);
                    }
                    color = (r << 16) | (g << 8) | b;
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
        new Mandelbrot2().setVisible(true);
    }
}