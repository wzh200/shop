package cn.ciloudit.wangzihao.first.service;

import cn.ciloudit.wangzihao.first.entity.User;

import java.util.List;

public interface UserService {
    public List<User> getAll();
    public User getByName(String username);
    public  int updateUser(User user);
    public int addUser(User user);
    public int deleteUser(int id);
}
