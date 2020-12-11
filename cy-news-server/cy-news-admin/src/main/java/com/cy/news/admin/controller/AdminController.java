package com.cy.news.admin.controller;

import com.cy.news.admin.utils.AdminJWTUtils;
import com.cy.news.admin.vo.AdminLoginSuccessVO;
import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Exception.UserRetErrorCode;
import com.cy.news.common.Pojo.Admin;
import com.cy.news.admin.exception.AdminLoginError;
import com.cy.news.admin.service.AdminService;
import com.cy.news.admin.vo.AdminLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yj
 * @ClassName AdminController
 * @date 2020/11/30  15:32
 * @Version V1.0
 * @Description: TODO
 */
@RestController
@CrossOrigin(origins = "http://192.168.17.145:8080", maxAge = 3600)
public class AdminController {
    @Autowired
    AdminService adminService;


    @PostMapping("/login")
    public ResultDTO login(@RequestBody AdminLoginVo adminLoginVo) {

        Admin admin = adminService.findAdminByAdminName(adminLoginVo.getAdminName());


        if (admin == null) {
            return ResultDTO.builder().code(AdminLoginError.ADMINNAME_ERROR).data("该管理员账户不存在").build();
        } else if (admin.getAPassword().equals(adminLoginVo.getPassWord()) == false) {
            return ResultDTO.builder().code(AdminLoginError.PASS_WORD_ERROR).data("密码错误").build();
        }
            admin.setAPassword(null);
            admin.setAStatus(null);
            String jwtString = AdminJWTUtils.adminLoginJwtString(admin);
            System.out.println(jwtString);
            AdminLoginSuccessVO adminLoginSuccessVO = AdminLoginSuccessVO.builder().JWT_TOKEN(jwtString).admin(admin).build();
            return ResultDTO.builder().code(UserRetErrorCode.OK).data(adminLoginSuccessVO).build();

    }
    @PostMapping("/news/test")
    public void test(){
        System.out.println("test");

    }
}
