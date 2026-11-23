# Biblioteca (back-end)

API REST do sistema de cadastro de livros, feita com Java 17 e Spring Boot. Os dados ficam no Firestore (Firebase). O front-end Angular está em um repositório separado.

## Equipe

### Ivan Roberto Gomes Alves dos Santos (maior parte do back-end)

API REST e acesso aos dados.

- `ErroFirestoreException`: erro das falhas de acesso ao Firestore, respondido como 502.
- `LivroController`, rota base `/livros`:
  - `GET /livros/list`, lista os livros;
  - `POST /livros`, cadastra e responde 201;
  - `PUT /livros/{id}`, atualiza;
  - `DELETE /livros/{id}`, exclui e responde 204;
  - resposta 404 com a mensagem "Livro não encontrado" quando o id não existe.
- `Livro`: modelo do livro, com `@DocumentId` para receber o id do documento do Firestore sem gravá-lo como campo.
- `LivroRequest`: validação do corpo das requisições, com título e autor de até 120 caracteres, gênero de até 60, descrição de até 500 e capa opcional de até 200 mil caracteres no formato `data:image/jpeg;base64`. Também converte a requisição em `Livro` pelo método `paraLivro`.
- `LivroRepository`, coleção `livros` do Firestore:
  - listar ordenado por título;
  - salvar com id gerado pelo Firestore;
  - atualizar e excluir, verificando antes se o documento existe;
  - método `aguardar`, que espera a resposta do Firestore e converte as falhas em `ErroFirestoreException`.

### Timóteo Batista da Silva Júnior

Configuração, integração com o Firebase e infraestrutura do projeto.

- `FirebaseConfig`: cria o bean do `Firestore`, lê o arquivo de credenciais pelo caminho configurado e inicializa o `FirebaseApp` uma única vez por processo.
- `CorsConfig`: libera a origem do front-end para `GET`, `POST`, `PUT` e `DELETE` em `/livros/**`.
- `application.properties`: porta, caminho das credenciais e origem permitida no CORS.
- `pom.xml`: dependências do projeto (web, validação e `firebase-admin`).
- `.gitignore`: garante que o `firebase-service-account.json` não seja versionado.
- Passo a passo de configuração do Firebase neste README.

## Tecnologias

| Tecnologia | Versão | Uso |
| --- | --- | --- |
| Java | 17 | Linguagem da API |
| Spring Boot | 4.1.2 | Framework da API REST, com os starters `webmvc` e `validation` |
| Firebase Admin SDK | 9.10.0 | Acesso ao Firestore |
| Maven | definido pelo `pom.xml` | Dependências e execução |
| Cloud Firestore | serviço gerenciado | Banco de dados dos livros |

## Pré-requisitos

- Java 17 ou superior
- Maven
- Conta no Firebase, com um projeto e o Firestore ativado

## Como executar

### 1. Configurar o Firebase

1. Crie um projeto em https://console.firebase.google.com.
2. No menu lateral, abra Firestore Database e clique em Criar banco de dados. Escolha o modo de produção, pois o back-end usa o Admin SDK, que não depende das regras de segurança.
3. Em Configurações do projeto, aba Contas de serviço, clique em Gerar nova chave privada.
4. Salve o arquivo baixado como `firebase-service-account.json` na raiz deste repositório.

A coleção `livros` é criada automaticamente no primeiro cadastro. O arquivo de credenciais está no `.gitignore` e não deve ser versionado.

### 2. Executar no Spring Tools

1. File > Import > Maven > Existing Maven Projects e selecione a pasta do repositório.
2. Clique com o botão direito em `BibliotecaApplication` > Run As > Spring Boot App.

Pelo terminal, também funciona com:

```bash
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.

## Configuração

As propriedades ficam em `src/main/resources/application.properties`.

| Propriedade | Descrição |
| --- | --- |
| `firebase.credentials-path` | Caminho do arquivo de credenciais. Pode ser trocado pela variável de ambiente `FIREBASE_CREDENTIALS_PATH` |
| `cors.allowed-origin` | Origem do front-end liberada no CORS (`http://localhost:4200`) |

## Endpoints

| Método | Rota | Descrição |
| --- | --- | --- |
| GET | `/livros/list` | Lista os livros ordenados por título |
| POST | `/livros` | Cadastra um livro |
| PUT | `/livros/{id}` | Atualiza um livro |
| DELETE | `/livros/{id}` | Exclui um livro |

Corpo do POST e do PUT:

```json
{
  "titulo": "Dom Casmurro",
  "autor": "Machado de Assis",
  "genero": "Romance",
  "descricao": "Clássico da literatura brasileira",
  "capa": ""
}
```

A `capa` é opcional. Quando existe, é uma imagem JPEG em base64 no formato `data:image/jpeg;base64,...` (até 200 mil caracteres), reduzida pelo navegador antes do envio e gravada no próprio documento do Firestore. A descrição aceita até 500 caracteres.

## Repositório do front-end

O front-end Angular que consome esta API fica em outro repositório: https://github.com/alvess29/sistema_livros_frontend.
