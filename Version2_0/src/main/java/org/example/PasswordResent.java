package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import static org.example.Login.manager;
import static org.example.Login.userResent;
import static org.example.Main.*;
import static org.example.ManagersButton.managerResent;

public class PasswordResent extends JFrame {
    public PasswordResent(boolean isUser) {
        initComponents(isUser);
    }
    private void Resent(ActionEvent e, boolean isUser) {
        int id=Integer.parseInt(idField.getText());
        String password=new String(passwordField.getPassword());
        String email= emailField.getText();
        if(users.JudgePassword(password)){
            JOptionPane.showMessageDialog(null, "非法密码输入，请重新输入！","警告", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
        else if (!users.resent(id, password, email)){
            idField.setText("");
            emailField.setText("");
            passwordField.setText("");
        }
        else{
            idField.setText("");
            emailField.setText("");
            passwordField.setText("");
            if (isUser) {
                userResent.setVisible(false);
                login.setVisible(true);
            }
            else {
                managerResent.setVisible(false);
                manager.setVisible(true);
            }
        }
    }
    private void returnFunction(ActionEvent e, boolean isUser) {
        if (isUser) {
            userResent.setVisible(false);
            login.setVisible(true);
        }
        else {
            managerResent.setVisible(false);
            manager.setVisible(true);
        }
    }
    private void initComponents(boolean isUser) {
        JPanel dialogPane = new JPanel();
        JPanel contentPanel = new JPanel();
        JPanel contentPanel2 = new JPanel();
        JPanel buttonBar2 = new JPanel();
        JLabel id = new JLabel();
        idField = new JTextField();
        JLabel email = new JLabel();
        emailField = new JTextField();
        JLabel password = new JLabel();
        passwordField = new JPasswordField();
        JLabel label1 = new JLabel();
        JPanel buttonBar = new JPanel();
        JButton resentButton = new JButton();
        JButton returnButton = new JButton();
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
                //======== contentPanel2 ========
                {
                    contentPanel2.setLayout(new FlowLayout());
                    //======== buttonBar2 ========
                    {
                        buttonBar2.setBorder(new EmptyBorder(12, 0, 0, 0));
                        buttonBar2.setLayout(new GridBagLayout());
                        ((GridBagLayout)buttonBar2.getLayout()).columnWidths = new int[] {129, 142};
                        //---- id ----
                        id.setText("用户身份码：");
                        id.setFont(id.getFont().deriveFont(id.getFont().getSize() + 6f));
                        buttonBar2.add(id, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 5), 0, 0));
                        buttonBar2.add(idField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 0), 0, 0));
                        //---- email ----
                        email.setText("用户邮箱：");
                        email.setFont(email.getFont().deriveFont(email.getFont().getSize() + 5f));
                        buttonBar2.add(email, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 5), 0, 0));
                        buttonBar2.add(emailField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 0), 0, 0));
                        //---- password ----
                        password.setText("新的密码：");
                        password.setFont(password.getFont().deriveFont(password.getFont().getSize() + 5f));
                        buttonBar2.add(password, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 5), 0, 0));
                        buttonBar2.add(passwordField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 0), 0, 0));
                    }
                    contentPanel2.add(buttonBar2);
                }
                contentPanel.add(contentPanel2);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //---- label1 ----
            label1.setText("密码重置");
            label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 10f));
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            dialogPane.add(label1, BorderLayout.NORTH);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setPreferredSize(new Dimension(315, 50));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {90, 90, 90};

                //---- resentButton ----
                resentButton.setText("重置");
                resentButton.setFont(resentButton.getFont().deriveFont(resentButton.getFont().getSize() + 4f));
                resentButton.addActionListener(e -> Resent(e, isUser));
                buttonBar.add(resentButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                //---- returnButton ----
                returnButton.setText("返回");
                returnButton.setFont(returnButton.getFont().deriveFont(returnButton.getFont().getSize() + 4f));
                returnButton.addActionListener(e -> returnFunction(e, isUser));
                buttonBar.add(returnButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        contentPane.setVisible(true);
    }
    private JTextField idField;
    private JTextField emailField;
    private JPasswordField passwordField;
}
