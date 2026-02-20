package gimnasios.com.dto;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class AfiliadoCorporativoDto extends AfiliadoDto{
    private String nombreEmpresa;
    private String cuit;
}
