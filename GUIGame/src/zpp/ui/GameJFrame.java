package zpp.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener , ActionListener {


    //创建二维数组,用来管理数据
    int[][] data = new int[4][4];

    //记录空白方块在二维数组中的位置
    int x=0;
    int y=0;

    //创建胜利的数组数据
    int [][] win={
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0},
    };

    //定义路径
    String path="image\\sport\\sport9\\";

    //定义变量，统计步数
    int step=0;

    //创建选项条目
    JMenuItem replayItem=new JMenuItem("重新开始");
    JMenuItem reLoginItem=new JMenuItem("重新登录");
    JMenuItem closeItem=new JMenuItem("关闭");

    JMenuItem accountItem=new JMenuItem("微信号");

    JMenuItem girlItem=new JMenuItem("美女");
    JMenuItem animalItem=new JMenuItem("动物");
    JMenuItem sportItem=new JMenuItem("运动");

    public GameJFrame(){
        //初始化界面
        initJFrame();

        //初始化菜单
        initJMenuBar();

        //初始化数据（打乱图片）
        initData();

        //初始化图片
        initImage();



        //显示界面,建议写在最后
        this.setVisible(true);
    }

    //初始化数据（打乱图片）
    private void initData() {
        //把一个一维数组0~15打乱顺序
        //按照4个一组添加到二维数组中

        //定义一个一维数组
        int[] temArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        //打乱数组中数据的顺序
        //遍历数组，得到每一个元素，拿着每一个元素跟随机索引上的数据进行交换
        Random r = new Random();
        for (int i = 0; i < temArr.length; i++) {
            //获取随机索引
            int index = r.nextInt(temArr.length);
            //拿着遍历到的每一个数据。跟随机索引上的数据交换
            int temp = temArr[i];
            temArr[i] = temArr[index];
            temArr[index] = temp;
        }

        //给二维数组添加数据
        for (int i = 0; i < temArr.length; i++) {
            if(temArr[i]==0){
                x=i/4;
                y=i%4;
            }
                data[i / 4][i % 4] = temArr[i];
        }

    }

    //初始化图片
    private void initImage() {
        //清空原本已经出现的图片
        this.getContentPane().removeAll();

        //加载胜利图片
        if(victory()){
        JLabel winJLabel=new JLabel(new ImageIcon("image\\win.png"));
        winJLabel.setBounds(203,283,197,73);
        this.getContentPane().add(winJLabel);
        }

        //细节：先加载的图片在上方，后添加的图片在下方
        //外循环----循环重复4次
        for (int i = 0; i < 4; i++) {
            //内循环----循环重复4次
            for (int j = 0; j < 4; j++) {
                int num=data[i][j];
                //创建图片
                ImageIcon icon=new ImageIcon(path+num+".jpg");
                //创建管理容器
                JLabel jLabel=new JLabel(icon);
                //指定图片位置
                jLabel.setBounds(105*j+83,105*i+134,105,105);
                //添加边框
                //0:让图片突起来
                //1：让图片凹下去
                jLabel.setBorder(new BevelBorder(1));
                //把管理器容器添加到界面中
                this.getContentPane().add(jLabel);

            }
        }
        //添加统计步数组件
        JLabel stepCount=new JLabel("步数"+step);
        stepCount.setBounds(50,30,100,20);
        this.getContentPane().add(stepCount);

        //添加背景图片
        JLabel background=new JLabel(new ImageIcon("image\\background.png"));
        //设置图片位置
        background.setBounds(40,40,518,580);
        //把管理器容器添加到界面中
        this.getContentPane().add(background);

        //刷新界面
        this.getContentPane().repaint();
    }

    //初始化菜单
    private void initJMenuBar() {
        //创建菜单对象
        JMenuBar jMenubar=new JMenuBar();

        //创建菜单选项
        JMenu functionMenu=new JMenu("功能");
        JMenu aboutMenu=new JMenu("关于我们");
        JMenu exchangeMenu=new JMenu("更换图片");

        //将更换图片添加到选项中
        functionMenu.add(exchangeMenu);
        //将条目添加到选项中
        functionMenu.add(replayItem);
        functionMenu.add(reLoginItem);
        functionMenu.add(closeItem);
        aboutMenu.add(accountItem);
        exchangeMenu.add(girlItem);
        exchangeMenu.add(animalItem);
        exchangeMenu.add(sportItem);

        //给条目绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        girlItem.addActionListener(this);
        animalItem.addActionListener(this);
        sportItem.addActionListener(this);

        //将选项添加到菜单中
        jMenubar.add(functionMenu);
        jMenubar.add(aboutMenu);

        //显示菜单
        this.setJMenuBar(jMenubar);
    }

    //初始化界面
    private void initJFrame() {
        //设置长宽
        this.setSize(603,680);
        //设置标题
        this.setTitle("小游戏");
        //设置置顶
        this.setAlwaysOnTop(true);
        //设置居中
        this.setLocationRelativeTo(null);
        //设置默认关闭
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认居中放置，按照XY轴的形式放组件
        this.setLayout(null);

        //添加键盘监听
        this.addKeyListener(this);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //按下不松时才会调用（按A键显示原图）
    @Override
    public void keyPressed(KeyEvent e) {
        int code =e.getKeyCode();
        if(code==65){
            //把界面中的图片全部删掉
            this.getContentPane().removeAll();
            //加载完整图片
            JLabel all=new JLabel(new ImageIcon(path+"all.jpg"));
            all.setBounds(83,134,420,420);
            this.getContentPane().add(all);
            //加载背景图片
            JLabel background=new JLabel(new ImageIcon("image\\background.png"));
            //设置图片位置
            background.setBounds(40,40,508,560);
            //把管理器容器添加到界面中
            this.getContentPane().add(background);

            //刷新界面
            this.getContentPane().repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //判断胜利则不能移动图片
        if(victory()){
            return;
        }

        //对上下左右进行判断
        //向左：37  向上：38  向右：39  向下：40
        int code=e.getKeyCode();
        if(code==37){
            System.out.println("向左移动");
            if(y==0){
                return;
            }
            //逻辑：将空白方块向左移
            //空白方块位置data[x][y],左边方块位置[x][y-1]
            data[x][y]=data[x][y-1];
            data[x][y-1]=0;
            y--;
            //计步器增加一次
            step++;
            //重新加载图片
            initImage();
        }else if (code==38){
            System.out.println("向上移动");
            if(x==0){
                return;
            }
            //逻辑：将空白方块向上移
            //空白方块位置data[x][y],上方方块位置[x-1][y]
            data[x][y]=data[x-1][y];
            data[x-1][y]=0;
            x--;
            step++;
            //重新加载图片
            initImage();

        }else if (code==39) {
            System.out.println("向右移动");
            if(y==3){
                return;
            }
            //逻辑：将空白方块向右移动
            //空白方块位置data[x][y],右边方块位置[x][y+1]
            data[x][y]=data[x][y+1];
            data[x][y+1]=0;
            y++;
            step++;
            //重新加载图片
            initImage();
        }else if (code==40) {
            System.out.println("向下移动");
            if(x==3){
                return;
            }
            //逻辑：将空白方块向下移动
            //空白方块位置data[x][y],下方方块位置[x+1][y]
            data[x][y]=data[x+1][y];
            data[x+1][y]=0;
            x++;
            step++;
            //重新加载图片
            initImage();
            //显示原来图片
        }else if(code==65){
            initImage();
        }else if(code==87){
            data=new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0},
            };
            initImage();
        }
    }



    public boolean victory(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j]!=win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Random r=new Random();
        Object obj=e.getSource();
        if(obj==replayItem){
            System.out.println("重新开始");
            //计步器清零
            step=0;
            //打乱数据
            initData();
            //重新加载图片
            initImage();

        }else if(obj==reLoginItem){
            System.out.println("重新登录");
            this.setVisible(false);
            new LoginJFrame();
        }else if(obj==closeItem){
            System.out.println("关闭游戏");
            //关闭游戏界面
            this.setVisible(false);
            //打开登录界面
            System.exit(0);
        }
        else if(obj==girlItem){
            int num=r.nextInt(13)+1;
            System.out.println("美女");
            path="image\\girl\\girl"+num+"\\";
            step=0;
            initData();
            initImage();

        }else if(obj==animalItem){
            System.out.println("动物");
            int num=r.nextInt(8)+1;
            System.out.println("美女");
            path="image\\animal\\animal"+num+"\\";
            step=0;
            initData();
            initImage();

        }else if(obj==sportItem){
            System.out.println("运动");
            int num=r.nextInt(10)+1;
            System.out.println("美女");
            path="image\\sport\\sport"+num+"\\";
            step=0;
            initData();
            initImage();

        }

        else if(obj==accountItem){
            System.out.println("微信号");
            //创建 一个弹窗对象
            JDialog jDialog=new JDialog();
            //创建一个管理容器
            JLabel jLabel=new JLabel(new ImageIcon("image\\zpp.jpg"));
            //设置位置和宽高
            jLabel.setBounds(0,0,258,258);
            //把图片添加到弹窗中
            jDialog.getContentPane().add(jLabel);
            //设置弹窗的大小
            jDialog.setSize(344,344);
            //设置弹窗置顶
            jDialog.setAlwaysOnTop(true);
            //设置弹窗居中
            jDialog.setLocationRelativeTo(null);
            //弹窗不关闭则无法操作下面的界面
            jDialog.setModal(true);

            //让弹窗显示出来
            jDialog.setVisible(true);



        }
    }
}
