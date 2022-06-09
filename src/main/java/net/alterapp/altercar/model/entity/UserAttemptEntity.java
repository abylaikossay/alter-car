package net.alterapp.altercar.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.alterapp.altercar.model.entity.audits.AuditModel;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_attempts")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_user_attempts",
        initialValue = 1,
        allocationSize = 1)
@ApiModel(description = "User attempts table")
public class UserAttemptEntity extends AuditModel {

    @ApiModelProperty(notes = "Зарегистрированный пользователь")
    @ManyToOne
    private AccountEntity accountEntity;

    @ApiModelProperty(notes = "Новый пользователь")
    @ManyToOne
    private UserTemplateEntity userTemplate;

    @ApiModelProperty(notes = "Количество попыток регистрация через смс")
    @Column(name = "registration_attempts")
    private Integer registrationAttempts;

}
