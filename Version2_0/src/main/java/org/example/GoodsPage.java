package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import static org.example.Login.manager;
import static org.example.Main.*;

public class GoodsPage extends JFrame {
    private Object[] tableHeader;
    public static GoodsChange goodsChange;
    public GoodsPage() {
        initComponents();
    }
    private void search(ActionEvent e) {
        int id=Integer.parseInt(searchField.getText());
        goods.find(id);
        searchField.setText("");
    }
    private void update(ActionEvent e) {
        Object[][] goodsTable = new Object[1000][];
        int n = goods.update(goodsTable);
        TableModel tableModel = new DefaultTableModel(Arrays.copyOfRange(goodsTable, 0, n), tableHeader);
        messages.setModel(tableModel);
    }
    private void returnFunction(ActionEvent e) {
        goodsPage.setVisible(false);
        manager.setVisible(true);
    }
    private void add(ActionEvent e) {
        goodsPage.setVisible(false);
        goodsChange = new GoodsChange(true);
        goodsChange.setSize(600, 500);
        goodsChange.setLocationRelativeTo(null);
        goodsChange.setTitle("购物管理系统");
        goodsChange.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        goodsChange.setVisible(true);
    }

    private void delete(ActionEvent e) {
        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "请输入想要删除的商品编号："));
        goods.delete(id);
    }

    private void modify(ActionEvent e) {
        goodsPage.setVisible(false);
        goodsChange = new GoodsChange(false);
        goodsChange.setSize(600, 500);
        goodsChange.setLocationRelativeTo(null);
        goodsChange.setTitle("购物管理系统");
        goodsChange.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        goodsChange.setVisible(true);
    }

    private void initComponents() {
        JPanel dialogPane = new JPanel();
        JPanel contentPanel = new JPanel();
        JLabel searchID = new JLabel();
        searchField = new JTextField();
        JButton searchButton = new JButton();
        JPanel buttonBar = new JPanel();
        JButton updateButton = new JButton();
        JButton addButton = new JButton();
        JButton deleteButton = new JButton();
        JButton modifyButton = new JButton();
        JButton returnButton = new JButton();
        JScrollPane scrollPane1 = new JScrollPane();
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
                //---- searchID ----
                searchID.setText("请输入查询的商品编号");
                searchID.setHorizontalAlignment(SwingConstants.LEFT);
                searchID.setFont(searchID.getFont().deriveFont(searchID.getFont().getSize() + 3f));
                contentPanel.add(searchID);
                //---- searchField ----
                searchField.setPreferredSize(new Dimension(230, 30));
                contentPanel.add(searchField);
                //---- searchButton ----
                searchButton.setText("查询");
                searchButton.addActionListener(this::search);
                contentPanel.add(searchButton);
            }
            dialogPane.add(contentPanel, BorderLayout.NORTH);
            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {90, 90, 90, 90, 90};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0};
                //---- updateButton ----
                updateButton.setText("更新商品");
                updateButton.addActionListener(this::update);
                buttonBar.add(updateButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                //---- addButton ----
                addButton.setText("添加商品");
                addButton.addActionListener(this::add);
                buttonBar.add(addButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                //---- deleteButton ----
                deleteButton.setText("删除商品");
                deleteButton.addActionListener(this::delete);
                buttonBar.add(deleteButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                //---- modifyButton ----
                modifyButton.setText("修改商品");
                modifyButton.addActionListener(this::modify);
                buttonBar.add(modifyButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                //---- returnButton ----
                returnButton.setText("返回");
                returnButton.addActionListener(this::returnFunction);
                buttonBar.add(returnButton, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
            //======== scrollPane1 ========
            {
                //---- messages ----
                tableHeader = new Object[]{"商品编号", "名称", "价格", "数量"};
                Object[][] tableContent = new Object[1000][];
                int n = goods.update(tableContent);
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
    private JTextField searchField;
}
