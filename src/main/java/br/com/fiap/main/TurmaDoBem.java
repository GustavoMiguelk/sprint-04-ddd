package br.com.fiap.main;

import br.com.fiap.dao.*;
import br.com.fiap.entities.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.UUID;

public class TurmaDoBem {

    static String texto(String j) { return JOptionPane.showInputDialog(j); }
    static int inteiro(String j) { return Integer.parseInt(JOptionPane.showInputDialog(j)); }
    static void mensagem(String j) { JOptionPane.showMessageDialog(null, j); }

    public static void main(String[] args) {

        ArrayList<Instituicao> instituicoes = new ArrayList<>();
        ArrayList<Local> locais = new ArrayList<>();
        ArrayList<Programa> programas = new ArrayList<>();
        ArrayList<Beneficiario> beneficiarios = new ArrayList<>();
        ArrayList<Profissional> profissionais = new ArrayList<>();
        ArrayList<Consulta> consultas = new ArrayList<>();
        ArrayList<Responsavel> responsaveis = new ArrayList<>();

        Programa p1 = new Programa("Dentista do Bem", "Atendimento para jovens carentes menores de idade");
        p1.setIdPrograma(1);

        Programa p2 = new Programa("Apolônias do Bem", "Atendimento para mulheres vitimas de violência");
        p2.setIdPrograma(2);

        programas.add(p1);
        programas.add(p2);

        while (true) {
            int opcao = inteiro("""
                ===== Sistema de Agendamento Turma do Bem =====
                --- Cadastrar ---
                1 - Cadastrar Instituição
                2 - Cadastrar Local
                3 - Cadastrar Beneficiário
                4 - Cadastrar Profissional
                5 - Agendar Consulta
                --- Consultar ---
                6 - Listar Consultas
                7 - Listar Beneficiários
                8 - Buscar Beneficiário por CPF
                9 - Listar Profissionais
                10 - Buscar Profissional por CPF
                --- Atualizar ---
                11 - Atualizar Beneficiário
                12 - Atualizar Profissional
                --- Excluir ---
                13 - Excluir Beneficiário
                14 - Excluir Profissional
                0 - Sair
                Escolha uma opção:""");

            switch (opcao) {
                case 1:
                    Instituicao instituicao = new Instituicao(
                            texto("Nome da Instituição:"),
                            texto("CNPJ da Instituição:")
                    );
                    try {
                        InstituicaoDao instituicaoDao = new InstituicaoDao();
                        mensagem(instituicaoDao.inserir(instituicao));
                        instituicoes.add(instituicao);
                    } catch (Exception e) {
                        mensagem("Erro ao salvar instituição: " + e.getMessage());
                    }
                    break;

                case 2:
                    if (instituicoes.isEmpty()) { mensagem("Cadastre uma instituição antes de continuar!"); break; }

                    StringBuilder listaInst = new StringBuilder();
                    for (int i = 0; i < instituicoes.size(); i++)
                        listaInst.append(i).append(" - ").append(instituicoes.get(i).getNome()).append("\n");

                    Local local = new Local(
                            instituicoes.get(inteiro("Selecione a instituição:\n" + listaInst)).getCnpj(),
                            texto("Nome do Local:"),
                            texto("Endereço:"),
                            texto("Tipo:")
                    );
                    try {
                        LocalDao localDao = new LocalDao();
                        mensagem(localDao.inserir(local));
                        locais.add(local);
                    } catch (Exception e) {
                        mensagem("Erro ao salvar local: " + e.getMessage());
                    }
                    break;

                case 3:
                    String nomeBen = texto("Nome do Beneficiário:");
                    int idade      = inteiro("Idade do Beneficiário:");
                    String cpfBen  = texto("CPF do Beneficiário:");
                    String sexo    = texto("Sexo (masculino/feminino):").toLowerCase();

                    StringBuilder listaPgm = new StringBuilder();
                    for (int i = 0; i < programas.size(); i++)
                        listaPgm.append(i).append(" - ").append(programas.get(i).getNome()).append("\n");
                    int idPgm = inteiro("Selecione o programa:\n" + listaPgm);

                    if (idPgm == 0 && idade >= 18) { mensagem("Somente menores de idade podem participar desse programa."); break; }
                    if (idPgm == 1 && sexo.equals("masculino")) { mensagem("Somente mulheres podem participar desse programa."); break; }

                    Responsavel resp;
                    if (idade < 18) {
                        mensagem("Insira os dados do responsável:");
                        resp = new Responsavel(texto("Nome do Responsável:"), texto("CPF do Responsável:"), texto("Contato:"));
                    } else {
                        resp = new Responsavel(nomeBen, cpfBen, texto("Contato:"));
                    }

                    Beneficiario beneficiario = new Beneficiario(nomeBen, idade, cpfBen, sexo, resp, programas.get(idPgm));

                    try {
                        ResponsavelDao responsavelDao = new ResponsavelDao();
                        responsavelDao.inserir(resp);
                        responsaveis.add(resp);

                        BeneficiarioDao beneficiarioDao = new BeneficiarioDao();
                        mensagem(beneficiarioDao.inserir(beneficiario));
                        beneficiarios.add(beneficiario);
                    } catch (Exception e) {
                        mensagem("Erro ao salvar beneficiário: " + e.getMessage());
                    }
                    break;

                case 4:
                    Profissional profissional = new Profissional(
                            texto("Nome do Profissional:"),
                            texto("CPF do Profissional:"),
                            texto("CRO do Profissional:"),
                            texto("Especialização:")
                    );
                    try {
                        ProfissionalDao profissionalDao = new ProfissionalDao();
                        mensagem(profissionalDao.inserir(profissional));
                        profissionais.add(profissional);
                    } catch (Exception e) {
                        mensagem("Erro ao salvar profissional: " + e.getMessage());
                    }
                    break;

                case 5:
                    if (profissionais.isEmpty() || beneficiarios.isEmpty() || locais.isEmpty()) {
                        mensagem("Cadastre Profissional, Beneficiário e Local antes de agendar.");
                        break;
                    }

                    String cpfBusca = texto("CPF do Beneficiário:");
                    int idBenef = -1;
                    for (int i = 0; i < beneficiarios.size(); i++)
                        if (cpfBusca.equals(beneficiarios.get(i).getCpf())) { idBenef = i; break; }

                    if (idBenef == -1) { mensagem("Beneficiário não encontrado!"); break; }

                    StringBuilder listaProf = new StringBuilder();
                    for (int i = 0; i < profissionais.size(); i++)
                        listaProf.append(i).append(" - ").append(profissionais.get(i).getNome()).append("\n");
                    int idProf = inteiro("Escolha o profissional:\n" + listaProf);

                    StringBuilder listaLocais = new StringBuilder();
                    for (int i = 0; i < locais.size(); i++)
                        listaLocais.append(i).append(" - ").append(locais.get(i).getNome()).append(" | ").append(locais.get(i).getEndereco()).append("\n");
                    int idLocal = inteiro("Escolha o local:\n" + listaLocais);

                    String codigo = UUID.randomUUID().toString();
                    Profissional prof = profissionais.get(idProf);
                    if (prof.getAgenda() == null) prof.setAgenda(new Agenda(prof));

                    Consulta consulta = new Consulta(
                            codigo, prof, beneficiarios.get(idBenef),
                            locais.get(idLocal), texto("Tipo da Consulta:"), texto("Descrição:")
                    );
                    try {
                        ConsultaDao consultaDao = new ConsultaDao();
                        mensagem(consultaDao.inserir(consulta));
                        consultas.add(consulta);
                        prof.getAgenda().adicionarConsulta(consulta);
                    } catch (Exception e) {
                        mensagem("Erro ao salvar consulta: " + e.getMessage());
                    }
                    break;

                case 6:
                    if (consultas.isEmpty()) { mensagem("Nenhuma consulta cadastrada."); break; }

                    StringBuilder lista = new StringBuilder("=== Consultas Turma do Bem ===\n\n");
                    for (Consulta c : consultas) {
                        lista.append("Código: ").append(c.getCodigoConsulta())
                                .append("\nProfissional: ").append(c.getProfissional().getNome())
                                .append("\nBeneficiário: ").append(c.getBeneficiario().getNome())
                                .append("\nLocal: ").append(c.getLocal().getNome()).append(" | ").append(c.getLocal().getEndereco())
                                .append("\nTipo: ").append(c.getTipo())
                                .append("\nDescrição: ").append(c.getDescricao())
                                .append("\n------------------------------------------\n");
                    }
                    mensagem(lista.toString());
                    break;

                case 7:
                    try {
                        BeneficiarioDao bdao = new BeneficiarioDao();
                        StringBuilder sb = new StringBuilder("=== Beneficiários ===\n");
                        for (Beneficiario b : bdao.selecionar())
                            sb.append("CPF: ").append(b.getCpf())
                                    .append(" | Nome: ").append(b.getNome())
                                    .append(" | Idade: ").append(b.getIdade())
                                    .append(" | Sexo: ").append(b.getSexo()).append("\n");
                        mensagem(sb.toString());
                    } catch (Exception e) { mensagem("Erro: " + e.getMessage()); }
                    break;

                case 8:
                    try {
                        String cpf = texto("CPF do Beneficiário:");
                        BeneficiarioDao bdao = new BeneficiarioDao();
                        Beneficiario b = bdao.buscarPorCpf(cpf);
                        if (b == null) mensagem("Beneficiário não encontrado.");
                        else mensagem("Nome: " + b.getNome() + "\nIdade: " + b.getIdade() + "\nSexo: " + b.getSexo());
                    } catch (Exception e) { mensagem("Erro: " + e.getMessage()); }
                    break;

                case 9:
                    try {
                        ProfissionalDao pdao = new ProfissionalDao();
                        StringBuilder sb = new StringBuilder("=== Profissionais ===\n");
                        for (Profissional p : pdao.selecionar())
                            sb.append("CPF: ").append(p.getCpf())
                                    .append(" | Nome: ").append(p.getNome())
                                    .append(" | CRO: ").append(p.getCro())
                                    .append(" | Especialização: ").append(p.getEspecializacao()).append("\n");
                        mensagem(sb.toString());
                    } catch (Exception e) { mensagem("Erro: " + e.getMessage()); }
                    break;

                case 10:
                    try {
                        String cpf = texto("CPF do Profissional:");
                        ProfissionalDao pdao = new ProfissionalDao();
                        Profissional p = pdao.buscarPorCpf(cpf);
                        if (p == null) mensagem("Profissional não encontrado.");
                        else mensagem("Nome: " + p.getNome() + "\nCRO: " + p.getCro() + "\nEspecialização: " + p.getEspecializacao());
                    } catch (Exception e) { mensagem("Erro: " + e.getMessage()); }
                    break;

                case 11:
                    try {
                        String cpf   = texto("CPF do Beneficiário a atualizar:");
                        String nome  = texto("Novo nome:");
                        int novaIdade = inteiro("Nova idade:");
                        String novoSexo = texto("Novo sexo (masculino/feminino):").toLowerCase();
                        BeneficiarioDao bdao = new BeneficiarioDao();
                        Beneficiario b = bdao.buscarPorCpf(cpf);
                        if (b == null) { mensagem("Beneficiário não encontrado."); break; }
                        b = new Beneficiario(nome, novaIdade, cpf, novoSexo, b.getResponsavel(), b.getPrograma());
                        mensagem(bdao.atualizar(b));
                    } catch (Exception e) { mensagem("Erro: " + e.getMessage()); }
                    break;

                case 12:
                    try {
                        String cpf   = texto("CPF do Profissional a atualizar:");
                        String nome  = texto("Novo nome:");
                        String espec = texto("Nova especialização:");
                        ProfissionalDao pdao = new ProfissionalDao();
                        Profissional p = pdao.buscarPorCpf(cpf);
                        if (p == null) { mensagem("Profissional não encontrado."); break; }
                        p = new Profissional(nome, cpf, p.getCro(), espec);
                        mensagem(pdao.atualizar(p));
                    } catch (Exception e) { mensagem("Erro: " + e.getMessage()); }
                    break;

                case 13:
                    try {
                        String cpf = texto("CPF do Beneficiário a excluir:");
                        BeneficiarioDao bdao = new BeneficiarioDao();
                        mensagem(bdao.deletar(cpf));
                    } catch (Exception e) { mensagem("Erro: " + e.getMessage()); }
                    break;

                case 14:
                    try {
                        String cpf = texto("CPF do Profissional a excluir:");
                        ProfissionalDao pdao = new ProfissionalDao();
                        mensagem(pdao.deletar(cpf));
                    } catch (Exception e) { mensagem("Erro: " + e.getMessage()); }
                    break;

                case 0:
                    mensagem("Encerrando sistema...");
                    return;

                default:
                    mensagem("Opção inválida!");
            }
        }
    }
}