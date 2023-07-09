package com.example.exerciciotestes.service;

import com.example.exerciciotestes.controller.request.ClienteRequest;
import com.example.exerciciotestes.model.Cliente;
import com.example.exerciciotestes.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscaTodosClientes() {
        // Mocking data
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente("Cliente 1", 100.0));
        clientes.add(new Cliente("Cliente 2", 200.0));
        when(clienteRepository.findAll()).thenReturn(clientes);

        // Call the method
        List<Cliente> result = clienteService.buscaTodosClientes();

        // Verify the result
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Cliente 1", result.get(0).getNomeCliente());
        Assertions.assertEquals(100.0, result.get(0).getSaldoCliente());
        Assertions.assertEquals("Cliente 2", result.get(1).getNomeCliente());
        Assertions.assertEquals(200.0, result.get(1).getSaldoCliente());

        // Verify that the repository method was called
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testSalvarCliente() {
        // Mocking data
        ClienteRequest clienteRequest = new ClienteRequest("Novo Cliente", 150.0);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(new Cliente("Novo Cliente", 150.0));

        // Call the method
        Cliente result = clienteService.salvarCliente(clienteRequest);

        // Verify the result
        Assertions.assertEquals("Novo Cliente", result.getNomeCliente());
        Assertions.assertEquals(150.0, result.getSaldoCliente());

        // Verify that the repository method was called
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testAtualizarCliente() {
        // Mocking data
        Long id = 1L;
        ClienteRequest clienteRequest = new ClienteRequest("Cliente Atualizado", 300.0);
        Cliente clienteAtual = new Cliente("Cliente Antigo", 200.0);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteAtual));
        when(clienteRepository.save(clienteAtual)).thenReturn(clienteAtual);

        // Call the method
        Cliente result = clienteService.atualizarCliente(id, clienteRequest);

        // Verify the result
        Assertions.assertEquals("Cliente Atualizado", result.getNomeCliente());
        Assertions.assertEquals(300.0, result.getSaldoCliente());

        // Verify that the repository methods were called
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).save(clienteAtual);
    }

    @Test
    void testBuscaClientePorId() {
        // Mocking data
        Long id = 1L;
        Cliente cliente = new Cliente("Cliente", 100.0);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        // Call the method
        Cliente result = clienteService.buscaClientePorId(id);

        // Verify the result
        Assertions.assertEquals("Cliente", result.getNomeCliente());
        Assertions.assertEquals(100.0, result.getSaldoCliente());

        // Verify that the repository method was called
        verify(clienteRepository, times(1)).findById(id);
    }

    @Test
    void testDetelaClientePorId() {
        // Mocking data
        Long id = 1L;
        Cliente cliente = new Cliente("Cliente", 100.0);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        // Call the method
        clienteService.detelaClientePorId(id);

        // Verify that the repository method was called
        verify(clienteRepository, times(1)).findById(id);
        verify(clienteRepository, times(1)).deleteById(id);
    }
}
