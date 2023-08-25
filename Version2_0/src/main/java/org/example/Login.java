package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import static org.example.Main.*;

public class Login extends JFrame {
    public static UsersButton user;
    public static PasswordResent userResent;
    public static ManagersButton manager;
    public Login() {
        initComponents();
    }
    private void login(ActionEvent e) {
        int id=Integer.parseInt(idField.getText());
        String password = new String(passwordField.getPassword());
        String type = (String) typeBox.getSelectedItem();
        assert type != null;
        if (type.equals("管理员")) {
            if (users.login(id, password, false)){
                login.setVisible(false);
                manager = new ManagersButton(id);
                manager.setSize(600, 500);
                manager.setLocationRelativeTo(null);
                manager.setTitle("购物管理系统");
                manager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                manager.setVisible(true);
            }
        }
        else if (type.equals("用户")) {
            if (users.login(id, password, true)){
                login.setVisible(false);
                user = new UsersButton(id);
                user.setSize(600, 500);
                user.setLocationRelativeTo(null);
                user.setTitle("购物管理系统");
                user.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                user.setVisible(true);
            }
        }
        idField.setText("");
        passwordField.setText("");
    }

    private void register(ActionEvent e) {
        login.setVisible(false);
        register.setVisible(true);
    }
    public void resent(ActionEvent e){
        login.setVisible(false);
        userResent = new PasswordResent(true);
        userResent.setSize(600, 500);
        userResent.setLocationRelativeTo(null);
        userResent.setTitle("购物管理系统");
        userResent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userResent.setVisible(true);
    }

    private void outFunction(ActionEvent e) {
        int order = JOptionPane.showConfirmDialog(null,"是否退出！","提示", JOptionPane.YES_NO_OPTION);
        if(order == JOptionPane.YES_OPTION) {
            users.closeConnection();
            goods.closeConnection();
            cars.writeFile("file\\CarsHistory.txt", shopHistory);
            System.exit(1);
        }
    }

    private void initComponents() {
        JPanel dialogPane = new JPanel();
        JPanel contentPanel = new JPanel();
        JPanel buttonBar2 = new JPanel();
        JLabel type = new JLabel();
        typeBox = new JComboBox<>();
        JLabel id = new JLabel();
        idField = new JTextField();
        JLabel password = new JLabel();
        passwordField = new JPasswordField();
        JLabel label1 = new JLabel();
        JPanel buttonBar3 = new JPanel();
        JPanel buttonBar4 = new JPanel();
        JButton loginButton = new JButton();
        JButton registerButton = new JButton();
        JButton resentButton = new JButton();
        JButton outButton = new JButton();
        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());
            //======== contentPanel ========
            {
                contentPanel.setLayout(new FlowLayout());
                //======== buttonBar2 ========
                {
                    buttonBar2.setBorder(new EmptyBorder(12, 0, 0, 0));
                    buttonBar2.setLayout(new GridBagLayout());
                    ((GridBagLayout)buttonBar2.getLayout()).columnWidths = new int[] {129, 142};
                    //---- type ----
                    type.setText("用户类型：");
                    type.setFont(type.getFont().deriveFont(type.getFont().getSize() + 5f));
                    buttonBar2.add(type, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 5), 0, 0));
                    //---- typeBox ----
                    typeBox.addItem("管理员");
                    typeBox.addItem("用户");
                    typeBox.setFont(typeBox.getFont().deriveFont(typeBox.getFont().getSize() + 5f));
                    buttonBar2.add(typeBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 0), 0, 0));
                    //---- id ----
                    id.setText("用户身份码：");
                    id.setFont(id.getFont().deriveFont(id.getFont().getSize() + 5f));
                    buttonBar2.add(id, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 5), 0, 0));
                    buttonBar2.add(idField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 0), 0, 0));
                    //---- password ----
                    password.setText("密码：");
                    password.setFont(password.getFont().deriveFont(password.getFont().getSize() + 5f));
                    buttonBar2.add(password, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 5), 0, 0));
                    buttonBar2.add(passwordField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                }
                contentPanel.add(buttonBar2);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);
            //---- label1 ----
            label1.setText("购物管理系统");
            label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 10f));
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            dialogPane.add(label1, BorderLayout.NORTH);
            //======== buttonBar3 ========
            {
                buttonBar3.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar3.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar3.getLayout()).columnWidths = new int[] {0, 0, 88, 24, 0};
                //======== buttonBar4 ========
                {
                    buttonBar4.setBorder(new EmptyBorder(12, 0, 0, 0));
                    buttonBar4.setLayout(new GridBagLayout());
                    ((GridBagLayout)buttonBar4.getLayout()).columnWidths = new int[] {0, 88, 0, 0};
                    //---- loginButton ----
                    loginButton.setText("登录");
                    loginButton.setFont(loginButton.getFont().deriveFont(loginButton.getFont().getSize() + 5f));
                    loginButton.addActionListener(this::login);
                    buttonBar4.add(loginButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 5), 0, 0));
                    //---- registerButton ----
                    registerButton.setText("注册");
                    registerButton.setFont(registerButton.getFont().deriveFont(registerButton.getFont().getSize() + 5f));
                    registerButton.addActionListener(this::register);
                    buttonBar4.add(registerButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 5), 0, 0));
                    //---- resentButton ----
                    resentButton.setText("重置密码");
                    resentButton.setFont(resentButton.getFont().deriveFont(resentButton.getFont().getSize() + 5f));
                    resentButton.addActionListener(this::resent);
                    buttonBar4.add(resentButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 5), 0, 0));
                    //---- outButton ----
                    outButton.setText("退出系统");
                    outButton.setFont(outButton.getFont().deriveFont(outButton.getFont().getSize() + 5f));
                    outButton.addActionListener(this::outFunction);
                    buttonBar4.add(outButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                }
                buttonBar3.add(buttonBar4, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
            }
            dialogPane.add(buttonBar3, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
    }
    private JComboBox<String> typeBox;
    private JTextField idField;
    private JPasswordField passwordField;
}

