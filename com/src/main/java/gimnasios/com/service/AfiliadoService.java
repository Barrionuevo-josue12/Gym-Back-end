package gimnasios.com.service;

import gimnasios.com.domain.Afiliado;
import gimnasios.com.dto.*;

import java.util.List;

public interface AfiliadoService {
    public AfiliadoCorporativoDto crearAfiliadoCorporativo (AfiliadoCorporativoDto dto);
    public AfiliadoIndependienteDto crearAfiliadoIndependienteDto (AfiliadoIndependienteDto dto);
    public AfiliadoCorporativoDto actualizarAfiliadoCorporativoDto (Long id, AfiliadoCorporativoRequestDto dto);
    public AfiliadoIndependienteDto actualizarAfiliadoIndependienteDto (Long id, AfiliadoIndependienteRequestDto dto);
    public void borrarAfiliadoPorId (Long id);
    public List<AfiliadoDto>  listarAfiliados();
    public AfiliadoIndependienteDto obtenerAfiliadoIndPorId(Long id);
    public AfiliadoCorporativoDto obtenerAfiliadoCorporId(Long id);
    public AfiliadoDto getAffiliateById(Long id);
}
