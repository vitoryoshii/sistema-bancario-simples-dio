# 🏦 Sistema Bancário em Java

Projeto simples que simula uma conta bancária com funcionalidades básicas, incluindo gerenciamento de saldo, cheque especial, depósitos, saques e pagamento de boletos. Feito para melhor aprendizado da linguagem utilizada.

---

## ✨ Funcionalidades

- 👤 Criar cliente com nome e CPF.  
- 🏧 Criar conta bancária com depósito inicial.  
- 💰 Consultar saldo e cheque especial.  
- ➕ Depositar dinheiro, quitando cheque especial com taxa de 20% quando usado.  
- 💸 Sacar dinheiro usando saldo e cheque especial.  
- 🧾 Pagar boleto (funciona como saque).  
- 🔍 Verificar se está usando cheque especial.

---

## 📋 Regras do Cheque Especial

- 🛑 Limite definido na criação da conta:  
  - Depósito inicial até R$500,00 → limite de R$50,00.  
  - Depósito inicial acima de R$500,00 → limite igual a 50% do depósito inicial.  
- 💵 Quando o cheque especial é usado, uma taxa de 20% sobre o valor usado é cobrada no próximo depósito.

---

## 🚀 Como usar

1. 💻 Clone o repositório:
   ```bash
    git clone https://github.com/vitoryoshii/sistema-bancario-simples-dio
   ```
2. 🛠️ Compile as classes:
   ```bash
    javac Client.java BankAccount.java MainSystemBank.java
   ```
3. ▶️ Execute o programa:
   ```bash
    java MainSystemBank
   ```
   
---

## 🖥️ Exemplo de execução

```plaintext
Digite seu nome: Vitor  
Digite seu CPF (Somente números): 12345678900  
Qual o depósito inicial: 1000  
Conta Bancaria: Conta de Vitor | Saldo: R$ 1000.00 | Limite Cheque Especial: R$ 500.00

===ESCOLHA UMA OPERAÇÃO===
1 - CONSULTAR SALDO
2 - CONSULTAR CHEQUE ESPECIAL
3 - DEPOSITAR DINHEIRO
4 - SACAR DINHEIRO
5 - PAGAR BOLETO
6 - VERIFICAR SE USO DO CHEQUE ESPECIAL
0 - Sair
===========================
```

---

## 🔮 Próximos passos

- ✅ Validar CPF com regras oficiais.  
- 🖼️ Criar interface gráfica.  
- 💾 Implementar persistência de dados (arquivo ou banco de dados).  
- 📄 Gerar extrato bancário.  

---

## 🤝 Contribuição

Contribuições são bem-vindas!  
Faça um fork, crie sua branch com a feature ou correção e abra um pull request.
