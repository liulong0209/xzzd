package com.wlt.xzzd.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.wlt.xzzd.dao.BargainMapper;
import com.wlt.xzzd.model.BargainInfo;
import com.wlt.xzzd.model.BargainRecord;
import com.wlt.xzzd.service.BargainService;
import com.wlt.xzzd.util.PagerUtil;

/**
 * 砍价接口实现
 * 
 * @ClassName: BargainServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Service
@Scope("prototype")
public class BargainServiceImpl implements BargainService
{
    /**
     * 注入砍价持久层接口
     */
    @Resource
    private BargainMapper bargainMapper;

    public List<BargainInfo> queryBargainList(PagerUtil pager, String openId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
        map.put("pageSize", pager.getPageSize());
        map.put("openId", openId);
        List<BargainInfo> bargainList = bargainMapper.queryBargainList(map);
        // 计算砍价还有多钱成功
        if (!CollectionUtils.isEmpty(bargainList))
        {
            for (BargainInfo bi : bargainList)
            {
                double cutedMoney = bargainMapper.queryCutedMoney(bi.getBargainId());
                bi.setStillMoney(bi.getBeCutMoney() - cutedMoney);
            }
        }
        return bargainList;
    }

    public List<BargainRecord> queryBargainRecord(Integer bargainId)
    {
        return bargainMapper.queryBargainRecord(bargainId);
    }

}
