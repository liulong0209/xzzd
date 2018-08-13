package com.wlt.xzzd.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wlt.xzzd.service.BargainService;
import com.wlt.xzzd.util.PagerUtil;

/**
 * 砍价控制器
 * 
 * @ClassName: BargainController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Controller
@RequestMapping("/api/bargain")
public class BargainController
{
    /**
     * 日志记录器
     */
    private Logger LOGGER = LoggerFactory.getLogger(BargainController.class);

    /**
     * 注入砍价接口
     */
    @Autowired
    private BargainService bargainService;

    /**
     * 查询我的砍价列表
     *
     * BargainController.queryBargainList
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param openId
     * @return Object
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月30日-下午4:21:06
     *
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Object queryBargainList(PagerUtil pager, String openId)
    {
        LOGGER.debug("BargainController->queryBargainList>openId={}", openId);
        return bargainService.queryBargainList(pager, openId);
    }

    /**
     * 查询砍价记录
     *
     * BargainController.queryBargainRecord
     *
     * @Description: TODO(这里用一句话描述这个方法的作用)
     *
     * @param bargainId
     * @return Object
     * 
     * @exception Exception
     * 
     * @author liulong03@chinasoftinc.com
     *
     * @date 2018年7月30日-下午2:58:43
     *
     */
    @RequestMapping(value = "/record", method = RequestMethod.POST)
    @ResponseBody
    public Object queryBargainRecord(Integer bargainId)
    {
        LOGGER.debug("BargainController->queryBargainRecord>bargainId={}", bargainId);
        return bargainService.queryBargainRecord(bargainId);
    }
}
