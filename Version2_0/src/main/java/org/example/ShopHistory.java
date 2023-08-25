package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.*;

import static org.example.Login.user;
import static org.example.Main.shopHistory;
import static org.example.UsersButton.history;

public class ShopHistory extends JFrame {
    public ShopHistory(int userId) {
        initComponents(userId);
    }
    private void returnFunction(ActionEvent e) {
        history.setVisible(false);
        user.setVisible(true);
    }
    public int carsMessage(Object[][] carsTable, int userId)
    {
        Object[] tmp;
        int count = 0;
            for(ShopCars shopCar: shopHistory) {
                if (shopCar.userID == userId){
                    tmp = new Object[]{"购买时间：", shopCar.time, " ", " "};
                    carsTable[count] = tmp;
                    count += 1;
                    tmp = new Object[]{"商品编号", "商品名称", "价格", "购买数量"};
                    carsTable[count] = tmp;
                    count += 1;
                    for(Goods product : shopCar.goods){
                        tmp = new Object[]{product.ID, product.name, product.price, product.buyNum};
                        carsTable[count] = tmp;
                        count += 1;
                    }
                }

            }
        return count;
    }
    private void initComponents(int userId) {
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
                Object[] tableHeader = new Object[]{"购买人身份码：", userId, " ", " "};
                Object[][] tableContent = new Object[1000][];
                int n = carsMessage(tableContent, userId);
                messages = new JTable(Arrays.copyOfRange(tableContent, 0, n), tableHeader);
                contentPanel.setViewportView(messages);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);
            //---- label1 ----
            label1.setText("购物历史");
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
