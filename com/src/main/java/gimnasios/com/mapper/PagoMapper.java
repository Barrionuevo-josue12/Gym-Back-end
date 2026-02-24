package gimnasios.com.mapper;

import gimnasios.com.domain.Pago;
import gimnasios.com.dto.PagoDto;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {
    public Pago toPagoEntity(PagoDto dto){
        return Pago.builder().importePago(dto.getImportePago())
                .conceptoPago(dto.getConceptoPago())
                .build();
    }
    public  PagoDto toPagoDto (Pago entidad){
        return PagoDto.builder().idPago(entidad.getIdPago())
                .conceptoPago(entidad.getConceptoPago())
                .importePago(entidad.getImportePago())
                .build();
    }
}
