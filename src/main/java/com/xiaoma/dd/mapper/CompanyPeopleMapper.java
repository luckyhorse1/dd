package com.xiaoma.dd.mapper;

import com.xiaoma.dd.pojo.CompanyPeople;
import com.xiaoma.dd.pojo.CompanyPeopleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CompanyPeopleMapper {
    long countByExample(CompanyPeopleExample example);

    int deleteByExample(CompanyPeopleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CompanyPeople record);

    int insertSelective(CompanyPeople record);

    List<CompanyPeople> selectByExample(CompanyPeopleExample example);

    CompanyPeople selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CompanyPeople record, @Param("example") CompanyPeopleExample example);

    int updateByExample(@Param("record") CompanyPeople record, @Param("example") CompanyPeopleExample example);

    int updateByPrimaryKeySelective(CompanyPeople record);

    int updateByPrimaryKey(CompanyPeople record);
}