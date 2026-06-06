# Projeto Integrador V - Sistema de Vendas Empresariais - Desktop

Um sistema desktop completo focado na automação de pedidos e controle de estoque para distribuidoras de bebidas, desenvolvido para substituir o processo manual de envio de e-mails e planilhas isoladas.

## 💻 Tecnologias Utilizadas
* **Java:** Lógica de programação e integração de sistemas.
* **Java Swing:** Construção da Interface Gráfica do Usuário (GUI).
* **FlatLaf:** Tema visual para modernização da interface (Look and Feel).
* **MySQL:** Banco de dados relacional para persistência de dados.
* **JDBC:** API de conexão e execução de queries SQL.
* **Maven:** Gerenciamento de dependências e build do projeto.

## ⚙️ Funcionalidades
* **Gerenciamento de Clientes:** Operações completas de CRUD (Criar, Ler, Atualizar e Excluir) para o cadastro de empresas compradoras.
* **Gerenciamento de Produtos:** Controle de catálogo com nome, preço e quantidade física disponível no estoque.
* **Automação de Vendas:** Registro de pedidos com validação de disponibilidade e baixa automática no estoque do banco de dados.
* **Relatório Consolidado:** Visualização financeira em tempo real através de junções relacionais (INNER JOIN), exibindo cruzamento de dados de clientes, produtos e o cálculo automático do Valor Total (R$).

## 🚀 Como Executar o Projeto

1. **Configuração do Banco de Dados:**
   * Abra o MySQL (via serviço local ou XAMPP).
   * Execute os comandos contidos no script SQL fornecido para criar o banco `SistemaVendasDB` e as tabelas `Clientes`, `Produtos` e `Pedidos`.
2. **Configuração do Projeto:**
   * Certifique-se de alterar a variável `SENHA` no arquivo `SistemaVendasGUI.java` para a senha do seu usuário `root` local.
3. **Dependências:**
   * O projeto utiliza Maven. Verifique se o seu `pom.xml` possui as dependências do `mysql-connector-j` e do `flatlaf`. Execute a atualização do projeto para baixar os pacotes.
4. **Execução:**
   * Rode o método `main` da classe `SistemaVendasGUI` diretamente pela sua IDE (ex: VSCode, Eclipse, IntelliJ).
