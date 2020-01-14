package com.xiaoma.dd.service.impl;

import com.xiaoma.dd.dto.CompCreateParam;
import com.xiaoma.dd.mapper.CompanyMapper;
import com.xiaoma.dd.mapper.CompanyStaffMapper;
import com.xiaoma.dd.pojo.Company;
import com.xiaoma.dd.pojo.CompanyExample;
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

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public boolean checkHasComp(int uid) {
        CompanyStaffExample example = new CompanyStaffExample();
        example.createCriteria().andUidEqualTo(uid);
        List<CompanyStaff> list = companyStaffMapper.selectByExample(example);
        if (list.size() > 0)
            return true;
        return false;
    }

    @Override
    public boolean createComp(CompCreateParam param, int createUid) {
        Company company = new Company();
        company.setName(param.getName());
        company.setRepName(param.getRepName());
        company.setCreateMoney(param.getCreateMoney());
        company.setCreateTime(param.getCreateTime());
        company.setTel(param.getTel());
        company.setAddress(param.getAddress());
        company.setCreateUid(createUid);

        CompanyExample example = new CompanyExample();
        example.createCriteria();
        long count = companyMapper.countByExample(example);
        System.out.println("------------------------------"+count);
        if (count == 0) {
            company.setId(1);
        }
        companyMapper.insertSelective(company);

        CompanyStaff staff = new CompanyStaff();
        CompanyExample example2 = new CompanyExample();
        example.createCriteria().andCreateUidEqualTo(createUid);
        List<Company> list = companyMapper.selectByExample(example2);
        staff.setCid(list.get(0).getId());
        staff.setUid(createUid);
        staff.setLevel(5);
        companyStaffMapper.insertSelective(staff);
        return true;
    }
}
