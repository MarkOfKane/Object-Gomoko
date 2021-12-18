import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

//棋盘
public class CheckerBoard {
    private int size;
    private int[][] chess;
    private int side=1;
    private double timeStart;
    private double timeEnd;
    private double timeBlack;
    private double timeWhite;
    private double timeRest;
    private double timeRound;




    CheckerBoard(){
        size=15;
        timeWhite=0;
        timeBlack=0;
        timeRest=600;
        timeRound=15;
        StdDraw.setXscale(0,24);
        StdDraw.setYscale(0,24);
        this.chess=new int[size][size];
        for (int i=0;i<chess.length;i++) {
            Arrays.fill(this.chess[i],0);
        }
        grid(size);
    }
    CheckerBoard(int n){
        size=n;
        timeWhite=0;
        timeBlack=0;
        timeRest=600;
        timeRound=15;
        StdDraw.setXscale(0,24);
        StdDraw.setYscale(0,24);
        this.chess=new int[size][size];
        for (int i=0;i<chess.length;i++) {
            Arrays.fill(this.chess[i],0);
        }
        grid(size);
    }

/****************************************************下一步***********************************************************/

    //下棋(人人）
    public void move() throws FileNotFoundException, InterruptedException {
        int x,y;
        //开始时间
        timeStart=System.currentTimeMillis();
        Thread.sleep(100);

        if (StdDraw.isMousePressed()) {
            x=(int)(StdDraw.mouseX()+0.5);
            y=(int)(StdDraw.mouseY()+0.5);
            int x0=((int)(StdDraw.mouseX()+0.5))-(12-(size-1)/2);
            int y0=((int)(StdDraw.mouseY()+0.5))-(12-(size-1)/2);
            if (x0>=0&&x0<size&&y0>=0&&y0<size&&isEmpty(x0,y0)){
                if (side==1&&!checkBlackForbid(x0,y0)){
                    StdDraw.clear();
                    Font font0=new Font("Times New Roman", Font.BOLD+Font.ITALIC,30);
                    StdDraw.setFont(font0);
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.text(12,12,"You can't play chess here!");
                    StdDraw.pause(1000);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.clear();
                    rePaint();
                    side=1;
                    timeEnd=System.currentTimeMillis()-1000;
                    timer(timeStart,timeEnd);
                }
                else {
                    StdDraw.filledCircle(x,y,0.35);
                    this.chess[x0][y0]=this.side;
                    timeEnd=System.currentTimeMillis();
                    timer(timeStart,timeEnd);
                    change(this.side);
                    this.timeRound=15;
                }
            }
            else if (StdDraw.mouseX()>=15&&StdDraw.mouseX()<=23&&StdDraw.mouseY()>=22&&StdDraw.mouseY()<=24){
                saveFilePerson();
                System.exit(1);
            }
        }

        //计时结束
        else {
            timeEnd=System.currentTimeMillis();
            timer(timeStart,timeEnd);
        }

        //棋盘满了，重开
        if (isFull()&&!isBlackVictory()) {
            StdDraw.setPenColor(StdDraw.RED);
            Font font0=new Font("Times New Roman", Font.BOLD+Font.ITALIC,20);
            StdDraw.setFont(font0);
            StdDraw.text(20,15,"There is no place to put another piece!\nPlease play again");
            for (int i=0;i<chess.length;i++) {
                Arrays.fill(this.chess[i],0);
            }
            side=1;
            timeBlack=0;
            timeWhite=0;
            timeRest=600;
            timeRound=15;
            grid(size);
        }
    }

//下棋(人机/黑）
    public void blackMove() throws FileNotFoundException, InterruptedException {
        int x,y;
        //开始时间
        timeStart=System.currentTimeMillis();
        Thread.sleep(100);

        if (StdDraw.isMousePressed()) {
            x=(int)(StdDraw.mouseX()+0.5);
            y=(int)(StdDraw.mouseY()+0.5);
            int x0=((int)(StdDraw.mouseX()+0.5))-(12-(size-1)/2);
            int y0=((int)(StdDraw.mouseY()+0.5))-(12-(size-1)/2);
            if (x0>=0&&x0<size&&y0>=0&&y0<size&&isEmpty(x0,y0)){
                if (side==1&&!checkBlackForbid(x0,y0)){
                    StdDraw.clear();
                    Font font0=new Font("Times New Roman", Font.BOLD+Font.ITALIC,30);
                    StdDraw.setFont(font0);
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.text(12,12,"You can't play chess here!");
                    StdDraw.pause(1000);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.clear();
                    rePaint();
                    side=1;
                    timeEnd=System.currentTimeMillis()-1000;
                    timer(timeStart,timeEnd);
                }
                else {
                    StdDraw.filledCircle(x,y,0.35);
                    this.chess[x0][y0]=this.side;
                    timeEnd=System.currentTimeMillis();
                    timer(timeStart,timeEnd);
                    RobotWhiteMove();
                    rePaint();
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledRectangle(19,23,4.2,1.2);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    this.timeRound=15;
                }
            }
        }

        //计时结束
        else {
            timeEnd=System.currentTimeMillis();
            timer(timeStart,timeEnd);
        }

        //棋盘满了，重开
        if (isFull()&&!isBlackVictory()) {
            StdDraw.setPenColor(StdDraw.RED);
            Font font0=new Font("Times New Roman", Font.BOLD+Font.ITALIC,20);
            StdDraw.setFont(font0);
            StdDraw.text(20,15,"There is no place to put another piece!\nPlease play again");
            for (int i=0;i<chess.length;i++) {
                Arrays.fill(this.chess[i],0);
            }
            side=1;
            timeBlack=0;
            timeWhite=0;
            timeRest=600;
            timeRound=15;
            grid(size);
        }
    }

//下棋(人机/白）
    public void whiteMove() throws FileNotFoundException, InterruptedException {
        int x,y;
        //开始时间
        timeStart=System.currentTimeMillis();
        Thread.sleep(100);

        if (StdDraw.isMousePressed()) {
            x=(int)(StdDraw.mouseX()+0.5);
            y=(int)(StdDraw.mouseY()+0.5);
            int x0=((int)(StdDraw.mouseX()+0.5))-(12-(size-1)/2);
            int y0=((int)(StdDraw.mouseY()+0.5))-(12-(size-1)/2);
            if (x0>=0&&x0<size&&y0>=0&&y0<size&&isEmpty(x0,y0)){
                StdDraw.filledCircle(x,y,0.35);
                this.chess[x0][y0]=this.side;
                timeEnd=System.currentTimeMillis();
                timer(timeStart,timeEnd);
                RobotBlackMove();
                rePaint();
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledRectangle(19,23,4.2,1.2);
                StdDraw.setPenColor(StdDraw.BLACK);
                this.timeRound=15;
            }
        }

        //计时结束
        else {
            timeEnd=System.currentTimeMillis();
            timer(timeStart,timeEnd);
        }

        //棋盘满了，重开
        if (isFull()&&!isBlackVictory()) {
            StdDraw.setPenColor(StdDraw.RED);
            Font font0=new Font("Times New Roman", Font.BOLD+Font.ITALIC,20);
            StdDraw.setFont(font0);
            StdDraw.text(20,15,"There is no place to put another piece!\nPlease play again");
            for (int i=0;i<chess.length;i++) {
                Arrays.fill(this.chess[i],0);
            }
            side=1;
            timeBlack=0;
            timeWhite=0;
            timeRest=600;
            timeRound=15;
            grid(size);
        }
    }

/*********************************************************计时器*******************************************************/
    //计时
    public void timer(double timeStart,double timeEnd) {
        //剩余总时长
        DecimalFormat format0=new DecimalFormat("0.0");
        timeRest = timeRest - ((timeEnd - timeStart) / 1000);
        timeRest=Double.parseDouble(format0.format(timeRest));
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(5,23,7,0.9);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(5,23,"Rest Time:"+timeRest+"s");

        //剩余回合时长
        timeRound = timeRound - ((timeEnd - timeStart) / 1000);
        timeRound=Double.parseDouble(format0.format(timeRound));
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(12,1,1.5,0.9);
        Font font0=new Font("Times New Roman", Font.BOLD+Font.ITALIC,30);
        StdDraw.setFont(font0);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(12,1,timeRound+"s");
        Font font1=new Font("Arial", Font.PLAIN,20);
        StdDraw.setFont(font1);
        StdDraw.setPenColor(StdDraw.BLACK);

        if (this.side==1) {
            double k=(timeEnd-timeStart)/1000;
            timeBlack+=k;
            DecimalFormat format1=new DecimalFormat("0.00");
            timeBlack=Double.parseDouble(format1.format(timeBlack));
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledRectangle(5,1,4.5,0.9);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(5,1,"Black Time:"+timeBlack+"s");
        }
        else {
            double k=(timeEnd-timeStart)/1000;
            timeWhite+=k;
            DecimalFormat format1=new DecimalFormat("0.00");
            timeWhite=Double.parseDouble(format1.format(timeWhite));
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledRectangle(18,1,4.5,0.9);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(18,1,"White time:"+timeWhite+"s");
            StdDraw.setPenColor(StdDraw.WHITE);
        }
    }
/*****************************************************存档读档********************************************************/
    //存档
    public void saveFilePerson() throws FileNotFoundException {
        PrintWriter printWriter=new PrintWriter("人人对战存档.txt");
        printWriter.flush();
        printWriter.print(this.size+" ");
        for (int i = 0; i < this.chess.length; i++) {
            for (int j = 0; j < this.chess[i].length; j++) {
                printWriter.print(this.chess[i][j]+" ");
            }
        }
        printWriter.print(this.side+" ");
        printWriter.print(this.timeBlack+" ");
        printWriter.print(this.timeWhite+" ");
        printWriter.print(this.timeRest+" ");
        printWriter.print(this.timeRound+" ");
        printWriter.close();
    }

    //读档
    public void readFilePerson() throws FileNotFoundException {
        StdDraw.clear();
        File file = new File("人人对战存档.txt");
        Scanner input = new Scanner(file);
        if (input.hasNext()){
            this.size=input.nextInt();
            this.chess=new int[this.size][this.size];
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    this.chess[i][j]=input.nextInt();
                }
            }
            this.side=input.nextInt();
        }
        this.timeBlack=input.nextDouble();
        this.timeWhite=input.nextDouble();
        this.timeRest=input.nextDouble();
        this.timeRound=input.nextDouble();
        rePaint();
    }

    //重置存档
    public void resetArchive() throws FileNotFoundException {
        size=15;
        this.chess=new int[size][size];
        for (int i=0;i<chess.length;i++) {
            Arrays.fill(this.chess[i],0);
        }
        side=1;
        timeBlack=0;
        timeWhite=0;
        timeRest=600;
        timeRound=15;
        saveFilePerson();
    }
/***********************************************辅助方法*************************************************************/
    //画格子
    public void grid(int n){
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.picture(12,12,"background.jpg",n+1,n+1);
        StdDraw.rectangle(12,12,(n-1)/2+1,(n-1)/2+1);
        StdDraw.rectangle(12,12,(n-1)/2,(n-1)/2);
        for (int i=1;i<n-1;i++) {
            StdDraw.line(i + (12-(n-1)/2), 12-(n-1)/2, i + (12-(n-1)/2), 12+(n-1)/2);
            StdDraw.line(12-(n-1)/2, i + (12-(n-1)/2), 12+(n-1)/2, i + (12-(n-1)/2));
        }

        //保存退出按钮
        Font font1=new Font("Arial", Font.PLAIN,25);
        StdDraw.setFont(font1);
        StdDraw.rectangle(19,23,4,1);
        StdDraw.text(19,23,"Save and Exit");

        //计时显示
        StdDraw.text(5,1,"Black Time:"+timeBlack+"s");
        StdDraw.text(18,1,"White time:"+timeWhite+"s");
        StdDraw.text(5,23,"Rest Time:"+timeRest+"s");

        Font font0=new Font("Times New Roman", Font.BOLD+Font.ITALIC,30);
        StdDraw.setFont(font0);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(12,1,timeRound+"s");
        Font font2=new Font("Arial", Font.PLAIN,20);
        StdDraw.setFont(font2);
        StdDraw.setPenColor(StdDraw.BLACK);
    }

    //重写棋局
    public void rePaint(){
        grid(size);
        for (int i=0;i<this.chess.length;i++) {
            for (int j=0;j<this.chess[i].length;j++) {
                int x=i+(12-(size-1)/2);
                int y=j+(12-(size-1)/2);
                if (this.chess[i][j]==1){
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.filledCircle(x,y,0.35);
                }
                else if (this.chess[i][j]==2){
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledCircle(x,y,0.35);
                }
                else;
                StdDraw.setPenColor(StdDraw.BLACK);
            }
        }
    }

    public int getSide() {
        return this.side;
    }
    public void setSide(int side) {
        this.side = side;
    }

    //换色
    public void change(int side){
        if (this.side==1) {
            this.side=2;
            StdDraw.setPenColor(StdDraw.WHITE);
        }
        else {
            this.side=1;
            StdDraw.setPenColor(StdDraw.BLACK);
        }
    }

    //判断棋格是否已下
    public boolean isEmpty(int x,int y){
        if (this.chess[x][y]==0) return true;
        else return false;
    }

    //棋盘是否没满
    public boolean isFull(){
        boolean Full=true;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.chess[i][j]==0) Full=false;
            }
        }
        return Full;
    }
    public int[][] getChess() {
        return this.chess;
    }
    /***************************************************判断胜利********************************************************/

    //是否结束
    public boolean isEnd(){
        return isTimeRunOut()||isBlackVictory()||isWhiteVictory();
    }

    //时间耗尽
    public boolean isTimeRunOut(){
        if (this.timeRest<=0) return true;
        else return false;
    }

    //判断黑棋胜利
    public boolean isBlackVictory(){
        int a[][]=this.chess;
        boolean res = false ;
        int n = a.length ;
        for( int i = 0 ; i < n ; i++) {
            for( int j = 0 ; j < n - 4 ; j++ ){
                if( a[i][j] == 1 ){                                                                                      // 右向检查是否五子
                    if( a[i][j+1] == 1 ){
                        if( a[i][j+2] == 1 ){
                            if( a[i][j+3] == 1 ){
                                if( a[i][j+4] == 1 ){
                                    res = true ;
                                }
                            }
                        }
                    }
                }
            }
        }
        for( int i = 4 ; i < n ; i++ ){                                                                                  // 向右上方检验是否五子
            for( int j = 0 ; j < n - 4 ; j++ ){
                if( a[i][j] == 1 ){
                    if( a[i-1][j+1] == 1 ){
                        if( a[i-2][j+2] == 1 ){
                            if( a[i-3][j+3] == 1 ){
                                if( a[i-4][j+4] == 1 ){
                                    res = true ;
                                }
                            }
                        }
                    }
                }
            }
        }
        for( int i = 0 ; i < n - 4 ; i++ ){
            for( int j = 0; j < n ; j++ ){
                if (a[i][j] == 1) {
                    if (a[i+1][j] == 1) {                                                                                // 向下检查是否五子
                        if (a[i+2][j] == 1) {
                            if (a[i+3][j] == 1) {
                                if (a[i+4][j] == 1) {
                                    res = true;
                                }
                            }
                        }
                    }
                }
            }
            for( int j = 0 ; j < n - 4 ; j++ ){                                                                          // 向右下检查是否五子
                if(a[i][j] == 1 ){
                    if( a[i+1][j+1] == 1 ){
                        if( a[i+2][j+2] == 1 ){
                            if( a[i+3][j+3] == 1 ){
                                if( a[i+4][j+4] == 1 ){
                                    res = true ;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (side==2&&timeRound<=0) res=true;
        return res ;
    }
    //判断白棋胜利
    public boolean isWhiteVictory(  ){
        int a[][]=this.chess;
        boolean res = false ;
        int n = a.length ;
        for( int i = 0 ; i < n ; i++) {
            for( int j = 0 ; j < n - 4 ; j++ ){
                if( a[i][j] == 2 ){                                                                                      // 右向检查是否五子
                    if( a[i][j+1] == 2 ){
                        if( a[i][j+2] == 2 ){
                            if( a[i][j+3] == 2 ){
                                if( a[i][j+4] == 2 ){
                                    res = true ;
                                }
                            }
                        }
                    }
                }
            }
        }
        for( int i = 4 ; i < n ; i++ ){                                                                                  // 向右上方检验是否五子
            for( int j = 0 ; j < n - 4 ; j++ ){
                if( a[i][j] == 2 ){
                    if( a[i-1][j+1] == 2 ){
                        if( a[i-2][j+2] == 2 ){
                            if( a[i-3][j+3] == 2 ){
                                if( a[i-4][j+4] == 2 ){
                                    res = true ;
                                }
                            }
                        }
                    }
                }
            }
        }
        for( int i = 0 ; i < n - 4 ; i++ ){
            for( int j = 0; j < n ; j++ ){
                if ( a[i][j] == 2 ) {
                    if ( a[i+1][j] == 2 ) {                                                                                // 向下检查是否五子
                        if ( a[i+2][j] == 2 )  {
                            if ( a[i+3][j] == 2 ) {
                                if ( a[i+4][j] == 2 ) {
                                    res = true;
                                }
                            }
                        }
                    }
                }
            }
            for( int j = 0 ; j < n - 4 ; j++ ){                                                                          // 向右下检查是否五子
                if( a[i][j] == 2 ){
                    if( a[i+1][j+1] == 2 ){
                        if( a[i+2][j+2] == 2 ){
                            if( a[i+3][j+3] == 2 ){
                                if( a[i+4][j+4] == 2 ){
                                    res = true ;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (side==1&&timeRound<=0) res=true;
        return res ;
    }
    /******************************************判断禁手**************************************************************/
    public boolean checkBlackForbid(int m , int n){                                                   // m为棋子纵坐标（从上到下），n为棋子横坐标（从左到右）
        int[][] a=this.chess;
        boolean res ;
        int x = a.length ;
        int num = 0 ;
        boolean test1 = checkMovementLegal( m , n , a );
        if( test1 ){                                                                                                     // 总体思路：将棋盘分为多个部分分别进行检验活三数目（有点烦）
            if( m >= 1 && m <= x - 4 ){                                                                                  //竖1
                if( verticalFirst( m , n , a ) ){
                    num += 1 ;
                }
                if( n >= 1 && n <= x - 4 ){                                                                              //左上1
                    if( leftUpFirst( m , n , a ) ){
                        num += 1 ;
                    }
                }
                if( n >= 3 && n <= x - 2 ){                                                                              //右上1
                    if( rightUpFirst( m , n , a ) ){
                        num += 1 ;
                    }
                }
            }
            if( m >= 2 && m <= x - 3 ){                                                                                  //竖2
                if( verticalMid( m , n , a ) ){
                    num += 1 ;
                }
                if( n >= 2 && n <= x - 3 ){
                    if( leftUpMid( m , n , a ) ){                                                                        //左上2
                        num += 1 ;
                    }
                    if( rightUpMid( m , n , a ) ){                                                                       //右上2
                        num += 1 ;
                    }
                }
            }
            if( m >= 3 && m <= x - 2 ){                                                                                  //竖3
                if( verticalLast( m , n , a ) ){
                    num += 1 ;
                }
                if( n >= 1 && n <= x - 4 ){
                    if( rightUpLast( m , n , a ) ){                                                                      //右上3
                        num += 1 ;
                    }
                }
                if( n >= 3 && n <= x - 2 ){
                    if( leftUpLast( m , n , a ) ){                                                                       //左上3
                        num += 1 ;
                    }
                }
            }
            if( n >= 1 && n <= x - 4 ){                                                                                  //横1
                if( horizonFirst( m , n , a ) ){
                    num += 1 ;
                }
            }
            if( n >= 2 && n <= x - 3 ){                                                                                  //横2
                if( horizonMid( m , n , a ) ){
                    num += 1 ;
                }
            }
            if( n >= 3 && n <= x - 2 ){                                                                                  //横3
                if( horizonLast( m , n , a ) ){
                    num += 1 ;
                }
            }
        }
        if( num <= 1 ){
            res = true ;
        }
        else{
            res = false ;
        }
        return res ;
    }

    public boolean checkMovementLegal(int m , int n , int[][] a){
        boolean res = true ;
        if( a[m][n] != 0 ){
            res = false ;
        }
        return res ;
    }

    public boolean leftUpLast( int m , int n , int[][] a ){                                                       // 情况1：左上方向活三，所下棋子为第三枚
        boolean res = false ;
        if( a[m-1][n-1] == 1 ){
            if( a[m-2][n-2] == 1 ){
                if( a[m-3][n-3] == 0 ){
                    if( a[m+1][n+1] == 0 ){
                        res = true ;
                    }
                }
            }
        }
        return res ;
    }

    public boolean leftUpMid( int m , int n , int[][] a ){                                                        // 情况2：左上方向活三，所下棋子为第二枚
        boolean res = false ;
        if( a[m-1][n-1] == 1 ){
            if( a[m+1][n+1] == 1 ){
                if( a[m-2][n-2] == 0 ){
                    if( a[m+2][n+2] == 0 ){
                        res = true ;
                    }
                }
            }
        }
        return res ;
    }

    public boolean leftUpFirst( int m , int n , int[][] a ){                                                      // 情况3：左上方向活三，所下棋子为第一枚
        boolean res = false ;
        if( a[m+1][n+1] == 1 ){
            if( a[m+2][n+2] == 1 ){
                if( a[m-1][n-1] == 0 ){
                    if( a[m+3][n+3] == 0 ){
                        res = true ;
                    }
                }
            }
        }
        return res ;
    }

    public boolean rightUpLast( int m , int n , int[][] a ){                                                      // 情况4：右上方向活三，所下棋子为从上至下第三枚（排法下同）
        boolean res = false ;
        if( a[m-1][n+1] == 1 ){
            if( a[m-2][n+2] == 1 ){
                if( a[m+1][n-1] == 0 ){
                    if( a[m-3][n+3] == 0 ){
                        res = true ;
                    }
                }
            }
        }
        return res ;
    }

    public boolean rightUpMid( int m , int n , int[][] a ){                                                       // 情况5：右上方向活三，所下棋子为第二枚
        boolean res = false ;
        if( a[m-1][n+1] == 1 ){
            if( a[m+1][n-1] == 1 ){
                if( a[m-2][n+2] == 0 ){
                    if( a[m+2][n-2] == 0 ){
                        res = true ;
                    }
                }
            }
        }
        return res ;
    }

    public boolean rightUpFirst( int m , int n , int[][] a ){                                                     // 情况6：右上方向活三，所下棋子为第一枚
        boolean res = false ;
        if( a[m+1][n-1] == 1 ){
            if( a[m+2][n-2] == 1 ){
                if( a[m-1][n+1] == 0 ){
                    if( a[m+3][n-3] == 0 ){
                        res = true ;
                    }
                }
            }
        }
        return res ;
    }

    public boolean horizonFirst( int m , int n , int[][] a ){                                                     // 情况7：横向活三，所下棋子为从左至右第一枚
        boolean res = false ;
        if( a[m][n+1] == 1 ){
            if( a[m][n+2] == 1 ){
                if( a[m][n-1] == 0 ){
                    if( a[m][n+3] == 0 ){
                        res = true ;
                    }
                }
            }
        }
        return res ;
    }

    public boolean horizonMid( int m , int n , int[][] a ){                                                       // 情况8：横向活三，所下棋子为第二枚
        boolean res = false ;
        if( a[m][n-1] == 1){
            if( a[m][n+1] == 1 ){
                if( a[m][n+2] == 0 ){
                    if( a[m][n-2] == 0 ){
                        res = true ;
                    }
                }
            }
        }
        return res ;
    }

    public boolean horizonLast( int m , int n , int[][] a ){                                                      // 情况9：横向活三，所下棋子为第三枚
        boolean res = false ;
        if( a[m][n-1] == 1 ){
            if( a[m][n-2] == 1 ){
                if( a[m][n+1] == 0 ){
                    if( a[m][n-3] == 0 ){
                        res = true ;
                    }
                }
            }
        }
        return res ;
    }

    public boolean verticalFirst( int m , int n , int[][] a ){                                                    // 情况10：竖向活三，所下棋子为第一枚
        boolean res = false ;
        if( a[m+1][n] == 1 ){
            if( a[m+2][n] == 1 ){
                if( a[m+3][n] == 0 ){
                    if( a[m-1][n] == 0 ){
                        res = true ;
                    }
                }
            }
        }
        return res ;
    }

    public boolean verticalMid( int m , int n , int[][] a ){                                                      // 情况11：竖向活三，所下棋子为第二枚
        boolean res = false ;
        if( a[m-1][n] == 1 ){
            if( a[m+1][n] == 1 ){
                if( a[m+2][n] == 0 ){
                    if( a[m-2][n] == 0 ){
                        res = true ;
                    }
                }
            }
        }
        return res ;
    }

    public boolean verticalLast( int m , int n , int[][] a ){                                                     // 情况12：竖向活三，所下棋子为第三枚
        boolean res = false ;
        if( a[m-1][n] == 1 ){
            if( a[m-2][n] == 1 ){
                if( a[m-3][n] == 0 ){
                    if( a[m+1][n] == 0 ){
                        res = true ;
                    }
                }
            }
        }
        return res ;
    }

/********************************************************机器人*******************************************************/

public void RobotBlackMove() {
    int[][] a=this.chess;
    int x = a.length;
    int k=0;
    for (int i = 0; i < x; i++) {
        for (int j = 0; j < x; j++) {
            if (checkMoveLegal(a[i][j])) {                                                                         // 这里的check方法可以用你的isEmpty替换
                if (whiteMayWinState1(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (whiteMayWinState2(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (whiteMayWinState3(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (whiteMayWinState4(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (whiteMayWinState5(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (whiteMayWinState6(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (whiteMayWinState7(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (whiteMayWinState8(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (blackMayWinState1(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (blackMayWinState2(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (blackMayWinState3(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (blackMayWinState4(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (blackMayWinState5(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (blackMayWinState6(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (blackMayWinState7(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (blackMayWinState8(i, j, a)) {
                    this.chess[i][j] = 1;
                    k=1;
                    break;
                }
                if (k==1) break;
            }
            if (k==1) break;
        }
        if (k==1) break;
    }
    if (k!=1) randomMoveBlack( a );
}

    public void RobotWhiteMove() {
        int[][] a=this.chess;
        int x = a.length;
        int k=0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                if (checkMoveLegal(a[i][j])) {                                                                         // 这里的check方法可以用你的isEmpty替换
                    if (blackMayWinState1(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (blackMayWinState2(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (blackMayWinState3(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (blackMayWinState4(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (blackMayWinState5(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (blackMayWinState6(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (blackMayWinState7(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (blackMayWinState8(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (whiteMayWinState1(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (whiteMayWinState2(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (whiteMayWinState3(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (whiteMayWinState4(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (whiteMayWinState5(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (whiteMayWinState6(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (whiteMayWinState7(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (whiteMayWinState8(i, j, a)) {
                        this.chess[i][j] = 2;
                        k=1;
                        break;
                    }
                    if (k==1) break;
                }
                if (k==1) break;
            }
            if (k==1) break;
        }
        if (k!=1) randomMoveWhite( a );
    }

    public  boolean whiteMayWinState1(int m, int n, int[][] a) {                                                // 白棋右向四连
        int x = a.length;
        boolean res = false;
        if (n <= x - 5) {
            if (a[m][n + 1] == 2) {
                if (a[m][n + 2] == 2) {
                    if (a[m][n + 3] == 2) {
                        if (a[m][n + 4] == 2) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public  boolean blackMayWinState1(int m, int n, int[][] a) {                                                // 黑棋右向四连
        int x = a.length;
        boolean res = false;
        if (n <= x - 5) {
            if (a[m][n + 1] == 1) {
                if (a[m][n + 2] == 1) {
                    if (a[m][n + 3] == 1) {
                        if (a[m][n + 4] == 1) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public  boolean whiteMayWinState2(int m, int n, int[][] a) {                                                // 白棋左向四连
        boolean res = false;
        if (n >= 4) {
            if (a[m][n - 1] == 2) {
                if (a[m][n - 2] == 2) {
                    if (a[m][n - 3] == 2) {
                        if (a[m][n - 4] == 2) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public  boolean blackMayWinState2(int m, int n, int[][] a) {                                                // 黑棋左向四连
        boolean res = false;
        if (n >= 4) {
            if (a[m][n - 1] == 1) {
                if (a[m][n - 2] == 1) {
                    if (a[m][n - 3] == 1) {
                        if (a[m][n - 4] == 1) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public  boolean whiteMayWinState3(int m, int n, int[][] a) {                                                // 白棋向下四连
        int x = a.length;
        boolean res = false;
        if (m <= x - 5) {
            if (a[m + 1][n] == 2) {
                if (a[m + 2][n] == 2) {
                    if (a[m + 3][n] == 2) {
                        if (a[m + 4][n] == 2) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public  boolean blackMayWinState3(int m, int n, int[][] a) {                                                // 黑棋向下四连
        int x = a.length;
        boolean res = false;
        if (m <= x - 5) {
            if (a[m + 1][n] == 1) {
                if (a[m + 2][n] == 1) {
                    if (a[m + 3][n] == 1) {
                        if (a[m + 4][n] == 1) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public boolean whiteMayWinState4(int m, int n, int[][] a) {                                                // 白棋向上四连
        boolean res = false;
        if (m >= 4) {
            if (a[m - 1][n] == 2) {
                if (a[m - 2][n] == 2) {
                    if (a[m - 3][n] == 2) {
                        if (a[m - 4][n] == 2) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public boolean blackMayWinState4(int m, int n, int[][] a) {                                                // 黑棋向上四连
        boolean res = false;
        if (m >= 4) {
            if (a[m - 1][n] == 1) {
                if (a[m - 2][n] == 1) {
                    if (a[m - 3][n] == 1) {
                        if (a[m - 4][n] == 1) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public boolean whiteMayWinState5(int m, int n, int[][] a) {                                                // 白棋右下四连
        int x = a.length;
        boolean res = false;
        if (m <= x - 5 && n <= x - 5) {
            if (a[m + 1][n + 1] == 2) {
                if (a[m + 2][n + 2] == 2) {
                    if (a[m + 3][n + 3] == 2) {
                        if (a[m + 4][n + 4] == 2) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public boolean blackMayWinState5(int m, int n, int[][] a) {                                                // 黑棋右下四连
        int x = a.length;
        boolean res = false;
        if (m <= x - 5 && n <= x - 5) {
            if (a[m + 1][n + 1] == 1) {
                if (a[m + 2][n + 2] == 1) {
                    if (a[m + 3][n + 3] == 1) {
                        if (a[m + 4][n + 4] == 1) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public boolean whiteMayWinState6(int m, int n, int[][] a) {                                                // 白棋左上四连
        boolean res = false;
        if (m >= 4 && n >= 4) {
            if (a[m - 1][n - 1] == 2) {
                if (a[m - 2][n - 2] == 2) {
                    if (a[m - 3][n - 3] == 2) {
                        if (a[m - 4][n - 4] == 2) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public boolean blackMayWinState6(int m, int n, int[][] a) {                                                // 黑棋左上四连
        boolean res = false;
        if (m >= 4 && n >= 4) {
            if (a[m - 1][n - 1] == 1) {
                if (a[m - 2][n - 2] == 1) {
                    if (a[m - 3][n - 3] == 1) {
                        if (a[m - 4][n - 4] == 1) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public boolean whiteMayWinState7(int m, int n, int[][] a) {                                                // 白棋左下四连
        int x = a.length;
        boolean res = false;
        if (n >= 4 && m <= x - 5) {
            if (a[m + 1][n - 1] == 2) {
                if (a[m + 2][n - 2] == 2) {
                    if (a[m + 3][n - 3] == 2) {
                        if (a[m + 4][n - 4] == 2) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public boolean blackMayWinState7(int m, int n, int[][] a) {                                                // 黑棋左下四连
        int x = a.length;
        boolean res = false;
        if (n >= 4 && m <= x - 5) {
            if (a[m + 1][n - 1] == 1) {
                if (a[m + 2][n - 2] == 1) {
                    if (a[m + 3][n - 3] == 1) {
                        if (a[m + 4][n - 4] == 1) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public  boolean whiteMayWinState8(int m, int n, int[][] a) {                                                // 白棋右上四连
        int x = a.length;
        boolean res = false;
        if (m >= 4 && n <= x - 5) {
            if (a[m - 1][n + 1] == 2) {
                if (a[m - 2][n + 2] == 2) {
                    if (a[m - 3][n + 3] == 2) {
                        if (a[m - 4][n + 4] == 2) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public boolean blackMayWinState8(int m, int n, int[][] a) {                                                // 黑棋右上四连
        int x = a.length;
        boolean res = false;
        if (m >= 4 && n <= x - 5) {
            if (a[m - 1][n + 1] == 1) {
                if (a[m - 2][n + 2] == 1) {
                    if (a[m - 3][n + 3] == 1) {
                        if (a[m - 4][n + 4] == 1) {
                            res = true;
                        }
                    }
                }
            }
        }
        return res;
    }

    public boolean checkMoveLegal( int a ) {
        boolean res = false;
        if (a == 0) {
            res = true;
        }
        return res;
    }

    public  void randomMoveBlack( int[][] a ){                                                            // 二维数组方法版随机下棋，在对应机器人程序中输入对应颜色的数字
    Random r = new Random();
        int rNum = r.nextInt(10);
        int x = a.length ;
        while(true){
            if( rNum <= 2 ){
                int m = r.nextInt(x) ;
                int n = r.nextInt(x) ;
                if( checkMoveLegal(a[m][n]) ){
                    if( checkBlackForbid( m , n ) ){                                                                 // 这里有禁手方法使用，记得更改
                        this.chess[m][n] = 1 ;
                        break ;
                    }
                }
            }
            if( rNum > 2 ){
                int m = r.nextInt(9) + ( x - 9 ) / 2 ;
                int n = r.nextInt(9) + ( x - 9 ) / 2 ;
                if( checkMoveLegal(a[m][n]) ){
                    if( checkBlackForbid( m , n ) ){                                                                 // 这里有禁手方法使用，记得更改
                        this.chess[m][n] = 1 ;
                        break ;
                    }
                }
            }
        }
    }

    public  void randomMoveWhite( int[][] a ){                                                            // 二维数组方法版随机下棋，在对应机器人程序中输入对应颜色的数字
        Random r = new Random();
        int rNum = r.nextInt(10);
        int x = a.length ;
        while(true){
            if( rNum <= 2 ){
                int m = r.nextInt(x) ;
                int n = r.nextInt(x) ;
                if( checkMoveLegal(a[m][n]) ){
                    this.chess[m][n] = 2 ;
                    break ;
                }
            }
            if( rNum > 2 ){
                int m = r.nextInt(9) + ( x - 9 ) / 2 ;
                int n = r.nextInt(9) + ( x - 9 ) / 2 ;
                if( checkMoveLegal(a[m][n]) ){
                    this.chess[m][n] = 2 ;
                    break ;
                }
            }
        }
    }
}
