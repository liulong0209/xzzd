package com.wlt.xzzd.dao;

import java.util.List;
import java.util.Map;

import com.wlt.xzzd.model.BargainInfo;
import com.wlt.xzzd.model.BargainRecord;

/**
 * 砍价持久层接口
 * 
 * @ClassName: BargainMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
public interface BargainMapper
{
    /**
     * 查询砍价列表
     *
     * BargainMapper.queryBargainList
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param param
     * @return List<BargainInfo>
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月30日-下午4:26:28
     *
     */
    List<BargainInfo> queryBargainList(Map<String, Object> param);

    /**
     * 查询砍价记录
     *
     * BargainMapper.queryBargainRecord
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
     * @date 2018年7月30日-下午4:25:47
     *
     */
    List<BargainRecord> queryBargainRecord(Integer bargainId);

    /**
     * 
     *
     * BargainMapper.queryCutedMoney
     *
     * @Description: 已砍总钱数
     *
     * @param bargainId
     * @return double
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月30日-下午5:34:55
     *
     */
    double queryCutedMoney(Integer bargainId);
}
