# Projetos de demonstração para o ContextNet

## groupdefiner

  Group Definer para envio de mensagens de broadcast (para os processing nodes se anunciarem uns aos outros)

  Utilize o plugin do Maven para gerar o arquivo .jar. Depois basta executar a aplicação na
  linha de comando: java -jar groupdefiner-0.0.1-SNAPSHOT.jar

## contexnetdemo

  Processing Node: aplicação web.

  Pode-se executar várias cópias desta aplicação. Cada uma iniciará em uma porta diferente.

  Uma vez iniciada, cada aplicação pode ser acessada pelo browser. Os diversos processing nodes
  enviam mensagens com seu uuid que são recebidas pelos demais. 

  De posse deste uuid, as aplicações podem enviar mensagens umas às outras.

  
  Utilize o plugin do Maven para gerar o arquivo .jar. Depois basta executar a aplicação na
  linha de comando: java -jar contextnetdemo-0.0.1-SNAPSHOT.jar

## interscitydemo

  Aplicação web para cadastrar capacidades e recursos na plataforma InterSCity.

## CEP Demo

  Aplicação para demonstrar o uso do engine Esper.

