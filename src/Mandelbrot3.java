import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Mandelbrot3 extends JFrame {

    private final int MAX_ITER = 570;
    private final double ZOOM = 200;
    private BufferedImage I;
    private double zx, zy, cX, cY;

    public Mandelbrot3() {
        super("Mandelbrot Set - Z⁵ + C");
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
                    for (int i = 0; i < 5; i++) {
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
                    color = 0x000010;
                } else {
                    double t = (double) iter / MAX_ITER;
                    t = Math.sqrt(t);

                    int r, g, b;
                    if (t < 0.25) {
                        double s = t / 0.25;
                        r = 0;
                        g = (int)(s * 30);
                        b = (int)(s * 130);
                    } else if (t < 0.55) {
                        double s = (t - 0.25) / 0.30;
                        r = (int)(s * 30);
                        g = (int)(30 + s * 100);
                        b = (int)(130 + s * 125); // → 255
                    } else if (t < 0.80) {
                        double s = (t - 0.55) / 0.25;
                        r = (int)(30 + s * 30);
                        g = (int)(130 + s * 125); // → 255
                        b = 255;
                    } else {
                        double s = (t - 0.80) / 0.20;
                        r = (int)(60 + s * 195);  // → 255
                        g = 255;
                        b = 255;
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
        new Mandelbrot3().setVisible(true);
    }
}