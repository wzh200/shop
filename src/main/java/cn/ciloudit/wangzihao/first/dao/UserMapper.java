package cn.ciloudit.wangzihao.first.dao;

import cn.ciloudit.wangzihao.first.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

   public List<User> getAll();
    public User getByName(String username);
    public  int updateUser(User user);
    public int addUser(User user);

    @Delete("delete from User_table where id=#{id}")
    public int deleteUser(int id);
}
