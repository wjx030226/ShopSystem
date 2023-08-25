package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import static org.example.Login.manager;
import static org.example.Main.*;

public class ManagersButton extends JFrame {
    public static PasswordModify managerModify;
    public static PasswordResent managerResent;
    public ManagersButton(int managerId) {
        initComponents(managerId);
    }
    private void usersManage(ActionEvent e) {
        manager.setVisible(false);
        usersPage.setVisible(true);
    }
    private void goodsManage(ActionEvent e) {
        manager.setVisible(false);
        goodsPage.setVisible(true);
    }
    private void returnFunction(ActionEvent e) {
        manager.setVisible(false);
        login.setVisible(true);
    }
    private void passwordManage(ActionEvent e, int managerId) {
        manager.setVisible(false);
        managerModify = new PasswordModify(managerId, false);
        managerModify.setSize(600, 500);
        managerModify.setLocationRelativeTo(null);
        managerModify.setTitle("购物管理系统");
        managerModify.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerModify.setVisible(true);
    }
    private void passwordResent(ActionEvent e) {
        manager.setVisible(false);
        managerResent = new PasswordResent(false);
        managerResent.setSize(600, 500);
        managerResent.setLocationRelativeTo(null);
        managerResent.setTitle("购物管理系统");
        managerResent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerResent.setVisible(true);
    }
    private void initComponents(int managerId) {
        JPanel dialogPane = new JPanel();
        JLabel choice = new JLabel();
        JPanel buttonBar = new JPanel();
        JButton passwordModify = new JButton();
        JButton passwordResent = new JButton();
        JButton myUsers = new JButton();
        JButton myGoods = new JButton();
        JButton returnButton = new JButton();
        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());
            //---- choice ----
            choice.setText("功能选择");
            choice.setFont(choice.getFont().deriveFont(choice.getFont().getSize() + 10f));
            choice.setHorizontalAlignment(SwingConstants.CENTER);
            dialogPane.add(choice, BorderLayout.NORTH);
            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {85, 0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0};
                //---- passwordModify ----
                passwordModify.setText("修改密码");
                passwordModify.setFont(passwordModify.getFont().deriveFont(passwordModify.getFont().getSize() + 5f));
                passwordModify.addActionListener(e -> passwordManage(e, managerId));
                buttonBar.add(passwordModify, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
                //---- passwordResent ----
                passwordResent.setText("重置用户密码");
                passwordResent.setFont(passwordResent.getFont().deriveFont(passwordResent.getFont().getSize() + 5f));
                passwordResent.addActionListener(this::passwordResent);
                buttonBar.add(passwordResent, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
                //---- myUsers ----
                myUsers.setText("客户管理");
                myUsers.setFont(myUsers.getFont().deriveFont(myUsers.getFont().getSize() + 5f));
                myUsers.addActionListener(this::usersManage);
                buttonBar.add(myUsers, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
                //---- myGoods ----
                myGoods.setText("商品管理");
                myGoods.setFont(myGoods.getFont().deriveFont(myGoods.getFont().getSize() + 5f));
                myGoods.addActionListener(this::goodsManage);
                buttonBar.add(myGoods, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 5, 5), 0, 0));
                //---- returnButton ----
                returnButton.setText("退出登录");
                returnButton.setFont(returnButton.getFont().deriveFont(returnButton.getFont().getSize() + 5f));
                returnButton.addActionListener(this::returnFunction);
                buttonBar.add(returnButton, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
    }
}

