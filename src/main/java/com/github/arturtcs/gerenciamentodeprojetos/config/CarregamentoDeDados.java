package com.github.arturtcs.gerenciamentodeprojetos.config;

import com.github.arturtcs.gerenciamentodeprojetos.enums.StatusAtividade;
import com.github.arturtcs.gerenciamentodeprojetos.enums.StatusProjeto;
import com.github.arturtcs.gerenciamentodeprojetos.model.Atividade;
import com.github.arturtcs.gerenciamentodeprojetos.model.Cliente;
import com.github.arturtcs.gerenciamentodeprojetos.model.Projeto;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.AtividadeRepository;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ClienteRepository;
import com.github.arturtcs.gerenciamentodeprojetos.repositories.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarregamentoDeDados {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {

            // Criação de Clientes
            Cliente cliente1 = new Cliente();
            cliente1.setNome("Cliente 1");
            cliente1.setCpf("45236429072");
            cliente1.setEmail("cliente1@email.com");
            clienteRepository.save(cliente1);

            Cliente cliente2 = new Cliente();
            cliente2.setNome("Cliente 2");
            cliente2.setCpf("45236428882");
            cliente2.setEmail("cliente2@email.com");
            clienteRepository.save(cliente2);

            // Criação de Projetos
            Projeto projeto1 = new Projeto();
            projeto1.setNome("Projeto 1");
            projeto1.setStatus(StatusProjeto.ABERTO);
            projeto1.setCliente(cliente1);
            projetoRepository.save(projeto1);

            Projeto projeto2 = new Projeto();
            projeto2.setNome("Projeto 2");
            projeto2.setStatus(StatusProjeto.EM_ANDAMENTO);
            projeto2.setCliente(cliente2);
            projetoRepository.save(projeto2);

            // Criação de Atividades
            Atividade atividade1 = new Atividade();
            atividade1.setDescricao("Atividade 1 do Projeto 1");
            atividade1.setStatus(StatusAtividade.PENDENCIA);
            atividade1.setProjeto(projeto1);
            atividadeRepository.save(atividade1);

            Atividade atividade2 = new Atividade();
            atividade2.setDescricao("Atividade 2 do Projeto 1");
            atividade2.setStatus(StatusAtividade.EM_ANDAMENTO);
            atividade2.setProjeto(projeto1);
            atividadeRepository.save(atividade2);

            Atividade atividade3 = new Atividade();
            atividade3.setDescricao("Atividade 1 do Projeto 2");
            atividade3.setStatus(StatusAtividade.CONCLUIDA);
            atividade3.setProjeto(projeto2);
            atividadeRepository.save(atividade3);
        };
    }
}
