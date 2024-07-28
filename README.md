# Gerenciamento De Projetos

Este projeto é parte de um desafio de desenvolvimento de software, onde são implementadas diversas funcionalidades para gerenciar projetos. O projeto é uma API em Java.

## Tecnologias Utilizadas

- Spring Boot
- Spring Data JPA
- Banco de Dados H2
- Maven
- Tomcat
- Swagger

### Passo a Passo para Executar uma Aplicação Spring Boot

#### 1. Configuração das Variáveis de Ambiente do Java:

Antes de tudo, é necessário verificar se o Java está instalado em sua máquina e configurar as variáveis de ambiente. Siga estes passos:

- **Verifique se o Java está instalado**: Abra o terminal (no Windows, utilize o prompt de comando) e digite o comando `java -version`. Se o Java estiver instalado, você verá a versão instalada.
  
- **Instale o Java, se necessário**: Se o Java não estiver instalado, você pode baixá-lo e instalá-lo no site oficial do [Java](https://www.java.com/).

- **Configurar as Variáveis de Ambiente**:
   - **No Windows**:
     - Pesquise por "Variáveis de Ambiente" na barra de busca do Windows e clique em "Editar as variáveis de ambiente do sistema".
     - Na janela que abrir, clique em "Variáveis de ambiente...".
     - Em "Variáveis do sistema", clique em "Novo..." e adicione uma nova variável chamada `JAVA_HOME` com o caminho para a pasta onde o Java está instalado (por exemplo, `C:\Program Files\Java\jdk1.8.0_311`).
     - Encontre a variável `Path` em "Variáveis do sistema" e clique em "Editar...".
     - Adicione `%JAVA_HOME%\bin` ao final do valor da variável `Path`.
     - Clique em "OK" para salvar as alterações.

   - **No macOS e Linux**:
     - Abra o terminal e edite o arquivo `.bash_profile` ou `.bashrc` (ou o arquivo de perfil correspondente) com seu editor de texto favorito.
     - Adicione a linha `export JAVA_HOME=/caminho/para/java` com o caminho para a pasta onde o Java está instalado.
     - Adicione a linha `export PATH=$PATH:$JAVA_HOME/bin`.
     - Salve e feche o arquivo.
     - Execute o comando `source ~/.bash_profile` ou `source ~/.bashrc` para aplicar as alterações.

#### 2. Clonando o Repositório:

Agora que o Java está configurado, você pode clonar o repositório da aplicação Spring Boot. Siga estes passos:

1. Abra o terminal.
2. Navegue até o diretório onde deseja clonar o repositório.
3. Execute o seguinte comando:
git clone https://github.com/arturtarcisio/gerenciamentoDeProjetos.git

4. Aguarde até que o processo de clonagem seja concluído.

#### 3. Executando a Aplicação:

Com o repositório clonado, você pode executar a aplicação Spring Boot. Siga estes passos:

1. Navegue até o diretório da aplicação clonada.
cd gerenciamentoDeProjetos

2. Execute o seguinte comando para iniciar a aplicação:
./mvnw spring-boot:run

Este comando irá compilar e executar a aplicação Spring Boot.

3. Aguarde até que a compilação seja concluída e a aplicação seja iniciada.

4. Uma vez iniciada, você verá mensagens indicando que a aplicação está rodando e pronta para receber requisições.

Agora a aplicação está em execução em sua máquina e você pode acessar os endpoints conforme documentado no README do projeto.
