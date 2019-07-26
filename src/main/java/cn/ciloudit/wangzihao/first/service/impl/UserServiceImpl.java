package cn.ciloudit.wangzihao.first.service.impl;

import cn.ciloudit.wangzihao.first.dao.UserMapper;
import cn.ciloudit.wangzihao.first.entity.User;
import cn.ciloudit.wangzihao.first.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserMapper userMapper;
    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    public User getByName(String username) {
        return userMapper.getByName(username);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int deleteUser(int id) {
        return userMapper.deleteUser(id);
    }
}
