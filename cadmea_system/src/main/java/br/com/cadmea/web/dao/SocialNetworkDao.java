package br.com.cadmea.web.dao;

import br.com.cadmea.model.dao.DaoGenerico;
import br.com.cadmea.model.orm.SocialNetwork;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SocialNetworkDao extends DaoGenerico<SocialNetwork> {
}