package com.itcast.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itcast.dao.HrMapper;
import com.itcast.util.Result;
import com.itcast.util.ResultEnum;
import com.itcast.vo.HrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/6/1 - 14:02
 */
@Controller
@RequestMapping("/api")
@CrossOrigin
public class ApiController {

    @Autowired
    private HrMapper hrMapper;

    @GetMapping("/list")
    @ResponseBody
    public Result list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<HrVo> list = hrMapper.list();
        PageInfo<HrVo> pageInfo = new PageInfo<>(list);
        return Result.success(pageInfo);
    }

    /**
     * 新增数据
     *
     * @param hrVo
     */
    @PostMapping("/post")
    @ResponseBody
    public Result post(@RequestBody HrVo hrVo) {
        return hrMapper.insert(hrVo) == 1 ? Result.success() : Result.failure(ResultEnum.FAILURE);
    }

    /**
     * 编辑数据
     */
    @GetMapping("/getHrInfo/{id}")
    @ResponseBody
    public Result getHrInfo(@PathVariable Long id) {
        return Result.success(hrMapper.get(id));
    }

    /**
     * 更新数据
     *
     * @param hrVo
     * @return
     */
    @PutMapping("/put")
    @ResponseBody
    public Result put(HrVo hrVo) {
        return hrMapper.update(hrVo) == 1 ? Result.success() : Result.failure(ResultEnum.FAILURE);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable("id") Long id) {
        int delete = 0;
        try {
            delete = hrMapper.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return delete == 1 ? Result.success() : Result.failure(ResultEnum.FAILURE);
    }

}
