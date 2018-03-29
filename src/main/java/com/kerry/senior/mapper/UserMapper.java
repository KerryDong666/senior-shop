package com.kerry.senior.mapper;

import com.kerry.senior.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    User get(Long id);

    void delete(Long id);

    int inset(User user);

    void update(User user);

    List<User> list();

    int count();
}
