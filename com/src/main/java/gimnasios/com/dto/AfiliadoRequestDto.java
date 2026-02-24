package gimnasios.com.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
public class AfiliadoRequestDto {
    private String nombreCompleto;
    private String email;
    private String fechaNacimiento;
    private Long sucursalId;

    @Override
    public String toString() {
        return "AfiliadoRequestDto{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", email='" + email + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", sucursalId=" + sucursalId +
                '}';
    }
}
