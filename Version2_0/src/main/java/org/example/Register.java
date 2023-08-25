package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.*;

import static org.example.Main.*;

/**
 * @author dell
 */
public class Register extends JFrame {
    public Register() {
        initComponents();
    }
    private void register(ActionEvent e) {
        int id=Integer.parseInt(idField.getText());
        String name= nameField.getText();
        String password=new String(passwordField.getPassword());
        String comPassword=new String(confirmField.getPassword());
        String email= emailField.getText();
        String type = (String)typeBox.getSelectedItem();
        if (!password.equals(comPassword)){
            JOptionPane.showMessageDialog(null, "两次密码输入不一致，请重新输入！","警告",JOptionPane.ERROR_MESSAGE);
            confirmField.setText("");
        }
        else if (name.length() <= 5){
            JOptionPane.showMessageDialog(null,"非法用户名输入，注册失败！", "警告", JOptionPane.ERROR_MESSAGE);
            nameField.setText("");
        }
        else if(users.JudgePassword(password)) {
            JOptionPane.showMessageDialog(null, "非法密码输入，注册失败！", "警告", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            confirmField.setText("");
        }
        else{
            assert type != null;
            if (type.equals("用户"))
                users.register(id, name, password, email, true);
            else if (type.equals("管理员"))
                users.register(id, name, password, email, false);
            idField.setText("");
            nameField.setText("");
            passwordField.setText("");
            confirmField.setText("");
            emailField.setText("");
            register.setVisible(false);
            login.setVisible(true);
        }
    }
    private void returnFunction(ActionEvent e) {
        register.setVisible(false);
        login.setVisible(true);
    }

    private void initComponents() {
        JPanel dialogPane = new JPanel();
        JPanel contentPanel = new JPanel();
        JPanel buttonBar2 = new JPanel();
        JLabel type = new JLabel();
        typeBox = new JComboBox<>();
        JLabel id = new JLabel();
        idField = new JTextField();
        JLabel name = new JLabel();
        nameField = new JTextField();
        JLabel password = new JLabel();
        passwordField = new JPasswordField();
        JLabel confirm = new JLabel();
        confirmField = new JPasswordField();
        JLabel email = new JLabel();
        emailField = new JTextField();
        JPanel buttonBar = new JPanel();
        JButton registerButton = new JButton();
        JButton returnButton = new JButton();
        JLabel label1 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setPreferredSize(new Dimension(281, 250));
                contentPanel.setLayout(new FlowLayout());

                //======== buttonBar2 ========
                {
                    buttonBar2.setBorder(new EmptyBorder(12, 0, 0, 0));
                    buttonBar2.setLayout(new GridBagLayout());
                    ((GridBagLayout)buttonBar2.getLayout()).columnWidths = new int[] {129, 142};

                    //---- type ----
                    type.setText("用户类型：");
                    type.setFont(type.getFont().deriveFont(type.getFont().getSize() + 4f));
                    buttonBar2.add(type, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 5), 0, 0));

                    //---- typeBox ----
                    typeBox.setModel(new DefaultComboBoxModel<>(new String[] {
                            "管理员",
                            "用户"
                    }));
                    typeBox.setFont(typeBox.getFont().deriveFont(typeBox.getFont().getSize() + 4f));
                    buttonBar2.add(typeBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 0), 0, 0));

                    //---- id ----
                    id.setText("用户身份码：");
                    id.setFont(id.getFont().deriveFont(id.getFont().getSize() + 4f));
                    buttonBar2.add(id, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 5), 0, 0));
                    buttonBar2.add(idField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 0), 0, 0));

                    //---- name ----
                    name.setText("用户名称；");
                    name.setFont(name.getFont().deriveFont(name.getFont().getSize() + 4f));
                    buttonBar2.add(name, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 5), 0, 0));
                    buttonBar2.add(nameField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 0), 0, 0));

                    //---- password ----
                    password.setText("密码：");
                    password.setFont(password.getFont().deriveFont(password.getFont().getSize() + 4f));
                    buttonBar2.add(password, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 5), 0, 0));
                    buttonBar2.add(passwordField, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 0), 0, 0));

                    //---- confirm ----
                    confirm.setText("确认密码：");
                    confirm.setFont(confirm.getFont().deriveFont(confirm.getFont().getSize() + 4f));
                    buttonBar2.add(confirm, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 5), 0, 0));
                    buttonBar2.add(confirmField, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 5, 0), 0, 0));

                    //---- email ----
                    email.setText("邮箱：");
                    email.setFont(email.getFont().deriveFont(email.getFont().getSize() + 4f));
                    buttonBar2.add(email, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 5), 0, 0));
                    buttonBar2.add(emailField, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                }
                contentPanel.add(buttonBar2);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setPreferredSize(new Dimension(315, 50));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {75, 75, 75};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0};

                //---- registerButton ----
                registerButton.setText("注册");
                registerButton.setFont(registerButton.getFont().deriveFont(registerButton.getFont().getSize() + 4f));
                registerButton.addActionListener(this::register);
                buttonBar.add(registerButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                //---- returnButton ----
                returnButton.setText("返回");
                returnButton.setFont(returnButton.getFont().deriveFont(returnButton.getFont().getSize() + 4f));
                returnButton.addActionListener(this::returnFunction);
                buttonBar.add(returnButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);

            //---- label1 ----
            label1.setText("购物管理系统");
            label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 10f));
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            dialogPane.add(label1, BorderLayout.NORTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        contentPane.setVisible(true);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    private JComboBox<String> typeBox;
    private JTextField idField;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JTextField emailField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
