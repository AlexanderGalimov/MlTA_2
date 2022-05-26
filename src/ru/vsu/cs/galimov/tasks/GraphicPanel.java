package ru.vsu.cs.galimov.tasks;

import javax.swing.*;
import java.awt.*;

public class GraphicPanel extends JPanel {
    private Color graphicColor = Color.BLUE;
    private int width;
    private int height;
    private double a = 10;

    public void paint(Graphics g)
    {
        super.paint(g);
        width = getWidth(); // сохраняем текущую ширину панели
        height = getHeight(); // и высоту

        drawGrid(g); // рисуем сетку
        drawAxis(g); // рисуем оси
        drawGraphic(g,1);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);  //задаем серый цвет

        for(int x=width/2; x<width; x+=30){  // цикл от центра до правого края
            g.drawLine(x, 0, x, height);    // вертикальная линия
        }

        for(int x=width/2; x>0; x-=30){  // цикл от центра до леваого края
            g.drawLine(x, 0, x, height);   // вертикальная линия
        }

        for(int y=height/2; y<height; y+=30){  // цикл от центра до верхнего края
            g.drawLine(0, y, width, y);    // горизонтальная линия
        }

        for(int y=height/2; y>0; y-=30){  // цикл от центра до левого края
            g.drawLine(0, y, width, y);    // горизонтальная линия
        }
    }

    private void drawAxis(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(width/2, 0, width/2, height);
        g.drawLine(0, height/2, width, height/2);
    }

    private void drawGraphic(Graphics g,int index) {
        g.setColor(graphicColor); // устанавливаем цвет графика

        if(index == 1){
            for (int x = 0; x < width; x++) {
            int realX = x - width / 2;
            int x2 = (int) (Math.pow(realX, 3) / 2 * a - realX);
            if(x2 >= 0){
                int y = (int) Math.sqrt(x2) / 30;
                g.drawOval(x,height / 2 - y, 2, 2);   // рисуем кружок в этой точке
                g.drawOval(x, height / 2 + y, 2, 2);   // рисуем кружок в этой точке
            }
        }
        }

        if(index == 2){
            for (int x = 0; x < width; x++) {           // делаем цикл с левой стороны экрана до правой
                int realX = x - width / 2;   // так, как слева от оси OX минус, то отнимаем от текущей точки центральную точку
                if ((a + realX) / (a - realX) >= 0) {
                    int y1 = height / 2 + (int) ( realX * Math.sqrt((a + realX) / (a - realX)));
                    int y2 = height / 2 - (int) ( realX * Math.sqrt((a + realX) / (a - realX)));
                    g.drawOval(x, y1, 2, 2);   // рисуем кружок в этой точке
                    g.drawOval(x, y2, 2, 2);   // рисуем кружок в этой точке
                }
            }
        }
    }

    public void setAAndColor(int a, Color color,int index) {
        try {
            this.graphicColor = color;
            this.a = a;
        }catch (Exception e ){
            this.graphicColor = Color.RED;
        }
        drawGraphic(super.getGraphics(),index);
    }
}
