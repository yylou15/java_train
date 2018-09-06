package dao;
import model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.DBUtil;
import util.*;

public class UserDao {
    public boolean addUser(){
        return false;
    }
    public boolean checkPwd(){
        return false;
    }
    public void getInfo() throws Exception{
        Connection cn = DBUtil.getConnection();
        PreparedStatement ptmt = cn.prepareStatement("SELECT * FROM tieyif4_user_info");
        ResultSet rs = ptmt.executeQuery();
        List<User> result = new ArrayList<User>();
        while(rs.next()){
            System.out.println(rs.getInt("uid"));
        }
    }


    /*
     *  提交个人信息
     */
    public static returnObj submitInfo(User user) throws Exception{
        returnObj res = new returnObj();
        user.setAvatarUrl(Util.getAvatarUrl(user.getQq()));
        Connection conn=DBUtil.getConnection();
        String sql = "SELECT * FROM tieyif4_user_info WHERE `name` = ?";
        PreparedStatement ptmt=conn.prepareStatement(sql);
        ptmt.setString(1,user.getName());
        ResultSet rs = ptmt.executeQuery();
        if(rs.next()){
            res.setStatus(false);
            res.setMsg("该用户名已存在");
            return res;
        }
        sql="INSERT INTO tieyif4_user_info (name,level_score,level_star,avatarUrl,phone,qq,introduction) VALUES (?,?,?,?,?,?,?)";
        ptmt=conn.prepareStatement(sql);

        ptmt.setString(1, user.getName());
        ptmt.setInt(2, user.getScore());
        ptmt.setInt(3, user.getStar());
        ptmt.setString(4, user.getAvatarUrl());
        ptmt.setString(5, user.getPhone());
        ptmt.setString(6, user.getQq());
        ptmt.setString(7, user.getIntroduction());
        ptmt.execute();
        res.setStatus(true);
        return res;
    }

    /*
        提交密码
     */
    public static void submitPwd(User user,String pwd) throws Exception{
        String name = user.getName();
        Connection cnn = DBUtil.getConnection();
        String sql = "SELECT * FROM tieyif4_user_info WHERE `name` = ?";
        PreparedStatement ptmt = cnn.prepareStatement(sql);
        ptmt.setString(1,name);
        ResultSet rs = ptmt.executeQuery();
        if(rs.next()) {
            int uid = rs.getInt("uid");
            String salt = CryptoUtils.getSalt();
            String hashedPassword = CryptoUtils.getHash(pwd, salt);
            sql = "SELECT * FROM tieyif4_user_pwd WHERE `uid` = " + uid;
            ptmt = cnn.prepareStatement(sql);
            rs = ptmt.executeQuery();
            if (rs.next()) {
                sql = "UPDATE tieyif4_user_pwd SET password=?,salt=? WHERE `uid` = " + uid;
                ptmt = cnn.prepareStatement(sql);
                ptmt.setString(1, hashedPassword);
                ptmt.setString(2, salt);
                ptmt.execute();
            } else {
                sql = "INSERT INTO tieyif4_user_pwd (uid,password,salt) VALUES (?,?,?)";
                ptmt = cnn.prepareStatement(sql);
                ptmt.setInt(1, uid);
                ptmt.setString(2, hashedPassword);
                ptmt.setString(3, salt);
                ptmt.execute();
            }
        }
//        String sql = "INSERT INTO tieyif4_user_pwd ()"
    }

    /**
     *  检查密码
     */
    public static returnObj checkPassword(String name,String pwd) throws Exception{
        returnObj res = new returnObj();
        Connection conn=DBUtil.getConnection();
        String sql = "SELECT * FROM tieyif4_user_info WHERE `name` = ?";
        PreparedStatement ptmt=conn.prepareStatement(sql);
        ptmt.setString(1,name);
        ResultSet rs = ptmt.executeQuery();
        if(rs.next()){
            int uid = rs.getInt("uid");
            sql = "SELECT * FROM tieyif4_user_pwd WHERE `uid` = " + uid;
            ptmt=conn.prepareStatement(sql);
            ResultSet rs1 = ptmt.executeQuery();
            if(rs1.next()){
                String salt = rs1.getString("salt");
                String hashedpwd = rs1.getString("password");
                if(CryptoUtils.verify(hashedpwd,pwd,salt)){
                    res.setStatus(true);
                }else{
                    res.setStatus(false);
                    res.setMsg("密码错误");
                }
            }

        }else{
            res.setStatus(false);
            res.setMsg("此用户不存在，请先注册!");
        }

        return res;
    }

}
