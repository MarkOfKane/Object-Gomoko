import java.awt.*;
import java.io.FileNotFoundException;

public class sizeSelection {

    sizeSelection(){
        StdDraw.setXscale(0,30);
        StdDraw.setYscale(0,30);

        StdDraw.picture(15,15,"大背景.jpg",30,30);
        Font font0=new Font("Times New Roman", Font.BOLD+Font.ITALIC,30);
        StdDraw.setFont(font0);
        StdDraw.text(15,26,"Please choose the size of checkerboard");
        StdDraw.text(8,21,"New Game");
        StdDraw.text(23,21,"Last Game");
        Font font1=new Font("Arial", Font.PLAIN,20);
        StdDraw.setFont(font1);
        StdDraw.rectangle(8,16,7,1);
        StdDraw.text(8,16,"15 x 15");
        StdDraw.rectangle(8,12,7,1);
        StdDraw.text(8,12,"17 x 17");
        StdDraw.rectangle(8,8,7,1);
        StdDraw.text(8,8,"19 x 19");
        StdDraw.rectangle(23,12,6,1);
        StdDraw.text(23,12,"Read the file");

    }

    //选择
    public static int select2() {
        int k=0;
        if (StdDraw.isMousePressed()){
            int x=(int) StdDraw.mouseX();
            int y=(int) StdDraw.mouseY();
            if (x>=1&&x<15&&y>=15&&y<17){
                k= 1;
            }
            else if (x>=1&&x<15&&y>=11&&y<13){
                k= 2;
            }
            else if (x>=1&&x<15&&y>=7&&y<9){
                k= 3;
            }
            else if (x>=17&&x<29&&y>=11&&y<13){
                k=4;
            }
            else k= 0;
        }
        return k;
    }

    //接收大小选择页选择
    public static int[] receiveSelect() throws FileNotFoundException {
        int n=0;
        int select2=0;
        do {
            select2=sizeSelection.select2();
        }while (select2==0);
        switch (select2){
            case 1:
                n=15;
                break;
            case 2:
                n=17;
                break;
            case 3:
                n=19;
                break;
            case 4:
                n=0;
                break;
        }
        return new int[]{n,0};
    }
}
