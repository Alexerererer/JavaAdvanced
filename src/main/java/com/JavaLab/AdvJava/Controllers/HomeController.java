package com.JavaLab.AdvJava.Controllers;

import com.JavaLab.AdvJava.models.RztkGood;
import com.JavaLab.AdvJava.models.RztkGoodsRepository;
import com.JavaLab.AdvJava.service.ParserService;
import com.JavaLab.AdvJava.service.PrivatCallerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

//Primary view controller
@Controller
public class HomeController {

    //Automatically get references to db and services via Autowired notion
    @Autowired
    private RztkGoodsRepository rztkGoodsRepository;

    @Autowired
    private ParserService parserService;

    @Autowired
    private PrivatCallerService privatCallerService;

    //Root path - home page
    @GetMapping("/")
    public String homePage(Model model) {
        List<RztkGood> rztkGoods = new ArrayList<>();
        //Code for getting data from H2 db
        for(RztkGood good : rztkGoodsRepository.findAll())
        {
            rztkGoods.add(good);
        }
        //Code for feeding data to thymeleaf for page building
        model.addAttribute("items",rztkGoods);
        return "home";
    }

    //Parse endpoint - for initiating parser service; Is in regular Controller because it returns a view(home page)
    @PostMapping("/parse")
    public String startParsing(@RequestParam String url, HttpServletRequest request)
    {
        List<Float> exchangeRates = privatCallerService.makeRestCall();
        parserService.parseAndSave(url,exchangeRates);

        return "redirect:/";
    }
}
