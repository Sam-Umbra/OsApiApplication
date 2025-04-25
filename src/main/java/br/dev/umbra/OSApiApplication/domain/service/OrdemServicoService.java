/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package br.dev.umbra.OSApiApplication.domain.service;

import br.dev.umbra.OSApiApplication.domain.exception.DomainException;
import br.dev.umbra.OSApiApplication.domain.model.OrdemServico;
import br.dev.umbra.OSApiApplication.domain.model.StatusOrdemServico;
import br.dev.umbra.OSApiApplication.domain.repository.OrdemServicoRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sam_Umbra
 */
@Service
public class OrdemServicoService {
    
    @Autowired
    private OrdemServicoRepository ordemServicoRepository;
    
    public OrdemServico criar(OrdemServico ordemServico) {
        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(LocalDateTime.now());
        
        return ordemServicoRepository.save(ordemServico);
    }
    
    public void excluir(Long ordemID) {
        ordemServicoRepository.deleteById(ordemID);
    }
    
    public OrdemServico finalizar(OrdemServico ordemServico) {
        
        if(ordemServico.getStatus().equals(StatusOrdemServico.FINALIZADA)) {
            
            throw new DomainException("A ordem já foi FINALIZADA!");
            
        } else if(ordemServico.getStatus().equals(StatusOrdemServico.CANCELADA)) {
            
            throw new DomainException("A ordem está CANCELADA!");
            
        } else{
            ordemServico.setStatus(StatusOrdemServico.FINALIZADA);
            ordemServico.setDataFinalizacao(LocalDateTime.now());

            return ordemServicoRepository.save(ordemServico);
        }
    }
    
    public OrdemServico cancelar(OrdemServico ordemServico) {
        if(ordemServico.getStatus().equals(StatusOrdemServico.CANCELADA)) {
            
            throw new DomainException("A ordem já for CANCELADA!");
            
        } else if(ordemServico.getStatus().equals(StatusOrdemServico.FINALIZADA)) {
            
            throw new DomainException("A ordem está FINALIZADA!");
            
        } else {
            ordemServico.setStatus(StatusOrdemServico.CANCELADA);
            ordemServico.setDataFinalizacao(LocalDateTime.now());
            
            return ordemServicoRepository.save(ordemServico);
        }
    }
    
}
