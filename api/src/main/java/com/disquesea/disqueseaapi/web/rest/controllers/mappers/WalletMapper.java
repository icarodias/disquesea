package com.disquesea.disqueseaapi.web.rest.controllers.mappers;

import com.disquesea.disqueseaapi.domain.model.Wallet;
import com.disquesea.disqueseaapi.web.rest.controllers.dtos.responses.WalletResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper extends AbstractMapper<Wallet, WalletResponseDTO> {

    public WalletMapper(ModelMapper modelMapper){
        super(modelMapper);
    }
}
