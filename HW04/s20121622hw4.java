package hw4;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


class windowDestroyer extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
     System.exit(0);
    }
 
 }

public class s20121622hw4 extends Frame implements ActionListener{
   
   Canvas canvas;
   private int ballNumber = 0;
   private int bSize[] = new int [10000];
   private int bX[] = new int [10000];
   private int bY[] = new int [10000];
   private int count = 0;
   
   public s20121622hw4 () {
      Frame f = new Frame("20121622");
      f.setSize(400, 300);
      
      canvas = new Canvas();
      add("Center", canvas);
      
      Panel p = new Panel();
      Button start = new Button("start");
      Button close = new Button("close");
      
      p.add(start);
      p.add(close);
      
      start.addActionListener(this);
      close.addActionListener(this);
      
      add("South", p);
      
      
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand() == "start") {
         Ball b1 = new Ball(canvas, canvas.getSize().width/2, canvas.getSize().height/2, 3, 2, 20);
         Ball b2 = new Ball(canvas, canvas.getSize().width/2, canvas.getSize().height/2, -3, 6, 20);
         Ball b3 = new Ball(canvas, canvas.getSize().width/2, canvas.getSize().height/2, -2, -1, 20);
         Ball b4 = new Ball(canvas, canvas.getSize().width/2, canvas.getSize().height/2, 4, -2, 20);
         Ball b5 = new Ball(canvas, canvas.getSize().width/2, canvas.getSize().height/2, 1, -1, 20);


         b1.start();
         b2.start();
         b3.start();
         b4.start();
         b5.start();
      }
      else if (e.getActionCommand() == "close") {
         System.exit(0);
      }
      
   }
   
  
   
   public static void main(String[] args) {
	   Frame f = new s20121622hw4();
	   f.setSize(400, 300);
	   windowDestroyer listener = new windowDestroyer();
	   f.addWindowListener(listener);
	   f.setVisible(true);
   }
   

      class Ball extends Thread {     
         private Canvas box;
            public int SIZE;
            public int x;
            public int y;
            public int dx;
            public int dy;
      private int myCount;
      private int time = 0;
            
         public Ball(Canvas c, int x, int y, int dx, int dy, int size) { 
            box = c; 
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
            this.SIZE = size;
            
            bX[count] = x;
            bY[count] = y;
            bSize[count] = SIZE;
            
            this.myCount = count;
            count += 1;
            ballNumber += 1;
         }
      public void draw() {     
         Graphics g = box.getGraphics();
            g.fillOval(this.x, this.y, this.SIZE, this.SIZE);
            g.dispose();
         }
      public void move()
         {     
    	  Graphics g = box.getGraphics();
         //   g.setXORMode(box.getBackground());
            Dimension d = box.getSize();
            
            if (this.SIZE == 0) return ;

            g.setColor(Color.WHITE);
            g.fillOval(this.x, this.y, this.SIZE, this.SIZE);  
            g.setColor(Color.BLACK);
            System.out.println("x : " + x + " y : " + y + " Size : " + SIZE + "\n");

            for (int i = 0; i < ballNumber; ++i) {
            	
               if (i == this.myCount) continue;
               System.out.println("i : " + i + " bx : " + bX[i] + " bY : " + bY[i] + " bSize : " + bSize[i] + "\n");

               if (collision(this.x, this.y, this.SIZE, bX[i], bY[i], bSize[i]) && this.time >= 10) {
                   System.out.println("----------==============--------!!!");

                  System.out.println("x : " + x + " y : " + y + " Size : " + SIZE + " \nbX : " + bX[i] + " bY : " + bY[i] + " bSize : " + bSize[i]);
                  System.out.println("Collision!!!");
                  
                  this.SIZE /= 2;
                  this.time = 0;
                  
                  Ball b = new Ball(canvas, this.x, this.y, dy /dx - dx, dx / dy - dy , this.SIZE);
                  b.start();
               }
            }
            
            this.x += dx;  
            this.y += dy;
            this.time += 1;

            bX[this.myCount] = this.x;
            bY[this.myCount] = this.y;
            bSize[this.myCount] = this.SIZE; 
         
            if (this.x < 0) { 
            	this.x = 0; dx = -dx; 
             }
             if (this.x + SIZE >= d.width) { 
            	 this.x = d.width - SIZE; dx = -dx; 
             }
             if (this.y < 0) { 
            	 this.y = 0; dy = -dy; 
             }
             if (this.y + SIZE >= d.height) {
            	 this.y = d.height - SIZE; dy = -dy; 
             }
             
            
             
             g.fillOval(this.x, this.y, this.SIZE, this.SIZE);   
             g.dispose();
            
            
         }
      
      public boolean collision(int x1, int y1, int size1, int x2, int y2, int size2) {
         double dist;
         
         if (size1 <= 1 || size2 <= 1) return false;
         
         dist = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
         
         if (dist  <= (size1 + size2) / 2) {
        	 System.out.println(dist);
            return true;
         }
         
         return false;
      }
      
      public void run(){   
    	  boolean status = true;
    draw();
          while (status) {
              move();

        	  for (int i = 0; i < ballNumber; ++i) {
        		  if (bSize[i] <= 1) {
        			  status = false;
        			  break;
        		  }
        	  }
                  
          try {
        	  Thread.sleep(20); 
        	  } 
          catch(InterruptedException e) {}
          }
      }      
      
   }

   
}