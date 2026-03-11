package gimnasios.com.service.imp;

import gimnasios.com.domain.Afiliado;
import gimnasios.com.domain.AfiliadoCorporativo;
import gimnasios.com.domain.AfiliadoIndependiente;
import gimnasios.com.domain.Sucursal;
import gimnasios.com.dto.*;
import gimnasios.com.exception.RecursoNoEncontradoException;
import gimnasios.com.exception.ReglaDeNegocioException;
import gimnasios.com.mapper.AfiliadoMapper;
import gimnasios.com.repository.AfiliadoRepository;
import gimnasios.com.repository.SucursalRepository;
import gimnasios.com.service.AfiliadoService;
import gimnasios.com.util.AfiliadoUtil;
import gimnasios.com.util.GimnasioUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Transactional
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
        Afiliado afi =  foundAfiliateOrThrowException(id);

        //verificacion del tipo de afiliado
        if(!(afi instanceof AfiliadoCorporativo wanted)){
            log.error("se ha intentando actualizar un afiliado corporativo, con un id que no pertenece a dicho tipo de afiliado {}",id);
            throw  new ReglaDeNegocioException("Se ha intentado actualizar un afiliado corporativo, con un id de otro tipo de afiliado: "+id);
        }

        AfiliadoUtil.validarActualizacionAfiliadoCorporativo(dto);
        if(dto.getSucursalId() != null && !wanted.getSucursal().getIdSucursal().equals(dto.getSucursalId())){
            Sucursal sucursal = sucursalRepository.findById(dto.getSucursalId())
                    .orElseThrow(() -> {
                        log.error("Se ha intentado actualizar un afiliado corporativo, cuya sucursal no existe {}", dto);
                        return new RecursoNoEncontradoException("La sucursal con id: " + dto.getSucursalId() + " no existe");
                    });
            //se cambia la sucursal, en el caso de que se desee cambiarla.
            wanted.setSucursal(sucursal);
        }

        //validar que el email sea unico
        if(GimnasioUtil.stringOk(dto.getEmail())
                && afiliadoRepository.existByEmail(dto.getEmail())
        && !dto.getEmail().equals(afi.getEmail())){
            log.error("Se ha intentado actualizar un Afiliado Corporativo con un email que ya existe {}",dto);
            throw new ReglaDeNegocioException("Se ha intentado actualizar un Afiliado corporativo con un email existente: "+dto.getEmail());
        }

        //se actualizan los datos desde el dto a la entidad (se mantienen, los que no se desean actualizar)
        wanted = afiliadoMapper.toAfiliadoCorporativoSinceRequestDto(wanted,dto);

        log.info("afiliado corporativo actualizado con exito. Cuyo id es: {}",wanted.getAfiliadoId());
        return afiliadoMapper.toAfiliadoCorporativoDto(afiliadoRepository.save(wanted));

    }

    private Afiliado foundAfiliateOrThrowException(Long id){
        Afiliado afi = afiliadoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("EL afiliado  con id: {} no existe", id);
                    throw  new RecursoNoEncontradoException("Afiliado con id: " + id+ " no existe");
                });
        return afi;
    }


    @Override
    @Transactional
    public AfiliadoIndependienteDto actualizarAfiliadoIndependienteDto(Long id, AfiliadoIndependienteRequestDto dto) {
        log.info("Se esta intentando actualizar un afiliado independiente...");

        //busqueda del afiliado
        Afiliado afi =  foundAfiliateOrThrowException(id);

        //verificacion del tipo de afiliado
        if(!(afi instanceof AfiliadoIndependiente wanted)){
            log.error("se ha intentando actualizar un afiliado independiente, con un id que no pertenece a dicho tipo de afiliado {}",id);
            throw  new ReglaDeNegocioException("Se ha intentado actualizar un afiliado independiente, con un id de otro tipo de afiliado: "+id);
        }

        AfiliadoUtil.validarActualizacionAfiliadoIndependiente(dto);
        if(dto.getSucursalId() != null && !wanted.getSucursal().getIdSucursal().equals(dto.getSucursalId())){
            Sucursal sucursal =  sucursalRepository.findById(dto.getSucursalId())
                    .orElseThrow(() -> {
                        log.error("Se ha intentado actualizar un afiliado independiente, cuya sucursal no existe {}", dto);
                        return new RecursoNoEncontradoException("La sucursal con id: " + dto.getSucursalId() + " no existe");
                    });
            //se cambia la sucursal, en el caso de que se desee cambiarla.
            wanted.setSucursal(sucursal);
        }

        //validar que el email sea unico
        if(GimnasioUtil.stringOk(dto.getEmail())
                && afiliadoRepository.existByEmail(dto.getEmail())
                && !dto.getEmail().equals(afi.getEmail())){
            log.error("Se hai intentado actualizar un Afiliado independiente con un email que ya existe {}",dto);
            throw new ReglaDeNegocioException("Se ha intentado actualizar un Afiliado independiente con un email existente: "+dto.getEmail());
        }

        //se actualizan los datos desde el dto a la entidad (se mantienen, los que no se desean actualizar)
        wanted = afiliadoMapper.toAfiliadoIndependienteSinceRequestDto(wanted,dto);

        log.info("afiliado independiente actualizado con exito. Cuyo id es: {}",wanted.getAfiliadoId());
        return afiliadoMapper.toAfiliadoIndependienteDto((afiliadoRepository.save(wanted)));
    }

    @Override
    public void borrarAfiliadoPorId(Long id) {
        if(!afiliadoRepository.existsById(id)){
            log.error("Se ha intentado borrar un afiliado inexistente. Id: {}",id);
            throw new RecursoNoEncontradoException("El afiliado con id: "+id+ " no existe");
        }
        afiliadoRepository.deleteById(id);
    }

    @Override
    public List<AfiliadoDto> listarAfiliados() {
        return afiliadoRepository.findAll()
                .stream() // 1. Ponemos la lista en la "cinta transportadora"
                .map(afiliadoMapper::toDto) // 2. Transformamos cada Entidad a DTO
                .toList(); // 3. Lo empaquetamos todo en una lista
    }

    @Override
    public AfiliadoIndependienteDto obtenerAfiliadoIndPorId(Long id) {
        Afiliado afi = foundAfiliateOrThrowException(id);
        if(!(afi instanceof AfiliadoIndependiente wanted)){
            log.warn("Se ha intentado obtener un afiliado que no es del tipo independiente. Con el id: {}",id);
            throw new RecursoNoEncontradoException("No existe un afiliado independiente con id: "+id);
        }
        return afiliadoMapper.toAfiliadoIndependienteDto ((AfiliadoIndependiente) wanted);
    }

    @Override
    public AfiliadoCorporativoDto obtenerAfiliadoCorporId(long id) {
        Afiliado afi = foundAfiliateOrThrowException(id);
        if(!(afi instanceof AfiliadoCorporativo wanted)){
            log.warn("Se ha intentado obtener un afiliado que no es del tipo corporativo. Con el id: {}",id);
            throw new RecursoNoEncontradoException("No existe un afiliado corporativo con id: "+id);
        }
        return afiliadoMapper.toAfiliadoCorporativoDto ((AfiliadoCorporativo) wanted);
    }
}
