package com.newlecture.rlandapi.repository;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.newlecture.rlandapi.entity.Menu;

import jakarta.persistence.EntityManager;

@Repository
public class JpaMenuRepository implements MenuRepository{

  // session 사용 가능
  @Autowired
  private EntityManager entityManager;

  @Override
  public List<Menu> getList(int offset, int size) {
    Session session = entityManager.unwrap(Session.class);

    List<Menu> list = session.createQuery("from Menu",Menu.class).list();

    session.close();
    return list;
  }

  @Override
  public Menu get(int id) {
    Session session = entityManager.unwrap(Session.class);

    Menu menu = session.get(Menu.class, id);

    session.close();
    return menu;
  }

  @Override
  public Menu create(Menu menu) {
    // TODO Auto-generated method stub
    return null;
  }
  
}
