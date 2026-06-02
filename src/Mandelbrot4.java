import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Mandelbrot4 extends JFrame {

    private final int MAX_ITER = 570;
    private final double ZOOM = 200;
    private BufferedImage I;
    private double zx, zy, cX, cY;

    public Mandelbrot4() {
        super("Mandelbrot Set - Z⁸ + C");
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
                    for (int i = 0; i < 8; i++) {
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
                    double angle = t * Math.PI * 2.5;

                    int r = (int)(128 + 127 * Math.sin(angle));
                    int g = (int)(128 + 127 * Math.sin(angle + Math.PI * 0.75));
                    int b = (int)(128 + 127 * Math.sin(angle + Math.PI * 1.5));

                    double brightness = Math.sqrt(t);
                    r = (int)(r * brightness);
                    g = (int)(g * brightness);
                    b = (int)(b * brightness);

                    r = Math.max(0, Math.min(255, r));
                    g = Math.max(0, Math.min(255, g));
                    b = Math.max(0, Math.min(255, b));

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
        new Mandelbrot4().setVisible(true);
    }
}