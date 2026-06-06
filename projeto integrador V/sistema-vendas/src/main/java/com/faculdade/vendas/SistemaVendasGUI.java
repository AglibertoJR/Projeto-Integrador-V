package com.faculdade.vendas;

//A proposta do projeto e ser Desktop simples e facil de usar até para um 
//dono de bar de bairro por isso não foi ultilizado Html nem JavaScript na
//elaboração do projeto e como e uma aplicação Desktop simples que não leva 
//dados sensiveis não e necessario nem um tipo de segurança além da senha do banco de dados.

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SistemaVendasGUI extends JFrame {

    
    // 1. CONFIGURAÇÕES DE BANCO DE DADOS - Agliberto
    private final String URL = "jdbc:mysql://localhost:3306/SistemaVendasDB";
    private final String USUARIO = "root";
    private final String SENHA = "12345678"; 

    // 2. VARIÁVEIS DA INTERFACE (COMPONENTES) - Agliberto

    // Aba Clientes
    private JTextField txtCliId, txtCliNome, txtCliEmail;
    private JTable tabelaClientes;
    private DefaultTableModel modeloClientes;

    // Aba Produtos
    private JTextField txtProdId, txtProdNome, txtProdPreco, txtProdEstoque;
    private JTable tabelaProdutos;
    private DefaultTableModel modeloProdutos;

    // Aba Registro de Pedidos
    private JTextField txtPedId; 
    private JComboBox<String> cbPedCliente;
    private JComboBox<String> cbPedProduto;
    private JTextField txtPedQtd;
    private JTable tabelaPedidos;
    private DefaultTableModel modeloPedidos;

    // Aba Visualização Geral (Relatório)
    private JTable tabelaGeral;
    private DefaultTableModel modeloGeral;

    
    // 3. CONSTRUTOR DA JANELA PRINCIPAL - Agliberto
    
    public SistemaVendasGUI() {
        setTitle("Projeto Integrador V - Sistema de Vendas Empresariais");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane painelAbas = new JTabbedPane();

        painelAbas.addTab("👤 Gerenciar Clientes", criarAbaClientes());
        painelAbas.addTab("📦 Gerenciar Produtos", criarAbaProdutos());
        painelAbas.addTab("🛒 Registro de Pedidos", criarAbaPedidos());
        painelAbas.addTab("📊 Relatório Consolidado", criarAbaGeral());

        add(painelAbas);

        carregarClientes();
        carregarProdutos();
        carregarPedidos();
    }

    
    // 4. CONSTRUÇÃO DAS ABAS (LAYOUTS) - Breno


    private JPanel criarAbaClientes() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Dados do Cliente"));
        
        txtCliId = new JTextField(); txtCliId.setEditable(false);
        txtCliNome = new JTextField();
        txtCliEmail = new JTextField();

        form.add(new JLabel("ID:")); form.add(txtCliId);
        form.add(new JLabel("Nome da Empresa:")); form.add(txtCliNome);
        form.add(new JLabel("E-mail:")); form.add(txtCliEmail);

        painel.add(form, BorderLayout.NORTH);

        modeloClientes = new DefaultTableModel(new String[]{"ID", "Nome", "E-mail"}, 0);
        tabelaClientes = new JTable(modeloClientes);
        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {
            int linha = tabelaClientes.getSelectedRow();
            if (linha >= 0) {
                txtCliId.setText(modeloClientes.getValueAt(linha, 0).toString());
                txtCliNome.setText(modeloClientes.getValueAt(linha, 1).toString());
                txtCliEmail.setText(modeloClientes.getValueAt(linha, 2).toString());
            }
        });
        painel.add(new JScrollPane(tabelaClientes), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAdd = new JButton("Adicionar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");

        btnAdd.addActionListener(e -> { adicionarCliente(); carregarClientes(); });
        btnAtualizar.addActionListener(e -> { atualizarCliente(); carregarClientes(); });
        btnExcluir.addActionListener(e -> { excluirCliente(); carregarClientes(); });
        btnLimpar.addActionListener(e -> { txtCliId.setText(""); txtCliNome.setText(""); txtCliEmail.setText(""); tabelaClientes.clearSelection(); });

        botoes.add(btnAdd); botoes.add(btnAtualizar); botoes.add(btnExcluir); botoes.add(btnLimpar);
        painel.add(botoes, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarAbaProdutos() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Dados do Produto"));

        txtProdId = new JTextField(); txtProdId.setEditable(false);
        txtProdNome = new JTextField();
        txtProdPreco = new JTextField();
        txtProdEstoque = new JTextField();

        form.add(new JLabel("ID:")); form.add(txtProdId);
        form.add(new JLabel("Nome do Produto:")); form.add(txtProdNome);
        form.add(new JLabel("Preço (R$):")); form.add(txtProdPreco);
        form.add(new JLabel("Quantidade em Estoque:")); form.add(txtProdEstoque);

        painel.add(form, BorderLayout.NORTH);

        modeloProdutos = new DefaultTableModel(new String[]{"ID", "Nome", "Preço", "Estoque"}, 0);
        tabelaProdutos = new JTable(modeloProdutos);
        tabelaProdutos.getSelectionModel().addListSelectionListener(e -> {
            int linha = tabelaProdutos.getSelectedRow();
            if (linha >= 0) {
                txtProdId.setText(modeloProdutos.getValueAt(linha, 0).toString());
                txtProdNome.setText(modeloProdutos.getValueAt(linha, 1).toString());
                txtProdPreco.setText(modeloProdutos.getValueAt(linha, 2).toString());
                txtProdEstoque.setText(modeloProdutos.getValueAt(linha, 3).toString());
            }
        });
        painel.add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAdd = new JButton("Adicionar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");

        btnAdd.addActionListener(e -> { adicionarProduto(); carregarProdutos(); carregarPedidos(); });
        btnAtualizar.addActionListener(e -> { atualizarProduto(); carregarProdutos(); carregarPedidos(); });
        btnExcluir.addActionListener(e -> { excluirProduto(); carregarProdutos(); carregarPedidos(); });
        btnLimpar.addActionListener(e -> { txtProdId.setText(""); txtProdNome.setText(""); txtProdPreco.setText(""); txtProdEstoque.setText(""); tabelaProdutos.clearSelection(); });

        botoes.add(btnAdd); botoes.add(btnAtualizar); botoes.add(btnExcluir); botoes.add(btnLimpar);
        painel.add(botoes, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarAbaPedidos() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Gestão de Vendas"));

        txtPedId = new JTextField(); txtPedId.setEditable(false);
        cbPedCliente = new JComboBox<>();
        cbPedProduto = new JComboBox<>();
        txtPedQtd = new JTextField();

        form.add(new JLabel("ID do Pedido (Automático):")); form.add(txtPedId);
        form.add(new JLabel("Selecione o Cliente:")); form.add(cbPedCliente);
        form.add(new JLabel("Selecione o Produto:")); form.add(cbPedProduto);
        form.add(new JLabel("Quantidade da Venda:")); form.add(txtPedQtd);

        painel.add(form, BorderLayout.NORTH);

        modeloPedidos = new DefaultTableModel(new String[]{"ID Pedido", "Cliente", "Produto", "Quantidade"}, 0);
        tabelaPedidos = new JTable(modeloPedidos);
        
        tabelaPedidos.getSelectionModel().addListSelectionListener(e -> {
            int linha = tabelaPedidos.getSelectedRow();
            if (linha >= 0) {
                txtPedId.setText(modeloPedidos.getValueAt(linha, 0).toString());
                cbPedCliente.setSelectedItem(modeloPedidos.getValueAt(linha, 1).toString());
                cbPedProduto.setSelectedItem(modeloPedidos.getValueAt(linha, 2).toString());
                txtPedQtd.setText(modeloPedidos.getValueAt(linha, 3).toString());
            }
        });
        painel.add(new JScrollPane(tabelaPedidos), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAdicionar = new JButton("🛒 Registrar Venda");
        JButton btnAtualizar = new JButton("Atualizar Pedido");
        JButton btnExcluir = new JButton("Excluir Pedido");
        // Botão Limpar aba de Pedidos
        JButton btnLimpar = new JButton("Limpar");

        btnAdicionar.addActionListener(e -> { adicionarPedido(); carregarPedidos(); carregarProdutos(); });
        btnAtualizar.addActionListener(e -> { atualizarPedido(); carregarPedidos(); carregarProdutos(); });
        btnExcluir.addActionListener(e -> { excluirPedido(); carregarPedidos(); carregarProdutos(); });
        btnLimpar.addActionListener(e -> limparCamposPedido());

        botoes.add(btnAdicionar);
        botoes.add(btnAtualizar);
        botoes.add(btnExcluir);
        botoes.add(btnLimpar); // Adicionado ao painel
        
        painel.add(botoes, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarAbaGeral() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modeloGeral = new DefaultTableModel(new String[]{"ID Venda", "Cliente / Empresa", "Produto Comprado", "Qtd Vendida", "Valor Total (R$)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaGeral = new JTable(modeloGeral);
        
        JLabel lblTitulo = new JLabel("📊 Relatório Consolidado de Vendas (Financeiro e Integração)", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        painel.add(lblTitulo, BorderLayout.NORTH);
        painel.add(new JScrollPane(tabelaGeral), BorderLayout.CENTER);

        return painel;
    }

  
    // 5. MÉTODOS DE BANCO DE DADOS (CRUD simples) - Agliberto

    
    private void carregarClientes() {
        modeloClientes.setRowCount(0);
        cbPedCliente.removeAllItems();
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Clientes")) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                modeloClientes.addRow(new Object[]{id, nome, rs.getString("email")});
                cbPedCliente.addItem(id + " - " + nome);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    private void adicionarCliente() {
        String sql = "INSERT INTO Clientes (nome, email) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, txtCliNome.getText());
            stmt.setString(2, txtCliEmail.getText());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cliente Cadastrado!");
            txtCliId.setText(""); txtCliNome.setText(""); txtCliEmail.setText("");
        } catch (SQLException ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
    }

    private void atualizarCliente() {
        if(txtCliId.getText().isEmpty()) return;
        String sql = "UPDATE Clientes SET nome = ?, email = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, txtCliNome.getText());
            stmt.setString(2, txtCliEmail.getText());
            stmt.setInt(3, Integer.parseInt(txtCliId.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cliente Atualizado!");
        } catch (SQLException ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
    }

    private void excluirCliente() {
        if(txtCliId.getText().isEmpty()) return;
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Clientes WHERE id = ?")) {
            stmt.setInt(1, Integer.parseInt(txtCliId.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cliente Excluído!");
            txtCliId.setText(""); txtCliNome.setText(""); txtCliEmail.setText("");
        } catch (SQLException ex) { JOptionPane.showMessageDialog(this, "Erro: Cliente possui pedidos vinculados."); }
    }

    private void carregarProdutos() {
        modeloProdutos.setRowCount(0);
        cbPedProduto.removeAllItems();
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Produtos")) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                modeloProdutos.addRow(new Object[]{id, nome, rs.getDouble("preco"), rs.getInt("quantidade_estoque")});
                cbPedProduto.addItem(id + " - " + nome);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    private void adicionarProduto() {
        String sql = "INSERT INTO Produtos (nome, preco, quantidade_estoque) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, txtProdNome.getText());
            stmt.setDouble(2, Double.parseDouble(txtProdPreco.getText().replace(",", ".")));
            stmt.setInt(3, Integer.parseInt(txtProdEstoque.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Produto Cadastrado!");
            txtProdId.setText(""); txtProdNome.setText(""); txtProdPreco.setText(""); txtProdEstoque.setText("");
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto."); }
    }

    private void atualizarProduto() {
        if(txtProdId.getText().isEmpty()) return;
        String sql = "UPDATE Produtos SET nome = ?, preco = ?, quantidade_estoque = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, txtProdNome.getText());
            stmt.setDouble(2, Double.parseDouble(txtProdPreco.getText().replace(",", ".")));
            stmt.setInt(3, Integer.parseInt(txtProdEstoque.getText()));
            stmt.setInt(4, Integer.parseInt(txtProdId.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Produto Atualizado!");
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro ao atualizar produto."); }
    }

    private void excluirProduto() {
        if(txtProdId.getText().isEmpty()) return;
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Produtos WHERE id = ?")) {
            stmt.setInt(1, Integer.parseInt(txtProdId.getText()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Produto Excluído!");
            txtProdId.setText(""); txtProdNome.setText(""); txtProdPreco.setText(""); txtProdEstoque.setText("");
        } catch (SQLException ex) { JOptionPane.showMessageDialog(this, "Erro: Produto vinculado a pedido."); }
    }

    private void carregarPedidos() {
        modeloPedidos.setRowCount(0);
        modeloGeral.setRowCount(0);

        String sqlJoin = "SELECT p.id AS venda_id, p.cliente_id, c.nome AS cliente_nome, p.produto_id, prod.nome AS produto_nome, p.quantidade AS qtd, (p.quantidade * prod.preco) AS valor_total " +
                         "FROM Pedidos p INNER JOIN Clientes c ON p.cliente_id = c.id " +
                         "INNER JOIN Produtos prod ON p.produto_id = prod.id";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlJoin)) {
            
            while (rs.next()) {
                String infoCliente = rs.getInt("cliente_id") + " - " + rs.getString("cliente_nome");
                String infoProduto = rs.getInt("produto_id") + " - " + rs.getString("produto_nome");
                
                modeloPedidos.addRow(new Object[]{rs.getInt("venda_id"), infoCliente, infoProduto, rs.getInt("qtd")});
                
                String valorMonetario = String.format("R$ %.2f", rs.getDouble("valor_total")).replace(".", ",");
                modeloGeral.addRow(new Object[]{rs.getInt("venda_id"), rs.getString("cliente_nome"), rs.getString("produto_nome"), rs.getInt("qtd"), valorMonetario});
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    private void adicionarPedido() {
        if (cbPedCliente.getSelectedItem() == null || cbPedProduto.getSelectedItem() == null || txtPedQtd.getText().isEmpty()) return;
        
        int idCliente = Integer.parseInt(((String) cbPedCliente.getSelectedItem()).split(" - ")[0]);
        int idProduto = Integer.parseInt(((String) cbPedProduto.getSelectedItem()).split(" - ")[0]);
        int qtdDesejada = Integer.parseInt(txtPedQtd.getText());

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            PreparedStatement stmtEstoque = conn.prepareStatement("SELECT quantidade_estoque FROM Produtos WHERE id = ?");
            stmtEstoque.setInt(1, idProduto);
            ResultSet rs = stmtEstoque.executeQuery();
            if (rs.next() && rs.getInt("quantidade_estoque") < qtdDesejada) {
                JOptionPane.showMessageDialog(this, "Estoque insuficiente!"); return;
            }

            PreparedStatement stmtPedido = conn.prepareStatement("INSERT INTO Pedidos (cliente_id, produto_id, quantidade) VALUES (?, ?, ?)");
            stmtPedido.setInt(1, idCliente); stmtPedido.setInt(2, idProduto); stmtPedido.setInt(3, qtdDesejada);
            stmtPedido.executeUpdate();

            PreparedStatement stmtBaixa = conn.prepareStatement("UPDATE Produtos SET quantidade_estoque = quantidade_estoque - ? WHERE id = ?");
            stmtBaixa.setInt(1, qtdDesejada); stmtBaixa.setInt(2, idProduto);
            stmtBaixa.executeUpdate();

            JOptionPane.showMessageDialog(this, "Venda Registrada!");
            limparCamposPedido();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
    }

    private void atualizarPedido() {
        if (txtPedId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido na tabela para atualizar."); return;
        }

        int idPedido = Integer.parseInt(txtPedId.getText());
        int novoIdCliente = Integer.parseInt(((String) cbPedCliente.getSelectedItem()).split(" - ")[0]);
        int novoIdProduto = Integer.parseInt(((String) cbPedProduto.getSelectedItem()).split(" - ")[0]);
        int novaQtd = Integer.parseInt(txtPedQtd.getText());

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            PreparedStatement stmtAntigo = conn.prepareStatement("SELECT produto_id, quantidade FROM Pedidos WHERE id = ?");
            stmtAntigo.setInt(1, idPedido);
            ResultSet rsAntigo = stmtAntigo.executeQuery();
            
            if (rsAntigo.next()) {
                int produtoAntigo = rsAntigo.getInt("produto_id");
                int qtdAntiga = rsAntigo.getInt("quantidade");

                PreparedStatement devolveEstoque = conn.prepareStatement("UPDATE Produtos SET quantidade_estoque = quantidade_estoque + ? WHERE id = ?");
                devolveEstoque.setInt(1, qtdAntiga); devolveEstoque.setInt(2, produtoAntigo);
                devolveEstoque.executeUpdate();

                PreparedStatement checaEstoque = conn.prepareStatement("SELECT quantidade_estoque FROM Produtos WHERE id = ?");
                checaEstoque.setInt(1, novoIdProduto);
                ResultSet rsEstoque = checaEstoque.executeQuery();
                
                if (rsEstoque.next() && rsEstoque.getInt("quantidade_estoque") >= novaQtd) {
                    PreparedStatement atualizaPedido = conn.prepareStatement("UPDATE Pedidos SET cliente_id = ?, produto_id = ?, quantidade = ? WHERE id = ?");
                    atualizaPedido.setInt(1, novoIdCliente); atualizaPedido.setInt(2, novoIdProduto); atualizaPedido.setInt(3, novaQtd); atualizaPedido.setInt(4, idPedido);
                    atualizaPedido.executeUpdate();

                    PreparedStatement retiraEstoque = conn.prepareStatement("UPDATE Produtos SET quantidade_estoque = quantidade_estoque - ? WHERE id = ?");
                    retiraEstoque.setInt(1, novaQtd); retiraEstoque.setInt(2, novoIdProduto);
                    retiraEstoque.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Pedido Atualizado e Estoque Ajustado!");
                    limparCamposPedido();
                } else {
                    devolveEstoque = conn.prepareStatement("UPDATE Produtos SET quantidade_estoque = quantidade_estoque - ? WHERE id = ?");
                    devolveEstoque.setInt(1, qtdAntiga); devolveEstoque.setInt(2, produtoAntigo);
                    devolveEstoque.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Estoque insuficiente para a nova quantidade!");
                }
            }
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
    }

    private void excluirPedido() {
        if (txtPedId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido na tabela para excluir."); return;
        }

        int idPedido = Integer.parseInt(txtPedId.getText());

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            PreparedStatement stmtBusca = conn.prepareStatement("SELECT produto_id, quantidade FROM Pedidos WHERE id = ?");
            stmtBusca.setInt(1, idPedido);
            ResultSet rs = stmtBusca.executeQuery();

            if (rs.next()) {
                int idProduto = rs.getInt("produto_id");
                int quantidadeVendida = rs.getInt("quantidade");

                PreparedStatement stmtDelete = conn.prepareStatement("DELETE FROM Pedidos WHERE id = ?");
                stmtDelete.setInt(1, idPedido);
                stmtDelete.executeUpdate();

                PreparedStatement stmtDevolve = conn.prepareStatement("UPDATE Produtos SET quantidade_estoque = quantidade_estoque + ? WHERE id = ?");
                stmtDevolve.setInt(1, quantidadeVendida);
                stmtDevolve.setInt(2, idProduto);
                stmtDevolve.executeUpdate();

                JOptionPane.showMessageDialog(this, "Pedido excluído e produtos devolvidos ao estoque!");
                limparCamposPedido();
            }
        } catch (SQLException ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
    }

    // Método que o botão vai chamar
    private void limparCamposPedido() {
        txtPedId.setText("");
        txtPedQtd.setText("");
        tabelaPedidos.clearSelection();
        if(cbPedCliente.getItemCount() > 0) cbPedCliente.setSelectedIndex(0);
        if(cbPedProduto.getItemCount() > 0) cbPedProduto.setSelectedIndex(0);
    }

  
    // 6. MÉTODO MAIN (START DO PROGRAMA) - Breno
   
    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            new SistemaVendasGUI().setVisible(true);
        });
    }
}