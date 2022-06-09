package net.alterapp.altercar.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TireOrderProductRequest {
  private Long tireId;
  private Integer amount;
}
