package com.xiaoma.dd.controller;

import com.xiaoma.dd.component.CommonResult;
import com.xiaoma.dd.dto.CompCreateParam;
import com.xiaoma.dd.pojo.User;
import com.xiaoma.dd.service.CompService;
import com.xiaoma.dd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("comp")
public class CompController {
    @Autowired
    private CompService compService;

    @Autowired
    private UserService userService;

    @RequestMapping(value="/checkForCreateComp", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult checkForCreateComp(Principal principal) {
        String phone = principal.getName();
        User user = userService.getUserByPhone(phone);
        if (compService.checkHasComp(user.getId())) {
            return CommonResult.failed("该用户已经加入了公司");
        }
        return CommonResult.success("未加入任何公司，可以新建");
    }

    @RequestMapping(value = "/createComp", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createComp(@RequestBody CompCreateParam param, Principal principal) {
        String phone = principal.getName();
        User user = userService.getUserByPhone(phone);
        if (compService.createComp(param, user.getId())){
            return CommonResult.success("创建成功");
        }
        return CommonResult.failed("创建失败");
    }
}
