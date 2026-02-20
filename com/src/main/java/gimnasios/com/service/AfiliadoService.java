package gimnasios.com.service;

import gimnasios.com.domain.Afiliado;
import gimnasios.com.dto.AfiliadoCorporativoDto;
import gimnasios.com.dto.AfiliadoIndependienteDto;

import java.util.List;

public interface AfiliadoService {
    public AfiliadoCorporativoDto crearAfiliadoCorporativo (AfiliadoCorporativoDto dto);
    public AfiliadoIndependienteDto crearAfiliadoIndependienteDto (AfiliadoIndependienteDto dto);
    public AfiliadoCorporativoDto actualizarAfiliadoCorporativoDto (AfiliadoCorporativoDto dto);
    public AfiliadoIndependienteDto actualizarAfiliadoIndependienteDto (AfiliadoIndependienteDto dto);
    public void borrarAfiliadoPorId (Long id);
    public List<Afiliado>  listarAfiliados();
    public AfiliadoIndependienteDto obtenerAfiliadoIndPorId(Long id);
    public AfiliadoCorporativoDto obtenerAfiliadoCorporId(long id);
}
