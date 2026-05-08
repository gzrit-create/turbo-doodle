package com.accountBook.accountBook.controller;

import com.accountBook.accountBook.common.Result;
import com.accountBook.accountBook.dto.BillAddRequest;
import com.accountBook.accountBook.entity.Bill;
import com.accountBook.accountBook.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    public Result<Bill> addBill(@RequestBody BillAddRequest request,
                                @RequestAttribute("userId") Long userId) {
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return Result.fail("金额必须大于0");
        }
        Bill bill = billService.addBill(userId, request);
        return Result.success(bill);
    }

    @PutMapping("/{billId}")
    public Result<String> updateBill(@PathVariable Long billId,
                                     @RequestBody BillAddRequest request,
                                     @RequestAttribute("userId") Long userId) {
        boolean ok = billService.updateBill(billId, userId, request);
        return ok ? Result.success("修改成功") : Result.fail("账单不存在或无权操作");
    }

    @DeleteMapping("/{billId}")
    public Result<String> deleteBill(@PathVariable Long billId,
                                     @RequestAttribute("userId") Long userId) {
        boolean ok = billService.deleteBill(billId, userId);
        return ok ? Result.success("删除成功") : Result.fail("账单不存在或无权操作");
    }

    @GetMapping
    public Result<List<Bill>> getUserBills(@RequestAttribute("userId") Long userId) {
        List<Bill> bills = billService.getUserBills(userId);
        return Result.success(bills);
    }

    @GetMapping("/{billId}")
    public Result<Bill> getBillDetail(@PathVariable Long billId,
                                      @RequestAttribute("userId") Long userId) {
        Bill bill = billService.getBillById(billId, userId);
        if (bill == null) return Result.fail("账单不存在");
        return Result.success(bill);
    }

    // 总余额
    @GetMapping("/balance")
    public Result<BigDecimal> getBalance(@RequestAttribute("userId") Long userId) {
        BigDecimal balance = billService.getBalance(userId);
        return Result.success(balance);
    }

    // 按分类统计（type 1收入 2支出）
    @GetMapping("/statistics")
    public Result<List<Object[]>> getStatistics(@RequestParam Integer type,
                                                @RequestAttribute("userId") Long userId) {
        List<Object[]> stats = billService.getCategoryStatistics(userId, type);
        return Result.success(stats);
    }
}
