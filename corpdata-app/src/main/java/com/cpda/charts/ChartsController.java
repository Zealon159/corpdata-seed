package com.cpda.charts;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther: Zealon
 * @Date: 2018-11-15 10:43
 */
@Controller
@RequestMapping("charts")
public class ChartsController {

    //
    @RequestMapping("/pie-nest")
    public String pieNest(){
      return "charts/pie-nest";
    }
}
