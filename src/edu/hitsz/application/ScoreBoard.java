package edu.hitsz.application;

import edu.hitsz.dao.Record;
import edu.hitsz.dao.RecordDao;
import edu.hitsz.dao.RecordDaoImpl;

import javax.swing.*;
        import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ScoreBoard {
    private JPanel mainPanel;
    private JScrollPane tableScrollPane;
    private JTable scoreTable;
    private JButton deleteButton;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // 🌟 构造方法接收 difficulty，这样才知道读哪个文件
    public ScoreBoard(String difficulty) {
        RecordDao recordDao = new RecordDaoImpl(difficulty);

        // 1. 设置表格列名
        String[] columnName = {"名次", "玩家名", "得分", "记录时间"};

        // 2. 从 DAO 获取数据并转换为二维数组
        List<Record> records = recordDao.getAllRecords();
        String[][] tableData = new String[records.size()][4];
        for (int i = 0; i < records.size(); i++) {
            Record r = records.get(i);
            tableData[i][0] = String.valueOf(i + 1);
            tableData[i][1] = r.getName();
            tableData[i][2] = String.valueOf(r.getScore());
            tableData[i][3] = r.getTime();
        }

        // 3. 将数据装载到表格模型中
        DefaultTableModel model = new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // 表格内容不可编辑
            }
        };
        scoreTable.setModel(model);
        tableScrollPane.setViewportView(scoreTable);

        // 4. 删除按钮事件
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();
                if (row != -1) {
                    int result = JOptionPane.showConfirmDialog(deleteButton, "是否确定删除？", "提示", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        // 在界面表格上删除
                        model.removeRow(row);
                        // 在本地文件中删除 (调用 DAO 的删除方法)
                        recordDao.deleteRecord(row);
                    }
                }
            }
        });
    }
}