package gimnasios.com.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter @Getter
@AllArgsConstructor
public class SucursalDto {
    private  Long idSucursal;
    private String horarioApertura;
    private String nombreSede;
}
