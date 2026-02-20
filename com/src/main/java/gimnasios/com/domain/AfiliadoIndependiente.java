package gimnasios.com.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(referencedColumnName="afiliadoId")
public class AfiliadoIndependiente  extends Afiliado{
    private Boolean aptoFisico;
    private String telefono;

    @Override
    public String toString() {
        return "AfiliadoIndependiente{" +
                "aptoFisico=" + aptoFisico +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
