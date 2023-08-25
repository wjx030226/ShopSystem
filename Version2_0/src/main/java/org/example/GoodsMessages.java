package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.*;

import static org.example.Main.*;
import static org.example.ShopPage.goodsMessages;
import static org.example.UsersButton.shopPage;

public class GoodsMessages extends JFrame {
    public GoodsMessages() {
        initComponents();
    }
    private void returnFunction(ActionEvent e) {
        goodsMessages.setVisible(false);
        shopPage.setVisible(true);
    }
    private void initComponents() {
        JPanel dialogPane = new JPanel();
        JScrollPane contentPanel = new JScrollPane();
        JTable messages;
        JLabel label1 = new JLabel();
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
                //---- table1 ----
                Object[] tableHeader = new Object[]{"商品编号", "名称", "价格", "数量"};
                Object[][] tableContent = new Object[1000][];
                int n = goods.update(tableContent);
                messages = new JTable(Arrays.copyOfRange(tableContent, 0, n), tableHeader);
                messages.setFont(messages.getFont().deriveFont(messages.getFont().getSize() + 5f));
                contentPanel.setViewportView(messages);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);
            //---- label1 ----
            label1.setText("全部商品信息");
            label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 10f));
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            dialogPane.add(label1, BorderLayout.NORTH);
            //---- returnButton ----
            returnButton.setText("返回");
            returnButton.addActionListener(this::returnFunction);
            dialogPane.add(returnButton, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
    }
}

