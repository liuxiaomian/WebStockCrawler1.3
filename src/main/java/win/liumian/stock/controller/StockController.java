package win.liumian.stock.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import win.liumian.stock.service.DataAdjustService;
import win.liumian.stock.service.DataParserService;
import win.liumian.stock.util.CodeSingleton;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liumian on 16/8/9.
 */
@Controller
public class StockController {

    private static Logger logger = Logger.getLogger(StockController.class);

    @Autowired
    private DataParserService parserService;

    @Autowired
    private DataAdjustService adjustService;

    @Autowired
    private CodeSingleton codeSingleton;


    @RequestMapping(value = "parse",produces = "application/json;charset=UTF-8",method = RequestMethod.GET)
    public String parseTodayData(@RequestParam("autoFlush") boolean autoFlush,
                                 @RequestParam("limit") int limit,
                                 @RequestParam("rate") int rate,
                                 Model model){

        long start = System.currentTimeMillis();

        JSONArray stockArray = parserService.parseCurrentData(limit);

        long end = System.currentTimeMillis();
        logger.info("consume time: "+ (end - start));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间

        model.addAttribute("stockArray",stockArray);
        model.addAttribute("time",time);
        model.addAttribute("consume",(end - start));
        model.addAttribute("status",0);
        model.addAttribute("rate",rate);

        if(autoFlush){
            return "parseAutoFlush";

        }else {
            return "parseNonFlush";
        }
    }

    @RequestMapping(value = "parse/{fileName}",produces = "application/json;charset=UTF-8",method = RequestMethod.GET)
    public String parseOtherData(   @PathVariable String fileName, Model model){
        long start = System.currentTimeMillis();
        JSONArray stockArray = parserService.parseData(fileName);
        long end = System.currentTimeMillis();
        logger.info("consume time: "+ (end - start));

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间

        model.addAttribute("stockArray",stockArray);
        model.addAttribute("time",time);
        model.addAttribute("consume",(end - start));
        model.addAttribute("start",0);

        return "parseNonFlush";

    }

    @RequestMapping(value = "code/list",produces = "application/json;charset=UTF-8",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getAllCode(){
        JSONObject result = new JSONObject();
        String[] codes = codeSingleton.getCodes();
        JSONArray codeArray = new JSONArray(codes.length);
        for (int i = 0; i < codes.length; i++) {
            codeArray.add(codes[i]);
        }
        result.put("status","0");
        result.put("codes",codeArray);
        return result;
    }

    @RequestMapping(value = "data/adjustment",produces = "application/json;charset=UTF-8",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject adjustData(){
        JSONObject result = new JSONObject();
        int status = adjustService.adjustMaxRatio();
        result.put("status",status);
        return result;
    }


}
