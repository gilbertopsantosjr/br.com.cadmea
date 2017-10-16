package br.com.cadmea.model.orm;

import br.com.cadmea.model.BaseEntityPersistent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "media")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "med_id", nullable = false)))
@EqualsAndHashCode(callSuper = false)
public class Media extends BaseEntityPersistent {

    @NotNull
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @NotNull
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "order_to_show")
    private int order;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "sys_id", referencedColumnName = "sys_id", nullable = false)
    private CadmeaSystem cadmeaSystem;

}
