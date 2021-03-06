package com.autobon.order.service;

import com.autobon.order.entity.SysStat;
import com.autobon.order.repository.SysStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lu on 2016/3/21.
 */
@Service
public class SysStatService {
    @Autowired
    private SysStatRepository repository;

    public SysStat save(SysStat stat) {
        return repository.save(stat);
    }

     //统计某日
    public SysStat getOfDay(Date day){
        return repository.getOfDay(day);
    }

    //统计某月
    public SysStat getOfMonth(Date month){
        return repository.getOfMonth(month);
    }

    //统计某个日期段内
    public List<SysStat> findBetweenOfDay(Date start,Date end){
        return repository.findBetweenOfDay(start,end);
    }

    //统计某个月份段内
    public List<SysStat> findBetweenOfMonth(Date start,Date end){
        return repository.findBetweenOfMonth(start,end);
    }
}
