package gimnasios.com.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
public class AfiliadoCorporativoRequestDto extends AfiliadoRequestDto{
    private String nombreEmpresa;
    private String cuit;

    @Override
    public String toString() {
        return super.toString()+
                "nombreEmpresa='" + nombreEmpresa + '\'' +
                ", cuit='" + cuit + '\'' +
                '}';
    }
}
