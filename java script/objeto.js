class Objeto {
  constructor(x, y, largura, altura) {
    this.posX = x;
    this.posY = y;
    this.largura = largura;
    this.altura = altura;
    this.velAliens = 10;
    this.direcaoAliens = 1;
  }

  update() {
    // cada subclasse implementa sua própria lógica
  }

}
