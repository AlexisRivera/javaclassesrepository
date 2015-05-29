**Ver en:** _español_ | [ingles](http://code.google.com/p/javaclassesrepository/wiki/JAnimatedImagePanel?tm=6&wl=en)

# Introducción #
Implementacion de JImagePanel pero con animaciones de movimientos y rotacion
# Definición #
```
package org.schimpf.awt.image
public class JAnimatedImagePanel extends JImagePanel {
   /**
    * Constructor a partir de una instancia de BufferedImage
    * @param image Imagen precargada
    */
   public JAnimatedImagePanel(BufferedImage image)

   /**
    * Constructor a partir de una instancia de BufferedImage especificando la posicion de la imagen en pixeles
    * @param image Imagen precargada
    * @param x Posicion horizontal
    * @param y Posicion vertical
    */
   public JAnimatedImagePanel(BufferedImage image, int x, int y)

   /**
    * Constructor a partir de una instancia de BufferedImage especificando la posicion de la imagen en pixeles y la rotacion
    * @param image Imagen precargada
    * @param x Posicion horizontal
    * @param y Posicion vertical
    * @param degress Grados de rotacion
    */
   public JAnimatedImagePanel(BufferedImage image, int x, int y, float degress)

   /**
    * Constructor especificando la ruta de la imagen
    * @param imagePath Ruta de la imagen
    * @throws IOException si la ruta a la imagen no existe
    */
   public JAnimatedImagePanel(String imagePath) throws IOException

   /**
    * Constructor especificando la ruta de la imagen y la posicion de la imagen en pixeles
    * @param imagePath Ruta de la imagen
    * @param x Posicion horizontal
    * @param y Posicion vertical
    * @throws IOException si la ruta a la imagen no existe
    */
   public JAnimatedImagePanel(String imagePath, int x, int y) throws IOException

   /**
    * Constructor especificando la ruta de la imagen y la posicion de la imagen en pixeles y la rotacion
    * @param imagePath Ruta de la imagen
    * @param x Posicion horizontal
    * @param y Posicion vertical
    * @param degress Grados de rotacion
    * @throws IOException si la ruta a la imagen no existe
    */
   public JAnimatedImagePanel(String imagePath, int x, int y, float degress) throws IOException

   /**
    * Reposiciona la imagen del panel
    * @param x Posicion horizontal
    * @param y Posicion vertical
    */
   public void positionate(float x, float y)

   /**
    * Reposiciona la imagen del panel especificando la velocidad
    * @param x Posicion horizontal
    * @param y Posicion vertical
    * @param speed Velocidad de movimiento (pixeles por segundo)
    */
   public void positionate(float x, float y, float speed)

   /**
    * Rota la imagen del panel
    * @param degress Grados de rotacion
    */
   public void rotate(float angle)

   /**
    * Rota la imagen del panel
    * @param degress Grados de rotacion
    * @param speed Velocidad de rotacion (grados por segundo)
    */
   public void rotate(float angle, float speed)
}
```
| **Fuentes** | [último](http://code.google.com/p/javaclassesrepository/source/browse/Trunk/awt/src/org/schimpf/awt/image/JAnimatedImagePanel.java) |
|:------------|:-------------------------------------------------------------------------------------------------------------------------------------|

# Utilización #
```
public class EjemploUtilizacion {
   public static void main(String... arg) {
      // creamos un JFrame
      JFrame window = new JFrame("Test JAnimatedImagePanel");
      // cargamos la imagen en la posicion 50, 30 y rotada 75 grados
      JImagePanel image = new JImagePanel("/ruta/imagen/prueba.jpg", 50, 30, 75)
      // agregamos la imagen al JFrame
      window.add(image);
      // seteamos el tamaño
      window.setSize(400, 300);
      // mostramos el JFrame con la imagen
      window.setVisible(true);
      // esperamos un segundo
      Thread.sleep(1000);
      // rotamos la imagen a 130 grados
      image.rotate(130);
   }
}
```

[Volver a la lista de paquetes](http://code.google.com/p/javaclassesrepository/wiki/packages?tm=6&wl=es)

---

  * **Hermann D. Schimpf**
  * _hschimpf@hds-solutions.net_
  * **[HDS Solutions](http://hds-solutions.net)** - _Soluciones Informáticas_