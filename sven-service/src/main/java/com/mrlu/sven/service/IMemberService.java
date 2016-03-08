package com.mrlu.sven.service;

import com.mrlu.sven.SearchParams.MemberParams;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.domain.Member;

/**
 * Created by xiexiyang on 15/4/11.
 */
public interface IMemberService {

    /**
     * 获取分页数据
     * @param memberParams
     * @return
     */
    ResultPage<Member> getResultPage(MemberParams memberParams);

    /**
     * 根据ID获取member
     * @param id
     * @return
     */
    Member getById(Long id);

    void updateMember(Member member)throws SvenException;
}