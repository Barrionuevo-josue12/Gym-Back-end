package gimnasios.com.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter @Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(referencedColumnName="afiliadoId")
public class AfiliadoCorporativo extends Afiliado{
 private String nombreEmpresa;
 private String cuit;

 @Override
 public String toString() {
  return "AfiliadoCorporativo{" +
          "nombreEmpresa='" + nombreEmpresa + '\'' +
          ", cuit='" + cuit + '\'' +
          '}';
 }
}
