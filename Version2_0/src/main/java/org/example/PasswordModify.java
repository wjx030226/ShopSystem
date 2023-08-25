package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import static org.example.Login.manager;
import static org.example.Login.user;
import static org.example.Main.users;
import static org.example.ManagersButton.managerModify;
import static org.example.UsersButton.userModify;

public class PasswordModify extends JFrame {
    public PasswordModify(int Id, boolean isUser) {
        initComponents(Id, isUser);
    }
    private void modify(ActionEvent e, int Id, boolean isUser) {
        int id=Integer.parseInt(idField.getText());
        String password = new String(oldField.getPassword());
        String newPassword = new String(newField.getPassword());
        if (id == Id){
            if(users.JudgePassword(newPassword)){
                JOptionPane.showMessageDialog(null, "非法密码输入，请重新输入！", "警告", JOptionPane.ERROR_MESSAGE);
                newField.setText("");
            }
            else if(users.modify(id, password, newPassword, isUser)){
                idField.setText("");
                oldField.setText("");
                newField.setText("");
                if (isUser){
                    userModify.setVisible(false);
                    user.setVisible(true);
                }
                else{
                    managerModify.setVisible(false);
                    manager.setVisible(true);
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "身份码输入有误，请重新输入！", "警告", JOptionPane.ERROR_MESSAGE);
        }
        idField.setText("");
        oldField.setText("");
        newField.setText("");
    }
    private void returnFunction(ActionEvent e, boolean isUser) {
        if (isUser){
            userModify.setVisible(false);
            user.setVisible(true);
        }
        else{
            managerModify.setVisible(false);
            manager.setVisible(true);
        }
    }
    private void initComponents(int Id, boolean isUser) {
        JPanel dialogPane = new JPanel();
        JLabel label1 = new JLabel();
        JPanel buttonBar = new JPanel();
        JButton modifyButton = new JButton();
        JButton returnButton = new JButton();
        JPanel contentPanel2 = new JPanel();
        JPanel buttonBar2 = new JPanel();
        JLabel id = new JLabel();
        idField = new JTextField();
        JLabel oldPassword = new JLabel();
        oldField = new JPasswordField();
        JLabel newPassword = new JLabel();
        newField = new JPasswordField();
        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());
            //---- label1 ----
            label1.setText("密码修改");
            label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 10f));
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            dialogPane.add(label1, BorderLayout.NORTH);
            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setPreferredSize(new Dimension(315, 50));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {90, 90, 90};
                //---- modifyButton ----
                modifyButton.setText("修改");
                modifyButton.setFont(modifyButton.getFont().deriveFont(modifyButton.getFont().getSize() + 4f));
                modifyButton.addActionListener(e -> modify(e, Id, isUser));
                buttonBar.add(modifyButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
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
                    //---- oldPassword ----
                    oldPassword.setText("原来的密码：");
                    oldPassword.setFont(oldPassword.getFont().deriveFont(oldPassword.getFont().getSize() + 5f));
                    buttonBar2.add(oldPassword, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 5), 0, 0));
                    buttonBar2.add(oldField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 0), 0, 0));
                    //---- newPassword ----
                    newPassword.setText("新的密码：");
                    newPassword.setFont(newPassword.getFont().deriveFont(newPassword.getFont().getSize() + 5f));
                    buttonBar2.add(newPassword, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 5), 0, 0));
                    buttonBar2.add(newField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                }
                contentPanel2.add(buttonBar2);
            }
            dialogPane.add(contentPanel2, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
    }
    private JTextField idField;
    private JPasswordField oldField;
    private JPasswordField newField;
}
