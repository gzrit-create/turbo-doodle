package com.accountBook.accountBook.service;


import com.accountBook.accountBook.Mapper.BillMapper;
import com.accountBook.accountBook.common.DataBase;
import com.accountBook.accountBook.dto.BillAddRequest;
import com.accountBook.accountBook.entity.Bill;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    private BillMapper billMapper;

    public Bill addBill(Long userId, BillAddRequest request) {
        Bill bill = new Bill();
        bill.setUserId(userId);
        bill.setCategoryId(request.getCategoryId());
        bill.setAmount(request.getAmount());
        bill.setNote(request.getNote());
        bill.setBillDate(request.getBillDate());
        bill.setType(request.getType());
        billMapper.insert(bill);
        return bill; // 插入后 id 会自动回填
    }

    public boolean updateBill(Long billId, Long userId, BillAddRequest request) {
        // 先检查归属权
        Bill existing = billMapper.selectById(billId);
        if (existing == null || !existing.getUserId().equals(userId)) {
            return false;
        }
        Bill bill = new Bill();
        bill.setId(billId);
        bill.setCategoryId(request.getCategoryId());
        bill.setAmount(request.getAmount());
        bill.setNote(request.getNote());
        bill.setBillDate(request.getBillDate());
        bill.setType(request.getType());
        return billMapper.updateById(bill) > 0;
    }

    public boolean deleteBill(Long billId, Long userId) {
        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bill::getId, billId)
                .eq(Bill::getUserId, userId);
        return billMapper.delete(wrapper) > 0;
    }

    public Bill getBillById(Long billId, Long userId) {
        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bill::getId, billId)
                .eq(Bill::getUserId, userId);
        return billMapper.selectOne(wrapper);
    }

    public List<Bill> getUserBills(Long userId) {
        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bill::getUserId, userId);
        return billMapper.selectList(wrapper);
    }

    public BigDecimal getBalance(Long userId) {
        List<Bill> bills = getUserBills(userId);
        BigDecimal income = bills.stream().filter(b -> b.getType() == 1)
                .map(Bill::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expense = bills.stream().filter(b -> b.getType() == 2)
                .map(Bill::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return income.subtract(expense);
    }

    public List<Object[]> getCategoryStatistics(Long userId, Integer type) {
        return getUserBills(userId).stream()
                .filter(b -> b.getType().equals(type))
                .collect(Collectors.groupingBy(Bill::getCategoryId,
                        Collectors.mapping(Bill::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))))
                .entrySet().stream()
                .map(e -> new Object[]{e.getKey(), e.getValue()})
                .collect(Collectors.toList());
    }
}