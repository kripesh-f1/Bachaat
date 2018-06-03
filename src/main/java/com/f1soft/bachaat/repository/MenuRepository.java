package com.f1soft.bachaat.repository;

import com.f1soft.bachaat.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
    Menu getById(long id);
    List<Menu> getByParentId(long id);
}
