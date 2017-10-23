package br.com.cadmea.web.business;

import br.com.cadmea.model.dao.DaoGenericoImp;
import br.com.cadmea.model.orm.PasswordResetToken;
import org.springframework.stereotype.Repository;

@Repository
class PasswordResetTokenDaoImp extends DaoGenericoImp<PasswordResetToken, Long> implements PasswordResetTokenDao {


}
