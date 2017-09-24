/**
 *
 */
package br.com.cadmea.model.orm;

import br.com.cadmea.model.BaseEntityPersistent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Gilberto Santos
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "cadmea_country")
@AttributeOverrides(@AttributeOverride(name = "id",
        column = @Column(name = "pai_id", nullable = false)))
public class Country extends BaseEntityPersistent {

    @Min(3)
    @Max(250)
    @Column(nullable = true, name = "con_name", length = 255, unique = true)
    private String name;

    @Min(3)
    @Max(25)
    @Column(nullable = false, name = "con_language", length = 25)
    private String language;

    @Min(3)
    @Max(5)
    @Column(nullable = true, name = "co_code", length = 5)
    private String code;


}
