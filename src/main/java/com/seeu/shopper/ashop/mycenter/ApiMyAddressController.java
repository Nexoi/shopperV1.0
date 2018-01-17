package com.seeu.shopper.ashop.mycenter;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.user.model.UserAddress;
import com.seeu.shopper.user.service.UserAddressService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;

/**
 * Created by neo on 11/09/2017.
 * <p>
 * 因为地址信息前端写得有点奇葩，所以用 ajax 处理
 */
@RestController
@RequestMapping("/api/shop/v1/ct/address")
public class ApiMyAddressController {
    @Resource
    UserAddressService userAddressService;

    @PostMapping
    public Result addAdrs(@RequestAttribute("uid") Integer uid,
                          @RequestParam(value = "name", required = true) String name,
                          @RequestParam(value = "phone", required = true) String phone,
                          @RequestParam(value = "email", required = true) String email,
                          @RequestParam(value = "detail", required = true) String detail,
                          @RequestParam(value = "city", required = true) String city,
                          @RequestParam(value = "country", required = true) String country,
                          @RequestParam(value = "zip", required = true) String zip) {
        if (name == null && phone == null && email == null && detail == null && city == null && country == null && zip == null) {
            return ResultGenerator.genSuccessResult();
        }

        UserAddress userAddress = new UserAddress();
        userAddress.setUid(uid);
        userAddress.setName(reformData(name, 125));
        userAddress.setPhone(reformData(phone, 18));
        userAddress.setEmail(email);
        userAddress.setDetail(detail);
        userAddress.setCity(city);
        userAddress.setCountry(reformData(country, 45));
        userAddress.setPostcode(reformData(zip, 10));

        userAddressService.save(userAddress);

        return ResultGenerator.genSuccessResult();
    }

    // 小工具自己用。。。
    private String reformData(String data, int length) {
        return (data == null ? null : data.substring(0, data.length() > length ? length : data.length()));
    }

    @PutMapping("/{id}")
    public Result updateAdrs(@RequestAttribute("uid") Integer uid,
                             @PathVariable("id") Integer id,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "phone", required = false) String phone,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "detail", required = false) String detail,
                             @RequestParam(value = "city", required = false) String city,
                             @RequestParam(value = "country", required = false) String country,
                             @RequestParam(value = "zip", required = false) String zip) {
        if (name == null && phone == null && email == null && detail == null && city == null && country == null && zip == null) {
            return ResultGenerator.genSuccessResult();
        }

        UserAddress userAddress = new UserAddress();
        userAddress.setId(id);
        userAddress.setName(reformData(name, 125));
        userAddress.setPhone(reformData(phone, 18));
        userAddress.setEmail(email);
        userAddress.setDetail(detail);
        userAddress.setCity(city);
        userAddress.setCountry(reformData(country, 45));
        userAddress.setPostcode(reformData(zip, 10));

        Condition condition = new Condition(UserAddress.class);
        condition.createCriteria().andCondition("id = " + id + " AND uid = " + uid);
        userAddressService.updateCondition(userAddress, condition);

        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result deleteAdrs(@RequestAttribute("uid") Integer uid,
                             @PathVariable("id") Integer id) {

        Condition condition = new Condition(UserAddress.class);
        condition.createCriteria().andCondition("id = " + id + " AND uid = " + uid);
        userAddressService.deleteByCondition(condition);
        return ResultGenerator.genSuccessResult();
    }
}
