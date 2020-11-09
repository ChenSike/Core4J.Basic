package ikcoder.services.services;

import ikcoder.services.entity.DTO.coredb_basic.DTO$message;
import ikcoder.services.mappers.Mapper$messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Services$server {

    private Mapper$messages _mapper$messages;

    private String code_running = "1001";
    private String code_suspended = "1002";
    private String code_stop = "1003";

    @Autowired
    public Services$server(Mapper$messages mapper$messages)
    {
        this._mapper$messages = mapper$messages;
    }

    public HashMap<String,String> GetStatus()
    {
        HashMap<String,String> result=new HashMap<>();
        try {

            DTO$message dto$message = _mapper$messages.Select$message$code(code_running);
            if (dto$message != null) {
                result.put("status", dto$message.getMessage());
                result.put("code", dto$message.getMessage());
            } else {
                result.put("status", "stop");
                result.put("code", code_stop);
            }
        }
        catch (Exception err)
        {
            result.put("status", "stop");
            result.put("code", code_stop);
            result.put("msg",err.getMessage());
        }
        finally {
            return result;
        }
    }

}
