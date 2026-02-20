package gimnasios.com.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="afiliadoId")
public class AfiliadoCorporativo extends Afiliado{
 private String nombreEmpresa;
 private String cuit;
}
