package com.mmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.model.LayUIResult;
import com.mmall.model.SkinType;
import com.mmall.service.LearnBookService;
import com.mmall.service.SkinTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/skinType")
public class SkinTypeController {
    @Autowired
    private LearnBookService learnBookService;
    @Autowired
    private SkinTypeService  skinTypeService;
    /**
     * 获取银行卡列表
     * @param curr
     * @param limit
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/selAll")
    @ResponseBody
    public LayUIResult getAllLearnBook(@RequestParam(value = "page", defaultValue = "1") Integer curr,
                                       @RequestParam(value = "limit",defaultValue = "10") Integer limit,
                                       Model model, HttpServletRequest request){

        LayUIResult layUIResult=new LayUIResult();
        try {
            String param=request.getParameter("keyword");
            log.info("皮肤类型列表"+param);
            PageHelper.startPage(curr,limit);
            List<SkinType> details= skinTypeService.getAllSkinType(param);
            log.info("details="+details.size());
            PageInfo pageInfo=new PageInfo(details);
            layUIResult.setCode(0);
            layUIResult.setMsg("获取成功");
            layUIResult.setData(details);
            layUIResult.setCount(pageInfo.getTotal());
        }catch (Exception e){
            e.printStackTrace();
            layUIResult.setCode(1);
            layUIResult.setMsg("获取失败！");
        }
            log.info("结果"+layUIResult);
            return layUIResult;
    }

    /**
     * 删除教材
     * @param id
     * @return
     */
    @RequestMapping("/deleteByKey")
    @ResponseBody
    public LayUIResult deleteByKey(@RequestParam("id")Integer id){
        LayUIResult layUIResult= new LayUIResult();
        //删除
        log.info("删除皮肤id="+id);
        int num=skinTypeService.deleteByPrimaryKey(id);
        if(num>0){
            layUIResult.setMsg("删除成功！");
            layUIResult.setCode(0);
        }else{
            layUIResult.setMsg("删除失败！");
            layUIResult.setCode(1);
        }
        log.info("结果"+layUIResult);
        return  layUIResult;
    }
    /**
     * 查询
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/selectByKey")
    public String selectByKey(@RequestParam("id")Integer id,Model model){
        log.info("查询--------selectByKey"+id);
        SkinType skinType = skinTypeService.selectByPrimaryKey(id);
        model.addAttribute("skinType",skinType);
        return "pages/updateSkinType";
    }


    /**
     * 添加
     * @param request
     * @return
     */
    @RequestMapping("/add")
    public String add(HttpServletRequest request){
        String name = request.getParameter("name");
        SkinType newSkinType=new SkinType();
        newSkinType.setName(name);
        System.out.println("新增的广告信息:"+newSkinType.toString());
        skinTypeService.insertSelective(newSkinType);
        return  "redirect:/get/skinType.page";
    }

    /**
     * 修改
     * @param request
     * @return
     */
    @RequestMapping("/update")
    public String updateLearn(HttpServletRequest request){
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        SkinType  newskinType = new SkinType();
        newskinType.setId(Integer.parseInt(id));
        newskinType.setName(name);
        log.info("修改广告="+id);

        skinTypeService.updateByPrimaryKeySelective(newskinType);
        log.info("修改成功="+id);
        return  "redirect:/get/skinType.page";
    }

}
