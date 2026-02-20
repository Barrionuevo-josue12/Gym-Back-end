package gimnasios.com.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter @Getter
@NoArgsConstructor
public class AfiliadoCorporativoDto extends AfiliadoDto{
    private String nombreEmpresa;
    private String cuit;

    @Override
    public String toString() {
        return "AfiliadoCorporativoDto{" +
                "nombreEmpresa='" + nombreEmpresa + '\'' +
                ", cuit='" + cuit + '\'' +
                '}';
    }
}
