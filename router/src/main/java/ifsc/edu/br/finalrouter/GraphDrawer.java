package ifsc.edu.br.finalrouter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;

public class GraphDrawer {

    public static void drawGraph(List<Router> routers, String outputPath) {
        int width = 800;
        int height = 600;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Set drawing color
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));

        // Draw routers and connections
        int radius = 20;
        int centerX = width / 2;
        int centerY = height / 2;
        int angleStep = 360 / routers.size();
        int angle = 0;

        for (Router router : routers) {
            int x = centerX + (int) (200 * Math.cos(Math.toRadians(angle)));
            int y = centerY + (int) (200 * Math.sin(Math.toRadians(angle)));
            g2d.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
            g2d.drawString(router.getName(), x - radius / 2, y + radius / 2);

            for (Node node : router.getDistanceVector()) {
                Router toRouter = node.getTo();
                int toX = centerX + (int) (200 * Math.cos(Math.toRadians(angle + angleStep)));
                int toY = centerY + (int) (200 * Math.sin(Math.toRadians(angle + angleStep)));
                g2d.drawLine(x, y, toX, toY);
                g2d.drawString(node.getDistance().toString(), (x + toX) / 2, (y + toY) / 2);
            }

            angle += angleStep;
        }

        g2d.dispose();

        try {
            ImageIO.write(image, "png", new File(outputPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Router routerA = new Router("A");
        Router routerB = new Router("B");
        Router routerC = new Router("C");
        Router routerD = new Router("D");

        routerA.addNeighbor(routerB, 9);
        routerA.addNeighbor(routerC, 7);
        routerC.addNeighbor(routerD, 2);
        routerD.addNeighbor(routerB, -3);

        List<Router> routers = List.of(routerA, routerB, routerC, routerD);
        drawGraph(routers, "graph.png");
    }
}
