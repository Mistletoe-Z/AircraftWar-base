package edu.hitsz.dao;

import java.util.List;

public interface RecordDao {
    void addRecord(Record record);
    List<Record> getAllRecords();
    void deleteRecord(int index); // 根据需求，后续可以在界面中删除记录
}