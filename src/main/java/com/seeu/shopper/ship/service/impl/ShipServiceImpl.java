package com.seeu.shopper.ship.service.impl;

import com.seeu.shopper.ship.dao.ShipMapper;
import com.seeu.shopper.ship.model.Ship;
import com.seeu.shopper.ship.service.ShipService;
import com.seeu.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by neoxiaoyi on 2017/09/09.
 */
@Service
@Transactional
public class ShipServiceImpl extends AbstractService<Ship> implements ShipService {
    @Resource
    private ShipMapper shipMapper;

}
