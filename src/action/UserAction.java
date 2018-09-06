package action;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;



import dao.UserDao;
import util.*;
import model.User;
import db.DBUtil;


public class UserAction {
    public static void main(String[] args) throws Exception{
        User user = new User();
        user.setName("楼远洋");
        String pwd = "14536878";
        returnObj res = UserDao.submitInfo(user);
        UserDao.submitPwd(user,pwd);
        System.out.println(UserDao.checkPassword("楼远洋","1453687").getStatus());
        System.out.println(res.getStatus());
        System.out.println(res.getMsg());
//        checkPassword(user.getName(),"2323");

    }


}
