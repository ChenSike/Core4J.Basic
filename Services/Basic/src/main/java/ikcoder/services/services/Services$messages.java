package ikcoder.services.services;

import ikcoder.services.entity.DTO.coredb_basic.DTO$message;
import ikcoder.services.mappers.Mapper$messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Services$messages {

    private Mapper$messages _mapper$messages;

    @Autowired
    public Services$messages(Mapper$messages _mapper$messages) {
        this._mapper$messages = _mapper$messages;
    }

    public DTO$message GetMessage$code(String code)
    {
        return _mapper$messages.Select$message$code(code);
    }

}
