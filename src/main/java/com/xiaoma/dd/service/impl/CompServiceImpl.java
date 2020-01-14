package com.xiaoma.dd.service.impl;

import com.xiaoma.dd.mapper.CompanyStaffMapper;
import com.xiaoma.dd.pojo.CompanyStaff;
import com.xiaoma.dd.pojo.CompanyStaffExample;
import com.xiaoma.dd.service.CompService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompServiceImpl implements CompService {

    @Autowired
    private CompanyStaffMapper companyStaffMapper;

    @Override
    public boolean checkHasComp(int uid) {
        CompanyStaffExample example = new CompanyStaffExample();
        example.createCriteria().andUidEqualTo(uid);
        List<CompanyStaff> list = companyStaffMapper.selectByExample(example);
        if (list.size() > 0)
            return true;
        return false;
    }
}
