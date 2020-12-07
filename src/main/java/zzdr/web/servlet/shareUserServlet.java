package zzdr.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import zzdr.domain.ResultInfo;
import zzdr.domain.SharedUser;
import zzdr.service.ShareUserService;
import zzdr.service.UserService;
import zzdr.service.impl.ShareUserServiceImpl;
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

@WebServlet("/shareUserServlet")
public class shareUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        Map<String, String[]> map = request.getParameterMap();

        //封装对象
        SharedUser sharedUser = new SharedUser();
        try {
            BeanUtils.populate(sharedUser, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession(false);
        sharedUser.setUsername((String) session.getAttribute("username"));
        sharedUser.setTelephone((String) session.getAttribute("telephone"));
        sharedUser.setEmail((String) session.getAttribute("email"));
//        System.out.println("longitude+++"+sharedUser.getLongitude());
//        System.out.println("Latitude+++"+sharedUser.getLatitude());
//        System.out.println("UsbType+++"+sharedUser.getUsbType());
//        System.out.println("");

        //调用Service完成注册
        ShareUserService service = new ShareUserServiceImpl();
        boolean flag = service.saveLocation(sharedUser);
//        System.out.println(sharedUser);
        //实例化封装返回的提示信息类
        ResultInfo info = new ResultInfo();

        //响应结果
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("已经在分享中了，耐心等待被人选中吧");
        }

        //将info对象序列化为json对象
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
