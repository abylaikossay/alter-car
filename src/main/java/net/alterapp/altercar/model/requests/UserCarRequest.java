package net.alterapp.altercar.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCarRequest {

  private Long carBrandId;
  private Long carModelId;
  private Integer carYear;
  private Double engineVolume;
  private String vinCode;
  private String color;
}
