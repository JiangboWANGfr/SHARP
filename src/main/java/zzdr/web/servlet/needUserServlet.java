package zzdr.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import zzdr.domain.NeedUser;
import zzdr.domain.ResultInfo;
import zzdr.domain.SharedUser;
import zzdr.service.NeedUserService;
import zzdr.service.ShareUserService;
import zzdr.service.impl.NeedUserServiceImpl;
import zzdr.service.impl.ShareUserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/needUserServlet")
public class needUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        Map<String, String[]> map = request.getParameterMap();

        //封装对象
//        SharedUser sharedUser = new SharedUser();
        NeedUser needUser = new NeedUser();

        try {
            BeanUtils.populate(needUser, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession(false);
        needUser.setUsername((String) session.getAttribute("username"));
//        needUser.setTelephone((String) session.getAttribute("telephone"));
//        needUser.setEmail((String) session.getAttribute("email"));
//        System.out.println("longitude+++"+sharedUser.getLongitude());
//        System.out.println("Latitude+++"+sharedUser.getLatitude());
//        System.out.println("UsbType+++"+sharedUser.getUsbType());


        //调用Service完成注册
        NeedUserService service = new NeedUserServiceImpl();
        String needUsername = needUser.getUsername();
        SharedUser needuser = service.findByShareUsername(needUsername);
//        System.out.println("getUsername"+needUser.getUsername());

        //向session中保存数据之后在need页面中展示

        //实例化封装返回的提示信息类
        ResultInfo info = new ResultInfo();
        if(needuser != null){
            info.setFlag((false));
            info.setErrorMsg("已经在分享中了，不能获取哦");
        } else{
            //响应结果
            boolean ifUserCanBorrow = service.IfUserCanBorrow(needUser.getUsername());
            if(ifUserCanBorrow){
                SharedUser sharedUser = service.GetUserforHelp(needUser);
                if (sharedUser != null) {
                    info.setFlag(true);
                    info.setData(sharedUser);
                } else {
                    info.setFlag(false);
                    info.setErrorMsg("附近没有人在分享之中，或该数据线没有用户在分享，没有找到你的天选之人");
                }
            }else{
                info.setFlag(false);
                info.setErrorMsg("已经获取太多次了，该是分享的时候了哦");
            }

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
