package cn.ciloudit.wangzihao.first.controller;

import cn.ciloudit.wangzihao.first.entity.User;
import cn.ciloudit.wangzihao.first.service.UserService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/toAdd")
     public String toAdd(){
         return "add";
     }
    @GetMapping("/getAll")
    public String getall(Model model) {
        List<User> users = userService.getAll();
        if (users != null) {
            model.addAttribute("users", users);
        }
        return "showall";
    }

    @GetMapping("/deleteUser")
    public String deleteUser() {
        userService.deleteUser(2);
        return "redirect:/getAll";
    }
   @PostMapping("/UpLoad")
    public String upload(MultipartFile multipartFile, HttpServletRequest request,User user) {
        String oldname = multipartFile.getOriginalFilename();
        String lastprefix = oldname.substring(oldname.lastIndexOf("."));
        String newname = UUID.randomUUID()  + lastprefix;
       String uploadpath = request.getServletContext().getRealPath("/resources/static/img/");
          File filepath=new File(uploadpath);
             if(!filepath.exists()) {
           filepath.mkdirs();
            }
       System.out.println("路径" + uploadpath);
        try {
            user.setImgname(newname);
            userService.addUser(user);
            multipartFile.transferTo(new File(uploadpath, newname));
        } catch (IOException e) {
            e.printStackTrace();
        }
         return "redirect:/getAll";
    }
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
   @RequestMapping("/mail")
    public String sendSimpleMail(){
        MimeMessage message = null;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("193041669@qq.com");
            helper.setTo("365842158@qq.com");
            helper.setSubject("Nature.:验证码：");
//            StringBuffer sb = new StringBuffer();
//            sb.append("<h1>王先生您好！</h1>")
//                   .append("<p style='color:#F00'>您在我行信用卡已透支，请尽快补交，不然你就爽歪歪</p>")
//                   .append("<a href=\"http://localhost:8080/toAdd\">邮件</a></br></br> ");
//           helper.setText(sb.toString(), true);
           Context context = new Context();
           context.setVariable("id","110");
            String content = templateEngine.process("showall",context);
           helper.setText(content,true);
            FileSystemResource fileSystemResource=new FileSystemResource(new File("C:\\Users\\王自豪\\Pictures\\Saved Pictures\\11.png"));
            helper.addAttachment("11.jpg",fileSystemResource);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
       return "redirect:/getAll";
    }

}
