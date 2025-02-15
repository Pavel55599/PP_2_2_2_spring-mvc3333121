package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.UserDao;
import web.model.User;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserDao userDao;

  

    @Override
    public void save(User user) {
        userDao.save(user);

    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
//        return null;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
//        return List.of();
    }

    @Override
    public void update(User user) {
        userDao.update(user);

    }

    @Override
    public void delete(User user) {
        userDao.delete(user);

    }
}
