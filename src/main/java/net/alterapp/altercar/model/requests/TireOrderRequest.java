package net.alterapp.altercar.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TireOrderRequest {
  @NotEmpty
  private List<TireOrderProductRequest> tireOrderProductRequests;
  private String comment;
}
