package gimnasios.com.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter @Getter
@AllArgsConstructor
public class AfiliadoDto {

    private Long afiliadoId;
    private String nombreCompleto;
    private String email;
    private String dni;
    private String fechaNacimiento;
    private Long sucursalId;
}
