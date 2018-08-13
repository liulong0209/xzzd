package com.wlt.xzzd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.wlt.xzzd.dao.FreeOrderMapper;
import com.wlt.xzzd.model.FreeOrder;
import com.wlt.xzzd.model.FreeOrderJoinMember;
import com.wlt.xzzd.service.FreeOrderService;
import com.wlt.xzzd.util.PagerUtil;

/**
 * 免单接口实现
 * 
 * @ClassName: FreeOrderServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liulong03@chinasoftinc.com
 *
 */
@Service
@Scope("prototype")
public class FreeOrderServiceImpl implements FreeOrderService
{
    /**
     * 注入免单持久层接口
     */
    @Autowired
    private FreeOrderMapper freeOrderMapper;

    public List<FreeOrder> queryOwnerFreeOrders(PagerUtil pager, String openId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("openId", openId);
        map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
        map.put("pageSize", pager.getPageSize());
        List<FreeOrder> freeOrders = freeOrderMapper.queryOwnerFreeOrders(map);
        if (!CollectionUtils.isEmpty(freeOrders))
        {
            for (FreeOrder freeOrder : freeOrders)
            {
                int joinedNum = freeOrderMapper.countJoinedNum(freeOrder.getOrderNo());
                freeOrder.setJoinedNum(joinedNum);
            }
        }
        return freeOrders;
    }

    public List<FreeOrder> queryJoinFreeOrders(PagerUtil pager, String openId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("openId", openId);
        map.put("startRow", (pager.getPageNo() - 1) * pager.getPageSize());
        map.put("pageSize", pager.getPageSize());
        List<FreeOrder> freeOrders = freeOrderMapper.queryJoinFreeOrders(map);
        if (!CollectionUtils.isEmpty(freeOrders))
        {
            for (FreeOrder freeOrder : freeOrders)
            {
                int joinedNum = freeOrderMapper.countJoinedNum(freeOrder.getParentOrderNo());
                freeOrder.setJoinedNum(joinedNum);

                // 参与的订单结束时间由父订单决定
                FreeOrder parentOrder = freeOrderMapper.queryFreeOrderByOrderNo(freeOrder.getParentOrderNo());
                freeOrder.setActEndTime(parentOrder.getActEndTime());
            }
        }
        return freeOrders;
    }

    public FreeOrder queryFreeOrderDetail(String orderNo)
    {
        FreeOrder freeOrder = freeOrderMapper.queryFreeOrderByOrderNo(orderNo);
        String joinMemberOrderNo = "";
        // 订单发起者,直接根据订单查询参与者，否则根据父订单查询
        if (freeOrder.getType().intValue() == 0)
        {
            joinMemberOrderNo = freeOrder.getOrderNo();
        }
        else
        {
            joinMemberOrderNo = freeOrder.getParentOrderNo();
            // 订单参与者，免单时限由父订单时间决定
            FreeOrder parentFreeOrder = freeOrderMapper.queryFreeOrderByOrderNo(freeOrder.getParentOrderNo());
            freeOrder.setActEndTime(parentFreeOrder.getActEndTime());
        }

        List<FreeOrderJoinMember> joinMembers = freeOrderMapper.queryJoinMembers(joinMemberOrderNo);
        freeOrder.setJoinMembers(joinMembers);
        // 还差几人
        freeOrder.setStillNum(freeOrder.getNeedjoinNum() - joinMembers.size());
        return freeOrder;
    }

    public void createFreeOrder(FreeOrder freeOrder)
    {
        // 没有父订单编号，则是免单发起者
        if (StringUtils.isEmpty(freeOrder.getParentOrderNo()))
        {
            freeOrder.setParentOrderNo("0000");
            freeOrder.setType(0);
        }
        else
        {
            freeOrder.setType(1);
        }
        freeOrderMapper.createFreeOrder(freeOrder);
    }

    public void updateFreeOrder(FreeOrder freeOrder)
    {
        freeOrderMapper.updateFreeOrder(freeOrder);
    }

    /**
     * 查询需要退单的父订单 1、免单时间到了人数不够，全部退款 2、免单成功，随机抽取一个人免单
     */
    public Map<String, Object> queryNeedRefundOrder()
    {
        Map<String, Object> result = new HashMap<String, Object>();
        // 免单成功需要退单的订单号
        List<FreeOrder> successOrders = new ArrayList<FreeOrder>();

        // 免单成功未抽中退款的订单，需要修改状态
        Map<String, List<String>> unLotteryMap = new HashMap<String, List<String>>();

        // 免单失败需要退单的订单号
        List<FreeOrder> failOrders = new ArrayList<FreeOrder>();

        // 查询未完成的顶级订单
        List<FreeOrder> unFinishTopOrders = freeOrderMapper.queryUnFinishTopOrder();
        if (!CollectionUtils.isEmpty(unFinishTopOrders))
        {
            for (FreeOrder unFinishTopOrder : unFinishTopOrders)
            {
                // 查询免单的参与者
                List<FreeOrderJoinMember> joinMembers = freeOrderMapper.queryJoinMembers(unFinishTopOrder.getOrderNo());
                if (!CollectionUtils.isEmpty(joinMembers))
                {
                    // 时间到了,人数不够，免单失败全部退款
                    if (unFinishTopOrder.getActEndTime().before(new Date())
                        && (unFinishTopOrder.getNeedjoinNum() - joinMembers.size()) > 0)
                    {
                        for (FreeOrderJoinMember joinMember : joinMembers)
                        {
                            FreeOrder freeOrder = new FreeOrder();
                            freeOrder.setOrderNo(joinMember.getOrderNo());
                            freeOrder.setPayMoney(joinMember.getPayMoney());
                            failOrders.add(freeOrder);
                        }
                    }

                    // 人数够了，随机抽取一个，免单成功
                    if ((unFinishTopOrder.getNeedjoinNum() - joinMembers.size()) < 1)
                    {
                        Random random = new Random();
                        int r = random.nextInt(joinMembers.size()); // 获得随机下标
                        for (int i = 0; i < joinMembers.size(); i++)
                        {
                            if (i == r)
                            {
                                FreeOrder freeOrder = new FreeOrder();
                                freeOrder.setOrderNo(joinMembers.get(r).getOrderNo());
                                freeOrder.setPayMoney(joinMembers.get(r).getPayMoney());
                                successOrders.add(freeOrder);
                            }
                            else
                            {
                                // 未抽中退款的订单，在抽中退款订单退款成功后修改其状态
                                List<String> unLottery = new ArrayList<String>();
                                unLottery.add(joinMembers.get(i).getOrderNo());
                                unLotteryMap.put(joinMembers.get(r).getOrderNo(), unLottery);

                            }
                        }
                    }
                }
            }
        }

        result.put("successOrders", successOrders);
        result.put("failOrders", failOrders);
        result.put("unLotteryMap", unLotteryMap);
        return result;
    }
}
