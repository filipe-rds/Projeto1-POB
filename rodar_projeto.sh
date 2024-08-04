#!/bin/bash

# Script criado para rodar o projeto Java com bibliotecas que não são mais acessíveis por padrão a partir do Java 9

# Definir os argumentos da JVM
JVM_ARGS="--add-opens=java.base/java.time=ALL-UNNAMED \
          --add-opens=java.base/java.lang=ALL-UNNAMED \
          --add-opens=java.base/java.io=ALL-UNNAMED \
          --add-opens=java.base/java.security=ALL-UNNAMED \
          --add-opens=java.base/java.net=ALL-UNNAMED \
          --add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED \
          --add-opens=java.base/jdk.internal.misc=ALL-UNNAMED \
          --add-opens=java.base/jdk.internal.reflect=ALL-UNNAMED \
          --add-opens=java.base/java.util=ALL-UNNAMED \
          --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"

# Determinar o diretório do script
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

# Definir o caminho para o arquivo JAR do projeto relativo ao diretório do script
JAR_PATH="$SCRIPT_DIR/Projeto.jar"

# Executar o JAR com os argumentos da JVM
java $JVM_ARGS -jar "$JAR_PATH"
