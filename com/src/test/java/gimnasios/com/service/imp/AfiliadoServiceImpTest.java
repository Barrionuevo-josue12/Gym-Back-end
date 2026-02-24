package gimnasios.com.service.imp;

import gimnasios.com.domain.Afiliado;
import gimnasios.com.domain.AfiliadoCorporativo;
import gimnasios.com.domain.Sucursal;
import gimnasios.com.dto.AfiliadoCorporativoDto;
import gimnasios.com.mapper.AfiliadoMapper;
import gimnasios.com.repository.AfiliadoRepository;
import gimnasios.com.repository.SucursalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AfiliadoServiceImpTest {

    @Mock
    AfiliadoRepository afiliadoRepository;
    @Mock
    SucursalRepository sucursalRepository;
    @Mock
    AfiliadoMapper afiliadoMapper;

    @InjectMocks
    AfiliadoServiceImp afiliadoServiceImp;

    private Sucursal suc;
    private AfiliadoCorporativo afiCorp;
    private AfiliadoCorporativoDto dto;

    @BeforeEach
    void setUp(){
        suc = Sucursal.builder()
                .horarioApertura(LocalDateTime.now())
                .idSucursal(1L)
                .nombreSede("Elite Gym").build();
        dto = AfiliadoCorporativoDto.builder()
                .cuit("33434")
                .sucursalId(suc.getIdSucursal())
                .afiliadoId(1L)
                .nombreEmpresa("Red Hat")
                .dni("4545")
                .email("pepe@gmail.com")
                .fechaNacimiento("21-05-04")
                .nombreCompleto("pepe ramirez").build();

        afiCorp = AfiliadoCorporativo.builder().cuit("33434")
                .afiliadoId(1L)
                .sucursal(suc)
                .nombreEmpresa("Red Hat")
                .dni("4545")
                .email("pepe@gmail.com")
                .nombreCompleto("pepe ramirez").build();

    }

    @Test
    @DisplayName("Crear un afiliado corporativo test")
    public void crearAfiliadoTest(){
        // --  given
        when(sucursalRepository.findById(suc.getIdSucursal())).thenReturn(Optional.of(suc));
        when(afiliadoMapper.toAfiliadoCorporativoEntity(any(AfiliadoCorporativoDto.class))).thenReturn(afiCorp);
        when(afiliadoRepository.save(any(Afiliado.class))).thenReturn(afiCorp);
        when(afiliadoMapper.toAfiliadoCorporativoDto(any(AfiliadoCorporativo.class))).thenReturn(dto);

        //

        AfiliadoCorporativoDto dtoReceived = afiliadoServiceImp.crearAfiliadoCorporativo(dto);

        assertThat(dtoReceived).isNotNull();
        assertThat(dtoReceived.getAfiliadoId()).isSameAs(dto.getAfiliadoId());
        assertThat(dtoReceived.getEmail()).isSameAs(dto.getEmail());

        //verify
        verify(sucursalRepository).findById(suc.getIdSucursal());
        verify(afiliadoMapper).toAfiliadoCorporativoEntity(any(AfiliadoCorporativoDto.class));
        verify(afiliadoRepository).save(afiCorp);
        verify(afiliadoMapper).toAfiliadoCorporativoDto(afiCorp);
    }
}