package br.com.cadmea.dto.user;

import br.com.cadmea.comuns.dto.Response;
import br.com.cadmea.model.orm.UserSystem;

import java.util.ArrayList;
import java.util.List;

public class UserSystemResponse implements Response<UserSystem> {

    private List<UserSystem> entities;
    private UserSystem user;

    @Override
    public List<UserSystem> getEntities() {
        return new ArrayList<>();
    }

    @Override
    public void setEntity(final UserSystem entity) {

    }

    @Override
    public UserSystem getEntity() {
        return new UserSystem();
    }


}
