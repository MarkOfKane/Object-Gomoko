import java.awt.*;

public class BlackOrWhite {
    BlackOrWhite() {
        StdDraw.setXscale(0,30);
        StdDraw.setYscale(0,30);

        StdDraw.picture(15,15,"大背景.jpg",30,30);
        Font font0=new Font("Times New Roman", Font.BOLD+Font.ITALIC,30);
        StdDraw.setFont(font0);
        StdDraw.text(15,26,"Please choose your side and");
        StdDraw.text(15,24,"the size of checkerboard");
        StdDraw.text(8,21,"Black");
        StdDraw.text(23,21,"White");
        Font font1=new Font("Arial", Font.PLAIN,20);
        StdDraw.setFont(font1);
        StdDraw.rectangle(8,16,3,1);
        StdDraw.text(8,16,"15 x 15");
        StdDraw.rectangle(8,12,3,1);
        StdDraw.text(8,12,"17 x 17");
        StdDraw.rectangle(8,8,3,1);
        StdDraw.text(8,8,"19 x 19");
        StdDraw.rectangle(23,16,3,1);
        StdDraw.text(23,16,"15 x 15");
        StdDraw.rectangle(23,12,3,1);
        StdDraw.text(23,12,"17 x 17");
        StdDraw.rectangle(23,8,3,1);
        StdDraw.text(23,8,"19 x 19");
    }

    //选择
    public static int select() {
        int k=0;
        if (StdDraw.isMousePressed()){
            int x=(int) StdDraw.mouseX();
            int y=(int) StdDraw.mouseY();
            if (x>=5&&x<11&&y>=15&&y<17) k= 1;
            else if (x>=5&&x<11&&y>=11&&y<13) k=2;
            else if (x>=5&&x<11&&y>=7&&y<9) k=3;
            else if (x>=20&&x<26&&y>=15&&y<17) k=4;
            else if (x>=20&&x<26&&y>=11&&y<13) k=5;
            else if (x>=20&&x<26&&y>=7&&y<9) k=6;
            else k= 0;
        }
        return k;
    }

    //接收选择
    public static int[] receiveSelect() {
        int n=15;
        int s=1;
        int select=0;
        do {
            select=BlackOrWhite.select();
        }while (select==0);
        switch (select){
            case 1:
                s=1;
                n=15;
                break;
            case 2:
                s=1;
                n=17;
                break;
            case 3:
                s=1;
                n=19;
                break;
            case 4:
                s=2;
                n=15;
                break;
            case 5:
                s=2;
                n=17;
                break;
            case 6:
                s=2;
                n=19;
                break;
        }
        return new int[]{n,s};
    }
}
