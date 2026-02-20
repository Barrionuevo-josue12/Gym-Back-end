package gimnasios.com.mapper;

import gimnasios.com.domain.Afiliado;
import gimnasios.com.domain.AfiliadoCorporativo;
import gimnasios.com.dto.AfiliadoCorporativoDto;
import gimnasios.com.dto.AfiliadoDto;

public class AfiliadoMapper {

    AfiliadoCorporativo afiliadoCorporativoToEntity (AfiliadoCorporativoDto dto){
        AfiliadoCorporativo afi = AfiliadoCorporativo.builder().dni(dto.getDni()).email(dto.getEmail());}
}
