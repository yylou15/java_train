package action;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;



import dao.*;
import model.Book;
import util.*;
import model.User;
import db.DBUtil;


public class UserAction {
    public static void main(String[] args) throws Exception{
//        User user = new User();
//        user.setName("楼远洋");
//        String pwd = "14536878";
//        returnObj res = UserDao.submitInfo(user);
//        UserDao.submitPwd(user,pwd);
//        System.out.println(UserDao.checkPassword("楼远洋","1453687").getStatus());
//        System.out.println(res.getStatus());
//        System.out.println(res.getMsg());
//        checkPassword(user.getName(),"2323");
//        BookDao.getBookByBid(10014);
//        Book book = new Book();
//        book.setBid(10029);
//        book.setName("LOU");
//        book.setOwnerid(1006);
//        System.out.println(BookDao.insertBook(book).getStatus());

//        User user = UserDao.getInfoByName("楼远洋");
//            user.setStar(10);
//        System.out.println(user.getStar());
//            returnObj res = UserDao.updateInfo(user);
//        System.out.println(res.getStatus());
//        System.out.println(res.getMsg());


        /*
           登录模块测试demo
         */
//        User user = new User();
//        user.setName("楼德华");
//        user.setPhone("13278886615 ");
//        user.setQq("1207862938");
//        user.setIntroduction("??????");
//        returnObj res = signUp(user);
//        System.out.println(res.getStatus());
//        System.out.println(res.getMsg());
//        res = logincheck("楼德华","lou1453687999999999");
//        System.out.println(res.getStatus());
//        System.out.println(res.getMsg());



    }

    public static returnObj signUp(User user){
        returnObj res = new returnObj();
        try {
            res = UserDao.submitInfo(user);
            if(res.getStatus()){
                user.setAvatarUrl(Util.getAvatarUrl(user.getQq()));
                UserDao.submitPwd(user,"lou1453687");
                res.setMsg("注册成功");
            }else{
                res.setStatus(false);
                res.setMsg(res.getMsg());
            }
        }catch (Exception E){
            res.setStatus(false);
            res.setMsg(E.getMessage());
        }
        return res;
    }

    public static returnObj logincheck(String username,String pwd){
        returnObj res = new returnObj();
        try{
            res = UserDao.checkPassword(username,pwd);
        }catch (Exception E){
            res.setStatus(false);
            res.setMsg(E.getMessage());
        }
        return res;
    }
}
