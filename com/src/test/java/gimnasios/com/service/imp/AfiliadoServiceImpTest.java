package gimnasios.com.service.imp;

import gimnasios.com.domain.Afiliado;
import gimnasios.com.domain.AfiliadoCorporativo;
import gimnasios.com.domain.Sucursal;
import gimnasios.com.dto.AfiliadoCorporativoDto;
import gimnasios.com.dto.AfiliadoCorporativoRequestDto;
import gimnasios.com.dto.AfiliadoRequestDto;
import gimnasios.com.exception.RecursoNoEncontradoException;
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
    private AfiliadoCorporativoRequestDto corpRequestDto;

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

        corpRequestDto = AfiliadoCorporativoRequestDto.builder().nombreEmpresa("Mercado Libre")
                .email("barrionuevoDevJosue12@gmail.com")
                .nombreCompleto("Josue Barrionuevo")
                .build();

    }

    @Test
    @DisplayName("Crear un afiliado corporativo test")
    public void crearAfiliadoTest(){
        // --  given
        when(sucursalRepository.findById(suc.getIdSucursal())).thenReturn(Optional.of(suc));
        when(afiliadoMapper.toAfiliadoCorporativoEntity(any(AfiliadoCorporativoDto.class))).thenReturn(afiCorp);
        when(afiliadoRepository.save(any(Afiliado.class))).thenReturn(afiCorp);
        when(afiliadoMapper.toAfiliadoCorporativoDto(any(AfiliadoCorporativo.class))).thenReturn(dto);

        //when

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

    @Test
    @DisplayName("get Corporative Affiliate byId")
    public void getCorpAffiliateById(){
        Long wantedId = 1L;

        //given
        when(afiliadoRepository.findById(wantedId)).thenReturn(Optional.of(afiCorp));
        when(afiliadoMapper.toAfiliadoCorporativoDto(any(AfiliadoCorporativo.class))).thenReturn(dto);

        //when
        AfiliadoCorporativoDto afiDto = afiliadoServiceImp.obtenerAfiliadoCorporId(wantedId);

        assertThat(afiDto).isNotNull();
        assertThat(afiDto.getAfiliadoId()).isNotNull();
        assertThat(afiDto.getCuit()).isEqualTo(afiCorp.getCuit());
        assertThat(afiDto.getNombreEmpresa()).isEqualTo(afiCorp.getNombreEmpresa());

        //then
        verify(afiliadoRepository).findById(wantedId);
        verify(afiliadoMapper).toAfiliadoCorporativoDto(afiCorp);
    }

    @Test
    @DisplayName(" don't get independent Affiliate by id")
    public void doNotGetIndpAffiliateByIdThrowsException(){
        Long corpAffiliateId = 1L;

        //given
        when(afiliadoRepository.findById(corpAffiliateId)).thenReturn(Optional.of(afiCorp));

        //when
        assertThatThrownBy(()->afiliadoServiceImp.obtenerAfiliadoIndPorId(corpAffiliateId))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("No existe un afiliado independiente con id: "+corpAffiliateId);

        //then
        verify(afiliadoRepository,times(1)).findById(corpAffiliateId);
    }

    @Test
    @DisplayName("Update Corporative Affiliate")
    void updateCorporativeAffiliate(){
        Long affiliateCorpId = 1L, locationId = 1L;
        AfiliadoCorporativo expectedAffiliate = AfiliadoCorporativo.builder().cuit("33434")
                .afiliadoId(1L)
                .sucursal(suc)
                .nombreEmpresa("Mercado Libre")
                .email("barrionuevoDevJosue12@gmail.com")
                .nombreCompleto("Josue Barrionuevo")
                .dni("4545")
                .build();

        AfiliadoCorporativoDto affiliateReturnedForUser = AfiliadoCorporativoDto.builder().
        cuit("33434")
                .sucursalId(suc.getIdSucursal())
                .afiliadoId(1L)
                .nombreEmpresa("Mercado Libre")
                .email("barrionuevoDevJosue12@gmail.com")
                .nombreCompleto("Josue Barrionuevo")
                .dni("4545")
                .build();

        //user send this in json format
        AfiliadoCorporativoRequestDto affiliateToUpdate = AfiliadoCorporativoRequestDto.builder().
        cuit("33434")
                .sucursalId(suc.getIdSucursal())
                .nombreEmpresa("Mercado Libre")
                .email("barrionuevoDevJosue12@gmail.com")
                .nombreCompleto("Josue Barrionuevo")
                .build();

        //given
        when(afiliadoRepository.findById(affiliateCorpId)).thenReturn(Optional.of(afiCorp));
        //here might be a LazyInitializationException
        //when(sucursalRepository.findById(locationId)).thenReturn(Optional.of(suc));
        when(afiliadoMapper.toAfiliadoCorporativoSinceRequestDto(any(AfiliadoCorporativo.class),any(AfiliadoCorporativoRequestDto.class)))
                .thenReturn(expectedAffiliate);
        when(afiliadoRepository.save(any(AfiliadoCorporativo.class))).thenReturn(expectedAffiliate);
        when(afiliadoMapper.toAfiliadoCorporativoDto(any(AfiliadoCorporativo.class))).thenReturn(affiliateReturnedForUser);


        AfiliadoCorporativoDto updatedAffiliate = afiliadoServiceImp.actualizarAfiliadoCorporativoDto(affiliateCorpId,affiliateToUpdate);
        //when
        assertThat(updatedAffiliate).isNotNull();
        assertThat(updatedAffiliate.getAfiliadoId()).isNotNull();
        assertThat(updatedAffiliate.getNombreEmpresa()).isEqualTo(affiliateToUpdate.getNombreEmpresa());
        assertThat(updatedAffiliate.getNombreCompleto()).isEqualTo(affiliateToUpdate.getNombreCompleto());
        assertThat(updatedAffiliate.getDni()).isEqualTo(expectedAffiliate.getDni());

        //then
        verify(afiliadoRepository,times(1)).findById(affiliateCorpId);
        verify(afiliadoMapper).toAfiliadoCorporativoSinceRequestDto(any(AfiliadoCorporativo.class),any(AfiliadoCorporativoRequestDto.class));
        verify(afiliadoRepository).save(any(AfiliadoCorporativo.class));
        verify(afiliadoMapper).toAfiliadoCorporativoDto(any(AfiliadoCorporativo.class));
    }
}
