package ikcoder.services.mappers;

import ikcoder.services.entity.DTI.coredb_basic.DTI$users;
import ikcoder.services.entity.DTO.coredb_basic.DTO$users;
import org.apache.ibatis.annotations.Mapper;


public interface Mapper$users {

    public DTO$users Select$users$id(int id);

    public DTO$users Select$users$username(String username);

    public void Insert$users(DTI$users dti$users);

    public void Update$users$pwd(int id,String pwd);

    public void Update$users$status(int id,String status);

    public void Delete$users$id(int id);

    public void Delete$users$uername(String username);

}
