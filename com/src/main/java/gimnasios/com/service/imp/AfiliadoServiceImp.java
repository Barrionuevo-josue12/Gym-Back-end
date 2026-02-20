package gimnasios.com.service.imp;

import gimnasios.com.domain.Afiliado;
import gimnasios.com.domain.AfiliadoCorporativo;
import gimnasios.com.domain.Sucursal;
import gimnasios.com.dto.AfiliadoCorporativoDto;
import gimnasios.com.dto.AfiliadoIndependienteDto;
import gimnasios.com.exception.RecursoNoEncontradoException;
import gimnasios.com.mapper.AfiliadoMapper;
import gimnasios.com.repository.AfiliadoRepository;
import gimnasios.com.repository.SucursalRepository;
import gimnasios.com.service.AfiliadoService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

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
        //AfiliadoUtil.validarAfiliadoCorporativo(dto)

        //verificar la existencia de la sucursal

        Optional<Sucursal> sucursalOptional =  sucursalRepository.findById(dto.getSucursalId());
        if(sucursalOptional.isEmpty()){
            log.error("Se ha intendado crear un afiliado corporativo, cuya sucursal no existe{}",dto);
            throw new RecursoNoEncontradoException("la sucursal con id: "+dto.getSucursalId()+"no existe");
        }

        AfiliadoCorporativo afi = afiliadoMapper.toAfiliadoCorporativoEntity(dto);

        afi.setSucursal(sucursalOptional.get());

        //esto puede evitarse, si en el mapper funciona el traspaso del sucursalId
        AfiliadoCorporativoDto afiliadoDto = afiliadoMapper.toAfiliadoCorporativoDto(afi);
        afiliadoDto.setSucursalId(sucursalOptional.get().getIdSucursal());
        log.info("Se ha creado un nuevo afiliado corporativo{}",afiliadoDto);
        return afiliadoDto;
    }

    @Override
    public AfiliadoIndependienteDto crearAfiliadoIndependienteDto(AfiliadoIndependienteDto dto) {
        return null;
    }

    @Override
    public AfiliadoCorporativoDto actualizarAfiliadoCorporativoDto(AfiliadoCorporativoDto dto) {
        return null;
    }

    @Override
    public AfiliadoIndependienteDto actualizarAfiliadoIndependienteDto(AfiliadoIndependienteDto dto) {
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
