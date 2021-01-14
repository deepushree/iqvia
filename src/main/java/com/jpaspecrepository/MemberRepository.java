package com.jpaspecrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.jpaspecdomain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor { }
