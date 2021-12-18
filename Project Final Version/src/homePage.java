import java.awt.*;

public class homePage {
    private static int type;

    homePage() {
        StdDraw.clear();
        StdDraw.setXscale(0,40);
        StdDraw.setYscale(0,25);

        StdDraw.picture(20,12.5,"大背景.jpg",40,25);
        Font font0=new Font("Times New Roman", Font.BOLD+Font.ITALIC,70);
        StdDraw.setFont(font0);
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.text(20,22,"Gomoku");
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.picture(12,12,"homepicture.jpg",23,15);


        Font font1=new Font("Arial", Font.PLAIN,20);
        StdDraw.setFont(font1);
        StdDraw.rectangle(32,16,7,1);
        StdDraw.text(32,16,"Play With People");
        StdDraw.rectangle(32,12,7,1);
        StdDraw.text(32,12,"Play With Computer");
        StdDraw.rectangle(32,8,7,1);
        StdDraw.text(32,8,"Exit Game");

        receiveSelect();

    }

    //选择
    public static int select() {
        int k=0;
        if (StdDraw.isMousePressed()){
            int x=(int) StdDraw.mouseX();
            int y=(int) StdDraw.mouseY();
            if (x>=25&&x<39&&y>=15&&y<17){
                k= 1;
            }
            else if (x>=25&&x<39&&y>=11&&y<13){
                k= 2;
            }
            else if (x>=25&&x<39&&y>=7&&y<9){
                k= 3;
            }
            else k= 0;
        }
        return k;
    }

    //接收主页选择
    public static void receiveSelect(){
        int select=0;
        do {
            select=select();
        }while (select==0);
        int k=select;
        switch (select){
            case 1:
                StdDraw.clear();
                new sizeSelection();
                type=1;
                break;
            case 2:
                StdDraw.clear();
                new BlackOrWhite();
                type=2;
                break;
            case 3:
                StdDraw.clear();
                System.exit(1);
                break;
        }
    }

    public int getType() {
        return type;
    }
}
