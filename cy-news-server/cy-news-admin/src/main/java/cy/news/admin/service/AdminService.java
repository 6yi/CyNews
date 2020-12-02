package cy.news.admin.service;

import com.cy.news.common.Pojo.Admin;
import cy.news.admin.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yj
 * @ClassName AdminService
 * @date 2020/11/30  15:30
 * @Version V1.0
 * @Description: TODO
 */
@Service
public class AdminService {
    @Autowired
    AdminDao adminDao;

    public Admin findAdminByAdminName(String adminName){
        return adminDao.findByUsername(adminName);
    }
}