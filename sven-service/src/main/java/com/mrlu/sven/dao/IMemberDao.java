package com.mrlu.sven.dao;

import com.mrlu.sven.domain.Member;

import java.util.HashMap;
import java.util.List;

public interface IMemberDao {

    public Long insert(Member member);

    public Member selectById(Long id);

    public List<Member> getPageListByParam(HashMap<String, Object> param);

    public Integer getPageCountByParam(HashMap<String, Object> paramMap);

    public void deleteById(Long id);

    Member selectByAccountId(Long accountId);

    void update(Member member);
}