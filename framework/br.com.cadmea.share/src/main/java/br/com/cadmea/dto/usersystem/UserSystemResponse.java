package br.com.cadmea.dto.usersystem;

import br.com.cadmea.comuns.dto.Response;
import br.com.cadmea.model.orm.UserSystem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserSystemResponse extends Response<UserSystem> {

    private List<UserSystem> entities;
    private UserSystem user;
    private String locale;
    private String message;

    @Override
    public List<UserSystem> getEntities() {
        return new ArrayList<>();
    }

    @Override
    public void setEntity(final UserSystem entity) {
        user = entity;
    }

    @Override
    public void clear() {

    }

    @Override
    public UserSystem getEntity() {
        return user;
    }


}
