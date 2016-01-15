package com.qiongsong.ficus.dal.optlock;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.qiongsong.ficus.dal.BaseDao;
import com.qiongsong.ficus.dal.BaseTest;
import com.qiongsong.ficus.dal.model.User;

public class OptimisticOperationTest extends BaseTest {

  @Autowired
  @Qualifier("jdbcDao")
  private BaseDao dao;

  // @Test
  public void testSave() {
    User u = new User();
    u.setUserName("fqs");
    u.setCreateTime(new Date());
    u.setBlance(100D);
    dao.insert(u);
  }

  @Test
  public void testUpdate() {
    List<User> users = dao.query(User.class);
    for (User user : users) {
      user.setUserName("张老板");
      dao.update(user);
    }
  }

}
