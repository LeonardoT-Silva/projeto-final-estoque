# TecInformática

| | |
|---|---|
| **Aluno(a)** | Leonardo Teodoro da Silva |
| **Turma** | TEC-N-001788/2026 |
| **Opção escolhida** | Controle de Estoque |
| **Versão atual** | 1.0.0 |

---

## 1. Visão geral

### 1.1 Problema
<!-- Que problema o sistema resolve? Quem sofre com esse problema hoje e como ele é resolvido (planilha, papel, WhatsApp...)? 3 a 5 linhas. --> Uma loja de informática precisa controlar os produtos disponíveis em estoque. Quando esse controle é feito manualmente, podem ocorrer erros nas quantidades e dificuldades para identificar produtos que precisam ser comprados.
O sistema tem como objetivo facilitar o cadastro dos produtos e o controle das entradas e saídas do estoque.


### 1.2 Canvas do projeto

| Bloco | Resposta |
|---|---|
| **Usuários** (quem usa o sistema) | Funcionários responsáveis pelo estoque e gerente da loja. |
| **Problema** (dor atual) | Dificuldade para controlar as quantidades dos produtos e identificar quando é necessário repor o estoque. |
| **Proposta de valor** (o que melhora com o sistema) | Facilitar a organização dos produtos e diminuir erros no controle do estoque. |
| **Funcionalidades principais** | Cadastrar produtos e categorias, consultar produtos e registrar entradas e saídas. |
| **Informações que o sistema guarda** | Nome, descrição, categoria, preço, quantidade disponível e estoque mínimo dos produtos. |
| **Indicadores** (o que o gestor quer acompanhar) | Quantidade de produtos disponíveis, produtos com estoque baixo e movimentações realizadas. |
| **Restrições** (prazo, tecnologia, equipe) | Projeto individual · Java 21 · Spring Boot 4 · entrega v1.0 em 11/11 |

---

## 2. Requisitos

### 2.1 Requisitos funcionais (o que o sistema FAZ)

| ID | Requisito | Nível |
|---|---|---|
| RF01 | O sistema deve permitir cadastrar, listar, editar e excluir categorias. | Essencial |
| RF02 | O sistema deve permitir cadastrar, listar, editar e consultar produtos. | Essencial |
| RF03 | O sistema deve permitir registrar entradas e saídas de produtos no estoque. | Essencial |
| RF04 | O sistema deve exibir os produtos com quantidade igual ou inferior ao estoque mínimo. | Essencial |

### 2.2 Requisitos não funcionais (COMO o sistema deve ser)

| ID | Requisito |
|---|---|
| RNF01 | O sistema deve ser acessado pelo navegador (aplicação web). |
| RNF02 | O sistema deve apresentar telas simples e organizadas, facilitando o uso pelos funcionários. |
| RNF03 | |

### 2.3 Regras de negócio (as REGRAS do negócio que o sistema precisa respeitar)

| ID | Regra |
|---|---|
| RN01 | Não é permitido registrar uma saída maior que a quantidade disponível do produto. |
| RN02 | O sistema deve alertar quando a quantidade de um produto for igual ou inferior ao estoque mínimo cadastrado. |
| RN03 | Limites: valor máximo, quantidade máxima, prazo máximo. |
| RN04 | Datas: não permitir fins de semana, feriados ou datas fora do horário de funcionamento. |
| RN05 | Situação: impedir uma mudança fora da ordem (por exemplo, concluir sem iniciar). |
| RN06 | Dependência: exigir um campo quando outro tiver certo valor. |
---

## 3. Histórias de usuário

Formato: **Como** *[papel]*, **quero** *[ação]*, **para** *[benefício]*.

**HU01 — Cadastro de produtos —** Como funcionário do estoque, quero cadastrar produtos, para manter os itens da loja organizados.

- Critério de aceite: O sistema deve permitir informar o nome, a categoria, o preço e a quantidade do produto.
- Critério de aceite: Após salvar, o produto deve aparecer na lista de produtos.

**HU02 — Movimentação de estoque —** Como funcionário do estoque, quero registrar entradas e saídas de produtos, para manter as quantidades atualizadas.

- Critério de aceite: O sistema deve aumentar a quantidade quando uma entrada for registrada.
- Critério de aceite: O sistema não deve permitir uma saída maior que a quantidade disponível.

**HU03 — Consulta de estoque baixo —** Como gerente da loja, quero visualizar os produtos com estoque baixo, para saber quais itens precisam ser comprados.

- Critérios de aceite: O sistema deve identificar os produtos com quantidade igual ou inferior ao estoque mínimo.
- Critérios de aceite: O sistema deve exibir o nome e a quantidade disponível desses produtos.

---

## 4. Modelo de dados

```mermaid
classDiagram
    direction LR
    class Categoria {
        Long id
        String nome
        String descricao
    }
    class Produto {
        Long id
        String codigo
        String nome
        String unidade
        BigDecimal precoCusto
        Integer estoqueMinimo
        Integer saldo
    }
    class Movimentacao {
        Long id
        String tipo
        Integer quantidade
        LocalDateTime dataHora
        String observacao
    }
    Categoria "1" --> "*" Produto : classifica
    Produto "1" --> "*" Movimentacao : registra
```

---

## 5. Como executar

### No GitHub Codespaces (recomendado)
1. No repositório, clique em **Code → Codespaces** e abra o Codespace existente (ou **Create codespace on main**).
2. No terminal, execute:
   ```bash
   mvn spring-boot:run
   ```
3. Aguarde a mensagem `Started SistemaApplication` no terminal.
4. Abra a aba **Portas** (ao lado do Terminal) e, na linha da porta **8080**, clique no ícone de globo (**Abrir no navegador**).
5. Entre com um dos usuários da seção **Usuários de teste**.

### No computador (ferramentas instaladas)
Requisitos: JDK 21 e Maven (ou uma IDE com suporte a Java, como IntelliJ IDEA ou VS Code com o *Extension Pack for Java*).
Na pasta do projeto, execute `mvn spring-boot:run` e acesse `http://localhost:8080`.

### Banco de dados de desenvolvimento
- O banco H2 fica na pasta `dados/` (não vai para o GitHub). Na primeira execução, o sistema cria as tabelas e os dados de exemplo.
- Para recomeçar com os dados de exemplo: pare o sistema e apague a pasta (`rm -rf dados`).
- Console do banco: endereço `/h2-console` (só para o perfil ADMIN).

### Acesso
| Usuário | Senha | Perfil |
|---|---|---|
| admin | admin123 | Administrador |
| operador | operador123 | Almoxarife |

*(o login passa a ser exigido a partir do Encontro 7)*

---

## 6. Tecnologias
Java 21 · Spring Boot 4 · Spring MVC · Thymeleaf · Bootstrap 5 · Spring Data JPA · H2 (desenvolvimento) · MySQL (produção) · Spring Security · Git/GitHub

## 7. Uso de inteligência artificial
<!-- Registre aqui, de forma resumida, quando e como você usou ferramentas de IA de forma relevante no projeto. -->

## 8. Histórico de versões
| Versão | Data | Descrição |
|---|---|---|
| 0.1.0 | | Projeto inicial criado a partir do repositório modelo |
| 1.0.0 | 11/11/2026 | Primeira versão estável |

## Usuários de teste

| Usuário | Senha | Perfil | O que pode fazer |
|---|---|---|---|
| admin | admin123 | Administrador | Tudo |
| operador | operador123 | Almoxarife | Consulta produtos e categorias e registra movimentações |

## 9. Funcionalidades e telas

### Funcionalidades
- Cadastro de produtos e categorias.
- Registro de entradas e saídas, com atualização automática do saldo.
- Alerta de produtos abaixo do estoque mínimo.
- Painel com os indicadores do estoque.
- Login com dois perfis: o Administrador pode tudo; o Operador consulta produtos e categorias e registra movimentações.

### Telas

**Painel com os indicadores do estoque**

![Painel com os indicadores do estoque](docs/painel.png)

**Movimentações de entrada e saída**

![Movimentações de entrada e saída](docs/movimentacoes.png)

**Regra de negócio: saída maior que o saldo**

![Regra de negócio: saída maior que o saldo](docs/regra.png)

**Tela de login**

![Tela de login](docs/login.png)

## 10. Checklist do nível essencial

- [x] O sistema sobe sem erros seguindo a seção 5 (Como executar)
- [x] Cadastro completo de produtos (cadastrar, listar, editar e excluir), com validação
- [x] Cadastro completo de categorias, com validação
- [x] Movimentações ligadas aos produtos (e produtos ligados às categorias), com lista de escolha no formulário
- [x] Regra de negócio 1: A saída não pode ser maior que o saldo do produto.
- [x] Regra de negócio 2: Alerta quando o saldo fica abaixo do estoque mínimo.
- [x] Login com 2 perfis (admin e operador), senhas criptografadas e acesso conforme o perfil
- [x] Painel com os indicadores na página inicial
- [x] Repositório no GitHub com commits frequentes
- [x] README com a visão do projeto, como executar, usuários de teste, funcionalidades e telas
- [x] Quadro Kanban atualizado