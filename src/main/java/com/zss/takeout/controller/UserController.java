package com.zss.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zss.takeout.common.R;
import com.zss.takeout.entity.User;
import com.zss.takeout.service.UserService;
import com.zss.takeout.utils.SMSUtils;
import com.zss.takeout.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 发送短信验证码（模拟）
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获得手机号
        String phone = user.getPhone();

        if(StringUtils.isNotEmpty(phone)) {
            //生成随机四位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
            //调用阿里云提供短信服务发送短信
            SMSUtils.sendMessage("阿里云短信测试","SMS_154950909",phone,code);
            //将生成验证码放入session
            session.setAttribute(phone,code);

            return R.success("验证码发送成功！");
        }
        return R.error("验证码发送失败！");
    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());

        //读取手机号
        String phone = map.get("phone").toString();
        //读取验证码
        String code = map.get("code").toString();
        //从session中获取保存验证码
        Object codeInSession = session.getAttribute(phone);
        //进行验证码的比对
        if(codeInSession != null && codeInSession.equals(code)){
            //如果成功，登录
            //判断手机号是否为新用户
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if(user == null){
                //是，加入数据库自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        //失败，跳转login页面
        return R.error("登录失败！");
    }
}
