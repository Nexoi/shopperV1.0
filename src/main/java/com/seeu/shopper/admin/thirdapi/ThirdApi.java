package com.seeu.shopper.admin.thirdapi;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.third.model.Third1;
import com.seeu.shopper.third.service.Third1Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.List;

/**
 * Created by neo on 09/10/2017.
 */
@Controller
public class ThirdApi {

    @Resource
    Third1Service third1Service;

    @RequestMapping("/adminx/third1.html")
    public String third1(Model model) {
        List<Third1> third1List = third1Service.findAll();
        model.addAttribute("list", third1List);
        return "adminx/third1";
    }

    @PutMapping("/api/admin/v1/third1/{id}")
    @ResponseBody
    public Result update(@PathVariable Integer id,
                         @RequestParam("imgURL") String imgURL,
                         @RequestParam("linkURL") String linkURL,
                         @RequestParam("md5") String md5) {
        Third1 third1 = new Third1();
        third1.setId(id);
        third1.setImgurl(imgURL);
        third1.setLinkurl(linkURL);
        third1.setMD5(md5);
        third1Service.update(third1);
        return ResultGenerator.genSuccessResult();
    }


    private String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
}
