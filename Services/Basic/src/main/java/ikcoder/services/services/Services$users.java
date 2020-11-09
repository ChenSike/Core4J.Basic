package ikcoder.services.services;
import ikcoder.services.entity.DTI.coredb_basic.DTI$users;
import ikcoder.services.entity.DTO.coredb_basic.DTO$users;
import ikcoder.services.mappers.Mapper$messages;
import ikcoder.services.mappers.Mapper$users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Services$users {

    private Mapper$messages _mappers$message;
    private Mapper$users _mapper$users;

    @Autowired
    public Services$users(Mapper$messages mapper$messages,Mapper$users mapper$users) {
        this._mapper$users = mapper$users;
        this._mappers$message = mapper$messages;
    }

    public boolean NewUser(DTI$users dt$users)
    {
        if(dt$users==null)
            return false;
        else
        {
            DTO$users dto$users = _mapper$users.Select$users$username(dt$users.getUsername());
            if(dto$users==null) {
                _mapper$users.Insert$users(dt$users);
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public int VerifyUser(DTI$users dt$users)
    {
        if(dt$users==null)
            return 0;
        else
        {
            DTO$users dto$users = _mapper$users.Select$users$username(dt$users.getUsername());
            if(dto$users!=null) {
                if (dt$users.getPwd().equals(dto$users.getPwd()))
                    return 1;
                else
                    return 2;
            }
            else
            {
                return 3;
            }
        }
    }

}
