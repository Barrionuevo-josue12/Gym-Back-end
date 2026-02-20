package gimnasios.com.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter @Getter
@SuperBuilder
public class AfiliadoIndependienteDto extends AfiliadoDto{
    private Boolean aptoFisico;
    private String telefono;
}
