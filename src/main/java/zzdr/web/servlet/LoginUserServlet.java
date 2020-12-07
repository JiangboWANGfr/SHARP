package zzdr.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.beanutils.BeanUtils;
import zzdr.domain.ResultInfo;
import zzdr.domain.User;
import zzdr.service.UserService;
import zzdr.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


@WebServlet("/loginUserServlet")
public class LoginUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码校验
        String verifycode = request.getParameter("verifycode");

        //从Session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");

        session.removeAttribute("CHECKCODE_SERVER"); //保证验证码只能使用一次
        //校验逻辑代码
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(verifycode)) {
            ResultInfo info = new ResultInfo();
            //失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        //获取用户和密码
        Map<String, String[]> map = request.getParameterMap();

        //封装用户信息
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service = new UserServiceImpl();
        User u = service.login(user);
        ResultInfo info = new ResultInfo();

        //判断用户对象是否为null
        if (u == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }else{
            //将数据储存到session对象中
            session.setAttribute("username",u.getUsername());
            session.setAttribute("telephone",u.getTelephone());
            session.setAttribute("email",u.getEmail());
//            System.out.println("loginGetUsername----"+u.getUsername());
//            System.out.println("loginGetEmail----"+u.getEmail());
//            int borrowNumber = u.getBorrowNumber();
//            int landNumber = u.getLandNumber();
            info.setFlag(true);
            info.setData(u);
        }

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}