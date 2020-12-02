package cy.news.admin.controller;

import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Pojo.Admin;
import cy.news.admin.exception.AdminLoginError;
import cy.news.admin.service.AdminService;
import cy.news.admin.vo.AdminLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yj
 * @ClassName AdminController
 * @date 2020/11/30  15:32
 * @Version V1.0
 * @Description: TODO
 */
@RestController
public class AdminController {
    @Autowired
    AdminService adminService;

    public ResultDTO login(@RequestBody AdminLoginVo adminLoginVo){
        Admin admin =adminService.findAdminByAdminName(adminLoginVo.getAdminName());

        if(admin==null){
            return ResultDTO.builder().code(AdminLoginError.ADMINNAME_ERROR).data("该管理员账户不存在").build();
        }else if(admin.getAPassword().equals(adminLoginVo.getPassWord())==false){
            return ResultDTO.builder().code(AdminLoginError.PASS_WORD_ERROR).data("密码错误").build();
        }
       // StpUtil.setLoginId(admin.getAId());

        return ResultDTO.builder().code(AdminLoginError.OK).data("登录成功").build();
    }
}
