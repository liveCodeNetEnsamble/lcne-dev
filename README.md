# lcne-dev

Sistema de conexión en red del grupo LiveCodeNet Ensamble. Extensión para SuperCollider.

## Instalación

Coloca el archivo lcne.sc en la carpeta de Extensiones de SuperCollider.

## Para iniciar el sistema de conexión en red:

LCNE.start("nombre", false, false);

El primer parámetro es el nombre de usuario que aparecerá en la red.

El segundo parámetro abre el scope cuando está en true.

El tercer parámetro abre el meter cuando está en true.

## Para probar sonido:

LCNE.test

El mensaje .test hará sonar una secuencia del instrumento \default alternando entre dos bocinas.