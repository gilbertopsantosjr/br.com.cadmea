package br.com.cadmea.model.orm;

import br.com.cadmea.model.BaseEntityPersistent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "cadmea_pictures")
@AttributeOverrides(@AttributeOverride(name = "id",
        column = @Column(name = "pic_id", nullable = false)))
@EqualsAndHashCode(callSuper = false)
public class Media extends BaseEntityPersistent {

    @NotNull
    private String fileName;

    @NotNull
    private String filePath;


}
