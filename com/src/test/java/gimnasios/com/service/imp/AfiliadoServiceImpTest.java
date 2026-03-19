package gimnasios.com.service.imp;

import gimnasios.com.domain.Afiliado;
import gimnasios.com.domain.AfiliadoCorporativo;
import gimnasios.com.domain.AfiliadoIndependiente;
import gimnasios.com.domain.Sucursal;
import gimnasios.com.dto.AfiliadoCorporativoDto;
import gimnasios.com.dto.AfiliadoCorporativoRequestDto;
import gimnasios.com.exception.RecursoNoEncontradoException;
import gimnasios.com.exception.ReglaDeNegocioException;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private  AfiliadoCorporativo expectedAffiliate;
    private   AfiliadoCorporativoDto affiliateReturnedForUser;
    private   AfiliadoCorporativoRequestDto affiliateToUpdate;
    private AfiliadoIndependiente indAfi;

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

        expectedAffiliate = AfiliadoCorporativo.builder().cuit("33434")
                .afiliadoId(1L)
                .sucursal(suc)
                .nombreEmpresa("Mercado Libre")
                .email("barrionuevoDevJosue12@gmail.com")
                .nombreCompleto("Josue Barrionuevo")
                .dni("4545")
                .build();
        affiliateReturnedForUser = AfiliadoCorporativoDto.builder().
                cuit("33434")
                .sucursalId(suc.getIdSucursal())
                .afiliadoId(1L)
                .nombreEmpresa("Mercado Libre")
                .email("barrionuevoDevJosue12@gmail.com")
                .nombreCompleto("Josue Barrionuevo")
                .dni("4545")
                .build();

        affiliateToUpdate = AfiliadoCorporativoRequestDto.builder().
                cuit("33434")
                .sucursalId(suc.getIdSucursal())
                .nombreEmpresa("Mercado Libre")
                .email("barrionuevoDevJosue12@gmail.com")
                .nombreCompleto("Josue Barrionuevo")
                .build();
        indAfi = AfiliadoIndependiente.builder()
                .telefono("3886309154")
                .afiliadoId(21L)
                .dni("3030303")
                .aptoFisico(Boolean.TRUE)
                .nombreCompleto("Angelica Gonzalez")
                .email("Angelica21@gmail.com")
                .sucursal(null).build();
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

    @Test
    @DisplayName("Don't update an corporative affiliate trows exception")
    void doNotUpdateCorporativeAffiliateDistinctAffiliateTypes(){
        Long indAffiliateId = 20L;

        //given
        when(afiliadoRepository.findById(indAffiliateId)).thenReturn(Optional.of(indAfi));

        //when
        assertThatThrownBy(()->afiliadoServiceImp.actualizarAfiliadoCorporativoDto(indAffiliateId,corpRequestDto))
                .isInstanceOf(ReglaDeNegocioException.class)
                .hasMessageContaining("Se ha intentado actualizar un afiliado corporativo, con un id de otro tipo de afiliado. Id: "+indAffiliateId);

        //then
        verify(afiliadoRepository).findById(indAffiliateId);
    }

    @Test
    @DisplayName("Don't update corporative affiliate all attributes are null or blank")
    void doNotUpdateCorpAffiliateAllAtributesAreNull(){
        //for searh affiliate
        Long corpAffiliateId = 1L;

        //the main core of test
        corpRequestDto.setNombreEmpresa(null);
        corpRequestDto.setEmail(null);
        corpRequestDto.setNombreCompleto(null);

        //given
        when(afiliadoRepository.findById(corpAffiliateId)).thenReturn(Optional.of(afiCorp));

        //when
        assertThatThrownBy(()->afiliadoServiceImp.actualizarAfiliadoCorporativoDto(corpAffiliateId,corpRequestDto))
                .isInstanceOf(ReglaDeNegocioException.class)
                .hasMessageContaining("Se ha intentado actualizar un afiliado corporativo, cuyos valores son vacions "+corpRequestDto);

        //then
        verify(afiliadoRepository).findById(corpAffiliateId);

    }
}
