/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package br.dev.umbra.OSApiApplication.domain.repository;

import br.dev.umbra.OSApiApplication.domain.model.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
 * @author Sam_Umbra
 */
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    
}
