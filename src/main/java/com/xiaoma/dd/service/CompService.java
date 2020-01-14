package com.xiaoma.dd.service;

import com.xiaoma.dd.dto.CompCreateParam;

public interface CompService {

    boolean checkHasComp(int uid);
    boolean createComp(CompCreateParam param, int createUid);
}
