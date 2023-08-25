package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import static org.example.Login.user;
import static org.example.Main.shopHistory;
import static org.example.Main.cars;
import static org.example.UsersButton.shopPage;

public class ShopPage extends JFrame {
    private Object[] tableHeader;
    public static GoodsMessages goodsMessages;
    public ShopPage(int userId) {
        initComponents(userId);
    }
    private void returnFunction(ActionEvent e) {
        shopPage.setVisible(false);
        user.setVisible(true);
    }
    private void add(ActionEvent e) {
        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "请输入想要添加的商品编码："));
        cars.car_add(id);
    }
    private void delete(ActionEvent e) {
        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "请输入想要删除的商品编码："));
        cars.car_delete(id);
    }
    private void modify(ActionEvent e) {
        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "请输入想要修改的商品编码："));
        int num = Integer.parseInt(JOptionPane.showInputDialog(null, "请输入想要修改的商品数量："));
        cars.car_change(id,num);
    }
    private void update(ActionEvent e) {
        Object[][] carsTable = new Object[1000][];
        int n = cars.car_update(carsTable);
        TableModel tableModel = new DefaultTableModel(Arrays.copyOfRange(carsTable, 0, n), tableHeader);
        messages.setModel(tableModel);
    }
    public void printGoods(ActionEvent e){
        shopPage.setVisible(false);
        goodsMessages = new GoodsMessages();
        goodsMessages.setSize(600, 500);
        goodsMessages.setLocationRelativeTo(null);
        goodsMessages.setTitle("购物管理系统");
        goodsMessages.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        goodsMessages.setVisible(true);
    }
    private void account(ActionEvent e, int userId) {
        cars.car_account(shopHistory, userId);
    }
    private void initComponents(int userId) {
        JPanel dialogPane = new JPanel();
        JPanel contentPanel = new JPanel();
        JPanel contentPanel2 = new JPanel();
        JButton updateButton = new JButton();
        JButton accountButton = new JButton();
        JButton goodsButton = new JButton();
        JPanel buttonBar = new JPanel();
        JButton addButton = new JButton();
        JButton deleteButton = new JButton();
        JButton modifyButton = new JButton();
        JButton returnButton = new JButton();
        JScrollPane scrollPane1 = new JScrollPane();
        messages = new JTable();
        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());
            //======== contentPanel ========
            {
                contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                //======== contentPanel2 ========
                {
                    contentPanel2.setLayout(new FlowLayout());
                }
                contentPanel.add(contentPanel2);
                //---- goodsButton ----
                goodsButton.setText("查看商品信息");
                goodsButton.setFont(goodsButton.getFont().deriveFont(goodsButton.getFont().getSize() + 2f));
                goodsButton.addActionListener(this::printGoods);
                contentPanel.add(goodsButton);
                //---- accountButton ----
                accountButton.setText("模拟结账");
                accountButton.setFont(accountButton.getFont().deriveFont(accountButton.getFont().getSize() + 2f));
                accountButton.addActionListener(e -> account(e, userId));
                contentPanel.add(accountButton);
            }
            dialogPane.add(contentPanel, BorderLayout.NORTH);
            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[] {90, 90, 90, 90, 80};
                //---- updateButton ----
                updateButton.setText("更新购物车");
                updateButton.setFont(updateButton.getFont().deriveFont(accountButton.getFont().getSize() + 2f));
                updateButton.addActionListener(this::update);
                buttonBar.add(updateButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                //---- addButton ----
                addButton.setText("加入购物车");
                addButton.setFont(addButton.getFont().deriveFont(addButton.getFont().getSize() + 2f));
                addButton.addActionListener(this::add);
                buttonBar.add(addButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                //---- deleteButton ----
                deleteButton.setText("移出购物车");
                deleteButton.setFont(deleteButton.getFont().deriveFont(deleteButton.getFont().getSize() + 2f));
                deleteButton.addActionListener(this::delete);
                buttonBar.add(deleteButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                //---- modifyButton ----
                modifyButton.setText("修改商品信息");
                modifyButton.setFont(modifyButton.getFont().deriveFont(modifyButton.getFont().getSize() + 2f));
                modifyButton.addActionListener(this::modify);
                buttonBar.add(modifyButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                //---- resentButton ----
                returnButton.setText("返回");
                returnButton.setFont(returnButton.getFont().deriveFont(returnButton.getFont().getSize() + 2f));
                returnButton.addActionListener(this::returnFunction);
                buttonBar.add(returnButton, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
            //======== scrollPane1 ========
            {
                //---- messages ----
                tableHeader = new Object[]{"商品编号", "名称", "价格", "购买数量"};
                Object[][] tableContent = new Object[1000][];
                int n = cars.car_update(tableContent);
                messages = new JTable(Arrays.copyOfRange(tableContent, 0, n), tableHeader);
                scrollPane1.setViewportView(messages);
            }
            dialogPane.add(scrollPane1, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
    }
    private JTable messages;
}
