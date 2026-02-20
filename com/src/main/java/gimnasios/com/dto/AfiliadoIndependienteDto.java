package gimnasios.com.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter @Getter
@SuperBuilder
@NoArgsConstructor
public class AfiliadoIndependienteDto extends AfiliadoDto{
    private Boolean aptoFisico;
    private String telefono;

    @Override
    public String toString() {
        return "AfiliadoIndependienteDto{" +
                "aptoFisico=" + aptoFisico +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
