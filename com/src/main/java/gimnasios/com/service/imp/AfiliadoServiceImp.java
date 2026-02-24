package gimnasios.com.service.imp;

import gimnasios.com.domain.Afiliado;
import gimnasios.com.domain.AfiliadoCorporativo;
import gimnasios.com.domain.AfiliadoIndependiente;
import gimnasios.com.domain.Sucursal;
import gimnasios.com.dto.AfiliadoCorporativoDto;
import gimnasios.com.dto.AfiliadoCorporativoRequestDto;
import gimnasios.com.dto.AfiliadoIndependienteDto;
import gimnasios.com.dto.AfiliadoIndependienteRequestDto;
import gimnasios.com.exception.RecursoNoEncontradoException;
import gimnasios.com.exception.ReglaDeNegocioException;
import gimnasios.com.mapper.AfiliadoMapper;
import gimnasios.com.repository.AfiliadoRepository;
import gimnasios.com.repository.SucursalRepository;
import gimnasios.com.service.AfiliadoService;
import gimnasios.com.util.AfiliadoUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AfiliadoServiceImp implements AfiliadoService {

    private final SucursalRepository sucursalRepository;
    private final AfiliadoRepository afiliadoRepository;
    private final AfiliadoMapper afiliadoMapper;

    //inject by build pattern
    public AfiliadoServiceImp(SucursalRepository sucursalRepository,AfiliadoRepository afiliadoRepository,AfiliadoMapper afiliadoMapper){
        this.sucursalRepository = sucursalRepository;
        this.afiliadoRepository = afiliadoRepository;
        this.afiliadoMapper = afiliadoMapper;
    }

    @Override
    @Transactional
    public AfiliadoCorporativoDto crearAfiliadoCorporativo(AfiliadoCorporativoDto dto) {
        log.info("se esta intentando crear un afiliado corporativo...");
        AfiliadoUtil.validarAfiliadoCorporativo(dto);

        //verificar la existencia de la sucursal
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursalId())
                .orElseThrow(() -> {
                    log.error("Se ha intentado crear un afiliado corporativo, cuya sucursal no existe {}", dto);
                    return new RecursoNoEncontradoException("La sucursal con id: " + dto.getSucursalId() + " no existe");
                });
        //traspaso de datos dto -> entidad
        AfiliadoCorporativo afi = afiliadoMapper.toAfiliadoCorporativoEntity(dto);
        afi.setSucursal(sucursal);

        //registro del nuevo afiliado en la BD
        AfiliadoCorporativoDto nuevoAfiDto =  afiliadoMapper.toAfiliadoCorporativoDto(afiliadoRepository.save(afi));
        log.info("nuevo afiliado corporativo registrado {}",nuevoAfiDto);
        return nuevoAfiDto;
    }

    @Override
    public AfiliadoIndependienteDto crearAfiliadoIndependienteDto(AfiliadoIndependienteDto dto) {
        log.info("Se esta intentando crear un nuevo afiliado independiente...");
        AfiliadoUtil.validarAfiliadoIndependiente(dto);

        //verificar la existencia de la sucursal
        Sucursal sucursal = sucursalRepository.findById(dto.getSucursalId())
                .orElseThrow(() -> {
                    log.error("Se ha intentado crear un afiliado independiente, cuya sucursal no existe {}", dto);
                    return new RecursoNoEncontradoException("La sucursal con id: " + dto.getSucursalId() + " no existe");
                });

        //traspaso de datos dto -> entidad
        AfiliadoIndependiente afiIndpte = afiliadoMapper.toAfiliadoIndependienteEntity(dto);
        afiIndpte.setSucursal(sucursal);

        //registro del nuevo afiliado en la BD
        AfiliadoIndependienteDto nuevoAfiDto =  afiliadoMapper.toAfiliadoIndependienteDto(afiliadoRepository.save(afiIndpte));
        log.info("nuevo afiliado independiente registrado {}",nuevoAfiDto);
        return nuevoAfiDto;
    }

    @Override
    @Transactional
    public AfiliadoCorporativoDto actualizarAfiliadoCorporativoDto(Long id, AfiliadoCorporativoRequestDto dto) {
        log.info("Se esta intentando actualizar un afiliado corporativo...");

        //busqueda del afiliado
        Afiliado afi =  afiliadoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Se ha intentado actualizar un afiliado que no existe. con id: {}", id);
                    return new RecursoNoEncontradoException("Afiliado con id: " + id+ " no existe");
                });

        //verificacion del tipo de afiliado
        if(!(afi instanceof AfiliadoCorporativo wanted)){
            log.error("se ha intentando actualizar un afiliado corporativo, con un id que no pertenece a dicho tipo de afiliado {}",id);
            throw  new ReglaDeNegocioException("Se ha intentado actualizar un afiliado corporativo, con un id de otro tipo de afiliado: "+id);
        }

        //AfiliadoUtil.ValidarActualizacionAfiliadoCorporativo(dto);
        if(dto.getSucursalId() != null){
            Sucursal sucursal = sucursalRepository.findById(dto.getSucursalId())
                    .orElseThrow(() -> {
                        log.error("Se ha intentado actualizar un afiliado corporativo, cuya sucursal no existe {}", dto);
                        return new RecursoNoEncontradoException("La sucursal con id: " + dto.getSucursalId() + " no existe");
                    });
            //se cambia la sucursal, en el caso de que se desee cambiarla.
            wanted.setSucursal(sucursal);
        }

        //se actualizan los datos desde el dto a la entidad (se mantienen, los que no se desean actualizar)
        wanted = afiliadoMapper.toAfiliadoCorporativoSinceRequestDto(wanted,dto);

        log.info("afiliado corporativo actualizado con exito. Cuyo id es: {}",wanted.getAfiliadoId());
        return afiliadoMapper.toAfiliadoCorporativoDto(afiliadoRepository.save(wanted));

    }

    @Override
    public AfiliadoIndependienteDto actualizarAfiliadoIndependienteDto(Long id, AfiliadoIndependienteRequestDto dto) {
        return null;
    }

    @Override
    public void borrarAfiliadoPorId(Long id) {

    }

    @Override
    public List<Afiliado> listarAfiliados() {
        return List.of();
    }

    @Override
    public AfiliadoIndependienteDto obtenerAfiliadoIndPorId(Long id) {
        return null;
    }

    @Override
    public AfiliadoCorporativoDto obtenerAfiliadoCorporId(long id) {
        return null;
    }
}
