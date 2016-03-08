package com.mrlu.sven.service.impl;

import com.mrlu.sven.SearchParams.MemberParams;
import com.mrlu.sven.common.PageUtil;
import com.mrlu.sven.common.ResultPage;
import com.mrlu.sven.common.SvenException;
import com.mrlu.sven.dao.IMemberDao;
import com.mrlu.sven.domain.Member;
import com.mrlu.sven.service.IMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiexiyang on 15/4/11.
 */
@Service("memberService")
public class MemberServiceImpl implements IMemberService {

    @Resource(name="memberDao")
    private IMemberDao memberDao;

    @Override
    public ResultPage<Member> getResultPage(MemberParams memberParams) {
        ResultPage<Member> resultPage = null;
        HashMap<String, Object> param = memberParams.toParamsMap();
        //格式化分页数据
        param = PageUtil.getPageMap(param);
        //查询总数
        Integer totalCount = memberDao.getPageCountByParam(param);
        //查询列表
        List<Member> list = memberDao.getPageListByParam(param);
        resultPage = new ResultPage<Member>(totalCount, (Integer) param.get("pageSize"), (Integer) param.get("pageNo"), list);
        return resultPage;
    }

    @Override
    public Member getById(Long id) {
        Member member = memberDao.selectById(id);
        return member;
    }

    @Override
    public void updateMember(Member member)throws SvenException{
        Member oldMember = memberDao.selectById(member.getId());
        if(null == oldMember){
            throw new SvenException(HttpStatus.BAD_REQUEST.value(), "member不存在");
        }
        member.setUpdateAt(System.currentTimeMillis());
        memberDao.update(member);
    }
}