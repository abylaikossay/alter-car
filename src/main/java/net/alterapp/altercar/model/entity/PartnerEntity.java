package net.alterapp.altercar.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.alterapp.altercar.model.entity.audits.AuditModel;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "partner")
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_partner",
        initialValue = 1,
        allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Partners table")
public class PartnerEntity extends AuditModel {

  @ApiModelProperty(notes = "Наименование")
  @Column(name = "name")
  private String name;

  @ApiModelProperty(notes = "Created by")
  @ManyToOne
  @JsonIgnore
  private AccountEntity createdBy;

  @ApiModelProperty(notes = "Link to the address of partner")
  @Column(name = "link")
  private String link;

  @ApiModelProperty(notes = "Email of partner")
  @Column(name = "email")
  private String email;

  @ApiModelProperty(notes = "Phone of partner")
  @Column(name = "phone")
  private String phone;

  @ApiModelProperty(notes = "Address of partner")
  @Column(name = "address")
  private String address;

  @ApiModelProperty(notes = "Visible")
  @Column(name = "visible")
  private Boolean visible = true;

  @ApiModelProperty(notes = "Description")
  @Column(name = "description", length = 1000)
  private String description;

  @ApiModelProperty(notes = "Логотив магазина партнера")
  @Column(name = "logo_url")
  private String logoUrl;

  @ApiModelProperty(notes = "QR image path")
  @Column(name = "qr_path")
  private String qrPath;

  @ApiModelProperty(notes = "Beginning of push from this brand")
  @Column(name = "push_text")
  private String pushText;


}

