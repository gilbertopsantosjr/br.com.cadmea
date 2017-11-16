package br.com.cadmea.dto.user;

import br.com.cadmea.comuns.dto.Response;
import br.com.cadmea.model.orm.UserSystem;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserSystemResponse implements Response<UserSystem> {

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
        try {
            BeanUtils.copyProperties(user, entity);
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            e.printStackTrace();
        }
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
