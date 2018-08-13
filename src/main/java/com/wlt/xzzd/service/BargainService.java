package com.wlt.xzzd.service;

import java.util.List;

import com.wlt.xzzd.model.BargainInfo;
import com.wlt.xzzd.model.BargainRecord;
import com.wlt.xzzd.util.PagerUtil;

/**
 * 砍价接口
 * 
 * @ClassName: BargainService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
public interface BargainService
{
    /**
     * 查询砍价列表
     *
     * BargainService.queryBargainList
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param pager
     * @param openId
     * @return List<BargainInfo>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月30日-下午4:24:42
     *
     */
    List<BargainInfo> queryBargainList(PagerUtil pager, String openId);

    /**
     * 查询砍价记录
     *
     * BargainService.queryBargainRecord
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param bargainId
     * @return List<BargainRecord>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月30日-下午4:21:27
     *
     */
    List<BargainRecord> queryBargainRecord(Integer bargainId);
}
