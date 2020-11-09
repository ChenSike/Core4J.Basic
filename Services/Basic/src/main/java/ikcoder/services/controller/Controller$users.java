package ikcoder.services.controller;

import ikcoder.services.entity.DTI.coredb_basic.DTI$users;
import ikcoder.services.entity.DTO.coredb_basic.DTO$message;
import ikcoder.services.conf.*;
import ikcoder.services.services.Services$messages;
import ikcoder.services.services.Services$users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.HttpCookie;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class Controller$users {

    Services$users _services$users;
    Services$messages _services$messages;
    StringRedisTemplate _redisTemplate;
    HttpServletRequest _httpServletRequest;
    HttpServletResponse _httpServletResponse;

    @Autowired
    public Controller$users(Services$users _services$users, Services$messages _services$messages, StringRedisTemplate _redisTemplate, HttpServletRequest _httpServletRequest, HttpServletResponse _httpServletResponse) {
        this._services$users = _services$users;
        this._services$messages = _services$messages;
        this._redisTemplate = _redisTemplate;
        this._httpServletRequest = _httpServletRequest;
        this._httpServletResponse = _httpServletResponse;
    }

    @ResponseBody
    @PostMapping("/users")
    public DTO$message NewUser(@RequestBody DTI$users newUser)
    {
        if(_services$users.NewUser(newUser)) {
            return _services$messages.GetMessage$code("8001");
        }
        else {
            return _services$messages.GetMessage$code("4001");
        }
    }

    @ResponseBody
    @PostMapping("/users/login")
    public DTO$message Login(@RequestBody DTI$users dti$users)
    {
        DTO$message dto$message=new DTO$message();
        int returnCode = _services$users.VerifyUser(dti$users);
        switch (returnCode)
        {
            case 0:
                return _services$messages.GetMessage$code("4002");
            case 1:
                HttpSession httpSession = this._httpServletRequest.getSession();
                UUID uuid = UUID.randomUUID();
                httpSession.setAttribute(Conf$symbol.symbol_token,uuid.toString());
                this._httpServletResponse.addHeader("token",uuid.toString());
                this._redisTemplate.opsForValue().set(dti$users.getUsername(),uuid.toString(),Conf$common.timeout_session, TimeUnit.SECONDS);
                return _services$messages.GetMessage$code("8002");
            case 2:
                return _services$messages.GetMessage$code("4003");
            case 3:
                return _services$messages.GetMessage$code("4004");
        }
        return dto$message;
    }


    @ResponseBody
    @GetMapping("/users/verify")
    public DTO$message Verify()
    {
        HttpSession httpSession = this._httpServletRequest.getSession();
        if(this._httpServletRequest.getHeader("username")==null)
        {
            return this._services$messages.GetMessage$code("4007");
        }
        if(this._httpServletRequest.getHeader("token")==null)
        {
            return this._services$messages.GetMessage$code("4008");
        }
        String username = this._httpServletRequest.getHeader("username");
        String token = this._httpServletRequest.getHeader("token");
        String token_redis = this._redisTemplate.opsForValue().get(username);
        if(token_redis.equals(token))
        {
            return _services$messages.GetMessage$code("8004");
        }
        else
        {
            return _services$messages.GetMessage$code("4006");
        }
    }



    @ResponseBody
    @GetMapping("/users/logout")
    public DTO$message Logout()
    {
        DTO$message dto$message=new DTO$message();
        HttpSession httpSession = this._httpServletRequest.getSession();
        String sessionID = httpSession.getId();
        if(this._redisTemplate.opsForValue().get(sessionID)==sessionID)
        {
            httpSession.removeAttribute(Conf$symbol.symbol_token);
            this._redisTemplate.delete(sessionID);
            dto$message = _services$messages.GetMessage$code("8003");
        }
        else
        {
            dto$message = _services$messages.GetMessage$code("4005");
        }
        return dto$message;
    }

}
