package com.seeu.shopper.admin.ship;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seeu.shopper.ship.model.Ship;
import com.seeu.shopper.ship.service.ShipService;
import com.seeu.shopper.store.model.DbStore;
import com.seeu.shopper.store.service.DbStoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 23/09/2017.
 */
@Controller
@RequestMapping("/adminx")
public class ShipManagerController {

    @Resource
    ShipService shipService;

    /**
     * 资源列表，分页
     *
     * @return
     */
    @RequestMapping("/ship.html")
    public String resourcesPage(Model model) {
        List<Ship> ships = shipService.findAll();
        model.addAttribute("ships", ships);
        return "adminx/ship";
    }

}
