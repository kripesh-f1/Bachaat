package com.f1soft.bachaat.repository;

import com.f1soft.bachaat.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
    Menu findByNameAndLinkAndParentId(String name, String link, long parentId);
}
