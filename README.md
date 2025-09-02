# 🏦 Sistema Bancário Simples em Java

Este projeto é uma simulação de um sistema bancário básico, desenvolvido em Java, que permite o gerenciamento de clientes e suas contas, incluindo operações de depósito, saque e visualização de extrato. O sistema também possui um módulo de gerente para cadastro e ativação de contas de clientes.

---

## ✨ Funcionalidades

### **Acesso ao Sistema**
*   **Acesso Cliente:** Permite que clientes existentes acessem suas contas para realizar operações.
*   **Acesso Gerente:** Permite que gerentes acessem funcionalidades administrativas, com cadastro inicial caso o CPF não seja encontrado.

### **Funcionalidades do Cliente**
*   **Saque:** Realiza a retirada de um valor da conta, verificando se a conta está ativa e se há saldo suficiente.
*   **Depósito:** Adiciona um valor à conta, verificando se a conta está ativa.
*   **Extrato:** Exibe todas as movimentações (saques e depósitos) e o saldo atual da conta.

### **Funcionalidades do Gerente**
*   **Criar Cliente:** Cadastra um novo cliente no sistema, solicitando nome, CPF, data de nascimento e endereço.
*   **Ativar Conta Cliente:** Ativa a conta de um cliente existente, permitindo que ele realize operações bancárias.
*   **Listar Clientes:** Exibe uma lista detalhada de todos os clientes cadastrados.
*   **Listar Contas Clientes:** Apresenta um resumo das contas dos clientes, indicando se estão ativas ou não.

---

## ⚙️ Estrutura do Projeto

O projeto é organizado em pacotes e classes para melhor modularidade:

*   `main.MainSystemBank`: Classe principal que contém o ponto de entrada do sistema e os menus de interação (principal, cliente e gerente).
*   `models.Client`: Representa um cliente do banco, com atributos como nome, CPF, data de nascimento, endereço, status da conta e saldo. Contém métodos para saque, depósito e exibição de extrato.
*   `models.Manager`: Representa um gerente do banco, com atributos como nome, CPF, data de nascimento, endereço, usuário e senha. Contém métodos para criar clientes, ativar contas e listar clientes/contas.
*   `models.User`: Classe base para `Client` e `Manager`, contendo atributos comuns como nome, CPF, data de nascimento e endereço.
*   `util.UtilServices`: Classe utilitária para funções de busca, como encontrar clientes ou gerentes por CPF.

---

## 🚀 Como Usar

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/vitoryoshii/sistema-bancario-simples-dio
    ```
2.  **Navegue até o diretório do projeto:**
    ```bash
    cd sistema-bancario-simples-dio
    ```
3.  **Compile as classes Java:**
    ```bash
    javac -d out src/main/*.java src/models/*.java src/util/*.java
    ```
    *(Assumindo que os arquivos `.java` estão em `src/main`, `src/models`, `src/util` e a saída compilada vai para `out`)*

4.  **Execute o programa:**
    ```bash
    java -cp out main.MainSystemBank
    ```

---

## 📝 Exemplo de Interação

Ao iniciar o programa, você verá o menu principal:

```plaintext

=== BANCO DIGITAL ===
1 - ACESSO CLIENTE
2 - ACESSO GERENTE
0 - SAIR
===========================
OPÇÃO:
```
--- 

*   **Para Acessar como Gerente (primeira vez):**
    *   Escolha `2`.
    *   Digite um CPF (ex: `123.456.789-00`). O sistema informará que o CPF não está cadastrado.
    *   Prossiga com o cadastro do gerente, informando nome, data de nascimento, endereço, usuário e senha.
    *   Após o cadastro, você poderá logar com o usuário e senha definidos.

*   **Para Criar um Cliente (como Gerente):**
    *   Acesse o menu do gerente.
    *   Escolha `1 - CRIAR CLIENTE`.
    *   Preencha os dados solicitados (nome, CPF, data de nascimento, endereço).

*   **Para Ativar a Conta de um Cliente (como Gerente):**
    *   Acesse o menu do gerente.
    *   Escolha `2 - ATIVAR CONTA CLIENTE`.
    *   Digite o CPF do cliente.

*   **Para Acessar como Cliente:**
    *   Volte ao menu principal (`0` no menu do gerente).
    *   Escolha `1`.
    *   Digite o CPF de um cliente que já foi cadastrado e teve sua conta ativada pelo gerente.
    *   Você poderá então realizar saques, depósitos e ver o extrato.
    
---

## 🤝 Contribuição

Contribuições são bem-vindas! Se você tiver ideias para melhorias ou encontrar bugs, sinta-se à vontade para:

1.  Fazer um fork do repositório.
2.  Criar uma nova branch (`git checkout -b feature/sua-feature` ou `bugfix/seu-bug`).
3.  Realizar suas alterações e fazer commit (`git commit -m 'Adiciona nova feature'`).
4.  Enviar para a branch (`git push origin feature/sua-feature`).
5.  Abrir um Pull Request.
