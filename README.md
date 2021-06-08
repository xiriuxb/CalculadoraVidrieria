# Calculadora Vidrieria
**Nota:** La aplicación funciona pero faltan añadir funciones, validar campos y mejorar el código
## Descripción General
Calcuadora hecha para calcular el precio de pedazos de vidrio en una vidrieria, se puede guardar los tipos de vidrios con un precio con el que se calcua el costo de los pedazos.
La lista de tipos de vidrio disponibles se almacena en un archivo en el almacenamiento interno.

## Pestaña Lista
En esta pestaña se agregan los pedazos de vidrios, las medidas se ingresan en cm (ej: 23x45). La cantidad por defecto es 1, por lo que se puede dejar en blanco.

## Pestaña TODO
TODO()

## Pestaña Vidrios
Se muestran los tipos de vidrios que existen. El botón flotante "Más"(+) abre un pop-up para guardar un nuevo tipo de vidrio, el botón flotante "Guardar" guarda los cambios realizados en el archivo del almacenamiento interno.

## Actividad (Dialog) Vidrio Nuevo
Guarda temporalmente un nuevo vidrio. Es necesario un nombre, medidas de ancho y alto de la lámina, precio de la lámina y precio por metro. Importante presionar el boton "Guardar" de la pestaña vidrios para guardar definitivamente los cambios. Los campos en esta activity no estan validados por lo que dejar un campo en blanco genera un en error.
