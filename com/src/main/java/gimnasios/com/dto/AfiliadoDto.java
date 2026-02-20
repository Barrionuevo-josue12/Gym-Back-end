package gimnasios.com.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter @Getter
@NoArgsConstructor
public class AfiliadoDto {

    private Long afiliadoId;
    private String nombreCompleto;
    private String email;
    private String dni;
    private String fechaNacimiento;
    private Long sucursalId;

    @Override
    public String toString() {
        return "AfiliadoDto{" +
                "afiliadoId=" + afiliadoId +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", email='" + email + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", sucursalId=" + sucursalId +
                '}';
    }
}
