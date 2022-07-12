package com.dean.my_blog.repo;

import com.dean.my_blog.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User,Long> {
    User findFirstByEmail(String emaail);
}
