package com.music.cms.controller;

import com.music.cms.FlashMessage;
import com.music.cms.model.User;
import com.music.cms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model)
    {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users",users);
        model.addAttribute("pageTitle","User Management");
        return "backend/user/index";
    }

    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public String create(ModelMap model)
    {
        model.addAttribute("user",new User());
        model.addAttribute("button","Add");
        model.addAttribute("pageTitle","Add User");
        model.addAttribute("url",String.format("/admin/user/store"));

        return "backend/user/form";
    }


    @RequestMapping(value = "/store",method = RequestMethod.POST)
    public String store(@Valid User user, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) throws Exception
    {
        if(result.hasErrors())
        {
            return "backend/user/form";
        }
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("flash",new FlashMessage("User added successfully!",FlashMessage.Status.SUCCESS));
        return "redirect:/admin/user";

    }

    @RequestMapping(value = "/{id}/edit",method = RequestMethod.GET)
    public String edit(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, ModelMap model)
    {
        User user = userService.findById(id);
        if(user == null)
        {
            redirectAttributes.addFlashAttribute("flash",new FlashMessage("User not found!",FlashMessage.Status.DANGER));
            return  "redirect:/admin/user";

        }
        model.addAttribute("user",user);
        model.addAttribute("button","Update");
        model.addAttribute("pageTitle","Edit User");
        model.addAttribute("url",String.format("/admin/user/%s/update",id));

        return "backend/user/form";

    }


    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(@PathVariable("id") Integer id,@Valid User user, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes) throws Exception
    {
        if(result.hasErrors())
        {
            model.addAttribute("button","Update");
            model.addAttribute("pageTitle","Edit User");
            model.addAttribute("url",String.format("/admin/user/%s/update",id));
            return "backend/user/form";
        }

        User userCheck = userService.findById(id);
        if(userCheck == null)
        {
            redirectAttributes.addFlashAttribute("flash",new FlashMessage("User not found!",FlashMessage.Status.DANGER));
            return  "redirect:/admin/user";

        }
        userService.update(user);
        redirectAttributes.addFlashAttribute("flash",new FlashMessage("User successfully updated!",FlashMessage.Status.DANGER));
        return  "redirect:/admin/user";

    }



}