package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import static org.example.Login.manager;
import static org.example.Main.*;

public class UsersPage extends JFrame {
    private Object[] tableHeader;
    public UsersPage() {
        initComponents();
    }
    private void update(ActionEvent e) {
        Object[][] usersTable = new Object[1000][];
        int n = users.update(usersTable);
        TableModel tableModel = new DefaultTableModel(Arrays.copyOfRange(usersTable, 0, n), tableHeader);
        messages.setModel(tableModel);
    }
    private void delete(ActionEvent e) {
        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "请输入想要删除的身份码："));
        users.delete(id);
    }
    private void returnFunction(ActionEvent e) {
        usersPage.setVisible(false);
        manager.setVisible(true);
    }
    private void search(ActionEvent e) {
        int id=Integer.parseInt(searchField.getText());
        users.find(id);
        searchField.setText("");
    }
    private void initComponents() {
        JPanel dialogPane = new JPanel();
        JPanel buttonBar = new JPanel();
        JButton updateButton = new JButton();
        JButton deleteButton = new JButton();
        JButton returnButton = new JButton();
        JScrollPane scrollPane1 = new JScrollPane();
        JPanel panel1 = new JPanel();
        JLabel search = new JLabel();
        JButton searchButton = new JButton();
        searchField = new JTextField();
        JScrollPane scrollPane2 = new JScrollPane();
        messages = new JTable();
        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());
            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setPreferredSize(new Dimension(315, 50));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {90, 90, 90};
                //---- updateButton ----
                updateButton.setText("更新用户");
                updateButton.addActionListener(this::update);
                buttonBar.add(updateButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                //---- deleteButton ----
                deleteButton.setText("删除用户");
                deleteButton.addActionListener(this::delete);
                buttonBar.add(deleteButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                //---- returnButton ----
                returnButton.setText("返回");
                returnButton.addActionListener(this::returnFunction);
                buttonBar.add(returnButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
            //======== scrollPane1 ========
            {
                //======== panel1 ========
                {
                    panel1.setLayout(new BorderLayout());
                    //---- search ----
                    search.setText("请输入查询的用户身份码：");
                    panel1.add(search, BorderLayout.WEST);
                    //---- searchButton ----
                    searchButton.setText("查询");
                    searchButton.addActionListener(this::search);
                    panel1.add(searchButton, BorderLayout.EAST);
                    panel1.add(searchField, BorderLayout.CENTER);
                }
                scrollPane1.setViewportView(panel1);
            }
            dialogPane.add(scrollPane1, BorderLayout.NORTH);
            //======== scrollPane2 ========
            {
                //---- messages ----
                tableHeader = new Object[]{"用户身份码", "名称", "加密密码", "邮箱"};
                Object[][] tableContent = new Object[1000][];
                int n = users.update(tableContent);
                messages = new JTable(Arrays.copyOfRange(tableContent, 0, n), tableHeader);
                scrollPane2.setViewportView(messages);
            }
            dialogPane.add(scrollPane2, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
    }
    private JTextField searchField;
    private JTable messages;
}

