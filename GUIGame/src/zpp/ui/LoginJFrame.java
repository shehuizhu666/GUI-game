package zpp.ui;

//import zpp.domain.User;
import zpp.uitl.CodeUtil;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.ArrayList;

public class LoginJFrame extends JFrame implements MouseListener  {

    JButton login = new JButton();
    JButton register = new JButton();

    JTextField username=new JTextField();
    JPasswordField password=new JPasswordField();
    JTextField code =new JTextField();

    //显示验证码
    JLabel rightCode = new JLabel();

    public LoginJFrame()  {
        //初始化界面
        initJFrame();

        //在界面中添加内容
        initView();


        //显示界面
        this.setVisible(true);
    }
    //在界面中添加内容
    private void initView() {
        //1.添加用户名
        //用户名文字
        JLabel usernameTxt=new JLabel(new ImageIcon("image\\login\\用户名.png"));
        usernameTxt.setBounds(116,135,47,17);
        this.getContentPane().add(usernameTxt);
        //添加用户名输入框
        username.setBounds(195, 134, 200, 30);
        this.getContentPane().add(username);


        //2.添加密码文字
        JLabel passwordTxt=new JLabel(new ImageIcon("image\\login\\密码.png"));
        passwordTxt.setBounds(130, 195, 32, 16);
        this.getContentPane().add(passwordTxt);
        //添加密码输入框
        password.setBounds(195, 195, 200, 30);
        this.getContentPane().add(password);


        //3.添加验证码文字
        JLabel codeTxt=new JLabel(new ImageIcon("image\\login\\验证码.png"));
        codeTxt.setBounds(133, 256, 50, 30);
        this.getContentPane().add(codeTxt);
        //添加验证码输入框
        code.setBounds(195, 256, 100, 30);
        this.getContentPane().add(code);

        //4.显示验证码
        String codeStr = CodeUtil.getCode();
        //设置内容
        rightCode.setText(codeStr);
        //位置和宽高
        rightCode.setBounds(300, 256, 50, 30);
        //绑定验证码事件
        rightCode.addMouseListener(this);
        //添加到界面
        this.getContentPane().add(rightCode);


        //5.添加登录
        login.setBounds(123, 310, 128, 47);
        login.setIcon(new ImageIcon("image\\login\\登录按钮.png"));
        //去除按钮的默认边框
        login.setBorderPainted(false);
        //去除按钮的默认背景
        login.setContentAreaFilled(false);
        //给登录按钮绑定事件
        login.addMouseListener(this);
        this.getContentPane().add(login);

        //6.添加注册
        register.setBounds(256, 310, 128, 47);
        register.setIcon(new ImageIcon("image\\login\\注册按钮.png"));
        //去除按钮的默认边框
        register.setBorderPainted(false);
        //去除按钮的默认背景
        register.setContentAreaFilled(false);
        //绑定事件
        register.addMouseListener(this);
        this.getContentPane().add(register);


        //7.添加背景图片
        JLabel background=new JLabel(new ImageIcon("image\\login\\background.png"));
        background.setBounds(0,0,470,390);
        this.getContentPane().add(background);
    }

    //初始化界面
    private void initJFrame() {
        //设置长宽
        this.setSize(488,430);
        //设置标题
        this.setTitle("登录");
        //设置置顶
        this.setAlwaysOnTop(true);
        //设置居中
        this.setLocationRelativeTo(null);
        //设置默认关闭
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消组件默认居中放置
        this.setLayout(null);
    }

    //点击
    @Override
    public void mouseClicked(MouseEvent e) {
        Object obj=e.getSource();
        if(obj==login) {
            System.out.println("点击了登录按钮");
            //获取输入框中的内容
            String usernameInput = username.getText();
            String passwordInput = password.getText();
            //获取用户输入的验证码
            String codeInput = code.getText();


            try {

                //获取链接
                String url = "jdbc:mysql://localhost:3306/user_info";
                Connection conn = DriverManager.getConnection(url, "root", "123456");

                //接受用户输入 用户名和密码
                String name = usernameInput;
                String pwd = passwordInput;

                String sql = "select*from user Where user_name=?and user_password=?";
                //获取stm对象
                PreparedStatement pstmt = conn.prepareStatement(sql);
                //设置问号的值
                pstmt.setString(1,name);
                pstmt.setString(2,pwd);

                //执行sql
                ResultSet rs = pstmt.executeQuery();

                boolean information;

                if (rs.next()) {
                    information = true;
                } else {
                    information = false;
                }

                //释放资源
                rs.close();
                pstmt.close();
                conn.close();

                if (codeInput.length() == 0 && usernameInput.length() == 0 && passwordInput.length() == 0) {
                    showJDialog("请填写信息");
                } else if (usernameInput.length() == 0 || passwordInput.length() == 0) {
                    //校验用户名和密码是否为空
                    System.out.println("用户名或者密码为空");

                    //调用showJDialog方法并展示弹框
                    showJDialog("用户名或者密码为空");


                } else if (codeInput.length() == 0) {
                    System.out.println("验证码不能为空");
                    showJDialog("验证码不能为空");
                    //刷新验证码
                    String code = CodeUtil.getCode();
                    rightCode.setText(code);

                } else if (!codeInput.equalsIgnoreCase(rightCode.getText())) {
                    System.out.println("验证码输入错误");
                    showJDialog("验证码输入错误");
                    //刷新验证码
                    String code = CodeUtil.getCode();
                    rightCode.setText(code);

                } else if (information) {

                    System.out.println("用户名和密码正确可以开始玩游戏了");
                    //关闭当前登录界面
                    this.setVisible(false);
                    //打开游戏的主界面
                    //需要把当前登录的用户名传递给游戏界面
                    new GameJFrame();

                } else {
                    System.out.println("用户名或密码错误");
                    showJDialog("用户名或密码错误");
                    //刷新验证码
                    String code = CodeUtil.getCode();
                    rightCode.setText(code);
                }
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            } else if (e.getSource() == register) {
                System.out.println("点击了注册按钮");
                new RegisterJFrame();
                this.setVisible(false);
            } else if (e.getSource() == rightCode) {
                System.out.println("更换验证码");
                //获取一个新的验证码
                String code = CodeUtil.getCode();
                rightCode.setText(code);
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
    //按下不松
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource()==login){
            login.setIcon(new ImageIcon("image\\login\\登录按下.png"));
        }else if(e.getSource()==register){
            register.setIcon(new ImageIcon("image\\login\\注册按下.png"));
        }

    }
    //松开按钮
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == login) {
            login.setIcon(new ImageIcon("image\\login\\登录按钮.png"));
        } else if (e.getSource() == register) {
            register.setIcon(new ImageIcon("image\\login\\注册按钮.png"));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
