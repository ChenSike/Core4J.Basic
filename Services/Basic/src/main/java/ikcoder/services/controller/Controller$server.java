package ikcoder.services.controller;

import ikcoder.services.services.Services$server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller$server {

    private Services$server _services$server;

    @Autowired
    public Controller$server(Services$server services$server) {
        this._services$server = services$server;
    }

    @GetMapping("/server/status")
    @ResponseBody
    public HashMap<String,String> ServerStatus()
    {
        return _services$server.GetStatus();
    }


}
