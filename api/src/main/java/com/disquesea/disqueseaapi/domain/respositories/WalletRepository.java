package com.disquesea.disqueseaapi.domain.respositories;

import com.disquesea.disqueseaapi.domain.model.Wallet;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WalletRepository extends PagingAndSortingRepository<Wallet, Long>, JpaSpecificationExecutor<Wallet> {
}


