package gimnasios.com.repository;

import gimnasios.com.domain.AfiliadoCorporativo;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

@DataJpaTest
public class AfiliadoRepositoryTest {
    @Inject
    private AfiliadoRepository afiliadoRepository;
    private AfiliadoCorporativo afi;

    @BeforeEach
    public void setUp(){
        afi = AfiliadoCorporativo.builder()
                .email("pepe@gmail.com")
                .cuit("2034567")
                .dni("4537654")
                .nombreEmpresa("Red Hat")
                .fechaNacimiento(LocalDate.now())
                .nombreCompleto("Juan Perez")
                .sucursal(null)
                .build();
    }
    @Test
    @DisplayName("Crear un afiliadoTest")
    public void crearAfiliadoCorp(){
        afi = afiliadoRepository.save(afi);
        Boolean creado = afiliadoRepository.existsById(afi.getAfiliadoId());
        assertThat(creado).isTrue();
    }
}
