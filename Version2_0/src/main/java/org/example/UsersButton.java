package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import static org.example.Login.user;
import static org.example.Main.login;

public class UsersButton extends JFrame {
    public UsersButton(int id) {
        initComponents(id);
    }
    public static ShopPage shopPage;
    public static ShopHistory history;
    public static PasswordModify userModify;
    private void returnFunction(ActionEvent e) {
        user.setVisible(false);
        login.setVisible(true);
    }
    private void passwordModify(ActionEvent e, int userId) {
        user.setVisible(false);
        userModify = new PasswordModify(userId, true);
        userModify.setSize(600, 500);
        userModify.setLocationRelativeTo(null);
        userModify.setTitle("购物管理系统");
        userModify.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userModify.setVisible(true);
    }
    private void shoppingManage(ActionEvent e, int userId) {
        user.setVisible(false);
        shopPage = new ShopPage(userId);
        shopPage.setSize(600, 500);
        shopPage.setLocationRelativeTo(null);
        shopPage.setTitle("购物管理系统");
        shopPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        shopPage.setVisible(true);
    }
    private void shoppingHistory(ActionEvent e, int userId) {
        user.setVisible(false);
        history = new ShopHistory(userId);
        history.setSize(600, 500);
        history.setLocationRelativeTo(null);
        history.setTitle("购物管理系统");
        history.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        history.setVisible(true);
    }
    private void initComponents(int userId) {
        JPanel dialogPane = new JPanel();
        JPanel contentPanel = new JPanel();
        JLabel choice = new JLabel();
        JPanel buttonBar = new JPanel();
        JButton modify = new JButton();
        JButton shopping = new JButton();
        JButton shopHistory = new JButton();
        JButton out = new JButton();
        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());
            //======== contentPanel ========
            {
                contentPanel.setFont(contentPanel.getFont().deriveFont(contentPanel.getFont().getSize() + 1f));
                contentPanel.setLayout(new FlowLayout());
                //---- choice ----
                choice.setText("功能选择");
                choice.setFont(choice.getFont().deriveFont(choice.getFont().getSize() + 10f));
                contentPanel.add(choice);
            }
            dialogPane.add(contentPanel, BorderLayout.NORTH);
            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {85, 0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0};
                //---- modify ----
                modify.setText("修改密码");
                modify.setFont(modify.getFont().deriveFont(modify.getFont().getSize() + 5f));
                modify.addActionListener(e -> passwordModify(e, userId));
                buttonBar.add(modify, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
                //---- shopping ----
                shopping.setText("购物");
                shopping.setFont(shopping.getFont().deriveFont(shopping.getFont().getSize() + 5f));
                shopping.addActionListener(e -> shoppingManage(e, userId));
                buttonBar.add(shopping, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
                //---- shopHistory ----
                shopHistory.setText("查看购物历史");
                shopHistory.setFont(shopHistory.getFont().deriveFont(shopHistory.getFont().getSize() + 5f));
                shopHistory.addActionListener(e -> shoppingHistory(e, userId));
                buttonBar.add(shopHistory, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
                //---- out ----
                out.setText("退出登录");
                out.setFont(out.getFont().deriveFont(out.getFont().getSize() + 5f));
                out.addActionListener(this::returnFunction);
                buttonBar.add(out, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        contentPane.setVisible(true);
    }
}

