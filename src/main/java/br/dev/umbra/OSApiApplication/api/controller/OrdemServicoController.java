/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package br.dev.umbra.OSApiApplication.api.controller;

import br.dev.umbra.OSApiApplication.domain.exception.DomainException;
import br.dev.umbra.OSApiApplication.domain.model.OrdemServico;
import br.dev.umbra.OSApiApplication.domain.model.StatusOrdemServico;
import br.dev.umbra.OSApiApplication.domain.repository.OrdemServicoRepository;
import br.dev.umbra.OSApiApplication.domain.service.OrdemServicoService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Sam_Umbra
 */
@RestController
@RequestMapping("/ordem-servico")
public class OrdemServicoController {
    
    @Autowired
    private OrdemServicoService ordemServicoService;
    
    @Autowired
    private OrdemServicoRepository ordemServicoRepository;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServico criar(@RequestBody OrdemServico ordemServico) {
        return ordemServicoService.criar(ordemServico);
    }
    
    @DeleteMapping("/{ordemID}")
    public ResponseEntity<Void> excluir(@PathVariable Long ordemID) {
        if(!ordemServicoRepository.existsById(ordemID)) {
            return ResponseEntity.notFound().build();
        } else {
            ordemServicoService.excluir(ordemID);
            return ResponseEntity.noContent().build();
        }
    }
    
    @GetMapping
    public List<OrdemServico> listar() {
        return ordemServicoRepository.findAll();
    }
    
    @GetMapping("/{ordemID}")
    public ResponseEntity<OrdemServico> buscar(@PathVariable Long ordemID) {
        Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemID);
        
        if(ordemServico.isPresent()) {
            return ResponseEntity.ok(ordemServico.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{ordemID}")
    public ResponseEntity<OrdemServico> atualizar(@PathVariable Long ordemID, 
                                                  @RequestBody OrdemServico ordemServico) {
        
        if(!ordemServicoRepository.existsById(ordemID)) {
            
            return ResponseEntity.notFound().build();
            
        } else {
            
            OrdemServico ordemStatusVerificar = ordemServicoRepository.findById(ordemID).get();
            
            if(ordemStatusVerificar.getStatus().equals(StatusOrdemServico.FINALIZADA)) {
            
            throw new DomainException("A ordem está FINALIZADA!");
            
            } else if(ordemStatusVerificar.getStatus().equals(StatusOrdemServico.CANCELADA)) {

                throw new DomainException("A ordem está CANCELADA!");

            } else {
                ordemServico.setId(ordemID);
                ordemServico = ordemServicoRepository.save(ordemServico);
                return ResponseEntity.ok(ordemServico);
            }
        }
    }
    
    @PatchMapping("/finalizar/{ordemID}")
    public ResponseEntity<OrdemServico> finalizar(@PathVariable Long ordemID) {
        
        if(!ordemServicoRepository.existsById(ordemID)) {
            return ResponseEntity.notFound().build();
        } else {
            OrdemServico ordemFinalizada = ordemServicoRepository.findById(ordemID).get();
            ordemFinalizada.setId(ordemID);
            ordemFinalizada = ordemServicoService.finalizar(ordemFinalizada);
            
            return ResponseEntity.ok(ordemFinalizada);
        }
    }
    
    @PatchMapping("/cancelar/{ordemID}")
    public ResponseEntity<OrdemServico> cancelar(@PathVariable Long ordemID) {
        if(!ordemServicoRepository.existsById(ordemID)) {
            return ResponseEntity.notFound().build();
        } else {
            OrdemServico ordemCancelada = ordemServicoRepository.findById(ordemID).get();
            ordemCancelada.setId(ordemID);
            ordemCancelada = ordemServicoService.cancelar(ordemCancelada);
            
            return ResponseEntity.ok(ordemCancelada);
        }
    }
    
}
