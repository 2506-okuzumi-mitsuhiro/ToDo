package com.example.odango.mapper;

import com.example.odango.repository.entity.Tasks;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface TaskMapper {
//    List<Tasks> selectAll(Timestamp startDate, Timestamp endDate);
    List<Tasks> select(@Param("startDate")Timestamp startDate, @Param("endDate")Timestamp endDate, @Param("content")String content, @Param("status")Short status);
    void insert(Tasks saveTask);
    void update(Tasks saveTask);
    void delete(Integer id);
}
