import java.io.FileNotFoundException;

public class zxc {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        do {
            homePage a=new homePage();
            int type=a.getType();

            int[] k;
            if (type==1)  k=sizeSelection.receiveSelect();
            else k=BlackOrWhite.receiveSelect();

            int n=k[0];
            int s=k[1];
            CheckerBoard checkerboard=new CheckerBoard(n);


            if (n==0) {
                checkerboard.readFilePerson();
                if (checkerboard.getSide()==2) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                }
                else {
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
            }
            if (s==0){
                while (!checkerboard.isEnd()){
                    checkerboard.move();
                }
            }
            else if (s==1){
                while (!checkerboard.isEnd()){
                    checkerboard.blackMove();
                }
            }
            else {
                checkerboard.setSide(2);
                checkerboard.RobotBlackMove();
                checkerboard.rePaint();
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledRectangle(19,23,4.2,1.2);
                StdDraw.setPenColor(StdDraw.BLACK);
                while (!checkerboard.isEnd()){
                    checkerboard.whiteMove();
                }
            }


            //胜利弹窗
            int q=0;
            if (checkerboard.isBlackVictory()) {
                q=1;
            }
            else if (checkerboard.isWhiteVictory()) {
                q=2;
            }
            else if (checkerboard.isTimeRunOut()) {
                q=3;
            }

            //重置存档
            checkerboard.resetArchive();

            //结束页
            endPage endPage=new endPage(q);
        }while (endPage.receiveSelect());

    }
}


