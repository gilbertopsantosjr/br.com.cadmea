package br.com.cadmea.model.orm;

import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;
import br.com.cadmea.model.BaseEntityPersistent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Gilberto Santos
 */
@Entity
@Data
@Table(name = "person_member")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "pes_id", nullable = false)))
@EqualsAndHashCode(callSuper = false)
public class Person extends BaseEntityPersistent {

    public Person(){
        super();
    }

    public Person(final String name) {
        super();
    }

    // can't have whitespace
    @NotEmpty
    @Column(nullable = false, length = 35, name = "name")
    private String name;

    @NotEmpty
    @Column(nullable = false, length = 250, name = "surname")
    private String surname;

    //personal private number
    @Column(nullable = false, length = 50, name = "serial_person_number")
    private String register;

    @NotNull
    @Enumerated
    @Column(nullable = false, length = 1, name = "gender")
    private Gender gender;

    @Enumerated
    @Column(length = 1, name = "relationship")
    private Relationship relationship;

    @NotNull
    @Column(nullable = false, name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, targetEntity = Passport.class)
    @JoinTable(name = "person_per_passport", joinColumns = {
            @JoinColumn(name = "pes_id")}, inverseJoinColumns = {@JoinColumn(name = "pas_id")})
    private Set<Passport> passports;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, targetEntity = Phone.class)
    @JoinTable(name = "person_member_phone", joinColumns = {
            @JoinColumn(name = "pes_id")}, inverseJoinColumns = {@JoinColumn(name = "pho_id")})
    private Set<Phone> phones;


}
