package com.seeu.shopper.user.iservice;

import com.seeu.shopper.user.dao.UserAmountMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by neo on 17/09/2017.
 */
@Service
public class UserAmountService {
    @Resource
    UserAmountMapper mapper;

    public int updateAmount(Integer uid, BigDecimal amount) {
        return mapper.updateAmount(uid, amount);
    }
}
