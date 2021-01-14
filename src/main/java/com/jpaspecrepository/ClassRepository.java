package com.jpaspecrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jpaspecdomain.MemberClass;

import java.util.List;

public interface ClassRepository extends JpaRepository<MemberClass, Long>, JpaSpecificationExecutor {
    List<MemberClass> findAllByNameContainsIgnoreCase(String searchString);
}
