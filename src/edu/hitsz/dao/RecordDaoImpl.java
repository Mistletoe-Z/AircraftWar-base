package edu.hitsz.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecordDaoImpl implements RecordDao {
    private String fileName;

    // 构造方法，传入难度名（如 "EASY", "NORMAL"）决定存入哪个文件
    public RecordDaoImpl(String difficulty) {
        this.fileName = "records_" + difficulty + ".dat";
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Record> getAllRecords() {
        File file = new File(fileName);
        if (!file.exists()) {
            return new ArrayList<>(); // 如果文件不存在，返回空列表
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Record>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void addRecord(Record record) {
        List<Record> records = getAllRecords();
        records.add(record);
        // 按分数从高到低排序 (使用 Lambda 表达式)
        records.sort((r1, r2) -> Integer.compare(r2.getScore(), r1.getScore()));

        // 写回文件
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRecord(int index) {
        // 留空或实现删除逻辑，以备后续实验五使用
    }
}