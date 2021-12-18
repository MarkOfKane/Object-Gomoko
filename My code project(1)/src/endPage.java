import java.awt.*;

public class endPage {
    endPage(){ }
    endPage(int n){
        StdDraw.setXscale(0,40);
        StdDraw.setYscale(0,25);
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.RED);
        Font font0=new Font("Times New Roman", Font.BOLD+Font.ITALIC,60);
        StdDraw.setFont(font0);
        StdDraw.text(20,15,"Congratulates!!");
        switch (n){
            case 1:
                StdDraw.text(20,10,"BLACK WON");
                break;
            case 2:
                StdDraw.text(20,10,"WHITE WON");
                break;
            case 3:
                StdDraw.text(20,10,"DRAWN GAME");
                break;
            default:
                break;
        }
        StdDraw.pause(3000);

        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        Font font1=new Font("Times New Roman", Font.BOLD+Font.ITALIC,40);
        StdDraw.setFont(font1);
        StdDraw.text(20,17,"Do you want to play again?");
        Font font2=new Font("Arial", Font.PLAIN,25);
        StdDraw.setFont(font2);
        StdDraw.rectangle(20,13,10,1);
        StdDraw.text(20,13,"Play Again");
        StdDraw.rectangle(20,10,10,1);
        StdDraw.text(20,10,"Exit Game");

    }

    //选择
    public static int select() {
        int k=0;
        if (StdDraw.isMousePressed()){
            int x=(int) StdDraw.mouseX();
            int y=(int) StdDraw.mouseY();
            if (x>=10&&x<30&y>=12&&y<14){
                k= 1;
            }
            else if (x>=10&&x<30&&y>=9&&y<11){
                k= 2;
            }
            else k= 0;
        }
        return k;
    }

    //接收结束页选择
    public static boolean receiveSelect(){
        int select=0;
        do {
            select=select();
        }while (select==0);
        boolean k=false;
        switch (select){
            case 1:
                k=true;
                break;
            case 2:
                System.exit(1);
                break;
        }
        return k;
    }
}
