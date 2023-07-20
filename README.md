# Simulador Leitor RFID (Acura e Similares) para Testes de Conexões Socket

<p align="center">
  <img src="https://github.com/alanmello1f/simulador-Leitor-RFID/assets/106087501/ee61a2cd-2a68-4dc8-8aae-33aa11903757" alt="simuladorLeitorRFID">
</p>

# Descrição

Este projeto consiste em um simulador de leitor RFID da marca Acura ou similares, com o objetivo de realizar testes de conexões com terminais (clientes) socket. Ao clicar no botão "Iniciar", um servidor socket é criado na porta especificada (padrão 3513) no localhost. A partir daí, conexões socket podem ser estabelecidas e o envio de "tags RFID" é iniciado automaticamente após alguns segundos, seguindo o padrão hexadecimal, por exemplo: 0X3DAC304D.

# Motivação

A realização de testes de conexão e recebimento de tags RFID é essencial para o desenvolvimento bem-sucedido de projetos nesta área. O simulador de leitor RFID se torna uma ferramenta útil, pois elimina a necessidade de possuir fisicamente o equipamento, agilizando o processo de desenvolvimento e permitindo uma abordagem mais eficiente para testes e depuração.

# Instalação

1. Clone este repositório em sua máquina local:

`git clone https://github.com/seu-usuario/simulador-leitor-RFID.git`

2. Navegue até o diretório do projeto:

`cd simulador-leitor-RFID`

3. Execute o programa e inicie o servidor socket:

`java -jar simulador-leitor-RFID.jar`

A partir deste ponto, o simulador estará pronto para receber conexões socket e simular o envio de "tags RFID" para fins de teste.

Sinta-se à vontade para contribuir para o projeto, reportar problemas ou propor melhorias. Esperamos que este simulador seja útil para agilizar seus testes e projetos relacionados a leitores RFID.



