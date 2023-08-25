package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import static org.example.GoodsPage.goodsChange;
import static org.example.Main.*;

/**
 * @author dell
 */
public class GoodsChange extends JFrame {
    public GoodsChange(boolean type) {
        initComponents(type);
    }
    private void returnFunction(ActionEvent e) {
        goodsChange.setVisible(false);
        goodsPage.setVisible(true);
    }
    private void submit(ActionEvent e, boolean type) {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        int num = Integer.parseInt(numField.getText());
        double price = Double.parseDouble(priceField.getText());
        if (type)
            goods.add(id, name, num, price);
        else
            goods.change(id, name, num, price);
        idField.setText("");
        nameField.setText("");
        numField.setText("");
        priceField.setText("");
        goodsChange.setVisible(false);
        goodsPage.setVisible(true);
    }
    private void initComponents(boolean type) {
        JPanel dialogPane = new JPanel();
        JPanel contentPanel = new JPanel();
        JPanel contentPanel2 = new JPanel();
        JPanel buttonBar2 = new JPanel();
        JLabel id = new JLabel();
        idField = new JTextField();
        JLabel name = new JLabel();
        nameField = new JTextField();
        JLabel num = new JLabel();
        numField = new JTextField();
        JLabel price = new JLabel();
        priceField = new JTextField();
        JLabel label1 = new JLabel();
        JPanel buttonBar = new JPanel();
        JButton submitButton = new JButton();
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
                        id.setText("商品编号：");
                        id.setFont(id.getFont().deriveFont(id.getFont().getSize() + 6f));
                        buttonBar2.add(id, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 5), 0, 0));
                        buttonBar2.add(idField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 0), 0, 0));
                        //---- name ----
                        name.setText("商品名称：");
                        name.setFont(name.getFont().deriveFont(name.getFont().getSize() + 6f));
                        buttonBar2.add(name, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 5), 0, 0));
                        buttonBar2.add(nameField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 0), 0, 0));
                        //---- price ----
                        price.setText("商品价格：");
                        price.setFont(price.getFont().deriveFont(price.getFont().getSize() + 6f));
                        buttonBar2.add(price, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 5), 0, 0));
                        buttonBar2.add(priceField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 0), 0, 0));
                        //---- num ----
                        num.setText("商品数量：");
                        num.setFont(num.getFont().deriveFont(num.getFont().getSize() + 6f));
                        buttonBar2.add(num, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 5), 0, 0));
                        buttonBar2.add(numField, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 5, 0), 0, 0));
                    }
                    contentPanel2.add(buttonBar2);
                }
                contentPanel.add(contentPanel2);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);
            //---- label1 ----
            if (type)
                label1.setText("商品添加");
            else label1.setText("商品修改");
            label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 10f));
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            dialogPane.add(label1, BorderLayout.NORTH);
            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setPreferredSize(new Dimension(315, 50));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {75, 75, 75};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0};
                //---- submitButton ----
                submitButton.setText("提交");
                submitButton.setFont(submitButton.getFont().deriveFont(submitButton.getFont().getSize() + 5f));
                submitButton.addActionListener(e -> submit(e, type));
                buttonBar.add(submitButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
                //---- returnButton ----
                returnButton.setText("取消");
                returnButton.setFont(returnButton.getFont().deriveFont(returnButton.getFont().getSize() + 5f));
                returnButton.addActionListener(this::returnFunction);
                buttonBar.add(returnButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
    }
    private JTextField idField;
    private JTextField nameField;
    private JTextField numField;
    private JTextField priceField;
}

