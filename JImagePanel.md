**Ver en:** _español_ | [ingles](http://code.google.com/p/javaclassesrepository/wiki/JImagePanel?tm=6&wl=en)

# Introducción #
Clase que extiende de [javax.swing.JPanel](http://docs.oracle.com/javase/6/docs/api/javax/swing/JPanel.html) y permite el manejo de imagenes en el componente
# Definición #
```
package org.schimpf.awt.image
public class JImagePanel extends JPanel {
   /**
    * Constructor a partir de una instancia de BufferedImage
    * @param image Imagen precargada
    */
   public JImagePanel(BufferedImage image)

   /**
    * Constructor a partir de una instancia de BufferedImage especificando la posicion de la imagen en pixeles
    * @param image Imagen precargada
    * @param x Posicion horizontal
    * @param y Posicion vertical
    */
   public JImagePanel(BufferedImage image, int x, int y)

   /**
    * Constructor a partir de una instancia de BufferedImage especificando la posicion de la imagen en pixeles y la rotacion
    * @param image Imagen precargada
    * @param x Posicion horizontal
    * @param y Posicion vertical
    * @param degress Grados de rotacion
    */
   public JImagePanel(BufferedImage image, int x, int y, float degress)

   /**
    * Constructor especificando la ruta de la imagen
    * @param imagePath Ruta de la imagen
    * @throws IOException si la ruta a la imagen no existe
    */
   public JImagePanel(String imagePath) throws IOException

   /**
    * Constructor especificando la ruta de la imagen y la posicion de la imagen en pixeles
    * @param imagePath Ruta de la imagen
    * @param x Posicion horizontal
    * @param y Posicion vertical
    * @throws IOException si la ruta a la imagen no existe
    */
   public JImagePanel(String imagePath, int x, int y) throws IOException

   /**
    * Constructor especificando la ruta de la imagen y la posicion de la imagen en pixeles y la rotacion
    * @param imagePath Ruta de la imagen
    * @param x Posicion horizontal
    * @param y Posicion vertical
    * @param degress Grados de rotacion
    * @throws IOException si la ruta a la imagen no existe
    */
   public JImagePanel(String imagePath, int x, int y, float degress) throws IOException

   /**
    * Reposiciona la imagen del panel
    * @param x Posicion horizontal
    * @param y Posicion vertical
    */
   public void positionate(float x, float y)

   /**
    * Rota la imagen del panel
    * @param degress Grados de rotacion
    */
   public void rotate(float angle)
}
```
| **Fuentes** | [último](http://code.google.com/p/javaclassesrepository/source/browse/Trunk/awt/src/org/schimpf/awt/image/JImagePanel.java) |
|:------------|:-----------------------------------------------------------------------------------------------------------------------------|

# Utilización #
```
public class EjemploUtilizacion {
   public static void main(String... arg) {
      // creamos un JFrame
      JFrame window = new JFrame("Test JImagePanel");
      // agregamos la imagen en la posicion 50, 30 y rotada 75 grados
      window.add(new JImagePanel("/ruta/imagen/prueba.jpg", 50, 30, 75));
      // seteamos el tamaño
      window.setSize(400, 300);
      // mostramos el JFrame con la imagen
      window.setVisible(true);
   }
}
```

[Volver a la lista de paquetes](http://code.google.com/p/javaclassesrepository/wiki/packages?tm=6&wl=es)

---

  * **Hermann D. Schimpf**
  * _hschimpf@hds-solutions.net_
  * **[HDS Solutions](http://hds-solutions.net)** - _Soluciones Informáticas_