package gimnasios.com.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
public class AfiliadoIndependienteRequestDto extends AfiliadoRequestDto{
    private Boolean aptoFisico;
    private String telefono;

    @Override
    public String toString() {
        return super.toString()+
                "aptoFisico=" + aptoFisico +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
