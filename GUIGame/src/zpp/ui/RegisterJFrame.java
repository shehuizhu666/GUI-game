package zpp.ui;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

public class RegisterJFrame extends JFrame implements MouseListener {

    //定义按钮
    JButton register = new JButton();
    JButton reset = new JButton();

    JTextField username=new JTextField();
    JTextField password=new JTextField();
    JTextField conPassword=new JTextField();


    public RegisterJFrame(){
        //初始化界面
        initJFrame();

        //在界面中添加内容
        initView();

        //显示界面
        this.setVisible(true);
    }
    //在界面中添加内容
    private void initView() {

        //1.添加注册用户名
        //注册用户名文字
        JLabel usernameTxt=new JLabel(new ImageIcon("image\\register\\注册用户名.png"));
        usernameTxt.setBounds(95,140,79,17);
        this.getContentPane().add(usernameTxt);

        //添加用户名输入框
        username.setBounds(195, 134, 200, 30);
        this.getContentPane().add(username);


        //2.添加注册密码文字
        JLabel passwordTxt=new JLabel(new ImageIcon("image\\register\\注册密码.png"));
        passwordTxt.setBounds(95, 200, 64, 16);
        this.getContentPane().add(passwordTxt);

        //添加注册密码输入框
        password.setBounds(195, 195, 200, 30);
        this.getContentPane().add(password);

        //3.添加确认密码文字

        JLabel conPasswordTxt=new JLabel(new ImageIcon("image\\register\\再次输入密码.png"));
        conPasswordTxt.setBounds(95, 260, 97, 17);
        this.getContentPane().add(conPasswordTxt);
        //添加注册密码输入框
        conPassword.setBounds(195, 256, 200, 30);
        this.getContentPane().add(conPassword);

        //4.添加注册按钮
        register.setBounds(115, 310, 128, 47);
        register.setIcon(new ImageIcon("image\\register\\注册按钮.png"));
        //去除按钮的默认边框
        register.setBorderPainted(false);
        //去除按钮的默认背景
        register.setContentAreaFilled(false);
        //绑定事件
        register.addMouseListener(this);
        this.getContentPane().add(register);


        //5.添加重置按钮
        reset.setBounds(265, 310, 128, 47);
        reset.setIcon(new ImageIcon("image\\register\\重置按钮.png"));
        //去除按钮的默认边框
        reset.setBorderPainted(false);
        //去除按钮的默认背景
        reset.setContentAreaFilled(false);
        //绑定事件
        reset.addMouseListener(this);
        this.getContentPane().add(reset);

        //6.添加背景图片
        JLabel background=new JLabel(new ImageIcon("image\\register\\background.png"));
        background.setBounds(0,0,470,390);
        this.getContentPane().add(background);
    }

    //初始化界面
    private void initJFrame() {
        //设置长宽
        this.setSize(480,432);
        //设置标题
        this.setTitle("注册");
        //设置置顶
        this.setAlwaysOnTop(true);
        //设置居中
        this.setLocationRelativeTo(null);
        //设置默认关闭
        this.setDefaultCloseOperation(3);

    }

    //单击按钮
    @Override
    public void mouseClicked(MouseEvent e) {
        Object obj=e.getSource();

        //获取输入框中的内容
        String userName=username.getText();
        String userPassword=password.getText();
        String userPassword2=conPassword.getText();

        boolean flag1=userInfo(userName);

        //点击注册按钮
        if(obj==register){
            System.out.println("点击了注册按钮");

            //信息填写不完整
            if(userName.length()==0||userPassword.length()==0||userPassword2.length()==0){
                System.out.println("缺少信息填写");
                showJDialog("缺少信息填写");
            }
            //密码不一致
            else if(!userPassword.equals(userPassword2)){
                System.out.println("两次密码输入不一致");
                showJDialog("两次密码输入不一致");
            }
            //验证用户名是否存在
            else if(flag1) {
                System.out.println("用户名已存在");
                showJDialog("用户名已存在");
            }
            //添加数据
            else{
                try {
                    //获取链接
                    String url = "jdbc:mysql://localhost:3306/user_info";
                    Connection conn = DriverManager.getConnection(url, "root", "123456");

                    //定义sql
                    String sql="insert into user(user_name,user_password) value(?,?)";

                    //获取pstmt对象
                    PreparedStatement pstmt=conn.prepareStatement(sql);

                    //设置?参数
                    pstmt.setString(1, userName);
                    pstmt.setString(2, userPassword);

                    //执行sql
                    int count = pstmt.executeUpdate();//count为影响的行数

                    //处理结果
                    if(count>0){
                        System.out.println("注册成功！");
                        showJDialog("注册成功！");
                    }

                    //释放资源
                    pstmt.close();
                    conn.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                //关闭注册界面打开登录界面
                this.setVisible(false);
                new LoginJFrame();

            }

            //重置按钮
        }else if(obj==reset){
            System.out.println("点击了重置按钮");
            //清空输入框
            username.setText("");
            password.setText("");
            conPassword.setText("");
        }

    }

    //按下不松
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource()==register){
            register.setIcon(new ImageIcon("image\\register\\注册按下.png"));
        }else if(e.getSource()==reset){
            reset.setIcon(new ImageIcon("image\\register\\重置按下.png"));
        }
    }

    //松开按钮
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == register) {
            register.setIcon(new ImageIcon("image\\register\\注册按钮.png"));
        } else if (e.getSource() == reset) {
            reset.setIcon(new ImageIcon("image\\register\\重置按钮.png"));
        }
    }

    public void showJDialog(String content) {
        //创建一个弹框对象
        JDialog jDialog = new JDialog();
        //给弹框设置大小
        jDialog.setSize(200, 150);
        //让弹框置顶
        jDialog.setAlwaysOnTop(true);
        //让弹框居中
        jDialog.setLocationRelativeTo(null);
        //弹框不关闭永远无法操作下面的界面
        jDialog.setModal(true);

        //创建Jlabel对象管理文字并添加到弹框当中
        JLabel warning = new JLabel(content);
        warning.setBounds(0, 0, 200, 150);
        jDialog.getContentPane().add(warning);

        //让弹框展示出来
        jDialog.setVisible(true);

    }

    //验证用户名是否存在
    public boolean userInfo(String userName){
        try {
            //获取链接
            String url = "jdbc:mysql://localhost:3306/user_info";
            Connection conn = DriverManager.getConnection(url, "root", "123456");

            //接受用户输入 用户名和密码
            String name = userName;
            //String pwd = passwordInput;

            String sql = "select*from user Where user_name=?";
            //获取stm对象
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //设置问号的值
            pstmt.setString(1, name);
            //执行sql
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  false;
    }







    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
